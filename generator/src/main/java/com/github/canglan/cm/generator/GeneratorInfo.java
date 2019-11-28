package com.github.canglan.cm.generator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 生成文件配置信息
 *
 * @author bairitan
 * @create 2018-01-11 10:51
 **/
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class GeneratorInfo {

  /**
   * 要生成的表的名称，如果是log_job_alert命名方式，把首字母改为大写。Log__Job_Alert
   */
  private String[] tableInfo;

  /**
   * 作者名字
   */
  private String author;

  /**
   * 生成文件路径
   */
  private String outputDir;
  /**
   * 包名称
   */
  private String packageName = "com.github.canglan.cm";
  /**
   * 模块名称
   */
  private String modelName;

  /**
   * 自定义父实体类
   */
  private String superEntityClass ;

  /**
   * controller 父类
   */
  private String superControllerClass;

  /**
   * 自定义服务父类
   */
  private String superServiceClass;

  /**
   * 自定义服务实现类
   */
  private String superServiceImplClass;

  /**
   * 自定义父mapper类
   */
  private String superMapperClass ;

}
