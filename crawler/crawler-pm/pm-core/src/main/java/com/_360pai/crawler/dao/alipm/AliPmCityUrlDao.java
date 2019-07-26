package com._360pai.crawler.dao.alipm;

import com._360pai.crawler.model.alipm.TAliPmZcUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public interface AliPmCityUrlDao extends JpaRepository<TAliPmZcUrl, Serializable> {


    @Query(value = "select * from t_ali_pm_zc_url b where b.pro_name =?1  and b.num > 0  ", nativeQuery = true)
    List<TAliPmZcUrl> getAliPmCityUrlListByProName(String proName);



    @Query(value = "select * from t_ali_pm_zc_url b where b.req_url =?1    ", nativeQuery = true)
    List<TAliPmZcUrl> getAliPmZcUrlByReqUrl(String reqUrl);



    @Query(value = "select * from t_ali_pm_zc_url b where    b.num > 0  and type_name  in ('机动车')  ", nativeQuery = true)
    List<TAliPmZcUrl> getAliPmZcUrlTodoUrl();

}
