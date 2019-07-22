package com._360pai.core.facade;

import com._360pai.core.facade.asset.req.TAssetPropertyActivityReq;
import com.github.pagehelper.PageInfo;

public interface TAssetPropertyActivityFacade {
    PageInfo propertySearch(TAssetPropertyActivityReq req);

    int addTAssetPropertyActivity(TAssetPropertyActivityReq req);

    int editTAssetPropertyActivity(TAssetPropertyActivityReq req);

    int deleteTAssetPropertyActivity(TAssetPropertyActivityReq req);
}
