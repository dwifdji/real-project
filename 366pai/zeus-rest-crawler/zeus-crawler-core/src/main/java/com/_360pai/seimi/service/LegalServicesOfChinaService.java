package com._360pai.seimi.service;

import com._360pai.seimi.model.LegalServicesOfChinaLawyer;
import com._360pai.seimi.model.LegalServicesOfChinaLawyerDep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author xdrodger
 * @Title: LegalServicesOfChinaService
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/15 15:08
 */
public interface LegalServicesOfChinaService {
    LegalServicesOfChinaLawyerDep findByLsswsbs(String lsswsbs);

    Page<LegalServicesOfChinaLawyerDep> findNeedToUpdate(int page, int size);

    LegalServicesOfChinaLawyerDep saveLawyerDep(LegalServicesOfChinaLawyerDep lawyerDep);

    LegalServicesOfChinaLawyer findByLsbs(String lsbs);

    LegalServicesOfChinaLawyer saveLawyer(LegalServicesOfChinaLawyer lawyer);

}
