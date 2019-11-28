package com.github.canglan.cm.common.model;

import java.nio.charset.Charset;

/**
 * 全局常量
 *
 * @author bairitan
 * @since 2018-04-21 14:11
 **/
public interface BaseConstants {

  /**
   * 全局字符编码
   */
  public static final Charset CHARACTER_ENCODING = Charset.forName("UTF-8");

  /**
   * 文件临时目录
   */
  public static final String FILE_TEMP_DIR = "/file_temp_";

  /**
   * zip文件正则
   */
  public static final String ZIP_PATTERN = ".*?\\.zip";

  /**
   * txt文件正则
   */
  public static final String TXT_PATTERN = ".*?\\.expand";
}
