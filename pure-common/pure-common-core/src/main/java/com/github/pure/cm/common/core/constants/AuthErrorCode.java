package com.github.pure.cm.common.core.constants;

/**
 * 验证相关错误码
 *
 * @author bairitan
 * @date 2020/8/16
 */
public enum AuthErrorCode implements ExceptionCode {

    /**
     * UNAUTHORIZED_401<br>
     * 没有权限：401
     */
    UNAUTHORIZED_401("没有访问权限", 401),
    /**
     * AUTH_FAIL_10001<br>
     * 认证失败：10001
     */
    AUTH_FAIL_10001("认证失败", 10001),
    /**
     * ACCOUNT_PASSWD_ERROR_10002<br>
     * 账号密码错误:10002
     */
    ACCOUNT_PASSWD_ERROR_10002("账号密码不正确", 10002),
    /**
     * TOKEN_INVALID_10003<br>
     * 无效token:10003
     */
    TOKEN_INVALID_10003("无效的token", 10003),
    ;
    private final int code;
    private final String des;

    AuthErrorCode(String des, int code) {
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
