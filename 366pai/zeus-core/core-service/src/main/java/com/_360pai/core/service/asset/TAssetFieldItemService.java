package com._360pai.core.service.asset;

import com._360pai.core.model.asset.TAssetFieldItem;

public interface TAssetFieldItemService{

    Object getTemplateGroupField(TAssetFieldItem tAssetFieldItem);

    int deleteTemplateGroupField(Integer id);
}