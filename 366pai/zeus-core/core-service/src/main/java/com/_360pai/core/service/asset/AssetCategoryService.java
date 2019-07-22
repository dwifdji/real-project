package com._360pai.core.service.asset;


import com._360pai.core.model.asset.AssetCategory;
import com._360pai.core.model.asset.TAssetCategory;
import com._360pai.core.model.assistant.Province;

import java.util.List;
import com._360pai.core.model.asset.AssetCategory;
import com.github.pagehelper.PageInfo;

public interface AssetCategoryService{

    PageInfo findAssetCategoryList(int page, int prePage);

    int insert(AssetCategory req);

    AssetCategory getById(Integer categoryId);
    int updateCategory(AssetCategory req);

    Object getFieldItems(Integer groupId);

    List<TAssetCategory> getAuctionAssetCategoryList();
}