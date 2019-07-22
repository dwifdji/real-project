package com._360pai.core.service.account;


import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.account.req.PartyBlackListActionReq;
import com._360pai.core.facade.account.resp.PartyBlackListActionResp;
import com._360pai.core.facade.account.vo.PartyBlackListActionVo;

public interface PartyBlackListActionService{

    PartyBlackListActionResp partyLockInBlackList(PartyBlackListActionReq.BaseReq req);

    PartyBlackListActionResp partyReleaseFromBlackList(PartyBlackListActionReq.BaseReq req);

    PageInfoResp<PartyBlackListActionVo> getPartyBlackListActionListByPage(PartyBlackListActionReq.BaseReq req);
}