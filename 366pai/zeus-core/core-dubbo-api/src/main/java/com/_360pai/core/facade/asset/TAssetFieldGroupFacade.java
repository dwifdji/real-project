package com._360pai.core.facade.asset;

import com._360pai.core.facade.asset.req.TAssetFieldGroupReq;
import com.github.pagehelper.PageInfo;

public interface TAssetFieldGroupFacade {

    int insertAssetFieldGroup(TAssetFieldGroupReq req);

    int updateAssetFieldGroup(TAssetFieldGroupReq req);

    PageInfo selectAllAssetFieldGroupList(TAssetFieldGroupReq req);
}
