package com._360pai.seimi.commons;

public class AliPmEnum {

    /**
     *
     * 功能描述: 拍卖状态
     *
     */
    public enum Status {
        DOING("doing", "正在进行"),
        TODO("todo", "即将开始"),
        FAILURE("failure", "已结束"),
        BREAK("break", "中止"),
        REVOCATION("revocation", "撤回");

        private String key;
        private String val;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        Status(String key, String val) {
            this.key = key;
            this.val = val;
        }
    }




    /**
     *
     * 功能描述: 标的类型
     *
     */
    public enum TYPE_CODE {
        CODE1("56950002", "房产"),
        CODE2("56940002", "其他资产"),
        CODE3("56968001", "机动车"),
        CODE4("56972001", "土地"),
        CODE5("56936003", "机器设备"),

        CODE6("56950003", "股权"),
        CODE7("56970001", "无形资产"),
        CODE8("201272017", "通信设备"),
        CODE9("56956002", "债权"),
        CODE10("201290015", "奢侈品"),

        CODE11("201280014", "农贸产品"),
        CODE12("56968002", "加贸边角料"),
        CODE13("57746020", "林权"),
        CODE14("57734016", "船舶"),
        CODE15("57728018", "矿权"),
        CODE16("57758011", "工程"),

        ;

        private String key;
        private String val;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        TYPE_CODE(String key, String val) {
            this.key = key;
            this.val = val;
        }
    }











    public enum SF_TYPE_CODE {
        CODE1("50025969", "住宅用房"),
        CODE2("200782003", "商业用房"),
        CODE3("200788003", "工业用房"),
        CODE4("200798003", "其他用房"),
        CODE5("50025972", "机动车"),

        CODE6("200790004", "航空交通"),
        CODE7("125228021", "船舶"),
        CODE8("200794003", "其他交通"),
        CODE9("125088031", "股权"),
        CODE10("200816002", "债权"),

        CODE11("50025973", "林权"),
        CODE12("50025974", "矿权"),
        CODE13("50025970", "土地"),
        CODE14("50025975", "工程"),
        CODE15("200778005", "海域"),
        CODE16("200772003", "机器设备"),
        CODE17("50025971", "资产"),
        CODE18("122406001", "无形资产"),
        CODE19("200804006", "古玩字画"),
        CODE20("200808002", "珠宝首饰"),
        CODE21("50025976", "其他"),

        ;

        private String key;
        private String val;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        SF_TYPE_CODE(String key, String val) {
            this.key = key;
            this.val = val;
        }
    }







    public enum URL_STATUS {
        DONE("done", "已经跑了的url"),
        TODO("todo", "即将要跑"),
        ;

        private String key;
        private String val;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        URL_STATUS(String key, String val) {
            this.key = key;
            this.val = val;
        }
    }


}
