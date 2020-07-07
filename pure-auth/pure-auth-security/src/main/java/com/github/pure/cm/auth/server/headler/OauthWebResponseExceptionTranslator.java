package com.github.pure.cm.auth.server.headler;

import com.github.pure.cm.common.model.Result;
import com.github.pure.cm.common.util.JsonUtil;
import com.github.pure.cm.common.web.exception.handler.ExceptionHandlerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.web.util.HtmlUtils;

import java.util.Objects;

/**
 * oauth2 异常信息进行转换
 *
 * @author bairitan
 * @date 2020/6/21
 */
@Slf4j
public class OauthWebResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {

    DefaultWebResponseExceptionTranslator exceptionTranslator = new DefaultWebResponseExceptionTranslator();

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {

        ResponseEntity<OAuth2Exception> translate = this.exceptionTranslator.translate(e);
        OAuth2Exception body = translate.getBody();
        String errorMessage = null;

        if (Objects.nonNull(body)) {
            errorMessage = body.getMessage();
            if (errorMessage != null) {
                errorMessage = HtmlUtils.htmlEscape(errorMessage);

                // 统一处理
                if (e instanceof InvalidTokenException) {
                    errorMessage = "无效的token";
                }
            }
        }
        log.error("oauth2 身份验证失败\n{}:", Objects.isNull(body) ? "" : ExceptionHandlerUtil.exceptionHandler(body), e);
        String json = JsonUtil.json(Result.newIns(translate.getStatusCode().value(), errorMessage, null));
        return new ResponseEntity<>(
                new OauthException(json, body),
                translate.getHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}

