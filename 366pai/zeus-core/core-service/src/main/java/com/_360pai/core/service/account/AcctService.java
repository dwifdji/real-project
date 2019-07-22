package com._360pai.core.service.account;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.req.AcctReq;
import com._360pai.core.facade.account.req.SearchBatchReq;
import com._360pai.core.facade.account.resp.AcctResp;
import com._360pai.core.facade.account.resp.WithdrawResp;
import com._360pai.core.facade.account.vo.*;
import com._360pai.core.model.account.*;
import com._360pai.core.model.agreement.WithdrawAgreement;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author RuQ
 * @Title: AcctService
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/11/23 16:47
 */
public interface AcctService {

    boolean addAcctAmount(Integer acctId, BigDecimal amount,String sourceType,String sourceOrderId,Integer shopId);
    WithdrawResp withdrawAcctAmount(String partyType, Integer partyId, BigDecimal amount, Integer bankAccountId);
    boolean releaseAcctLockedAmount(Long acctDetailId);
    TAcct findAcctByPartyIdAndType(Integer partyId,String type);
    TAcct findAcctById(Integer acctId);
    TAcctDetail findDetailById(Long detailId);
    boolean  updateAcctDetail(TAcctDetail acctDetail);
    TInvoice findInvoiceById(Integer invoiceId);
    boolean saveBatchOrder(Long batchOrderId,Integer operatorId);
    boolean updateBatchOrder(TBatchOrder order);
    boolean saveBatchDetailRef(Long batchOrderId,Long detailId);
    boolean delteBatchDetailRef(Long batchOrderId);
    boolean delteBatchDetailRefById(Integer refId);
    boolean checkDetailRefExist(Long detailId);
    TBatchOrder findMaxOrder();
    boolean saveInvoice(TInvoice invoice);
    boolean updateInvoice(TInvoice invoice);
    public boolean withdrawFail(Long acctDetailId);
    public boolean cancelPay(Long detailId,Integer operatorId,String reason);
    public boolean hc(Long detailId,Integer operatorId,String reason);
    List<TBatchDetailRef> findDetailRefListByBatchId(Long batchId);

    PageInfoResp<InviteCommissionVo> getInviteCommissionListByPage(AcctReq.QueryReq req);

    InviteCommissionVo getInviteCommission(AcctReq.BaseReq req);

    PageInfoResp<CommissionVo> getMyCommissionListByPage(AcctReq.QueryReq req);

    PageInfoResp<AcctDetailVo> getFrontAcctDetailListByPage(AcctReq.QueryReq req);

    PageInfoResp<AcctDetailVo> getAcctDetailListByPage(AcctReq.QueryReq req);

    AcctDetailVo getFrontAcctDetail(AcctReq.BaseReq req);

    ResponseModel getAcctListByPage(AcctReq.QueryReq req);

    AcctVo getAcct(AcctReq.BaseReq req);

    public PageInfoResp<TBatchOrder> searchBatchOrder(SearchBatchReq req);


    ResponseModel getFirstVerifyWithdrawListByPage(AcctReq.QueryReq req);

    WithdrawAcctDetailVo getFirstVerifyWithdrawDetail(AcctReq.BaseReq req);

    ResponseModel getInvoiceVerifyWithdrawListByPage(AcctReq.QueryReq req);

    WithdrawAcctDetailVo getInvoiceVerifyWithdrawDetail(AcctReq.BaseReq req);

    public PageInfoResp<TAcctDetail> getWithdrawRecord(SearchBatchReq req);

    void saveTAcct(TAcct acct);

    public List<TAcctDetail> getNoBatchDetail();

    AcctResp getAcctByPartyIdAndType(Integer partyId,String type);

    public boolean saveContract(Long detailId,String contractId,String viewUrl,String downUrl);

    public WithdrawAgreement getWithdrawAgreement(Long detailId);

    public boolean updateWithdrawAgreement(WithdrawAgreement withdrawAgreement);

    TAcct saveAcctIfNeed(Integer partyId,String type);
}
