package com._360pai.seimi.service;

import com._360pai.seimi.model.TAgencyNews;

import java.util.ArrayList;

public interface AgencyNewsService {

    Integer saveBatchNews(ArrayList<TAgencyNews> tAgencyNewsList);
}
