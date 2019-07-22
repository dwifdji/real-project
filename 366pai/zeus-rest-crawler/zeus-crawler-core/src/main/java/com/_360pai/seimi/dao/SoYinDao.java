package com._360pai.seimi.dao;

import com._360pai.seimi.model.SoYinContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface SoYinDao extends JpaRepository<SoYinContract, Serializable> {
    @Query(value = "select * from soyin_contract b where b.name=?1 AND small_category=?2", nativeQuery = true)
    SoYinContract getOneByName(String name, String smallCategory);

    @Query(value = "select * from soyin_contract b where b.big_category=?1", nativeQuery = true)
    List<SoYinContract> getSmallCategoryListByName(String bigCateggory);
}
