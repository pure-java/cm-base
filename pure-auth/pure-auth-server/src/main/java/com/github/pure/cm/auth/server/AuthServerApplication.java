package com.github.pure.cm.auth.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author bairitan
 * @since 2019/11/11
 */
@SpringBootApplication
@ComponentScan(value = "com.github.pure.cm")
@EnableFeignClients
@MapperScan(value = "com.github.pure.cm.auth.server.mapper")
@EnableCaching
// @DubboComponentScan(basePackages = "com.github.pure.cm.auth.server.dubbo")
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

}