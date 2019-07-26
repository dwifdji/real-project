package com._360pai.crawler.dao.caa123;

import com._360pai.crawler.model.caa123.Caa123TranscationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public interface Caa123TransactionDataDao extends JpaRepository<Caa123TranscationData, Serializable> {
    @Query(value = "select * from t_base_caa123_pm b where b.code=?1", nativeQuery = true)
    Caa123TranscationData selectOneByCode(Integer id);
}
