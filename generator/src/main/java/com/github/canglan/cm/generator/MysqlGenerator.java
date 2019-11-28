package com.github.canglan.cm.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * mysql代码生成器
 *
 * @author chenhuan
 * @create 2017-07-03 10:20
 **/

public class MysqlGenerator {


  public static void main(String[] args) {
    DataBaseConnectionConfig dataBaseInfo = new DataBaseConnectionConfig();
    dataBaseInfo.setDeiver("com.mysql.jdbc.Driver");
    dataBaseInfo.setUrl("jdbc:mysql://localhost:3306/ag_admin_v1?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8");
    dataBaseInfo.setUserName("root");
    dataBaseInfo.setPassword("chen");
    dataBaseInfo.setTypeConvert(new MySqlTypeConvert());
    GeneratorInfo gi = new GeneratorInfo();
    gi.setOutputDir("F:\\");
    gi.setAuthor("chenhuan");
    gi.setPackageName("com.github.canglan.cm");
    gi.setModelName("identity");

    gi.setTableInfo(new String[]{"id_authority","id_user","id_role","id_menu"});

    gi.setSuperEntityClass("com.github.canglan.cm.common.entity.BaseDomOpt");
    gi.setSuperControllerClass("com.github.canglan.cm.common.controller.BaseController");
    gi.setSuperServiceClass("com.github.canglan.cm.common.service.IBaseService");
    gi.setSuperServiceImplClass("com.github.canglan.cm.common.service.impl.BaseServiceImpl");
    gi.setSuperMapperClass("com.github.canglan.cm.common.mapper.BaseMapper");

    generatorMvcFile(dataBaseInfo, gi);
  }

  public static void generatorMvcFile(DataBaseConnectionConfig dataBaseInfo, GeneratorInfo generatorInfo) {
    AutoGenerator mpg = new AutoGenerator();
    // 全局配置
    GlobalConfig globalConfig = new GlobalConfig();
    globalConfig.setOutputDir(generatorInfo.getOutputDir());
    globalConfig.setFileOverride(true);
    globalConfig.setActiveRecord(true);
    // 二级缓存
    globalConfig.setEnableCache(false);
    // basexml resultMap
    globalConfig.setBaseResultMap(true);
    // basexml columnList
    globalConfig.setBaseColumnList(true);
    globalConfig.setAuthor(generatorInfo.getAuthor());
    // 配置生成文件前缀后缀
    globalConfig.setMapperName("%sMapper");
    globalConfig.setXmlName("%sMapper");
    globalConfig.setServiceName("I%sService");
    globalConfig.setServiceImplName("%sServiceImpl");
    globalConfig.setControllerName("%sController");
    mpg.setGlobalConfig(globalConfig);
    // 配置数据库信息
    DataSourceConfig dsc = new DataSourceConfig();
    dsc.setDbType(dataBaseInfo.getDbType());
    dsc.setTypeConvert(dataBaseInfo.getTypeConvert());
    dsc.setDriverName(dataBaseInfo.getDeiver());
    dsc.setUsername(dataBaseInfo.getUserName());
    dsc.setPassword(dataBaseInfo.getPassword());
    dsc.setUrl(dataBaseInfo.getUrl());
    mpg.setDataSource(dsc);

    // 配置生成表的信息以及规则
    StrategyConfig strategyConfig = new StrategyConfig();
    strategyConfig.setRestControllerStyle(true);
    // 设置为lombok模式，使用注解getter,setter,toStr
    strategyConfig.setEntityLombokModel(true);
    //strategyConfig.setCapitalMode(true);//全局大写命名 Oracle注意
    strategyConfig.setLogicDeleteFieldName("do");
    // 表前缀
    strategyConfig.setTablePrefix(new String[]{});
    // 表明生产策略
    strategyConfig.setNaming(NamingStrategy.underline_to_camel);
    // 需要生产的表
    strategyConfig.setInclude(generatorInfo.getTableInfo());
    // 需要排除的表
    strategyConfig.setExclude(new String[]{});
    // 自定义controller父类
    strategyConfig.setSuperControllerClass(generatorInfo.getSuperControllerClass());
    // 自定义实体类父类
    strategyConfig.setSuperEntityClass(generatorInfo.getSuperEntityClass());
    // 自定义服务父类
    strategyConfig.setSuperServiceClass(generatorInfo.getSuperServiceClass());
    // 自定义服务实现类
    strategyConfig.setSuperServiceImplClass(generatorInfo.getSuperServiceImplClass());
    // 自定义mapper父类
    strategyConfig.setSuperMapperClass(generatorInfo.getSuperMapperClass());
    //是否生成字段常量
    // public static final String ID = "test_id";
    strategyConfig.setEntityColumnConstant(false);
    //是否为构建者模型
    strategyConfig.setEntityBuilderModel(false);
    mpg.setStrategy(strategyConfig);

    // 配置包的信息
    PackageConfig pc = new PackageConfig();
    // 请求前缀
    pc.setRequestMapperPrefix("");
    pc.setController("controller");
    pc.setEntity("entity");
    pc.setMapper("mapper");
    pc.setService("service");
    pc.setServiceImpl("service.impl");
    pc.setParent(generatorInfo.getPackageName());
    pc.setModuleName(generatorInfo.getModelName());
    mpg.setPackageInfo(pc);
    TemplateConfig tc = new TemplateConfig();
    tc.setXml("temp/mapper.xml.vm");
    tc.setMapper("temp/mapper.java.vm");
    tc.setEntity("temp/entity.java.vm");
    tc.setController("temp/controller.java.vm");
    tc.setService("temp/service.java.vm");
    tc.setServiceImpl("temp/serviceImpl.java.vm");

    mpg.setTemplate(tc);
    mpg.setTemplateEngine(new MyVelocityTemplateEngine(pc));
    mpg.execute();
  }


}
