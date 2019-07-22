package com._360pai.core.common.constants;

/**
 * @author : whisky_vip
 * @date : 2018/8/17 11:18
 */
public class ActivityEnum {


    public enum Status {
        /**
         * 等待完善信息
         */
        PENDING("PENDING", "未发布"),
        /**
         * 机构审核
         */
        AGENCY_PENDING("AGENCY_PENDING", "等待机构审核"),
        /**
         * 机构拒绝
         */
        AGENCY_REJECT("AGENCY_REJECT", "机构审核拒绝"),
        /**
         * 平台审核
         */
        PLATFORM_REVIEWING("PLATFORM_REVIEWING", "等待平台审核"),
        /**
         * 平台拒绝
         */
        PLATFORM_REJECTED("PLATFORM_REJECTED", "平台审核拒绝"),
        /**
         * 平台通过
         */
        PLATFORM_APPROVED("PLATFORM_APPROVED", "待签协议"),
        /**
         * 已上线 : 发生在签署协议后
         */
        ONLINE("ONLINE", "上线"),
        /**
         * 委托人在签协议之前撤掉
         */
        CANCELLED("CANCELLED", "取消"),
        /**
         * 等待确认
         */
        UNCONFIRMED("UNCONFIRMED", "等待确认"),
        /**
         * 成交, 有订单生成
         */
        SUCCESS("SUCCESS", "成交"),

        /**
         * 失败, 流拍
         */
        FAILED("FAILED", "流拍");


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
        public static String getKeyByValue(String value) {
            for (Status model : Status.values()) {
                if (model.getKey().equals(value)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    public enum ActivityMode {
        /**
         * 增价 升价拍
         */
        ENGLISH("ENGLISH", "增价拍"),
        /**
         * 减价 降价拍模式
         */
        DUTCH("DUTCH", "减价拍"),
        /**
         * 暗标 一口价暗标
         */
        SEALED("SEALED", "一口价暗标"),

        /**
         * 明标 一口价明标
         */
        PUBLIC("PUBLIC", "一口价明标"),

        /**
         * 自由报价 自由出价 择优成交
         */
        FREE("FREE", "择优成交"),

        /**
         * 秒杀 限时秒杀
         */
        FLASH("FLASH", "限时秒杀");


        private final String key;
        private final String value;

        ActivityMode(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key.toString();
        }

        /**
         * 通过值得到key
         */
        public static String getKeyByValue(String value) {
            for (ActivityMode model : ActivityMode.values()) {
                if (model.getKey().equals(value)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    public enum OnlineStatus {
        /**
         * 即将开拍
         */
        UPCOMING("UPCOMING", "即将开拍"),
        /**
         * 正在拍卖
         */
        SALE("SALE", "正在拍卖"),
        /**
         * 成功
         */
        SUCCESS("SUCCESS", "成功"),
        /**
         * 成功
         */
        FAILED("FAILED", "已完成");


        private final String key;
        private final String value;

        OnlineStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key.toString();
        }

        /**
         * 通过值得到key
         */
        public static String getKeyByValue(String value) {
            for (OnlineStatus model : OnlineStatus.values()) {
                if (model.getKey().equals(value)) {
                    return model.value;
                }
            }
            return null;
        }

        public static String getKeyByDesc(String value) {
            for (OnlineStatus model : OnlineStatus.values()) {
                if (model.getValue().equals(value)) {
                    return model.key;
                }
            }
            return null;
        }
    }

    public enum AuctionStatus {
        /**
         * 未交保证金
         */
        NOT_DEPOSIT("NOT_DEPOSIT", "未交保证金"),
        /**
         * 缴纳保证金没开拍
         */
        DEPOSIT("DEPOSIT", "缴纳保证金且没开拍"),
        /**
         * 正在开拍
         */
        DEPOSIT_ONLINE("DEPOSIT_ONLINE", "正在开拍"),

        /**
         * 已成交
         */
        SUCCESS("SUCCESS", "已成交"),
        /**
         * 已结束
         */
        FAILED("FAILED", "已结束");

        private final String key;
        private final String value;

        AuctionStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key.toString();
        }

    }

    public enum ActivityBroadcastStatus {
        /**
         * 上拍
         */
        UP_FOR_AUCTION("UP_FOR_AUCTION", "上拍"),
        /**
         * 成交
         */
        IS_DEAL("IS_DEAL", "成交");

        private final String key;
        private final String value;

        ActivityBroadcastStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key.toString();
        }
    }

    /**
     * 联拍状态
     */
    public enum UnionStatus {
        /**
         * 未联拍
         */
        NOT_UNITED("0", "未联拍"),
        /**
         * 已联拍
         */
        UNITED("1", "已联拍");

        private final String key;
        private final String value;

        UnionStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key.toString();
        }
    }

    /**
     * 活动可见性
     */
    public enum VisibilityLevel {
        /**
         * 未联拍
         */
        PLATFORM_DEFAULT("PLATFORM_DEFAULT", "全部可见"),
        /**
         * 详情可见
         */
        PLATFORM_DETAIL("PLATFORM_DETAIL", "详情可见"),
        /**
         * 已联拍
         */
        PLATFORM_INVISIBLE("PLATFORM_INVISIBLE", "全部不可见");

        private final String key;
        private final String value;

        VisibilityLevel(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key.toString();
        }

        /**
         * 通过值得到key
         */
        public static String getKeyByValue(String value) {
            for (VisibilityLevel model : VisibilityLevel.values()) {
                if (model.getKey().equals(value)) {
                    return model.value;
                }
            }
            return null;
        }

        public static boolean contains(String code) {
            for (VisibilityLevel item : VisibilityLevel.values()) {
                if (item.getKey().equals(code)) {
                    return true;
                }
            }
            return false;
        }
    }
}
