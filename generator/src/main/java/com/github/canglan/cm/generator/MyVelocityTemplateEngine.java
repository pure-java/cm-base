package com.github.canglan.cm.generator;

import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author bairitan
 * @since 2018/8/10
 */
public class MyVelocityTemplateEngine extends VelocityTemplateEngine {

  private PackageConfig pc;
  /**
   * 组件html模板
   */
  private String angularHtmlTemp = "temp/html/component.html";
  /**
   * 组件css模板
   */
  private String angularCssTemp = "temp/html/component.css";
  /**
   * 组件typescript模板
   */
  private String angularTsTemp = "temp/html/component.ts";

  /**
   * 组件html模板
   */
  private String addAngularHtmlTemp = "temp/html/add/add.html";
  /**
   * 组件css模板
   */
  private String addAngularCssTemp = "temp/html/add/add.css";
  /**
   * 组件typescript模板
   */
  private String addAngularTsTemp = "temp/html/add/add.ts";

  private String serviceTemp = "temp/html/component.service.ts";

  private String tsEntityTemp = "temp/html/entity.ts.vm";

  public MyVelocityTemplateEngine(PackageConfig pc) {
    this.pc = pc;
  }

  public void init() {

    ConfigBuilder configBuilder = this.getConfigBuilder();

    String outputDir = configBuilder.getGlobalConfig().getOutputDir();
    String angular = joinPath(outputDir, joinPackage(pc.getParent(), "angular"));

    configBuilder.getPathInfo().put(MyConstVal.TEMPLATE_ANGULAR_ENTITY_PATH, angular);
    configBuilder.getPathInfo().put(MyConstVal.TEMPLATE_ANGULAR_TS_PATH, angular);

    configBuilder.getPathInfo().put(MyConstVal.TEMPLATE_ANGULAR_CSS_PATH, angular);

    configBuilder.getPathInfo().put(MyConstVal.TEMPLATE_ANGULAR_HTML_PATH, angular);

    String addPath = joinPath(outputDir, joinPackage(pc.getParent(), "angular.add"));
    configBuilder.getPathInfo().put(MyConstVal.TEMPLATE_ADD_ANGULAR_TS_PATH, addPath);

    configBuilder.getPathInfo().put(MyConstVal.TEMPLATE_ADD_ANGULAR_CSS_PATH, addPath);

    configBuilder.getPathInfo().put(MyConstVal.TEMPLATE_ADD_ANGULAR_HTML_PATH, addPath);
  }

