package com._360pai.core.provider;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.service.WxPayService;
import com._360pai.core.service.WxService;
import com._360pai.gateway.controller.req.wx.WXACodeUnLimitReq;
import com._360pai.gateway.facade.WxFacade;
import com._360pai.gateway.resp.wx.MpUserListResp;
import com._360pai.gateway.resp.wx.OpenIdResp;
import com._360pai.gateway.resp.wx.MpUserInfoResp;
import com._360pai.gateway.resp.wx.TemplateResp;
import com._360pai.gateway.resp.wxpay.WXACodeUnLimitResp;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
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
    private  WxPayService wxPayService;


    @Autowired
    private WxService wxService;


    @Override
    public OpenIdResp getWxOpenId(String jsCode) {
        return wxPayService.getWxOpenId(jsCode,"applet");
    }


    @Override
    public WXACodeUnLimitResp getWXACodeUnLimit(WXACodeUnLimitReq req) {
        if(StringUtils.isEmpty(req.getScene())){
            return WXACodeUnLimitResp.failure(ApiCallResult.EMPTY.getCode(),ApiCallResult.EMPTY.getDesc());
        }
        return wxService.getWXACodeUnLimit(req);
    }


    @Override
    public OpenIdResp getCalculatorOpenId(String jsCode) {
        return wxPayService.getWxOpenId(jsCode,SystemConstant.WX_CHANNEL_KEY);
    }

    @Override
    public WXACodeUnLimitResp getCalculatorACodeUnLimit(WXACodeUnLimitReq req) {
        if(StringUtils.isEmpty(req.getScene())){
            return WXACodeUnLimitResp.failure(ApiCallResult.EMPTY.getCode(),ApiCallResult.EMPTY.getDesc());
        }

        req.setChannel(SystemConstant.WX_CHANNEL_KEY);

        return wxService.getWXACodeUnLimit(req);
    }

    @Override
    public TemplateResp sendDebtCalculatorBroadcastTemplateMsg(String openId, JSONObject params) {
        return wxService.sendDebtCalculatorBroadcastTemplateMsg(openId, params);
    }

    @Override
    public TemplateResp sendPrincipalInterestCalculatorBroadcastTemplateMsg(String openId, JSONObject params) {
        return wxService.sendPrincipalInterestCalculatorBroadcastTemplateMsg(openId, params);
    }

    @Override
    public MpUserInfoResp getMpUserInfo(String openId) {
        return wxService.getMpUserInfo(openId);
    }

    @Override
    public MpUserListResp getMpUserList(String nextOpenId) {
        return wxService.getMpUserList(nextOpenId);
    }
}
