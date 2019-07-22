package com.winback.gateway.controller.req.wxpay;

import java.io.Serializable;

/**
 * create by liuhaolei on 2018-01-22
 * 微信支付业务交互实体
 */
public class AppPayReq implements Serializable {

    /**
     * app商品描述
     */
    private String body;

    /**
     * app下的商户订单号
     */
    private String out_trade_no;

    /**
     * 订单总价
     */
    private String total_fee;

    /**
     * 产品id
     */
    private String product_id;

    /**
     * 微信支付订单id
     */
    private String transaction_id;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
