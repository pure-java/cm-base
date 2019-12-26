package com.github.canglan.cm.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author bairitan
 * @date 2019/12/26
 */

@SpringBootApplication
@EnableDiscoveryClient // nacos 不需要该注解也可以自动现应用，因为添加了 spring-cloud-starter-alibaba-nacos-discovery 依赖之后会自动发现
@EnableAdminServer
public class AdminServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(AdminServerApplication.class, args);
  }

}