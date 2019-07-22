package com._360pai.seimi.dao;

import com._360pai.seimi.model.Caa123Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2018-11-15 17:47
 */
@Repository
public interface Caa123ProvinceDao extends JpaRepository<Caa123Province, Serializable> {
}
