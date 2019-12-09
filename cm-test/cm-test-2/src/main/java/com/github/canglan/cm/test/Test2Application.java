package com.github.canglan.cm.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author bairitan
 * @date 2019/12/7
 */
@SpringBootApplication(scanBasePackages = {"com.github.canglan.cm"})
@EnableFeignClients(/*value = "com.github.canglan.cm"*/)
public class Test2Application {

  public static void main(String[] args) {
    SpringApplication.run(Test2Application.class, args);
  }
}
