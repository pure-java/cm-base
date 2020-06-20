package com.github.pure.cm.common.core.util.encry;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class CipherUtil {

  public static String md5(String s) {
    if (s == null) {
      return null;
    }
    if ("".equals(s)) {
      return "";
    }

    return new String(Base64.encodeBase64(DigestUtils.md5(s)));
  }

  /**
   * base 加密
   */
  public static String baseEncrypt(String s) {
    if (s == null) {
      return null;
    }
    if ("".equals(s)) {
      return "";
    }
    byte[] b = s.getBytes();
    return new String(Base64.encodeBase64(b));
  }

  /**
   * base 解密
   */
  public static String baseDecrypt(String s) {
    if (s == null) {
      return null;
    }
    if ("".equals(s)) {
      return "";
    }
    return new String(Base64.decodeBase64(s));
  }

}
