package com.github.canglan.cm.auth.client;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bairitan
 * @since 2019/11/18
 */
@SpringBootApplication
@RestController
@EnableFeignClients({"com.github.canglan.cm.auth.client.feign"})
public class AuthClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthClientApplication.class, args);
  }

  @GetMapping("/product/{id}")
  public String getProduct(@PathVariable String id, HttpServletRequest req) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    System.out.println("用户名  : " + authentication.getPrincipal());
    System.out.println("封装的传递信息  : " + AuthUtils.getReqUser(req));
    return "(Need Auth Request)product id : " + id;
  }

  @GetMapping("/order/{id}")
  public String getOrder(@PathVariable String id, HttpServletRequest req) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    System.out.println("用户名  : " + authentication.getPrincipal());
    System.out.println("封装的传递信息  : " + AuthUtils.getReqUser(req));
    return "(No Auth Request)order id : " + id;
  }
}
