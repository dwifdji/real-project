package com._360pai.core.service.asset;

import com._360pai.core.model.asset.TAssetFieldFilter;
import com.github.pagehelper.PageInfo;

public interface TAssetFieldFilterService{


    PageInfo findAssetTemplateFieldList(int page, int perPage);

    Object detailAssetTemplateField(TAssetFieldFilter req);

    int addAssetTemplateField(TAssetFieldFilter params);

    int editAssetTemplateField(TAssetFieldFilter params);
}