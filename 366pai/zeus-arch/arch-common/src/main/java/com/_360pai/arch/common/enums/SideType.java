package com._360pai.arch.common.enums;

/**
 * @author xdrodger
 * @Title: SideType
 * @ProjectName zeus
 * @Description:
 * @date 21/09/2018 11:07
 */
public enum SideType {
    /**
     * 请求来源
     */
    UNKNOWN("UNKNOWN", "未知"),
    PLATFORM("PLATFORM", "平台网站"),
    ADMIN("ADMIN", "管理后台"),
    AGENCY("AGENCY", "机构后台"),
    AGENCY_JOINT("AGENCY_JOINT", "机构后台联拍展示");


    private String key;
    private String desc;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    SideType(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

   public enum Operate {
        /**
         * 操作方式
         */
        ADD("ADD", "添加"),
        EDIT("EDIT", "修改");


        private String key;
        private String desc;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        Operate(String key, String desc) {
            this.key = key;
            this.desc = desc;
        }
    }
}
