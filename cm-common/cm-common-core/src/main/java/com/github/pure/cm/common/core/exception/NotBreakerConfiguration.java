package com.github.pure.cm.common.core.exception;

import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.JacksonUtil;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

/**
 * 不进入熔断，直接抛出异常
 *
 * @author bairitan
 * @date 2019/12/24
 */
public class NotBreakerConfiguration {

  @Bean
  public ErrorDecoder errorDecoder() {
    return new UserErrorDecoder();
  }

  /**
   * 自定义错误
   */
  public class UserErrorDecoder implements ErrorDecoder {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Exception decode(String methodKey, Response response) {
      Exception exception = null;
      try {
        String json = Util.toString(response.body().asReader());
        Result result = JacksonUtil.singleInstance().jsonToObject(json, Result.class);
        // 业务异常包装成 HystrixBadRequestException，不进入熔断逻辑
        exception = new HystrixBadRequestException(result.getMessage());
      } catch (IOException ex) {
        logger.error(ex.getMessage(), ex);
      }
      return exception;
    }
  }
}
