package com.tzCloud.arch.core.filter;

import java.util.HashSet;
import java.util.Set;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/2/27 13:39
 */
public class XSSAndSQLUtil {

    private static String      key                = "and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+";
    private static Set<String> notAllowedKeyWords = new HashSet<>(0);

    static {
        String[] keyStr = key.split("\\|");
        for (String str : keyStr) {
            notAllowedKeyWords.add(str);
        }
    }

    public static String cleanXSS(String valueP) {
        String value = valueP.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        value = cleanSqlKeyWords(value);
        return value;
    }

    private static String cleanSqlKeyWords(String value) {
        String paramValue = value;
        for (String keyword : notAllowedKeyWords) {
            if (paramValue.length() > keyword.length() + 4
                    && (paramValue.contains(" " + keyword) || paramValue.contains(keyword + " ") || paramValue.contains(" " + keyword + " "))) {
                throw new RuntimeException("系统检测参数非法，请勿输入非法参数");
            }
        }
        return paramValue;
    }
}
