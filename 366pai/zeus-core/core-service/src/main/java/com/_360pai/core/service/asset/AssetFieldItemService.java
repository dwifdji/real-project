package com._360pai.core.service.asset;


import com._360pai.core.model.asset.AssetFieldItem;

public interface AssetFieldItemService {

    int insert(AssetFieldItem item);

    int delete(AssetFieldItem item);

    Object update(AssetFieldItem params);
}