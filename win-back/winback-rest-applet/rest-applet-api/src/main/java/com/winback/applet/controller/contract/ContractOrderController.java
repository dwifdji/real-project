package com.winback.applet.controller.contract;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.winback.applet.controller.AbstractController;
import com.winback.arch.common.ResponseModel;
import com.winback.core.facade.contract.ContractFacade;
import com.winback.core.facade.contract.req.AppContractOrderReq;
import com.winback.core.facade.contract.req.AppletContractOrderReq;
import com.winback.core.facade.contract.req.AppletContractReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author xdrodger
 * @Title: ContractController
 * @ProjectName winback
 * @Description:
 * @date 2019/2/11 10:06
 */
@Slf4j
@RestController
public class ContractOrderController extends AbstractController {

    @Reference(version = "1.0.0")
    private ContractFacade contractFacade;

    /**
     * 立即购买接口
     */
    @PostMapping(value = "/confined/contract/buy/now")
    public ResponseModel buyNow(@RequestBody AppletContractOrderReq.BuyNowReq req) {
        Assert.notNull(req.getContractId(), "contractId 参数不能为空");
        req.setLoginId(loadCurLoginId());
        Integer orderId = contractFacade.buyNow(req);
        Map<String, Object> data = Maps.newHashMap();
        data.put("orderId", orderId);
        return ResponseModel.succ(data);
    }

    /**
     * 购物车购买接口
     */
    @PostMapping(value = "/confined/contract/shopping/cart/buy")
    public ResponseModel shoppingCartBuy(@RequestBody AppletContractOrderReq.ShoppingCartBuyReq req) {
        req.setLoginId(loadCurLoginId());
        Integer orderId = contractFacade.shoppingCartBuy(req);
        Map<String, Object> data = Maps.newHashMap();
        data.put("orderId", orderId);
        return ResponseModel.succ(data);
    }

    /**
     * 订单购买接口
     */
    @PostMapping(value = "/confined/contract/order/buy")
    public ResponseModel orderBuy(@RequestBody AppletContractOrderReq.OrderBuyReq req) {
        Assert.notNull(req.getOrderId(), "contractId 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.orderBuy(req));
    }


    /**
     * 订单预支付接口
     */
    @PostMapping(value = "/confined/contract/order/prepay")
    public ResponseModel orderPrepay(@RequestBody AppletContractOrderReq.PrepayReq req) {
        Assert.notNull(req.getOrderId(), "orderId 参数不能为空");
        Assert.notNull(req.getPayType(), "payType 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.orderPrepay(req));
    }

    /**
     * 订单支付状态查询接口
     */
    @GetMapping(value = "/confined/contract/order/pay/status")
    public ResponseModel orderPayStatus(AppletContractOrderReq.QueryReq req) {
        Assert.notNull(req.getOrderId(), "orderId 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ();
    }

    /**
     * 取消订单接口
     */
    @PostMapping(value = "/confined/contract/order/cancel")
    public ResponseModel cancelOrder(@RequestBody AppletContractOrderReq.QueryReq req) {
        Assert.notNull(req.getOrderId(), "orderId 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.cancelOrder(req));
    }

    /**
     * 获取合同订单列表接口(分页)
     */
    @GetMapping(value = "/confined/contract/order/list")
    public ResponseModel getOrderList(AppletContractOrderReq.QueryReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.getOrderList(req));
    }

    /**
     * 获取合同订单详情接口
     */
    @GetMapping(value = "/confined/contract/order/detail")
    public ResponseModel getOrderDetail(AppletContractOrderReq.QueryReq req) {
        Assert.notNull(req.getOrderId(), "orderId 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.getOrder(req));
    }

    /**
     * 获取已购合同列表接口(分页)
     */
    @GetMapping(value = "/confined/contract/possessed/list")
    public ResponseModel getPossessedContractList(AppletContractReq.QueryReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.getPossessedContractList(req));
    }

    /**
     * 订单支付回调接口
     */
    @PostMapping(value = "/confined/contract/order/pay/callback")
    public ResponseModel payCallback(@RequestBody AppletContractOrderReq.PayCallBackReq req) {
        Assert.notNull(req.getOrderId(), "orderId 参数不能为空");
        return ResponseModel.succ(contractFacade.payCallBack(req));
    }

    /**
     * 开票用合同订单列表接口
     */
    @GetMapping(value = "/confined/contract/invoice/order/list")
    public ResponseModel getInvoiceUseOrderList(AppletContractOrderReq.QueryReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.getInvoiceOrderList(req));
    }
}
