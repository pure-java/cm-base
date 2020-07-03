package com.github.pure.cm.common.core.auth.support;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 陈欢
 * @since 2020/7/3
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class AuthEntry {
    /**
     * 服务名称
     */
    private String serverName;
    /**
     * 名称
     */
    private String name;
    /**
     * code
     */
    private String code;
    /**
     * 父级编码
     */
    private String parentCode;

    /**
     * 权限 url
     */
    private String url;
    /**
     * 资源类型
     */
    private AuthTypeEnum type;
    /**
     * 备注
     */
    private String remark;

}
