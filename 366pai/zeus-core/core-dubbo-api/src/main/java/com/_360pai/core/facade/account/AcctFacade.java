package com._360pai.core.facade.account;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.req.SearchBatchReq;
import com._360pai.core.facade.account.req.WithdrawReq;
import com._360pai.core.facade.account.resp.AcctResp;
import com._360pai.core.facade.account.resp.BatchOrderInfoResp;
import com._360pai.core.facade.account.resp.WithdrawDetailVo;
import com._360pai.core.facade.account.resp.WithdrawResp;
import com._360pai.core.facade.activity.resp.SignContractResp;

import java.util.List;

/**
 * @author RuQ
 * @Title: AcctFacade
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/12/3 15:20
 */
public interface AcctFacade {

    /** 提现 **/
    public WithdrawResp withdraw(WithdrawReq req);

    /** 初审 **/
    public boolean firstVerify(WithdrawReq req);

    /** 签合同 **/
    public SignContractResp signContract(WithdrawReq req);

    /** 法大大回调 **/
    public boolean signCallBack(String activityId, String contractId, boolean hasSuccess);

    /** 提交发票 **/
    public boolean submitInvoice(WithdrawReq req);

    /** 发票审核 **/
    public boolean verifyInvoice(WithdrawReq req);

    /** 审批单预览 **/
    public BatchOrderInfoResp preBatchOrder(WithdrawReq req);


    /** 保存审批单 **/
    public boolean saveOrUpdateBatchOrder(Long batchOrderId,List<String> detailIdList,Integer operatorId);


    /** 终审 **/
    public boolean lastVerify(WithdrawReq req);

    /**出款**/
    public boolean hasPay(WithdrawReq req);

    /**取消出款**/
    public boolean cancelPay(WithdrawReq req);

    /**红冲**/
    public boolean hc(WithdrawReq req);

    /** 历史审批单搜索 **/
    public ResponseModel searchBatchOrder(SearchBatchReq req);

    public ResponseModel getDetailByBatchNo(WithdrawReq req);

    public List<WithdrawDetailVo> getNoBatchDetail();

    /** 删除审批单中的某条明细 **/
    public boolean deleteDetailRef(WithdrawReq req);

    /** 提现记录 **/
    public PageInfoResp<WithdrawDetailVo> getWithdrawRecord(SearchBatchReq req);

    public AcctResp getAcctByPartyIdAndType(Integer partyId,String type);

}
