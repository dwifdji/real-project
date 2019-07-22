package com._360pai.core.common.constants;

/**
 * @author xdrodger
 * @Title: AgencyEnum
 * @ProjectName zeus
 * @Description:
 * @date 16/09/2018 16:11
 */
public class AgencyEnum {

    public enum AgencyPortalStatus {
        /**
         * 等待审核
         */
        PENDING("PENDING", "等待审核"),
        /**
         * 上线
         */
        ONLINE("ONLINE", "上线"),
        /**
         * 关闭
         */
        CLOSED("CLOSED", "关闭");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        AgencyPortalStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /**
         * 通过值得到key
         */
        public static String getKeyByValue(String value) {
            for (AgencyPortalStatus model : AgencyPortalStatus.values()) {
                if (model.getKey().equals(value)) {
                    return model.value;
                }
            }
            return null;
        }
    }



    public enum AgencyApplyStatus {
        /**
         * 等待审核
         */
        PENDING("PENDING", 1),
        /**
         * 审核通过
         */
        APPROVED("APPROVED", 2),
        /**
         * 审核拒绝
         */
        REJECT("REJECT", 3);

        private final String key;
        private final Integer value;

        public String getKey() {
            return key;
        }

        public Integer getValue() {
            return value;
        }

        AgencyApplyStatus(String key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }
}
