package com._360pai.core.facade.finance;

import com._360pai.core.facade.finance.resp.AccountMoneyDTO;

/**
 * @author xiaolei
 * @create 2018-10-03 13:24
 */
public interface ServiceAccountMoneyFacade {

    /**
     * 根据partyId获取账户的金额信息
     * @param partyId
     * @return
     */
    AccountMoneyDTO getAccountMoneyByPartyId(Integer partyId);
}
