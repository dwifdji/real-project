package com.tzCloud.gateway.facade;

import com.tzCloud.gateway.controller.req.alisms.FAliSmsSendReq;
import com.tzCloud.gateway.resp.AliSendSmsResp;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 15:30
 */
public interface AliSmsFacade {

    /**
     * 000 发送成功
     * ApiCallResult.SUCCESS.getCode()
     * @param req 发送阿里短信请求接口
     * @time 2018/8/7 14:41
     */
    AliSendSmsResp sendSms(FAliSmsSendReq req);
}
