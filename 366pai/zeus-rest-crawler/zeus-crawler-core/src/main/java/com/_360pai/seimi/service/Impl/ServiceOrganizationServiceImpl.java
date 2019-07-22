package com._360pai.seimi.service.Impl;

import com._360pai.seimi.dao.ServiceOrganizationDao;
import com._360pai.seimi.model.TServiceOrganization;
import com._360pai.seimi.service.ServiceOrganizationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ServiceOrganizationServiceImpl implements ServiceOrganizationService {

    @Resource
    private ServiceOrganizationDao serviceOrganizationDao;

    @Override
    public Integer saveEntity(TServiceOrganization tServiceOrganization) {
        TServiceOrganization serviceOrganization = serviceOrganizationDao.save(tServiceOrganization);
        if(serviceOrganization != null) {
            return serviceOrganization.getId();
        }
        return null;
    }
}
