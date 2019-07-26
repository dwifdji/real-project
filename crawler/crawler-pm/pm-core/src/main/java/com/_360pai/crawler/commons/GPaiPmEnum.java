package com._360pai.crawler.commons;

public class GPaiPmEnum {

    /**
     *
     * 功能描述: 拍卖状态
     *
     */
    public enum Status {
        FAILURE("113", "流标"),
        DEAL("114", "成交"),
        ONLINE("103", "上线"),
        WITHDRAW("125", "撤回");

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
     * 功能描述: 拍卖状态
     *
     */
    public enum type_name {
        type1("376", "房产"),
        type2("381", "土地");

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

        type_name(String key, String val) {
            this.key = key;
            this.val = val;
        }
    }





}
