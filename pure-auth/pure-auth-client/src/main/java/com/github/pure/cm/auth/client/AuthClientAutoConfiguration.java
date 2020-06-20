package com.github.pure.cm.auth.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈欢
 * @date 2020/5/31
 */
@Slf4j
@Configuration
@ComponentScan(value = "com.github.pure.cm.auth.client")
@EnableFeignClients({"com.github.pure.cm.auth.client.feign"})
public class AuthClientAutoConfiguration implements InitializingBean {

  public AuthClientAutoConfiguration() {
    log.info("------------------------加载权限客户端服务------------------------");
  }

  @Override
  public void afterPropertiesSet() throws Exception {

  }


}
