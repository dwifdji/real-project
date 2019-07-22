package com._360pai.core.provider;

import com._360pai.core.service.WxPayService;
import com._360pai.gateway.controller.req.wxpay.ScanPayReq;
import com._360pai.gateway.facade.WxPayFacade;
import com._360pai.gateway.resp.wxpay.ScanPayResp;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 描述：微信支付接口实现类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 15:44
 */
@Component
@Service(version = "1.0.0")
public class WxPayProvider implements WxPayFacade {

    @Autowired
    private  WxPayService wxPayService;

    @Override
    public ScanPayResp scanPay(ScanPayReq req) {
        return wxPayService.scanPay(req);
    }

}
