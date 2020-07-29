package com.github.pure.cm.auth.sdk.helper;

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
public class RequestHelper {

    public static HttpRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        return requestAttributes instanceof ServletRequestAttributes ? new ServletServerHttpRequest(((ServletRequestAttributes) requestAttributes).getRequest()) : (HttpRequest) requestAttributes;
    }
}
