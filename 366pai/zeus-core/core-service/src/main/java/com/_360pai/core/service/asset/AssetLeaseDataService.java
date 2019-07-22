package com._360pai.core.service.asset;


import com._360pai.core.facade.asset.req.AssetReq;
import com._360pai.core.model.asset.AssetDataDrafts;
import com._360pai.core.vo.asset.AssetLeaseDataVO;

public interface AssetLeaseDataService {

    Integer saveOrUpdateLeaseAsset(AssetReq.LeaseDataReq req);

    AssetLeaseDataVO getLeaseAssetById(Integer assetId, String webFlag);

    Integer saveOrUpdateLeaseDraft(AssetReq.LeaseDataReq req);

    AssetDataDrafts getLeaseDraft(Integer partyId);
}