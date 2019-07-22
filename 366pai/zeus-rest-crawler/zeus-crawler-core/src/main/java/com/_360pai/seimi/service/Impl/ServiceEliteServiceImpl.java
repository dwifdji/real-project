package com._360pai.seimi.service.Impl;

import com._360pai.seimi.dao.ServiceEliteDao;
import com._360pai.seimi.model.TServiceElite;
import com._360pai.seimi.service.ServiceEliteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ServiceEliteServiceImpl implements ServiceEliteService {

    @Resource
    private ServiceEliteDao serviceEliteDao;

    @Override
    public Integer saveEntity(TServiceElite tServiceElite) {
        TServiceElite saveEntity = serviceEliteDao.save(tServiceElite);
        return saveEntity.getId();
    }
}
