package com._360pai.core.model.DfftPay;

/**
 * 描述：支付返回
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 16:20
 */
public class PayResp extends CommResp{

    private String payID;//支付id


    public String getPayID() {
        return payID;
    }

    public void setPayID(String payID) {
        this.payID = payID;
    }
}
