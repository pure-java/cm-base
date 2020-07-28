package com.github.pure.cm.gate.gateway.config.filter;

import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.BeanUtils;
import com.github.pure.cm.common.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>全局过滤器</p>
 *
 * @author : 陈欢
 * @date : 2020-07-25 11:15
 **/
@Slf4j
@Component
public class GlobalFilter extends AbstractErrorWebExceptionHandler {

    public GlobalFilter(
            ResourceProperties resourceProperties, ObjectProvider<ViewResolver> viewResolvers,
            ServerCodecConfigurer serverCodecConfigurer, ApplicationContext applicationContext) {
        super(new DefaultErrorAttributes(true), resourceProperties, applicationContext);
        super.setMessageReaders(serverCodecConfigurer.getReaders());
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setViewResolvers(viewResolvers.orderedStream().collect(Collectors.toList()));
    }

    /**
     * 获取异常信息
     */
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        HttpStatus httpStatus;
        String body;
        Throwable ex = super.getError(request);

        Result<Boolean> result = new Result<>();
        if (ex instanceof NotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
            body = "Service Not Found";
        } else if (ex instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            httpStatus = responseStatusException.getStatus();
            body = responseStatusException.getMessage();
        }/* else if (ex instanceof BusinessException) {

        }*/ else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            body = "Internal Server Error";
        }
        result.setCode(httpStatus.value()).setMessage(body).setStatus(false).setData(null);
        return BeanUtils.beanToMap(result);
    }

    /**
     * 打印错误信息
     */
    @Override
    protected void logError(ServerRequest request, ServerResponse response, Throwable throwable) {
        log.error("异常信息：{}", throwable.getMessage());
        if (log.isDebugEnabled()) {
            throwable.printStackTrace();
        }
    }

    /**
     * 设置返回值为json格式
     *
     * @param errorAttributes 异常属性信息
     * @return 响应
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(final ErrorAttributes errorAttributes) {

        return RouterFunctions.route(RequestPredicates.all(),
                (request) -> {
                    Map<String, Object> attributes = getErrorAttributes(request, true);
                    return ServerResponse
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(attributes));
                }
        );
    }
}
