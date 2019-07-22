package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.ListResp;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.common.constants.ActivityServiceProviderEnum;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.condition.asset.TAssetCategoryCondition;
import com._360pai.core.dao.account.TAgencyDao;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.dao.activity.TActivityPosterDao;
import com._360pai.core.dao.asset.AssetDao;
import com._360pai.core.dao.asset.TAssetCategoryDao;
import com._360pai.core.dao.enrolling.EnrollingActivityDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.vo.ActivityPoster;
import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.asset.req.TAssetCategoryReq;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.activity.TActivityPoster;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.asset.TAssetCategory;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.assistant.ActivityPosterService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: ActivityPosterServiceImpl
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/7/1 13:35
 */
@Slf4j
@Service
public class ActivityPosterServiceImpl implements ActivityPosterService {

    @Autowired
    private TActivityPosterDao activityPosterDao;
    @Autowired
    private TAssetCategoryDao tAssetCategoryDao;
    @Autowired
    private AuctionActivityDao auctionActivityDao;
    @Autowired
    private EnrollingActivityDao enrollingActivityDao;
    @Autowired
    private AssetDao assetDao;
    @Autowired
    private TAgencyDao agencyDao;
    @Autowired
    private AccountService accountService;

    @Override
    public PageInfoResp<ActivityPoster> getBackendActivityPosterList(ActivityReq.QueryReq req) {
        PageInfoResp<ActivityPoster> resp = new PageInfoResp<>();
        List<ActivityPoster> resultList = new ArrayList<>();
        PageInfo<TActivityPoster> pageInfo = activityPosterDao.getBackendList(req.getPage(), req.getPerPage(), Maps.newHashMap());
        Date now = new Date();
        for (TActivityPoster item : pageInfo.getList()) {
            ActivityPoster vo = new ActivityPoster();
            BeanUtils.copyProperties(item, vo);
            if (item.getBeginAt().compareTo(now) <= 0 && item.getEndAt().compareTo(now) >= 0) {
                vo.setOnlineFlag(true);
            } else {
                vo.setOnlineFlag(false);
            }
            resultList.add(vo);
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public ActivityPoster getActivityPoster(ActivityReq.QueryReq req) {
        TActivityPoster activityPoster = activityPosterDao.selectById(req.getId());
        if (activityPoster == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        ActivityPoster vo = new ActivityPoster();
        BeanUtils.copyProperties(activityPoster, vo);
        Date now = new Date();
        if (activityPoster.getBeginAt().compareTo(now) <= 0 && activityPoster.getEndAt().compareTo(now) >= 0) {
            vo.setOnlineFlag(true);
        } else {
            vo.setOnlineFlag(false);
        }
        if (!activityPoster.getAutoFlag()) {
            if (StringUtils.isNotBlank(activityPoster.getActivityIds())) {
                List<Map> activityList = new ArrayList<>();
                List<Integer> auctionActivityIdList = new ArrayList<>();
                List<Integer> enrollingActivityIdList = new ArrayList<>();
                JSONArray jsonArray = JSON.parseArray(activityPoster.getActivityIds());
                for (Object item : jsonArray) {
                    JSONObject jsonObject = (JSONObject) item;
                    if (ActivityServiceProviderEnum.ActivityType.Auction.getKey().equals(jsonObject.getString("activityType"))) {
                        auctionActivityIdList.add(jsonObject.getIntValue("activityId"));
                    } else {
                        enrollingActivityIdList.add(jsonObject.getIntValue("activityId"));
                    }
                }
                if (auctionActivityIdList.size() > 0) {
                    activityList.addAll(activityPosterDao.getManualAuctionActivityList(auctionActivityIdList));
                }
                if (enrollingActivityIdList.size() > 0) {
                    activityList.addAll(activityPosterDao.getManualEnrollingActivityList(enrollingActivityIdList));
                }
                for (Map item : activityList) {
                    if (StringUtils.isBlank((String) item.get("cityName"))) {
                        item.put("cityName", (String) item.get("provinceName"));
                    }
                    if (ActivityServiceProviderEnum.ActivityType.Auction.getKey().equals(item.get("activityType"))) {
                        String categoryName = (String) item.get("categoryName");
                        if (StringUtils.isBlank(categoryName) && item.get("category").equals("-1")) {
                            item.put("categoryName", "租赁权拍卖");
                        }
                        AuctionActivity activity = auctionActivityDao.selectById(item.get("activityId"));
                        Asset asset = assetDao.selectById(activity.getAssetId());
                        String agencyName = agencyDao.getName(activity.getAgencyId());
                        item.put("agencyName", agencyName);
                        String sellerName;
                        if (AssetEnum.ComeFrom.AGENCY.getKey().equals(asset.getComeFrom())) {
                            sellerName = agencyName;
                        } else {
                            PartyAccount partyAccount = accountService.getPartyAccountById(asset.getPartyId());
                            sellerName = partyAccount.getName();
                        }
                        item.put("sellerName", sellerName);

                    } else {
                        item.put("categoryName", EnrollingEnum.ENROLLING_TYPE.getDesc((String) item.get("category")));
                        EnrollingActivity activity = enrollingActivityDao.selectById(item.get("activityId"));
                        String agencyName = agencyDao.getName(activity.getAgencyId());
                        item.put("agencyName", agencyName);
                        String sellerName;
                        if (EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType().equals(activity.getThirdPartyStatus())) {
                            sellerName = agencyName;
                        } else {
                            PartyAccount partyAccount = accountService.getPartyAccountById(activity.getPartyId());
                            sellerName = partyAccount.getName();
                        }
                        item.put("sellerName", sellerName);
                    }
                    item.put("statusDesc", getActivityStatus(item));
                    item.put("id", item.get("activityId"));
                }
                vo.setActivityList(activityList);
            }

        }
        return vo;
    }

    private String getActivityStatus(Map map) {
        String status = (String) map.get("status");
        if (ActivityServiceProviderEnum.ActivityType.Auction.getKey().equals(map.get("activityType"))) {
            Date beginAt = (Date) map.get("beginAt");
            //判断当前活动的状态
            if (status.equals(ActivityEnum.Status.ONLINE.getKey()) && DateUtil.getMarginMin(new Date(), beginAt) < 0) {
                return  "即将开拍"; //即将开拍
            } else if (status.equals(ActivityEnum.Status.ONLINE.getKey()) && DateUtil.getMarginMin(new Date(), beginAt) > 0) {
                return "正在拍卖";  //正在拍卖
            } else if (status.equals(ActivityEnum.Status.SUCCESS.getKey())) {
                return "成交";   //成功
            } else if (status.equals(ActivityEnum.Status.FAILED.getKey())) {
                return "结束";   //完成
            }
        } else {
            if (EnrollingEnum.STATUS.ONLINE.getType().equals(status)) {
                return EnrollingEnum.STATUS.ONLINE.getTypeName();
            } else if (EnrollingEnum.STATUS.FINISHED.getType().equals(status)) {
                return EnrollingEnum.STATUS.FINISHED.getTypeName();
            } else if (EnrollingEnum.STATUS.CLOSED.getType().equals(status)) {
                return EnrollingEnum.STATUS.CLOSED.getTypeName();
            }
        }
        return status;
    }

    @Override
    public Integer addActivityPoster(ActivityReq.ActivityPosterAddReq req) {
        TActivityPoster activityPoster = new TActivityPoster();
        BeanUtils.copyProperties(req, activityPoster);
        if (req.getActivityIds() != null) {
            activityPoster.setActivityIds(JSON.toJSONString(req.getActivityIds()));
        }
        int result = activityPosterDao.insert(activityPoster);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return activityPoster.getId();
    }

    @Override
    public Integer updateActivityPoster(ActivityReq.ActivityPosterUpdateReq req) {
        TActivityPoster activityPoster = activityPosterDao.selectById(req.getId());
        if (activityPoster == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        BeanUtils.copyProperties(req, activityPoster);
        if (req.getActivityIds() != null) {
            activityPoster.setActivityIds(JSON.toJSONString(req.getActivityIds()));
        }
        int result = activityPosterDao.updateById(activityPoster);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return activityPoster.getId();
    }

    @Override
    public Integer deleteActivityPoster(ActivityReq.QueryReq req) {
        TActivityPoster activityPoster = activityPosterDao.selectById(req.getId());
        if (activityPoster == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        activityPoster.setDeleteFlag(true);
        int result = activityPosterDao.updateById(activityPoster);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return activityPoster.getId();
    }

    @Override
    public Object getActivityPosterAssetCategoryList() {
        TAssetCategoryCondition condition = new TAssetCategoryCondition();
        condition.setBusinessType(TAssetCategoryReq.AUCTION);
        condition.setEnabled(true);
        List<TAssetCategory> list = tAssetCategoryDao.selectList(condition);
        JSONArray array = new JSONArray();
        for (TAssetCategory item : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", ActivityServiceProviderEnum.ActivityType.Auction.getKey() + "_" + item.getId());
            jsonObject.put("name", item.getName());
            array.add(jsonObject);
        }
        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put("id", ActivityServiceProviderEnum.ActivityType.Auction.getKey() + "_" + "-1");
        jsonObject4.put("name", "租赁权拍卖");
        array.add(jsonObject4);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", ActivityServiceProviderEnum.ActivityType.Enrolling.getKey() + "_" + "1");
        jsonObject.put("name", "抵押物转让");
        array.add(jsonObject);
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("id", ActivityServiceProviderEnum.ActivityType.Enrolling.getKey() + "_" + "2");
        jsonObject2.put("name", "债权转让");
        array.add(jsonObject2);
        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("id", ActivityServiceProviderEnum.ActivityType.Enrolling.getKey() + "_" + "3");
        jsonObject3.put("name", "物权转让");
        array.add(jsonObject3);

        return array;
    }

    @Override
    public ListResp<JSONObject> getActivityPosterList(ActivityReq.QueryReq req) {
        ListResp<JSONObject> resp = new ListResp<>();
        List<JSONObject> resultList = new ArrayList<>();
        List<TActivityPoster> list = activityPosterDao.getFrontList((Map<String, Object>) JSON.toJSON(req));
        for (TActivityPoster item : list) {
            JSONObject vo = new JSONObject();
            vo.put("id", item.getId());
            vo.put("imgUrl", item.getImgUrl());
            resultList.add(vo);
        }
        resp.setList(resultList);
        return resp;
    }
}
