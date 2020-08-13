package com.github.pure.cm.rocketmq.core;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.messaging.Message;
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
    private CallBackFactory callBackFactory;

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
     * 其他叫 asyncSend 名称的方法实际都是调用的该方法，只是精简了参数
     */
    @Override
    public void asyncSend(String destination, Message<?> message, SendCallback sendCallback, long timeout, int delayLevel) {

        MqAsyncMessage<?> build = MqAsyncMessage.builder()

                .destination(destination)
                .message(message)
                .sendCallback(sendCallback)
                .timeout(timeout)
                .delayLevel(delayLevel)

                .build();

        super.asyncSend(destination, message, callBackFactory.newIns(build), timeout, delayLevel);
    }

    /**
     * 其他叫 asyncSendOrderly 名称的方法实际都是调用的该方法，只是精简了参数
     */
    @Override
    public void asyncSendOrderly(String destination, Message<?> message, String hashKey, SendCallback sendCallback, long timeout) {

        MqAsyncMessage<?> build = MqAsyncMessage.builder()

                .destination(destination)
                .message(message)
                .hashKey(hashKey)
                .sendCallback(sendCallback)
                .timeout(timeout)

                .build();

        super.asyncSendOrderly(destination, message, hashKey, callBackFactory.newIns(build), timeout);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
