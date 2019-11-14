package com.github.canglan.cm.auth.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author bairitan
 * @date 2019/11/11
 */
@SpringBootApplication(scanBasePackages = "com.github.canglan.cm")
@EnableDiscoveryClient
@MapperScan(value = "com.github.canglan.cm.auth.server.mapper")
public class AuthServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthServerApplication.class, args);
  }
}
