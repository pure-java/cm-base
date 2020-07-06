package com.github.pure.cm.common.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class AuthRegisterVo {
    /**
     * 服务名称
     */
    @ApiModelProperty(value = "服务名称", example = "pure-auth-server")
    private String serverName;

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

}
