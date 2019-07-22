package com._360pai.core.service.asset;


import com._360pai.core.model.asset.AssetTemplateField;
import com.github.pagehelper.PageInfo;

public interface AssetTemplateFieldService{

    PageInfo findAssetTemplateFieldList(int page, int prePaeg);

    int addAssetTemplateField(AssetTemplateField param);

    int editAssetTemplateField(AssetTemplateField param);

    int deleteAssetTemplateField(AssetTemplateField param);

    Object detailAssetTemplateField(AssetTemplateField param);
}