package com._360pai.core.service.asset;

import com._360pai.core.condition.asset.TAssetCategoryCondition;
import com._360pai.core.model.asset.TAssetCategory;
import com._360pai.core.model.asset.TAssetCategoryOption;
import com.github.pagehelper.PageInfo;

public interface TAssetCategoryService{


    Object getAllCateGoryList(TAssetCategoryCondition categoryCondition);

    PageInfo getCateGoryList(int page, int perPage,TAssetCategoryCondition categoryCondition);

    Object addCateGory(TAssetCategory copyTAssetCategory);

    Object updateCateGory(TAssetCategory copyTAssetCategory);

    PageInfo getAllTempByCateGoryId(int page, int perPage, Integer categoryId);

    Object categoryOptions(Integer assetCategoryId);

    TAssetCategoryOption categoryOptionByOptionId(Integer optionId);

    TAssetCategory getCategoryById(Integer categoryId);
}