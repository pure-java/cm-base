package com.github.pure.cm.rocketmq.suport;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pure.cm.common.core.exception.BusinessException;
import com.github.pure.cm.common.core.util.JsonUtil;
import com.github.pure.cm.common.core.util.StringUtil;
import com.github.pure.cm.rocketmq.MqProperties;
import com.github.pure.cm.rocketmq.suport.msg.MqIdMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <pre>
 * rocket mq 防重消费实现
 * 消费去重：
 * 消息消费时，会在redis中根据消息的业务ID将消费状态(消费中或消费完成)进行保存。
 * 如果消费失败或消费状态异常，则抛出异常让broker重发消息进行重试。
 * 注意：
 * 1.如果redis key过期后，有重复消息到来，则去重失败。（消费时间过长或增加redis key过期时间）
 * 2.多个服务之间不能消息去重。（多个服务使用同一个redis db就可以实现）
 * </pre>
 *
 * @since 陈欢 2020-08-06 18:08
 **/
@Slf4j
public abstract class MqConsumer<T extends MqIdMessage<T>> implements RocketMQListener<MessageExt>, MessageListenerConcurrently {

    /**
     * mq配置
     */
    @Autowired
    private MqProperties mqProperties;
    /**
     * 消费中
     */
    private static final String CONSUMING_STATUS = "CONSUMING";
    /**
     * 消费成功
     */
    private static final String CONSUMED_STATUS = "CONSUMED";

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 处理匹配消费
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        int ackIndex = -1;
        boolean normal = true;
        try {
            for (int i = 0; i < msgs.size(); i++) {
                if (consumer(msgs.get(i))) {
                    ackIndex = i;
                } else {
                    normal = false;
                    break;
                }
            }
        } catch (Exception e) {
            normal = false;
            log.error("消费发生异常：总消息{}条,ackIndex = {}", msgs.size(), ackIndex);
        }

        // 消费失败，设置最后正常消费的索引号
        if (!normal) {
            context.setAckIndex(ackIndex);
        }

        // 无论如何都返回成功，
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    /**
     * @param message
     */
    @Override
    public void onMessage(MessageExt message) {
        boolean consumer = consumer(message);
        if (!consumer) {
            throw new BusinessException("消费失败:" + new String(message.getBody(), StandardCharsets.UTF_8));
        }
    }

    private boolean consumer(MessageExt message) {
        String str = new String(message.getBody(), StandardCharsets.UTF_8);
        MqConsumer<T> consumerThat = this;
        // json转换为对应的数据
        T body = JsonUtil.singleInstance().jsonToObject(str, new TypeReference<T>() {
            @Override
            public Type getType() {
                return ((ParameterizedType) consumerThat.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            }
        });

        String nxId;
        if (Objects.isNull(body) || StringUtil.isBlank(nxId = body.getNxId())) {
            log.error("消息没有业务ID");
            return consumer(body);
        }

        // 设置redis key，值是一个ID
        if (setConsumingIfNX(nxId, this.mqProperties.getDedupExpireMillis())) {
            return consuming(message, body);
        } else {
            String consumerStatus = getConsumerStatus(nxId);
            // 在消费中或消费完成，都表示消费成功。
            // 正在消费中，表示已经有重复的消息了，把这条重复的消息跳过，当正在消费的消息如果消费失败，会抛出异常，进行消息重试；减少了重复的消息由于争夺 redis key
            if (CONSUMING_STATUS.equals(consumerStatus)) {
                log.warn("正在消费中");
                // 由于rocket mq重发消息时间有间隔，在这间隔内，redis key已经失效，导致消息可以被消费两次，所以如果重复了，则跳过这条重复消息不进行处理
                return true;
            } else if (CONSUMED_STATUS.equals(consumerStatus)) {
                log.debug("消费完成");
                return true;
            } else {
                // 状态不正确，broker 重发消息，重新消费
                // 当消费过程中，状态不正常，则会抛出异常，等待下次补偿消费。
                return false;
            }
        }
    }

    /**
     * 进行消费
     */
    private boolean consuming(MessageExt message, T body) {
        String nxId = body.getNxId();
        try {
            boolean consumer = this.consumer(body);// 进行消息消费
            // 消费成功
            if (consumer) {
                this.onSuccess(body);
                setConsumedNX(nxId, this.mqProperties.getDedupExpireMillis()); // 标记消费完成
            }
            return consumer;
        } catch (Exception e) {
            // 消费失败，进入异常处理逻辑
            try {
                // 消费失败，删除 消费中的key
                delConsumer(nxId);
            } catch (Exception ex) {
                log.error("消费失败:{},删除key失败", message, e);
            }
            onException(body, e);
            throw e;
        }
    }

    /**
     * 消费；
     *
     * @param message
     * @return
     */
    public abstract boolean consumer(T message);

    /**
     * 消费成功回调
     *
     * @param message
     */
    public void onSuccess(T message) {

    }

    /**
     * 消费失败回调
     *
     * @param message
     * @param e
     */
    public void onException(T message, Exception e) {

    }

    /**
     * 添加消费的redis key，进行去重<br>
     * 消费中
     *
     * @param msgUniqKey         去重key
     * @param expireMilliSeconds key在redis中的保存时间：毫秒数
     * @return 添加是否成功，如果失败，已经正在消费
     */
    public boolean setConsumingIfNX(String msgUniqKey, long expireMilliSeconds) {
        String dedupKey = builderDedupKey(msgUniqKey);

        Boolean absent = redisTemplate.opsForValue().setIfAbsent(dedupKey, CONSUMING_STATUS, Duration.ofMillis(expireMilliSeconds));
        return Optional.ofNullable(absent).orElse(Boolean.FALSE);
    }

    /**
     * 添加消费redis key，进行去重<br>
     * 消费完成
     *
     * @param msgUniqKey         去重key
     * @param expireMilliSeconds key在redis中的保存时间：毫秒数
     * @return 添加是否成功，如果失败，已经正在消费
     */
    public void setConsumedNX(String msgUniqKey, long expireMilliSeconds) {
        redisTemplate.opsForValue().set(builderDedupKey(msgUniqKey), CONSUMED_STATUS, Duration.ofMillis(expireMilliSeconds));
    }

    /**
     * 获取key
     *
     * @param msgUniqKey
     * @return
     */
    public String getConsumerStatus(String msgUniqKey) {
        return redisTemplate.opsForValue().get(builderDedupKey(msgUniqKey));
    }

    /**
     * 删除key
     *
     * @param msgUniqKey
     * @return
     */
    public boolean delConsumer(String msgUniqKey) {
        String key = builderDedupKey(msgUniqKey);
        if (StringUtil.isBlank(key)) {
            return false;
        }
        return redisTemplate.delete(key);
    }

    /**
     * 获取去重redis key
     *
     * @param msgUniqKey 消息唯一ID
     */
    public String builderDedupKey(String msgUniqKey) {
        RocketMQMessageListener annotation = this.getClass().getAnnotation(RocketMQMessageListener.class);
        if (Objects.isNull(annotation)) {
            throw new IllegalArgumentException("not used RocketMQMessageListener");
        }

        String topic = annotation.topic();

        SelectorType selectorType = annotation.selectorType();

        String tags = Objects.equals(selectorType, SelectorType.TAG) ? annotation.selectorExpression() : "";

        return applicationName + ":" + topic + ":" + (StringUtils.isEmpty(tags) ? ":" : tags + ":") + msgUniqKey;
    }
}
