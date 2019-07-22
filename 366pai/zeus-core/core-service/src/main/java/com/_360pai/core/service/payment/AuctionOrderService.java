package com._360pai.core.service.payment;


import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.condition.payment.AuctionOrderCondition;
import com._360pai.core.facade.payment.req.AuctionOrderReq;
import com._360pai.core.facade.payment.resp.AuctionOrderResp;
import com._360pai.core.facade.payment.vo.AuctionOrderVo;
import com._360pai.core.facade.payment.vo.ShopAuctionOrderVo;
import com._360pai.core.model.payment.AuctionOrder;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.List;

public interface AuctionOrderService {

    AuctionOrder getById(Long orderId);

    AuctionOrder getByIdAndPartyId(Long orderId,Integer buyerId,Integer sellerId);

    AuctionOrderResp getAuctionOrder(AuctionOrderReq.OrderIdReq req);

    AuctionOrderResp getMyBuyerOrder(AuctionOrderReq.OrderIdReq req);

    AuctionOrderResp getMySellerOrder(AuctionOrderReq.OrderIdReq req);

    PageInfoResp<AuctionOrderVo> getAllByPage(AuctionOrderReq.QueryReq req);

    PageInfoResp<AuctionOrderVo> getSellerOrderListByPage(AuctionOrderReq.QueryReq req);

    PageInfo getAllByPage(int page, int perPage, AuctionOrderCondition condition, String orderBy);

    AuctionOrder getFirstByActivityId(Integer activityId);

    boolean toggleAutoHandleDelay(Long orderId);

    boolean saveAuctionOrder(AuctionOrder order);

    boolean updateAuctionOrder(AuctionOrder order);

    Object getMyOrders(int page, int perPage, Integer partyId,String categoryId,String name);

    PageInfoResp<ShopAuctionOrderVo> getShopDealCommissionListByPage(AuctionOrderReq.QueryReq req);

    ShopAuctionOrderVo getShopDealCommissionDetail(AuctionOrderReq.OrderIdReq req);

    List<AuctionOrder> getBuyerNeedToPaidList(Integer partyId);

    public BigDecimal getLeaseCommissionDiscount(BigDecimal dealAmount);
}