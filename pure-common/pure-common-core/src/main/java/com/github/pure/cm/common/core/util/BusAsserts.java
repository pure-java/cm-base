package com.github.pure.cm.common.core.util;

import com.github.pure.cm.common.core.constants.ExceptionCode;
import com.github.pure.cm.common.core.exception.BusinessException;

import java.util.Objects;

/**
 * 业务异常断言类
 *
 * @author 陈欢
 * @since 2020/7/7
 */
public final class BusAsserts {

    /**
     * 表达式是否为 true<br>
     * 为false将会抛出业务异常
     *
     * @param expression    表达式
     * @param exceptionCode 异常码
     */
    public static void isTrue(boolean expression, ExceptionCode exceptionCode) {
        if (!expression) {
            throw new BusinessException(exceptionCode.getCode(), exceptionCode.getDesc());
        }
    }

    /**
     * 表达式是否为 true<br>
     * 为false将会抛出业务异常
     *
     * @param expression    表达式
     * @param exceptionCode 异常码
     * @param errorMsg      错误信息
     */
    public static void isTrue(boolean expression, ExceptionCode exceptionCode, String errorMsg) {
        if (!expression) {
            throw new BusinessException(exceptionCode.getCode(), errorMsg);
        }
    }

    /**
     * 对象不为空抛出异常 <br>
     *
     * @param expression    表达式
     * @param exceptionCode 异常码
     */
    public static void isNull(Object expression, ExceptionCode exceptionCode) {
        if (!Objects.isNull(expression)) {
            throw new BusinessException(exceptionCode.getCode(), exceptionCode.getDesc());
        }
    }

    /**
     * 对象不为空抛出异常
     *
     * @param expression    表达式
     * @param exceptionCode 异常码
     * @param errorMsg      错误信息
     */
    public static void isNull(Object expression, ExceptionCode exceptionCode, String errorMsg) {
        if (!Objects.isNull(expression)) {
            throw new BusinessException(exceptionCode.getCode(), errorMsg);
        }
    }

    /**
     * 对象为空抛出异常 <br>
     *
     * @param expression    表达式
     * @param exceptionCode 异常码
     */
    public static void nonNull(Object expression, ExceptionCode exceptionCode) {
        if (!Objects.nonNull(expression)) {
            throw new BusinessException(exceptionCode.getCode(), exceptionCode.getDesc());
        }
    }

    /**
     * 对象为空抛出异常
     *
     * @param expression    表达式
     * @param exceptionCode 异常码
     * @param errorMsg      错误信息
     */
    public static void nonNull(Object expression, ExceptionCode exceptionCode, String errorMsg) {
        if (!Objects.nonNull(expression)) {
            throw new BusinessException(exceptionCode.getCode(), errorMsg);
        }
    }
}
