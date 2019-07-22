package com._360pai.core.facade.asset;

import com._360pai.core.facade.asset.req.TAssetCategoryOptionReq;
import com._360pai.core.facade.asset.req.TAssetCategoryReq;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

public interface TAssetCateGoryFacade {

    Object getAllCateGoryList(TAssetCategoryReq req);

    PageInfo getCateGoryList(TAssetCategoryReq req);

    Object addCateGory(TAssetCategoryReq req);

    Object updateCateGory(TAssetCategoryReq req);

    PageInfo getAllTempByCateGoryId(TAssetCategoryReq req);

    Object categoryOptions(TAssetCategoryOptionReq req);

    String getCategoryOptionNameByCategoryId(Integer categoryId);
}
