package com._360pai.core.facade.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.asset.condition.AssetCGCondition;
import com._360pai.core.facade.asset.req.AssetAuthorizationReq;
import com._360pai.core.facade.asset.req.AssetReq;
import com._360pai.core.facade.asset.resp.AssetAuthorizationResp;
import com._360pai.core.facade.asset.resp.AssetListResp;
import com._360pai.core.facade.asset.resp.AssetResp;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface AssetFacade {
    AssetResp getAsset(Integer id);

    AssetResp getAsset(AssetReq.AssetIdReq req);

    PageInfo getAllAssetByPage(int pageNum, int pageSize);

    PageInfo selectAssetCategoryGroup(int pageNum, int pageSize);

    PageInfo selectAssetCategoryGroupByCondition(AssetCGCondition params, int pageNum, int pageSize);

    AssetListResp getAllAssetByPage(AssetReq.QueryReq assertReq);

    Object addAsset(AssetReq.AddReq req, Integer partyPrimaryId, String comeFrom);

    PageInfo myAsset(AssetReq.QueryReq req);

    AssetResp update(AssetReq.UpdateReq req);

    AssetResp withdrawAsset(AssetReq.AssetIdReq req);

    ResponseModel assetEdit(AssetReq.AddReq req);

    ResponseModel disposalAssetEdit(AssetReq.AddDisposalReq req);

    ResponseModel withfudigAssetEdit(AssetReq.AddReq req);

    Object assetDetail(AssetReq.AddReq req);

    Object makeOldData(AssetReq.AddReq req);

    Object addDisposalAsset(AssetReq.AddReq req, Integer partyPrimaryId);

    Object addWithfudigAsset(AssetReq.AddReq req, Integer partyPrimaryId);

    Object seeAssetDetail(AssetReq.AddReq req);

    Map<String, Object> productDetail(AssetReq.AddReq req);

    Object MyDetail(AssetReq.AddReq req);

    void uploadSelfReport(AssetReq.UploadReportReq req);

    AssetAuthorizationResp.PreSignTuneAuthProtocolResp preSignTuneAuthProtocol(AssetAuthorizationReq.PreSignTuneAuthProtocol req);

    AssetAuthorizationResp signTuneAuthProtocol(AssetAuthorizationReq.SignTuneAuthProtocol req);


    void draftsAsset(AssetReq.AddReq req, String partyPrimaryId);

    Object findDrafts(AssetReq.AddReq req, String partyPrimaryId);

    ResponseModel saveOrUpdateLeaseAsset(AssetReq.LeaseDataReq req);

    ResponseModel getLeaseAssetById(AssetReq.LeaseDataReq req);

    ResponseModel saveOrUpdateLeaseDraft(AssetReq.LeaseDataReq req);

    ResponseModel getLeaseDraft(AssetReq.LeaseDataReq req);
}
