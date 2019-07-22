package com.winback.gateway.common.alisms;

/**
 *阿里现有短信模板枚举类
 */
public enum AliSmsTemplateEnums {


    SMS_SEND_CODE("SMS_133964282", "发送短信验证码","您的验证码是${code}，请在5分钟内使用，切勿泄露。"),//{"code":验证码字段,"duration":验证码有效时间}

    LOGIN_CODE("SMS_160480083", "登陆验证码","您的登录验证码是${code}（5分钟内有效），请勿泄漏给他人。"),//{"code":验证码字段"}

    REGISTER_CODE("SMS_160490051", "注册验证码","您手机注册验证码为：${code}，请不要把验证码泄漏给其他人，如非本人请勿操作。"),//{"code":验证码字段"}

    RETRIEVE_PASSWORD_CODE("SMS_160480086", "找回密码","您正在找回密码，验证码为：${code}请不要把验证码泄露给其他人。如非本人操作，请忽略该短信。"),//{"code":验证码字段"}

    DEFAULT_PASSWORD_CODE("SMS_160480090", "默认密码","您已成功注册，同时为您开通账户，默认密码为${default_password}。"),//{"default_password":密码"}

    LAWYER_APPLY_CODE("SMS_164665699", "用户提交律师资质审核，推送对象-后台人员","已有用户申请律师资质，请尽快审核。"),//{}

    ;




    private String code;
    private String desc;
    private String template;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    AliSmsTemplateEnums(String code, String desc,String template) {
        this.code = code;
        this.desc = desc;
        this.template = template;

    }



    public static String getTemplate(String code) {
        for (AliSmsTemplateEnums e : AliSmsTemplateEnums.values()) {
            if(e.getCode().equals(code)) {
                return e.getTemplate();
            }
        }
        return null;
    }



    public static String getDesc(String code) {
        for (AliSmsTemplateEnums e : AliSmsTemplateEnums.values()) {
            if(e.getCode().equals(code)) {
                return e.getDesc();
            }
        }
        return null;
    }
}
