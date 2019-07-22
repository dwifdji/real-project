package com._360pai.core.provider.payment;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.NumberValidationUtils;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.vo.BidRecord;
import com._360pai.core.facade.payment.AuctionOrderFacade;
import com._360pai.core.facade.payment.req.AuctionOrderReq;
import com._360pai.core.facade.payment.resp.AuctionOrderResp;
import com._360pai.core.facade.payment.vo.AuctionOrderVo;
import com._360pai.core.facade.payment.vo.ShopAuctionOrderVo;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.service.activity.AuctionActivityService;
import com._360pai.core.service.activity.BidService;
import com._360pai.core.service.payment.AuctionOrderService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : whisky_vip
 * @date : 2018/8/15 16:57
 */
@Component
@Service(version = "1.0.0")
public class AuctionOrderProvider implements AuctionOrderFacade {

    @Autowired
    private AuctionOrderService auctionOrderService;
    @Autowired
    private BidService bidService;
    @Resource
    private AuctionActivityService auctionActivityService;

    @Override
    public PageInfoResp<AuctionOrderVo> getAllByPage(AuctionOrderReq.QueryReq req) {
        return auctionOrderService.getAllByPage(req);
    }

    @Override
    public PageInfoResp<AuctionOrderVo> getSellerOrderListByPage(AuctionOrderReq.QueryReq req) {
        return auctionOrderService.getSellerOrderListByPage(req);
    }

    @Override
    public AuctionOrderResp getAuctionOrder(AuctionOrderReq.OrderIdReq req) {
        return auctionOrderService.getAuctionOrder(req);
    }

    @Override
    public AuctionOrderResp getMyBuyerOrder(AuctionOrderReq.OrderIdReq req) {
        return auctionOrderService.getMyBuyerOrder(req);
    }

    @Override
    public AuctionOrderResp getMySellerOrder(AuctionOrderReq.OrderIdReq req) {
        return auctionOrderService.getMySellerOrder(req);
    }

    @Override
    public AuctionOrderResp toggleAutoHandleDelay(AuctionOrderReq.OrderIdReq req) {
        AuctionOrderResp resp = new AuctionOrderResp();
        boolean success = auctionOrderService.toggleAutoHandleDelay(req.getOrderId());
        if (!success) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        resp.setOrderId(req.getOrderId());
        return resp;
    }

    @Override
    public Object getMyOrders(AuctionOrderReq.OrderIdReq req) {
        return auctionOrderService.getMyOrders(req.getPage(), req.getPerPage(), req.getPartyId(),req.getCategoryId(),req.getName());
    }

    @Override
    public Object bidOrder(ActivityReq.ActivityId req) {
        PageInfoResp<BidRecord> allBidRecordsByPage = bidService.getAllBidRecordsByPage(req);
        AuctionActivity auctionActivity = auctionActivityService.getById(req.getActivityId());
        //特殊处理   一口价暗标 出价记录只会显示自己的出价记录
        if (auctionActivity.getMode().equals(ActivityEnum.ActivityMode.SEALED.getKey()) ||
                auctionActivity.getMode().equals(ActivityEnum.ActivityMode.PUBLIC.getKey())) {
            if (req.getPartyId() == null) {
                return null;
            }
            List<BidRecord> list = allBidRecordsByPage.getList();
            List<BidRecord> bidRecords = new ArrayList<>();
            for (BidRecord bidRecord : list) {
                String meFlag = bidRecord.getMeFlag();
                String amount = bidRecord.getAmount();
                bidRecord.setAmount(NumberValidationUtils.formatPrice(amount));
                if ("1".equals(meFlag)) {
                    bidRecords.add(bidRecord);
                }
            }
            allBidRecordsByPage.setList(bidRecords);
            return allBidRecordsByPage;
        } else {
            return allBidRecordsByPage;
        }
    }

    @Override
    public PageInfoResp<ShopAuctionOrderVo> getShopDealCommissionListByPage(AuctionOrderReq.QueryReq req) {
        return auctionOrderService.getShopDealCommissionListByPage(req);
    }

    @Override
    public ShopAuctionOrderVo getShopDealCommissionDetail(AuctionOrderReq.OrderIdReq req) {
        return auctionOrderService.getShopDealCommissionDetail(req);
    }
}
