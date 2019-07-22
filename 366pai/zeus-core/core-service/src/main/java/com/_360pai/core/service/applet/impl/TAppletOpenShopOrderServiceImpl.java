package com._360pai.core.service.applet.impl;

import com._360pai.core.condition.applet.TAppletOpenShopOrderCondition;
import com._360pai.core.dao.applet.TAppletOpenShopOrderDao;
import com._360pai.core.model.applet.TAppletOpenShopOrder;
import com._360pai.core.service.applet.TAppletOpenShopOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TAppletOpenShopOrderServiceImpl implements TAppletOpenShopOrderService {

    @Autowired
    private TAppletOpenShopOrderDao tAppletOpenShopOrderDao;


    @Override
    public TAppletOpenShopOrder getOrderById(Integer id) {
        return tAppletOpenShopOrderDao.selectById(id);
    }

    @Override
    public List<TAppletOpenShopOrder> getAppletOpenShopOrderList(TAppletOpenShopOrderCondition condition) {
        return tAppletOpenShopOrderDao.selectList(condition);
    }


    @Override
    public Integer saveOrder(TAppletOpenShopOrder req) {
        return tAppletOpenShopOrderDao.insert(req);
    }

    @Override
    public Integer updateById(TAppletOpenShopOrder req) {
        return tAppletOpenShopOrderDao.updateById(req);
    }

    @Override
    public TAppletOpenShopOrder getFirstAppletOpenShopOrder(TAppletOpenShopOrderCondition condition) {
        return tAppletOpenShopOrderDao.selectFirst(condition);
    }

    @Override
    public List<TAppletOpenShopOrder> getAppletNotPayOrderList() {
        return tAppletOpenShopOrderDao.getAppletNotPayOrderList();
    }
}
