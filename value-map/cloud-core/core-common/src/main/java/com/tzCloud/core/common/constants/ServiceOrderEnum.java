package com.tzCloud.core.common.constants;

/**
 * 描述 订单枚举
 *
 * @author : whisky_vip
 * @date : 2018/9/12 15:16
 */
public class ServiceOrderEnum {

    /**
     * 订单类型
     */
    public enum OrderType {

        /**
         * 会员费用
         */
        MEMBERSHIP_MONTH_FEE("月会员开通费用", 10),
        MEMBERSHIP_YEAR_FEE("年会员开通费用", 20);

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
