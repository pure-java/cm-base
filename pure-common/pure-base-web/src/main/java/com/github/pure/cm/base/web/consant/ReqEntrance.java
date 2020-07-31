package com.github.pure.cm.base.web.consant;

import java.util.Objects;

/**
 * <p>请求入口</p>
 *
 * @author : 陈欢
 * @date : 2020-07-31 17:26
 **/
public enum ReqEntrance {
    /**
     * 内部
     */
    inner,
    /**
     * 网关
     */
    gateway;

    /**
     * 保存请求入口的请求头key
     */
    public static final String key = "x-entrance";



    public boolean eq(String entrance) {
        return Objects.equals(this.name(), entrance);
    }
}
