package com._360pai.seimi.dao;

import com._360pai.seimi.model.TAgencyRecruitment;
import com._360pai.seimi.model.TServiceOrganization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface AgencyRecruitmentDao extends JpaRepository<TAgencyRecruitment, Serializable> {
}
