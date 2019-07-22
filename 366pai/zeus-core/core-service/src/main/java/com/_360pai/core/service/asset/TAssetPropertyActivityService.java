package com._360pai.core.service.asset;

import com._360pai.core.model.asset.TAssetPropertyActivity;
import com.github.pagehelper.PageInfo;

public interface TAssetPropertyActivityService {
    PageInfo propertySearch(int page, int perPage, Integer assetPropertyId);

    int addTAssetPropertyActivity(TAssetPropertyActivity copy);

    int editTAssetPropertyActivity(TAssetPropertyActivity copy);

    int deleteTAssetPropertyActivity(TAssetPropertyActivity copy);
}
