package com._360pai.seimi.service.alipm.Impl;

import com._360pai.seimi.dao.alipm.AliPmZcDao;
import com._360pai.seimi.model.alipm.TAliPmSf;
import com._360pai.seimi.model.alipm.TAliPmZc;
import com._360pai.seimi.service.alipm.AliPmZcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class AliPmZcServiceImpl implements AliPmZcService {

    Logger logger = LoggerFactory.getLogger(AliPmZcServiceImpl.class);


    @Resource
    private AliPmZcDao aliPmZcDao;


    @Override
    public TAliPmZc saveAliPmZc(TAliPmZc model) {

        List<TAliPmZc> aliPmZcList =  aliPmZcDao.getByCode(model.getCode());

        if(aliPmZcList!=null&&aliPmZcList.size()>0){
            TAliPmZc sf = aliPmZcList.get(0);
            model.setId(sf.getId());
            model.setUpdateTime(new Date());
        }

        return aliPmZcDao.save(model);
    }
}
