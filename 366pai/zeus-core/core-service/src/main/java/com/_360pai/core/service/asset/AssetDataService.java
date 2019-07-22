package com._360pai.core.service.asset;


import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.asset.AssetData;

import java.util.List;

public interface AssetDataService{


    AssetData getByAssetId(Integer assetId);
}