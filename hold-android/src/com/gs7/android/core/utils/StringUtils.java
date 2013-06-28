package com.gs7.android.core.utils;

public class StringUtils {

    /**
     * 判断字符串是否为NULL或者空
     * 
     * @param s
     * @return
     */
    public static boolean isNullOrEmpty(String s) {
        return (s == null || s.length() <= 0);
    }

    /**
     * 判断字符串是否为NULL、空、空格
     * 
     * @param s
     * @return
     */
    public static boolean isNullOrEmptyOrSpace(String s) {
        return (isNullOrEmpty(s) || s.trim().length() <= 0);
    }
}
