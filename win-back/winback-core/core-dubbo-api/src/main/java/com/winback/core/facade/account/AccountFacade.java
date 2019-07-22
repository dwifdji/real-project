package com.winback.core.facade.account;

import com.winback.arch.common.PageInfoResp;
import com.winback.core.facade._case.vo.Case;
import com.winback.core.facade.account.req.AdminAccountReq;
import com.winback.core.facade.account.req.AppAccountReq;
import com.winback.core.facade.account.req.AppletAccountReq;
import com.winback.core.facade.account.resp.AdminAccountResp;
import com.winback.core.facade.account.resp.AppAccountResp;
import com.winback.core.facade.account.resp.AppletAccountResp;
import com.winback.core.facade.account.vo.*;

/**
 * @author xdrodger
 * @Title: AccountFacade
 * @ProjectName winback
 * @Description:
 * @date 2019/1/24 15:56
 */
public interface AccountFacade {

    AppAccountResp.AccountResp login(AppAccountReq.LoginReq req);

    AppAccountResp.AccountResp register(AppAccountReq.RegisterReq req);

    String sendSmsCode(AppAccountReq.SmsReq req);

    AccountInfo getAccountInfo(Integer accountId);

    AppAccountResp.AccountResp forgetPassword(AppAccountReq.ForgetPasswordReq req);

    AppAccountResp.AccountResp modifyPassword(AppAccountReq.ModifyPasswordReq req);

    AppAccountResp.ApplyResp lawyerApply(AppAccountReq.LawyerApplyReq req);

    AppAccountResp.ApplyResp franchiseeApply(AppAccountReq.FranchiseeApplyReq req);

    AppAccountResp.AccountResp updateAccount(AppAccountReq.AccountUpdateReq req);

    boolean hasRegister(String mobile);

    PageInfoResp<Customer> getInviteCustomerListByPage(AppAccountReq.FranchiseeQueryReq req);

    PageInfoResp<Case> getInviteCaseListByPage(AppAccountReq.FranchiseeQueryReq req);

    PageInfoResp<AppMessage> getAppMessageListByPage(AppAccountReq.MessageReq req);

    AppMessage getAppMessage(AppAccountReq.MessageReq req);

    AdminAccountResp.AccountResp login(AdminAccountReq.LoginReq req);

    PageInfoResp<Account> getAccountListByPage(AdminAccountReq.AccountQueryReq req);

    PageInfoResp<Party> getPartyListByPage(AdminAccountReq.PartyQueryReq req);

    PageInfoResp<LawyerApplyRecord> getLawyerApplyRecordListByPage(AdminAccountReq.LawyerQueryReq req);

    Integer lawyerApplyApprove(AdminAccountReq.LawyerVerifyReq req);

    void lawyerApplyReject(AdminAccountReq.LawyerVerifyReq req);

    PageInfoResp<Lawyer> getLawyerListByPage(AdminAccountReq.LawyerQueryReq req);

    Integer lawyerUpdate(AdminAccountReq.LawyerUpdateReq req);

    PageInfoResp<LawFirm> getLawFirmListByPage(AdminAccountReq.LawFirmQueryReq req);

    Integer lawFirmAdd(AdminAccountReq.LawFirmAddReq req);

    Integer lawFirmUpdate(AdminAccountReq.LawFirmUpdateReq req);

    PageInfoResp<FranchiseeApplyRecord> getFranchiseeApplyRecordListByPage(AdminAccountReq.FranchiseeQueryReq req);

    Integer franchiseeApplyApprove(AdminAccountReq.FranchiseeVerifyReq req);

    void franchiseeApplyReject(AdminAccountReq.FranchiseeVerifyReq req);

    PageInfoResp<Franchisee> getFranchiseeListByPage(AdminAccountReq.FranchiseeQueryReq req);

    Integer franchiseeUpdate(AdminAccountReq.FranchiseeUpdateReq req);

    PageInfoResp<Customer> getFranchiseeInviteCustomerListByPage(AdminAccountReq.FranchiseeQueryReq req);

    PageInfoResp<Case> getFranchiseeInviteCaseListByPage(AdminAccountReq.FranchiseeQueryReq req);

    AppletAccountResp.LoginResp login(AppletAccountReq.LoginReq req);

    String sendSmsCode(AppletAccountReq.SmsReq req);

    AppletAccountResp.AccountResp bindAccount(AppletAccountReq.BindAccountReq req);

    AppletAccountInfo getAppletAccountInfo(Integer extBindId);

    PageInfoResp<AppletMessage> getAppletMessageList(AppletAccountReq.MessageReq req);

    AppletMessage getAppletMessage(AppletAccountReq.MessageReq req);

    String sendSmsCode(AdminAccountReq.SmsReq req);

    void uploadContacts(AppAccountReq.UploadContactsReq req);

    Integer updateAccount(AdminAccountReq.AccountUpdateReq req);

    PageInfoResp<ProjectManager> getProjectManagerListByPage(AdminAccountReq.AccountQueryReq req);

}
