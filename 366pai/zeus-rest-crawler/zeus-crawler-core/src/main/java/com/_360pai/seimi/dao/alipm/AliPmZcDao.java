package com._360pai.seimi.dao.alipm;

import com._360pai.seimi.model.alipm.TAliPmSf;
import com._360pai.seimi.model.alipm.TAliPmZc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public interface AliPmZcDao extends JpaRepository<TAliPmZc, Serializable> {


    @Query(value = "select * from t_ali_pm_zc b where b.code =?1   ", nativeQuery = true)
    List<TAliPmZc> getByCode(String code);

}
