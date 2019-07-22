package com._360pai.core.service.finance;

import com._360pai.core.facade.finance.req.AccountBankReq;
import com._360pai.core.model.order.TServiceAccountBank;

/**
 * @author xiaolei
 * @create 2018-10-06 23:35
 */
public interface ServiceAccountBankService {

    int addAccountBank(AccountBankReq.AddAccountBank req);

    TServiceAccountBank getBindingCardByPartyId(Integer partyId);
}
