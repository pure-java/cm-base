package com.github.pure.cm.manage.common;

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
@EnableFeignClients({"com.github.pure.cm.manage.common"})
public class ManageCommonApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ManageCommonApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
