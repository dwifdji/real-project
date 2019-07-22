package com.tzCloud.core.facade.legalEngine;

import com.tzCloud.core.facade.caseMatching.req.CourtReq;
import com.tzCloud.core.facade.caseMatching.resp.CourtResp;

public interface CourtFacade {
    CourtResp courtDetail(CourtReq req);

    Object getAggs(CourtReq req);

    void migrationCourtToElasticSearch();
}
