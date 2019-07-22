package com._360pai.seimi.dao;

import com._360pai.seimi.model.GPaiPm;
import com._360pai.seimi.model.RmfysszcPm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: RmfysszcPmDao
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/14 14:09
 */
@Component
public interface RmfysszcPmDao extends JpaRepository<RmfysszcPm, Serializable> {
    RmfysszcPm findByItemId(String itemId);
}
