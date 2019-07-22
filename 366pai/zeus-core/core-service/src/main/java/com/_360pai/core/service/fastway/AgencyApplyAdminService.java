package com._360pai.core.service.fastway;

import com._360pai.core.facade.fastway.resp.AgencyAuctionDetailVO;
import com._360pai.core.model.fastway.TFastwayAgencyApply;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-11-29 16:10
 */
public interface AgencyApplyAdminService {
    PageInfo findByParam(Map<String, Object> query, int pageNum, int pageSize);
    TFastwayAgencyApply findById(Integer applyId);
    int auctionUpdate(AgencyAuctionDetailVO detailVO, Integer applyId, Integer operatorId);
    int auctionVerifyAccess(AgencyAuctionDetailVO detailVO, Integer applyId, Integer operatorId);
    int auctionVerifyDeny(String refuseReason, Integer applyId, Integer operatorId);
}
