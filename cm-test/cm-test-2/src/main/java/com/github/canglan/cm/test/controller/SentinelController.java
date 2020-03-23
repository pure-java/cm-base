package com.github.canglan.cm.test.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.github.canglan.cm.common.core.model.Result;
import com.github.canglan.cm.test.feign.SentinelTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * @author bairitan
 * @date 2020/1/7
 */
@RestController
@RequestMapping("/sentinelTest")
public class SentinelController {

  /**
   * 配置跨域
   */
  @Bean
  public CorsWebFilter corsFilter() {
    CorsConfiguration config = new CorsConfiguration();
    // cookie跨域
    config.setAllowCredentials(Boolean.TRUE);
    config.addAllowedMethod("*");
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    // 配置前端js允许访问的自定义响应头
    // config.addExposedHeader("setToken");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
    source.registerCorsConfiguration("/**", config);

    return new CorsWebFilter(source);
  }


  @Autowired
  private SentinelTest sentinelTest;

  /**
   * 使用硬编码方式
   */
  @PostMapping("/sphuNtTrace")
  public Result sphuNtTrace() {
    return sentinelTest.sphuNtTrace();
  }

  /**
   * 使用硬编码方式
   */
  @RequestMapping("/sphoNtTrace")
  public Result sphoNtTrace() {
    return sentinelTest.sphoNtTrace();
  }

  /**
   * 使用注解方式
   */
  @RequestMapping("/blockSentinelResource")
  public Result blockSentinelResource() {
    return sentinelTest.blockSentinelResource();
  }

}
