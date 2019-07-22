package com._360pai.core.service;

import com._360pai.core.model.sms.AliSmsSendReq;
import com._360pai.gateway.resp.AliSendSmsResp;

/**
 * 描述：阿里短信服务接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 14:41
 */
public interface AliSmsService {

    /**
     *
     * @param req 发送短信手机号码

     * @time 2018/8/7 14:41
\     */
    AliSendSmsResp sendSms(AliSmsSendReq req);
}
