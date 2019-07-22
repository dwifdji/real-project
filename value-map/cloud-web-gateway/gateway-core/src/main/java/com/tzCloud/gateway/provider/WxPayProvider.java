package com.tzCloud.gateway.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.tzCloud.gateway.service.pay.WxPayService;
import com.tzCloud.gateway.controller.req.wxpay.AppPayReq;
import com.tzCloud.gateway.controller.req.wxpay.AppletPayReq;
import com.tzCloud.gateway.controller.req.wxpay.ScanPayReq;
import com.tzCloud.gateway.facade.WxPayFacade;
import com.tzCloud.gateway.resp.wxpay.AppPayResp;
import com.tzCloud.gateway.resp.wxpay.AppletPayResp;
import com.tzCloud.gateway.resp.wxpay.ScanPayResp;
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

}
