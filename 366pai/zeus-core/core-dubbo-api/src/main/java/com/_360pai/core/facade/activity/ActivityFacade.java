package com._360pai.core.facade.activity;

import com._360pai.arch.common.ListResp;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.vo.ActivityPoster;
import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.req.FavoriteActivityReq;
import com._360pai.core.facade.activity.resp.ActivityResp;
import com._360pai.core.facade.activity.vo.AuctionActivityVo;
import com._360pai.core.facade.activity.vo.BidRecord;
import com._360pai.core.facade.applet.req.AuctionReq;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import netscape.javascript.JSObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author : whisky_vip
 * @date : 2018/8/15 18:30
 */
public interface ActivityFacade {
    ActivityResp getActivity(ActivityReq.ActivityId req);

    PageInfoResp<AuctionActivityVo> getAllByPage(ActivityReq.QueryReq req);

    ActivityResp platformApprove(ActivityReq.ActivityId req);

    ActivityResp platformReject(ActivityReq.ActivityId req);

    ActivityResp agencyApprove(ActivityReq.ActivityId req);

    ActivityResp agencyReject(ActivityReq.ActivityId req);

    PageInfoResp<BidRecord> getAllBidRecordsByPage(ActivityReq.ActivityId req);

    PageInfoResp<PartyAccount> getParticipatingPartiesByPage(ActivityReq.ActivityId req);

    PageInfoResp<PartyAccount> getFavoredPartyAccountsByPage(ActivityReq.ActivityId req);

    PageInfoResp<PartyAccount> getNotifiedPartyAccountsByPage(ActivityReq.ActivityId req);

    PageInfoResp<PartyAccount> getSharePartyAccountsByPage(ActivityReq.ActivityId req);

    ActivityResp forceWithdraw(ActivityReq.ActivityId req);

    Object getById(Integer activityId);

    void activityView(ActivityReq.ActivityId req);

    void activityFavor(ActivityReq.ActivityId req);

    int cancelFavor(ActivityReq.ActivityId req);

    PageInfo myFavor(FavoriteActivityReq.Query req);

    int unFavor(ActivityReq.ActivityId req);

    PageInfo activityList(ActivityReq.QueryReq req);

    PageInfoResp<AuctionActivityVo> getAgencyPortalActivityListByPage(ActivityReq.QueryReq req);

    PageInfoResp<AuctionActivityVo> getAvailablePlatformActivityListByPage(ActivityReq.QueryReq req);

    ActivityResp publishUnion(ActivityReq.ActivityId req);

    ActivityResp cancelUnion(ActivityReq.ActivityId req);

    PageInfo search(ActivityReq.Search req);

    PageInfoResp<PartyAccount> getAgencyParticipatingPartiesByPage(ActivityReq.ActivityId req);

    ActivityResp updateActivity(ActivityReq.UpdateReq req);

    Object activityDetail(ActivityReq.ActivityId req);

    ActivityResp delegationSignatureUrl(ActivityReq.ActivityId req);

    ActivityResp additionalSignatureUrl(ActivityReq.ActivityId req);

    ActivityResp signDelegationAgreementCallBack(ActivityReq.SignDelegationAgreementCallBackReq req);

    ActivityResp signAdditionalAgreementCallBack(ActivityReq.SignAdditionalAgreementCallBackReq req);

    Object getRankActivity(ActivityReq.ActivityId req);

    Map<Object, Map<Object, Object>> getSystemDict();

    Object staffAndAuctioneer(ActivityReq.ActivityId req);

    Map<String,Object> favoriteCount(ActivityReq.ActivityId req);

    ActivityResp bindStaff(ActivityReq.ActivityId req);

    ActivityResp unbindStaff(ActivityReq.ActivityId req);

    ActivityResp share(ActivityReq.ActivityId req);

    ActivityResp.DelegationAgreement getDelegationAgreement(ActivityReq.ActivityId req);

    ActivityResp modifyVisibility(ActivityReq.ActivityId req);

    Map<String, Object> unionData(ActivityReq.ActivityId req);

    BigDecimal checkReservePrice(Integer activityId);

    boolean modifyReservePrice(ActivityReq.ModifyReservePriceReq req);

    PageInfoResp exportActivityList(ActivityReq.ActivityId req);

    ActivityResp withdrawActivity(ActivityReq.ActivityId req);

    ResponseModel getConcernedFlag(AuctionReq.AuctionInfoReq req);

    PageInfo getMyShopFavor(AuctionReq.AuctionInfoReq req);



    /**
     * 获取机构连拍列表
     */
    ResponseModel getJointList(ActivityReq.JointListReq req);


    /**
     * 添加或者取消连拍
     */
    ResponseModel saveOrCancelJoint(ActivityReq.JointReq req);


    /**
     * 获取下载机构连拍列表
     */
    List<Map<String, Object>> getDownloadJointList(ActivityReq.JointListReq req);


    ResponseModel getShareInfo(ActivityReq.ActivityId req);

    ResponseModel getActivityServiceProvider(ActivityReq.ActivityId req);

    ResponseModel activityServiceProviderEnroll(ActivityReq.ActivityId req);

    ResponseModel activityServiceProviderSetup(ActivityReq.ActivityServiceProviderReq req);

    ResponseModel cancelActivityServiceProvider(ActivityReq.ActivityServiceProviderReq req);

    ResponseModel updateActivityServiceProvider(ActivityReq.ActivityServiceProviderReq req);

    ResponseModel getActivityServiceProviderEnrollRecord(ActivityReq.ActivityServiceProviderReq req);

    ResponseModel getBackgroundActivityServiceProvider(ActivityReq.ActivityServiceProviderReq req);

    PageInfoResp<ActivityPoster> getBackendActivityPosterList(ActivityReq.QueryReq req);

    ActivityPoster getActivityPoster(ActivityReq.QueryReq req);

    Integer addActivityPoster(ActivityReq.ActivityPosterAddReq req);

    Integer updateActivityPoster(ActivityReq.ActivityPosterUpdateReq req);

    Integer deleteActivityPoster(ActivityReq.QueryReq req);

    Object getActivityPosterAssetCategoryList();

    ListResp<JSONObject> getActivityPosterList(ActivityReq.QueryReq req);
}
