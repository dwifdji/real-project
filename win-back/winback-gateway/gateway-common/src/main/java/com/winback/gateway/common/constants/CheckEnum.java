package com.winback.gateway.common.constants;

public class CheckEnum {

    /**
     *
     *企查查接口类型定义
     */
    public enum  API_TYPE{
        COM_DETAIL_INFO("1", "公司的基本信息"),
        COM_EXCEPTION_INFO("2", "经营异常接口"),
        COM_ZHI_XING("3", "执行接口"),
        COM_SHI_XIN("4", "失信执行接口"),
        COM_JUDGMENT("5", "裁判文书信息"),
        SEARCH_INVESTMENT("6", "对外投资信息"),
        STOCK_ANALYSIS_DATA("7", "股权十层穿透接口"),
        ;
        private String type;

        private String typeDesc;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeDesc() {
            return typeDesc;
        }

        public void setTypeDesc(String typeDesc) {
            this.typeDesc = typeDesc;
        }

        API_TYPE(String type, String typeDesc) {
            this.type = type;
            this.typeDesc = typeDesc;
        }


    }






}
