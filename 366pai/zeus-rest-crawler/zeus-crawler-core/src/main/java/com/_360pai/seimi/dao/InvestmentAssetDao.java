package com._360pai.seimi.dao;

import com._360pai.seimi.model.TInvestmentAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public interface InvestmentAssetDao extends JpaRepository<TInvestmentAsset, Serializable> {

}
