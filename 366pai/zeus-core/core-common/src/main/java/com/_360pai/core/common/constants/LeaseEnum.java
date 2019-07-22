package com._360pai.core.common.constants;

/**
 *租赁权枚举
 */
public class LeaseEnum {




    public enum ApplyStatus {
        /**
         * 等待审核
         */
        PENDING("PENDING", "等待审核", "参拍资质待审核"),
        /**
         * 审核通过
         */
        APPROVED("APPROVED", "审核通过", "待交保证金参拍"),
        /**
         * 审核拒绝
         */
        REJECT("REJECT", "审核拒绝", "参拍资质已拒绝");

        private final String key;
        private final String value;
        private final String secondValue;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public String getSecondValue() {
            return secondValue;
        }

        ApplyStatus(String key, String value, String secondValue) {
            this.key = key;
            this.value = value;
            this.secondValue = secondValue;
        }


        public static String getDescByKey(String key) {
            ApplyStatus[] values = ApplyStatus.values();
            for (ApplyStatus tmp : values) {
                if (tmp.getKey().equals(key)) {
                    return tmp.getValue();
                }
            }
            return "";
        }


        public static String getSecondDescByKey(String key) {
            ApplyStatus[] values = ApplyStatus.values();
            for (ApplyStatus tmp : values) {
                if (tmp.getKey().equals(key)) {
                    return tmp.getSecondValue();
                }
            }
            return "";
        }
    }


    /**
     *
     * 在线中的banner的状态数据显示
     *
     */
    public enum LeaseBannerStatus {
        /**
         *  意向报名
         */
        INTENTION_REGISTRATION("INTENTION_REGISTRATION", "意向报名"),
        /**
         * 资质审核中……
         */
        QUALIFICATION_REVIEW("QUALIFICATION_REVIEW", "资质审核中……"),
        /**
         * 资质审核拒绝
         */
        QUALIFICATION_REVIEW_REJECT("QUALIFICATION_REVIEW_REJECT", "资质审核拒绝"),
        /**
         * 资质审核拒绝
         */
        LEASE_APPLY_END("LEASE_APPLY_END", "已过报名时间"),
        /**
         * 缴纳保证金参拍
         */
        PAYING_DEPOSIT("PAYING_DEPOSIT", "交纳保证金参拍");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        LeaseBannerStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }


        public static String getDescByKey(String key) {
            ApplyStatus[] values = ApplyStatus.values();
            for (ApplyStatus tmp : values) {
                if (tmp.getKey().equals(key)) {
                    return tmp.getValue();
                }
            }
            return null;
        }
    }
}
