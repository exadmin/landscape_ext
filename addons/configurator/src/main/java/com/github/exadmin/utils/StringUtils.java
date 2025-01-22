package com.github.exadmin.utils;

public class StringUtils {
    public static String getFirstNonEmpty(String str1, String str2) {
        if (str1 != null && !str1.isEmpty()) return str1;
        return str2;
    }
}
