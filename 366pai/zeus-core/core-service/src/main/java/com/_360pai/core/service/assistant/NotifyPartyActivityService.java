package com._360pai.core.service.assistant;


import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.condition.assistant.NotifyPartyActivityCondition;
import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.model.activity.AuctionActivity;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface NotifyPartyActivityService{

    PageInfo getAllByActivityId(int page, int perPage, NotifyPartyActivityCondition condition, String orderBy);

    void notifyMe(Integer activityId, String agency_code, Integer party_id, Integer accountId,short pathType);

    int cancelNotifyMe(List<Integer> ids);

    void insertAgencyPortalActivity(Integer activityId, String agency_code);

    PageInfoResp<PartyAccount> getNotifiedPartyAccountsByPage(ActivityReq.ActivityId req);

    PageInfo myNotify(int page, int perPage,Integer partyId, Integer accountId,String categoryId,String name);

    int cancel(Integer activityId, Integer partyPrimaryId, Integer accountId);

    void auctionBeAboutToBeginNotify(Integer activityId);

    void auctionBeAboutToEndNotify(Integer activityId);

    int partyIdBind(Integer partyId, Integer accountId);

    List<AuctionActivity> getBeginIn5MinuteList(Integer accountId, Integer partyId);

    List<AuctionActivity> getEndIn5MinuteList(Integer accountId, Integer partyId);
}