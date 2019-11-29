package com.github.canglan.cm.auth.server.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * 客户端验证拦截器
 *
 * @author bairitan
 * @since 2019/11/12
 */
@Configuration
public class ClientInterceptor implements RequestInterceptor {

  @Override
  public void apply(RequestTemplate requestTemplate) {
  }
}
