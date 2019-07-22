package com._360pai.seimi.service.Impl;

import com._360pai.seimi.dao.GPaiPmDao;
import com._360pai.seimi.dao.RmfysszcPmDao;
import com._360pai.seimi.model.GPaiPm;
import com._360pai.seimi.model.RmfysszcPm;
import com._360pai.seimi.service.RmfysszcPmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author xdrodger
 * @Title: GPaiPmServiceImpl
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/14 14:10
 */
@Service
@Slf4j
public class RmfysszcPmServiceImpl implements RmfysszcPmService {
    @Autowired
    private RmfysszcPmDao rmfysszcPmDao;

    @Override
    public RmfysszcPm saveItem(RmfysszcPm rmfysszcPm) {
        return rmfysszcPmDao.save(rmfysszcPm);
    }

    @Override
    public RmfysszcPm findByItemId(String itemId) {
        return rmfysszcPmDao.findByItemId(itemId);
    }
}
