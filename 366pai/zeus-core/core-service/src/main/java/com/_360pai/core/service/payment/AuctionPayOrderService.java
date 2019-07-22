package com._360pai.core.service.payment;

import com._360pai.core.model.activity.TAuctionPayOrder;

/**
 * @author RuQ
 * @Title: AuctionPayOrderService
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/14 19:14
 */
public interface AuctionPayOrderService {
    public boolean saveAuctionPayOrder(TAuctionPayOrder order);

    public boolean updateAuctionPayOrder(TAuctionPayOrder order);

    public TAuctionPayOrder findAuctionPayOrderByOrderId(Long orderId);
}
