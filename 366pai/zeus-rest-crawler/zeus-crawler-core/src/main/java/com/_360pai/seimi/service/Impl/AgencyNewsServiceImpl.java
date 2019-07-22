package com._360pai.seimi.service.Impl;

import com._360pai.seimi.dao.AgencyNewsDao;
import com._360pai.seimi.model.TAgencyNews;
import com._360pai.seimi.service.AgencyNewsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgencyNewsServiceImpl implements AgencyNewsService {

    @Resource
    private AgencyNewsDao agencyNewsDao;


    @Override
    public Integer saveBatchNews(ArrayList<TAgencyNews> tAgencyNewsList) {
        List<TAgencyNews> saveNews = agencyNewsDao.save(tAgencyNewsList);
        return saveNews.size();
    }
}
