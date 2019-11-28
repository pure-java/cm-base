package com.github.canglan.cm.auth.server.config;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * 认证失败
 *
 * @author chenhuan
 * @since 2019/11/20
 */
@Component
public class TokenAuthenticationFailureHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException e) throws IOException {
    response.setContentType("application/json;charset=utf-8");
    response.getWriter().write(e.getMessage());
  }
}
