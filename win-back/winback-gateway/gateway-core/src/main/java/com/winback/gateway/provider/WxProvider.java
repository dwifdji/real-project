package com.winback.gateway.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.gateway.service.pay.WxPayService;
import com.winback.gateway.service.wx.WxService;
import com.winback.gateway.controller.req.wx.WXACodeUnLimitReq;
import com.winback.gateway.facade.WxFacade;
import com.winback.gateway.resp.wx.OpenIdResp;
import com.winback.gateway.resp.wxpay.WXACodeUnLimitResp;
import com.winback.gateway.service.wx.WxService;
import org.apache.commons.lang.StringUtils;
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
public class WxProvider implements WxFacade {


    @Autowired
    private WxService wxService;


    @Override
    public OpenIdResp getWxOpenId(String jsCode) {
        return wxService.getWxOpenId(jsCode);
    }


    @Override
    public WXACodeUnLimitResp getWXACodeUnLimit(WXACodeUnLimitReq req) {
        if(StringUtils.isEmpty(req.getScene())){
            return WXACodeUnLimitResp.failure(ApiCallResult.EMPTY.getCode(),ApiCallResult.EMPTY.getDesc());
        }
        return wxService.getWXACodeUnLimit(req);
    }
}
