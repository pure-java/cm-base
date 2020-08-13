package com.github.pure.cm.rocketmq.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.messaging.Message;

/**
 * 默认发送消息失败处理类
 *
 * @param <T>
 * @since : 陈欢 2020-08-13 16:48
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class MqAsyncMessage<T> {

    /**
     * 异步回调
     */
    private SendCallback sendCallback;
    /**
     * 消息
     */
    private Message<?> message;
    /**
     * 消息
     */
    private T payload;
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
