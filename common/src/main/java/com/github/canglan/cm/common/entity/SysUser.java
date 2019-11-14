package com.github.canglan.cm.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 系统用户
 *
 * @author chenhuan
 * @create 2017-06-26 19:57
 **/
@TableName("sys_user")
@Setter
@Getter
@ToString(callSuper = true)
@Accessors(chain = true)
public class SysUser extends BaseDomOpt<String, SysUser> {

  private static final long serialVersionUID = 1L;

  // 用户系统属性开始 =============================================
  /**
   * 账号
   **/
  @ApiModelProperty(name = "userName", value = "请输入账号", dataType = "String")
  @TableField(value = "userName")
  private String userName;

  /**
   * 用户密码
   **/
  @ApiModelProperty(hidden = true)
  @TableField(value = "userPassword")
  private String userPassword;

  /**
   * 密码加密盐
   */
  @ApiModelProperty(hidden = true)
  @TableField(value = "salt")
  private String salt;

  /**
   * 可用状态： 0 - 不可用, 1 - 可用
   */
  @ApiModelProperty(name = "usable", value = "可用状态： 0 - 不可用, 1 - 可用", dataType = "Integer")
  @TableField(value = "usable")
  private Integer usable;

  /**
   * 登陆类型,默认需要密码登陆 0 - 密码登陆 2 - token登陆
   */
  @ApiModelProperty(name = "loginType", value = "登陆类型,默认需要密码登陆 0 - 密码登陆 2 - token登陆", dataType = "Integer")
  @TableField(value = "loginType")
  private Integer loginType;

  /**
   * 账号类型，根据实际需要进行改变 <br>
   */
  @ApiModelProperty(name = "userType", value = "账号类型，根据实际需要进行改变", dataType = "Integer")
  @TableField(value = "userType")
  private Integer userType;

  /**
   * 部门ID
   */
  @ApiModelProperty(name = "departId", value = "部门ID", dataType = "String")
  @TableField(value = "departId")
  private String departId;

  /**
   * 是否是管理员权限（0 - 否，1 - 是）
   */
  @ApiModelProperty(name = "isAdmin", value = "是否是管理员权限（0 - 否，1 - 是）", dataType = "Integer")
  @TableField(value = "isAdmin")
  private Integer isAdmin;
  /**
   * 分页行数 默认20
   */
  @ApiModelProperty(name = "pageLimit", value = "分页行数 默认20", dataType = "Integer")
  @TableField(value = "pageLimit")
  private Integer pageLimit = 20;


  // 用户真实身份属性开始 =============================================
  /**
   * 真实姓名
   **/
  @ApiModelProperty(name = "userNick", value = "真实姓名", dataType = "String")
  @TableField(value = "userNick")
  private String userNick;

  /**
   * 性别 0 - 男 1- 女
   */
  @ApiModelProperty(name = "sex", value = "性别 0 - 男 1- 女", dataType = "Integer")
  @TableField(value = "sex")
  private Integer sex;

  /**
   * 电话号码
   **/
  @ApiModelProperty(name = "telephone", value = "电话号码", dataType = "String")
  @TableField(value = "telephone")
  private String telephone;

  /**
   * 邮箱地址
   **/
  @ApiModelProperty(name = "emailAddress", value = "邮箱地址", dataType = "String")
  @TableField(value = "emailAddress")
  private String emailAddress;
  @Override
  public Set<String> jsonRetain() {
    Set<String> remainFieldSet = super.jsonRetain();
    remainFieldSet.add("cityCode");
    return remainFieldSet;
  }

}
