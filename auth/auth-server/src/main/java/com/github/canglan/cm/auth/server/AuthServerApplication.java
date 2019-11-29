package com.github.canglan.cm.auth.server;

import java.util.ArrayList;
import java.util.List;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import feign.codec.Decoder;

/**
 * @author bairitan
 * @since 2019/11/11
 */
@SpringBootApplication
@ComponentScan("com.github.canglan.cm")
@EnableFeignClients
@MapperScan(value = "com.github.canglan.cm.auth.server.mapper")
public class AuthServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthServerApplication.class, args);
  }

}