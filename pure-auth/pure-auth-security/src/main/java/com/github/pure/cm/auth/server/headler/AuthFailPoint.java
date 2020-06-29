package com.github.pure.cm.auth.server.headler;

import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.JsonUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        Result<String> fail = Result.fail();
        if (authException instanceof InsufficientAuthenticationException) {
            fail.setMessage("请进行身份验证");
        } else if (authException instanceof UsernameNotFoundException || authException instanceof BadCredentialsException) {
            fail.setMessage("账号密码不正确");
        } else {
            fail = Result.fail(authException.getMessage());
        }
        fail.setCode(response.getStatus());
        response.getWriter().write(JsonUtil.json(fail));
        authException.printStackTrace();
    }
}
