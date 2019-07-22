package com._360pai.seimi.dao;

import com._360pai.seimi.model.TInvestmentRecruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface InvestmentRecruitmentDao extends JpaRepository<TInvestmentRecruitment, Serializable> {

}
