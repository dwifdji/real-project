package com._360pai.seimi.dao;

import com._360pai.seimi.model.AliPmTransactionData;
import com._360pai.seimi.model.JdPmTransactionDataAssets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public interface AliPmTransactionDataDao extends JpaRepository<AliPmTransactionData, Serializable> {

    @Query(value = "select * from ali_pm_transaction_data b where b.code=?1", nativeQuery = true)
    AliPmTransactionData getOneByCode(String code);

}
