package com._360pai.seimi.dao.alipm;

import com._360pai.seimi.model.alipm.TAliPmSfUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public interface AliPmSfUrlDao extends JpaRepository<TAliPmSfUrl, Serializable> {

    @Query(value = "select * from t_ali_pm_sf_url b where b.req_url =?1   ", nativeQuery = true)
    List<TAliPmSfUrl> getUrlByReqUrl(String reqUrl);


    @Query(value = "select * from t_ali_pm_sf_url b where b.status = 'todo' and b.num>0   ", nativeQuery = true)
    List<TAliPmSfUrl> getAllToDoUrl();

}
