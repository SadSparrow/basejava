package com.basejava.webapp.util;

import com.basejava.webapp.model.Organization;

public class HtmlUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isEmptyArray(String[] strings) {
        return strings == null || strings.length < 2;
    }

    public static String formatDates(Organization.Period period) {
        return DateUtil.format(period.getStartDate()) + " - " + DateUtil.format(period.getEndDate());
    }
}
