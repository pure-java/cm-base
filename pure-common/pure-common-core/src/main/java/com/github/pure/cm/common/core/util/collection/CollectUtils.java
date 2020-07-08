package com.github.pure.cm.common.core.util.collection;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
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
     * 获取并行 stream，集合为空，将会返回空 stream
     */
    public static <T> Stream<T> parallelStream(Collection<T> tCollection) {
        if (CollectionUtils.isEmpty(tCollection)) {
            return Stream.empty();
        }
        return tCollection.parallelStream();
    }


}
