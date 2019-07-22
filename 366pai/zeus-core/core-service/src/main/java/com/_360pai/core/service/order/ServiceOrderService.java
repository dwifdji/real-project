package com._360pai.core.service.order;

import com._360pai.core.model.order.TServiceOrder;
import com._360pai.gateway.controller.req.dfftpay.UnifiedPayResp;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/11 16:49
 */
public interface ServiceOrderService {
    Integer insert(TServiceOrder tServiceOrder);

    Integer update(TServiceOrder serviceOrder);

    TServiceOrder selectById(Integer serviceOrderId);

    int doProcess(Integer serviceOrderId, String code, String msg);

    /**
     * 描述返回该用户 是否购买尽调报告
     *
     * @author : whisky_vip
     * @date : 2018/9/29 16:13
     */
    boolean adjustedPayStatus(Long partyId, Integer activityId);
}
