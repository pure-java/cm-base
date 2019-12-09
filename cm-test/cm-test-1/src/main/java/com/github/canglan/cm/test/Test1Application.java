package com.github.canglan.cm.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author bairitan
 * @date 2019/12/7
 */
@SpringBootApplication(scanBasePackages = {"com.github.canglan.cm"})
@EnableFeignClients
public class Test1Application {

  public static void main(String[] args) {
    SpringApplication.run(Test1Application.class, args);
  }
}
