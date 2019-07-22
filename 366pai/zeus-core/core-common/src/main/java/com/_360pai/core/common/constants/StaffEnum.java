package com._360pai.core.common.constants;

/**
 * @author xdrodger
 * @Title: StaffEnum
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/24 13:16
 */
public class StaffEnum {

    public enum Status {
        /**
         * 禁用
         */
        DISABLED("0", "禁用"),
        /**
         * 启用
         */
        ENABLED("1", "启用");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        Status(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
