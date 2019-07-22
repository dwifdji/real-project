package com._360pai.core.facade.asset;

import com._360pai.core.facade.asset.req.TAssetPropertyActivityMapReq;
import com.github.pagehelper.PageInfo;

public interface TAssetPropertyActivityMapFacade {

    int insertTAssetPropertyActivityMap(TAssetPropertyActivityMapReq req);

    int updateTAssetPropertyActivityMap(TAssetPropertyActivityMapReq req);

    PageInfo selectTAssetPropertyActivityMap(TAssetPropertyActivityMapReq req);
}
