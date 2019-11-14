package com.github.canglan.cm.common.cfg;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * web层常量
 *
 * @author bairitan
 * @create 2018-04-21 16:40
 **/
@ConfigurationProperties(prefix = "app")
public class AppProperties {

  /**
   * session用户key
   */
  public static final String SESSION_USER_KEY = "session_user_key";
  /**
   * session验证码key
   */
  public static final String SESSION_VERIFY_CODE = "session_verify_code";

  /**
   * session是否管理员key
   */
  public static final String SESSION_USER_IS_ADMIN = "session_user_isAdmin";

  /**
   * APP返回sessionKey的key
   */
  public static final String APP_SESSION_KEY = "sessionKey";

  /**
   * APP返回用户信息的key
   */
  public static final String APP_USER_INFO = "userInfo";

}

