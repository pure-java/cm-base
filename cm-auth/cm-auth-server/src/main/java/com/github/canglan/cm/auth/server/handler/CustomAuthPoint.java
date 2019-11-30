package com.github.canglan.cm.auth.server.handler;

import com.github.canglan.cm.common.data.model.Result;
import com.github.canglan.cm.common.data.util.JacksonUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @author 陈欢
 * @since 2019/11/30
 */
@Configuration
public class CustomAuthPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    response.getWriter().write(JacksonUtil.json(Result.fail("token校验失败")));
  }
}
