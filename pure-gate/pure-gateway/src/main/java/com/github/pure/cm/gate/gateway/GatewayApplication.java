package com.github.pure.cm.gate.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author bairitan
 * @date 2019/12/4
 */
@SpringBootApplication
@ComponentScan(value = "com.github.pure.cm")
@EnableFeignClients({"com.github.pure.cm.gate.gateway"})
public class GatewayApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(GatewayApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(this.getClass());
  }
}
