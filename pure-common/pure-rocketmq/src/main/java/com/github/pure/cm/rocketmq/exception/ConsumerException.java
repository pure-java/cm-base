package com.github.pure.cm.rocketmq.exception;

import com.github.pure.cm.common.core.constants.ExceptionCode;
import com.github.pure.cm.common.core.exception.BusinessException;
import com.github.pure.cm.common.core.model.Result;

/**
 * <p>
 *
 *
 * </p>
 *
 * @author : 陈欢
 * @date : 2020-08-11 09:45
 **/
public class ConsumerException extends BusinessException {
    public ConsumerException() {
    }

    public ConsumerException(Integer code) {
        super(code);
    }

    public ConsumerException(ExceptionCode code) {
        super(code);
    }

    public ConsumerException(Integer code, String message) {
        super(code, message);
    }

    public ConsumerException(String message) {
        super(message);
    }

    public ConsumerException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public ConsumerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConsumerException(Throwable cause) {
        super(cause);
    }

    public ConsumerException(Result result) {
        super(result);
    }

    public ConsumerException(Integer code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(code, message, cause, enableSuppression, writableStackTrace);
    }
}
