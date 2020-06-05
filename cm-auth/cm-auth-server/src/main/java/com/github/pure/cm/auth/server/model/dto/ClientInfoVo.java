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
     * 客户端秘钥
     */
    @ApiModelProperty(value = "客户端秘钥")
    private String clientSecret;

    /**
     * 客户端管理域
     */
    @ApiModelProperty(value = "客户端管理域")
    private Set<String> scope = Collections.emptySet();

    /**
     * 资源ID
     */
    @ApiModelProperty(value = "资源ID")
    private Set<String> resourceIds = Collections.emptySet();

    /**
     * 权限
     */
    @ApiModelProperty(value = "权限")
    private Set<String> authorizedGrantTypes = Collections.emptySet();

    /**
     * 要跳转到的uri
     */
    @ApiModelProperty(value = "要跳转到的uri")
    private Set<String> registeredRedirectUri;

    /**
     * 自动验证范围
     */
    @ApiModelProperty(value = "自动验证范围")
    private Set<String> autoApproveScopes;

    /**
     * 权限
     */
    @ApiModelProperty(value = "权限")
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
