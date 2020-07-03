package com.github.pure.cm.common.core.auth.support;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author 陈欢
 * @since 2020/7/2
 */
public enum AuthTypeEnum {
    MENU(0), BUTTON(1);
    private int type;

    private AuthTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    /**
     * 根据code获取权限类型
     */
    public static AuthTypeEnum byCode(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        return Arrays.stream(values()).filter(var -> Objects.equals(var.type, code)).findFirst().orElse(null);
    }

}
