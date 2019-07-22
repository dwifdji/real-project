package com._360pai.seimi.dao;

import com._360pai.seimi.model.Caa123TranscationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public interface Caa123TransactionDataDao extends JpaRepository<Caa123TranscationData, Serializable> {
    @Query(value = "select * from caa123_transaction_data b where b.code=?1", nativeQuery = true)
    Caa123TranscationData selectOneByCode(Integer id);
}
