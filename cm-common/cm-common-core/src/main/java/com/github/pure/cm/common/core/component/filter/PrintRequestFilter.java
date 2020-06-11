package com.github.pure.cm.common.core.component.filter;

import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.JsonUtil;
import com.github.pure.cm.common.core.util.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
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
 * 打印请求URL、参数、方法
 *
 * @author 陈欢
 * @since 2020/6/11
 */
@Slf4j
@Component
public class PrintRequestFilter implements Filter, WebFilter {

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

    /**
     * webflux 打印请求：请求方法、URL、参数
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        MediaType contentType = request.getHeaders().getContentType();
        String uri = request.getURI().getPath();

        Map<String, Object> queryParam = new HashMap<>();
        request.getQueryParams().forEach((key, value) -> {
            if (CollectionUtil.isNotEmpty(value) && value.size() == 1) {
                queryParam.put(key, value.get(0));
            } else {
                queryParam.put(key, value);
            }
        });

        //请求的 contentType = MediaType.APPLICATION_FORM_URLENCODED ，不能去读取请求体 [request.getBody()]
        if (MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(contentType)) {
            exchange.getFormData().flatMap(map -> {
                log.debug("请求method:{},请求url:{}\nURL参数:{}\n表单参数:{}", request.getMethod(), uri, JsonUtil.json(queryParam), map);
                return Mono.just(new Result<Map<String, Object>>());
            }).subscribe();
            return chain.filter(exchange);

        } else {
            return DataBufferUtils
                    .join(request.getBody())
                    .flatMap(dataBuffer -> {
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);

                        if (MediaType.APPLICATION_JSON.isCompatibleWith(contentType)) {
                            log.debug("请求method:{},请求url:{}\nURL参数:{}\n请求体:{}", request.getMethod(), uri, JsonUtil.json(queryParam), new String(bytes));
                        } else {
                            log.debug("请求method:{},请求url:{}\nURL参数:{}", request.getMethod(), uri, JsonUtil.json(queryParam));
                        }
                        Flux<DataBuffer> bufferFlux = Flux.defer(() -> Flux.just(response.bufferFactory().wrap(bytes)));
                        ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                            @Override
                            @NonNull
                            public Flux<DataBuffer> getBody() {
                                return bufferFlux;
                            }
                        };
                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
                    });
        }
    }
}
