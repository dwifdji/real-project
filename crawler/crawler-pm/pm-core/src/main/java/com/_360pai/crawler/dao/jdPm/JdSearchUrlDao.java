package com._360pai.crawler.dao.jdPm;

import com._360pai.crawler.model.jdPm.JDSearchUrl;
import com._360pai.crawler.model.jdPm.JdPm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public interface JdSearchUrlDao extends JpaRepository<JDSearchUrl, Serializable> {

    @Query(value = "select * from jd_pm_search_url b where b.city_code=?1 and b.category_name = ?2", nativeQuery = true)
    JDSearchUrl findByCode(String code, String categoryName);


    @Query(value = "select * from jd_pm_search_url b where b.status='todo' and b.total_Num > 0", nativeQuery = true)
    List<JDSearchUrl> getTodoUrl();


}
