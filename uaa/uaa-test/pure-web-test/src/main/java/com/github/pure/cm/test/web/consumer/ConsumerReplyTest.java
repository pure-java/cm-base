package com.github.pure.cm.test.web.consumer;

import com.github.pure.cm.common.core.util.date.DateUtil;
import com.github.pure.cm.test.web.ConsumerMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.springframework.stereotype.Component;

/**
 * <p>消费者
 *
 *
 * </p>
 *
 * @author : 陈欢
 * @date : 2020-08-05 16:46
 **/
@Slf4j
@Component
@RocketMQMessageListener(topic = ConsumerMessage.Reply_topic, consumerGroup = "consumer-group-reply")
public class ConsumerReplyTest implements RocketMQReplyListener<ConsumerMessage, String> {

    @Override
    public String onMessage(ConsumerMessage consumerMessage) {
        String dateTime = DateUtil.newIns().asStr();
        log.error("消费消息时间{}:{}", dateTime, consumerMessage);
        return dateTime;
    }
}
