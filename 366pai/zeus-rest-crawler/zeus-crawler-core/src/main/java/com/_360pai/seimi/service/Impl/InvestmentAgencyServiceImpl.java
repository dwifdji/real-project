package com._360pai.seimi.service.Impl;

import com._360pai.seimi.dao.InvestmentAgencyDao;
import com._360pai.seimi.model.TInvestmentAgency;
import com._360pai.seimi.service.InvestmentAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvestmentAgencyServiceImpl implements InvestmentAgencyService {

    @Autowired
    private InvestmentAgencyDao investmentAgencyDao;

    @Override
    public Integer saveTInvestmentAgency(TInvestmentAgency tInvestmentAgency) {
        TInvestmentAgency investmentAgency = investmentAgencyDao.save(tInvestmentAgency);
        return investmentAgency.getId();
    }
}
