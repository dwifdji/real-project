package com._360pai.core.service.asset;


import com._360pai.core.condition.asset.AssetFieldGroupCondition;
import com._360pai.core.model.asset.AssetFieldGroup;

import java.util.List;

public interface AssetFieldGroupService {
    List<AssetFieldGroup> selectAllAssetFileGroupList();

    int insertAssetFieldGroup(AssetFieldGroup params);

    int updateAssetFieldGroup(AssetFieldGroup params);

    List<AssetFieldGroup> selectFileGroupByCondition(AssetFieldGroupCondition params);
}