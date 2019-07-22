package com._360pai.core.facade.fastway;

import com._360pai.core.facade.fastway.req.AgencyApplyReq;

import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-11-29 14:22
 */
public interface AgencyApplyFacade {

    /**
     * 拍卖行快速通道
     * @param req
     * @return
     */
    int auctionApply(AgencyApplyReq.AuctionApply req, Integer accountId);

    /**
     * 检测机构认证信息
     * @param accountId
     * @return
     */
    Map<String, Object> agencyAuthInfo(Integer accountId, Integer partyId, Integer agencyId);

    /**
     * 机构简称校验
     * @param abbr
     */
    void checkAgencyAbbr(String abbr);
}
