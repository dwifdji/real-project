package com._360pai.core.facade.payment;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.payment.req.AuctionOrderReq;
import com._360pai.core.facade.payment.resp.AuctionOrderResp;
import com._360pai.core.facade.payment.vo.AuctionOrderVo;
import com._360pai.core.facade.payment.vo.ShopAuctionOrderVo;

/**
 * @author : whisky_vip
 * @date : 2018/8/15 16:58
 */
public interface AuctionOrderFacade {
    PageInfoResp<AuctionOrderVo> getAllByPage(AuctionOrderReq.QueryReq req);

    PageInfoResp<AuctionOrderVo> getSellerOrderListByPage(AuctionOrderReq.QueryReq req);

    AuctionOrderResp getAuctionOrder(AuctionOrderReq.OrderIdReq req);

    AuctionOrderResp getMyBuyerOrder(AuctionOrderReq.OrderIdReq req);

    AuctionOrderResp getMySellerOrder(AuctionOrderReq.OrderIdReq req);

    AuctionOrderResp toggleAutoHandleDelay(AuctionOrderReq.OrderIdReq req);

    Object getMyOrders(AuctionOrderReq.OrderIdReq req);

    Object bidOrder(ActivityReq.ActivityId req);

    PageInfoResp<ShopAuctionOrderVo> getShopDealCommissionListByPage(AuctionOrderReq.QueryReq req);

    ShopAuctionOrderVo getShopDealCommissionDetail(AuctionOrderReq.OrderIdReq req);
}
