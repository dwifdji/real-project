package com._360pai.seimi.dao;

import com._360pai.seimi.model.TInvestmentAgency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface InvestmentAgencyDao extends JpaRepository<TInvestmentAgency, Serializable> {

}
