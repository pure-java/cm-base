package com.github.canglan.cm.auth.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author bairitan
 * @since 2019/11/11
 */
@SpringBootApplication
@ComponentScan("com.github.canglan.cm")
@EnableFeignClients
@MapperScan(value = "com.github.canglan.cm.auth.server.mapper")
public class AuthServerApplication  extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(AuthServerApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(this.getClass());
  }
}