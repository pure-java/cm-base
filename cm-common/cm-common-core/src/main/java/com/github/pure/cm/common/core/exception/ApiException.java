package com.github.pure.cm.common.core.exception;

import org.springframework.http.HttpStatus;

/**
 * @author bairitan
 * @date 2019/12/24
 */
public class ApiException extends Exception {

  private Integer code;

  public ApiException() {
    this.code  = HttpStatus.INTERNAL_SERVER_ERROR.value();
  }

  public ApiException(Integer code) {
    this.code = code;
  }

  public ApiException(String message, Integer code) {
    super(message);
    this.code = code;
  }
  public ApiException(String message) {
    super(message);
    this.code  = HttpStatus.INTERNAL_SERVER_ERROR.value();
  }

  public ApiException(String message, Integer code, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public ApiException(Throwable cause) {
    super(cause);
  }

  public ApiException(String message, Integer code, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.code = code;
  }

  public Integer getCode() {
    return code;
  }

  public ApiException setCode(Integer code) {
    this.code = code;
    return this;
  }
}
