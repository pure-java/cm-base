package com.github.pure.cm.auth.client.filter.auth;

import com.github.pure.cm.auth.client.service.AuthService;
import com.github.pure.cm.common.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * webflux 拦截器<br>
 * 验证请求是否需要身份验证，以及身份鉴定
 *
 * @author 陈欢
 * @since 2020/6/10
 */
@Slf4j
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class WebFluxIgnoreAuthFilter extends IgnoreAuthFilter implements WebFilter {
    @Autowired
    private AuthService authService;

    /**
     * webflux 过虑器
     *
     * @param exchange 请求响应
     * @param chain    过滤器链
     */
    @Override
    @NonNull
    public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        // 身份认证通过
        ServerHttpRequest request = exchange.getRequest();
        // 忽略身份鉴定，以及身份鉴定成功
        if (super.isIgnoreAuth(request.getURI().getPath())) {
            return chain.filter(exchange);
        } else if (authService.checkToken(request)) {
            return chain.filter(exchange);
        } else {
            //  身份认证失败
            ServerHttpResponse response = exchange.getResponse();
            return response.writeWith(Mono.just(response.bufferFactory().wrap(JsonUtil.json(unauthorized()).getBytes())));
        }
    }
}
