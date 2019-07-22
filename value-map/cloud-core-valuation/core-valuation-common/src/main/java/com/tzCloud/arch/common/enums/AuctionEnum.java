package com.tzCloud.arch.common.enums;

public class AuctionEnum {

    /**
     *
     * 功能描述: 标的类型
     *
     */
    public enum TAG_CODE {
        CODE1("01", "美食"),
        CODE2("02", "酒店"),
        CODE3("03", "购物"),
        CODE4("04", "生活服务"),
        CODE5("05", "丽人"),
        CODE6("06", "旅游景点"),
        CODE7("07", "休闲娱乐"),
        CODE8("08", "运动健身"),
        CODE9("09", "教育培训"),
        CODE10("10", "文化传媒"),
        /*CODE11("11", "医疗"),
        CODE12("12", "汽车服务"),
        CODE13("13", "交通设施"),
        CODE14("14", "金融"),
        CODE15("15", "房地产"),
        CODE16("16", "公司企业"),
        CODE17("17", "政府机构"),
        CODE18("18", "出入口"),
        CODE19("19", "自然地物"),*/

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

        TAG_CODE(String key, String val) {
            this.key = key;
            this.val = val;
        }
    }




    public enum TAG_CODE_TWO {

        CODE11("11", "医疗"),
        CODE12("12", "汽车服务"),
        CODE13("13", "交通设施"),
        CODE14("14", "金融"),
        CODE15("15", "房地产"),
        CODE16("16", "公司企业"),
        CODE17("17", "政府机构"),
        CODE18("18", "出入口"),
        CODE19("19", "自然地物"),

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

        TAG_CODE_TWO(String key, String val) {
            this.key = key;
            this.val = val;
        }
    }



    /**
     *
     * 功能描述: 标的物状态
     *
     */
    public enum STATUS_CODE {
        CODE1("done", "已成交"),
        CODE2("failure", "流拍"),

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

        STATUS_CODE(String key, String val) {
            this.key = key;
            this.val = val;
        }



        //根据Key 获取描述
        public static String getVal(String key) {
            for (STATUS_CODE c : STATUS_CODE.values()) {
                if (c.getKey().equals(key)) {
                    return c.getVal();
                }
            }
            return null;
        }



    }





    /**
     *
     * 功能描述: 标的物类型
     *
     */
    public enum ASSET_TYPE {
        CODE1("1", "住宅用房"),
        CODE2("2", "商业用房"),
        CODE3("3", "工业用房"),
        CODE4("4", "其他用房"),
        CODE5("5", "土地"),

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

        ASSET_TYPE(String key, String val) {
            this.key = key;
            this.val = val;
        }



        //根据Key 获取描述
        public static String getVal(String key) {
            for (ASSET_TYPE c : ASSET_TYPE.values()) {
                if (c.getKey().equals(key)) {
                    return c.getVal();
                }
            }
            return null;
        }



    }


}
