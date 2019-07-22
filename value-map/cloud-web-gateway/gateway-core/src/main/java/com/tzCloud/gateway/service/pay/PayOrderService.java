package com.tzCloud.gateway.service.pay;

import com.tzCloud.gateway.condition.pay.TGatewayPayOrderCondition;
import com.tzCloud.gateway.model.pay.TGatewayPayOrder;
import com.tzCloud.gateway.condition.pay.TGatewayPayOrderCondition;

/**
 * 描述：支付服务类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/16 9:59
 */
public interface PayOrderService {

    /**
     *
     *保存订单信息
     */
    void savePayOrder(TGatewayPayOrder order);



    /**
     *
     *获取订单信息
     */
    TGatewayPayOrder getPayOrder(TGatewayPayOrderCondition condition);


}
