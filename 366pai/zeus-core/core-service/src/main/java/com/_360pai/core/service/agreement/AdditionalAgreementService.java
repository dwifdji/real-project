package com._360pai.core.service.agreement;

import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.resp.ActivityResp;

import java.math.BigDecimal;
import java.util.List;

public interface AdditionalAgreementService{
    boolean generateContract(Integer activityId, BigDecimal newReservePrice);

    ActivityResp signCallBack(ActivityReq.SignAdditionalAgreementCallBackReq req);

    ActivityResp additionalSignatureUrl(ActivityReq.ActivityId req);
}