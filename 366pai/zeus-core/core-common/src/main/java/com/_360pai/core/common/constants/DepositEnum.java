package com._360pai.core.common.constants;

/**
 * @author xdrodger
 * @Title: DepositEnum
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/6 15:04
 */
public class DepositEnum {
    /**
     * 保证金状态
     */
    public enum Status {
        /**
         * 锁定
         */
        LOCKED("LOCKED", "锁定"),
        /**
         * 保证金已退还
         */
        SEND_BACK("SEND_BACK", "保证金已退还"),
        /**
         * 保证金锁定支付或者打给了银行卡
         */
        TRANSFERRED("TRANSFERRED", "保证金锁定支付或者打给了银行卡"),
        /**
         * 释放
         */
        RELEASED("RELEASED", "释放"),
        /**
         * 保证金支付已经解锁
         */
        TRANSFER_RELEASED("TRANSFER_RELEASED", "保证金支付已经解锁"),
        /**
         * 线下保证金已缴纳，待确认
         */
        INIT("INIT", "待确认"),
        /**
         * 线下保证金已收到
         */
        OFFLINE_RECEIVED("OFFLINE_RECEIVED", "已收到"),
        /**
         * 线下已成交
         */
        OFFLINE_FINISHED("OFFLINE_FINISHED", "已成交"),
        /**
         * 线下保证金已退回
         */
        OFFLINE_RETURNED("OFFLINE_RETURNED", "已退回"),
        /**
         * 线下保证金已转账
         */
        OFFLINE_TRANSFERRED("OFFLINE_TRANSFERRED", "已转账");


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

    /**
     * 线下保证金
     */
    public enum OfflineAction {
        /**
         * 付款
         */
        PAYMENT("PAYMENT", "付款"),
        /**
         * 退还
         */
        REFUND("REFUND", "退还"),
        /**
         * 转账
         */
        TRANSFERRED("TRANSFERRED", "转账");


        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        OfflineAction(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getKeyByValue(String value) {
            for (OfflineAction model : OfflineAction.values()) {
                if (model.getKey().equals(value)) {
                    return model.value;
                }
            }
            return null;
        }
    }
}
