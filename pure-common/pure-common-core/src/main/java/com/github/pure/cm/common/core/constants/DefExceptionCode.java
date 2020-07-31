package com.github.pure.cm.common.core.constants;

/**
 * <p>默认异常码</p>
 *
 * @author : 陈欢
 * @date : 2020-07-18 15:23
 **/
public enum DefExceptionCode implements ExceptionCode {

    /**
     * success_200<p>
     * 成功
     */
    SUCCESS_200("成功", 200, 0),

    /**
     * UNAUTHORIZED_401 <p>
     * 没有权限：401
     */
    UNAUTHORIZED_401("没有访问权限", 401, 0),
    /**
     * NOT_FOUND_404<br>
     * 未找到
     */
    NOT_FOUND_404("Not Found", 404, 0),

    /**
     * AUTH_FAIL_10001<p>
     * 认证失败：10001
     */
    AUTH_FAIL_10001("认证失败", 1, AppErrorCode.DEFAULT_ERROR_10000.getCode()),
    /**
     * ACCOUNT_PASSWD_ERROR_10002 <p>
     * 账号密码错误:10002
     */
    ACCOUNT_PASSWD_ERROR_10002("账号密码不正确", 2, AppErrorCode.DEFAULT_ERROR_10000.getCode()),
    /**
     * TOKEN_INVALID_10003<p>
     * 无效token:10003
     */
    TOKEN_INVALID_10003("无效的token", 3, AppErrorCode.DEFAULT_ERROR_10000.getCode()),

    /**
     * system_error_10500 <p>
     * 系统错误:10500
     */
    SYSTEM_ERROR_10500("系统错误", 500, AppErrorCode.DEFAULT_ERROR_10000.getCode()),

    /**
     * param_valid_error_501 <p>
     * 参数错误:10501
     */
    PARAM_VALID_ERROR_501("参数错误", 501, AppErrorCode.DEFAULT_ERROR_10000.getCode()),
    ;

    private final int code;
    private final String des;

    DefExceptionCode(String des, int code, Integer parentCode) {
        this.code = parentCode + code;
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
