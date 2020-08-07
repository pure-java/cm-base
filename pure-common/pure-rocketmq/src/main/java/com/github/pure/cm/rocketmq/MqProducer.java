package com.github.pure.cm.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

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
public class MqProducer {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

}
