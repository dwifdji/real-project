package com._360pai.core.facade.order;

import com._360pai.core.facade.order.req.ServiceOrderReq;
import com._360pai.core.facade.order.resp.ServiceOrderResp;
import com._360pai.core.facade.order.resp.ServiceOrderStatusResp;

import java.util.Map;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/12 14:57
 */
public interface ServiceOrderFacade {
    /**
     * 描述 订单查询
     *
     * @author : whisky_vip
     * @date : 2018/9/19 10:35
     */
    ServiceOrderStatusResp queryStatus(ServiceOrderReq.QueryStatus req);

    /**
     * 描述 大买办需求单 支付
     *
     * @author : whisky_vip
     * @date : 2018/9/19 10:35
     */
    ServiceOrderResp compradorRequirementPay(ServiceOrderReq.PayReq req);

    /**
     * 描述  配资乐需求单 支付
     *
     * @author : whisky_vip
     * @date : 2018/9/19 10:35
     */
    ServiceOrderResp withfudigRequirementPay(ServiceOrderReq.PayReq req);


    /**
     * 描述 服务处置支付
     *
     * @author : whisky_vip
     * @date : 2018/9/18 13:20
     */
    ServiceOrderResp disposalRequirementPay(ServiceOrderReq.DisposalRequirementPay req);


    /**
     * 描述 尽调报告
     *
     * @author : whisky_vip
     * @date : 2018/9/29 14:33
     */
    ServiceOrderResp adjustedPay(ServiceOrderReq.AdjustedPay req);

    /**
     * 描述 我要处置
     *
     * @author : xiaolei
     * @date : 2018/11/2 10:05
     */
    ServiceOrderResp providerShowPay(ServiceOrderReq.ProviderShowPay req);

    /**
     * 描述 第三方尽调报告支付
     *
     * @author : whisky_vip
     * @date : 2018/9/29 14:33
     */
    Map thirdReportPay(ServiceOrderReq.AdjustedPay req);
}
