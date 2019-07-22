package com._360pai.core.facade.disposal;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.disposal.req.DisposalBiddingReq;
import com._360pai.core.facade.disposal.resp.BiddingInfoResp;

import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-09-17 09:03
 */
public interface DisposalBiddingFacade {

    /**
     * 处置服务商查找投标进展
     * @param req
     * @return
     */
    PageInfoResp findBiddingInfoListPage(DisposalBiddingReq.GetBiddingInfoReq req);

    /**
     * 处置服务商进行投标
     * @param req
     */
    int addBiddingInfo(DisposalBiddingReq.AddBiddingReq req);

    /**
     * 查看投标详情
     * @param req
     * @return
     */
    BiddingInfoResp findBiddingDetailInfo(DisposalBiddingReq.GetBiddingInfoReq req);

    /**
     * 获取首页招商情况
     * @return
     */
    PageInfoResp findInvestmentInfo(DisposalBiddingReq.GetBiddingInfoReq req);

    /**
     * 查看方案详情
     * @param disposalId
     * @return
     */
    Map<String, Object> findDisposalProgramDetail(Integer disposalId);

    /**
     * 竞标成功
     * @param biddingId
     * @return
     */
    boolean confirmBid(Integer biddingId, Integer partyId);
}
