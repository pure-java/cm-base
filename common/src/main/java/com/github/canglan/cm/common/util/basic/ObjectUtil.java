package com.github.canglan.cm.common.util.basic;

import java.util.Objects;
import java.util.function.Function;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author bairitan
 * @date 2018-04-19 15:27
 **/
public class ObjectUtil {

  public static boolean isNotNull(Object o) {
    return Objects.nonNull(o);
  }

  public static <T, R> R isNotNull(T t, Function<T, R> function) {
    if (Objects.isNull(t)) {
      return null;
    }
    return function.apply(t);
  }

  public static boolean notEqual(Object obj, Object obj1) {
    return !Objects.equals(obj, obj1);
  }

  public static boolean allNotNull(Object... o) {
    return ObjectUtils.allNotNull(o);
  }
}
