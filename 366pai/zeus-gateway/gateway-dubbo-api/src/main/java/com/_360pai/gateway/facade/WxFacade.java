package com._360pai.gateway.facade;

import com._360pai.gateway.controller.req.wx.WXACodeUnLimitReq;
import com._360pai.gateway.resp.wx.MpUserListResp;
import com._360pai.gateway.resp.wx.OpenIdResp;
import com._360pai.gateway.resp.wx.MpUserInfoResp;
import com._360pai.gateway.resp.wx.TemplateResp;
import com._360pai.gateway.resp.wxpay.WXACodeUnLimitResp;
import com.alibaba.fastjson.JSONObject;

/**
 * 描述：微信FACADE接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/11/23 9:43
 */
public interface WxFacade {


    /**
     * 获取微信openId
     *
     * jsCode : 登录时获取的 code
     *
     */
    OpenIdResp getWxOpenId(String jsCode);



    /**
     * 获取小程序二维码
     *
     * WXACodeUnLimitReq : 请求参数
     *
     */
    WXACodeUnLimitResp getWXACodeUnLimit(WXACodeUnLimitReq req);



    /**
     * 获取计算器 的openid
     *
     * jsCode : 登录时获取的 code
     *
     */
    OpenIdResp getCalculatorOpenId(String jsCode);



    /**
     * 获取计算器的二维码信息
     *
     * WXACodeUnLimitReq : 请求参数
     *
     */
    WXACodeUnLimitResp getCalculatorACodeUnLimit(WXACodeUnLimitReq req);

    /**
     * 发送债权计算器实时播报模版消息接口
     */
    TemplateResp sendDebtCalculatorBroadcastTemplateMsg(String openId, JSONObject params);

    /**
     * 发送本息计算器实时播报模版消息接口
     */
    TemplateResp sendPrincipalInterestCalculatorBroadcastTemplateMsg(String openId, JSONObject params);

    /**
     * 获取公众号用户信息
     */
    MpUserInfoResp getMpUserInfo(String openId);

    /**
     * 获取公众号用户列表接口
     */
    MpUserListResp getMpUserList(String nextOpenId);
}
