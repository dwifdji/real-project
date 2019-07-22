package com.tzCloud.core.service;


import com.tzCloud.core.model.order.TServiceOrder;

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
}
