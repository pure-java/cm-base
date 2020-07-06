package com.github.pure.cm.auth.server.headler;

import com.github.pure.cm.common.core.exception.handler.ExceptionHandlerUtil;
import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.JsonUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 身份认证失败处理器
 *
 * @author 陈欢
 * @since 2019/11/30
 */
@Configuration
public class AuthFailPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Result<String> fail = ExceptionHandlerUtil.exceptionHandler(authException);

        response.setStatus(fail.getCode());
        response.getWriter().write(JsonUtil.json(fail));
        authException.printStackTrace();
    }
}
