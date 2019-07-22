package com._360pai.core.provider.disposal;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.disposal.DisposalBiddingFacade;
import com._360pai.core.facade.disposal.req.DisposalBiddingReq;
import com._360pai.core.facade.disposal.resp.BiddingInfoResp;
import com._360pai.core.service.disposal.DisposalBiddingService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-09-17 09:36
 */
@Component
@Service(version = "1.0.0")
public class DisposalBiddingProvider implements DisposalBiddingFacade {

    @Autowired
    DisposalBiddingService disposalBiddingService;

    @Override
    public PageInfoResp findBiddingInfoListPage(DisposalBiddingReq.GetBiddingInfoReq req) {
        return disposalBiddingService.findBiddingInfoListPage(req);
    }

    @Override
    public int addBiddingInfo(DisposalBiddingReq.AddBiddingReq req) {
        return disposalBiddingService.addBiddingInfo(req);
    }

    @Override
    public BiddingInfoResp findBiddingDetailInfo(DisposalBiddingReq.GetBiddingInfoReq req) {
        return disposalBiddingService.findBiddingDetailInfo(req);
    }
    @Override
    public PageInfoResp findInvestmentInfo(DisposalBiddingReq.GetBiddingInfoReq req) {
        return disposalBiddingService.findInvestmentInfo(req);
    }

    @Override
    public Map<String, Object> findDisposalProgramDetail(Integer biddingId) {
        return disposalBiddingService.findDisposalProgramDetail(biddingId);
    }

    @Override
    public boolean confirmBid(Integer biddingId, Integer partyId) {
        return disposalBiddingService.confirmBid(biddingId, partyId);
    }
}
