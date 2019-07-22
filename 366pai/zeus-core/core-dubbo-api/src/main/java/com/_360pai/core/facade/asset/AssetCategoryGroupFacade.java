package com._360pai.core.facade.asset;

import com._360pai.core.facade.asset.req.AssetCategoryGroupReq;
import com.github.pagehelper.PageInfo;

public interface AssetCategoryGroupFacade {

    Object getAllCateGoryGroupList(AssetCategoryGroupReq req);

    PageInfo getCateGoryGroupList(AssetCategoryGroupReq req);

    Object addCateGoryGroup(AssetCategoryGroupReq req);

    Object updateCateGoryGroup(AssetCategoryGroupReq req);

    Object selectCateGoryGroupFields(AssetCategoryGroupReq req);

    void editCateGoryGroupFields(AssetCategoryGroupReq req);

    PageInfo searchResultByGroupId(AssetCategoryGroupReq req);

    PageInfo searchCategoriesByGroupId(AssetCategoryGroupReq req);

    void bind(AssetCategoryGroupReq param);
}
