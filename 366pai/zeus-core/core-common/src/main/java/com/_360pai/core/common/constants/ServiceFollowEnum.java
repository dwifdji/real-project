package com._360pai.core.common.constants;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/17 11:22
 */
public class ServiceFollowEnum {
    /**
     * 关联类型
     */
    public enum RelativeType {

        /**
         * 资产大买办需求单
         */
        COMPRADOR("comprador", 10),
        /**
         * 配资乐需求单
         */
        WITHFUDIG("withfudig", 20),
        /**
         * 处置服务
         */
        DIPOSAL("diposal", 30);


        private final String  key;
        private final Integer value;

        public String getKey() {
            return key;
        }

        public Integer getValue() {
            return value;
        }

        RelativeType(String key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }
}
