package com.winback.core.commons.constants;

public class RiskEnum {

    /**
     *
     *查询记录的类型
     */
    public enum  RECORD_TYPE{
        COM_INFO("1", "公司信息"),
        LAWYER("2", "律师"),
        SERVICE("3", "平台人员"),
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

        RECORD_TYPE(String type, String typeDesc) {
            this.type = type;
            this.typeDesc = typeDesc;
        }


    }





}
