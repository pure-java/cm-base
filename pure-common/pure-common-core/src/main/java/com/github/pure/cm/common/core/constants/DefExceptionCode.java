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
     * auth_client_unauthorized_10401 <p>
     * 鉴权中心客户端验证，没有访问权限！10401
     */
    UNAUTHORIZED_401("没有访问权限", 401, 0),

    /**
     * auth_fail_10402<p>
     * 认证失败：10402
     */
    AUTH_FAIL_10402("认证失败", 402, AppErrorCode.DEFAULT_ERROR_10000.getCode()),
    /**
     * account_passwd_error_10403 <p>
     * 账号密码错误:10403
     */
    ACCOUNT_PASSWD_ERROR_10403("账号密码不正确", 403, AppErrorCode.DEFAULT_ERROR_10000.getCode()),
    /**
     * token_invalid_10404<p>
     * 无效token:10404
     */
    TOKEN_INVALID_10404("无效的token", 404, AppErrorCode.DEFAULT_ERROR_10000.getCode()),

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

    private DefExceptionCode(String des, int code, Integer parentCode) {
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
