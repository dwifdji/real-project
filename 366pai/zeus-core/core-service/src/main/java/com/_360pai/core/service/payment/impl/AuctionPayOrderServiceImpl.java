package com._360pai.core.service.payment.impl;

import com._360pai.core.condition.activity.TAuctionPayOrderCondition;
import com._360pai.core.dao.activity.TAuctionPayOrderDao;
import com._360pai.core.model.activity.TAuctionPayOrder;
import com._360pai.core.service.payment.AuctionPayOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author RuQ
 * @Title: AuctionPayOrderServiceImpl
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/14 19:18
 */
@Service
public class AuctionPayOrderServiceImpl implements AuctionPayOrderService {

    @Resource
    private TAuctionPayOrderDao tAuctionPayOrderDao;

    @Override
    public boolean saveAuctionPayOrder(TAuctionPayOrder order) {
        return tAuctionPayOrderDao.insert(order) == 1;
    }

    @Override
    public boolean updateAuctionPayOrder(TAuctionPayOrder order) {
        return tAuctionPayOrderDao.updateById(order) == 1;
    }

    @Override
    public TAuctionPayOrder findAuctionPayOrderByOrderId(Long orderId) {
        TAuctionPayOrderCondition condition = new TAuctionPayOrderCondition();
        condition.setOrderId(orderId);
        return tAuctionPayOrderDao.selectFirst(condition);
    }
}
