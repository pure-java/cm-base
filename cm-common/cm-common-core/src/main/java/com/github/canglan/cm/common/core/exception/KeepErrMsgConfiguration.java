package com.github.canglan.cm.common.core.exception;

import com.github.canglan.cm.common.core.model.Result;
import com.github.canglan.cm.common.core.util.JacksonUtil;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

/**
 * 保留原始异常信息
 *
 * @author bairitan
 * @date 2019/12/24
 */
@Slf4j
public class KeepErrMsgConfiguration {

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
        // 获取原始的返回内容
        String json = Util.toString(response.body().asReader());
        // 将返回内容反序列化为Result，这里应根据自身项目作修改
        Result result = JacksonUtil.singleInstance().jsonToObject(json, Result.class);
        // 业务异常抛出简单的 InternalApiException，保留原来错误信息
        exception = new InternalApiException(result.getMessage());
      } catch (IOException ex) {
        logger.error(ex.getMessage(), ex);
      }
      return exception;
    }
  }
}
