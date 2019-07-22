package com._360pai.seimi.service.Impl;

import com._360pai.seimi.dao.AgencyRecruitmentDao;
import com._360pai.seimi.model.TAgencyRecruitment;
import com._360pai.seimi.service.AgencyRecruitmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AgencyRecruitmentServiceImpl implements AgencyRecruitmentService {

    @Resource
    private AgencyRecruitmentDao agencyRecruitmentDao;

    @Override
    public Integer savaBatchList(List<TAgencyRecruitment> tAgencyRecruitments) {
        List<TAgencyRecruitment> saveCounts = agencyRecruitmentDao.save(tAgencyRecruitments);
        return saveCounts.size();
    }
}
