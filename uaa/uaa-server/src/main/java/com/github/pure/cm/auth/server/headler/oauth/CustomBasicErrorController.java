package com.github.pure.cm.auth.server.headler.oauth;

import com.github.pure.cm.common.core.constants.DefExceptionCode;
import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * spring security的错误处理：
 * ExceptionTranslationFilter 将会处理 basic 方式认证的错误，并将错误信息转发到该 mapper。
 * <p>
 * 如果spring security 使用 form 方式认证，现版本无法进行自定义异常处理
 *
 * </p>
 *
 * @author : 陈欢
 * @date : 2020-07-27 16:24
 **/

@Slf4j
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomBasicErrorController extends BasicErrorController {

    private final ErrorProperties errorProperties;

    public CustomBasicErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties,
                                      List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
        this.errorProperties = errorProperties;
    }

    @RequestMapping
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);

        if (status == HttpStatus.NO_CONTENT) {
            String resultJson = JsonUtil.json(Result.fail(DefExceptionCode.UNAUTHORIZED_401));
            return new ResponseEntity<>(JsonUtil.newIns().jsonToMap(resultJson), status);
        }

        Map<String, Object> body = this.getErrorAttributes(request, false);
        log.error(">>>>>body error:{}", body);
        String resultJson = JsonUtil.json(Result.fail(DefExceptionCode.UNAUTHORIZED_401));
        Map<String, Object> resultJsonMap = JsonUtil.newIns().jsonToMap(resultJson);

        return new ResponseEntity<>(resultJsonMap, status);
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
