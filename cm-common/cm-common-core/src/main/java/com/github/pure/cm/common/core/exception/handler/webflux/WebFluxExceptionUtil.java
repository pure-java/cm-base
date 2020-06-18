package com.github.pure.cm.common.core.exception.handler.webflux;

import com.github.pure.cm.common.core.exception.ApiException;
import com.github.pure.cm.common.core.exception.BusinessException;
import com.github.pure.cm.common.core.model.ExceptionResult;
import com.github.pure.cm.common.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 异常处理器：对具体异常进行细分，返回提示信息
 *
 * @author 陈欢
 * @since 2020/6/9
 */
@Slf4j
public class WebFluxExceptionUtil {

    public static ExceptionResult<String> exceptionHandler(Throwable error, boolean isPrintException) {
        ExceptionResult<String> result = new ExceptionResult<>();

        result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.setStatus(false);

        Throwable cause = error.getCause();
        // 业务异常
        isException(error, BusinessException.class, (var) -> {
            BusinessException businessException = (BusinessException) var;
            setCode(businessException.getCode(), result);
            result.setMessage(businessException.getMessage());
        });

        // api 异常
        isException(error, ApiException.class, (var) -> {
            ApiException apiException = (ApiException) var;
            setCode(apiException.getCode(), result);
            result.setMessage(apiException.getMessage());
        });

        // 业务异常
        if (error instanceof BusinessException || cause instanceof BusinessException) {
            BusinessException businessException = (BusinessException) (error instanceof BusinessException ? error : cause);
            setCode(businessException.getCode(), result);
            result.setMessage(businessException.getMessage());

            // api 异常
        } else if (error instanceof ApiException || cause instanceof ApiException) {
            ApiException apiException = (ApiException) (error instanceof ApiException ? error : cause);
            setCode(apiException.getCode(), result);
            result.setMessage(apiException.getMessage());

            //  缺少必要请求参数
        } else if (cause instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException missingException = (MissingServletRequestParameterException) cause;
            result.setMessage("请求缺少必要参数: " + missingException.getParameterName());

            //   验证异常
        } else if (cause instanceof BindException) {
            //处理请求中 使用 @Valid 验证路径中请求实体校验失败后抛出的异常，详情继续往下看代码
            BindException bindException = (BindException) cause;
            String message = bindException.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
            result.setMessage(message);

            //    验证异常
        } else if (cause instanceof ConstraintViolationException) {
            //处理请求参数格式错误 @RequestParam 上 validate 失败后抛出的异常是 javax.validation.ConstraintViolationException
            ConstraintViolationException violationException = (ConstraintViolationException) cause;
            String message = violationException.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
            result.setMessage(message);

            //     参数验证异常
        } else if (cause instanceof MethodArgumentNotValidException) {
            //处理请求参数格式错误 @RequestBody 上validate失败后抛出的异常是 MethodArgumentNotValidException 异常。
            MethodArgumentNotValidException argumentNotValidException = (MethodArgumentNotValidException) cause;
            String message = argumentNotValidException.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
            result.setMessage(message);

            //   请求类型异常
        } else if (error instanceof UnsupportedMediaTypeStatusException) {
            result.setMessage("请求头 contentType 错误");

            //  响应状态异常
        } else if (error instanceof ResponseStatusException || cause instanceof ResponseStatusException) {
            ResponseStatusException statusException;
            if (error instanceof ResponseStatusException) {
                statusException = (ResponseStatusException) error;
            } else {
                statusException = (ResponseStatusException) cause;
            }
            result.setMessage(statusException.getMessage());
            setCode(statusException.getStatus().value(), result);

            //  其他异常
        } else {
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.setMessage("该请求发生错误");
        }
        if (isPrintException) {
            log.error("发生异常", cause);
        }
        return result;
    }

    private static <T extends Throwable> T isException(Throwable orgException, Class<T> exceptionClass, Consumer<Throwable> consumer) {
        boolean instance = exceptionClass.isInstance(orgException);
        if (!instance) {
            Throwable cause = orgException.getCause();
            return isException(cause, exceptionClass, consumer);
        }
        consumer.accept(orgException);
        return (T) orgException;
    }


    private static void setCode(Integer code, Result<?> result) {
        if (Objects.nonNull(code)) {
            result.setCode(code);
        }
    }
}
