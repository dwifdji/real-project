package com._360pai.core.service.account;


import com._360pai.core.facade.account.req.PartyChannelAgentReq;
import com._360pai.core.facade.account.resp.PartyChannelAgentResp;
import com._360pai.core.model.account.PartyChannelAgent;

public interface PartyChannelAgentService{
    PartyChannelAgentResp partySetChannelAgent(PartyChannelAgentReq.BaseReq req);

    PartyChannelAgentResp partySelectChannelAgent(PartyChannelAgentReq.BaseReq req);

    PartyChannelAgentResp partyCancelSelectChannelAgent(PartyChannelAgentReq.BaseReq req);

    PartyChannelAgent findChannelByPartyId(Integer partyId);
}