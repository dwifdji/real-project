package com._360pai.seimi.service.Impl;

import com._360pai.seimi.dao.InvestmentRecruitmentDao;
import com._360pai.seimi.model.TInvestmentRecruitment;
import com._360pai.seimi.service.InvestmentRecruitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvestmentRecruitmentServiceImpl implements InvestmentRecruitmentService {

    @Autowired
    private InvestmentRecruitmentDao investmentRecruitmentDao;

    @Override
    public void saveBatchRecruitments(List<TInvestmentRecruitment> tInvestmentRecruitments) {
        investmentRecruitmentDao.save(tInvestmentRecruitments);
    }
}
