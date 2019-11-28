package com.github.canglan.cm.auth.client.interceptor;

import com.github.canglan.cm.auth.client.IgnoreToken;
import com.github.canglan.cm.auth.client.config.AuthResourceProperties;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author bairitan
 * @since 2019/11/19
 */
@Configuration
@Slf4j
public class UserAuthInterceptor extends HandlerInterceptorAdapter {

  @Autowired
  private AuthResourceProperties properties;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    HandlerMethod handlerMethod = (HandlerMethod) handler;
    IgnoreToken annotation = handlerMethod.getMethodAnnotation(IgnoreToken.class);
    if (annotation == null) {
      handlerMethod.getBeanType().getAnnotation(IgnoreToken.class);
    }
    if (annotation != null) {
      return super.preHandle(request, response, handler);
    }

    String token = request.getHeader(properties.getTokenHeader());
    if (!StringUtils.hasText(token)) {
      for (Cookie cookie : request.getCookies()) {
        if (cookie.getName().equalsIgnoreCase(properties.getTokenHeader())) {
          token = cookie.getValue();
        }
      }
    }

    log.error(" token = {}", token);
    return super.preHandle(request, response, handler);
  }
}
