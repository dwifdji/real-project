package com.winback.gateway.facade;

import com.winback.gateway.controller.req.pay.PayReq;
import com.winback.gateway.controller.req.pay.PayResp;
import com.winback.gateway.controller.req.pay.QueryReq;

/**
 * 描述：统一支付接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/28 9:43
 */
public interface PayFacade {




    /**
     *
     *统一支付接口
     * @param
     * @param
     */
    PayResp unifyPay(PayReq req);



    /**
     *
     *统一查询接口
     * @param
     * @param
     */
    PayResp query(QueryReq req);



    /**
     *
     *支付补偿接口
     * @param
     * @param
     */
    void quartzQueryOrder();




}
