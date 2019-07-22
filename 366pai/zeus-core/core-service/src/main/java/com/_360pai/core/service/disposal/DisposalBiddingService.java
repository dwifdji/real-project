package com._360pai.core.service.disposal;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.disposal.req.DisposalBiddingReq;
import com._360pai.core.facade.disposal.resp.BiddingInfoResp;

import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-09-14 11:12
 */
public interface DisposalBiddingService {

    /**
     * 处置商查找投标进展
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
     * 获取首页招商信息
     * @return
     */
    PageInfoResp findInvestmentInfo(DisposalBiddingReq.GetBiddingInfoReq req);

    /**
     * 查看投标方案
     * @param biddingId
     * @return
     */
    Map<String, Object> findDisposalProgramDetail(Integer biddingId);

    /**
     * 竞标成功
     * @param biddingId
     * @return
     */
    boolean confirmBid(Integer biddingId, Integer partyId);

    boolean biddingFlag(Integer disposalId, Integer partyId);

}
