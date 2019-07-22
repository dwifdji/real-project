package com._360pai.core.service.asset;


import com._360pai.core.condition.asset.AssetPropertyCondition;
import com._360pai.core.model.asset.AssetProperty;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AssetPropertyService {


    AssetProperty getById(Integer propertyId);

    PageInfo getPropertiesList(int page, int perPage, AssetPropertyCondition condition);

    int addAssetProperties(AssetProperty copy);

    int editAssetProperties(AssetProperty param);

    Object getProperties();
    
	List<AssetProperty> getAssetPropertyListByType(String type);


}