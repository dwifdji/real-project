package com._360pai.seimi.dao;

import com._360pai.seimi.model.TTransactionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public interface TransactionDataDao extends JpaRepository<TTransactionData, Serializable> {

    @Query(value = "select * from t_transaction_data b where b.title=?1", nativeQuery = true)
    TTransactionData getTransactionDataByName(String realName);
}
