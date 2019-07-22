package com._360pai.core.common.constants;

/**
 * 描述 配资乐 enum
 *
 * @author : whisky_vip
 * @date : 2018/9/7 16:37
 */
public class WithfudigEnum {
    /**
     * 配资乐 需求单 状态
     */
    public enum RequirementStatus {
        /**
         * 等待平台审核
         */
        WAITING_PASS("等待平台审核", 1),

        NO_PASS("审核不通过", 2),

        /**
         * 审核通过 待支付
         */
        PASS_FOR_PAY("审核通过-待支付", 3),

        /**
         * 审核通过 未支付
         */
        PAY_EXPIRED("已失效", 4),

        /**
         * 支付完成 初冾中
         */
        BEGINNING("初冾中", 12),
        /**
         * 补充资料
         */
        ADDITIONAL_MATERIALS("补充资料", 13),
        /**
         * 等待平台二次审核
         */
        WAITING_PASS_TWICE("等待平台二次审核", 14),
        /**
         * 配资中
         */
        WITHFUDIG_ING("配资中", 15),
        /**
         * 已完成
         */
        FINISH("已完成", 20),
        /**
         * 撮合成功
         */
        SUCCESS("撮合成功", 30);

        private final String  key;
        private final Integer value;

        public String getKey() {
            return key;
        }

        public Integer getValue() {
            return value;
        }

        RequirementStatus(String key, Integer value) {
            this.key = key;
            this.value = value;
        }

        public static String getKeyByValue(int value) {
            for (RequirementStatus model : RequirementStatus.values()) {
                if (model.getValue() == value) {
                    return model.key;
                }
            }
            return null;
        }
    }

    public enum InvestStatus {
        /**
         * 配置中
         */
        RECOMMENDING("配资中", 10),
        /**
         * 已完成
         */
        FINISH("已完成", 20),
        /**
         * 撮合成功
         */
        SUCCESS("撮合成功", 30);

        private final String  key;
        private final Integer value;

        public String getKey() {
            return key;
        }

        public Integer getValue() {
            return value;
        }

        InvestStatus(String key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }

    public enum AssetType {
//        assetType	是	string	标的类型 10 债权 20 物权 30 债权包
        /**
         * 债券
         */
        DEBT("单户债权", 10),
        /**
         * 物权
         */
        PROPERTY("标准物权配资", 20),
        /**
         * 债券包
         */
        DEBTPACKAGE("多户债权包", 30),
        /**
         * 债券包
         */
        JUDICIAL_AUCTION("司法拍卖垫资", 40);

        private final String  key;
        private final Integer value;

        public String getKey() {
            return key;
        }

        public Integer getValue() {
            return value;
        }

        AssetType(String key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }

    public enum CompanyType {
        ///**
        // * 国有企业
        // */
        //STATE_OWNED_ENTERPRISE("10", "国有企业"),
        ///**
        // * 有限责任公司
        // */
        //LIMITED_LIABLITY_COMPANY("20", "有限责任公司"),
        ///**
        // * 股份有限公司
        // */
        //LIMITED_COMPANY("30", "股份有限公司");

        /**
         * 国有控股企业
         */
        STATE_CONTROLLING_ENTERPRISE("50", "国有控股企业"),
        /**
         * 国有参股企业
         */
        STATE_SHARE_ENTERPRISE("60", "国有参股企业"),
        /**
         * 民营企业
         */
        PRIVATE_ENTERPRISE("70", "民营企业"),
        /**
         * 其他
         */
        OTHER_ENTERPRISE("80", "其他");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        CompanyType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static boolean contains(String code) {
            for (CompanyType item : CompanyType.values()) {
                if (item.getKey().equals(code)) {
                    return true;
                }
            }
            return false;
        }

        public static String getValueByKey(String key) {
            for (CompanyType model : CompanyType.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }
}
