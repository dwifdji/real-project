package com._360pai.core.service.activity;


import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.req.AcctReq;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.resp.ActivityResp;
import com._360pai.core.facade.activity.vo.AuctionActivityVo;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.asset.Asset;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface AuctionActivityService{

    AuctionActivity getById(Integer activityId);

    PageInfoResp<AuctionActivityVo> getAllByPage(ActivityReq.QueryReq req);

    boolean updateActivityById(AuctionActivity activity);

    List<AuctionActivity> getByAssetId(Integer assetId);

    List<AuctionActivity> getOnlinedActivity(String status);

    String specialDetail(Integer activityId);

    PageInfo activityList(int page, int perPage, String assetPropertyName, String activityName, String agencyName);

    AuctionActivityVo getActivityById(Integer activityId);

    ActivityResp getActivity(ActivityReq.ActivityId req);

    PageInfoResp<AuctionActivityVo> getAgencyPortalActivityListByPage(ActivityReq.QueryReq req);

    PageInfoResp<AuctionActivityVo> getAvailablePlatformActivityListByPage(ActivityReq.QueryReq req);

    PageInfo search(ActivityReq.Search req);

    ActivityResp agencyApprove(ActivityReq.ActivityId req);

    ActivityResp agencyReject(ActivityReq.ActivityId req);

    ActivityResp platformApprove(ActivityReq.ActivityId req);

    ActivityResp platformReject(ActivityReq.ActivityId req);

    ActivityResp forceWithdraw(ActivityReq.ActivityId req);

    ActivityResp updateActivity(ActivityReq.UpdateReq req);

    Object activityDetail(Integer page,Integer perPage,Integer id, Integer activityId, Integer accountId);

    Object getRankActivity(ActivityReq.ActivityId req);

    Asset getAssetByActivityId(Integer activityId);

    Object staffAndAuctioneer(ActivityReq.ActivityId req);

    ActivityResp bindStaff(ActivityReq.ActivityId req);

    ActivityResp unbindStaff(ActivityReq.ActivityId req);

    List<Integer> getNeedRepairAuctionActivity();

    boolean repairAuctionActivity(Integer activityId);

    ActivityResp modifyVisibility(ActivityReq.ActivityId req);

    Map<String, Object> unionData(ActivityReq.ActivityId req);

    BigDecimal checkReservePrice(Integer activityId);

    boolean modifyReservePrice(ActivityReq.ModifyReservePriceReq req);

    int addParticipantNumber(Integer activityId);

    PageInfoResp exportActivityList(ActivityReq.ActivityId req);

    ActivityResp withdrawActivity(ActivityReq.ActivityId req);


    /**
     *
     *获取机构连拍列表
     */
    PageInfo getJointList(ActivityReq.JointListReq req);


    /**
     *
     *添加或者
     */
    void  saveOrCancelJoint(ActivityReq.JointReq req);


    List<Map<String, Object>> getDownloadJointList(ActivityReq.JointListReq req);

    /**
     *
     * @param
     * @return
     */
    PageInfo getAuctionActivityByAccountId(AcctReq.ViewRecordRequest viewRecordRequest);

    Map<String, Object> getShareInfo(ActivityReq.ActivityId req);
}