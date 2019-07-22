package com._360pai.seimi.service;

import com._360pai.seimi.model.TInvestmentNews;

import java.util.ArrayList;
import java.util.List;

public interface InvestmentNewsService {

    void saveBatchNewsList(List<TInvestmentNews> tInvestmentNewsList);
}
