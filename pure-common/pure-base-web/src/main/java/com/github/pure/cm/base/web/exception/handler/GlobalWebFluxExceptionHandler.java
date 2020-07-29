package com.github.pure.cm.base.web.exception.handler;

import com.github.pure.cm.common.core.util.BeanUtils;
import com.github.pure.cm.common.core.util.collection.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * web flux 自定义异常处理器，返回json格式的异常信息
 *
 * @author 陈欢
 * @since 2020/6/18
 */
@Slf4j
@Order(-2)
@Component
@ConditionalOnMissingBean(AbstractErrorWebExceptionHandler.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class GlobalWebFluxExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalWebFluxExceptionHandler(
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
        return BeanUtils.beanToMap(ExceptionHandlerUtil.exceptionHandler(super.getError(request)));
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
                    Integer code = MapUtils.getInteger(attributes, "code", 500);
                    return ServerResponse
                            .status(code <= 500 ? code : HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(attributes));
                }
        );
    }
}
