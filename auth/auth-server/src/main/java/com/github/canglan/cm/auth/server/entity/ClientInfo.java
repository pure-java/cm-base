package com.github.canglan.cm.auth.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.canglan.cm.common.entity.BaseDomOpt;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author bairitan
 * @since 2019/11/13
 */
@TableName("auth_client_info")
@Data
@Accessors(chain = true)
public class ClientInfo extends BaseDomOpt<Integer, ClientInfo> {

  /**
   * 客户端名称
   */
  @TableField(value = "name")
  private String name;
  /**
   * 客户端秘钥
   */
  @TableField(value = "secret")
  private String secret;
  /**
   * 客户端编码
   */
  @TableField(value = "code")
  private String code;
  /**
   * 客户端是否锁定
   */
  @TableField(value = "locked")
  private Integer locked;
  /**
   * 描述
   */
  @TableField(value = "desc")
  private String desc;

}
