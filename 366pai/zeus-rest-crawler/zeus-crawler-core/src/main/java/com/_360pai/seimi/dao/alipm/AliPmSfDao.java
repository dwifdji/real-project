package com._360pai.seimi.dao.alipm;

import com._360pai.seimi.model.alipm.TAliPmSf;
import com._360pai.seimi.model.alipm.TAliPmSfUrl;
import com._360pai.seimi.model.alipm.TAliPmZc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public interface AliPmSfDao extends JpaRepository<TAliPmSf, Serializable> {


    @Query(value = "select * from t_ali_pm_sf b where b.code =?1   ", nativeQuery = true)
    List<TAliPmSf> getByCode(String code);

}
