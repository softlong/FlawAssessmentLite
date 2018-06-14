package com.softgrid.flawAssessmentLite.util;

import java.util.ArrayList;

public class StringUtils {

    public static boolean isEmpty(String s) {
        return (s == null || s.trim().isEmpty());
    }

    public static void add(ArrayList<String> al, String str) {
        if (isEmpty(str)) {
            al.add("NA");
        } else {
            al.add(str);
        }
    }

}
