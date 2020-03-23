package com.github.canglan.cm.auth.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.configuration.ClientDetailsServiceConfiguration;

/**
 * @author bairitan
 * @since 2019/11/11
 */
@SpringBootApplication
@ComponentScan(value = "com.github.canglan.cm")
@EnableFeignClients
@MapperScan(value = "com.github.canglan.cm.auth.server.mapper")
@EnableCaching
public class AuthServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthServerApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(this.getClass());
  }
}