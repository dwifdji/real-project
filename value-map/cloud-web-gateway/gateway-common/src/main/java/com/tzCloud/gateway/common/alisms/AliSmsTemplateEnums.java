package com.tzCloud.gateway.common.alisms;

/**
 *阿里现有短信模板枚举类
 */
public enum AliSmsTemplateEnums {


    SMS_SEND_CODE("SMS_133964282", "发送短信验证码"),//{"code":验证码字段,"duration":验证码有效时间}


    ;




    private String code;
    private String desc;

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

    AliSmsTemplateEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
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
