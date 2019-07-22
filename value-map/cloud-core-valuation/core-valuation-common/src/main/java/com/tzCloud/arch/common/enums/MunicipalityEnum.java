package com.tzCloud.arch.common.enums;

/**
 * @author xdrodger
 * @Title: CityEnum
 * @ProjectName zeus
 * @Description:
 * @date 2019/1/22 10:06
 */
public enum MunicipalityEnum {
    Beijing(1, "北京"),
    Shanghai(9, "上海"),
    Tianjin(2, "天津"),
    Chongqing(22, "重庆"),
    Hongkong(32, "香港"),
    Macau(33, "澳门");


    private Integer key;
    private String desc;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    MunicipalityEnum(Integer key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public static boolean contains(Integer key) {
        for (MunicipalityEnum item : MunicipalityEnum.values()) {
            if (item.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }
}
