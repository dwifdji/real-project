package com._360pai.core.common.constants;

/**
 * @author xdrodger
 * @Title: SmsTypeEnum
 * @ProjectName zeus
 * @Description:
 * @date 2018/12/11 16:17
 */
public enum SmsType {

    SMS_REGISTER_TYPE("0", true, "注册"),
    SMS_LOGIN_TYPE("1", true, "登录"),
    SMS_FORGET_PWD_TYPE("2", true, "忘记密码"),
    SMS_MODIFY_PWD_TYPE("3", true, "修改密码"),
    SMS_MODIFY_MOBILE_TYPE("4", true, "修改手机号"),
    SMS_SPV_APPLY_TYPE("5", true, "SPV申请"),
    SMS_BIND_ACCOUNT_TYPE("6", false, "小程序绑定账户"),
    SMS_BIND_BANK_CARD_TYPE("7", false, "绑定银行卡"),
    SMS_WITHDRAW_DEPOSIT_TYPE("8", false, "提现"),
    SMS_UNBIND_BANK_CARD_TYPE("9", false, "解绑银行卡"),
    SMS_MODIFY_SHOP_CONTACT_PHONE("10", false, "修改店铺联系电话"),
    ADMIN_SMS_LOGIN_TYPE("admin_login", true, "管理后台登录"),
    AGENCY_SMS_LOGIN_TYPE("agency_login", true, "机构登录");

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
