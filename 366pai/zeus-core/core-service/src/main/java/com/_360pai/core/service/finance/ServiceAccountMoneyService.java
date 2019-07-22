package com._360pai.core.service.finance;

import com._360pai.core.model.order.TServiceAccountMoney;

import java.math.BigDecimal;

/**
 * @author xiaolei
 * @create 2018-10-03 11:04
 */
public interface ServiceAccountMoneyService {

    TServiceAccountMoney getAccountMoneyByPartyId(Integer partyId);
    int addAvailableAmount(Integer partyId, BigDecimal addVar);
    int subAvailableAmount(Integer partyId, BigDecimal subVar);
    int addPendingAmount(Integer partyId, BigDecimal addVar);
    int subPendingAmount(Integer partyId, BigDecimal subVar);
    int addWithdrawAmount(Integer partyId, BigDecimal addVar);
    int subWithdrawAmount(Integer partyId, BigDecimal subVar);

}
