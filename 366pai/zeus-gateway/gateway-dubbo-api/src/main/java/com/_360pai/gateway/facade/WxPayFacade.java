package com._360pai.gateway.facade;

import com._360pai.gateway.controller.req.wxpay.ScanPayReq;
import com._360pai.gateway.resp.wxpay.ScanPayResp;

/**
 * 描述：微信支付FACADE接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/28 9:43
 */
public interface WxPayFacade {


    /**
     * 微信扫码支付接口
     *
     * code : 000000 请求成功
     * WxPayResultEnums.SUCCESS_CODE.getCode()
     */
    ScanPayResp scanPay(ScanPayReq req) ;




}
