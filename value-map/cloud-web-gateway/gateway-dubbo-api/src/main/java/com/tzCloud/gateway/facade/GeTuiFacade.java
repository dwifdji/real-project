package com.tzCloud.gateway.facade;

import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.gateway.controller.req.email.EmailSendReq;
import com.tzCloud.gateway.controller.req.email.GatewayEmailRecordReq;
import com.tzCloud.gateway.controller.req.push.SinglePushReq;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 15:30
 */
public interface GeTuiFacade {


    /**
     *
     *单体推送
     */
    ResponseModel pushMessageToSingle(SinglePushReq req);










}
