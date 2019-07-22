package com._360pai.seimi.service.alipm;

import com._360pai.seimi.model.alipm.TAliPmZcUrl;
import com._360pai.seimi.model.alipm.TAliPmSfUrl;

import java.util.List;

/**
 *
 */
public interface AliPmUrlService {

    /**
     *
     *保存城市请求url
     */
    TAliPmZcUrl saveAliPmCityUrl(TAliPmZcUrl model);


    /**
     *
     *获取请求url
     */
    TAliPmZcUrl findAliPmCityUrl(TAliPmZcUrl model);


    /**
     *
     *更新城市请求url
     */
    TAliPmZcUrl updateAliPmCityUrl(TAliPmZcUrl model);




    /**
     *
     *获取城市uri
     */
    List<TAliPmZcUrl> findAliPmCityUrlList(TAliPmZcUrl model);



    /**
     *
     *保存城市请求url
     */
    TAliPmSfUrl saveAliPmSfUrl(TAliPmSfUrl model);



    /**
     *
     *获取城市uri
     */
    List<TAliPmSfUrl> findAliPmSfUrlList(TAliPmSfUrl model);



    /**
     *
     *更新城市url
     */
    TAliPmSfUrl updateAliPmSfUrl(TAliPmSfUrl model);



    /**
     *
     *获取所有司法拍卖要请求的列表
     */
    List<TAliPmSfUrl> findAliPmSfTodoUrlList();



    /**
     *
     *获取资产拍卖即将要执行的url
     */
    List<TAliPmZcUrl> findAliPmZcTodoUrlList();
}
