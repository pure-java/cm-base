package com.github.pure.cm.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *
 *
 * </p>
 *
 * @author : 陈欢
 * @date : 2020-08-05 16:05
 **/
public class RocketMqTest {
    /**
     * 用于发送消息到 RocketMQ 的api
     */
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

}
