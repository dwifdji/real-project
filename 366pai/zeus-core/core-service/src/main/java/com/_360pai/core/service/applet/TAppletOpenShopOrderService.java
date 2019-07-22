package com._360pai.core.service.applet;

import com._360pai.core.condition.applet.TAppletOpenShopOrderCondition;
import com._360pai.core.model.applet.TAppletOpenShopOrder;

import java.util.List;

public interface TAppletOpenShopOrderService {


    TAppletOpenShopOrder getOrderById(Integer id);


    List<TAppletOpenShopOrder> getAppletOpenShopOrderList(TAppletOpenShopOrderCondition condition);


    Integer saveOrder(TAppletOpenShopOrder req);


    Integer updateById(TAppletOpenShopOrder req);


    TAppletOpenShopOrder getFirstAppletOpenShopOrder(TAppletOpenShopOrderCondition condition);


    List<TAppletOpenShopOrder> getAppletNotPayOrderList();




}
