package com.github.pure.cm.auth.server.model.dto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

/**
 * 客户端信息
 *
 * @author 陈欢
 * @see org.springframework.security.oauth2.provider.ClientDetails
 * @since 2019/12/1
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "客户端信息")
public class ClientInfoVo {
    /**
     * 客户端ID
     */
    @ApiModelProperty(value = "客户端ID")
    private String clientId;

    /**
     * 客户端秘钥，{@link ClientDetails#isSecretRequired()} 决定客户端是否需要秘钥
     */
    @ApiModelProperty(value = "客户端秘钥，")
    private String clientSecret;

    /**
     * 用户授予客户端权限的范围，作用域代表用户授权给第三方的接口权限范围
     */
    @ApiModelProperty(value = "用户授予客户端权限的范围（授权域）")
    private Set<String> scope = Collections.emptySet();

    /**
     * 该客户端可以访问的资源。如果为空，则可以被调用方忽略
     */
    @ApiModelProperty(value = "该客户端可以访问的资源")
    private Set<String> resourceIds = Collections.emptySet();

    /**
     * 授权模式
     */
    @ApiModelProperty(value = "授权模式")
    private Set<String> authorizedGrantTypes = Collections.emptySet();

    /**
     * 预先设定的需要重定向的uri
     */
    @ApiModelProperty(value = "预先设定的需要重定向的uri")
    private Set<String> registeredRedirectUri;

    /**
     * 自动验证范围
     */
    @ApiModelProperty(value = "自动验证范围")
    private Set<String> autoApproveScopes;

    /**
     * 返回授予OAuth客户端的权限。无法返回null。请注意，这些不是使用授权访问令牌授予用户的权限。相反，这些权限是客户本身固有的。
     */
    @ApiModelProperty(value = "授予OAuth客户端的权限")
    private List<GrantedAuthority> authorities = Collections.emptyList();

    /**
     * 访问令牌有效时间
     */
    @ApiModelProperty(value = "访问令牌有效时间")
    private Integer accessTokenValiditySeconds;

    /**
     * 刷新令牌时间
     */
    @ApiModelProperty(value = "刷新令牌时间")
    private Integer refreshTokenValiditySeconds;

    /**
     * 附加信息
     */
    @ApiModelProperty(value = "附加信息")
    private Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();
}
