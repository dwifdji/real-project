package com._360pai.core.facade.asset;

import com._360pai.core.facade.asset.req.AssetFieldReq;
import com.github.pagehelper.PageInfo;

public interface AssetFieldFacade {

    int insert(AssetFieldReq req);

    Object updateAssetField(AssetFieldReq req);

    PageInfo searchAssetFields(AssetFieldReq assetFieldReq);
}
