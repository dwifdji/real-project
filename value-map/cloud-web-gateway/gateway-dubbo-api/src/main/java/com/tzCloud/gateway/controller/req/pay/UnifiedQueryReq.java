package com.tzCloud.gateway.controller.req.pay;

import java.io.Serializable;

/**
 * 描述：统一支付请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/9/3 18:32
 */
public class UnifiedQueryReq implements Serializable {

    private String payType; //支付方式 PayEnums.PAY_TYPE  枚举

    private String payOrder;//查询订单号


    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayOrder() {
        return payOrder;
    }

    public void setPayOrder(String payOrder) {
        this.payOrder = payOrder;
    }
}
