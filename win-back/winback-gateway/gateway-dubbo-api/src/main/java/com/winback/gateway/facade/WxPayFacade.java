package com.winback.gateway.facade;

import com.winback.gateway.controller.req.wxpay.AppPayReq;
import com.winback.gateway.controller.req.wxpay.AppletPayReq;
import com.winback.gateway.controller.req.wxpay.ScanPayReq;
import com.winback.gateway.resp.wxpay.AppPayResp;
import com.winback.gateway.resp.wxpay.AppletPayResp;
import com.winback.gateway.resp.wxpay.ScanPayResp;

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


    /**
     * 小程序微信支付
     * @param appletPayReq
     * @return
     */
    AppletPayResp appletPay(AppletPayReq appletPayReq);


    /**
     * app微信支付
     * @param appPayReq
     * @return
     */
    AppPayResp appPay(AppPayReq appPayReq);


    /**
     * 查询订单支付信息
     * @param appPayReq
     * @return
     */
    AppPayResp queryAppPay(AppPayReq appPayReq);
}
