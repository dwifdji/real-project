package com.tzCloud.core.utils;

public class Constant {

    public static final String REGISTER_KEY = "pai";

    public static class DisposalCons {
        public static final String PT_PREFIX = "PT-";// 单号前缀
        public static final String CZ_PREFIX = "CZ-";
        public static final String TB_PREFIX = "TB-";
        public static final int NO_LENGTH = 8;// 单号位数
        public static final String DEFAULT_ORDER = "default_desc";
        public static final String COST_ASC = "cost_asc";
        public static final String COST_DESC = "cost_desc";
        public static final String PERIOD_ASC = "period_asc";
        public static final String PERIOD_DESC = "period_desc";
        public static final String FIRST_LEVEL = "10";// 一级合伙人标识
        public static final String NO_LEVEL = "00";// 默认等级合伙人

    }

    public static class DisposalStatus {
        public static final String BID_BIDDINGING = "10";
        public static final String BID_DONE = "20";
        public static final String BID_SUCCESS = "30";

        public static final String Require_PAY_NOT = "00";//未支付
        public static final String Require_VERIFY_WAIT = "10";//审核等待
        public static final String REQUIRE_VERIFY_NOT = "11";//审核未通过
        public static final String REQUIRE_VERIFY_OK = "12";//审核通过
        public static final String REQUIRE_DONE = "20";//已完成
        public static final String REQUIRE_SUCCESS = "30";//成功
    }

    public static class WithdrawCons {
        public static final String TX_PREFIX = "TX-";
        public static final int NO_LENGTH = 8;// 单号位数
    }


}
