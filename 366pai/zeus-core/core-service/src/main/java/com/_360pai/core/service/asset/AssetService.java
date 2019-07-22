package com._360pai.core.service.asset;


import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.asset.req.AssetReq;
import com._360pai.core.facade.asset.resp.AssetListResp;
import com._360pai.core.facade.asset.resp.AssetResp;
import com._360pai.core.facade.asset.vo.AssetVo;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.asset.TPreemptivePerson;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AssetService {
    Asset getAsset(Integer id);

    List<Asset> getAllAsset();

    AssetListResp getAllAssetByPage(AssetReq.QueryReq req);

    AssetVo getAssetById(Integer id);

    AssetResp getAsset(AssetReq.AssetIdReq req);

    boolean updateAssetById(Asset asset);

    Object addAsset(String fields, Integer partyPrimaryId, String comeFrom, Integer spvId,AssetReq.AddReq req);

    PageInfo myAsset(AssetReq.QueryReq req);

    AssetResp update(AssetReq.UpdateReq req);

    AssetResp withdrawAsset(AssetReq.AssetIdReq req);

    Object assetDetail(AssetReq.AddReq assetId);

    ResponseModel assetEdit(AssetReq.AddReq req);

    ResponseModel disposalAssetEdit(Integer assetId, String fields, Integer partyId);

    ResponseModel withfudigAssetEdit(Integer assetId, String fields);

    Object addDisposalAsset(AssetReq.AddReq req, Integer partyPrimaryId);

    Object addWithfudigAsset(String fields, Integer partyPrimaryId);

    Object seeAssetDetail(AssetReq.AddReq assetId);

    Map<String, Object> productDetail(AssetReq.AddReq req);

    Object MyDetail(AssetReq.AddReq req);

    Object makeOldData(AssetReq.AddReq req);

    boolean isSelfAsset(Integer assetId, Integer partyId);

    void uploadSelfReport(Integer assetId, BigDecimal tuneReport, String[] tuneReportUrl, Integer partyId);

    void draftsAsset(AssetReq.AddReq req, String partyPrimaryId);

    Object findDrafts(AssetReq.AddReq req, String partyPrimaryId);

    List<TPreemptivePerson> getPreemptivePersons(Integer assetId);

}
