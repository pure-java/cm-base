package com.github.canglan.cm.identity.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.canglan.cm.common.entity.BaseDomCfg;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 系统用户
 *
 * @author chenhuan
 * @since 2017-06-26 19:57
 **/
@Setter
@Getter
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("id_user")
public class IdUser extends BaseDomCfg<Long, IdUser> {

  private static final long serialVersionUID = 1L;

  // 用户系统属性开始 =============================================
  /**
   * 账号
   **/
  @TableField(value = "user_name")
  private String userName;

  /**
   * 用户密码
   **/
  @TableField(value = "password")
  private String password;

  /**
   * 可用状态： 0 - 不可用, 1 - 可用
   */
  @TableField(value = "usable")
  private Integer usable;

  /**
   * 登陆类型,默认需要密码登陆 0 - 密码登陆
   */
  @TableField(value = "login_type")
  private Integer loginType;

  /**
   * 账号类型，根据实际需要进行改变 <br>
   */
  @TableField(value = "user_type")
  private Integer userType;

  /**
   * 分页行数 默认20
   */
  @TableField(value = "page_limit")
  private Integer pageLimit = 20;

  // 用户真实身份属性开始 =============================================
  /**
   * 真实姓名
   **/
  @TableField(value = "user_nick")
  private String userNick;

  /**
   * 性别 0 - 男 1- 女
   */
  @TableField(value = "sex")
  private Integer sex;

  /**
   * 电话号码
   **/
  @TableField(value = "telephone")
  private String telephone;

  /**
   * 邮箱地址
   **/
  @TableField(value = "email_address")
  private String emailAddress;


}
