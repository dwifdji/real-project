package com.tzCloud.core.common.constants;

/**
 * @author xdrodger
 * @Title: SmsTypeEnum
 * @ProjectName zeus
 * @Description:
 * @date 2018/12/11 16:17
 */
public enum SmsType {

    // 平台
    PLATFORM_LOGIN("PLATFORM_LOGIN", false, "登录"),
    PLATFORM_REGISTER("PLATFORM_REGISTER", false, "注册"),
    PLATFORM_FORGET_PASSWORD("PLATFORM_FORGET_PASSWORD", false, "忘记密码"),

    ;

    private final String key;
    private final boolean needCaptcha;
    private final String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    SmsType(String key, boolean needCaptcha, String value) {
        this.key = key;
        this.needCaptcha = needCaptcha;
        this.value = value;
    }

    /**
     * 是否需要验证码
     */
    public static boolean isNeedCaptcha(String key) {
        for (SmsType model : SmsType.values()) {
            if (model.getKey().equals(key)) {
                return model.needCaptcha;
            }
        }
        return true;
    }
}
