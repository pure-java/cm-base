package com.github.pure.cm.auth.server.headler;

import com.github.pure.cm.common.core.constants.AuthErrorCode;
import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.JsonUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 没有权限处理器
 *
 * @author 陈欢
 * @since 2019/11/30
 */
@Configuration
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(AuthErrorCode.UNAUTHORIZED_401.getCode());
        response.getWriter().write(JsonUtil.json(Result.failMsg(accessDeniedException.getMessage())));
    }
}
