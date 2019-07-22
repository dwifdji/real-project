package com._360pai.gateway.facade;

import com._360pai.gateway.controller.req.dfftpay.*;
import com._360pai.gateway.controller.req.wxpay.ScanResultNoticeReq;

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
     *东方付通返回通知结果处理
     * @param
     * @param
     */
    UnifiedPayResp ddftResultNotice(DfftPayCallBackReq req);


    /**
     *
     *统一支付接口主动查询接口
     * @param
     * @param
     */
    UnifiedPayResp unifiedPayQuery(UnifiedQueryReq req);



    /**
     *
     *回调参数保存
     * @param
     * @param
     */
    void saveCallBackInfo(GatewayPayBackRecordReq req);



    /**
     *
     * 更新回调参数
     * @param
     * @param
     */
    void updateCallBackInfo(GatewayPayBackRecordReq req);


    /**
     *
     * 定时任务查询订单转态
     * @param
     * @param
     */
    void queryOrderQuartz();
}
