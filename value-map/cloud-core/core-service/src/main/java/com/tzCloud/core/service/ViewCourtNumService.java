package com.tzCloud.core.service;

import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.facade.caseMatching.req.ViewCourtNumReq;
import com.tzCloud.core.facade.caseMatching.resp.ViewCourtNumResp;

/**
 * 功能描述:
 *
 * @auther: zxiao
 * @date: 2019/4/19 9:54
 */
public interface ViewCourtNumService {

    PageInfoResp<ViewCourtNumResp> findByCourtNum(ViewCourtNumReq req);

}
