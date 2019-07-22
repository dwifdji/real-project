package com._360pai.core.common.constants;

/**
 * @author xdrodger
 * @Title: SmsEmailConfigEnum
 * @ProjectName zeus
 * @Description:
 * @date 2018/12/13 15:05
 */
public enum SmsEmailConfig {
    /**
     * 预招商
     */
    Enrolling("1", "预招商"),
    /**
     * 处置服务
     */
    Disposal("2", "处置服务"),
    /**
     * 配资乐
     */
    Withfudig("3", "配资乐"),
    /**
     * 资产大买办
     */
    Comprador("4", "资产大买办"),
    /**
     * 一级合伙人
     */
    Partner("6", "一级合伙人"),
    /**
     * 提现发送财务
     */
    Bus_Type_23("23", "提现发送财务"),
    /**
     * 小程序用户头像/昵称修改申请
     */
    Bus_Type_24("24", "小程序用户头像/昵称修改申请"),
    /**
     * 优质处置服务商推荐
     */
    Bus_Type_25("25", "优质处置服务商推荐");


    private final String key;
    /**
     * 默认value值
     */
    private final String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    SmsEmailConfig(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
