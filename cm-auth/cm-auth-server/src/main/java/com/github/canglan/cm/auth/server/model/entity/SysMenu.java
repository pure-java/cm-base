package com.github.canglan.cm.auth.server.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.canglan.cm.common.data.base.BaseDomCfg;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
@Getter
@Setter
@ToString(callSuper = true)
@TableName("sys_menu")
public class SysMenu extends BaseDomCfg<Long, SysMenu> {

  /**
   * 父节点oid
   */
  @TableField(value = "pid")
  private Long pid;
  /**
   * 菜单名称
   */
  @TableField(value = "title")
  private String title;
  /**
   * url
   */
  @TableField(value = "href")
  private String href;
  /**
   * icon
   */
  @TableField(value = "icon")
  private String icon;
  /**
   * 参数
   */
  @TableField(value = "param")
  private String param;

  /**
   * 排序
   */
  @TableField(value = "order_num")
  private Integer orderNum;


}
