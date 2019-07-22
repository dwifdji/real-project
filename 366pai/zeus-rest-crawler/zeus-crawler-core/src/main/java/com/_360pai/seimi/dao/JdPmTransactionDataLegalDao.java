package com._360pai.seimi.dao;

import com._360pai.seimi.model.JdPmTransactionDataLegal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Component
public interface JdPmTransactionDataLegalDao extends JpaRepository<JdPmTransactionDataLegal, Serializable> {

    @Query(value = "select * from jd_pm_transaction_data_legal b where b.code=?1", nativeQuery = true)
    JdPmTransactionDataLegal getOneByCode(String code);

    @Query(value = "update jd_pm_transaction_data sm set sm.priority_flag=?1 where sm.code=?2 ", nativeQuery = true)
    @Modifying
    @Transactional
    void updatePriorityFlag(String priorityFlag, String code);
}
