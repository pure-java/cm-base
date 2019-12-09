package com.github.canglan.cm.common.core.util;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.github.canglan.cm.common.core.util.collection.MapUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * jackson转换辅助类
 *
 * @author bairitan
 **/
public final class JacksonUtil {


  /**
   * 导入
   */
  private static final String DYNC_INCLUDE = "DYNC_INCLUDE";
  /**
   * 过滤
   */
  private static final String DYNC_FILTER = "DYNC_FILTER";

  private final ObjectMapper objectMapper = new ObjectMapper();

  public JacksonUtil() {
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  // common=====================================开始

  /**
   * 创建 原型模式 对象
   */
  public static JacksonUtil newInstance() {
    return new JacksonUtil();
  }

  /**
   * 返回 单例模式对象
   */
  public static JacksonUtil singleInstance() {
    return JacksonUtilEnum.INSTANCE.jacksonUtil;
  }

  /**
   * 获取objectMapper对象
   */
  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  /**
   * 获取class的java Type
   */
  public final <T> JavaType getJavaType(Class<T> beanClass) {
    return objectMapper.getTypeFactory().constructType(beanClass);
  }

  /**
   * 过滤接口
   */
  @JsonFilter(DYNC_FILTER)
  interface DynamicFilter {

  }

  /**
   * 保留接口
   */
  @JsonFilter(DYNC_INCLUDE)
  interface DynamicInclude {

  }


  /**
   * 序列化为json，对字段进行(排除)过滤，
   *
   * @param include 剩余字段,使用[,]分隔
   * @param filter 过滤字段,使用[,]分隔
   */
  public JacksonUtil filter(Class<?> clazz, String include, String filter) {
    // 导入
    if (StringUtil.isNotBlank(include)) {
      Set<String> hashSet = Sets.newHashSet();
      hashSet.addAll(Arrays.asList(include.split(",")));
      filter(clazz, hashSet, null);
      // 排除
    } else if (StringUtil.isNotBlank(filter)) {
      Set<String> hashSet = Sets.newHashSet();
      hashSet.addAll(Arrays.asList(filter.split(",")));
      filter(clazz, null, hashSet);
    }
    return this;
  }


  /**
   * 设置过滤字段
   *
   * @param clazz 类型
   * @param filter 过滤字段
   */
  public JacksonUtil filterField(Class<?> clazz, Set<String> filter) {
    return this.filter(clazz, null, filter);
  }

  /**
   * 设置过滤字段
   *
   * @param clazz 类型
   * @param filter 过滤字段
   */
  public JacksonUtil filterField(Class<?> clazz, String... filter) {
    return this.filter(clazz, null, Sets.newHashSet(Arrays.asList(filter)));
  }


  /**
   * 保留属性,必须每次序列化为json都调用一次,不然会发生意料之外的情况
   *
   * @param clazz 类型
   * @param include 保留属性集合
   */
  public JacksonUtil includeField(Class<?> clazz, Set<String> include) {
    return this.filter(clazz, include, null);
  }

  /**
   * 序列化为json，对字段进行(排除)过滤，
   *
   * @param include 剩余字段,使用[,]分隔
   * @param filter 过滤字段,使用[,]分隔
   */
  public JacksonUtil filter(Class<?> clazz, Set<String> include, Set<String> filter) {
    if (clazz == null) {
      return null;
    }
    SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
    ObjectMapper objectMapper = this.getObjectMapper();
    if (StringUtil.isNotBlank(include)) {
      objectMapper.setFilterProvider(
          simpleFilterProvider.addFilter(DYNC_INCLUDE, SimpleBeanPropertyFilter.filterOutAllExcept(include)));
      objectMapper.addMixIn(clazz, DynamicInclude.class);
    }

    if (StringUtil.isNotBlank(filter)) {
      objectMapper.setFilterProvider(
          simpleFilterProvider.addFilter(DYNC_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(filter)));
      objectMapper.addMixIn(clazz, DynamicFilter.class);
    }
    return this;
  }

  // common   =====================================结束

  // mapConversion json======================================开始

  /**
   * 转换为json
   */
  public static <T> String json(T t) {
    return singleInstance().toJson(t);
  }

  public static <T> String json(T t, boolean nonEmpty) {
    JacksonUtil jacksonUtil = new JacksonUtil();
    if (nonEmpty) {
      jacksonUtil.objectMapper.setSerializationInclusion(Include.NON_EMPTY);
    }
    return jacksonUtil.toJson(t);
  }

  /**
   * 转换为json
   */
  public <T> String toJson(T t, boolean nonEmpty) {
    if (Objects.isNull(t)) {
      return null;
    }
    try {
      if (nonEmpty) {
        this.getObjectMapper().setSerializationInclusion(Include.NON_EMPTY);
      }
      return this.getObjectMapper().writeValueAsString(t);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("转换为json失败", e);
    }
  }

  /**
   * 转换为json
   */
  public <T> String toJson(T t) {
    if (Objects.isNull(t)) {
      return null;
    }
    try {
      return this.getObjectMapper().writeValueAsString(t);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("转换为json失败", e);
    }
  }

  public String writeValueAsString(Object value) {
    return this.toJson(value);
  }
  // mapConversion json======================================结束

  // mapConversion collection================================开始


  /**
   * 获取集合类型{@link JavaType}
   *
   * @param <T> bean泛型
   * @param collectionClass 集合类型
   * @param beanClass bean类型
   */
  public final <T> JavaType getCollectionJavaType(Class collectionClass, Class<T> beanClass) {
    return objectMapper.getTypeFactory().constructParametricType(collectionClass, beanClass);
  }


  /**
   * json转为list
   *
   * @param listContentClass list原先类型class
   */
  public <T> List<T> jsonToList(String json, Class<T> listContentClass) {
    if (StringUtil.isBlank(json)) {
      return Lists.newArrayList();
    }
    try {
      return objectMapper.readValue(json, this.getCollectionJavaType(ArrayList.class, listContentClass));
    } catch (IOException e) {
      throw new RuntimeException("转换为list失败", e);
    }
  }

  // mapConversion collection================================结束

  // mapConversion map================================开始

  /**
   * 获取Map类型的{@link JavaType}
   *
   * @param mapClass 集合类型
   * @param keyClass key类型
   * @param valueClass value类型
   */
  public <T extends Map, KEY, VALUE> JavaType getMapJavaType(Class<T> mapClass, Class<KEY> keyClass,
      Class<VALUE> valueClass) {
    return objectMapper.getTypeFactory().constructMapLikeType(mapClass, keyClass, valueClass);
  }

  /**
   * json转为map
   */
  public <T> Map<String, Object> jsonToMap(String json) {
    return jsonToMap(json, HashMap.class, String.class, Object.class);
  }

  // public Date formDate(String dateStr){
  //   // return  this.objectMapper.
  // }
  //

  /**
   * json转为map
   *
   * @param keyClass map key
   * @param valueClass map value
   */
  public <KEY, VALUE> Map<KEY, VALUE> jsonToMap(String json, Class<KEY> keyClass, Class<VALUE> valueClass) {
    return jsonToMap(json, HashMap.class, keyClass, valueClass);
  }

  /**
   * json转为map
   */
  public <T extends Map> Map jsonToMap(String json, Class<T> mapClass) {
    return jsonToMap(json, mapClass, Object.class, Object.class);
  }

  /**
   * json转为map
   *
   * @param mapClass map
   * @param keyClass map key
   * @param valueClass map value
   */
  public <T extends Map, KEY, VALUE> Map<KEY, VALUE> jsonToMap(String json, Class<T> mapClass, Class<KEY> keyClass,
      Class<VALUE> valueClass) {
    if (StringUtil.isBlank(json)) {
      return MapUtil.newHashMap();
    }
    try {
      return objectMapper.readValue(json, this.getMapJavaType(mapClass, keyClass, valueClass));
    } catch (IOException e) {
      throw new RuntimeException("json转换为map失败", e);
    }
  }

  // mapConversion map================================结束

  // mapConversion object =================================开始

  /**
   * 反序列化json为<T>对象
   *
   * @param json json数据
   * @param tClass 被转化目标Class
   */
  public <T> T jsonToObject(String json, Class<T> tClass) {
    return jsonToObject(json, getJavaType(tClass));
  }

  /**
   * 反序列化json为<T>对象
   *
   * @param json json数据
   * @param tClass 被转化目标Class
   */
  public <T, C> T jsonToObject(String json, Class<T> tClass, Class<C> contentType) {
    return jsonToObject(json, objectMapper.getTypeFactory().constructParametricType(tClass, contentType));
  }


  /**
   * 反序列化json为<T>对象
   *
   * @param json json数据
   * @param javaType 被转化目标{@link JavaType}
   */
  public <T> T jsonToObject(String json, JavaType javaType) {
    try {
      if (StringUtil.isNotBlank(json)) {
        return objectMapper.readValue(json, javaType);
      }
      return null;
    } catch (IOException e) {
      throw new RuntimeException("json转换为" + javaType + "失败", e);
    }
  }

  // mapConversion object =================================结束

  // file mapConversion object =================================开始

  /**
   * 反序列化json文件为<T>对象
   *
   * @param filePath 存储json数据的文件路径
   * @param tClass 被转化目标Class
   */
  public <T> T readFileToObject(String filePath, Class<T> tClass) {
    return readFileToObject(new File(filePath), tClass);

  }

  /**
   * 反序列化json文件为<T>对象
   *
   * @param filePath 存储json数据的文件路径
   * @param javaType 被转化目标{@link JavaType}
   */
  public <T> T readFileToObject(String filePath, JavaType javaType) {
    return readFileToObject(new File(filePath), javaType);
  }

  /**
   * 反序列化json文件为<T>对象
   *
   * @param file 存储json数据的文件
   * @param tClass 被转化目标Class
   */
  public <T> T readFileToObject(File file, Class<T> tClass) {
    return readFileToObject(file, this.getJavaType(tClass));
  }

  /**
   * 反序列化json文件为<T>对象
   *
   * @param file 存储json数据的文件
   * @param javaType 被转化目标{@link JavaType}
   */
  public <T> T readFileToObject(File file, JavaType javaType) {
    try {
      if (!file.exists()) {
        return null;
      }
      return objectMapper.readValue(file, javaType);
    } catch (IOException e) {
      throw new RuntimeException("json转换" + javaType + "失败", e);
    }
  }

  // file mapConversion object =================================结束
  private static enum JacksonUtilEnum {

    INSTANCE();
    JacksonUtil jacksonUtil;

    JacksonUtilEnum() {
      this.jacksonUtil = new JacksonUtil();
    }

    public JacksonUtil getJacksonUtil() {
      return INSTANCE.jacksonUtil;
    }

  }
}
