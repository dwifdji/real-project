package com._360pai.core.common.constants;

/**
 * 描述 订单枚举
 *
 * @author : whisky_vip
 * @date : 2018/9/12 15:16
 */
public class ServiceOrderEnum {
    // 尽调报告分润百分比
    public static final double ADJUSTED_RATE = 0.5;
    public static final double BASIS_REPORT_RATE = 0.5;
    public static final double COMPLETE_REPORT_RATE = 0.5;

    /**
     * 订单类型
     */
    public enum OrderType {

        /**
         * 资产大买办需求单
         */
        COMPRADOR_REQUIREMENT("资产大买办需求单", 10),
        /**
         * 配资乐需求单
         */
        WITHFUDIG_REQUIREMENT("配资乐需求单", 20),
        /**
         * 处置服务
         */
        DIPOSAL_REQUIREMENT("处置服务", 30),
        /**
         * 基础尽调
         */
        ADJUSTED("尽调报告", 31),
        /**
         * 基础尽调
         */
        REPORT_BASIS("基础尽调报告", 32),
        /**
         * 完整尽调
         */
        REPORT_COMPLETE("完整尽调报告", 33),
        /**
         * 我要处置
         */
        DISPOSAL_WANT_SHOW("我要处置", 34);

        private final String  key;
        private final Integer value;

        public String getKey() {
            return key;
        }

        public Integer getValue() {
            return value;
        }

        OrderType(String key, Integer value) {
            this.key = key;
            this.value = value;
        }

        public static String getKeyByValue(Integer value) {
            OrderType[] values = OrderType.values();
            for (OrderType tmp : values) {
                if (tmp.getValue().equals(value)) {
                    return tmp.getKey();
                }
            }
            return null;
        }
    }
}
