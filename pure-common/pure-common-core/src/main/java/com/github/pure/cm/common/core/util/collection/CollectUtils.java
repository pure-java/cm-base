package com.github.pure.cm.common.core.util.collection;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author 陈欢
 * @since 2020/7/8
 */
public final class CollectUtils {
    /**
     * 获取stream，集合为空，将会返回空stream
     */
    public static <T> Stream<T> stream(Collection<T> tCollection) {
        if (CollectionUtils.isEmpty(tCollection)) {
            return Stream.empty();
        }
        return tCollection.stream();
    }

    /**
     * 排序
     *
     * @param ts         集合
     * @param comparator 排序规则
     * @param <T>        类型
     * @return 排序后的流
     */
    public static <T> Stream<T> sort(Collection<T> ts, Comparator<? super T> comparator) {
        return stream(ts).sorted(comparator);
    }

    /**
     * 过虑
     *
     * @param ts         集合
     * @param tPredicate 规则，为true将保留
     * @param <T>        类型
     * @return 过虑后的流
     */
    public static <T> Stream<T> filter(Collection<T> ts, Predicate<T> tPredicate) {
        return stream(ts).filter(tPredicate);
    }

    /**
     * 获取并行 stream，集合为空，将会返回空 stream
     */
    public static <T> Stream<T> parallelStream(Collection<T> tCollection) {
        if (CollectionUtils.isEmpty(tCollection)) {
            return Stream.empty();
        }
        return tCollection.parallelStream();
    }


}
