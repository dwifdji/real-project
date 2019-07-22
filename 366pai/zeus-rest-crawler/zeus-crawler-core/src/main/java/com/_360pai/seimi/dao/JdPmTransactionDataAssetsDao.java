package com._360pai.seimi.dao;

import com._360pai.seimi.model.JdPmTransactionDataAssets;
import com._360pai.seimi.model.JdPmTransactionDataLegal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Component
public interface JdPmTransactionDataAssetsDao extends JpaRepository<JdPmTransactionDataAssets, Serializable> {

    @Query(value = "select * from jd_pm_transaction_data_assets b where b.code=?1", nativeQuery = true)
    JdPmTransactionDataAssets getOneByCode(String code);


}
