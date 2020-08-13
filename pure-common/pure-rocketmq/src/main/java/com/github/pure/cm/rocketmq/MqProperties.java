package com.github.pure.cm.rocketmq;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>mq 配置</p>
 *
 * @author 陈欢
 * @since : 2020-08-06 18:10
 **/
@Data
@Configuration
public class MqProperties {

    /**
     * MQ 消息去重 redis key
     */
    @Value("${pure.mq.consumer.dedup:MQ:MSG_DEDUP}")
    private String mqDistinct = "MQ:MSG_DEDUP";

    /**
     * mq 消息去重 key 过期时间：毫秒数<br>
     * key过期时间，毫秒，默认10000毫秒
     */
    @Value("${pure.mq.consumer.dedup-expire-millis:10000}")
    private Long dedupExpireMillis;

    /**
     * mq 消息判断事务的 key
     */
    @Value("${pure.mq.producer.tx:MQ:MSG_TX}")
    private String mqTx = "MQ:MSG_TX";

}
