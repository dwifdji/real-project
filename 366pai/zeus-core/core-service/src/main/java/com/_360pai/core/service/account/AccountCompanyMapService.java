package com._360pai.core.service.account;

import com._360pai.core.model.account.AccountCompanyMap;

import java.util.List;

public interface AccountCompanyMapService{

    public AccountCompanyMap getCompanyByAccountId(Integer accountId);
    public List<AccountCompanyMap> getCompanyListByAccountId(Integer accountId);

}