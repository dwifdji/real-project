package com._360pai.core.service;

import com._360pai.gateway.controller.req.wx.WXACodeUnLimitReq;
import com._360pai.gateway.resp.wx.MpUserInfoResp;
import com._360pai.gateway.resp.wx.MpUserListResp;
import com._360pai.gateway.resp.wx.TemplateResp;
import com._360pai.gateway.resp.wxpay.WXACodeUnLimitResp;
import com.alibaba.fastjson.JSONObject;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/28 11:03
 */
public interface WxService {

    /**
     *
     *获取微信小程序二维码
     */
    WXACodeUnLimitResp getWXACodeUnLimit(WXACodeUnLimitReq req);


    /**
     * 发送债权计算器实时播报模版消息接口
     */
    TemplateResp sendDebtCalculatorBroadcastTemplateMsg(String openId, JSONObject params);

    /**
     * 发送本息计算器实时播报模版消息接口
     */
    TemplateResp sendPrincipalInterestCalculatorBroadcastTemplateMsg(String openId, JSONObject params);

    /**
     * 获取公众号用户信息接口
     */
    MpUserInfoResp getMpUserInfo(String openId);

    /**
     * 获取公众号用户列表接口
     */
    MpUserListResp getMpUserList(String nextOpenId);
}
