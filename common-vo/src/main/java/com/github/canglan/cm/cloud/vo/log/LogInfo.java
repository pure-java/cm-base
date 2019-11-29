package com.github.canglan.cm.cloud.vo.log;

import java.io.Serializable;
import lombok.Data;

/**
 * 日志vo
 *
 * @since 2019/11/11
 */
@Data
public class LogInfo implements Serializable {

 /**
  * 菜单
  */
  private String menu;
 /**
  * URL地址
  */
 private String url;
 /**
  * 操作
  */
  private String opt;
 /**
  * 操作时间
  */
 private String optTime;
 /**
  * 操作人名称
  */
  private String optName;
 /**
  * 操作人用户ID
  */
 private String optUser;
 /**
  * 操作ip
  */
  private String optHost;
 /**
  * 操作内容
  */
 private String content;
}
