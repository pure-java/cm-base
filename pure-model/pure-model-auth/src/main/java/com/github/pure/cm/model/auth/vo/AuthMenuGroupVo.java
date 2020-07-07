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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("菜单组")
public class AuthMenuGroupVo {
    /**
     * 权限组名称
     */
    @ApiModelProperty("权限组名称")
    private String groupName;

    /**
     * 权限编码
     */
    @ApiModelProperty("权限编码")
    private String code;

    /**
     * 父级权限编码
     */
    @ApiModelProperty("父级权限编码")
    private String parentCode;

    /**
     * 权限角色
     */
    @ApiModelProperty("权限角色")
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
