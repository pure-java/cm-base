package com.github.pure.cm.common.core.component.filter;

import com.github.pure.cm.common.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * spring web 打印请求URL、参数、方法
 *
 * @author 陈欢
 * @since 2020/6/11
 */
@Slf4j
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebPrintRequestFilter implements Filter {


    public WebPrintRequestFilter() {
        log.debug("加载 web {}------------", WebPrintRequestFilter.class.getSimpleName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;

        Map<String, Object> requestParamMap = new HashMap<>();

        request.getParameterMap().forEach((key, value) -> {
            if (value.length == 1) {
                requestParamMap.put(key, value[0]);
            } else {
                requestParamMap.put(key, value);
            }
        });
        log.debug("请求method:{}, 请求url:{}\n请求参数:{}",
                servletRequest.getMethod(),
                servletRequest.getRequestURI(),
                JsonUtil.json(requestParamMap));

        filterChain.doFilter(request, servletResponse);
    }


}
