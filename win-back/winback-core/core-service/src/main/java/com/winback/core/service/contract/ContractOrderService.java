package com.winback.core.service.contract;

import com.winback.arch.common.ListResp;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.ResponseModel;
import com.winback.core.facade.contract.req.AdminContractOrderReq;
import com.winback.core.facade.contract.req.AppContractOrderReq;
import com.winback.core.facade.contract.req.AppletContractOrderReq;
import com.winback.core.facade.contract.resp.AppContractOrderResp;
import com.winback.core.facade.contract.resp.AppletContractOrderResp;
import com.winback.core.facade.contract.vo.ContractOrder;
import com.winback.core.facade.contract.vo.InvoiceContractOrder;

/**
 * @author xdrodger
 * @Title: ContractOrderService
 * @ProjectName winback
 * @Description:
 * @date 2019/2/11 13:22
 */
public interface ContractOrderService {

    Integer buyNow(AppContractOrderReq.BuyNowReq req);

    Integer shoppingCartBuy(AppContractOrderReq.ShoppingCartBuyReq req);

    AppContractOrderResp.OrderBuyResp orderBuy(AppContractOrderReq.OrderBuyReq req);

    AppContractOrderResp.PrepayResp prepay(AppContractOrderReq.PrepayReq req);

    PageInfoResp<ContractOrder> getOrderList(AppContractOrderReq.QueryReq req);

    ContractOrder getOrder(AppContractOrderReq.QueryReq req);

    Integer buyNow(AppletContractOrderReq.BuyNowReq req);

    Integer shoppingCartBuy(AppletContractOrderReq.ShoppingCartBuyReq req);

    AppletContractOrderResp.OrderBuyResp orderBuy(AppletContractOrderReq.OrderBuyReq req);

    AppletContractOrderResp.PrepayResp orderPrepay(AppletContractOrderReq.PrepayReq req);

    PageInfoResp<ContractOrder> getOrderList(AppletContractOrderReq.QueryReq req);

    ContractOrder getOrder(AppletContractOrderReq.QueryReq req);

    Integer cancelOrder(AppContractOrderReq.QueryReq req);

    Integer cancelOrder(AppletContractOrderReq.QueryReq req);

    ResponseModel getOrderList(AdminContractOrderReq.QueryReq req);

    Integer payTimeOut(Integer orderId);

    Integer beAboutToPayTimeOut(Integer orderId);

    String payCallBack(AppContractOrderReq.PayCallBackReq req);

    String payCallBack(AppletContractOrderReq.PayCallBackReq req);

    String payCallBack(String orderNo);

    ListResp<InvoiceContractOrder> getInvoiceOrderList(AppContractOrderReq.QueryReq req);

    ListResp<InvoiceContractOrder> getInvoiceOrderList(AppletContractOrderReq.QueryReq req);
}
