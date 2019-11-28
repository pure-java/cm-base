package com.github.canglan.cm.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.ITypeConvert;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据库连接配置信息
 *
 * @author bairitan
 * @create 2018-01-11 10:48
 **/
@Getter
@Setter
@ToString(callSuper = true)
public class DataBaseConnectionConfig {

  //用户名
  private String userName = "root";

  //密码
  private String password;

  //数据库连接url
  private String url;

  //驱动
  private String deiver = "com.mysql.jdbc.Driver";

  //数据库类型
  private DbType dbType = DbType.MYSQL;

  /**
   * 数据转换类型
   */
  private ITypeConvert typeConvert;

}
