package com.fis.crm.commons;

import java.util.List;

public class MakeQueryUtils {
    public static String createInQuery(List lstObj) {
        if (lstObj == null || lstObj.isEmpty()) return "";
        String results = "(";
        int count = 0;
        int len = lstObj.size();
        for (Object object : lstObj) {
            results += object + ",";
            if (count > len - 1) {
                count++;
            }
        }
        results += ")";
        return results;
    }
}
