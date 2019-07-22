package com.winback.core.provider.contract;

import com.alibaba.dubbo.config.annotation.Service;
import com.winback.arch.common.*;
import com.winback.core.facade.contract.ContractFacade;
import com.winback.core.facade.contract.req.*;
import com.winback.core.facade.contract.resp.AppContractOrderResp;
import com.winback.core.facade.contract.resp.AppContractResp;
import com.winback.core.facade.contract.resp.AppletContractOrderResp;
import com.winback.core.facade.contract.resp.AppletContractResp;
import com.winback.core.facade.contract.vo.*;
import com.winback.core.service.contract.ContractOrderService;
import com.winback.core.service.contract.ContractService;
import com.winback.core.service.contract.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xdrodger
 * @Title: ContractProvider
 * @ProjectName winback
 * @Description:
 * @date 2019/1/31 14:40
 */
@Slf4j
@Component
@Service(version = "1.0.0")
public class ContractProvider implements ContractFacade {

    @Autowired
    private ContractService contractService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ContractOrderService contractOrderService;


    @Override
    public PageInfoResp<Contract> getContractList(AppContractReq.QueryReq req) {
        return contractService.getContractList(req);
    }

    @Override
    public Contract getContract(AppContractReq.QueryReq req) {
        return contractService.getContract(req);
    }

    @Override
    public ContractShoppingCart getShoppingCart(AppShoppingCartReq.QueryReq req) {
        return shoppingCartService.getShoppingCart(req);
    }

    @Override
    public Integer addShoppingCartItem(AppShoppingCartReq.AddItemReq req) {
        return shoppingCartService.addShoppingCartItem(req);
    }

    @Override
    public Integer deleteShoppingCartItem(AppShoppingCartReq.DeleteItemReq req) {
        return shoppingCartService.deleteShoppingCartItem(req);
    }

    @Override
    public Integer clearShoppingCart(AppShoppingCartReq.QueryReq req) {
        return shoppingCartService.clearShoppingCart(req);
    }

    @Override
    public Integer buyNow(AppContractOrderReq.BuyNowReq req) {
        return contractOrderService.buyNow(req);
    }

    @Override
    public Integer shoppingCartBuy(AppContractOrderReq.ShoppingCartBuyReq req) {
        return contractOrderService.shoppingCartBuy(req);
    }

    @Override
    public AppContractOrderResp.OrderBuyResp orderBuy(AppContractOrderReq.OrderBuyReq req) {
        return contractOrderService.orderBuy(req);
    }

    @Override
    public AppContractOrderResp.PrepayResp orderPrepay(AppContractOrderReq.PrepayReq req) {
        return contractOrderService.prepay(req);
    }

    @Override
    public PageInfoResp<ContractOrder> getOrderList(AppContractOrderReq.QueryReq req) {
        return contractOrderService.getOrderList(req);
    }

    @Override
    public ContractOrder getOrder(AppContractOrderReq.QueryReq req) {
        return contractOrderService.getOrder(req);
    }

    @Override
    public PageInfoResp<Contract> getPossessedContractList(AppContractReq.QueryReq req) {
        return contractService.getPossessedContractList(req);
    }

    @Override
    public ListResp<String> getSearchRecordList(AppContractReq.QueryReq req) {
        return contractService.getSearchRecordList(req);
    }

    @Override
    public void clearSearchRecord(AppContractReq.QueryReq req) {
        contractService.clearSearchRecord(req);
    }

    @Override
    public ListResp<String> download(AppContractReq.DownloadReq req) {
        return contractService.download(req);
    }

    @Override
    public Integer favor(AppContractReq.QueryReq req) {
        return contractService.favor(req);
    }

    @Override
    public Integer unfavor(AppContractReq.QueryReq req) {
        return contractService.unfavor(req);
    }

    @Override
    public PageInfoResp<Contract> getContractList(AppletContractReq.QueryReq req) {
        return contractService.getContractList(req);
    }

    @Override
    public Contract getContract(AppletContractReq.QueryReq req) {
        return contractService.getContract(req);
    }

    @Override
    public ListResp<String> getSearchRecordList(AppletContractReq.QueryReq req) {
        return contractService.getSearchRecordList(req);
    }

    @Override
    public void clearSearchRecord(AppletContractReq.QueryReq req) {
        contractService.clearSearchRecord(req);
    }

    @Override
    public void deleteSearchRecord(AppletContractReq.QueryReq req) {
        contractService.deleteSearchRecord(req);
    }

    @Override
    public ListResp<String> download(AppletContractReq.DownloadReq req) {
        return contractService.download(req);
    }

    @Override
    public Integer favor(AppletContractReq.QueryReq req) {
        return contractService.favor(req);
    }

    @Override
    public Integer unfavor(AppletContractReq.QueryReq req) {
        return contractService.unfavor(req);
    }

    @Override
    public ContractShoppingCart getShoppingCart(AppletShoppingCartReq.QueryReq req) {
        return shoppingCartService.getShoppingCart(req);
    }

    @Override
    public Integer addShoppingCartItem(AppletShoppingCartReq.AddItemReq req) {
        return shoppingCartService.addShoppingCartItem(req);
    }

    @Override
    public Integer deleteShoppingCartItem(AppletShoppingCartReq.DeleteItemReq req) {
        return shoppingCartService.deleteShoppingCartItem(req);
    }

