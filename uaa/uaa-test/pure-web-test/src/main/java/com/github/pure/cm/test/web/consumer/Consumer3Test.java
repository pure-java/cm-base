package com.github.pure.cm.test.web.consumer;

import com.github.pure.cm.common.core.util.JsonUtil;
import com.github.pure.cm.rocketmq.suport.MqConsumer;
import com.github.pure.cm.test.web.ConsumerMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = ConsumerMessage.topic, selectorExpression = "test2", consumerGroup = "cousumer-group-3")
public class Consumer3Test extends MqConsumer<ConsumerMessage> {

    @Override
    public boolean consumer(ConsumerMessage message) {
        log.info("消费消息：{}", JsonUtil.json(message));
        return true;
    }
}
