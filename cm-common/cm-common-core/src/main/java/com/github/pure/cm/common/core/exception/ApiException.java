package com.github.pure.cm.common.core.exception;

import com.github.pure.cm.common.core.model.Result;
import org.springframework.http.HttpStatus;

/**
 * api 的异常
 * @author bairitan
 * @date 2019/12/24
 */
public class ApiException extends Exception {

  protected Integer code;

  public ApiException() {
    this.code  = HttpStatus.INTERNAL_SERVER_ERROR.value();
  }

  public ApiException(Integer code) {
    this.code = code;
  }

  public ApiException(Integer code,String message) {
    super(message);
    this.code = code;
  }
  public ApiException(String message) {
    super(message);
    this.code  = HttpStatus.INTERNAL_SERVER_ERROR.value();
  }

  public ApiException(Integer code,String message,  Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public ApiException(Result result){
    this(result.getCode(),result.getMessage());
  }

  public ApiException(Throwable cause) {
    super(cause);
  }

  public ApiException( Integer code, String message,Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
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
