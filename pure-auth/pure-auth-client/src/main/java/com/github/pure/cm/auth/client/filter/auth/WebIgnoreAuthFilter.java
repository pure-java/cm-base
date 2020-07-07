package com.github.pure.cm.auth.client.filter.auth;

import com.github.pure.cm.auth.client.service.AuthService;
import com.github.pure.cm.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author 陈欢
 * @since 2020/6/10
 */
@Slf4j
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebIgnoreAuthFilter extends IgnoreAuthComponent implements Filter {
    @Autowired
    private AuthService authService;

    /**
     * spring mvc 拦截器
     *
     * @param request         请求
     * @param servletResponse 响应
     * @param filterChain     调用链
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        if (super.isIgnoreAuth(servletRequest.getRequestURI())) {
            filterChain.doFilter(request, servletResponse);

        } else if (authService.checkToken(servletRequest)) {
            filterChain.doFilter(request, servletResponse);

        } else {
            ServletOutputStream outputStream = servletResponse.getOutputStream();
            servletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            outputStream.write(JsonUtil.json(unauthorized()).getBytes());
            outputStream.flush();
        }
    }
}
