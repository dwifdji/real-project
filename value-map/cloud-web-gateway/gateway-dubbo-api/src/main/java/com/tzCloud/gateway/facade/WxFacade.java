package com.tzCloud.gateway.facade;

import com.tzCloud.gateway.controller.req.wx.WXACodeUnLimitReq;
import com.tzCloud.gateway.resp.wx.OpenIdResp;
import com.tzCloud.gateway.resp.wxpay.WXACodeUnLimitResp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

}
