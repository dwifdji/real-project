package com._360pai.core.service.account.impl;

import com._360pai.core.dao.account.AccountCompanyMapDao;
import com._360pai.core.model.account.AccountCompanyMap;
import com._360pai.core.service.account.AccountCompanyMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountCompanyMapServiceImpl implements AccountCompanyMapService {

    @Autowired
    private AccountCompanyMapDao accountCompanyMapDao;


    @Override
    public AccountCompanyMap getCompanyByAccountId(Integer accountId) {
        List<AccountCompanyMap> mapList = accountCompanyMapDao.getByAccountId(accountId);
        if(mapList != null && !mapList.isEmpty()){
            return mapList.get(0);
        }
        return null;
    }

    @Override
    public List<AccountCompanyMap> getCompanyListByAccountId(Integer accountId) {
        return accountCompanyMapDao.getByAccountId(accountId);
    }
}