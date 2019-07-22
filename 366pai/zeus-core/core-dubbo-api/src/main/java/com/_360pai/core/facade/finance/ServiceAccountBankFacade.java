package com._360pai.core.facade.finance;

import com._360pai.core.facade.finance.req.AccountBankReq;
import com._360pai.core.facade.finance.resp.AccountBankDTO;

/**
 * @author xiaolei
 * @create 2018-10-06 23:51
 */
public interface ServiceAccountBankFacade {

    int addAccountBank(AccountBankReq.AddAccountBank req);

    AccountBankDTO getBindingCardByPartyId(Integer partyId);
}
