package com.github.canglan.cm.identity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
@SpringBootApplication
@ComponentScan("com.github.canglan.cm")
@MapperScan(value = "com.github.canglan.cm.identity.mapper")
public class IdentityApplication {

  public static void main(String[] args) {
    SpringApplication.run(IdentityApplication.class, args);
  }
}
