package com.github.pure.cm.common.core.util.encry;

import java.util.Base64;
import lombok.extern.slf4j.Slf4j;

/**
 * 加密
 *
 * @author bairitan
 */
@Slf4j
public class Base64Util {

  /**
   * BASE64Decoder编码
   *
   * @param s 需要编码字符串
   */
  public static String baseEncrypt(String s) {
    if (s == null) {
      return null;
    }
    if ("".equals(s)) {
      return "";
    }
    byte[] b = s.getBytes();
    Base64.Encoder base64 = Base64.getEncoder();
    return new String(base64.encode(b));
  }

  /**
   * BASE64Decoder解码
   */
  public static String baseDecrypt(String s) {
    if (s == null) {
      return null;
    }
    if ("".equals(s)) {
      return "";
    }
    try {
      Base64.Decoder decoder = Base64.getDecoder();
      return new String(decoder.decode(s));
    } catch (Exception e) {
      log.error("base64解密发生错误 " , e);
    }
    return s;
  }

  public static void main(String[] args) {

    //String s="login:||_||menglin||_||assword:||_||1qaz@WSX";
//		String s127="login:,user:,userName:|administrator\r|password:,pass:|wangchong\r";
    String s127 = "login:,user:,userName:|menglin|password:,pass:|!QAZ2wsx";
    String[] arr = s127.split("\\|");
    for (String s : arr) {
      System.out.println(baseEncrypt(s));
    }
//		System.out.println(baseEncrypt("1qaz@WSX"));
    System.out.println("---------------------");
    System.out.println(baseDecrypt("MQ=="));
    System.out.println("aaaaaaa");
    String t = "bG9naW46LHVzZXI6LHVzZXJOYW1lOg==|YWRtaW5pc3RyYXRvcg==|cGFzc3dvcmQ6LHBhc3M6|d2FuZ2Nob25n";
    for (String s : t.split("\\|")) {
      System.out.println(baseDecrypt(s));
    }
    System.out.println(baseDecrypt("VGhRLw=="));
    System.out.println(baseDecrypt(baseDecrypt("VTBOWlJFQjVZVzExTURJeA==")));
    System.out.println(baseDecrypt(baseDecrypt(baseDecrypt("VGhRLw=="))));
  }
}
