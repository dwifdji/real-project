package com._360pai.seimi.dao;

import com._360pai.seimi.model.Lawyer345Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public interface Lawyer345Dao extends JpaRepository<Lawyer345Contract, Serializable> {

    @Query(value = "select * from t_lawyer345_contract b where b.name=?1 AND small_category=?2", nativeQuery = true)
    Lawyer345Contract getOneByCategoryName(String name, String smallCategory);

    @Query(value = "select * from t_lawyer345_contract", nativeQuery = true)
    List<Lawyer345Contract> getLawyer345ContractList();

    @Query(value = "update t_lawyer345_contract sm set sm.name=?1 where sm.id=?2 ", nativeQuery = true)
    @Modifying
    @Transactional
    void updateById(String name, Integer id);

    @Query(value = "select * from t_lawyer345_contract b where b.name=?1 AND small_category=?2", nativeQuery = true)
    List<Lawyer345Contract> getOneByCategoryListName(String name, String smallCategory);

}
