package com.github.pure.cm.common.core.util;

/**
 * @author bairitan
 * @since 2019/1/10
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
