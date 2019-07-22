package com.tzCloud.gateway.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.gateway.service.pay.WxPayService;
import com.tzCloud.gateway.service.wx.WxService;
import com.tzCloud.gateway.controller.req.wx.WXACodeUnLimitReq;
import com.tzCloud.gateway.facade.WxFacade;
import com.tzCloud.gateway.resp.wx.OpenIdResp;
import com.tzCloud.gateway.resp.wxpay.WXACodeUnLimitResp;
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
