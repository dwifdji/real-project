package com._360pai.core.common.constants;

/**
 * @author xiaolei
 * @create 2018-11-26 13:55
 */
public class FastwayEnum {

    public enum DisposeStatusEnum {
        waitting("10","等待审核"),
        access("20","通过"),
        deny("30","拒绝");

        private String key;
        private String desc;

        DisposeStatusEnum(String key, String desc) {
            this.key = key;
            this.desc = desc;
        }

        public String getKey() {
            return key;
        }

        public String getDesc() {
            return desc;
        }

        public static String getDescByKey(String key) {
            DisposeStatusEnum[] values = DisposeStatusEnum.values();
            for (DisposeStatusEnum tmp : values) {
                if (tmp.getKey().equals(key)) {
                    return tmp.getDesc();
                }
            }
            return "";
        }

        public static boolean containKey(String key) {
            DisposeStatusEnum[] values = DisposeStatusEnum.values();
            for (DisposeStatusEnum tmp : values) {
                if (tmp.getKey().equals(key)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class DisposeType {
        public static final String LAWYER     = "lawyer";
        public static final String LAW_OFFICE = "lawOffice";
    }

    public static class AgencyType {
        // 拍卖行
        public static final String AUCTION     = "auction";
    }

    public static class FundType {
        // 个人
        public static final String User     = "user";
        // 企业
        public static final String Company  = "company";
    }


    public static class Enviroment {
        public static final String ONLINE = "online";
        public static final String PRE = "pre";
        public static final String DEVE = "deve";
        public static final String LOCAL = "local";
    }

    public static class AdminLink {

        public static final String ONLINE_DOMAIN = "https://admin.360pai.com/#";
        public static final String PRE_DOMAIN    = "http://adminprev2.360pai.xyz:81/#";

        public static final String DISPOSE_DETAIL    = "/accounts/disposal_fastWay_applicate_detail?";
        public static final String AUCTION_DETAIL    = "/accounts/auction_house_hyperchannel_detail?";
        public static final String FUND_DETAIL       = "/accounts/fund_service_provider_company?";

    }
}
