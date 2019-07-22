package com.tzCloud.core.common.constants;

/**
 * 描述 serviceConfig enum
 * value为默认提供值
 *
 * @author : whisky_vip
 * @date : 2018/9/6 18:45
 */
public enum ServiceConfigEnum {

    MEMBERSHIP_GENERAL_FEE("membership_general_fee","0"),
    MEMBERSHIP_MONTHLY_FEE("membership_monthly_fee","199"),
    MEMBERSHIP_ANNUAL_FEE("membership_annual_fee","1999");

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

    ServiceConfigEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static boolean contansKey(String key) {
        ServiceConfigEnum[] values = ServiceConfigEnum.values();
        for (ServiceConfigEnum serviceConfigEnum : values) {
            if (serviceConfigEnum.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public static String getValueByKey(String key) {
        ServiceConfigEnum[] values = ServiceConfigEnum.values();
        for (ServiceConfigEnum serviceConfigEnum : values) {
            if (serviceConfigEnum.getKey().equals(key)) {
                return serviceConfigEnum.getValue();
            }
        }
        return null;
    }


}
