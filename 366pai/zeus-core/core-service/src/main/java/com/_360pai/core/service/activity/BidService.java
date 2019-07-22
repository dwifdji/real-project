package com._360pai.core.service.activity;


import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.condition.activity.BidCondition;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.vo.BidRecord;
import com._360pai.core.model.activity.Bid;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BidService{

    PageInfoResp<BidRecord> getAllBidRecordsByPage(ActivityReq.ActivityId req);

    PageInfo getAllByActivityId(int page, int perPage, BidCondition condition, String orderBy);

    boolean saveBid(Bid bid);

    List<Bid> getBidListByActivityId(Integer activityId);

    Bid getBidById(Integer bidId);

    List<Bid> getBidListByActivityIdAndPartyId(Integer activityId,Integer partyId);
}