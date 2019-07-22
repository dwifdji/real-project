package com._360pai.seimi.service;

import com._360pai.seimi.model.TInvestmentRecruitment;

import java.util.ArrayList;
import java.util.List;

public interface InvestmentRecruitmentService {

    void saveBatchRecruitments(List<TInvestmentRecruitment> tInvestmentRecruitments);
}
