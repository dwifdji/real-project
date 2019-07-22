package com._360pai.core.common.constants;

/**
 * @author xiaolei
 * @create 2018-09-17 18:59
 */
public class DisposalEnum {

    public static final long pay_expired_timeout = 60 * 10;


    public enum BiddingStatus {
        BIDDINGING("10", "投标中"),
        DONE("20", "流标"),
        SUCCESS("30", "竞标成功");

        private String key;
        private String value;

        BiddingStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum RequirementStatus {
        //00、未支付\r\n10、等待平台审核\r\n11、审核不通过\r\n12、处置中\r\n20、已完成\r\n30、撮合成功\r\n
        /**
         * 等待平台审核
         */
        WAITING_PASS("等待平台审核", "1"),
        /**
         * 审核不通过
         */
        NO_PASS("审核不通过", "2"),
        /**
         * 审核通过 待支付
         */
        PASS_FOR_PAY("审核通过-待支付", "3"),
        /**
         * 审核通过 未支付
         */
        PAY_EXPIRED("已失效", "4"),
        /**
         * 支付完成 初冾中
         */
        BEGINNING("招标中", "12"),
        /**
         * 已完成
         */
        FINISH("流标", "20"),
        /**
         * 撮合成功
         */
        SUCCESS("竞标成功", "30");

        private final String  key;
        private final String value;

        RequirementStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public static String getKeyByValue(String value) {
            for (RequirementStatus model : RequirementStatus.values()) {
                if (model.getValue().equals(value)) {
                    return model.key;
                }
            }
            return null;
        }
    }

    public enum RequirementType {
        JINDIAO("10", "尽职调查"),
        PINGGU("11", "资产评估"),
        SIFACHUZHI("12", "司法处置"),
        ZHIXINGCHUZHI("13", "执行处置"),
        QINGFANG("14", "清房"),
        CUISHOU("15", "催收"),
        CHAZHAOCANCHANXIANSUO("16", "查找财产线索");

        RequirementType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        private String  key;
        private String value;

        public static boolean isCodeExist(String key) {
            RequirementType[] values = RequirementType.values();
            for (RequirementType tmp : values) {
                if (tmp.getKey().equals(key)) {
                    return true;
                }
            }
            return false;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        /**
         * 通过值得到key
         */
        public static String getDescByKey(String key) {
            for (RequirementType model : RequirementType.values()) {
                if (model.getKey().equals(key)) {
                    return model.value;
                }
            }
            return null;
        }
    }

    public enum DisposeType {
        /**
         * 律师事务所
         */
        LAW_OFFICE("10", "律师事务所"),
        /**
         * 评估机构
         */
        EVALUATE_AGENCY("20", "评估机构"),
        /**
         * 个人
         */
        LAWYER("30", "律师");

        private final String key;
        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        DisposeType(String key, String value) {
            this.key = key;
            this.value = value;
        }
        public static String getValueByKey(String key) {
            for (DisposeType tmp : DisposeType.values()) {
                if (tmp.getKey().equals(key)) {
                    return tmp.getValue();
                }
            }
            return null;
        }
    }


    public enum SurveyStatus {
        PENDING_ACCESS("00","待接受"),
        PENDING_UPLOAD("10","待上传"),
        UPLOADED("20","已上传"),
        EXPIRED("30","已失效");

        private String key;
        private String value;

        SurveyStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public static String getValueByKey(String key) {
            SurveyStatus[] values = SurveyStatus.values();
            for (SurveyStatus tmp : values) {
                if (tmp.getKey().equals(key)) {
                    return tmp.getValue();
                }
            }
            return null;
        }
    }

}
