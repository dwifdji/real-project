package com.tzCloud.core.constant;

import com.alibaba.dubbo.common.utils.StringUtils;

/**
 * @author xdrodger
 * @Title: CourtEnum
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019-04-26 10:22
 */
public class CourtEnum {
    public enum Type {
        Type_1("1", "基层法院"),
        Type_2("2", "中级法院"),
        Type_3("3", "高级法院"),
        Type_4("4", "最高法院");

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

        /**
         * 通过值得到key
         */
        public static String getKeyByValue(String value) {
            if (StringUtils.isBlank(value)) {
                return Type.Type_1.key;
            }
            for (Type model : Type.values()) {
                if (model.getValue().equals(value)) {
                    return model.key;
                }
            }
            return Type.Type_1.key;
        }
    }
}
