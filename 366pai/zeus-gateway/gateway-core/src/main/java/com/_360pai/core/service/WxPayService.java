package com._360pai.core.service;

import com._360pai.gateway.controller.req.wxpay.AppletPayReq;
import com._360pai.gateway.controller.req.wxpay.ScanPayReq;
import com._360pai.gateway.resp.wx.OpenIdResp;
import com._360pai.gateway.resp.wxpay.AppletPayResp;
import com._360pai.gateway.resp.wxpay.ScanPayResp;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/28 11:03
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
     * 微信小程序支付接口
     *
     */
    AppletPayResp appletPay(AppletPayReq req) ;




    /**
     * 微信扫码支付接口
     *
     * @param
     * @param
     */
    ScanPayResp queryScanPayResult(String outTradeNo,String channel) ;


    /**
     *获取问下openId
     *
     *
     */
    OpenIdResp getWxOpenId(String jsCode,String channel);
}
