package com._360pai.seimi.dao;

import com._360pai.seimi.model.TInvestmentNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface InvestmentNewsDao extends JpaRepository<TInvestmentNews, Serializable> {

}
