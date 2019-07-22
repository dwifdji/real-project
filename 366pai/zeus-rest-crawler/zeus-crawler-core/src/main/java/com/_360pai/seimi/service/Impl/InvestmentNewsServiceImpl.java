package com._360pai.seimi.service.Impl;


import com._360pai.seimi.dao.InvestmentNewsDao;
import com._360pai.seimi.model.TInvestmentNews;
import com._360pai.seimi.service.InvestmentNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestmentNewsServiceImpl implements InvestmentNewsService {

    @Autowired
    private InvestmentNewsDao investmentNewsDao;

    @Override
    public void saveBatchNewsList(List<TInvestmentNews> tInvestmentNewsList) {
        investmentNewsDao.save(tInvestmentNewsList);
    }
}
