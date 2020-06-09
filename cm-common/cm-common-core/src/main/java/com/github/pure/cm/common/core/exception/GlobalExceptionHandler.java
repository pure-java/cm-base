package com.github.pure.cm.common.core.exception;

import com.github.pure.cm.common.core.model.ExceptionResult;
import com.github.pure.cm.common.core.util.JacksonUtil;
import com.github.pure.cm.common.core.util.collection.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author bairitan
 * @date 2019/12/24
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler implements WebExceptionHandler {

    @Resource
    MessageSource messageSource;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, @NonNull Throwable exception) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String requestURI = request.getURI().getPath();
        log.error("请求方法：{},请求路径：{}", request.getMethod(), requestURI);

        Map<String, Object> param = MapUtil.newHashMap();
        exchange.getFormData().subscribe(map -> map.forEach((key, value) -> log.error("key = {},value = {}", key, value)));

        //request.getQueryParams().forEach((key, value) -> {
        //    if (ArrayUtil.isEmpty(value)) {
        //        param.put(key, null);
        //    } else if (value.length == 1) {
        //        param.put(key, value[0]);
        //    } else {
        //        param.put(key, Arrays.asList(value));
        //    }
        //});

        log.error("请求参数：{}", JacksonUtil.json(param));
        log.error("错误信息", exception);

        ExceptionResult<String> result = ExceptionUtil.exceptionHandler(exception,false);
        return response
                .writeWith(Mono.fromSupplier(() -> {
                    DataBufferFactory bufferFactory = response.bufferFactory();
                    return bufferFactory.wrap(JacksonUtil.json(result).getBytes(StandardCharsets.UTF_8));
                }));
    }
}
