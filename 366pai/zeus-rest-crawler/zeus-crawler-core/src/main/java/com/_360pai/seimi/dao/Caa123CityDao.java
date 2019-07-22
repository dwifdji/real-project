package com._360pai.seimi.dao;

import com._360pai.seimi.model.Caa123City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiaolei
 * @create 2018-11-15 17:46
 */
@Repository
public interface Caa123CityDao extends JpaRepository<Caa123City, Serializable> {

 @Query(value = "select t1.*,t2.pro_name as proName" +
            " from crawler.caa123_city t1 inner join crawler.caa123_province t2 on t1.pro_id = t2.pro_id", nativeQuery = true)
    List<Caa123City> findByJoinProvince();
}
