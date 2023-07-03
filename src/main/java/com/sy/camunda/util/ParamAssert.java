package com.sy.camunda.util;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * @Author  YuXunHao
 * @Description  检验
 * @Date  2023/3/13 11:10
 * @Update
 **/
public class ParamAssert extends Assert {

    public static void notEmpty(String string, String message) {
        if (!StringUtils.hasLength(string)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(String string, String message, Object... object) {
        if (!StringUtils.hasLength(string)) {
            throw new IllegalArgumentException(String.format(message, object));
        }
    }

    public static void isEmpty(Collection<?> collection, String message, Object... object) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(String.format(message, object));
        }
    }

    public static void notEmpty(Collection<?> collection, String message, Object... object) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(String.format(message, object));
        }
    }

    public static void notNull(Object object, String message, Object... param) {
        if (object == null) {
            throw new IllegalArgumentException(String.format(message, param));
        }
    }

    public static void isNull(Object object, String message, Object... param) {
        if (object != null) {
            throw new IllegalArgumentException(String.format(message, param));
        }
    }

    public static void isTrue(boolean expression, String message, Object... object) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, object));
        }
    }

    private ParamAssert() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

}
