package com.github.pure.cm.auth.sdk.helper;

import com.github.pure.cm.auth.sdk.service.AuthService;
import com.github.pure.cm.common.core.util.SpringHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.jwt.Jwt;
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

    public static HttpRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        return requestAttributes instanceof ServletRequestAttributes ? new ServletServerHttpRequest(((ServletRequestAttributes) requestAttributes).getRequest()) : (HttpRequest) requestAttributes;
    }

    public static Jwt jwt() {
        String authorization = getCurrentRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        AuthService bean = SpringHelper.getBean(AuthService.class);
        return bean.decodeAndVerify(authorization);
    }

}
