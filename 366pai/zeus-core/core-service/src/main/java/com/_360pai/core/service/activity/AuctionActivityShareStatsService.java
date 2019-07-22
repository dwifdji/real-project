package com._360pai.core.service.activity;


import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.condition.activity.AuctionActivityShareStatsCondition;
import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.resp.ActivityResp;
import com.github.pagehelper.PageInfo;

public interface AuctionActivityShareStatsService{

    PageInfoResp<PartyAccount> getSharePartyAccountsByPage(ActivityReq.ActivityId req);

    PageInfo getAllByActivityId(int page, int perPage, AuctionActivityShareStatsCondition condition, String orderBy);

    void activityView(Integer activityId, String agencyCode,Integer incrementViewCountNum);

    ActivityResp share(ActivityReq.ActivityId req);
}