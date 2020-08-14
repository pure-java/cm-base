package com.github.pure.cm.rocketmq.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pure.cm.common.core.util.JsonUtil;
import com.github.pure.cm.common.core.util.StringUtil;
import com.github.pure.cm.rocketmq.MqMessage;
import com.github.pure.cm.rocketmq.MqProperties;
import com.github.pure.cm.rocketmq.exception.ConsumerException;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * rocket mq 生产者
 * 实现
 *
 * </p>
 *
 * @author : 陈欢
 * @date : 2020-08-06 18:08
 **/
@Slf4j
public abstract class MqConsumer<T extends MqMessage<T>> implements RocketMQListener<MessageExt> {

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

    @SneakyThrows
    @Override
    public void onMessage(MessageExt message) {
        String str = new String(message.getBody(), StandardCharsets.UTF_8);
        MqConsumer<T> consumerThat = this;
        // json转换为对应的数据
        T tType = JsonUtil.singleInstance().jsonToObject(str, new TypeReference<T>() {
            @Override
            public Type getType() {
                return ((ParameterizedType) consumerThat.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            }
        });

        String nxId;
        if (Objects.isNull(tType) || StringUtil.isBlank(nxId = tType.getNxId())) {
            log.error("消息没有业务ID");
            throw new RuntimeException("消息没有业务ID");
        }

        // 添加消费中的 redis key
        if (setConsumingIfNX(nxId, this.mqProperties.getDedupExpireMillis())) {
            // 进行消费
            boolean consumer;
            try {
                consumer = this.consumer(tType);
            } catch (Exception e) {
                // 消费失败，删除 消费中的key
                boolean delConsumer = delConsumer(nxId);
                log.error("消费失败:{},删除key:{}", message, delConsumer, e);
                throw new ConsumerException("删除去重redis key:[" + delConsumer + "];" + "消费mq消息失败:" + JsonUtil.json(message), e);
            }

            if (consumer) {
                // 消费成功，设置消费完成的key
                boolean consumedIfNX = setConsumedIfNX(nxId, this.mqProperties.getDedupExpireMillis());
                log.debug("设置消费完成:redis key:{},消费成功:{}", consumedIfNX, message);
            } else {
                // 消费失败，删除 消费中的key
                boolean delConsumer = delConsumer(nxId);
                log.debug("删除消费中redis key:{},消费失败:{}", delConsumer, message);
            }
        } else {
            log.debug("其他线程正在消费:{}", message);
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
    public boolean setConsumedIfNX(String msgUniqKey, long expireMilliSeconds) {
        String dedupKey = builderDedupKey(msgUniqKey);
        if (StringUtil.isBlank(dedupKey)) {
            return false;
        }

        boolean ifPresent = redisTemplate.opsForValue().setIfPresent(dedupKey, CONSUMED_STATUS, Duration.ofMillis(expireMilliSeconds));
        return Optional.ofNullable(ifPresent).orElse(Boolean.FALSE);
    }

    /**
     * 删除key
     *
     * @param msgUniqKey
     * @return
     */
    public boolean delConsumer(String msgUniqKey) {
        return redisTemplate.delete(builderDedupKey(msgUniqKey));
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