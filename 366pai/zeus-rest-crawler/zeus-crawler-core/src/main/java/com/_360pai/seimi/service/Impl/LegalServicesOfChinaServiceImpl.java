package com._360pai.seimi.service.Impl;

import com._360pai.seimi.dao.LegalServicesOfChinaLawyerDao;
import com._360pai.seimi.dao.LegalServicesOfChinaLawyerDepDao;
import com._360pai.seimi.model.LegalServicesOfChinaLawyer;
import com._360pai.seimi.model.LegalServicesOfChinaLawyerDep;
import com._360pai.seimi.service.LegalServicesOfChinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xdrodger
 * @Title: LegalServicesOfChinaServiceImpl
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/15 15:08
 */
@Service
public class LegalServicesOfChinaServiceImpl implements LegalServicesOfChinaService {
    @Autowired
    private LegalServicesOfChinaLawyerDao lawyerDao;
    @Autowired
    private LegalServicesOfChinaLawyerDepDao lawyerDepDao;

    @Override
    public LegalServicesOfChinaLawyerDep findByLsswsbs(String lsswsbs) {
        return lawyerDepDao.findByLsswsbs(lsswsbs);
    }

    @Override
    public Page<LegalServicesOfChinaLawyerDep> findNeedToUpdate(int page, int size) {
        return lawyerDepDao.findByLsswsmcIsNull(new PageRequest(page, size));
    }

    @Override
    public LegalServicesOfChinaLawyerDep saveLawyerDep(LegalServicesOfChinaLawyerDep lawyerDep) {
        return lawyerDepDao.save(lawyerDep);
    }

    @Override
    public LegalServicesOfChinaLawyer findByLsbs(String lsbs) {
        return lawyerDao.findByLsbs(lsbs);
    }

    @Override
    public LegalServicesOfChinaLawyer saveLawyer(LegalServicesOfChinaLawyer lawyer) {
        return lawyerDao.save(lawyer);
    }
}
