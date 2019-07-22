package com.winback.gateway.common.wxpay;

/**
 * 微信支付返回枚举类
 */
public enum WxPayResultEnums {
    RETURN_CODE_SUCCESS("SUCCESS", "请求成功"),

    SUCCESS_CODE("000000","请求成功"),

    FAIL_CODE("100000","请求失败"),

    RESP_SIGN_ERROR("100001","返回参数验签失败"),

    SYS_EXCEPTION("999999","支付系统异常，请稍后重试");

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

    //根据code 获取描述
    public static String getDesc(String code) {
        for (WxPayResultEnums c : WxPayResultEnums.values()) {
            if (c.getCode().equals(code)) {
                return c.getDesc();
            }
        }
        return null;
    }

    WxPayResultEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
