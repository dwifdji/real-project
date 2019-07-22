package com.winback.arch.common.enums;

/**
 * @author xdrodger
 * @Title: SideType
 * @ProjectName winback
 * @Description:
 * @date 21/09/2018 11:07
 */
public enum SideType {
    /**
     * 请求来源
     */
    UNKNOWN("UNKNOWN", "未知"),
    ADMIN("ADMIN", "管理后台"),
    APP("APP", "手机app"),
    APPLET("APPLET", "小程序");


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
