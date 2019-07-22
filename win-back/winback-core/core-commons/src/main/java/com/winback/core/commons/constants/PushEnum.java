package com.winback.core.commons.constants;

public class PushEnum {

    /**
     *
     *推送人的类型
     */
    public enum  PUSH_PERSON_TYPE{
        PARTY("1", "当事人"),
        LAWYER("2", "律师"),
        SERVICE("3", "平台人员"),
        MANAGE("4", "项目经理"),
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

        PUSH_PERSON_TYPE(String type, String typeDesc) {
            this.type = type;
            this.typeDesc = typeDesc;
        }


    }



    /**
     *
     *推送的消息来源
     */
    public enum  SOURCE{
        APP("1", "APP端"),
        ADMIN("2", "平台管理后台"),

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

        SOURCE(String type, String typeDesc) {
            this.type = type;
            this.typeDesc = typeDesc;
        }


    }



    /**
     *
     *推送业务类型
     */
    public enum  BUS_TYPE{
        CONNECT("CONNECT", "聊天沟通推送"),
        MESSAGE("MESSAGE", "平台消息推送"),
        CUSTOMER_SERVICE("CUSTOMER_SERVICE", "客服沟通推送"),

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

        BUS_TYPE(String type, String typeDesc) {
            this.type = type;
            this.typeDesc = typeDesc;
        }


    }




    /**
     *
     *消息类型 1 前端跳转到聊天室 2 前端跳转到消息中心
     */
    public enum  MSG_TYPE{
        CONNECT(1, "聊天沟通推送"),
        MESSAGE(2, "平台消息推送"),
        CUSTOMER_SERVICE(3, "客服沟通推送")
        ;
        private Integer type;

        private String typeDesc;

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getTypeDesc() {
            return typeDesc;
        }

        public void setTypeDesc(String typeDesc) {
            this.typeDesc = typeDesc;
        }

        MSG_TYPE(Integer type, String typeDesc) {
            this.type = type;
            this.typeDesc = typeDesc;
        }


    }






    /**
     *
     *个推返回code
     */
    public enum  RESP_CODE{
        OK("ok", "OK"),
        SUCCESSED_IGNORE("successed_ignore", "⽆效⽤户，消息丢弃"),
        TOKENMD5NOUSERS("TokenMD5NoUsers", "在系统中未查找到⽤户"),

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

        RESP_CODE(String type, String typeDesc) {
            this.type = type;
            this.typeDesc = typeDesc;
        }


    }





    /**
     *
     *推送业务类型
     */
    public enum  PUSH_TYPE{
        CONNECT("1", "跳转聊天室"),
        MESSAGE("2", "跳转消息中心"),
        MY("3", "标签栏-我的页面"),
        CASE_STEP("4", "标签栏-案件进展页面"),
        CASE_LIBRARY("5", "案件库详情"),
        SERVICE("6", "跳转客服聊天室"),

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

        PUSH_TYPE(String type, String typeDesc) {
            this.type = type;
            this.typeDesc = typeDesc;
        }


    }





    /**
     *
     *个推返回code
     */
    public enum  REBACK_STATUS{
        REPLIED("REPLIED", "已回复"),
        UNANSWERED("UNANSWERED", "等待回复");
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

        REBACK_STATUS(String type, String typeDesc) {
            this.type = type;
            this.typeDesc = typeDesc;
        }
    }


}
