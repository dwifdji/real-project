package com.winback.core.commons.constants;

/**
 * @author xdrodger
 * @Title: ContractEnum
 * @ProjectName winback
 * @Description:
 * @date 2019/2/11 14:39
 */
public class ContractEnum {

    /**
     *
     *查询枚举类转换
     */
    public  enum PayType
    {
        ALI_PAY("1", "支付宝"),
        WX_PAY("2", "微信支付"),
        ;
        private final String key;
        private final String value;


        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        PayType(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过key得到值
         */
        public static String getValueByKey(String key) {
            for (PayType model : PayType.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    public enum PayStatus {
        /**
         * 初始状态
         */
        INIT(0, "待支付"),
        /**
         * 支付成功
         */
        SUCCESS(1, "已完成"),
        /**
         * 支付失败
         */
        FAIL(2, "已失效"),
        /**
         * 支付失败
         */
        CANCEL(3, "已取消");

        private final Integer key;
        private final String value;

        public Integer getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        PayStatus(Integer key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过key得到值
         */
        public static String getValueByKey(Integer key) {
            for (PayStatus model : PayStatus.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    /**
     * 发票类型
     * 纸质、电子
     */
    public enum InvoiceType {

        PAPER("PAPER", "纸质"),
        IMAGE("IMAGE", "电子");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        InvoiceType(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过key得到值
         */
        public static String getValueByKey(String key) {
            for (InvoiceType model : InvoiceType.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    public enum TitleType {
        /**
         * 初始状态
         */
        USER("user", "个人"),
        /**
         * 支付成功
         */
        COMPANY("company", "企业");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        TitleType(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (TitleType model : TitleType.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    public enum OrderSource {
        /**
         * 手机app
         */
        APP("APP", "APP"),
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

        OrderSource(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getValueByKey(String key) {
            for (OrderSource model : OrderSource.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }
}
