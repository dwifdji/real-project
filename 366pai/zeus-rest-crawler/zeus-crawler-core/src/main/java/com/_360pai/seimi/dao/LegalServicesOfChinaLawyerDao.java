package com._360pai.seimi.dao;

import com._360pai.seimi.model.LegalServicesOfChinaLawyer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: LegalServicesOfChinaLawyerDao
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/15 15:06
 */
public interface LegalServicesOfChinaLawyerDao extends JpaRepository<LegalServicesOfChinaLawyer, Serializable> {
    LegalServicesOfChinaLawyer findByLsbs(String lsbs);
}
