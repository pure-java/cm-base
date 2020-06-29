package com.github.pure.cm.common.core.exception.handler;

import com.github.pure.cm.common.core.model.ExceptionResult;
import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.JsonUtil;
import com.github.pure.cm.common.core.util.collection.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author bairitan
 * @date 2019/12/24
 */
@Slf4j
@RestControllerAdvice
@RestController
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class GlobalExceptionHandler implements ErrorController {

    public GlobalExceptionHandler() {
        log.debug("加载全局异常处理 {}", GlobalExceptionHandler.class.getSimpleName());
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ExceptionResult<String> handle(HttpServletRequest request, @NonNull Throwable exception) {

        String requestURI = request.getRequestURI() + request.getQueryString();
        Map<String, Object> param = MapUtil.newHashMap();
        request.getParameterMap().forEach((key, value) -> {
            if (ArrayUtils.isNotEmpty(value)) {
                param.put(key, value.length == 1 ? value[0] : ArrayUtils.toString(value));
            }
        });
        log.error("请求方法：{},请求路径：{}\n请求参数：{}", request.getMethod(), requestURI, JsonUtil.json(param), exception);

        return ExceptionHandlerUtil.exceptionHandler(exception);
    }

    /**
     * 指定错误页面
     */
    @Override
    public String getErrorPath() {
        return "error";
    }

    /**
     * 404 异常
     */
    @RequestMapping(value = "/error")
    public Result<String> error() {
        return new Result<String>().setCode(HttpStatus.NOT_FOUND.value()).setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
    }

}
