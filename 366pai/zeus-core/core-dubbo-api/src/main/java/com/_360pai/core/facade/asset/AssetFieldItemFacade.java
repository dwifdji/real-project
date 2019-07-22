package com._360pai.core.facade.asset;

import com._360pai.core.facade.asset.req.AssetFieldItemReq;

public interface AssetFieldItemFacade {
    int addAssetFieldItem(AssetFieldItemReq req);

    int deleteItem(AssetFieldItemReq req);

    Object updateItem(AssetFieldItemReq req);
}
