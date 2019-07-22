package com.winback.gateway.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.winback.gateway.service.pay.WxPayService;
import com.winback.gateway.controller.req.wxpay.AppPayReq;
import com.winback.gateway.controller.req.wxpay.AppletPayReq;
import com.winback.gateway.controller.req.wxpay.ScanPayReq;
import com.winback.gateway.facade.WxPayFacade;
import com.winback.gateway.resp.wxpay.AppPayResp;
import com.winback.gateway.resp.wxpay.AppletPayResp;
import com.winback.gateway.resp.wxpay.ScanPayResp;
import com.winback.gateway.service.pay.WxPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * create by liuhaolei on 2019-01-24
 * 微信支付生产者
 */
@Component
@Service(version = "1.0.0")
public class WxPayProvider implements WxPayFacade {

    @Autowired
    private WxPayService wxPayService;

    @Override
    public ScanPayResp scanPay(ScanPayReq req) {

        return wxPayService.scanPay(req);
    }

    @Override
    public AppletPayResp appletPay(AppletPayReq appletPayReq) {
        return wxPayService.appletPay(appletPayReq);
    }


    @Override
    public AppPayResp appPay(AppPayReq appPayReq) {
        return wxPayService.appPay(appPayReq);
    }

    @Override
    public AppPayResp queryAppPay(AppPayReq appPayReq) {

        return wxPayService.queryAppPay(appPayReq);
    }


}
