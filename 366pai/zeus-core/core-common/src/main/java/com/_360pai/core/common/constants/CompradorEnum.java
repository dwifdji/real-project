package com._360pai.core.common.constants;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/8/31 10:55
 */
public class CompradorEnum {

    /**
     * 资产大买办 状态
     */
    public enum RequirementStatus {

        /**
         * 等待平台审核
         */
        WAITING_PASS("等待平台审核", 1),
        /**
         * 审核不通过
         */
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
         * 审核通过 置办中
         */
        PLACEMENT("置办中", 12),
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

    /**
     * 交易方式
     */
    public enum RequirementTransactionMode {

        /**
         * 债权
         */
        CREDITOR_RIGHT("债权", 10),
        /**
         * 物权
         */
        REAL_RIGHT("物权", 20),
        /**
         * 股权
         */
        STOCK_RIGHT("股权", 30);

        private final String  key;
        private final Integer value;

        public String getKey() {
            return key;
        }

        public Integer getValue() {
            return value;
        }

        RequirementTransactionMode(String key, Integer value) {
            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getKeyByValue(int value) {
            for (RequirementTransactionMode model : RequirementTransactionMode.values()) {
                if (model.getValue() == value) {
                    return model.key;
                }
            }
            return null;
        }

        public static void main(String[] args) {
            System.out.println(RequirementTransactionMode.getKeyByValue(10));
        }
    }

    /**
     * 建筑类型
     */
    public enum RequirementBuildingType {

        /**
         * 住宅
         */
        RESIDENCE("住宅", 10),
        /**
         * 商业
         */
        BUSINESS("商业", 20),
        /**
         * 厂房
         */
        FACTORY("厂房", 30),
        /**
         * 办公
         */
        OFFICE("办公", 40),
        /**
         * 其他
         */
        OTHER("其他", 50);

        private final String  key;
        private final Integer value;

        public String getKey() {
            return key;
        }

        public Integer getValue() {
            return value;
        }

        RequirementBuildingType(String key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 我有资产的推荐 状态
     */
    public enum RecommendStatus {

        /**
         * 推介中
         */
        RECOMMENDING("推介中", 10),
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

        RecommendStatus(String key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 资产大买办 对应的我有资产状态
     */
    public enum RecommenderStatus {

        /**
         * 推介中
         */
        RECOMMENDING("推介中", 10),
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

        RecommenderStatus(String key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }


}
