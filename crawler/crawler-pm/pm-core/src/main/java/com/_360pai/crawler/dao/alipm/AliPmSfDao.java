package com._360pai.crawler.dao.alipm;

import com._360pai.crawler.model.alipm.TAliPmSf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public interface AliPmSfDao extends JpaRepository<TAliPmSf, Serializable> {


    @Query(value = "select * from t_base_ali_pm_sf b where b.code =?1   ", nativeQuery = true)
    List<TAliPmSf> getByCode(String code);


    @Query(value = "select * from t_base_ali_pm_sf b where b.lat is null or b.lat = '302' limit 200000  ", nativeQuery = true)
    List<TAliPmSf> getLatNull();


    @Query(value = "select * from t_base_ali_pm_sf b where b.id =?1   ", nativeQuery = true)
    TAliPmSf getById(Integer id);
}
