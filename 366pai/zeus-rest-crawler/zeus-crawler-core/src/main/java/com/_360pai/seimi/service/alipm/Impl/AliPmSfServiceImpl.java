package com._360pai.seimi.service.alipm.Impl;

import com._360pai.seimi.dao.alipm.AliPmSfDao;
import com._360pai.seimi.dao.alipm.AliPmZcDao;
import com._360pai.seimi.model.alipm.TAliPmSf;
import com._360pai.seimi.model.alipm.TAliPmZc;
import com._360pai.seimi.service.alipm.AliPmSfService;
import com._360pai.seimi.service.alipm.AliPmZcService;
import com.alibaba.fastjson.JSON;
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
public class AliPmSfServiceImpl implements AliPmSfService {

    Logger logger = LoggerFactory.getLogger(AliPmSfServiceImpl.class);


    @Resource
    private AliPmSfDao aliPmSfDao;


    @Override
    public TAliPmSf saveAliPmSf(TAliPmSf model) {

        List<TAliPmSf> aliPmSfsList =  aliPmSfDao.getByCode(model.getCode());

        if(aliPmSfsList!=null&&aliPmSfsList.size()>0){
            TAliPmSf sf = aliPmSfsList.get(0);

            model.setId(sf.getId());
            model.setUpdateTime(new Date());
        }

        TAliPmSf pmSf = aliPmSfDao.saveAndFlush(model);

        return pmSf;
    }
}
