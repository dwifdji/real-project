package com._360pai.core.common.constants;

/**
 * @author xdrodger
 * @Title: BackstageOperationEnum
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/22 14:56
 */
public class BackstageOperationEnum {
    public enum Type {
        /**
         * 未签协议
         */
        AUCTION_ACTIVITY("1", "拍卖活动"),
        /**
         * 未付款, 等待支付
         */
        ENROLLING_ACTIVITY("2", "预招商活动"),
        /**
         * 已付款,可发货
         */
        AUCTION_ORDER("3", "拍卖活动成交订单"),

        /**
         * 资产大买办 需求单审核
         */
        COMPRADOR_AUDIT("4", "大买办审核"),
        /**
         * 配资乐 需求单审核
         */
        WITHFUDIG_AUDIT("5", "配资乐审核"),
        /**
         * 处置服务 需求单审核
         */
        DISPOSAL_AUDIT("6", "处置服务审核");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        Type(String key, String value) {

            this.key = key;
            this.value = value;
        }

        /**
         * 通过key得到value
         */
        public static String getValueByKey(String key) {
            for (BackstageOperationEnum.Type model : BackstageOperationEnum.Type.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }
}
