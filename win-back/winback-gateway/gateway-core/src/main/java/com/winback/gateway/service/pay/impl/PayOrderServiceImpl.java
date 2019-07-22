package com.winback.gateway.service.pay.impl;


import com.winback.arch.core.sysconfig.properties.GatewayProperties;
import com.winback.gateway.condition.pay.TGatewayPayOrderCondition;
import com.winback.gateway.dao.pay.TGatewayPayOrderDao;
import com.winback.gateway.dao.pay.TGatewayPayRecordDao;
import com.winback.gateway.model.pay.TGatewayPayOrder;
import com.winback.gateway.service.pay.PayOrderService;
import com.winback.gateway.condition.pay.TGatewayPayOrderCondition;
import com.winback.gateway.dao.pay.TGatewayPayOrderDao;
import com.winback.gateway.model.pay.TGatewayPayOrder;
import com.winback.gateway.service.pay.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述：支付请求服务类型
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/16 10:00
 */
@Slf4j
@Service
public class PayOrderServiceImpl implements PayOrderService {



    @Autowired
    private TGatewayPayOrderDao gatewayPayOrderDao;


    @Override
    public void savePayOrder(TGatewayPayOrder order) {
        gatewayPayOrderDao.insert(order);
    }

    @Override
    public TGatewayPayOrder getPayOrder(TGatewayPayOrderCondition condition) {
        return gatewayPayOrderDao.selectFirst(condition);
    }

    @Override
    public void updatePayOrder(TGatewayPayOrder order) {
        gatewayPayOrderDao.updateById(order);
    }


    @Override
    public List<TGatewayPayOrder> getNotPayOrder() {
        return gatewayPayOrderDao.getNotPayOrder();
    }
}
