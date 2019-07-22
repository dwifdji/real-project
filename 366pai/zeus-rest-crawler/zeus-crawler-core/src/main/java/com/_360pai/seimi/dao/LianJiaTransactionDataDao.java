package com._360pai.seimi.dao;

import com._360pai.seimi.model.LianJiaAreaModel;
import com._360pai.seimi.model.LianJiaTranscationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author liuhaolei
 * @create 2018-04-03
 */
@Repository
public interface LianJiaTransactionDataDao extends JpaRepository<LianJiaTranscationData, Serializable> {
    @Query(value = "select * from lj_transaction_data b where b.code=?1", nativeQuery = true)
    LianJiaTranscationData selectOneByCode(String code);
}
