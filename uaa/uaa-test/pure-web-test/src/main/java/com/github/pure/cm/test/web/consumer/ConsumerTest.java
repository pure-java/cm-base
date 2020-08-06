package com.github.pure.cm.test.web.consumer;

import com.github.pure.cm.common.core.util.date.DateUtil;
import com.github.pure.cm.test.web.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
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
@RocketMQMessageListener(topic = Message.topic, consumerGroup = "cousumer-group")
public class ConsumerTest implements RocketMQListener<Message> {

    @Override
    public void onMessage(Message message) {
        log.error("消费消息时间{}:{}", DateUtil.newIns().asStr(), message);
    }
}
