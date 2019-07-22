package com._360pai.core.service.asset;

import com._360pai.core.model.asset.TAssetFieldFilterOption;

public interface TAssetFieldFilterOptionService{


    int addAssetTemplateFieldOption(TAssetFieldFilterOption params);

    int editAssetTemplateFieldOption(TAssetFieldFilterOption params);

    int deleteAssetTemplateFieldOption(TAssetFieldFilterOption params);
}