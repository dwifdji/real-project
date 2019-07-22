package com._360pai.core.service.asset;


import com._360pai.core.model.asset.AssetTemplateFieldOption;

import java.util.List;

public interface AssetTemplateFieldOptionService {


    int addAssetTemplateFieldOption(AssetTemplateFieldOption params);

    int editAssetTemplateFieldOption(AssetTemplateFieldOption params);

    int editWeightAssetTemplateFieldOption(AssetTemplateFieldOption params);

    int deleteAssetTemplateFieldOption(AssetTemplateFieldOption params);
}