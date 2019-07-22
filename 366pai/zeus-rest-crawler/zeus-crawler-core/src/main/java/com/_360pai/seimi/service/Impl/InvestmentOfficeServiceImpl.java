package com._360pai.seimi.service.Impl;

import com._360pai.seimi.dao.InvestmentOfficeDao;
import com._360pai.seimi.model.TInvestmentOffice;
import com._360pai.seimi.service.InvestmentOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestmentOfficeServiceImpl implements InvestmentOfficeService {
    @Autowired
    private InvestmentOfficeDao investmentOfficeDao;

    @Override
    public void saveBatchOffices(List<TInvestmentOffice> tInvestmentOffices) {
        investmentOfficeDao.save(tInvestmentOffices);
    }
}
