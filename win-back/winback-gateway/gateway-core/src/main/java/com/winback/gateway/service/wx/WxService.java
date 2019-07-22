package com.winback.gateway.service.wx;
import com.winback.gateway.controller.req.wx.WXACodeUnLimitReq;
import com.winback.gateway.resp.wx.OpenIdResp;
import com.winback.gateway.resp.wxpay.WXACodeUnLimitResp;

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
     *获取问下openId
     */
    OpenIdResp getWxOpenId(String jsCode);


}
