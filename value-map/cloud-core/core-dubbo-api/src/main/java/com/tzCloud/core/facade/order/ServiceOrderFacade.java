package com.tzCloud.core.facade.order;

import com.tzCloud.core.facade.order.req.ServiceOrderReq;
import com.tzCloud.core.facade.order.resp.ServiceConfigResp;
import com.tzCloud.core.facade.order.resp.ServiceOrderResp;
import com.tzCloud.core.facade.order.resp.ServiceOrderStatusResp;

import java.util.List;

/**
 * @author xiaolei
 * @create 2019-06-19 15:16
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
     * 会员支付
     * @param req
     * @return
     */
    ServiceOrderResp membershipPay(ServiceOrderReq.MembershipOpenReq req);

    /**
     * 获取会员配置
     * @return
     */
    List<ServiceConfigResp> getMembershipConfig();
}
