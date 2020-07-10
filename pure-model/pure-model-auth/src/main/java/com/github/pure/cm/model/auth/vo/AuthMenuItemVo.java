package com.github.pure.cm.model.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "菜单项名称不能为空")
    private String name;
    /**
     * 菜单项ID
     */
    @ApiModelProperty("菜单ID")
    @NotBlank(message = "菜单ID不能为空")
    private String itemId;

    /**
     * 菜单项url，只用于展示
     */
    @ApiModelProperty("菜单项url，只用于展示")
    private String url;
    /**
     * 父级id
     */
    @ApiModelProperty("菜单项父级ID")
    @NotBlank(message = "菜单项父级ID不能为空")
    private String parentId;
    /**
     * 菜单项角色
     */
    @ApiModelProperty("菜单项角色")
    private List<AuthRoleVo> authRoleVos;
    /**
     * 服务名称
     */
    @ApiModelProperty(value = "服务名称", example = "pure-auth-server")
    private String appName;
    /**
     * 服务编码
     */
    @ApiModelProperty(value = "服务编码", example = "pure-auth-server")
    private String appCode;

    /**
     * 菜单项权限码
     */
    @ApiModelProperty(value = "菜单项权限码", example = "ROLE_ADMIN;_auth_server_test")
    @NotBlank(message = "菜单项权限码不能为空")
    private String authCode;
}
