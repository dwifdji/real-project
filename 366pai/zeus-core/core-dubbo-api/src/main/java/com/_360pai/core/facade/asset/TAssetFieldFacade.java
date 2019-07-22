package com._360pai.core.facade.asset;

import com._360pai.core.facade.asset.req.TAssetFieldReq;
import com.github.pagehelper.PageInfo;

public interface TAssetFieldFacade {

    int insert(TAssetFieldReq req);

    int updateAssetField(TAssetFieldReq req);

    PageInfo searchAssetFields(TAssetFieldReq assetFieldReq);

    Object findAssetFieldsByGroupId(TAssetFieldReq req);
}
