package com._360pai.core.common.constants;

/**
 * @author : whisky_vip
 * @date : 2018/8/17 10:47
 */
public class PartyEnum {
    public enum Type {
        /**
         * 个人
         */
        user,
        /**
         * 公司
         */
        company
    }

    public enum Category {
        /**
         * # 正常人
         */
        NORMAL_USER("NORMAL_USER", "正常人"),
        /**
         * 银行
         */
        BANK_COMPANY("BANK_COMPANY", "银行债权"),
        /**
         * AMC
         */
        AMC_COMPANY("AMC_COMPANY", "AMC债权"),
        /**
         * 民营资管
         */
        FOLK_ASSET_COMPANY("FOLK_ASSET_COMPANY", "民营资管"),
        /**
         * 普通公司
         */
        NORMAL_COMPANY("NORMAL_COMPANY", "一般公司"),
        /**
         * 拍卖公司
         */
        AUCTION_COMPANY("AUCTION_COMPANY", "拍卖机构"),
        /**
         * 处置服务商
         */
        DISPOSE_PROVIDER("DISPOSE_PROVIDER", "处置服务商"),
        /**
         * 处置服务商
         */
        FUND_PROVIDER("FUND_PROVIDER", "资金服务商"),
        /**
         * 交易所
         */
        EXCHANGE("EXCHANGE", "交易所"),

        LEASE_COMPANY("LEASE_COMPANY", "租赁权"),

        ;

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        Category(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getValueByKey(String key) {
            for (Category model : Category.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }

        public static String getFrontNameByKey(String key) {
            if (Category.NORMAL_USER.key.equals(key)) {
                return "个人";
            } else if (Category.BANK_COMPANY.key.equals(key)) {
                return "银行";
            } else if (Category.AMC_COMPANY.key.equals(key)) {
                return "AMC";
            } else if (Category.FOLK_ASSET_COMPANY.key.equals(key)) {
                return "民营资管";
            } else if (Category.AUCTION_COMPANY.key.equals(key)) {
                return "拍卖公司";
            } else if (Category.NORMAL_COMPANY.key.equals(key)) {
                return "一般公司";
            } else if (Category.DISPOSE_PROVIDER.key.equals(key)) {
                return "处置服务商";
            } else if (Category.FUND_PROVIDER.key.equals(key)) {
                return "资金服务商";
            }
            return "";
        }
    }

    public enum ApplySource {
        /**
         * 平台
         */
        PLATFORM("PLATFORM", "平台"),
        /**
         * 小程序
         */
        APPLET("APPLET", "小程序"),
        /**
         * 管理后台
         */
        ADMIN("ADMIN", "管理后台"),
        /**
         * 移动端
         */
        FASTWAY("FASTWAY", "快速通道");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        ApplySource(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getValueByKey(String key) {
            for (ApplySource model : ApplySource.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }
}
