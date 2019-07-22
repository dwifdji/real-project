package com._360pai.web.controller.activity;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.core.filter.PropertyFilterFactory;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.payment.AuctionOrderFacade;
import com._360pai.core.facade.payment.req.AuctionOrderReq;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: ActivityOrderController
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/13 11:07
 */
@RestController
public class ActivityOrderController extends AbstractController {
    @Reference(version = "1.0.0")
    private AuctionOrderFacade auctionOrderFacade;


    /**
     *
     * 功能描述: 参拍成交订单
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/10/19 13:54
     */
    @GetMapping(value = "/confined/auction_orders")
    public ResponseModel getMyOrders(AuctionOrderReq.OrderIdReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        Object object = auctionOrderFacade.getMyOrders(req);
        return ResponseModel.succ(object);
    }

    /**
     * 功能描述:  出价记录
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/23 14:24
     */
    @PostMapping(value = "/open/bid_order")
    public ResponseModel bidOrder(@RequestBody ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(),"活动ID参数不能为空");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        Object object = auctionOrderFacade.bidOrder(req);
        return ResponseModel.succ(object, PropertyFilterFactory.create(new String[]{"bidderName"}));
    }

    /**
     * 功能描述:  参拍成交详情
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/20 15:24
     */
    @GetMapping(value = "/confined/auction_orders/buyer/detail")
    public ResponseModel detail(AuctionOrderReq.OrderIdReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setBuyerId(accountBaseInfo.getPartyPrimaryId());
        Object object = auctionOrderFacade.getMyBuyerOrder(req);
        return ResponseModel.succ(object);
    }

    /**
     * 我的送拍成交订单列表
     */
    @GetMapping(value = "/confined/my/seller/auction/order/list")
    public ResponseModel mySellerAuctionOrderList(AuctionOrderReq.QueryReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setSellerId(accountBaseInfo.getPartyPrimaryId());
        req.setComeFrom(AssetEnum.ComeFrom.PLATFORM.getKey());
        return ResponseModel.succ(auctionOrderFacade.getSellerOrderListByPage(req));
    }

    /**
     * 我的送拍成交订单详情
     */
    @GetMapping(value = "/confined/my/seller/auction/order/detail")
    public ResponseModel mySellerAuctionOrderDetail(AuctionOrderReq.OrderIdReq req) {
        Assert.notNull(req.getOrderId(), "orderId 参数不能为空");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setSellerId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(auctionOrderFacade.getMySellerOrder(req));
    }
}
