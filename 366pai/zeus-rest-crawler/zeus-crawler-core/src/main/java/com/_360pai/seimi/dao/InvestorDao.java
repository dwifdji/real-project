package com._360pai.seimi.dao;

import com._360pai.seimi.model.TInvestmentOffice;
import com._360pai.seimi.model.TInvestor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;

public interface InvestorDao extends JpaRepository<TInvestor, Serializable> {
    @Query(value = "select * from t_investor b where b.investor_name=?1", nativeQuery = true)
    TInvestor getInvestorByName(String realName);
}
