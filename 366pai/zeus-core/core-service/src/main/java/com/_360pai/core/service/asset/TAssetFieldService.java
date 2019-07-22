package com._360pai.core.service.asset;

import com._360pai.core.dto.TAssetFieldDto;
import com._360pai.core.model.asset.TAssetField;
import com.github.pagehelper.PageInfo;

public interface TAssetFieldService{


    int insert(TAssetField params);

    int updateAssetField(TAssetField params);

    PageInfo searchAssetFields(TAssetFieldDto dto, int page, int perPage);

    Object findAssetFieldsByGroupId(Integer fieldGroupId);
}