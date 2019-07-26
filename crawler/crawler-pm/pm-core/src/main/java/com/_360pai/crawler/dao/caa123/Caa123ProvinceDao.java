package com._360pai.crawler.dao.caa123;

import com._360pai.crawler.model.caa123.Caa123Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2018-11-15 17:47
 */
@Repository
public interface Caa123ProvinceDao extends JpaRepository<Caa123Province, Serializable> {
}
