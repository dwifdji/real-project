package com._360pai.core.service.finance.impl;

import com._360pai.core.condition.order.TServiceAccountBankCondition;
import com._360pai.core.dao.order.TServiceAccountBankDao;
import com._360pai.core.facade.finance.req.AccountBankReq;
import com._360pai.core.model.order.TServiceAccountBank;
import com._360pai.core.service.finance.ServiceAccountBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-10-06 23:47
 */
@Service
public class ServiceAccountBankServiceImpl implements ServiceAccountBankService {

    @Autowired
    private TServiceAccountBankDao tServiceAccountBankDao;

    @Override
    public int addAccountBank(AccountBankReq.AddAccountBank req) {
        updateBindingStatus(req.getPartyId());
        TServiceAccountBank bank = new TServiceAccountBank();
        bank.setBankAddress(req.getBankAddress());
        bank.setBankName(req.getBankName());
        bank.setBankNo(req.getBankNo());
        bank.setUserName(req.getUserName());
        bank.setAccountId(req.getAccountId());
        bank.setPartyId(req.getPartyId());
        int id = tServiceAccountBankDao.insert(bank);
        return id ;
    }

    @Override
    public TServiceAccountBank getBindingCardByPartyId(Integer partyId) {
        TServiceAccountBankCondition condition = new TServiceAccountBankCondition();
        condition.setPartyId(partyId);
        condition.setCurrentBiding(true);
        return tServiceAccountBankDao.selectOneResult(condition);
    }

    private void updateBindingStatus(Integer partyId) {
        TServiceAccountBank bank = new TServiceAccountBank();
        bank.setPartyId(partyId);
        bank.setCurrentBiding(false);
        bank.setUpdateTime(new Date());
        tServiceAccountBankDao.updateByPartyId(bank);
    }
}
