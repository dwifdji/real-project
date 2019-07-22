package com._360pai.core.facade.fastway;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.fastway.req.AgencyApplyReq;
import com._360pai.core.facade.fastway.resp.AgencyAuctionDetailVO;

/**
 * @author xiaolei
 * @create 2018-11-29 16:04
 */
public interface AgencyApplyAdminFacade {
    /**
     * admin获取拍卖行快速申请列表
     * @param req
     * @return
     */
    PageInfoResp findAgencyApplyByParam(AgencyApplyReq.AgencyFindReq req);

    /**
     *  admin 根据id 获取拍卖行详情
     * @param applyId
     * @return
     */
    AgencyAuctionDetailVO findAuctionDetailById(Integer applyId);

    /**
     *  admin 保存更新
     * @param req
     * @return
     */
    int auctionUpdate(AgencyApplyReq.AuctionUpdateReq req, Integer operatorId);

    /**
     * admin 审核通过
     * @param req
     * @param operatorId
     * @return
     */
    int auctionVerifyAccess(AgencyApplyReq.AuctionUpdateReq req, Integer operatorId);

    /**
     *  admin 审核拒绝
     * @param req
     * @param operatorId
     * @return
     */
    int auctionVerifyDeny(AgencyApplyReq.AuctionUpdateReq req, Integer operatorId);
}
