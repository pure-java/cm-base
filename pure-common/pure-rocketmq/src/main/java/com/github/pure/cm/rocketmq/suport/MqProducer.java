package com.github.pure.cm.rocketmq.suport;

import com.github.pure.cm.rocketmq.suport.call.SendCallBackFactory;
import com.github.pure.cm.rocketmq.suport.call.SendMsgCallBack;
import com.github.pure.cm.rocketmq.suport.msg.MqMessage;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

import static org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration.ROCKETMQ_TEMPLATE_DEFAULT_GLOBAL_NAME;

/**
 * <p>
 * 封装mq 发送消息
 * </p>
 *
 * @since 陈欢 2020-08-13 15:45
 **/
@Slf4j
@NoArgsConstructor
@Component(ROCKETMQ_TEMPLATE_DEFAULT_GLOBAL_NAME)
@Qualifier(ROCKETMQ_TEMPLATE_DEFAULT_GLOBAL_NAME)
public class MqProducer extends RocketMQTemplate implements Ordered {

    @Autowired
    private SendCallBackFactory sendCallBackFactory;

    @Autowired
    public void setMqProducer(DefaultMQProducer mqProducer) {
        super.setProducer(mqProducer);
    }

    @Override
    @PreDestroy
    public void destroy() {
        super.destroy();
    }

    @Autowired
    public void setRocketMQMessageConverter(RocketMQMessageConverter rocketMQMessageConverter) {
        super.setMessageConverter(rocketMQMessageConverter.getMessageConverter());
    }

    /**
     * Same to {@link #asyncSend(String, Message, SendMsgCallBack)}.
     */
    public <T> void asyncSend(String destination, Message<T> message, SendMsgCallBack<T> sendCallback, long timeout, int delayLevel) {
        MqMessage<T> build =
                new MqMessage<T>()
                        .setDestination(destination)
                        .setMessage(message)
                        .setTimeout(timeout)
                        .setDelayLevel(delayLevel);

        super.asyncSend(destination, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                sendCallback.onSuccess(build, sendResult);
            }

            @Override
            public void onException(Throwable e) {
                sendCallback.onException(build, e);
            }
        }, timeout, delayLevel);
    }


    /**
     * Same to {@link #asyncSend(String, Message, SendMsgCallBack)}.
     */
    public <T> void asyncSend(String destination, Message<T> message, SendMsgCallBack<T> sendCallback, long timeout) {
        asyncSend(destination, message, sendCallback, timeout, 0);
    }

    /**
     * 异步发送消息<br>
     * 异步发送消息到代理。异步传输通常用于对时间敏感的业务场景中。<br>
     * 此方法立即返回。发送完成后，将执行sendCallback。<br>
     * 与syncSend（String，Object）相似，内部实现可能会在声明发送失败之前重试DefaultMQProducer.getRetryTimesWhenSendAsyncFailed时间，<br>
     * 这可能会导致消息重复，并且应用程序开发人员将解决此潜在问题。
     */
    public <T> void asyncSend(String destination, Message<T> message, SendMsgCallBack<T> sendCallback) {
        asyncSend(destination, message, sendCallback, super.getProducer().getSendMsgTimeout());
    }

    /**
     * {@link #asyncSend(String, Message, SendMsgCallBack)}. with send timeout specified in addition.
     *
     * @param destination  formats: `topicName:tags`
     * @param payload      the Object to use as payload
     * @param sendCallback {@link SendCallback}
     * @param timeout      send timeout with millis
     */
    public <T> void asyncSend(String destination, T payload, SendMsgCallBack<T> sendCallback, long timeout) {
        Message<T> message = MessageBuilder.withPayload(payload).build();
        asyncSend(destination, message, sendCallback, timeout);
    }

    /**
     * Same to {@link #asyncSend(String, Message, SendMsgCallBack)}.
     *
     * @param destination  formats: `topicName:tags`
     * @param payload      the Object to use as payload
     * @param sendCallback {@link SendCallback}
     */
    public <T> void asyncSend(String destination, T payload, SendMsgCallBack<T> sendCallback) {
        asyncSend(destination, payload, sendCallback, super.getProducer().getSendMsgTimeout());
    }


    /**
     * 其他叫 asyncSend 名称的方法实际都是调用的该方法，只是精简了参数
     */
    @Override
    public void asyncSend(String destination, Message<?> message, SendCallback sendCallback, long timeout, int delayLevel) {

        MqMessage<?> build = new MqMessage()

                .setDestination(destination)
                .setMessage(message)
                .setSendCallback(sendCallback)
                .setTimeout(timeout)
                .setDelayLevel(delayLevel);

        super.asyncSend(destination, message, sendCallBackFactory.newIns(build), timeout, delayLevel);
    }

    /**
     * 其他叫 asyncSendOrderly 名称的方法实际都是调用的该方法，只是精简了参数
     */
    @Override
    public void asyncSendOrderly(String destination, Message<?> message, String hashKey, SendCallback sendCallback, long timeout) {
        MqMessage<?> build = new MqMessage()
                .setDestination(destination)
                .setMessage(message)
                .setHashKey(hashKey)
                .setSendCallback(sendCallback)
                .setTimeout(timeout);

        super.asyncSendOrderly(destination, message, hashKey, sendCallBackFactory.newIns(build), timeout);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
