package com.tzCloud.core.constant;

/**
 * @author xdrodger
 * @Title: AccountEnum
 * @ProjectName zeus
 * @Description:
 * @date 11/09/2018 15:36
 */
public class AccountEnum {

    public enum RegisterSource {
        /**
         * 平台
         */
        PLATFORM("PLATFORM", "APP"),
        /**
         * 手机app
         */
        APP("APP", "APP"),
        /**
         * 小程序
         */
        APPLET("APPLET", "小程序"),
        /**
         * 网页
         */
        H5("WEB", "网页");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        RegisterSource(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (RegisterSource model : RegisterSource.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    public enum ApplyStatus {
        /**
         * 未申请
         */
        NO_APPLY("NO_APPLY", "未申请"),
        /**
         * 等待审核
         */
        PENDING("PENDING", "等待审核"),
        /**
         * 审核通过
         */
        APPROVED("APPROVED", "审核通过"),
        /**
         * 审核拒绝
         */
        REJECT("REJECT", "审核拒绝");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        ApplyStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (ApplyStatus model : ApplyStatus.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    /**
     * 账户外部关系类型
     */
    public enum ExtType {
        /**
         * 小程序
         */
        APPLET("APPLET", "小程序");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        ExtType(String key, String value) {

            this.key = key;
            this.value = value;
        }
    }

}
