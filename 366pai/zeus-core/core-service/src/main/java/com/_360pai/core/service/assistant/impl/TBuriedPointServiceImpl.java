package com._360pai.core.service.assistant.impl;

import com._360pai.core.dao.assistant.TBuriedPointDao;
import com._360pai.core.dao.assistant.TFrontErrorDao;
import com._360pai.core.model.assistant.TBuriedPoint;
import com._360pai.core.model.assistant.TFrontError;
import com._360pai.core.service.assistant.TBuriedPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/10/8 12:32
 */
@Service
public class TBuriedPointServiceImpl implements TBuriedPointService {
    @Autowired
    private TBuriedPointDao tBuriedPointDao;
    @Autowired
    private TFrontErrorDao frontErrorDao;

    @Override
    public void insert(TBuriedPoint tBuriedPoint) {
        tBuriedPointDao.insert(tBuriedPoint);
    }

    @Override
    public void insertFrontError(TFrontError model) {
        frontErrorDao.insert(model);
    }
}
