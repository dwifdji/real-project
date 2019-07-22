package com._360pai.core.service.assistant;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.condition.assistant.FavoriteActivityCondition;
import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.req.FavoriteActivityReq;
import com._360pai.core.facade.applet.req.AuctionReq;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.assistant.FavoriteActivity;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface FavoriteActivityService {

    PageInfo getAllByActivityId(int page, int perPage, FavoriteActivityCondition condition, String orderBy);

    void activityFavor(Integer activityId, Integer partyId, String agencyCode, String type ,Integer resourceId, Integer accountId);

    int cancelFavor(List<Integer> id, Integer partyId);

    PageInfo myFavor(FavoriteActivityReq.Query req);

    int unFavor(Integer partyId, Integer activityId, String type ,Integer resourceId, Integer accountId);

    PageInfoResp<PartyAccount> getFavoredPartyAccountsByPage(ActivityReq.ActivityId req);

    Map<String,Object> favoriteCount(Integer accountId,Integer partyId);

    List<FavoriteActivity> getConcernedFlag(String shopId, String auctionId);

    PageInfo getMyShopFavor(AuctionReq.AuctionInfoReq req);

    int partyIdBind(Integer accountId, Integer partyId);

    List<AuctionActivity> getBeginIn5MinuteList(Integer accountId, Integer partyId);

    List<AuctionActivity> getEndIn5MinuteList(Integer accountId, Integer partyId);
}