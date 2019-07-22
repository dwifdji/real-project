package com.winback.gateway.facade;

import com.winback.arch.common.ResponseModel;
import com.winback.gateway.controller.req.email.EmailSendReq;
import com.winback.gateway.controller.req.email.GatewayEmailRecordReq;
import com.winback.gateway.controller.req.push.SinglePushReq;

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




    /**
     *
     *消息全部推送
     */
    ResponseModel pushMessageToAll(SinglePushReq req);





}
