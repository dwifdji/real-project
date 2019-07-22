package com._360pai.core.common.constants;

/**
 * @author xdrodger
 * @Title: PartyBlackListActionEnum
 * @ProjectName zeus
 * @Description:
 * @date 12/09/2018 18:38
 */
public enum PartyBlackListActionEnum {
    /**
     * 取消拉黑
     */
    RELEASE("RELEASE", 1),
    /**
     * 拉黑
     */
    LOCK("LOCK", 2);

    private final String key;
    private final Integer value;

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }

    PartyBlackListActionEnum(String key, Integer value) {
        this.key = key;
        this.value = value;
    }
}
