package com._360pai.core.service.agreement;


import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.resp.ActivityResp;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.agreement.DelegationAgreement;
import com._360pai.core.model.asset.Asset;

import java.util.List;

public interface DelegationAgreementService {

    DelegationAgreement getByActivityId(Integer activityId);

    boolean generateContract(Integer activityId);

    ActivityResp delegationSignatureUrl(ActivityReq.ActivityId req);

    ActivityResp signCallBack(ActivityReq.SignDelegationAgreementCallBackReq req);

    List<Integer> getAllSignedTimeout();

    boolean processDelegationAgreementSignedTimeout(Integer activityId);
}