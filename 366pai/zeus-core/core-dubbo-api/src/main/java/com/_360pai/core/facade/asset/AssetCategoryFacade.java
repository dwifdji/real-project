package com._360pai.core.facade.asset;

import com._360pai.core.facade.asset.req.AssetCategoryReq;
import com.github.pagehelper.PageInfo;

public interface AssetCategoryFacade {
    PageInfo findAssetCategoryList(AssetCategoryReq req);

    int addCategory(AssetCategoryReq req);

    int updateCategory(AssetCategoryReq req);

    Object getFieldItems(AssetCategoryReq req);
}
