package com.github.canglan.cm.auth.server.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.canglan.cm.common.data.base.BaseDomCfg;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("sys_role")
public class SysRole extends BaseDomCfg<Long, SysRole> {

  /**
   * 父级
   */
  @TableField(value = "pid")
  private Long pid;
  /**
   * 角色标签
   */
  @TableField(value = "label")
  private String label;
  /**
   * 名称
   */
  @TableField(value = "name")
  private String name;
  /**
   * 备注
   */
  @TableField(value = "remake")
  private String remake;
}
