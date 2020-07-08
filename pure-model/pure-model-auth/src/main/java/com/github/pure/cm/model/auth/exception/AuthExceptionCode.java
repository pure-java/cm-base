package com.github.pure.cm.model.auth.exception;

import com.github.pure.cm.common.core.constants.ExceptionCode;

/**
 * 错误类型枚举
 *
 * @author 陈欢
 * @since 2020/7/7
 */
public enum AuthExceptionCode implements ExceptionCode {
    /**
     * 找不到权限的父级
     */
    NOT_FOUND_PARENT(15001, "菜单找不到父级");

    private Integer code;
    private String desc;


    AuthExceptionCode(int i, String desc) {
        this.code = i;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
