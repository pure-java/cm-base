package com.github.pure.cm.auth.sdk;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 公共组件自动扫描包
 *
 * @author 陈欢
 * @since 2020/6/18
 */
@ComponentScan(value = "com.github.pure.cm.auth.sdk")
@EnableFeignClients(value = "com.github.pure.cm.auth.sdk.core.feign")
public class AuthSdkAutoConfiguration {

}
