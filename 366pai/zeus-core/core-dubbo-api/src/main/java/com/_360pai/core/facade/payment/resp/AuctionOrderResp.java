package com._360pai.core.facade.payment.resp;

import com._360pai.arch.common.BaseResp;
import com._360pai.core.facade.payment.vo.AuctionOrderVo;

/**
 * @author xdrodger
 * @Title: AuctionOrderResp
 * @ProjectName zeus
 * @Description:
 * @date 07/09/2018 13:36
 */
public class AuctionOrderResp extends BaseResp {
    private Long orderId;
    private AuctionOrderVo auctionOrder;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public AuctionOrderVo getAuctionOrder() {
        return auctionOrder;
    }

    public void setAuctionOrder(AuctionOrderVo auctionOrder) {
        this.auctionOrder = auctionOrder;
    }
}
