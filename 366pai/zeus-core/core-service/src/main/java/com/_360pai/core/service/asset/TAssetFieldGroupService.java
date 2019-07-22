package com._360pai.core.service.asset;

import com._360pai.core.model.asset.TAssetFieldGroup;
import com.github.pagehelper.PageInfo;

public interface TAssetFieldGroupService{


    int updateAssetFieldGroup(TAssetFieldGroup params);

    PageInfo selectAllAssetFieldGroupList(int page, int perPage);

    int insertAssetFieldGroup(TAssetFieldGroup params);
}