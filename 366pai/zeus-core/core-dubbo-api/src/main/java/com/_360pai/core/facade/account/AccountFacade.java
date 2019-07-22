package com._360pai.core.facade.account;


import com._360pai.arch.common.ListResp;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.req.*;
import com._360pai.core.facade.account.resp.*;
import com._360pai.core.facade.account.vo.*;
import com._360pai.core.facade.fastway.resp.CompanyFundDetailVO;
import com._360pai.core.facade.fastway.resp.UserFundDetailVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AccountFacade {

    public String getDisposerApplyStatus(Integer partyId);

    String getAgencyApplyStatus(Integer accountId, Integer partyId, Integer agencyId);

    String getFundApplyStatus(Integer accountId, Integer partyId, String applyType);

    boolean updateAccountById(AccountReq accountReq);

    boolean updateUserById(UserReq req);

    boolean updateCompanyById(CompanyReq req);

    AccountResp getAccount(Long id);

    boolean registerAccount(AccountReq params);

    AccountResp findAccountByMobile(String mobile);

    AgencyResp getAgencyByCode(String code);

    AgencyResp getAgencyById(Integer id);

    UserResp getUserById(Integer userId);

    UserResp getUserByAccountId(Integer accountId);

    CompanyResp getCompanyByCompanyId(Integer companyId);

    List<CompanyResp> getCompanyByAccountId(Integer accountId);

    PartyResp getPartyById(Integer id);

    boolean applyUserAuth(ApplyUserAuthReq req);

    boolean applyCompanyAuth(ApplyCompanyAuthReq req);

    boolean applyAgencyAuth(ApplyAgencyAuthReq req);

    boolean applyFundAuth(FundProviderApplyReq req);

    boolean applyDisposeAuth(DisposeProviderApplyReq req);


    boolean verifyUser(ApplyUserAuthReq req,String operation);

    boolean verifyCompany(ApplyCompanyAuthReq req,String operation);

    boolean verifyAgency(ApplyAgencyAuthReq req,String operation);

    boolean verifyFund(FundProviderApplyReq req, String operation);

    boolean verifyDispose(DisposeProviderApplyReq req, String operation);


    AccountBaseDto getAccoutBaseByPartyId(Integer partyId);

    List<AgencyResp> searchAgency(String cityId,String name);

    PageInfoResp<AccountVo> getAccountListByPage(AccountReq.QueryReq req);

    AccountResp.DetailResp getAccountByAccountId(AccountReq.BaseReq req);

    PageInfoResp<UserApplyRecordVo> getUserApplyRecordListByPage(AccountReq.QueryReq req);

    UserApplyResp.DetailResp getUserApplyRecordById(AccountReq.BaseReq req);

    PageInfoResp<CompanyApplyRecordVo> getCompanyApplyRecordListByPage(AccountReq.QueryReq req);

    CompanyApplyResp.DetailResp getCompanyApplyRecordById(AccountReq.BaseReq req);

    PageInfoResp<UserVo> getUserListByPage(UserReq.QueryReq req);

    UserResp.DetailResp getUserById(AccountReq.BaseReq req);

    PageInfoResp<CompanyVo> getCompanyListByPage(CompanyReq.QueryReq req);

    CompanyResp.DetailResp getCompanyById(AccountReq.BaseReq req);

    UserResp updateUser(UserReq.UpdateReq req);

    CompanyResp updateCompany(CompanyReq.UpdateReq req);

    CompanyResp changeAdmin(CompanyReq.ChangeAdminReq req);

    PageInfoResp<AccountVo> getCompanyAccountListByPage(AccountReq.QueryReq req);

    CompanyResp createChannelPayCompany(CompanyReq.CreateChannelPayCompanyReq req);

    PageInfoResp<AgencyVo> getAgencyListByPage(AgencyReq.QueryReq req);

    AgencyResp.DetailResp getAgencyById(AgencyReq.BaseReq req);

    PartyBlackListActionResp partyLockInBlackList(PartyBlackListActionReq.BaseReq req);

    PartyBlackListActionResp partyReleaseFromBlackList(PartyBlackListActionReq.BaseReq req);

    PageInfoResp<PartyBlackListActionVo> getPartyBlackListActionListByPage(PartyBlackListActionReq.BaseReq req);

    PartyChannelAgentResp partySetChannelAgent(PartyChannelAgentReq.BaseReq req);

    PartyChannelAgentResp partySelectChannelAgent(PartyChannelAgentReq.BaseReq req);

    PartyChannelAgentResp partyCancelSelectChannelAgent(PartyChannelAgentReq.BaseReq req);

    CompanyResp companySetChannelPay(CompanyReq.BaseReq req);

    AgencyApplyResp agencyApply(AgencyApplyReq.CreateReq req);

    PageInfoResp<AgencyApplyRecordVo> getAgencyApplyListByPage(AgencyApplyReq.QueryReq req);

    AgencyApplyResp.DetailResp getAgencyApplyRecordById(AgencyApplyReq.BaseReq req);

    AgencyApplyResp agencyApplyUpdate(AgencyApplyReq.UpdateReq req);

    AgencyApplyResp agencyApplyApprove(AgencyApplyReq.BaseReq req);

    AgencyApplyResp agencyApplyReject(AgencyApplyReq.BaseReq req);

    AgencyResp updateAgency(AgencyReq.UpdateReq req);

    AgencyResp updateAgency(AgencyReq.UpdateDfftOrFadadaReq req);

    /**
     * 机构修改
     */
    AgencyResp updateAgency(AgencyReq.AgencyUpdateReq req);

    AgencyResp agencySetChannelAgent(AgencyReq.BaseReq req);

    AgencyResp agencySelectChannelAgent(AgencyReq.BaseReq req);

    AgencyResp agencyCancelSelectChannelAgent(AgencyReq.BaseReq req);

    AgencyResp agencyPortalOffline(AgencyReq.BaseReq req);

    AgencyResp agencyPortalOnline(AgencyReq.BaseReq req);

    AccountResp agencyAccountAdd(AccountReq.BaseReq req);

    AccountResp agencyAccountDelete(AccountReq.BaseReq req);

    AgencyResp.DfftResp agencyPaymentAccountBalance(AgencyReq.BaseReq req);

    FundProviderApplyResp fundProviderApply(FundProviderApplyReq.CreateReq req);

    PageInfoResp<FundProviderApplyVo> getFundProviderApplyListByPage(FundProviderApplyReq.QueryReq req);

    FundProviderApplyResp.DetailResp getFundProviderApply(FundProviderApplyReq.BaseReq req);

    FundProviderApplyResp approveFundProviderApply(FundProviderApplyReq.UpdateReq req);

    FundProviderApplyResp rejectFundProviderApply(FundProviderApplyReq.BaseReq req);

    PageInfoResp<FundProviderVo> getFundProviderListByPage(FundProviderReq.QueryReq req);

    FundProviderResp.DetailResp getFundProvider(FundProviderReq.BaseReq req);

    FundProviderResp updateFundProvider(FundProviderReq.UpdateReq req);

    DisposeProviderApplyResp disposeProviderApply(DisposeProviderApplyReq.CreateReq req);

    PageInfoResp<DisposeProviderApplyVo> getDisposeProviderApplyListByPage(DisposeProviderApplyReq.QueryReq req);

    DisposeProviderApplyResp.DetailResp getDisposeProviderApply(DisposeProviderApplyReq.BaseReq req);

    DisposeProviderApplyResp approveDisposeProviderApply(DisposeProviderApplyReq.BaseReq req);

    DisposeProviderApplyResp rejectDisposeProviderApply(DisposeProviderApplyReq.BaseReq req);

    PageInfoResp<DisposeProviderVo> getDisposeProviderListByPage(DisposeProviderReq.QueryReq req);

    DisposeProviderResp.DetailResp getDisposeProvider(DisposeProviderReq.BaseReq req);

    DisposeProviderResp updateDisposeProvider(DisposeProviderReq.UpdateReq req);

    PageInfo getPartnerAgencyList(AgencyReq.QueryReq req);

    ListResp<BankAccountVo> getBankAccounts(BankAccountReq.BaseReq req);

    BankAccountResp addBankAccount(BankAccountReq.CreateReq req);

    BankAccountResp updateBankAccount(BankAccountReq.UpdateReq req);

    BankAccountResp deleteBankAccount(BankAccountReq.BaseReq req);

    boolean saveSpvApply(Integer companyId,String mobile,String license,String name);

    boolean updateSpv(SpvReq req);

    List<SpvResp> getSpvListByCompanyId(Integer companyId);

    PageInfoResp<SpvVo> getSpvListByPage(Integer companyId,Integer page,Integer perPage,String source);

    SpvResp getSpvById(Integer spvId);

    boolean verifySpv(Integer spvApplyId,String operation,Integer operatorId);

    boolean checkSpvIsPendingOrApproved(String mobile);

    PageInfoResp<CompanyMemberVo> getCompanyMemberList(CompanyReq.BaseReq req);

    CompanyResp addCompanyMember(CompanyReq.AddMemberReq req);

    CompanyResp deleteCompanyMember(CompanyReq.DeleteMemberReq req);

    AgencyResp.ProfileResp agencyProfile(AgencyReq.BaseReq req);

    AgencyResp agencySetCanCheckReservePrice(AgencyReq.BaseReq req);

    public Integer getCompanyIdByAccountId(Integer accountId);

    public List<CompanyResp> getCompanyListByEmployAccountId(Integer accountId);

    void openElectronicSignatureNotify(String mobile);

    void openEasternPayNotify(String mobile);

    boolean checkCanApllyAuth(Integer accountId,String type);

    CompanyResp getCompanyByMemCode(String memCode);
    ResponseModel getApplyRecordList(AccountReq.BaseReq req);


    AccountResp.LoginResp login(AccountReq.LoginReq req);

    AccountExtBindIVo getAppletExtBind(Integer id);

    AccountExtBindIVo getNumberJumpExtBind(Integer id);

    AccountResp.BindAccountResp bindAccount(AccountReq.BindAccountReq req);

    AccountResp selectParty(AccountReq.SelectPartyReq req);

    AccountResp.AuthInfoResp getAuthInfoList(Integer accountId);


    boolean partyOperateOffline(PartyReq.OperateOfflineReq req);

    ListResp<TBankAccountVo> getTBankAccounts(TBankAccountReq.BaseReq req);

    ResponseModel addTBankAccount(TBankAccountReq.CreateReq req);

    ResponseModel unbindTBankAccount(TBankAccountReq.BaseReq req);

    PageInfoResp<InviteCommissionVo> getInviteCommissionListByPage(AcctReq.QueryReq req);

    InviteCommissionVo getInviteCommission(AcctReq.BaseReq req);

    AcctResp getAcct(Integer partyId,String type);

    PageInfoResp<AcctDetailVo> getFrontAcctDetailListByPage(AcctReq.QueryReq req);

    PageInfoResp<AcctDetailVo> getAcctDetailListByPage(AcctReq.QueryReq req);

    ResponseModel getAcctListByPage(AcctReq.QueryReq req);

    AcctVo getAcct(AcctReq.BaseReq req);

    AcctDetailVo getFrontAcctDetail(AcctReq.BaseReq req);

    PageInfoResp<CommissionVo> getMyCommissionListByPage(AcctReq.QueryReq req);

    ListResp<TBankAccountVo> getPlatformTBankAccounts(TBankAccountReq.BaseReq req);

    ResponseModel addPlatformTBankAccount(TBankAccountReq.PlatformCreateReq req);

    ResponseModel updatePlatformTBankAccount(TBankAccountReq.PlatformUpdateReq req);

    ResponseModel togglePlatformTBankAccountStatus(TBankAccountReq.BaseReq req);

    ResponseModel getFirstVerifyWithdrawListByPage(AcctReq.QueryReq req);

    WithdrawAcctDetailVo getFirstVerifyWithdrawDetail(AcctReq.BaseReq req);

    ResponseModel getInvoiceVerifyWithdrawListByPage(AcctReq.QueryReq req);

    WithdrawAcctDetailVo getInvoiceVerifyWithdrawDetail(AcctReq.BaseReq req);

    ListResp<UnfinishedTaskVo> getUnfinishedTaskList(AccountReq.BaseReq req);

    ResponseModel getViewRecords(AcctReq.ViewRecordRequest viewRecordRequest);

    public String getDisposerApplyStatusByAccountId(Integer accountId,String type);

    ResponseModel getFundProvider(Integer providerId);

    ResponseModel getFundProviderApply(Integer applyId);

    PageInfoResp<DisposeProvider> getRecommendDisposeProviderList(AccountReq.QueryReq req);

    void subscribeMp(String openId);

    ResponseModel getAgencyList(AgencyReq.BaseReq req);

    ResponseModel searchAgencyList(AgencyReq.QueryReq req);

    boolean checkUserIsSubscribeMp360(Integer extBindId);
}
