package com.winback.core.commons.constants;

public class SystemSiteEnum {


    public enum  CaseSiteType{
        CASE_PHASE("1", "案件阶段"),
        CAPITAL_USES("2", "资金用途");


        private String type;

        private String typeDesc;

        CaseSiteType(String type, String typeDesc) {
            this.type = type;
            this.typeDesc = typeDesc;
        }

        public static String getDescByType(String type) {
            CaseSiteType[] values = CaseSiteType.values();
            for (CaseSiteType caseSiteType:values) {
                if(caseSiteType.type.equals(type)) {
                    return  caseSiteType.typeDesc;
                }
            }
            return null;
        }
    }
}
