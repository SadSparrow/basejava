package com.basejava.webapp.util;

public class HtmlUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isEmpty(String[] strings) {
        return strings == null || strings.length < 2;
    }
}
