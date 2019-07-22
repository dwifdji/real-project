package com._360pai.seimi.dao;

import com._360pai.seimi.model.GPaiAreaModel;
import com._360pai.seimi.model.GPaiPm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * @author xdrodger
 * @Title: GPaiDao
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/14 14:09
 */
@Component
public interface GPaiPmAreaDao extends JpaRepository<GPaiAreaModel, Serializable> {

}
