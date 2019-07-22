package com.tzCloud.core.service;

import com.github.pagehelper.PageInfo;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.facade.caseMatching.req.CaseMatchingReq;
import com.tzCloud.core.facade.caseMatching.resp.BriefResp;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/3/7 16:31
 */
public interface TBriefService {
    PageInfoResp<BriefResp> getBriefList(CaseMatchingReq.BriefSearch req);
}
