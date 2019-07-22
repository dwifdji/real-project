package com._360pai.core.service.account;


import com._360pai.core.facade.account.req.PartyReq;
import com._360pai.core.facade.account.resp.PartyResp;
import com._360pai.core.model.account.TParty;




public interface PartyService{

        TParty findPartyById(Integer id);

        boolean saveParty(TParty party);

        boolean updateById(TParty party);

        boolean operateOffline(PartyReq.OperateOfflineReq req);
}