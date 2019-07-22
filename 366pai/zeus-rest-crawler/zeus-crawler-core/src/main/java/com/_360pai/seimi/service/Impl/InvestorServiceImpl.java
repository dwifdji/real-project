package com._360pai.seimi.service.Impl;

import com._360pai.seimi.dao.InvestorDao;
import com._360pai.seimi.model.TInvestor;
import com._360pai.seimi.service.InvestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
public class InvestorServiceImpl implements InvestorService {

    @Autowired
    private InvestorDao investorDao;

    @Override
    public void saveInvestor(TInvestor tInvestor) {
        investorDao.save(tInvestor);
    }

    @Override
    public TInvestor getInvestorByName(String realName) {
        return investorDao.getInvestorByName(realName);
    }
}
