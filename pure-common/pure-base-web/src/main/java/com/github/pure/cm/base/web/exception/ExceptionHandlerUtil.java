package com.github.pure.cm.base.web.exception;

import com.github.pure.cm.common.core.constants.AuthErrorCode;
import com.github.pure.cm.common.core.constants.DefaultErrorCode;
import com.github.pure.cm.common.core.exception.BusinessException;
import com.github.pure.cm.common.core.model.ExceptionResult;
import com.github.pure.cm.common.core.util.ArrayUtil;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.server.ResponseStatusException;

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
public class ExceptionHandlerUtil {

    /**
     * 错误码不能为200
     */
    public static ExceptionResult<String> exceptionHandler(Throwable error) {
        Throwable cause = error.getCause();
        ExceptionResult<String> result = new ExceptionResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), false);

        if (instanceOf(BusinessException.class, error, cause)) {
            // 业务异常
            BusinessException businessException = (BusinessException) (error instanceof BusinessException ? error : cause);
            result.setCode(businessException.getCode());
            result.setMessage(businessException.getMessage());

        } else if (instanceOf(MissingServletRequestParameterException.class, error, cause)) {
            //  缺少必要请求参数
            result.error(DefaultErrorCode.PARAM_VALID_ERROR_10501);

        } else if (instanceOf(BindException.class, error, cause)) {
            //处理请求中 使用 @Valid 验证路径中请求实体校验失败后抛出的异常，详情继续往下看代码
            String message = ((BindException) (error instanceof BindException ? error : cause))
                    .getBindingResult()
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
            result.setMessage(message);

        } else if (instanceOf(ConstraintViolationException.class, error, cause)) {
            //处理请求参数格式错误 @RequestParam 上 validate 失败后抛出的异常是 javax.validation.ConstraintViolationException
            ConstraintViolationException violationException = (ConstraintViolationException) (error instanceof ConstraintViolationException ? error : cause);
            String message = violationException.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
            result.setMessage(message);

        } else if (instanceOf(MethodArgumentNotValidException.class, error, cause)) {
            //处理请求参数格式错误 @RequestBody 上validate失败后抛出的异常是 MethodArgumentNotValidException 异常。
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) (error instanceof MethodArgumentNotValidException ? error : cause);
            String message = validException.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
            result.setMessage(message);

        } else if (instanceOf(ResponseStatusException.class, error, cause)) {
            //  响应状态异常
            ResponseStatusException statusException = (ResponseStatusException) (error instanceof ResponseStatusException ? error : cause);
            result.setMessage(statusException.getMessage());
            result.setCode(statusException.getStatus().value());

        } else if (instanceOf(HttpRequestMethodNotSupportedException.class, error, cause)) {
            // 请求 http method 类型错误 错误
            result.setMessage("该请求方法类型错误");

        } else if (instanceOf(HttpMediaTypeNotSupportedException.class, error, cause) || instanceOf(HttpRequestMethodNotSupportedException.class, error, cause)) {
            // 请求类型不正确
            result.setMessage("请求参数Content-Type不正确");

        } else if (instanceOf(HttpMessageNotReadableException.class, error, cause)) {
            // 请求确少参数 json 类型参数
            result.setMessage("没有请求体");

        } else if (isPresent("org.springframework.security.access.AccessDeniedException")
                && instanceOf(AccessDeniedException.class, error, cause)) {
            // 访问权限异常
            result.setMessage("该请求被拒绝");

            // security 身份和权限验证异常
        } else if (isPresent("org.springframework.security.core.AuthenticationException")
                && instanceOf(org.springframework.security.core.AuthenticationException.class, error, cause)) {
            authException(error, result);

            //   身份认证 oauth2 异常
        } else if (isPresent("org.springframework.security.oauth2.common.exceptions.OAuth2Exception") && instanceOf(OAuth2Exception.class, error, cause)) {
            oauth2Exception(error, result);

        } else if (instanceOf(FeignException.class, error, cause)) {
            //  feign 异常
            FeignException statusException = (FeignException) (error instanceof FeignException ? error : cause);
            result.setMessage(statusException.getMessage());

        } else {
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.setMessage("该请求发生异常，请联系开发人员处理");
        }

        // 响应码为200或为空，设置为 500 错误码
        if (Objects.isNull(result.getCode()) || Objects.equals(200, result.getCode())) {
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return result;
    }

    /**
     * Security 相关异常处理
     */
    private static void authException(Throwable error, ExceptionResult<String> result) {
        Throwable cause = error.getCause();

        if (isPresent("org.springframework.security.authentication.InsufficientAuthenticationException") && instanceOf(InsufficientAuthenticationException.class, error, cause)) {
            result.error(AuthErrorCode.AUTH_FAIL_10001);

        } else if (isPresent("org.springframework.security.core.userdetails.UsernameNotFoundException") && instanceOf(UsernameNotFoundException.class, error, cause)) {
            result.error(AuthErrorCode.ACCOUNT_PASSWD_ERROR_10002);

        } else if (isPresent("org.springframework.security.authentication.BadCredentialsException") && instanceOf(BadCredentialsException.class, error, cause)) {
            result.error(AuthErrorCode.ACCOUNT_PASSWD_ERROR_10002);
        }
    }

    /**
     * oauth2 相关异常处理
     */
    private static void oauth2Exception(Throwable error, ExceptionResult<String> result) {
        // 验证token 异常
        boolean invalidTokenException = isPresent("org.springframework.security.oauth2.common.exceptions.InvalidTokenException");
        // 准许异常
        boolean invalidGrantException = isPresent("org.springframework.security.oauth2.common.exceptions.InvalidGrantException");
        Throwable cause = error.getCause();
        // InvalidTokenException 无效的token
        if (invalidTokenException && instanceOf(InvalidTokenException.class, error, cause)) {
            result.error(AuthErrorCode.TOKEN_INVALID_10003);

        } else if (invalidGrantException && instanceOf(InvalidGrantException.class, error, cause)) {
            result.error(AuthErrorCode.ACCOUNT_PASSWD_ERROR_10002);
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
    private static <T extends Throwable> boolean instanceOf(Class<T> tClass, Throwable... throwables) {
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

    /**
     * 判断类是否在classpath下
     *
     * @param className 类的全限定名
     * @return 是否存在与 classpath
     */
    private static boolean isPresent(String className) {
        return ClassUtils.isPresent(className, ExceptionHandlerUtil.class.getClassLoader());
    }

}
