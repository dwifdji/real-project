package com.winback.gateway.service.push;

import com.winback.arch.common.ResponseModel;
import com.winback.gateway.controller.req.push.SinglePushReq;

/**
 * 描述：个推接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/16 9:59
 */
public interface GeTuiService {


    /**
     *
     *单体推送
     */
    ResponseModel pushMessageToSingle(SinglePushReq req);


    /**
     *
     *全体推送
     *
     */
    ResponseModel pushMessageToAll(SinglePushReq req);
}
