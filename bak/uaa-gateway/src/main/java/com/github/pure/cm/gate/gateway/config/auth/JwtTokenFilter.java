package com.github.pure.cm.gate.gateway.config.auth;

import com.github.pure.cm.gate.gateway.config.auth.impl.WebFluxIgnoreAuthFilter;
import com.github.pure.cm.gate.gateway.config.auth.impl.WebIgnoreAuthFilter;
import com.github.pure.cm.gate.gateway.service.AuthService;
import com.github.pure.cm.gate.gateway.properties.AuthProperties;

import java.util.Map;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.jwt.Jwt;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 网关 过滤器，在 {@link WebFluxIgnoreAuthFilter} 或 {@link WebIgnoreAuthFilter}后面执行
 *
 * @author bairitan
 * @date 2019/12/4
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class JwtTokenFilter implements GlobalFilter {

    private AuthService authService;
    private AuthProperties authProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authentication = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String url = request.getURI().getPath();
        if (StringUtils.isNotBlank(url) && authProperties.ignoreAuthentication(url)) {
            return chain.filter(exchange);
        }
        if (StringUtils.isNotBlank(authentication)) {
            Jwt jwt = authService.decodeAndVerify(authentication);
            log.debug("jwt = {}", jwt);

            Map<String, Object> checkToken = authService.checkToken(authentication);

            if (Objects.nonNull(checkToken)) {
                ServerHttpRequest.Builder builder = request.mutate();
                return chain.filter(exchange.mutate().request(builder.build()).build());
            }
        } else {
            log.debug("认证失败:{}", url);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            DataBuffer buffer = exchange.getResponse().bufferFactory()
                    .wrap(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.getReasonPhrase().getBytes());
            return exchange.getResponse().writeWith(Flux.just(buffer));
        }

        return Mono.empty();
    }
}
