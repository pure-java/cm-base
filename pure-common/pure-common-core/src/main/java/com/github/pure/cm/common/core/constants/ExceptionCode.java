package com.github.pure.cm.common.core.constants;

import com.github.pure.cm.common.core.enums.AppCode;

/**
 * @author chenhuan
 */
public interface ExceptionCode {

    ///**
    // * 没有权限
    // */
    //public static final int NO_PERMISSION = -2;
    //
    ///**
    // * 失败
    // */
    //public static final int FAIL = 0;
    ///**
    // * 成功
    // */
    //public static final int SUCCESS = 200;
    ///**
    // * 数据验证错误
    // */
    //public static final int VALID = 2;
    /**
    * 系统错误:10500
    */
    public static final int SYSTEM_ERROR = AppCode.SYSTEM_INNER.getValue() + 500;
    /**
     * 鉴权中心客户端验证，没有访问权限！10401
     */
    public static final int AUTH_CLIENT_UNAUTHORIZED = AppCode.SYSTEM_INNER.getValue() + 401;
    ///**
    // * 未登陆
    // */
    //public static final int NO_LOGIN = 501;

    Integer getCode();

    String getDesc();
}
