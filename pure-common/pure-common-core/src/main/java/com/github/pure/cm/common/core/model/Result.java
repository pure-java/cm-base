package com.github.pure.cm.common.core.model;

import com.github.pure.cm.common.core.constants.ExceptionCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Objects;

/**
 * 返回操作信息
 *
 * @author admin
 * @since 2017-07-19 15:43
 **/
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 2235085689160152768L;

    /**
     * 状态码: {@link ExceptionCode}
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String message;

    /**
     * 数据
     * 操作成功才应该有数据
     */
    private T data;
    /**
     * 【 操作状态,没有权限、没登陆、操作失败 】 = false，成功 = true
     */
    private Boolean status;

    private Result(Integer code, String message, T data, Boolean status) {
        this.setCode(code);
        this.setMessage(message);
        this.setData(data);
        this.setStatus(status);
    }

    public Result() {
    }

    public static <T> Result<T> error(ExceptionCode exceptionCode) {
        return newIns(exceptionCode.getCode(), exceptionCode.getDesc(), null, false);
    }


    // newIns ==========================

    /**
     * @param status this.code = status ?  HttpStatus.OK.value() : StatusCode.FAIL;
     */
    public static <T> Result<T> newIns(Boolean status) {
        return newIns(status ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(), null, null, status);
    }


    public static <T> Result<T> newIns(Integer code, String message, T data, Boolean status) {
        return new Result<T>(code, message, data, status);
    }


    public static <T> Result<T> newIns(Integer code, String message, T data) {
        return new Result<T>(code, message, data, true);
    }

    // success  ====================================

    /**
     * 成功 <p> status = true
     */
    public static <T> Result<T> success() {
        return successMsg(null);
    }

    /**
     * 失败 <p> 状态默认 = false,message = null
     */
    public static <T> Result<T> successMsg(String message) {
        return newIns(HttpStatus.OK.value(), message, null, true);
    }

    /**
     * 成功 <p> 状态默认 = true,message = null
     */
    public static <T> Result<T> success(T data) {
        return newIns(HttpStatus.OK.value(), null, data, true);
    }

    // fail  ====================================

    /**
     * 失败 <p> 状态默认 = false,message = null
     */
    public static <T> Result<T> fail() {
        return failMsg(null);
    }

    /**
     * 错误响应码
     *
     * @param exceptionCode
     * @return
     */
    public static <T> Result<T> fail(ExceptionCode exceptionCode) {
        return newIns(exceptionCode.getCode(), exceptionCode.getDesc(), null, false);
    }


    /**
     * 失败 <p> 状态默认 = false,message = null
     */
    public static <T> Result<T> fail(T data) {
        return newIns(HttpStatus.OK.value(), null, data, false);
    }

    /**
     * 失败
     *
     * @param message 提示信息
     */
    public static <T> Result<T> failMsg(String message) {
        Result<T> tResult = newIns(HttpStatus.OK.value(), message, null, false);
        tResult.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return tResult;
    }

    // setter =================

    public Result<T> setCode(Integer code) {
        this.code = code;
        this.status = Objects.equals(HttpStatus.OK.value(), code);
        return this;
    }

}
