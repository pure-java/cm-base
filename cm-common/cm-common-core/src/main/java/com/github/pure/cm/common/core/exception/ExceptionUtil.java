package com.github.pure.cm.common.core.exception;

import com.github.pure.cm.common.core.model.ExceptionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * @author 陈欢
 * @since 2020/6/9
 */
@Slf4j
public class ExceptionUtil {

    public static ExceptionResult<String> exceptionHandler(Throwable error, boolean isPrintException) {
        ExceptionResult<String> result = new ExceptionResult<>();

        result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.setStatus(false);
        Throwable cause = error.getCause();
        if (cause instanceof BusinessException) {
            // 业务异常
            BusinessException businessException = (BusinessException) cause;
            result.setCode(businessException.getCode());
            result.setMessage(businessException.getMessage());

        } else if (cause instanceof ApiException) {
            ApiException apiException = (ApiException) cause;
            result.setCode(apiException.getCode());
            result.setMessage(apiException.getMessage());

        } else if (cause instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException missingException = (MissingServletRequestParameterException) cause;
            result.setMessage("请求缺少必要参数: " + missingException.getParameterName());

        } else if (cause instanceof BindException) {
            //处理请求中 使用 @Valid 验证路径中请求实体校验失败后抛出的异常，详情继续往下看代码
            BindException bindException = (BindException) cause;
            String message = bindException.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
            result.setMessage(message);

        } else if (cause instanceof ConstraintViolationException) {
            //处理请求参数格式错误 @RequestParam 上 validate 失败后抛出的异常是 javax.validation.ConstraintViolationException
            ConstraintViolationException violationException = (ConstraintViolationException) cause;
            String message = violationException.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
            result.setMessage(message);

        } else if (cause instanceof MethodArgumentNotValidException) {
            //处理请求参数格式错误 @RequestBody 上validate失败后抛出的异常是 MethodArgumentNotValidException 异常。
            MethodArgumentNotValidException argumentNotValidException = (MethodArgumentNotValidException) cause;
            String message = argumentNotValidException.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
            result.setMessage(message);
        } else if (error instanceof UnsupportedMediaTypeStatusException) {
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.setMessage("请求头 contentType 错误");
        } else {
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.setMessage("该请求发生错误");
        }
        if (isPrintException) {
            log.error("发生异常", cause);
        }
        return result;
    }
}
