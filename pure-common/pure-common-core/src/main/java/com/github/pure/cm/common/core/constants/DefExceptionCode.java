package com.github.pure.cm.common.core.constants;

/**
 * <p>默认异常码</p>
 *
 * @author : 陈欢
 * @date : 2020-07-18 15:23
 **/
public enum DefExceptionCode implements ExceptionCode {

    /**
     * 成功
     */
    SUCCESS_200(200, "成功"),

    /**
     * 鉴权中心客户端验证，没有访问权限！10401
     */
    AUTH_CLIENT_UNAUTHORIZED_10401(AppErrorCode.DEFAULT_ERROR_10000.getCode() + 401, "没有访问权限！"),

    /**
     * 系统错误:10500
     */
    SYSTEM_ERROR_10500(AppErrorCode.DEFAULT_ERROR_10000.getCode() + 500, "系统错误"),

    /**
     * 参数错误:10501
     */
    PARAM_VALID_ERROR_501(AppErrorCode.DEFAULT_ERROR_10000.getCode() + 501, "参数错误");

    private final int code;
    private final String des;

    DefExceptionCode(int code, String des) {
        this.code = code;
        this.des = des;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return des;
    }
}