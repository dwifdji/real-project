package com.winback.core.service.contract;

import com.winback.arch.common.AppReq;
import com.winback.arch.common.AppletReq;
import com.winback.arch.common.ListResp;
import com.winback.arch.common.PageInfoResp;
import com.winback.core.facade.contract.req.AdminContractReq;
import com.winback.core.facade.contract.req.AppContractReq;
import com.winback.core.facade.contract.req.AppletContractReq;
import com.winback.core.facade.contract.resp.AdminContractResp;
import com.winback.core.facade.contract.resp.AppContractResp;
import com.winback.core.facade.contract.resp.AppletContractResp;
import com.winback.core.facade.contract.vo.Contract;
import com.winback.core.facade.contract.vo.ContractBigType;
import com.winback.core.facade.contract.vo.ContractInvoice;

import java.util.List;

/**
 * @author xdrodger
 * @Title: ContractService
 * @ProjectName winback
 * @Description:
 * @date 2019/2/11 09:32
 */
public interface ContractService {

    PageInfoResp<Contract> getContractList(AppContractReq.QueryReq req);

    Contract getContract(AppContractReq.QueryReq req);

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

    PageInfoResp<Contract> getPossessedContractList(AppletContractReq.QueryReq req);

    PageInfoResp<Contract> getFavoriteContractList(AppReq req);

    PageInfoResp<Contract> getFavoriteContractList(AppletReq req);

    ListResp<ContractBigType> getContractBigTypeList();

    ListResp<ContractBigType> getBackContractBigTypeList();

    String getLatestEmail(Integer accountId);

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

    Integer favoriteContractCount(Integer accountId);

    Integer appletFavoriteContractCount(Integer extBindId);

    Integer resetContractAmount(List<List<Object>> rowList);

    AppletContractResp.ShareResp getShareInfo(AppletContractReq.ShareReq req);
}
