package com.tzCloud.gateway.service.sms;

import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.gateway.controller.req.alisms.FAliSmsSendReq;
import com.tzCloud.gateway.resp.AliSendSmsResp;

/**
 * 描述：短信接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/16 9:59
 */
public interface SmsService {


    /**
     *
     *发送短信
     */
    ResponseModel sendSms(FAliSmsSendReq req);




}
