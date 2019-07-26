package com._360pai.crawler.commons;

public class CaaPmEnum {



    /**
     *
     * 功能描述: 拍卖状态
     *
     */
    public enum Status {
        FAILURE("0", "todo"),
        DEAL("1", "doing"),
        ONLINE("2", "failure"),
        WITHDRAW("3", "done");

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


        public static String getVal(String key) {

            for (Status enrolling_TYPE : Status.values()) {
                if(key.equals(enrolling_TYPE.getKey())) {
                    return enrolling_TYPE.getVal();
                }
            }
            return null;
        }

    }







    /**
     *
     * 功能描述: 拍卖状态
     *
     */
    public enum time {
        code1("1", "第一次拍卖"),
        code2("2", "第二次拍卖"),
        code3("3", "第三次拍卖"),
        code4("4", "第四次拍卖");

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

        time(String key, String val) {
            this.key = key;
            this.val = val;
        }


        public static String getVal(String key) {

            for (time enrolling_TYPE : time.values()) {
                if(key.equals(enrolling_TYPE.getKey())) {
                    return enrolling_TYPE.getVal();
                }
            }
            return null;
        }

    }




    /**
     *
     * 功能描述: 拍卖状态
     *
     */
    public enum typeName {
        code1("6", "6", "住宅用房"),
        code2("6", "26", "商业用房"),
        code3("6", "27", "工业用房"),
        code4("6", "28", "其他用房");

        private String key;
        private String val;
        private String val2;

        public String getVal2() {
            return val2;
        }

        public void setVal2(String val2) {
            this.val2 = val2;
        }

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

        typeName(String key, String val,String val2) {
            this.key = key;
            this.val = val;
            this.val2 = val2;

        }



    }





}
