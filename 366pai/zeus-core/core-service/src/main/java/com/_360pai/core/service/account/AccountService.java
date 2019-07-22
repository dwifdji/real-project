package com._360pai.core.service.account;


import com._360pai.arch.common.ListResp;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.facade.account.req.AccountReq;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.account.resp.AccountResp;
import com._360pai.core.facade.account.vo.*;
import com._360pai.core.model.account.TAccount;

import java.util.List;
import java.util.Map;

public interface AccountService {

    public String getDisposerApplyStatus(Integer partyId);

    String getAgencyApplyStatus(Integer accountId, Integer partyId, Integer agencyId);

    String getFundApplyStatus(Integer accountId, Integer partyId, String applyType);

    TAccount selectByPrimaryKey(Integer id);

    TAccount findAccountByMobile(String mobile);

    int updateById(TAccount account);

    int insert(TAccount param);

    AccountBaseDto getAccountBaseByPartyId(Integer partyId);

    AccountBaseDto getAcctBaseByPartyIdAndType(Integer partyId,String type);

    TAccount getAgencyAdminAccount(Integer agencyId);

    PageInfoResp<AccountVo> getAccountListByPage(AccountReq.QueryReq req);

    AccountVo getAccountById(AccountReq.BaseReq req);

    PageInfoResp<AccountVo> getCompanyListByPage(AccountReq.QueryReq req);

    AccountResp agencyAccountAdd(AccountReq.BaseReq req);

    AccountResp agencyAccountDelete(AccountReq.BaseReq req);

    String getNotifierMobile(Integer partyId);

    String getAgencyNotifierMobile(Integer agencyId);

    String getShopNotifierMobile(Integer shopId);

    List<String> getPlatformNotifierMobile();

    PartyAccount getPartyAccountById(Integer id);

    PageInfoResp<ApplyRecordVo> getApplyRecordList(AccountReq.BaseReq req);

    AccountResp.LoginResp login(AccountReq.LoginReq req);

    AccountExtBindIVo getAppletExtBind(Integer id);

    AccountExtBindIVo getNumberJumpExtBind(Integer id);

    AccountResp.BindAccountResp bindAccount(AccountReq.BindAccountReq req);

    AccountResp selectParty(AccountReq.SelectPartyReq req);

    AccountResp.AuthInfoResp getAuthInfoList(Integer accountId);

    boolean setExtBindCurrentPartyIdIfNeed(Integer accountId, Integer partyId, PartyEnum.Type type);

    void syncShopIfNeed(Integer accountId, Integer partyId, PartyEnum.Type type);

    ListResp<String> getUnfinishedTaskList(AccountReq.BaseReq req);

    Map<String, Object> checkAccountDispose(String mobile);

    public String getDisposerApplyStatusByAccountId(Integer accountId,String type);

    String getAgencyApplyStatusByAccountId(Integer accountId,Integer partyId);

    void subscribeMp(String openId);

    boolean checkUserIsSubscribeMp360(Integer extBindId);
}