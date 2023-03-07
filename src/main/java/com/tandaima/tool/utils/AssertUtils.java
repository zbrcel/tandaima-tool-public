package com.tandaima.tool.utils;


import com.tandaima.tool.config.exception.AssertException;

/**
 * 描述：断言工具类
 * 创建时间：2023/3/6
 * @author zbrcel@gmail.com
 */
public class AssertUtils {

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new AssertException(message);
        }
    }

    public static void isTrue(boolean expression, Integer code, String message) {
        if (!expression) {
            throw new AssertException(message, code);
        }
    }

    public static void isTrue(boolean expression) {
        if (!expression) {
            throw new AssertException("操作失败");
        }
    }

    public static void fail() {
        throw new AssertException("操作失败");
    }

    public static void fail(String message) {
        throw new AssertException(message);
    }

}
