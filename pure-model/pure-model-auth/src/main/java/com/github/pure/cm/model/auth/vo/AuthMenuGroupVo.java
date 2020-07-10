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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("菜单组")
public class AuthMenuGroupVo {

    /**
     * 菜单组名称
     */
    @ApiModelProperty("菜单组名称")
    @NotBlank(message = "菜单组名称不能为空")
    private String groupName;

    /**
     * 菜单组ID
     */
    @ApiModelProperty("菜单组ID")
    @NotBlank(message = "菜单组ID不能为空")
    private String groupId;

    /**
     * 父级ID
     */
    @ApiModelProperty("父级ID")
    @NotBlank(message = "父级ID不能为空")
    private String parentId;

    ///**
    // * 权限角色
    // */
    //@ApiModelProperty("权限角色")
    //private List<AuthRoleVo> authRoleVos;
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
}
