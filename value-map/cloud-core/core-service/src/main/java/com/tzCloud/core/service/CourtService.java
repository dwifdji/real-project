package com.tzCloud.core.service;

import com.tzCloud.core.facade.caseMatching.req.CourtReq;
import com.tzCloud.core.facade.caseMatching.resp.CourtResp;
import com.tzCloud.core.facade.legalEngine.vo.Court;

public interface CourtService {


    CourtResp findByCourtName(CourtReq req);

    Court convertToCourt(String courtName);
}
