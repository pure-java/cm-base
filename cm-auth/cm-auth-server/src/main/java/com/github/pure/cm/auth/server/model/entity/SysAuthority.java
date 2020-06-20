package com.github.pure.cm.auth.server.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.pure.cm.common.data.base.BaseDomOpt;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陈欢
 * @since 2018-03-01 16:36
 **/
@TableName("sys_authority")
@Getter
@Setter
@ToString(callSuper = true)
public class SysAuthority extends BaseDomOpt<Long, SysAuthority> {

  /**
   * 菜单ID
   */
  @TableField(value = "menu_id")
  private String menuId;
  /**
   * 序号
   */
  @TableField(value = "order_num")
  private Integer orderNum;
  /**
   * 权限名称
   */
  @TableField(value = "name")
  private String name;
  /**
   * 权限url
   */
  @TableField(value = "expression")
  private String expression;

  /**
   * 权限描述
   */
  @TableField(value = "remark")
  private String remark;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SysAuthority)) {
      return false;
    }
    SysAuthority sysPower = (SysAuthority) o;
    return Objects.equals(getMenuId(), sysPower.getMenuId()) &&
        Objects.equals(getOrderNum(), sysPower.getOrderNum()) &&
        Objects.equals(getName(), sysPower.getName()) &&
        Objects.equals(getExpression(), sysPower.getExpression());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getMenuId(), getOrderNum(), getName(), getExpression());
  }

}