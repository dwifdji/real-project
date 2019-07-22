package com._360pai.core.common.constants;

/**
 * @author xdrodger
 * @Title: ActivityServiceProviderEnum
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-05-21 13:38
 */
public class ActivityServiceProviderEnum {
    /**
     * 活动类型
     */
    public enum ActivityType {
        /**
         * 拍卖
         */
        Auction("auction", "拍卖"),
        /**
         * 招商
         */
        Enrolling("enrolling", "招商");

        private final String key;
        private final String value;

        ActivityType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key.toString();
        }


        public static boolean contains(String key) {
            for (ActivityType item : ActivityType.values()) {
                if (item.getKey().equals(key)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 服务商类型
     */
    public enum ProviderType {
        /**
         * 处置
         */
        Dispose("dispose", "处置"),
        /**
         * 处置
         */
        Fund("fund", "资金");

        private final String key;
        private final String value;

        ProviderType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key.toString();
        }


        public static boolean contains(String key) {
            for (ProviderType item : ProviderType.values()) {
                if (item.getKey().equals(key)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 服务商类型
     */
    public enum Source {
        /**
         * 主站
         */
        Web("web", "主站"),
        /**
         * 管理后台
         */
        Admin("admin", "管理后台");

        private final String key;
        private final String value;

        Source(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key.toString();
        }


        public static boolean contains(String key) {
            for (Source item : Source.values()) {
                if (item.getKey().equals(key)) {
                    return true;
                }
            }
            return false;
        }
    }
}
