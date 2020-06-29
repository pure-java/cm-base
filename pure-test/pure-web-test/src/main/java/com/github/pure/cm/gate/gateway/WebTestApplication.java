package com.github.pure.cm.gate.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author bairitan
 * @date 2019/12/4
 */
@SpringBootApplication
public class WebTestApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WebTestApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
