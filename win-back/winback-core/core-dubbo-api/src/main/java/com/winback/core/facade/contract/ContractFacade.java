package com.winback.core.facade.contract;

import com.winback.arch.common.*;
import com.winback.core.facade.contract.req.*;
import com.winback.core.facade.contract.resp.AppContractOrderResp;
import com.winback.core.facade.contract.resp.AppContractResp;
import com.winback.core.facade.contract.resp.AppletContractOrderResp;
import com.winback.core.facade.contract.resp.AppletContractResp;
import com.winback.core.facade.contract.vo.*;

import java.util.List;

/**
 * @author xdrodger
 * @Title: ContractFacade
 * @ProjectName winback
 * @Description:
 * @date 2019/1/31 14:40
 */
public interface ContractFacade {

    PageInfoResp<Contract> getContractList(AppContractReq.QueryReq req);

    Contract getContract(AppContractReq.QueryReq req);

    ContractShoppingCart getShoppingCart(AppShoppingCartReq.QueryReq req);

    Integer addShoppingCartItem(AppShoppingCartReq.AddItemReq req);

    Integer deleteShoppingCartItem(AppShoppingCartReq.DeleteItemReq req);

    Integer clearShoppingCart(AppShoppingCartReq.QueryReq req);

    Integer buyNow(AppContractOrderReq.BuyNowReq req);

    Integer shoppingCartBuy(AppContractOrderReq.ShoppingCartBuyReq req);

    AppContractOrderResp.OrderBuyResp orderBuy(AppContractOrderReq.OrderBuyReq req);

    AppContractOrderResp.PrepayResp orderPrepay(AppContractOrderReq.PrepayReq req);

    PageInfoResp<ContractOrder> getOrderList(AppContractOrderReq.QueryReq req);

    ContractOrder getOrder(AppContractOrderReq.QueryReq req);

    PageInfoResp<Contract> getPossessedContractList(AppContractReq.QueryReq req);

    ListResp<String> getSearchRecordList(AppContractReq.QueryReq req);

    void clearSearchRecord(AppContractReq.QueryReq req);

    ListResp<String> download(AppContractReq.DownloadReq req);

    Integer favor(AppContractReq.QueryReq req);

    Integer unfavor(AppContractReq.QueryReq req);

    PageInfoResp<Contract> getContractList(AppletContractReq.QueryReq req);

    Contract getContract(AppletContractReq.QueryReq req);

    ListResp<String> getSearchRecordList(AppletContractReq.QueryReq req);

    void clearSearchRecord(AppletContractReq.QueryReq req);

    void deleteSearchRecord(AppletContractReq.QueryReq req);

    ListResp<String> download(AppletContractReq.DownloadReq req);

    Integer favor(AppletContractReq.QueryReq req);

    Integer unfavor(AppletContractReq.QueryReq req);

    ContractShoppingCart getShoppingCart(AppletShoppingCartReq.QueryReq req);

    Integer addShoppingCartItem(AppletShoppingCartReq.AddItemReq req);

    Integer deleteShoppingCartItem(AppletShoppingCartReq.DeleteItemReq req);

    Integer clearShoppingCart(AppletShoppingCartReq.QueryReq req);

    Integer buyNow(AppletContractOrderReq.BuyNowReq req);

    Integer shoppingCartBuy(AppletContractOrderReq.ShoppingCartBuyReq req);

    AppletContractOrderResp.OrderBuyResp orderBuy(AppletContractOrderReq.OrderBuyReq req);

    AppletContractOrderResp.PrepayResp orderPrepay(AppletContractOrderReq.PrepayReq req);

    PageInfoResp<ContractOrder> getOrderList(AppletContractOrderReq.QueryReq req);

    ContractOrder getOrder(AppletContractOrderReq.QueryReq req);

    PageInfoResp<Contract> getPossessedContractList(AppletContractReq.QueryReq req);

    PageInfoResp<Contract> getFavoriteContractList(AppReq req);

    PageInfoResp<Contract> getFavoriteContractList(AppletReq req);

    Integer cancelOrder(AppContractOrderReq.QueryReq req);

    Integer cancelOrder(AppletContractOrderReq.QueryReq req);

    ListResp<ContractBigType> getContractBigTypeList();

    ListResp<ContractBigType> getBackContractBigTypeList();

    AppContractResp.PreInvoiceResp preInvoice(AppContractReq.PreInvoiceReq req);

    Integer invoice(AppContractReq.InvoiceReq req);

    PageInfoResp<ContractInvoice> getContractInvoiceList(AppContractReq.QueryReq req);

    AppletContractResp.PreInvoiceResp preInvoice(AppletContractReq.PreInvoiceReq req);

    Integer invoice(AppletContractReq.InvoiceReq req);

    PageInfoResp<ContractInvoice> getContractInvoiceList(AppletContractReq.QueryReq req);

    PageInfoResp<Contract> getContractList(AdminContractReq.QueryReq req);

    Contract getContract(AdminContractReq.QueryReq req);

    Integer addContract(AdminContractReq.AddReq req);

    Integer editContract(AdminContractReq.EditReq req);

    PageInfoResp<ContractInvoice> getContractInvoiceList(AdminContractReq.QueryReq req);

    Integer invoiceApplyApprove(AdminContractReq.InvoiceVerifyReq req);

    Integer invoiceApplyReject(AdminContractReq.InvoiceVerifyReq req);

    ResponseModel getOrderList(AdminContractOrderReq.QueryReq req);

    String payCallBack(AppContractOrderReq.PayCallBackReq req);

    String payCallBack(AppletContractOrderReq.PayCallBackReq req);

    String payCallBack(String orderNo);

    ListResp<InvoiceContractOrder> getInvoiceOrderList(AppContractOrderReq.QueryReq req);

    ListResp<InvoiceContractOrder> getInvoiceOrderList(AppletContractOrderReq.QueryReq req);

    Integer resetContractAmount(List<List<Object>> rowList);

    AppletContractResp.ShareResp getShareInfo(AppletContractReq.ShareReq req);
}
