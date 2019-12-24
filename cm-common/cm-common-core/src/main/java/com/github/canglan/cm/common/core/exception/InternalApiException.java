package com.github.canglan.cm.common.core.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * @author bairitan
 * @date 2019/12/24
 */
@Getter
@Setter
public class InternalApiException extends Exception {

  private Integer code;

  public InternalApiException() {
    this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
  }

  public InternalApiException(String message) {
    super(message);
    this.code  = HttpStatus.INTERNAL_SERVER_ERROR.value();
  }

  public InternalApiException(Integer code, String message) {
    super(message);
    this.code = code;
  }

  public InternalApiException(Integer code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public InternalApiException(Throwable cause) {
    super(cause);
  }

  public InternalApiException(String message, int code, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.code = code;
  }

}
