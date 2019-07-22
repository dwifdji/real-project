package com._360pai.admin.controller.asset;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.StaffFacade;
import com._360pai.core.facade.activity.AuctionFacade;
import com._360pai.core.facade.payment.AuctionOrderFacade;
import com._360pai.core.facade.payment.req.AuctionOrderReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
    AuctionFacade auctionFacade;
    @Reference(version = "1.0.0")
    StaffFacade staffFacade;

    /**
     * 拍卖订单列表
     */
    @RequiresPermissions("pmgl_cjdd:list")
    @GetMapping(value = "/admin/auction/order/list")
    public ResponseModel list(AuctionOrderReq.QueryReq req) {
        return ResponseModel.succ(auctionOrderFacade.getAllByPage(req));
    }

    /**
     * 拍卖订单详情
     */
    @GetMapping(value = "/admin/auction/order/detail")
    public ResponseModel detail(AuctionOrderReq.OrderIdReq req) {
        Assert.notNull(req.getOrderId(), "orderId 参数不能为空");
        return ResponseModel.succ(auctionOrderFacade.getAuctionOrder(req));
    }

    /**
     * 拍卖订单强制执行
     */
    @RequiresPermissions("pmgl_cjdd:auto_handle_delay")
    @PostMapping(value = "/admin/auction/order/autoHandleDelay")
    public ResponseModel autoHandleDelay(@RequestBody  AuctionOrderReq.OrderIdReq req) {
        Assert.notNull(req.getOrderId(), "orderId 参数不能为空");
        auctionFacade.forceExecute(req.getOrderId());
        staffFacade.saveAuctionOrderOperationRecord(loadCurLoginId(), "成交订单强制执行", req.getOrderId());
        return ResponseModel.succ();
    }
}
