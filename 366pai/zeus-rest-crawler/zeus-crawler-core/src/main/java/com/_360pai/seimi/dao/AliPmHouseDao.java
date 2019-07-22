package com._360pai.seimi.dao;

import com._360pai.seimi.model.AliPmHouseLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface AliPmHouseDao extends JpaRepository<AliPmHouseLoan, Serializable> {

    @Query(value = "select max(id) from ali_pm_house_loan b where b.activity_name=?1", nativeQuery = true)
    Integer getAliPmHouseByTitle(String title);
}
