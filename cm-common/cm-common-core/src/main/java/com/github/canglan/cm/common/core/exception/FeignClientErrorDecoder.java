package com.github.canglan.cm.common.core.exception;

import com.github.canglan.cm.common.core.model.Result;
import com.github.canglan.cm.common.core.util.JacksonUtil;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * feign 默认不保留错误信息，将默认改为保留默认错误信息，并且在开启熔断情况下，自动进入熔断，相当于 @{@link KeepErrMsgConfiguration}
 *
 * @author bairitan
 * @date 2019/12/24
 */
@Slf4j
@Component
public class FeignClientErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {
    if (response.status() != HttpStatus.OK.value()) {
      String errorContent;
      try {
        errorContent = Util.toString(response.body().asReader());
        Result<String> result = JacksonUtil.singleInstance().jsonToObject(errorContent, Result.class);
        return new InternalApiException(result.getCode(), result.getMessage());
      } catch (IOException e) {
        log.error("handle error exception");
        return new InternalApiException("unknown error");
      }
    }
    return new InternalApiException("unknown error");
  }
}
