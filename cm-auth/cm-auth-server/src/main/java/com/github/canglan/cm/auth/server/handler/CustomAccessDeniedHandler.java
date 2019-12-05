package com.github.canglan.cm.auth.server.handler;

import com.github.canglan.cm.common.core.model.Result;
import com.github.canglan.cm.common.core.util.JacksonUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @author 陈欢
 * @since 2019/11/30
 */
@Configuration
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
      throws IOException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    response.getWriter().write(JacksonUtil.json(Result.fail("未授权")));
  }
}
