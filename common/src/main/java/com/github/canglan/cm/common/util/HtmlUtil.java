package com.github.canglan.cm.common.util;

/**
 * @author bairitan
 * @date 2019/1/10
 */
public class HtmlUtil {


  /**
   * 将html 标签替换为空格
   */
  public static String htmlTagRepSpace(String str) {
    if (StringUtil.isBlank(str)) {
      return "";
    }
    return str.replaceAll("<[.[^>]]*>" , " ");
  }

}
