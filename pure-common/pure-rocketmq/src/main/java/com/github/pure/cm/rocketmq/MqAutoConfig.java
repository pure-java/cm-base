package com.github.pure.cm.rocketmq;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>mq模块自动配置类
 *
 *
 * </p>
 *
 * @author : 陈欢
 * @date : 2020-08-06 18:19
 **/
@ComponentScan("com.github.pure.cm.rocketmq")
@ConfigurationPropertiesScan("com.github.pure.cm.rocketmq")
public class MqAutoConfig {
}
