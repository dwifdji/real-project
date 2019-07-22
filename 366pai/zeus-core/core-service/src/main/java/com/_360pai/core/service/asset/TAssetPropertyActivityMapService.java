package com._360pai.core.service.asset;

import com._360pai.core.model.asset.TAssetPropertyActivityMap;
import com.github.pagehelper.PageInfo;

public interface TAssetPropertyActivityMapService {
    int insertTAssetPropertyActivityMap(TAssetPropertyActivityMap params);

    int updateTAssetPropertyActivityMap(TAssetPropertyActivityMap params);

    PageInfo selectTAssetPropertyActivityMap(int page, int perPage);
}
