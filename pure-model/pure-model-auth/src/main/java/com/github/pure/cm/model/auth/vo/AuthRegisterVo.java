package com.github.pure.cm.model.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 注册权限vo
 *
 * @author 陈欢
 * @since 2020/7/6
 */
@Data
@ApiModel(value = "注册权限vo")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class AuthRegisterVo {
    /**
     * 服务名称
     */
    @ApiModelProperty(value = "服务名称", example = "pure-auth-server")
    private String serverName;

    /**
     * 服务code
     */
    @ApiModelProperty(value = "服务code", example = "pure-auth-server")
    private String serverCode;

    /**
     * 菜单组
     */
    @ApiModelProperty(value = "菜单组")
    private List<AuthMenuGroupVo> authMenuGroupVos;

    /**
     * 菜单项
     */
    @ApiModelProperty(value = "菜单项")
    private List<AuthMenuItemVo> authMenuItemVos;
    /**
     * 菜单资源
     */
    @ApiModelProperty(value = "菜单资源")
    private List<AuthResourceVo> authResourceVos;

    /**
     * 角色信息
     */
    @ApiModelProperty(value = "角色信息")
    List<AuthRoleVo> authRoleVos;
}
