package com.tzCloud.core.facade.caseMatching;

import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.facade.caseMatching.req.ViewCourtNumReq;
import com.tzCloud.core.facade.caseMatching.resp.ViewCourtNumResp;

public interface ViewCourtNumFacade {

    PageInfoResp<ViewCourtNumResp> findByCourtName(ViewCourtNumReq req);

}
