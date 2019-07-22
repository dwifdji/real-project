package com.tzCloud.gateway.service.pay;

import com.tzCloud.gateway.controller.req.wxpay.ScanPayReq;
import com.tzCloud.gateway.resp.wx.OpenIdResp;
import com.tzCloud.gateway.resp.wxpay.ScanPayResp;

/**
 * 描述：微信支付服务类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/16 9:59
 */
public interface WxPayService {

    /**
     * 微信扫码支付接口
     *
     * @param
     * @param
     */
    ScanPayResp scanPay(ScanPayReq req) ;


    /**
     * 微信扫码支付查询接口
     *
     * @param
     * @param
     */
    ScanPayResp queryScanPayResult(String outTradeNo,String channel) ;
}
