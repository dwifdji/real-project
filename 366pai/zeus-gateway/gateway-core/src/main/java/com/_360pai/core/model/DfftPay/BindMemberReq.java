package com._360pai.core.model.DfftPay;

/**
 * 描述：绑定会员的请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 16:09
 */
public class BindMemberReq {

    private String memCode;//会员的code

    private String memName;//会员的名称

    private String payMemType;//会员的类型 1 企业用户 2 个人用户

    public String getMemCode() {
        return memCode;
    }

    public void setMemCode(String memCode) {
        this.memCode = memCode;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public String getPayMemType() {
        return payMemType;
    }

    public void setPayMemType(String payMemType) {
        this.payMemType = payMemType;
    }
}
