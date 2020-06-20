package com.github.pure.cm.common.core.util.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author bairitan
 * @since 2018-04-18 18:14
 **/

public class MapUtil {

  private static final int INIT_SIZE = 50;

  public static <E, K, V> Function<E, LinkedHashMap<K, V>> newLinkedHashMapFn() {
    return (v) -> newLinkedHashMap();
  }

  public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
    return new LinkedHashMap<K, V>();
  }

  public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(int initSize) {
    return new LinkedHashMap<K, V>(initSize);
  }

  public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(int initSize, float loadFactor) {
    return new LinkedHashMap<K, V>(initSize, loadFactor);
  }

  public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<K, V> initMap) {
    return new LinkedHashMap<K, V>(initMap);
  }

  // new newHashMap =====================================

  /**
   * 创建一个HashMap
   */
  public static <K, V> HashMap<K, V> newHashMap() {
    return new HashMap<K, V>();
  }

  public static <K, V> HashMap<K, V> newHashMap(K key, V value) {
    HashMap<K, V> kvHashMap = new HashMap<>();
    kvHashMap.put(key, value);
    return kvHashMap;
  }

  public static <K, V> HashMap<K, V> newHashMap(int initSize) {
    return new HashMap<K, V>(initSize);
  }

  public static <K, V> HashMap<K, V> newHashMap(Map<K, V> initMap) {
    return new HashMap<K, V>(initMap);
  }

  // new newConcurrentHashMap =====================================

  /**
   * 创建一个ConcurrentHashMap
   */
  public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
    return new ConcurrentHashMap<>(INIT_SIZE);
  }

  public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(int initSize) {
    return new ConcurrentHashMap<K, V>(initSize);
  }

  public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(Map<K, V> initMap) {
    return new ConcurrentHashMap<K, V>(initMap);
  }

  // empty =================================

  /**
   * 判断对像引用是否为null，或内容是否为empty
   */
  public static boolean isEmpty(final Map<?, ?> map) {
    return MapUtils.isEmpty(map);
  }

  /**
   * 判断内容引用是否为空
   */
  public static boolean isNotEmpty(final Map<?, ?> map) {
    return MapUtils.isNotEmpty(map);
  }

  // remove map ==================================

  /**
   * 移除map中空key或者value空值
   */
  public static <K, V> void removeNullEntry(Map<K, V> map) {
    removeNullKey(map);
    removeNullValue(map);
  }

  /**
   * 移除map的空key
   */
  public static <K, V> void removeNullKey(Map<K, V> map) {
    Set<K> set = map.keySet();
    for (Iterator<K> iterator = set.iterator(); iterator.hasNext(); ) {
      K obj = iterator.next();
      remove(obj, iterator);
    }
  }

  /**
   * 移除map中的value空值
   */
  public static <K, V> void removeNullValue(Map<K, V> map) {
    Set<K> set = map.keySet();
    for (Iterator<K> iterator = set.iterator(); iterator.hasNext(); ) {
      K obj = iterator.next();
      V value = map.get(obj);
      remove(value, iterator);
    }
  }

  /**
   * Iterator 是工作在一个独立的线程中，并且拥有一个 mutex 锁。 Iterator 被创建之后会建立一个指向原来对象的单链索引表，当原来的对象数量发生变化时，这个索引表的内容不会同步改变，
   * 所以当索引指针往后移动的时候就找不到要迭代的对象，所以按照 fail-fast 原则 Iterator 会马上抛出 java.util.ConcurrentModificationException
   * 异常。 所以 Iterator 在工作的时候是不允许被迭代的对象被改变的。 但你可以使用 Iterator 本身的方法 remove() 来删除对象， Iterator.remove()
   * 方法会在删除当前迭代对象的同时维护索引的一致性。
   */
  private static void remove(Object obj, Iterator iterator) {
    if (obj instanceof String) {
      String str = (String) obj;
      if (StringUtils.isEmpty(str)) {
        iterator.remove();
      }
    } else if (obj instanceof Collection) {
      if (CollectionUtil.isEmpty((Collection) obj)) {
        iterator.remove();
      }

    } else if (obj instanceof Map) {
      Map temp = (Map) obj;
      if (MapUtils.isEmpty(temp)) {
        iterator.remove();
      }

    } else if (obj instanceof Object[]) {
      Object[] array = (Object[]) obj;
      if (ArrayUtils.isEmpty(array)) {
        iterator.remove();
      }
    } else {
      if (Objects.isNull(obj)) {
        iterator.remove();
      }
    }
  }
}
