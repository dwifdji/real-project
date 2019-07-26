package com._360pai.crawler.commons;

/**
 * @author xdrodger
 * @Title: CourtConstants
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/10 15:14
 */
public class CourtConstants {
    public enum Type {
        Type_1("基层法院", "基层法院"),
        Type_2("高级法院", "高级法院"),
        Type_3("最高法院", "最高法院");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        Type(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (Type model : Type.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }
}
