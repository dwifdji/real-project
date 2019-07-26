package com._360pai.crawler.dao.caa123;

import com._360pai.crawler.model.caa123.Caa123AreaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public interface Caa123AreaModelDao extends JpaRepository<Caa123AreaModel, Serializable> {


}