  @Override
  public AbstractTemplateEngine batchOutput() {
    init();
    try {
      List<TableInfo> tableInfoList = this.getConfigBuilder().getTableInfoList();
      Map<String, String> pathInfo = this.getConfigBuilder().getPathInfo();

      for (TableInfo tableInfo : tableInfoList) {
        Map<String, Object> objectMap = this.getObjectMap(tableInfo);
        objectMap.put("MyVelocityTemplateEngine" , new MyVelocityTemplateEngine(null));
        TemplateConfig template = this.getConfigBuilder().getTemplate();
        // 自定义内容
        InjectionConfig injectionConfig = this.getConfigBuilder().getInjectionConfig();
        if (null != injectionConfig) {
          injectionConfig.initMap();
          objectMap.put("cfg" , injectionConfig.getMap());
          List<FileOutConfig> focList = injectionConfig.getFileOutConfigList();
          if (CollectionUtils.isNotEmpty(focList)) {
            for (FileOutConfig foc : focList) {
              this.writer(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
            }
          }
        }
        // Mp.java
        String entityName = tableInfo.getEntityName();
        if (null != entityName && null != pathInfo.get(ConstVal.ENTITY_PATH)) {
          String entityFile = String
              .format((pathInfo.get(ConstVal.ENTITY_PATH) + File.separator + "%s" + this.suffixJavaOrKt()), entityName);
          this.writer(objectMap,
              this.templateFilePath(template.getEntity(this.getConfigBuilder().getGlobalConfig().isKotlin())),
              entityFile);
        }
        // MpMapper.java
        if (null != tableInfo.getMapperName() && null != pathInfo.get(ConstVal.MAPPER_PATH)) {
          String mapperFile = String.format(
              (pathInfo.get(ConstVal.MAPPER_PATH) + File.separator + tableInfo.getMapperName() + this.suffixJavaOrKt()),
              entityName);
          this.writer(objectMap, this.templateFilePath(template.getMapper()), mapperFile);
        }
        // MpMapper.xml
        if (null != tableInfo.getXmlName() && null != pathInfo.get(ConstVal.XML_PATH)) {
          String xmlFile = String
              .format((pathInfo.get(ConstVal.XML_PATH) + File.separator + tableInfo.getXmlName() + ConstVal.XML_SUFFIX),
                  entityName);
          this.writer(objectMap, this.templateFilePath(template.getXml()), xmlFile);
        }
        // IMpService.java
        if (null != tableInfo.getServiceName() && null != pathInfo.get(ConstVal.SERVICE_PATH)) {
          String serviceFile = String.format(
              (pathInfo.get(ConstVal.SERVICE_PATH) + File.separator + tableInfo.getServiceName() + this.suffixJavaOrKt()),
              entityName);
          this.writer(objectMap, this.templateFilePath(template.getService()), serviceFile);
        }
        // MpServiceImpl.java
        if (null != tableInfo.getServiceImplName() && null != pathInfo.get(ConstVal.SERVICE_IMPL_PATH)) {
          String implFile = String.format(
              (pathInfo.get(ConstVal.SERVICE_IMPL_PATH) + File.separator + tableInfo.getServiceImplName() + this
                  .suffixJavaOrKt()), entityName);
          this.writer(objectMap, this.templateFilePath(template.getServiceImpl()), implFile);
        }
        // MpController.java
        if (null != tableInfo.getControllerName() && null != pathInfo.get(ConstVal.CONTROLLER_PATH)) {
          String controllerFile = String.format(
              (pathInfo.get(ConstVal.CONTROLLER_PATH) + File.separator + tableInfo.getControllerName() + this
                  .suffixJavaOrKt()), entityName);
          this.writer(objectMap, this.templateFilePath(template.getController()), controllerFile);
        }

        // ========================================================================

        // Angular service
        // 输出文件路径
        // if (null != tableInfo.getControllerName() && null != pathInfo.get(MyConstVal.TEMPLATE_ANGULAR_HTML_PATH)) {
        //   // 文件路径
        //   String componentTs = String.format(
        //       (pathInfo.get(MyConstVal.TEMPLATE_ANGULAR_HTML_PATH) + File.separator
        //           + split(tableInfo.getEntityName()) + ".service.ts"), entityName);
        //   createFile(componentTs);
        //   // 模板路径
        //   this.writer(objectMap, this.templateFilePath(serviceTemp), componentTs);
        // }

        //  生产 ts entity
        // 输出文件路径
        // if (null != tableInfo.getControllerName() && null != pathInfo.get(MyConstVal.TEMPLATE_ANGULAR_ENTITY_PATH)) {
        //   // 文件路径
        //   String componentTs = String.format(
        //       (pathInfo.get(MyConstVal.TEMPLATE_ANGULAR_ENTITY_PATH) + File.separator
        //           + split(tableInfo.getEntityName()) + ".entity.ts"), entityName);
        //   createFile(componentTs);
        //   // 模板路径
        //   this.writer(objectMap, this.templateFilePath(tsEntityTemp), componentTs);
        // }
        //
        // // 输出文件路径
        // if (null != tableInfo.getControllerName() && null != pathInfo.get(MyConstVal.TEMPLATE_ANGULAR_TS_PATH)) {
        //   // 文件路径
        //   String componentTs = String.format(
        //       (pathInfo.get(MyConstVal.TEMPLATE_ANGULAR_TS_PATH) + File.separator
        //           + split(tableInfo.getEntityName()) + ".component.ts"), entityName);
        //   createFile(componentTs);
        //   // 模板路径
        //   this.writer(objectMap, this.templateFilePath(angularTsTemp), componentTs);
        // }
        //
        // // Angular component html
        // // 输出文件路径
        // if (null != tableInfo.getControllerName() && null != pathInfo.get(MyConstVal.TEMPLATE_ANGULAR_HTML_PATH)) {
        //   // 文件路径
        //   String componentTs = String.format(
        //       (pathInfo.get(MyConstVal.TEMPLATE_ANGULAR_HTML_PATH) + File.separator
        //           + split(tableInfo.getEntityName()) + ".component.html"), entityName);
        //   createFile(componentTs);
        //   // 模板路径
        //   this.writer(objectMap, this.templateFilePath(angularHtmlTemp), componentTs);
        // }
        //
        // // Angular component css
        // // 输出文件路径
        // if (null != tableInfo.getControllerName() && null != pathInfo.get(MyConstVal.TEMPLATE_ANGULAR_CSS_PATH)) {
        //   // 文件路径
        //   String componentTs = String.format(
        //       (pathInfo.get(MyConstVal.TEMPLATE_ANGULAR_CSS_PATH) + File.separator
        //           + split(tableInfo.getEntityName()) + ".component.css"), entityName);
        //   createFile(componentTs);
        //   // 模板路径
        //   this.writer(objectMap, this.templateFilePath(angularCssTemp), componentTs);
        // }
        //
        // // add
        // // Angular add html
        // if (null != tableInfo.getControllerName() && null != pathInfo.get(MyConstVal.TEMPLATE_ADD_ANGULAR_TS_PATH)) {
        //   // 文件路径
        //   String componentTs = String.format(
        //       (pathInfo.get(MyConstVal.TEMPLATE_ADD_ANGULAR_TS_PATH) + File.separator
        //           + split(tableInfo.getEntityName()) + "-add.component.ts"), entityName);
        //   createFile(componentTs);
        //   // 模板路径
        //   this.writer(objectMap, this.templateFilePath(addAngularTsTemp), componentTs);
        // }
        //
        // // Angular add html
        // // 输出文件路径
        // if (null != tableInfo.getControllerName() && null != pathInfo.get(MyConstVal.TEMPLATE_ADD_ANGULAR_HTML_PATH)) {
        //   // 文件路径
        //   String componentTs = String.format(
        //       (pathInfo.get(MyConstVal.TEMPLATE_ADD_ANGULAR_HTML_PATH) + File.separator
        //           + split(tableInfo.getEntityName()) + "-add.component.html"), entityName);
        //   createFile(componentTs);
        //   // 模板路径
        //   this.writer(objectMap, this.templateFilePath(addAngularHtmlTemp), componentTs);
        // }
        //
        // // Angular add css
        // // 输出文件路径
        // if (null != tableInfo.getControllerName() && null != pathInfo.get(MyConstVal.TEMPLATE_ADD_ANGULAR_CSS_PATH)) {
        //   // 文件路径
        //   String componentTs = String.format(
        //       (pathInfo.get(MyConstVal.TEMPLATE_ADD_ANGULAR_CSS_PATH) + File.separator
        //           + split(tableInfo.getEntityName()) + "-add.component.css"), entityName);
        //   createFile(componentTs);
        //   // 模板路径
        //   this.writer(objectMap, this.templateFilePath(addAngularCssTemp), componentTs);
        // }

      }
    } catch (Exception e) {
      logger.error("无法创建文件，请检查配置信息！" , e);
    }
    return this;
  }

  private void createFile(String filePath) {
    File file = new File(filePath);
    if (!file.getParentFile().exists()) {
      file.getParentFile().mkdirs();
    }
  }

  public void setAngularHtmlTemp(String angularHtmlTemp) {
    this.angularHtmlTemp = angularHtmlTemp;
  }

  public void setAngularCssTemp(String angularCssTemp) {
    this.angularCssTemp = angularCssTemp;
  }

  public void setAngularTsTemp(String angularTsTemp) {
    this.angularTsTemp = angularTsTemp;
  }

  /**
   * <p>
   * 连接父子包名
   * </p>
   *
   * @param parent 父包名
   * @param subPackage 子包名
   * @return 连接后的包名
   */
  private String joinPackage(String parent, String subPackage) {
    if (StringUtils.isEmpty(parent)) {
      return subPackage;
    }
    return parent + "." + subPackage;
  }

  /**
   * <p>
   * 连接路径字符串
   * </p>
   *
   * @param parentDir 路径常量字符串
   * @param packageName 包名
   * @return 连接后的路径
   */
  private String joinPath(String parentDir, String packageName) {
    if (StringUtils.isEmpty(parentDir)) {
      parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
    }
    if (!StringUtils.endsWith(parentDir, File.separator)) {
      parentDir += File.separator;
    }
    packageName = packageName.replaceAll("\\." , "\\" + File.separator);
    return parentDir + packageName;
  }

  public static String split(String string) {
    StringBuilder stringBuilder = new StringBuilder(string);

    int temp = 0;
    for (int i = 1; i < string.length(); i++) {
      if (Character.isUpperCase(string.charAt(i))) {
        stringBuilder.insert(i + temp, "-");
        temp += 1;
      }
    }
    return stringBuilder.toString().toLowerCase();
  }

}
