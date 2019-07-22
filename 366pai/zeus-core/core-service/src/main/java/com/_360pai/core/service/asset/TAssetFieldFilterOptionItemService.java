package com._360pai.core.service.asset;

import com._360pai.core.model.asset.TAssetFieldFilterOptionItem;

public interface TAssetFieldFilterOptionItemService{


    int addTAssetFieldFilterOptionItem(TAssetFieldFilterOptionItem params);

    int editTAssetFieldFilterOptionItem(TAssetFieldFilterOptionItem params);

    int deleteTAssetFieldFilterOptionItem(TAssetFieldFilterOptionItem params);

    Object detailAssetTemplateFieldOptionItem(Integer filterOptionId, Integer filterId);
}