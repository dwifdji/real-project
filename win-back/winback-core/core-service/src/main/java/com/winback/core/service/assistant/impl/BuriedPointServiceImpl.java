package com.winback.core.service.assistant.impl;

import com.winback.core.dao.assistant.TBuriedPointDao;
import com.winback.core.model.assistant.TBuriedPoint;
import com.winback.core.service.assistant.BuriedPointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xdrodger
 * @Title: BuriedPointServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/2/15 09:28
 */
@Slf4j
@Service
public class BuriedPointServiceImpl implements BuriedPointService {

    @Autowired
    private TBuriedPointDao buriedPointDao;

    @Override
    public Integer insert(TBuriedPoint model) {
        buriedPointDao.insert(model);
        return model.getId();
    }

    @Override
    public Integer update(TBuriedPoint model) {
        return buriedPointDao.updateById(model);
    }
}
