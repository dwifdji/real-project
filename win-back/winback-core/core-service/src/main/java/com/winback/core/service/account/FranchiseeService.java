package com.winback.core.service.account;

import com.winback.arch.common.AppReq;
import com.winback.arch.common.PageInfoResp;
import com.winback.core.facade._case.vo.Case;
import com.winback.core.facade.account.req.AdminAccountReq;
import com.winback.core.facade.account.req.AppAccountReq;
import com.winback.core.facade.account.resp.AppAccountResp;
import com.winback.core.facade.account.vo.Customer;
import com.winback.core.facade.account.vo.Franchisee;
import com.winback.core.facade.account.vo.FranchiseeApplyRecord;
import com.winback.core.model.account.TFranchisee;

/**
 * @author xdrodger
 * @Title: FranchiseeService
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 15:08
 */
public interface FranchiseeService {
    AppAccountResp.ApplyResp franchiseeApply(AppAccountReq.FranchiseeApplyReq req);

    String getApplyStatus(Integer accountId);

    TFranchisee findByAccountId(Integer accountId);

    TFranchisee findById(Integer id);

    PageInfoResp<FranchiseeApplyRecord> getApplyRecordListByPage(AdminAccountReq.FranchiseeQueryReq req);

    Integer franchiseeApplyApprove(AdminAccountReq.FranchiseeVerifyReq req);

    void franchiseeApplyReject(AdminAccountReq.FranchiseeVerifyReq req);

    PageInfoResp<Franchisee> getFranchiseeListByPage(AdminAccountReq.FranchiseeQueryReq req);

    Integer franchiseeUpdate(AdminAccountReq.FranchiseeUpdateReq req);

    PageInfoResp<Customer> getInviteCustomerListByPage(AppAccountReq.FranchiseeQueryReq req);

    PageInfoResp<Customer> getInviteCustomerListByPage(AdminAccountReq.FranchiseeQueryReq req);

    PageInfoResp<Case> getInviteCaseListByPage(AppAccountReq.FranchiseeQueryReq req);

    PageInfoResp<Case> getInviteCaseListByPage(AdminAccountReq.FranchiseeQueryReq req);
}
