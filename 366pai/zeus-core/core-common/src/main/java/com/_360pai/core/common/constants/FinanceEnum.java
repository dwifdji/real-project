package com._360pai.core.common.constants;

/**
 * @author xiaolei
 * @create 2018-10-03 15:08
 */
public class FinanceEnum {

    public enum AdjustedStatusEnum {
        CAN_WITHDRAW("10", "可提现"),
        PENDING("20", "待转账"),
        WITHDRAW("30", "已提现");

        private String key;
        private String value;

        AdjustedStatusEnum(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum WithdrawStatusEnum {
        PENDING("10", "待转账"),
        REFUSED("20", "已拒绝"),
        WITHDRAW("30", "已提现");

        private String key;
        private String value;

        WithdrawStatusEnum(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public static String getValueByKey(String key) {
            WithdrawStatusEnum[] values = WithdrawStatusEnum.values();
            for (WithdrawStatusEnum tmp : values) {
                if (tmp.getKey().equals(key)) {
                    return tmp.getValue();
                }
            }
            return null;
        }
    }

    public enum WithdrawAdminStatusEnum {
        PENDING("10", "待审核"),
        REFUSED("20", "拒绝"),
        WITHDRAW("30", "同意");

        private String key;
        private String value;

        WithdrawAdminStatusEnum(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public static String getValueByKey(String key) {
            WithdrawAdminStatusEnum[] values = WithdrawAdminStatusEnum.values();
            for (WithdrawAdminStatusEnum tmp : values) {
                if (tmp.getKey().equals(key)) {
                    return tmp.getValue();
                }
            }
            return null;
        }
    }
}
