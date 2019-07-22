package com._360pai.core.service.asset;

import com._360pai.core.dto.AssetFieldDto;
import com._360pai.core.model.asset.AssetField;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AssetFieldService {

    List<AssetField> selectAssetFieldList();

    PageInfo selectAssetFieldAndGroup(AssetFieldDto params, Integer pageNum, Integer pageSize);

    int insertAssetField(AssetField params);

    int updateAssetField(AssetField params);

    AssetField getByName(String name);
}