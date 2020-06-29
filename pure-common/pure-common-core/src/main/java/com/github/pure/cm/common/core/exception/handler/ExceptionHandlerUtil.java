package com.github.pure.cm.common.core.exception.handler;

import com.github.pure.cm.common.core.exception.ApiException;
import com.github.pure.cm.common.core.exception.BusinessException;
import com.github.pure.cm.common.core.model.ExceptionResult;
import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 异常处理器：对具体异常进行细分，返回提示信息; <br>
 * 处理异常时，有些异常是经过包装的，需要判断多层的异常信息；
 *
 * @author 陈欢
 * @since 2020/6/9
 */
@Slf4j
class ExceptionHandlerUtil {

    public static ExceptionResult<String> exceptionHandler(Throwable error) {
        ExceptionResult<String> result = new ExceptionResult<>();

        // 设置默认响应信息
        result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.setStatus(false);
        Throwable cause = error.getCause();

        // 业务异常
        if (instanceOf(BusinessException.class, error, cause)) {
            BusinessException businessException = (BusinessException) (error instanceof BusinessException ? error : cause);
            setCode(businessException.getCode(), result);
            result.setMessage(businessException.getMessage());

            // api 异常
        } else if (instanceOf(ApiException.class, error, cause)) {
            ApiException apiException = (ApiException) (error instanceof ApiException ? error : cause);
            setCode(apiException.getCode(), result);
            result.setMessage(apiException.getMessage());

            //  缺少必要请求参数
        } else if (instanceOf(MissingServletRequestParameterException.class, error, cause)) {
            MissingServletRequestParameterException missingException = (MissingServletRequestParameterException) (error instanceof MissingServletRequestParameterException ? error : cause);
            result.setMessage("请求缺少必要参数: " + missingException.getParameterName());

            //处理请求中 使用 @Valid 验证路径中请求实体校验失败后抛出的异常，详情继续往下看代码
        } else if (instanceOf(BindException.class, error, cause)) {
            BindException bindException = (BindException) (error instanceof BindException ? error : cause);
            String message = bindException.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
            result.setMessage(message);

            //处理请求参数格式错误 @RequestParam 上 validate 失败后抛出的异常是 javax.validation.ConstraintViolationException
        } else if (instanceOf(ConstraintViolationException.class, error, cause)) {
            ConstraintViolationException violationException = (ConstraintViolationException) (error instanceof ConstraintViolationException ? error : cause);
            String message = violationException.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
            result.setMessage(message);

            //处理请求参数格式错误 @RequestBody 上validate失败后抛出的异常是 MethodArgumentNotValidException 异常。
        } else if (instanceOf(MethodArgumentNotValidException.class, error, cause)) {
            MethodArgumentNotValidException argumentNotValidException = (MethodArgumentNotValidException) (error instanceof MethodArgumentNotValidException ? error : cause);
            String message = argumentNotValidException.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
            result.setMessage(message);

            //   请求类型异常
        } else if (instanceOf(UnsupportedMediaTypeStatusException.class, error, cause)) {
            result.setMessage("请求头 contentType 错误");

            //  响应状态异常
        } else if (instanceOf(ResponseStatusException.class, error, cause)) {
            ResponseStatusException statusException = (ResponseStatusException) (error instanceof ResponseStatusException ? error : cause);
            result.setMessage(statusException.getMessage());
            setCode(statusException.getStatus().value(), result);

            // 请求 http method 类型错误 错误
        } else if (instanceOf(HttpRequestMethodNotSupportedException.class, error, cause)) {
            result.setMessage("该请求方法类型错误！！！");

            //  其他异常
        } else {
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.setMessage("该请求发生异常");
        }

        return result;
    }

    private static void setCode(Integer code, Result<?> result) {
        if (Objects.nonNull(code)) {
            result.setCode(code);
        }
    }

    /**
     * 判断是不是该类型的异常
     *
     * @param tClass     异常类型
     * @param throwables 异常对象
     * @param <T>        异常
     * @return true：是该类型的异常，false：不是
     */
    static <T extends Throwable> boolean instanceOf(Class<T> tClass, Throwable... throwables) {
        if (ArrayUtil.isEmpty(throwables)) {
            return false;
        }

        for (Throwable throwable : throwables) {
            if (tClass.isInstance(throwable)) {
                return true;
            }
        }
        return false;
    }
}
