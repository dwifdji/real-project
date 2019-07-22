package com._360pai.seimi.dao;

import com._360pai.seimi.model.Caa123Province;
import com._360pai.seimi.model.LianJiaAreaModel;
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
