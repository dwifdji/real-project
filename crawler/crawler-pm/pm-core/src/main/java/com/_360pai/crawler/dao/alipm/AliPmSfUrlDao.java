package com._360pai.crawler.dao.alipm;

import com._360pai.crawler.model.alipm.TAliPmSfUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public interface AliPmSfUrlDao extends JpaRepository<TAliPmSfUrl, Serializable> {

    @Query(value = "select * from t_base_ali_pm_sf_url b where b.req_url =?1   ", nativeQuery = true)
    List<TAliPmSfUrl> getUrlByReqUrl(String reqUrl);


    @Query(value = "select * from t_base_ali_pm_sf_url b where  b.num>0  and  type_name in ('住宅用房','商业用房','工业用房','其他用房','土地') and status = 'todo' ", nativeQuery = true)
    List<TAliPmSfUrl> getAllToDoUrl();

}
