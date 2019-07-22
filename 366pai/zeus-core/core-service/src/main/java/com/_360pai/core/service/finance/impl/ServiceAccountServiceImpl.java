package com._360pai.core.service.finance.impl;

import com._360pai.core.condition.order.TServiceAccountMoneyCondition;
import com._360pai.core.dao.order.TServiceAccountMoneyDao;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.model.order.TServiceAccountMoney;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.finance.ServiceAccountMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-10-03 11:04
 */
@Service
public class ServiceAccountServiceImpl implements ServiceAccountMoneyService {

    @Autowired
    private TServiceAccountMoneyDao tServiceAccountMoneyDao;
    @Autowired
    private AccountService accountService;

    @Override
    public TServiceAccountMoney getAccountMoneyByPartyId(Integer partyId) {
        TServiceAccountMoneyCondition condition = new TServiceAccountMoneyCondition();
        condition.setPartyId(partyId);
        TServiceAccountMoney accountMoney = tServiceAccountMoneyDao.selectFirst(condition);
        if (accountMoney == null) {
            AccountBaseDto accountBaseByPartyId = accountService.getAccountBaseByPartyId(partyId);
            accountMoney = new TServiceAccountMoney();
            accountMoney.setAccountId(accountBaseByPartyId.getAccountId());
            accountMoney.setPartyId(partyId);
            tServiceAccountMoneyDao.insert(accountMoney);
            accountMoney = tServiceAccountMoneyDao.selectFirst(condition);
        }
        return accountMoney;
    }

    @Override
    public int addAvailableAmount(Integer partyId, BigDecimal addVar) {
        TServiceAccountMoney pojo = getAccountMoneyByPartyId(partyId);
        pojo.setAvailableAmount(pojo.getAvailableAmount().add(addVar).setScale(4, RoundingMode.HALF_UP));
        pojo.setUpdateTime(new Date());
        return tServiceAccountMoneyDao.updateById(pojo);
    }

    @Override
    public int subAvailableAmount(Integer partyId, BigDecimal subVar) {
        TServiceAccountMoney pojo = getAccountMoneyByPartyId(partyId);
        pojo.setAvailableAmount(pojo.getAvailableAmount().subtract(subVar).setScale(4, RoundingMode.HALF_UP));
        pojo.setUpdateTime(new Date());
        return tServiceAccountMoneyDao.updateById(pojo);
    }

    @Override
    public int addPendingAmount(Integer partyId, BigDecimal addVar) {
        TServiceAccountMoney pojo = getAccountMoneyByPartyId(partyId);
        pojo.setPendingAmount(pojo.getPendingAmount().add(addVar).setScale(4, RoundingMode.HALF_UP));
        pojo.setUpdateTime(new Date());
        return tServiceAccountMoneyDao.updateById(pojo);
    }

    @Override
    public int subPendingAmount(Integer partyId, BigDecimal subVar) {
        TServiceAccountMoney pojo = getAccountMoneyByPartyId(partyId);
        pojo.setPendingAmount(pojo.getPendingAmount().subtract(subVar).setScale(4, RoundingMode.HALF_UP));
        pojo.setUpdateTime(new Date());
        return tServiceAccountMoneyDao.updateById(pojo);
    }

    @Override
    public int addWithdrawAmount(Integer partyId, BigDecimal addVar) {
        TServiceAccountMoney pojo = getAccountMoneyByPartyId(partyId);
        pojo.setWithdrawAmount(pojo.getWithdrawAmount().add(addVar).setScale(4, RoundingMode.HALF_UP));
        pojo.setUpdateTime(new Date());
        return tServiceAccountMoneyDao.updateById(pojo);
    }

    @Override
    public int subWithdrawAmount(Integer partyId, BigDecimal subVar) {
        TServiceAccountMoney pojo = getAccountMoneyByPartyId(partyId);
        pojo.setWithdrawAmount(pojo.getWithdrawAmount().subtract(subVar).setScale(4, RoundingMode.HALF_UP));
        pojo.setUpdateTime(new Date());
        return tServiceAccountMoneyDao.updateById(pojo);
    }

}
