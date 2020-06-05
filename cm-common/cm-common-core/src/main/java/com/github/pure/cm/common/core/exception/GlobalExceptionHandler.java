package com.github.pure.cm.common.core.exception;

import com.github.pure.cm.common.core.model.ExceptionResult;
import com.github.pure.cm.common.core.model.Result;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.github.pure.cm.common.core.util.ArrayUtil;
import com.github.pure.cm.common.core.util.JacksonUtil;
import com.github.pure.cm.common.core.util.collection.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author bairitan
 * @date 2019/12/24
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @Resource
    MessageSource messageSource;

    /**
     * 程序异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ExceptionResult<String> processDefaultException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        String requestURI = request.getRequestURI();
        log.error("请求路径：{}", requestURI);

        Map<String, Object> param = MapUtil.newHashMap();
        request.getParameterMap().forEach((key, value) -> {
            if (ArrayUtil.isEmpty(value)) {
                param.put(key, null);
            } else if (value.length == 1) {
                param.put(key, value[0]);
            } else {
                param.put(key, Arrays.asList(value));
            }
        });

        log.error("请求参数：{}", JacksonUtil.json(param));
        log.error("错误信息", exception);

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json;charset=UTF-8");
        ExceptionResult<String> result = new ExceptionResult<>();

        result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.setMessage(
                messageSource.getMessage(HttpStatus.INTERNAL_SERVER_ERROR.value() + "", null, LocaleContextHolder.getLocale())
        );
        result.setStatus(false);
        if (exception instanceof BusinessException) {
            // 业务异常
            BusinessException businessException = (BusinessException) exception;
            result.setCode(businessException.getCode());
            result.setMessage(businessException.getMessage());

        } else if (exception instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException missingException = (MissingServletRequestParameterException) exception;
            result.setMessage("请求缺少必要参数: " + missingException.getParameterName());

        } else if (exception instanceof BindException) {
            //处理请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常，详情继续往下看代码
            BindException bindException = (BindException) exception;
            String message = bindException.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
            result.setMessage(message);

        } else if (exception instanceof ApiException) {
            ApiException apiException = (ApiException) exception;
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.setCode(apiException.getCode());
            String message = messageSource.getMessage(apiException.getCode() + "", null, LocaleContextHolder.getLocale());
            result.setMessage(message);

        } else if (exception instanceof ConstraintViolationException) {
            //处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是 javax.validation.ConstraintViolationException
            ConstraintViolationException violationException = (ConstraintViolationException) exception;
            String message = violationException.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
            result.setMessage(message);

        } else if (exception instanceof MethodArgumentNotValidException) {
            //处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
            MethodArgumentNotValidException argumentNotValidException = (MethodArgumentNotValidException) exception;
            String message = argumentNotValidException.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
            result.setMessage(message);
        } else {
            exception.printStackTrace();
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.setMessage("发生未知异常");
        }

        return result;
    }

}
