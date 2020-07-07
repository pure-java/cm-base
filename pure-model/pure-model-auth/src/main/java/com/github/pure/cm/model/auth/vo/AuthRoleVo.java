package com.github.pure.cm.model.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 陈欢
 * @since 2020/7/6
 */
@Builder
@Data
@ToString
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ApiModel("角色vo")
public class AuthRoleVo {
    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String name;
    /**
     * 服务名称
     */
    @ApiModelProperty(value = "服务名称", example = "pure-auth-server")
    private String serverName;
    /**
     * 角色code
     */
    @ApiModelProperty("角色code")
    private String code;
    /**
     * 服务编码
     */
    @ApiModelProperty(value = "服务编码", example = "pure-auth-server")
    private String serverCode;
}
