package com.github.pure.cm.test.web.consumer;

import com.github.pure.cm.common.core.util.JsonUtil;
import com.github.pure.cm.rocketmq.suport.MqConsumer;
import com.github.pure.cm.test.web.ConsumerMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 *
 * </p>
 *
 * @author : 陈欢
 * @date : 2020-08-06 11:27
 **/
@Slf4j
@Component
@RocketMQMessageListener(topic = ConsumerMessage.topic,selectorExpression = "test1", consumerGroup = "cousumer-group-1")
public class ConsumerTest extends MqConsumer<ConsumerMessage> {

    @Override
    public boolean consumer(ConsumerMessage message) {
        log.info("消费消息：{}", JsonUtil.json(message));
        return true;
    }
}
