package com.github.pure.cm.rocketmq.suport.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.springframework.messaging.Message;

/**
 * mq发送的消息信息
 *
 * @param <T>
 * @since : 陈欢 2020-08-13 16:48
 **/
@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class MqMessage<T> {

    /**
     * 异步回调
     */
    private SendCallback sendCallback;
    /**
     * 消息
     */
    private Message<T> message;
    /**
     * topic
     */
    private String destination;
    /**
     * 超时时间
     */
    private long timeout;
    /**
     * 重试等级
     */
    private int delayLevel;
    /**
     * hash key
     */
    private String hashKey;

}
