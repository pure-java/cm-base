package com.github.pure.cm.auth.server.handler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.pure.cm.auth.server.handler.OauthException.OauthExceptionJackson2Serializer;
import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.JsonUtil;
import java.io.IOException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * oauth 验证异常信息转换器；<br>
 * 使用  JsonSerialize 注解指定转换器类
 *
 * @author bairitan
 * @date 2020/6/21
 */
@com.fasterxml.jackson.databind.annotation.JsonSerialize(using = OauthExceptionJackson2Serializer.class)
class OauthException extends OAuth2Exception {

  public OauthException(String msg, Throwable t) {
    super(msg, t);
  }

  static class OauthExceptionJackson2Serializer extends StdSerializer<OauthException> {

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
