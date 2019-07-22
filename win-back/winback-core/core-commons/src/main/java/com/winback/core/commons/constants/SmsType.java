package com.winback.core.commons.constants;

/**
 * @author xdrodger
 * @Title: SmsTypeEnum
 * @ProjectName zeus
 * @Description:
 * @date 2018/12/11 16:17
 */
public enum SmsType {

    // 应用app
    APP_LOGIN("APP_LOGIN", false, "登录"),
    APP_REGISTER("APP_REGISTER", false, "注册"),
    APP_FORGET_PASSWORD("APP_FORGET_PASSWORD", false, "忘记密码"),


    // 小程序
    APPLET_BIND_ACCOUNT("APPLET_BIND_ACCOUNT", false, "绑定账户"),

    // 管理后台
    ADMIN_LOGIN("ADMIN_LOGIN", false, "登录"),

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
