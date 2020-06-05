package com.github.pure.cm.common.core.exception;

import com.github.pure.cm.common.core.model.Result;
import org.springframework.http.HttpStatus;

/**
 * 业务异常
 *
 * @author 陈欢
 * @since 2020/6/5
 */
public class BusinessException extends Exception {

    protected Integer code;


    public BusinessException() {
        this.code  = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public BusinessException(Integer code) {
        this.code = code;
    }

    public BusinessException(Integer code,String message) {
        super(message);
        this.code = code;
    }
    public BusinessException(String message) {
        super(message);
        this.code  = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public BusinessException(Integer code,String message,  Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BusinessException(Result result){
        this(result.getCode(),result.getMessage());
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException( Integer code, String message,Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public BusinessException setCode(Integer code) {
        this.code = code;
        return this;
    }
}
