package com._360pai.core.common.constants;

/**
 * @author xdrodger
 * @Title: OrderEnum
 * @ProjectName zeus
 * @Description:
 * @date 08/09/2018 16:34
 */
public class OrderEnum {

    public enum Status {
        /**
         * 未签协议
         */
        NOT_SIGNED("NOT_SIGNED", "待签协议"),
        NOT_SIGNED_LEASE("NOT_SIGNED_LEASE", "待签租赁协议"),
        /**
         * 未付款, 等待支付
         */
        NOT_PAID("NOT_PAID", "等待支付"),
        /**
         * 已付款,可发货
         */
        PAID("PAID", "等待发货"),
        /**
         * 已发货
         */
        DELIVERED("DELIVERED", "等待收货"),
        /**
         * 已收货
         */
        RECEIVED("RECEIVED", "完成"),
        /**
         * 终止
         */
        CLOSED("CLOSED", "终止");


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
         * 通过key得到value
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

    public enum OrderSource {
        /**
         * 平台
         */
        PLATFORM("PLATFORM", "平台"),
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
