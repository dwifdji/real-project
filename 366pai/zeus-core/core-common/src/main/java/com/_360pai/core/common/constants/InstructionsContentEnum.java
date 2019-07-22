package com._360pai.core.common.constants;

/**
 * @author xdrodger
 * @Title: InstructionsContentEnum
 * @ProjectName zeus
 * @Description:
 * @date 2019/1/7 16:27
 */
public class InstructionsContentEnum {
    public enum Status {
        /**
         * 转让类竞拍公告
         */
        SELL_AUCTION("SELL_AUCTION", "转让类竞拍公告"),
        /**
         * 服务类竞拍公告
         */
        SERVICE_AUCTION("SERVICE_AUCTION", "服务类竞拍公告"),
        /**
         * 竞买公告
         */
        AUCTION_MUST_KNOW("AUCTION_MUST_KNOW", "竞买公告"),
        /**
         * 报名类公告
         */
        ENROLLING_AUCTION("ENROLLING_AUCTION", "报名类公告"),
        /**
         * 小程序协议
         */
        APPLET_AGREEMENT("APPLET_AGREEMENT", "小程序协议");

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
        public static String getValueByKey(String key) {
            for (Status model : Status.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }
}
