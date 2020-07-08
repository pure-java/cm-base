package com.github.pure.cm.base.web.component.filter;

import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * spring web flux 打印请求相关信息：请求方法、URL、参数等信息
 *
 * @author 陈欢
 * @since 2020/6/18
 */
@Slf4j
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class WebFluxPrintRequestFilter implements WebFilter {

    public WebFluxPrintRequestFilter() {
        log.debug("加载 {}------------", WebFluxPrintRequestFilter.class.getSimpleName());
    }

    /**
     * webflux 打印请求：请求方法、URL、参数
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        MediaType contentType = request.getHeaders().getContentType();
        String uri = request.getURI().getPath();

        Map<String, Object> queryParam = new HashMap<>();
        request.getQueryParams().forEach((key, value) -> {
            if (CollectionUtils.isNotEmpty(value) && value.size() == 1) {
                queryParam.put(key, value.get(0));
            } else {
                queryParam.put(key, value);
            }
        });
        // 分开打印请求目的是让内容在一起，不会被其他日志穿插

        //请求的 contentType = MediaType.APPLICATION_FORM_URLENCODED ，MULTIPART_FORM_DATA 不能去读取请求体 [request.getBody()]
        if (MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(contentType) || MediaType.MULTIPART_FORM_DATA.isCompatibleWith(contentType)) {
            exchange.getFormData().flatMap(map -> {
                log.debug("请求method:{},请求url:{}\nURL参数:{}\n表单参数:{}", request.getMethod(), uri, JsonUtil.json(queryParam), map);
                return Mono.just(new Result<Map<String, Object>>());
            }).subscribe();
            return chain.filter(exchange);

            //   json
        } else if (MediaType.APPLICATION_JSON.isCompatibleWith(contentType)) {
            return request.getBody().flatMap(dataBuffer -> {
                byte[] bytes = new byte[dataBuffer.readableByteCount()];
                dataBuffer.read(bytes);
                log.debug("请求method:{},请求url:{}\nURL参数:{}\n请求体:{}", request.getMethod(), uri, JsonUtil.json(queryParam), new String(bytes));
                Flux<DataBuffer> bufferFlux = Flux.defer(() -> Flux.just(response.bufferFactory().wrap(bytes)));
                ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                    @Override
                    @NonNull
                    public Flux<DataBuffer> getBody() {
                        return bufferFlux;
                    }
                };
                return chain.filter(exchange.mutate().request(mutatedRequest).build());
            }).then(chain.filter(exchange.mutate().request(request).build()));
        } else {
            log.debug("请求method:{},请求url:{}\nURL参数:{}", request.getMethod(), uri, JsonUtil.json(queryParam));
            return chain.filter(exchange.mutate().request(request).build());
        }
    }
}
