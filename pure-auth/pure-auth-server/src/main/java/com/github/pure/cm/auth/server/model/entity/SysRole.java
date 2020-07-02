package com.github.pure.cm.auth.server.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.pure.cm.common.data.base.domain.BaseDomDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @author 陈欢
 * @since 2019/11/20
 */
@Getter
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role")
public class SysRole extends BaseDomDate<Long, SysRole> {

    /**
     * 父级id
     */
    @TableField(value = "pid")
    private Long pid;
    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 角色标签
     */
    @TableField(value = "code")
    private String code;

    /**
     * 备注
     */
    @TableField(value = "remake")
    private String remake;

    /**
     * 菜单
     */
    @TableField(exist = false)
    private List<SysMenu> sysMenuList;

}
