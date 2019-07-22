package com._360pai.core.common.constants;

import lombok.Getter;

/**
 * @author xiaolei
 * @create 2018-11-06 16:46
 */
public class SurveyReportEnum {

    @Getter
    public enum ReportSourceEnum {
        NO_REPORT("00" ,"无尽调文件"),
        SELF("10", "自有"),
        THIRD("20", "第三方服务商");

        private String key;
        private String value;

        ReportSourceEnum(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getValueByKey(String key) {
            ReportSourceEnum[] values = ReportSourceEnum.values();
            for (ReportSourceEnum tmp :
                    values) {
                if (tmp.getKey().equals(key)) {
                    return tmp.getValue();
                }
            }
            return null;
        }
    }

    @Getter
    public enum ProtocolStatusEnum {
        NOT_SIGNED("00" ,"未签署尽调协议"),
        SIGNED("10", "已签署尽调协议"),
        CAN_NOT1("20", "请先签署委托协议"),
        CAN_NOT2("21", "已过签署尽调协议时间"),
        CAN_NOT3("22", "未达到代签协议状态");

        private String key;
        private String value;

        ProtocolStatusEnum(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

}
