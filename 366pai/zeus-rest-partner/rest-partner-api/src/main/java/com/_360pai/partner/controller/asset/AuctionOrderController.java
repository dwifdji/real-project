package com._360pai.partner.controller.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.facade.activity.AuctionFacade;
import com._360pai.core.facade.activity.req.AuctionReq;
import com._360pai.core.facade.payment.AuctionOrderFacade;
import com._360pai.core.facade.payment.req.AuctionOrderReq;
import com._360pai.partner.controller.AbstractController;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/16 10:32
 */
@RestController
public class AuctionOrderController extends AbstractController {

    @Reference(version = "1.0.0")
    AuctionOrderFacade auctionOrderFacade;
    @Reference(version = "1.0.0")
    private AuctionFacade auctionFacade;

    /**
     * 送拍成交订单列表
     */
    @GetMapping(value = "/agency/seller/auction/order/list")
    public ResponseModel sellerOrderList(AuctionOrderReq.QueryReq req) {
        req.setSellerAgencyId(loadCurLoginAgencyId());
        return ResponseModel.succ(auctionOrderFacade.getAllByPage(req));
    }

    /**
     * 送拍成交订单详情
     */
    @GetMapping(value = "/agency/seller/auction/order/detail")
    public ResponseModel sellerOrderDetail(AuctionOrderReq.OrderIdReq req) {
        Assert.notNull(req.getOrderId(), "orderId 不能为空");
        return ResponseModel.succ(auctionOrderFacade.getAuctionOrder(req));
    }

    /**
     * 联拍成交订单列表
     */
    @GetMapping(value = "/agency/buyer/auction/order/list")
    public ResponseModel buyerOrderList(AuctionOrderReq.QueryReq req) {
        req.setBuyerAgencyId(loadCurLoginAgencyId());
        return ResponseModel.succ(auctionOrderFacade.getAllByPage(req));
    }

    /**
     * 联拍成交订单详情
     */
    @GetMapping(value = "/agency/buyer/auction/order/detail")
    public ResponseModel buyerOrderDetail(AuctionOrderReq.OrderIdReq req) {
        Assert.notNull(req.getOrderId(), "orderId 不能为空");
        return ResponseModel.succ(auctionOrderFacade.getAuctionOrder(req));
    }

    /**
     * 上拍成交订单列表
     */
    @GetMapping(value = "/agency/my/seller/auction/order/list")
    public ResponseModel mySellerAuctionOrderList(AuctionOrderReq.QueryReq req) {
        req.setSellerId(loadCurLoginAgencyId());
        req.setComeFrom(AssetEnum.ComeFrom.AGENCY.getKey());
        return ResponseModel.succ(auctionOrderFacade.getSellerOrderListByPage(req));
    }

    /**
     * 上拍成交订单详情
     */
    @GetMapping(value = "/agency/my/seller/auction/order/detail")
    public ResponseModel mySellerAuctionOrderDetail(AuctionOrderReq.OrderIdReq req) {
        Assert.notNull(req.getOrderId(), "orderId 不能为空");
        req.setSellerId(loadCurLoginAgencyId());
        return ResponseModel.succ(auctionOrderFacade.getMySellerOrder(req));
    }

    /**
     * 支付
     */

    @PostMapping(value = "/agency/auction/pay")
    public ResponseModel pay(@RequestBody AuctionReq req) {
        if (req == null || req.getOrderId() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setPartyId(loadCurLoginAgencyId());

        return ResponseModel.succ(auctionFacade.pay(req));
    }

    /**
     * 发货
     */
    @PostMapping(value = "/agency/auction/confirmSend")
    public ResponseModel confirmSend(@RequestBody AuctionReq req) {
        if (req == null || req.getOrderId() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setPartyId(loadCurLoginAgencyId());
        return ResponseModel.succ(auctionFacade.confirmSend(req));
    }
}
