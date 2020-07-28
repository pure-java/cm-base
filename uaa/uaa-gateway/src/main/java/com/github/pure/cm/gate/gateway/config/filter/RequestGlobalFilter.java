package com.github.pure.cm.gate.gateway.config.filter;

import com.github.pure.cm.common.core.constants.DefExceptionCode;
import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * <p>过虑响应内容：将所有非200的响应码转换为 服务器异常；如果要返回业务异常，使用包装实体</p>
 *
 * @author : 陈欢
 * @date : 2020-07-28 15:31
 **/
@Slf4j
@Component
public class RequestGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        ServerHttpResponseDecorator response = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

                if (body instanceof Flux) {
                    return super.writeWith(Flux.from(body)
                            .map(dataBuffer -> {
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                //释放掉内存
                                DataBufferUtils.release(dataBuffer);
                                int value = Objects.nonNull(getStatusCode()) ? getStatusCode().value() : 500;
                                switch (value) {
                                    case 401: // 没权限
                                        log.error("响应码:{},错误信息:{}", getStatusCode(), new String(content, StandardCharsets.UTF_8));
                                    case 200: // 正常
                                        return bufferFactory.wrap(new String(content, StandardCharsets.UTF_8).getBytes());
                                    default:
                                        log.error("响应码:{},错误信息:{}", getStatusCode(), new String(content, StandardCharsets.UTF_8));
                                        Result<?> result = Result.fail().setCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).setStatus(false).setData(null);
                                        // 设置响应体的长度
                                        String json = JsonUtil.json(result);
                                        originalResponse.getHeaders().setContentLength(json.getBytes().length);
                                        return bufferFactory.wrap(json.getBytes());
                                }
                            }));
                }
                return super.writeWith(body);
            }

            @Override
            public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
                return writeWith(Flux.from(body)
                        .flatMapSequential(p -> p));
            }
        };
        return chain.filter(exchange.mutate().response(response).build());
    }

    @Override
    public int getOrder() {
        return -10;
    }
}
