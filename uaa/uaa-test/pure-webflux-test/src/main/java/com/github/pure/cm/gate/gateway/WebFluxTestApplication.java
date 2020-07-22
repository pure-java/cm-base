package com.github.pure.cm.gate.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author bairitan
 * @date 2019/12/4
 */
@SpringBootApplication
@EnableFeignClients
public class WebFluxTestApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(WebFluxTestApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(this.getClass());
  }
}
