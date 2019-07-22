package com._360pai.seimi.service.alipm.Impl;

import com._360pai.seimi.commons.AliPmEnum;
import com._360pai.seimi.dao.alipm.AliPmCityUrlDao;
import com._360pai.seimi.dao.alipm.AliPmSfUrlDao;
import com._360pai.seimi.model.alipm.TAliPmZcUrl;
import com._360pai.seimi.model.alipm.TAliPmSfUrl;
import com._360pai.seimi.service.alipm.AliPmUrlService;
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
public class AliPmCityUrlServiceImpl implements AliPmUrlService {

    Logger logger = LoggerFactory.getLogger(AliPmCityUrlServiceImpl.class);


    @Resource
    private AliPmCityUrlDao aliPmCityUrlDao;


    @Resource
    private AliPmSfUrlDao aliPmSfUrlDao;


    @Override
    public TAliPmZcUrl saveAliPmCityUrl(TAliPmZcUrl model) {
        List<TAliPmZcUrl> urlList = aliPmCityUrlDao.getAliPmZcUrlByReqUrl(model.getReqUrl());

        if(urlList.size()>0){
            TAliPmZcUrl zcUrl =  urlList.get(0);
            if(model.getNum().equals(zcUrl.getNum())){
                return zcUrl;
            }
            model.setId(urlList.get(0).getId());
            model.setStatus(AliPmEnum.URL_STATUS.TODO.getKey());
            model.setUpdateTime(new Date());
        }

        return aliPmCityUrlDao.save(model);
    }

    @Override
    public TAliPmZcUrl findAliPmCityUrl(TAliPmZcUrl model) {
        return aliPmCityUrlDao.getOne(model.getId());
    }

    @Override
    public TAliPmZcUrl updateAliPmCityUrl(TAliPmZcUrl model) {
        return null;
    }

    @Override
    public List<TAliPmZcUrl> findAliPmCityUrlList(TAliPmZcUrl model) {
        return aliPmCityUrlDao.getAliPmCityUrlListByProName(model.getProName());
    }

    @Override
    public TAliPmSfUrl saveAliPmSfUrl(TAliPmSfUrl model) {

        // 根据url 获取数据
        List<TAliPmSfUrl> urlList = aliPmSfUrlDao.getUrlByReqUrl(model.getReqUrl());

        if(urlList.size()>0){
            TAliPmSfUrl sfUrl =  urlList.get(0);


            if(model.getNum().equals(sfUrl.getNum())){
                return sfUrl;
            }

            model.setId(urlList.get(0).getId());
            model.setStatus(AliPmEnum.URL_STATUS.TODO.getKey());
            model.setUpdateTime(new Date());
        }

        return aliPmSfUrlDao.save(model);
    }


    /**
     *
     *获取阿里司法拍卖请求url
     */
    @Override
    public List<TAliPmSfUrl> findAliPmSfUrlList(TAliPmSfUrl model) {
        return aliPmSfUrlDao.findAll();
    }

    /**
     *
     *更新城市url
     */
    @Override
    public TAliPmSfUrl updateAliPmSfUrl(TAliPmSfUrl model) {
        return aliPmSfUrlDao.saveAndFlush(model);
    }


    @Override
    public List<TAliPmSfUrl> findAliPmSfTodoUrlList() {
        return aliPmSfUrlDao.getAllToDoUrl();
    }


    @Override
    public List<TAliPmZcUrl> findAliPmZcTodoUrlList() {
        return aliPmCityUrlDao.getAliPmZcUrlTodoUrl();
    }
}
