package com._360pai.seimi.dao;

import com._360pai.seimi.model.TInvestmentOffice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface InvestmentOfficeDao extends JpaRepository<TInvestmentOffice, Serializable> {
}
