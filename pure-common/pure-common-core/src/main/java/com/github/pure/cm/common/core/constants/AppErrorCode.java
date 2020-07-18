package com.github.pure.cm.common.core.constants;


/**
 * 区分模块异常码
 */
public enum AppErrorCode implements ExceptionCode {
    /**
     * 全局错误信息
     */
    DEFAULT_ERROR_10000(10000, "全局错误"),
    /**
     * 系统内部异常:10000
     */
    SYSTEM_INNER_20000(20000, "系统内部错误"),
    /**
     * 后台系统管理：30000
     */
    AUTH_SERVER_30000(30000, "系统管理");
    /**
     * 值
     */
    private final int code;
    /**
     * 名称
     */
    private final String desc;

    AppErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

}