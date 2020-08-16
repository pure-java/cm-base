package com.github.pure.cm.base.web.exception;

import com.github.pure.cm.common.core.constants.DefExceptionCode;
import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.BeanUtils;
import com.github.pure.cm.common.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 统一处理 HttpStatus = 200 外的异常信息。
 * <p>
 * 顺便可以处理部分spring security的错误信息：
 * ExceptionTranslationFilter 将会处理 basic 方式认证的错误，并将错误信息转发到该 mapper。
 * <p>
 * 如果spring security 使用 form 方式认证，现版本无法进行自定义部分异常处理。如oauth2 的client id 找不到时。
 * </p>
 *
 * @author : 陈欢
 * @date : 2020-07-27 16:24
 **/
@Slf4j
@Configuration
@RequestMapping("${server.error.path:${error.path:/error}}")
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class CustomErrorController extends BasicErrorController {

    private final ErrorProperties errorProperties;

    public CustomErrorController(ErrorAttributes errorAttributes,
                                 ServerProperties serverProperties,
                                 ObjectProvider<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, serverProperties.getError(), errorViewResolvers.orderedStream().collect(Collectors.toList()));
        this.errorProperties = serverProperties.getError();
    }

    @RequestMapping
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        Map<String, Object> body = this.getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        log.error(">>>>>body error:{}", body);
        switch (status) {
            case NO_CONTENT:
                return new ResponseEntity<>(status);
            case NOT_FOUND:
                return new ResponseEntity<>(BeanUtils.beanToMap(Result.fail(DefExceptionCode.NOT_FOUND_404)), status);
            case UNAUTHORIZED:
                return new ResponseEntity<>(BeanUtils.beanToMap(Result.fail(DefExceptionCode.UNAUTHORIZED_401)), status);
            default:
                return new ResponseEntity<>(BeanUtils.beanToMap(Result.newIns(status.value(), MapUtils.getString(body, "error"), false)), status);
        }
    }

    @Override
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<String> mediaTypeNotAcceptable(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(JsonUtil.json(Result.fail(DefExceptionCode.PARAM_VALID_ERROR_501)), status);
    }

    /**
     * Provide access to the error properties.
     *
     * @return the error properties
     */
    @Override
    protected ErrorProperties getErrorProperties() {
        return this.errorProperties;
    }
}
