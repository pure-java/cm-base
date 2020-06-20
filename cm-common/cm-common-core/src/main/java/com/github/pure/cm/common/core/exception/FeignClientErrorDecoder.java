package com.github.pure.cm.common.core.exception;

import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.JsonUtil;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * feign 默认不保留错误信息，将默认改为保留默认错误信息，并且在开启熔断情况下，自动进入熔断
 *
 * @author bairitan
 * @date 2019/12/24
 */
@Slf4j
// @Component
public class FeignClientErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {
    if (response.status() != HttpStatus.OK.value()) {
      try {
        String errorContent = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
        return new ApiException(JsonUtil.singleInstance().jsonToObject(errorContent, Result.class));
      } catch (IOException e) {
        log.error("handle error exception");
        return new ApiException("unknown error");
      }
    }
    return new ApiException("unknown error");
  }
}
