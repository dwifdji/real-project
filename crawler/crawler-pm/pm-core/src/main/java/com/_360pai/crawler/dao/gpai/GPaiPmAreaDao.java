package com._360pai.crawler.dao.gpai;

import com._360pai.crawler.model.gpai.GPaiAreaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;

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
