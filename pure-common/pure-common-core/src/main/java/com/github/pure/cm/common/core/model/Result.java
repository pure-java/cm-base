package com.github.pure.cm.common.core.model;

import com.github.pure.cm.common.core.constants.ExceptionCode;

import java.io.Serializable;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

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
    // newIns ==========================

    /**
     * @param status this.code = status ?  HttpStatus.OK.value() : StatusCode.FAIL;
     */
    public static <T> Result<T> newInstance(Boolean status) {
        return newInstance(status ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(), null, null, status);
    }


    public static <T> Result<T> newInstance(Integer code, String message, T data, Boolean status) {
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
        return success(null);
    }

    /**
     * 成功 <p> 状态默认 = true,message = message，data = null
     */
    public static <T> Result<T> success(String message) {
        return newInstance(HttpStatus.OK.value(), message, null, true);
    }

    /**
     * 成功 <p> 状态默认 = true,message = null
     */
    public static <T> Result<T> success(T data) {
        return newInstance(HttpStatus.OK.value(), null, data, true);
    }

    // fail  ====================================

    /**
     * 失败 <p> 状态默认 = false,message = null
     */
    public static <T> Result<T> fail() {
        return fail(null);
    }

    /**
     * 失败 <p> 状态默认 = false,message = null
     */
    public static <T> Result<T> fail(T data) {
        return newInstance(HttpStatus.OK.value(), null, data, false);
    }

    /**
     * 失败
     *
     * @param message 提示信息
     */
    public static <T> Result<T> fail(String message) {
        Result<T> tResult = newInstance(HttpStatus.OK.value(), message, null, false);
        tResult.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return tResult;
    }

    // setter =================

    public Result<T> setCode(Integer code) {
        this.code = code;
        if (!Objects.equals(HttpStatus.OK.value(), code)) {
            this.status = false;
        } else {
            this.status = true;
        }
        return this;
    }

}
