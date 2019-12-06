package com.github.canglan.cm.gate.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author bairitan
 * @date 2019/12/4
 */
@SpringBootApplication
@ComponentScan(value = "com.github.canglan.cm")
@EnableFeignClients({"com.github.canglan.cm.gate.gateway.feign"})
public class GatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(GatewayApplication.class, args);
  }
}
