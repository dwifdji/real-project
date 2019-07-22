package com._360pai.seimi.dao;

import com._360pai.seimi.model.AreaModel;
import com._360pai.seimi.model.Caa123AreaModel;
import com._360pai.seimi.model.GPaiAreaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public interface Caa123AreaModelDao extends JpaRepository<Caa123AreaModel, Serializable> {


}
