package com.github.pure.cm.base.web.exception.handler;

import com.github.pure.cm.common.core.constants.ExceptionCode;
import com.github.pure.cm.common.core.exception.BusinessException;
import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.JsonUtil;
import feign.RequestTemplate;
import feign.Response;
import feign.Util;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * feign 默认不保留错误信息，将默认改为保留默认错误信息，并且在开启熔断情况下，自动进入熔断
 *
 * @author bairitan
 * @date 2019/12/24
 */
@Slf4j
@Component
public class FeignClientErrorDecoder extends ErrorDecoder.Default{

  @Override
  public Exception decode(String methodKey, Response response) {
    String message = "";
    try {
      // 这里直接拿到我们抛出的异常信息
      message = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
      return new BusinessException(JsonUtil.singleInstance().jsonToObject(message, Result.class));
    } catch (IOException ignored) {
      return new BusinessException(ExceptionCode.SYSTEM_ERROR, message);
    }
  }

}