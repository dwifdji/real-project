package com._360pai.arch.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zxiao
 * @Title: PlaceholderUtils
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/20 16:46
 */
public class PlaceholderUtils {
    private static final Logger logger = LoggerFactory.getLogger(PlaceholderUtils.class);

    /**
     * 占位符前缀: "${"
     */
    private static final String PLACEHOLDER_PREFIX = "${";
    /**
     * 占位符的后缀: "}"
     */
    private static final String PLACEHOLDER_SUFFIX = "}";

    private static String resolvePlaceholders(String text, Map<String, String> parameter) {
        if (parameter == null || parameter.isEmpty()) {
            return text;
        }
        StringBuilder buf = new StringBuilder(text);
        int startIndex = buf.indexOf(PLACEHOLDER_PREFIX);
        while (startIndex != -1) {
            int endIndex = buf.indexOf(PLACEHOLDER_SUFFIX, startIndex + PLACEHOLDER_PREFIX.length());
            if (endIndex != -1) {
                String placeholder = buf.substring(startIndex + PLACEHOLDER_PREFIX.length(), endIndex);
                int nextIndex = endIndex + PLACEHOLDER_SUFFIX.length();
                try {
                    String propVal = parameter.get(placeholder);
                    if (propVal != null) {
                        buf.replace(startIndex, endIndex + PLACEHOLDER_SUFFIX.length(), propVal);
                        nextIndex = startIndex + propVal.length();
                    } else {
                        logger.error("Could not resolve placeholder '" + placeholder + "' in [" + text + "] ");
                    }
                } catch (Exception ex) {
                    logger.error("Could not resolve placeholder '" + placeholder + "' in [" + text + "]: " + ex);
                }
                startIndex = buf.indexOf(PLACEHOLDER_PREFIX, nextIndex);
            } else {
                startIndex = -1;
            }
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        String aa = "我们都是好孩子,${num}说是嘛？ 我觉得${people}是傻蛋!";
        Map<String, String> map = new HashMap<>();
        map.put("num", "小二比");
        map.put("people", "卫强");
        System.out.println(PlaceholderUtils.resolvePlaceholders(aa, map));
    }
}
