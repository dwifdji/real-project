package com._360pai.core.service.asset;

import com._360pai.core.facade.asset.req.AssetAuthorizationReq;
import com._360pai.core.facade.asset.resp.AssetAuthorizationResp;
import com._360pai.core.model.asset.TAssetAuthorization;

import java.util.List;

/**
 * @author xiaolei
 * @create 2018-11-05 13:05
 */
public interface AssetAuthorizationService {
    int signProtocol(Integer activityId, Integer partyId, String reportSource,
                     String protocolViewUrl, String protocolDownloadUrl, String[] reportUrls, String reportPrice);
    String getReportSource(Integer activityId);
    List<TAssetAuthorization> getByActivityId(Integer activityId);
    boolean isSignByProtocolType(Integer activityId, String protocolType);
    boolean isSignByProtocol(Integer activityId, String protocolSubtype);
    AssetAuthorizationResp.PreSignTuneAuthProtocolResp preSignTuneAuthProtocol(AssetAuthorizationReq.PreSignTuneAuthProtocol req);
    AssetAuthorizationResp signTuneAuthProtocol(AssetAuthorizationReq.SignTuneAuthProtocol req);
    String getAuthSource(Integer activityId);
}
