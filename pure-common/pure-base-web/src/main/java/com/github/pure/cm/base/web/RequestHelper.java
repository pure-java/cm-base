package com.github.pure.cm.base.web;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <p></p>
 *
 * @author : 陈欢
 * @date : 2020-07-29 16:19
 **/
@Slf4j
public class RequestHelper {

    /**
     * 获取http请求
     *
     * @return
     */
    public static HttpRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        return requestAttributes instanceof ServletRequestAttributes ? new ServletServerHttpRequest(((ServletRequestAttributes) requestAttributes).getRequest()) : (HttpRequest) requestAttributes;
    }

    /**
     * 获取请求头
     *
     * @param handlerName
     * @return
     */
    public static String handler(String handlerName) {
        return getCurrentRequest().getHeaders().getFirst(handlerName);
    }

    /**
     * 添加请求头
     *
     * @param handlerName
     * @param value
     */
    public static void addHandler(String handlerName, String value) {
        getCurrentRequest().getHeaders().putIfAbsent(handlerName, Lists.newArrayList()).add(value);
    }
}
