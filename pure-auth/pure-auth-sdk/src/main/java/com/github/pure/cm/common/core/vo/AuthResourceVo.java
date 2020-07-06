package com.github.pure.cm.common.core.vo;

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
@ApiModel("权限资源")
public class AuthResourceVo {

    /**
     * 菜单项url，只用于展示
     */
    @ApiModelProperty("菜单项url，只用于展示")
    private String url;

    /**
     * 资源权限码
     */
    @ApiModelProperty("资源权限码")
    private String code;
    /**
     * 资源权限名称
     */
    @ApiModelProperty("资源权限名称")
    private String name;

    /**
     * 父级权限码
     */
    @ApiModelProperty("父级权限码")
    private String parentCode;

    /**
     * 服务名称
     */
    @ApiModelProperty(value = "服务名称", example = "pure-auth-server")
    private String serverName;
    /**
     * 角色
     */
    @ApiModelProperty("角色")
    private List<AuthRoleVo> authRoleVos;

}
