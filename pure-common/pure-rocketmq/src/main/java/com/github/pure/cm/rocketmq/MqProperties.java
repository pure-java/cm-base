package com.github.pure.cm.rocketmq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>mq 枚举</p>
 *
 * @author : 陈欢
 * @date : 2020-08-06 18:10
 **/
@Configuration
@ConfigurationProperties(prefix = "pure")
public class MqProperties {

    /**
     * MQ 消息去重redis key
     */
    @Value("${pure.mq.distinct}")
    public String mqDistinct = "MQ:MSG_DISTINCT";

    /**
     * mq 消息判断事务的key
     */
    @Value("${pure.mq.tx}")
    public String mqTx = "MQ:MSG_TX";

}
