package com._360pai.crawler.dao.lianjia;

import com._360pai.crawler.model.lianjia.LianJiaSellData;
import com._360pai.crawler.model.lianjia.LianJiaTranscationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author liuhaolei
 * @create 2018-04-03
 */
@Repository
public interface LianJiaSellDataDao extends JpaRepository<LianJiaSellData, Serializable> {
    @Query(value = "select * from lj_sell_data b where b.code=?1", nativeQuery = true)
    LianJiaSellData selectOneByCode(String code);
}
