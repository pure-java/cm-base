package com.github.pure.cm.auth.sdk.vo;

import com.github.pure.cm.common.core.util.StringUtil;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * <p>
 *
 *
 * </p>
 *
 * @author : 陈欢
 * @date : 2020-07-30 18:10
 **/
public enum AuthGrantType implements Serializable {
    /**
     * authorization_code
     */
    AUTHORIZATION_CODE("authorization_code"),
    /**
     * implicit
     */
    IMPLICIT("implicit"),
    /**
     * refresh_token
     */
    REFRESH_TOKEN("refresh_token"),
    /**
     * client_credentials
     */
    CLIENT_CREDENTIALS("client_credentials"),
    /**
     * password
     */
    PASSWORD("password");
    private static final long serialVersionUID = 1L;
    private final String value;

    AuthGrantType(String value) {
        Assert.hasText(value, "value cannot be empty");
        this.value = value;
    }

    public static AuthGrantType byValue(String v) {
        if (StringUtil.isBlank(v)) {
            return null;
        }

        return Arrays.stream(values()).filter(value -> value.eq(v)).findFirst().orElse(null);
    }

    public String getValue() {
        return this.value;
    }

    public boolean eq(String type) {
        return Objects.equals(this.value, type);
    }
}
