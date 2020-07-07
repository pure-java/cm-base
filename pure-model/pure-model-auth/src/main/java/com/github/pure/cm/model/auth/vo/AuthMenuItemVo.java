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
 * @author 陈欢
 * @since 2020/7/6
 */
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("菜单项")
public class AuthMenuItemVo {
    /**
     * 菜单项名称
     */
    @ApiModelProperty("菜单项名称")
    private String name;
    /**
     * 菜单项权限码
     */
    @ApiModelProperty("菜单项权限码")
    private String code;

    /**
     * 菜单项url，只用于展示
     */
    @ApiModelProperty("菜单项url，只用于展示")
    private String url;
    /**
     * 父级权限码
     */
    @ApiModelProperty("菜单项父级权限码")
    private String parentCode;
    /**
     * 菜单项角色
     */
    @ApiModelProperty("菜单项角色")
    private List<AuthRoleVo> authRoleVos;
    /**
     * 服务名称
     */
    @ApiModelProperty(value = "服务名称", example = "pure-auth-server")
    private String serverName;
    /**
     * 服务编码
     */
    @ApiModelProperty(value = "服务编码", example = "pure-auth-server")
    private String serverCode;
}
