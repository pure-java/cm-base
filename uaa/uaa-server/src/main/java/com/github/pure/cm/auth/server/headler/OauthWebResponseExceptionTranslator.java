package com.github.pure.cm.auth.server.headler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.pure.cm.base.web.exception.ExceptionHandlerUtil;
import com.github.pure.cm.common.core.constants.DefExceptionCode;
import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
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

        return new ResponseEntity<>(
                new OauthException(JsonUtil.json(Result.fail(DefExceptionCode.AUTH_FAIL_10001)), body),
                translate.getHeaders(),
                HttpStatus.UNAUTHORIZED
        );
    }
}

/**
 * oauth 验证异常信息转换器；<br>
 * 使用  JsonSerialize 注解指定转换器类
 *
 * @author bairitan
 * @date 2020/6/21
 */
@com.fasterxml.jackson.databind.annotation.JsonSerialize(using = OauthException.OauthExceptionJackson2Serializer.class)
class OauthException extends OAuth2Exception {

    public OauthException(String msg, Throwable t) {
        super(msg, t);
    }

    public static class OauthExceptionJackson2Serializer extends StdSerializer<OauthException> {

        public OauthExceptionJackson2Serializer() {
            super(OauthException.class);
        }

        @Override
        public void serialize(OauthException value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeStartObject();
            Result<?> result = JsonUtil.singleInstance().jsonToObject(value.getMessage(), Result.class);
            jgen.writeStringField("message", result.getMessage());
            jgen.writeNumberField("code", result.getCode());
            jgen.writeBooleanField("status", result.getStatus());
            jgen.writeStringField("data", null);
            jgen.writeEndObject();
        }
    }
}
