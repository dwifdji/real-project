package com.tzCloud.gateway.facade;

import com.tzCloud.gateway.controller.req.pay.UnifiedPayReq;
import com.tzCloud.gateway.controller.req.pay.UnifiedPayResp;
import com.tzCloud.gateway.controller.req.pay.UnifiedQueryReq;
import com.tzCloud.gateway.controller.req.wxpay.ScanResultNoticeReq;

/**
 * 描述：统一支付接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/9/3 18:30
 */
public interface PayFacade {


    /**
     * 描述：统一支付接口
     *
     */
    UnifiedPayResp unifiedPay(UnifiedPayReq req);


    /**
     *
     *扫码支付结果通知接口
     * @param
     * @param
     */
    UnifiedPayResp scanResultNotice(ScanResultNoticeReq req);



    /**
     *
     *统一支付接口主动查询接口
     * @param
     * @param
     */
    UnifiedPayResp unifiedPayQuery(UnifiedQueryReq req);
}
