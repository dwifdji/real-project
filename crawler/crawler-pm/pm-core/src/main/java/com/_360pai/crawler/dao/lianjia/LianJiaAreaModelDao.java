package com._360pai.crawler.dao.lianjia;

import com._360pai.crawler.model.lianjia.LianJiaAreaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author liuhaolei
 * @create 2018-04-03
 */
@Repository
public interface LianJiaAreaModelDao extends JpaRepository<LianJiaAreaModel, Serializable> {

}
