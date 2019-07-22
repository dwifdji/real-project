package com.winback.gateway.service.pay;

import com.winback.gateway.condition.pay.TGatewayPayOrderCondition;
import com.winback.gateway.model.pay.TGatewayPayOrder;
import com.winback.gateway.condition.pay.TGatewayPayOrderCondition;
import com.winback.gateway.model.pay.TGatewayPayOrder;

import java.util.List;

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



    /**
     *
     *更新订单信息
     */
    void updatePayOrder(TGatewayPayOrder order);



    /**
     *
     *获取未支付订单
     */
    List<TGatewayPayOrder> getNotPayOrder();

}
