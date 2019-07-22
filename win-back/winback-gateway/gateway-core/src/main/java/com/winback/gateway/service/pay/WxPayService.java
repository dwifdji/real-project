package com.winback.gateway.service.pay;

import com.winback.gateway.controller.req.wxpay.AppPayReq;
import com.winback.gateway.controller.req.wxpay.AppletPayReq;
import com.winback.gateway.controller.req.wxpay.ScanPayReq;
import com.winback.gateway.resp.wxpay.AppPayResp;
import com.winback.gateway.resp.wxpay.AppletPayResp;
import com.winback.gateway.resp.wxpay.ScanPayResp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     * 微信小程序支付接口
     *
     */
    AppletPayResp appletPay(AppletPayReq req) ;


    /**
     * 微信扫码支付查询
     *
     * @param
     * @param
     */
    ScanPayResp queryScanPayResult(String outTradeNo) ;

    /**
     *
     *  微信app支付
     * @return
     */
    AppPayResp appPay(AppPayReq appPayReq);


    /**
     *
     *  微信app支付订单查询
     * @return
     */
    AppPayResp queryAppPay(AppPayReq appPayReq);

}