    @Override
    public Integer clearShoppingCart(AppletShoppingCartReq.QueryReq req) {
        return shoppingCartService.clearShoppingCart(req);
    }

    @Override
    public Integer buyNow(AppletContractOrderReq.BuyNowReq req) {
        return contractOrderService.buyNow(req);
    }

    @Override
    public Integer shoppingCartBuy(AppletContractOrderReq.ShoppingCartBuyReq req) {
        return contractOrderService.shoppingCartBuy(req);
    }

    @Override
    public AppletContractOrderResp.OrderBuyResp orderBuy(AppletContractOrderReq.OrderBuyReq req) {
        return contractOrderService.orderBuy(req);
    }

    @Override
    public AppletContractOrderResp.PrepayResp orderPrepay(AppletContractOrderReq.PrepayReq req) {
        return contractOrderService.orderPrepay(req);
    }

    @Override
    public PageInfoResp<ContractOrder> getOrderList(AppletContractOrderReq.QueryReq req) {
        return contractOrderService.getOrderList(req);
    }

    @Override
    public ContractOrder getOrder(AppletContractOrderReq.QueryReq req) {
        return contractOrderService.getOrder(req);
    }

    @Override
    public PageInfoResp<Contract> getPossessedContractList(AppletContractReq.QueryReq req) {
        return contractService.getPossessedContractList(req);
    }

    @Override
    public PageInfoResp<Contract> getFavoriteContractList(AppReq req) {
        return contractService.getFavoriteContractList(req);
    }

    @Override
    public PageInfoResp<Contract> getFavoriteContractList(AppletReq req) {
        return contractService.getFavoriteContractList(req);
    }

    @Override
    public Integer cancelOrder(AppContractOrderReq.QueryReq req) {
        return contractOrderService.cancelOrder(req);
    }

    @Override
    public Integer cancelOrder(AppletContractOrderReq.QueryReq req) {
        return contractOrderService.cancelOrder(req);
    }

    @Override
    public ListResp<ContractBigType> getContractBigTypeList() {
        return contractService.getContractBigTypeList();
    }

    @Override
    public ListResp<ContractBigType> getBackContractBigTypeList() {
        return contractService.getBackContractBigTypeList();
    }

    @Override
    public AppContractResp.PreInvoiceResp preInvoice(AppContractReq.PreInvoiceReq req) {
        return contractService.preInvoice(req);
    }

    @Override
    public Integer invoice(AppContractReq.InvoiceReq req) {
        return contractService.invoice(req);
    }

    @Override
    public PageInfoResp<ContractInvoice> getContractInvoiceList(AppContractReq.QueryReq req) {
        return contractService.getContractInvoiceList(req);
    }

    @Override
    public AppletContractResp.PreInvoiceResp preInvoice(AppletContractReq.PreInvoiceReq req) {
        return contractService.preInvoice(req);
    }

    @Override
    public Integer invoice(AppletContractReq.InvoiceReq req) {
        return contractService.invoice(req);
    }

    @Override
    public PageInfoResp<ContractInvoice> getContractInvoiceList(AppletContractReq.QueryReq req) {
        return contractService.getContractInvoiceList(req);
    }

    @Override
    public PageInfoResp<Contract> getContractList(AdminContractReq.QueryReq req) {
        return contractService.getContractList(req);
    }

    @Override
    public Contract getContract(AdminContractReq.QueryReq req) {
        return contractService.getContract(req);
    }

    @Override
    public Integer addContract(AdminContractReq.AddReq req) {
        return contractService.addContract(req);
    }

    @Override
    public Integer editContract(AdminContractReq.EditReq req) {
        return contractService.editContract(req);
    }

    @Override
    public PageInfoResp<ContractInvoice> getContractInvoiceList(AdminContractReq.QueryReq req) {
        return contractService.getContractInvoiceList(req);
    }

    @Override
    public Integer invoiceApplyApprove(AdminContractReq.InvoiceVerifyReq req) {
        return contractService.invoiceApplyApprove(req);
    }

    @Override
    public Integer invoiceApplyReject(AdminContractReq.InvoiceVerifyReq req) {
        return contractService.invoiceApplyReject(req);
    }

    @Override
    public ResponseModel getOrderList(AdminContractOrderReq.QueryReq req) {
        return contractOrderService.getOrderList(req);
    }

    @Override
    public String payCallBack(AppContractOrderReq.PayCallBackReq req) {
        return contractOrderService.payCallBack(req);
    }

    @Override
    public String payCallBack(AppletContractOrderReq.PayCallBackReq req) {
        return contractOrderService.payCallBack(req);
    }

    @Override
    public String payCallBack(String orderNo) {
        return contractOrderService.payCallBack(orderNo);
    }

    @Override
    public ListResp<InvoiceContractOrder> getInvoiceOrderList(AppContractOrderReq.QueryReq req) {
        return contractOrderService.getInvoiceOrderList(req);
    }

    @Override
    public ListResp<InvoiceContractOrder> getInvoiceOrderList(AppletContractOrderReq.QueryReq req) {
        return contractOrderService.getInvoiceOrderList(req);
    }

    @Override
    public Integer resetContractAmount(List<List<Object>> rowList) {
        return contractService.resetContractAmount(rowList);
    }

    @Override
    public AppletContractResp.ShareResp getShareInfo(AppletContractReq.ShareReq req) {
        return contractService.getShareInfo(req);
    }
}
