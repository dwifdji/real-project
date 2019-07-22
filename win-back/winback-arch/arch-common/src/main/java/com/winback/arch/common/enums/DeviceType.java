package com.winback.arch.common.enums;

/**
 * 设备类型
 */
public enum  DeviceType {
    /**
     * 设备类型
     */
    ANDROID("0", "安卓"),
    IOS("1", "苹果"),
    WEB("2", "网站"),
    APPLET("3", "小程序");


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

    DeviceType(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

}
