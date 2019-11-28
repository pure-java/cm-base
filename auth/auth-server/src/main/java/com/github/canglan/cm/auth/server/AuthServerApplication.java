package com.github.canglan.cm.auth.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
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
public class AuthServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthServerApplication.class, args);
  }
}
