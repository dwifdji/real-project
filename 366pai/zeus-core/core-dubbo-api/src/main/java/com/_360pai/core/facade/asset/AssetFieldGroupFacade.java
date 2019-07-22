package com._360pai.core.facade.asset;

import com._360pai.core.facade.asset.req.AssetFieldGroupReq;
import com.github.pagehelper.PageInfo;

public interface AssetFieldGroupFacade {

    int updateAssetFieldGroup(AssetFieldGroupReq req);

    PageInfo selectAllAssetFieldGroupList(AssetFieldGroupReq req);
}
