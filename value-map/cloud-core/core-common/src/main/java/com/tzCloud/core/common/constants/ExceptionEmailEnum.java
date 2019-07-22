package com.tzCloud.core.common.constants;

/**
 * 描述：异常邮件发送枚举类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/11/06 9:51
 */
public class ExceptionEmailEnum {
    /**
     * 系统模块类型
     */
    public enum MODULE_TYPE {

        AUCTION("AUCTION", "【拍卖模块】","10"),

        ENROLLING("ENROLLING", "【预招商模块】","11"),

        SERVICE("SERVICE", "【服务类模块】","12"),

        USER("USER", "【用户模块】","13"),

        PAY("PAY", "【支付模块】","14"),

        SIGNATURE("SIGNATURE", "【签章模块】","15"),

        TEMPLATE("TEMPLATE", "【模板模块】","16"),

        SMS("SMS", "【短信模块】","17"),

        OTHER("OTHER", "【其他模块】","18"),

        APPLET("APPLET", "【小程序模块】","17"),
        TRY_CATCH("TRY_CATCH", "【try/catch模块】","19");

        private final String key;
        private final String value;
        private final String type;

        public String getType() {
            return type;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        MODULE_TYPE(String key, String value,String type) {

            this.key = key;
            this.value = value;
            this.type = type;
        }

        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (MODULE_TYPE model : MODULE_TYPE.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    /**
     * 重要等级
     */
    public enum IMPORTANT_LEVEL {

        BREAK_DOWN("BREAK_DOWN", "【崩溃】"),

        SERIOUS("SERIOUS", "【严重】"),

        MINOR("MINOR", "【次要】"),

        OTHER("OTHER", "【其他】"),

        IGNORE("IGNORE", "【忽略】"),;


        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        IMPORTANT_LEVEL(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getKeyByValue(String value) {
            for (IMPORTANT_LEVEL model : IMPORTANT_LEVEL.values()) {
                if (model.getKey().equals(value)) {
                    return model.value;
                }
            }
            return null;
        }
    }
}