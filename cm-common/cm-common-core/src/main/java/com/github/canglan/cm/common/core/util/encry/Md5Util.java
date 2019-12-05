package com.github.canglan.cm.common.core.util.encry;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bairitan
 * @since 2019/1/29
 */
@Slf4j
public class Md5Util {

  /**
   * md5加密，并使用BASE64Decoder进行编码
   */
  public static String md5(String s) {
    try {
      if (s == null) {
        return null;
      }
      if ("".equals(s)) {
        return "";
      }
      MessageDigest md = MessageDigest.getInstance("md5");
      return new String(md.digest(s.getBytes()));
    } catch (NoSuchAlgorithmException e) {
      log.error("md5加密发生错误 " , e);
    }
    return s;
  }

}
