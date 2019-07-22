package com.winback.gateway.service.sms;

import com.winback.arch.common.ResponseModel;
import com.winback.gateway.controller.req.alisms.FAliSmsSendReq;

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





    /**
     *
     *发送创蓝短信
     */
    ResponseModel send235Sms(FAliSmsSendReq req);

}
