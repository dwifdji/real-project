package com._360pai.crawler.commons;

/**
 * @author xdrodger
 * @Title: TaskConstants
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/10 14:30
 */
public class TaskConstants {

    public enum Type {
        Type_1("rmfygg", "人民法院公告");

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

    public enum Status {
        INIT("0", "新建"),
        DOING("1", "进行中"),
        FINISH("2", "完成"),
        FAILURE("3", "失败"),
        ;

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

        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (Status model : Status.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }
}
