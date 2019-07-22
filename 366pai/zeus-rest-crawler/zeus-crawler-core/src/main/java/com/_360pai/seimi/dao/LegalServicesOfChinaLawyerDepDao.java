package com._360pai.seimi.dao;

import com._360pai.seimi.model.LegalServicesOfChinaLawyerDep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: LegalServicesOfChinaLawyerDepDao
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/15 15:07
 */
public interface LegalServicesOfChinaLawyerDepDao extends JpaRepository<LegalServicesOfChinaLawyerDep, Serializable> {

    LegalServicesOfChinaLawyerDep findByLsswsbs(String lsswsbs);


    //@Query(value = "select * from crawler.zgfv_12348_lawerdep2 where lsswsmc is null",
    //        countQuery = "select count(*) from crawler.zgfv_12348_lawerdep2 where lsswsmc is null",
    //        nativeQuery = true)
    Page<LegalServicesOfChinaLawyerDep> findByLsswsmcIsNull(Pageable pageable);
}
