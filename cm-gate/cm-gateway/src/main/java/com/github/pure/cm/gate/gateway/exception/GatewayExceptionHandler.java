package com.github.pure.cm.gate.gateway.exception;

import com.github.pure.cm.common.core.exception.ExceptionUtil;
import com.github.pure.cm.common.core.model.ExceptionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bairitan
 * @date 2019/12/24
 */
@Slf4j
public class GatewayExceptionHandler extends DefaultErrorWebExceptionHandler {

    public GatewayExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
                                   ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    /**
     * 获取异常属性
     */
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        return response(super.getError(request));
    }

    /**
     * 指定响应处理方法为JSON处理的方法
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     * 根据code获取对应的HttpStatus
     */
    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        return (int) errorAttributes.get("code");
    }

    @Override
    protected void logError(ServerRequest request, ServerResponse response, Throwable throwable) {
        if (log.isDebugEnabled()) {
            log.error("错误信息{}", throwable.getMessage(),throwable);
        } else {
            log.error("堆栈信息{}", throwable.getMessage());
        }
    }

    /**
     * 构建返回的JSON数据格式
     *
     * @param ex 异常信息
     */
    public static Map<String, Object> response(Throwable ex) {
        ExceptionResult<String> result = ExceptionUtil.exceptionHandler(ex, false);
        Map<String, Object> map = new HashMap<>();
        map.put("code", result.getCode());
        map.put("message", result.getMessage());
        map.put("status", result.getStatus());
        map.put("data", null);
        if (ex instanceof org.springframework.cloud.gateway.support.NotFoundException) {
            map.put("code", HttpStatus.NOT_FOUND);
        }
        return map;
    }

}