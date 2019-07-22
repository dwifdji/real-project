package com._360pai.core.common.constants;

/**
 * @author RuQ
 * @Title: AuctionOfflineEnum
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/4/29 9:26
 */
public class AuctionOfflineEnum {


    public enum ContractType {

        DEAL("deal", "成交"),
        LEASE("lease", "租賃");

        private final String key;
        private final String value;

        ContractType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getValueByKey(String key) {
            ContractType[] values = ContractType.values();
            for (ContractType status : values) {
                if (status.key.equals(key)) {
                    return status.value;
                }
            }
            return null;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }


    public enum FinanceType {

        REMAIN_AND_COMMISSION("REMAIN_AND_COMMISSION", "尾款加佣金"),
        COMMISSION("COMMISSION", "佣金");

        private final String key;
        private final String value;

        FinanceType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getValueByKey(String key) {
            FinanceType[] values = FinanceType.values();
            for (FinanceType status : values) {
                if (status.key.equals(key)) {
                    return status.value;
                }
            }
            return null;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }


    public enum ReceiveRemainType {

        SELF_RECEIVE("SELF_RECEIVE", "我方收取"),
        OTHER_RECEIVE("OTHER_RECEIVE", "三方收取");

        private final String key;
        private final String value;

        ReceiveRemainType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getValueByKey(String key) {
            ReceiveRemainType[] values = ReceiveRemainType.values();
            for (ReceiveRemainType status : values) {
                if (status.key.equals(key)) {
                    return status.value;
                }
            }
            return null;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum RoleType {

        BUYER("BUYER", "买受人"),
        SELLER("SELLER", "委托人");

        private final String key;
        private final String value;

        RoleType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getValueByKey(String key) {
            RoleType[] values = RoleType.values();
            for (RoleType status : values) {
                if (status.key.equals(key)) {
                    return status.value;
                }
            }
            return null;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ReceiveCommissionType {

        CONSISTENT("CONSISTENT", "一致"),
        OFFLINE_CONSULT("OFFLINE_CONSULT", "线下协商");

        private final String key;
        private final String value;

        ReceiveCommissionType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getValueByKey(String key) {
            ReceiveCommissionType[] values = ReceiveCommissionType.values();
            for (ReceiveCommissionType status : values) {
                if (status.key.equals(key)) {
                    return status.value;
                }
            }
            return null;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ConfirmStatus {

        CONFIRM("CONFIRM", "已确认"),
        NO_CONFIRM("NO_CONFIRM", "未确认");

        private final String key;
        private final String value;

        ConfirmStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getValueByKey(String key) {
            ConfirmStatus[] values = ConfirmStatus.values();
            for (ConfirmStatus status : values) {
                if (status.key.equals(key)) {
                    return status.value;
                }
            }
            return null;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

}
