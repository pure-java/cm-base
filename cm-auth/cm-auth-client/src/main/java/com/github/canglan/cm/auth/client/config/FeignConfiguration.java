package com.github.canglan.cm.auth.client.config;

import com.github.canglan.cm.auth.client.interceptor.FeignAuthRequestInterceptor;
import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * feign 配置
 *
 * @author bairitan
 * @date 2019/12/9
 */
// @Configuration
// @ConditionalOnClass({Hystrix.class})
// @ConditionalOnProperty(value = "hystrix.propagate.request-attribute.enabled", matchIfMissing = true)
public class FeignConfiguration {

  /**
   * 设置日志级别
   */
  @Bean
  public Logger.Level level() {
    return Logger.Level.FULL;
  }

  @Bean
  public FeignAuthRequestInterceptor feignAuthRequestInterceptor() {
    return new FeignAuthRequestInterceptor();
  }
}
