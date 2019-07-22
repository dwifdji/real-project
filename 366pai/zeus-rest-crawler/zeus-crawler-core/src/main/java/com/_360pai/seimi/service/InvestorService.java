package com._360pai.seimi.service;

import com._360pai.seimi.model.TInvestor;

public interface InvestorService {

    void saveInvestor(TInvestor tInvestor);

    TInvestor getInvestorByName(String realName);
}
