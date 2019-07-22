package com._360pai.web.controller.activity;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.core.filter.PropertyFilterFactory;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.common.constants.ActivityServiceProviderEnum;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.facade.activity.ActivityFacade;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.req.FavoriteActivityReq;
import com._360pai.core.facade.asset.TAssetCateGoryFacade;
import com._360pai.core.facade.asset.req.TAssetCategoryReq;
import com._360pai.core.facade.enrolling.EnrollingWebFacade;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author zxiao
 * @Title: ActivityController  平台-拍卖活动
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/27 13:09
 */
@RestController
public class ActivityController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Reference(version = "1.0.0")
    private ActivityFacade activityFacade;
    @Reference(version = "1.0.0")
    private TAssetCateGoryFacade tAssetCateGoryFacade;
    @Reference(version = "1.0.0")
    private EnrollingWebFacade enrollingWebFacade;
    @Resource
    private RedisCachemanager redisCachemanager;

    /**
     * 功能描述: 平台 浏览量增加
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/27 15:02
     */
    @GetMapping(value = "/open/activity/view")
    public ResponseModel activityView(ActivityReq.ActivityId req) {
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        req.setAccountId(accountBaseInfo.getAccountId());
        activityFacade.activityView(req);
        return model;
    }

    /**
     * 功能描述: 平台 拍品详情
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/27 15:02
     */
    @PostMapping(value = "/open/activity/detail")
    public ResponseModel activityDetail(@RequestBody ActivityReq.ActivityId req) {
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        req.setAccountId(accountBaseInfo.getAccountId());
        Object obj = activityFacade.activityDetail(req);
        model.setContent(obj);
        return model;
    }

    /**
     * 功能描述: 平台 活动关注
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/27 15:09
     */
    @PostMapping(value = "/confined/activity/favor")
    public ResponseModel activityFavor(@RequestBody ActivityReq.ActivityId req) {
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
//        isAuth();
        Integer partyPrimaryId = accountBaseInfo.getPartyPrimaryId();
        req.setPartyId(partyPrimaryId);
        req.setAccountId(accountBaseInfo.getAccountId());

        req.setType(AssetEnum.FavorType.WEB.getKey());
        req.setResourceId(0);

        activityFacade.activityFavor(req);
        return model;
    }

    /**
     * 功能描述: 平台 取消活动关注 支持批量操作
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/27 15:09
     */
    @PostMapping(value = "/confined/activity/cancelFavor")
    public ResponseModel cancelFavor(@RequestBody ActivityReq.ActivityId req) {
        logger.info("取消活动参数（支持批量操作）={}", req);
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        int i = activityFacade.cancelFavor(req);
        if (i <= 0) {
            return ResponseModel.fail();
        }
        return model;
    }

    /**
     * 功能描述: 平台 取消活动关注
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/27 15:09
     */
    @PostMapping(value = "/confined/activity/unFavor")
    public ResponseModel unFavor(@RequestBody ActivityReq.ActivityId req) {
        logger.info("取消活动参数={}", req);
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        req.setAccountId(accountBaseInfo.getAccountId());

        req.setType(AssetEnum.FavorType.WEB.getKey());
        req.setResourceId(0);
        int i = activityFacade.unFavor(req);

        if (i <= 0) {
            return ResponseModel.fail();
        }
        return model;
    }


    /**
     * 功能描述: 平台 我的关注
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/27 15:09
     */
    @GetMapping(value = "/confined/activity/myFavor")
    public ResponseModel myFavor(FavoriteActivityReq.Query req) {
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
//        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        Integer partyPrimaryId = accountBaseInfo.getPartyPrimaryId();
        if (partyPrimaryId != null)
            req.setPartyId(partyPrimaryId);
        req.setAccountId(accountBaseInfo.getAccountId());
        PageInfo pageInfo = activityFacade.myFavor(req);
        model.setContent(pageInfo);
        return model;
    }


    /**
     * 功能描述: 活动搜索
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/27 15:09
     */
    @GetMapping(value = "/open/activity/search")
    public ResponseModel search(ActivityReq.Search req) {
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        PageInfo pageInfo = activityFacade.search(req);
        model.setContent(pageInfo);
        return model;
    }


    /**
     * 功能描述: 平台 我的关注基础数据
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/27 15:09
     */
    @GetMapping(value = "/confined/activity/base")
    public ResponseModel favorbase(FavoriteActivityReq.Query req) {
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        TAssetCategoryReq tAssetCategoryReq = new TAssetCategoryReq();
        tAssetCategoryReq.setEnabled(true);
        tAssetCategoryReq.setBusinessType(TAssetCategoryReq.AUCTION);
        Object allCateGoryList = tAssetCateGoryFacade.getAllCateGoryList(tAssetCategoryReq);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cateGoryList", allCateGoryList);
        //债券类型
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("id", ActivityEnum.ActivityMode.ENGLISH.getKey());
        jsonObject1.put("name", ActivityEnum.ActivityMode.ENGLISH.getValue());
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("id", ActivityEnum.ActivityMode.DUTCH.getKey());
        jsonObject2.put("name", ActivityEnum.ActivityMode.DUTCH.getValue());
        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("id", ActivityEnum.ActivityMode.SEALED.getKey());
        jsonObject3.put("name", ActivityEnum.ActivityMode.SEALED.getValue());
        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put("id", ActivityEnum.ActivityMode.PUBLIC.getKey());
        jsonObject4.put("name", ActivityEnum.ActivityMode.PUBLIC.getValue());
        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);
        jsonArray.add(jsonObject3);
        jsonArray.add(jsonObject4);
        //拍卖方式
        jsonObject.put("activityModes", jsonArray);
        JSONArray jsonArray1 = new JSONArray();
        JSONObject json1 = new JSONObject();
        json1.put("id", "ahead");
        json1.put("name", ActivityEnum.OnlineStatus.UPCOMING.getValue());
        JSONObject json2 = new JSONObject();
        json2.put("id", "beginAuction");
        json2.put("name", ActivityEnum.OnlineStatus.SALE.getValue());
        JSONObject json3 = new JSONObject();
        json3.put("id", "success");
        json3.put("name", "成交");
        JSONObject json4 = new JSONObject();
        json4.put("id", "end");
        json4.put("name", "结束");
        jsonArray1.add(json1);
        jsonArray1.add(json2);
        jsonArray1.add(json3);
        jsonArray1.add(json4);
        //拍卖状态
        jsonObject.put("activityStatus", jsonArray1);
        model.setContent(jsonObject);
        return model;
    }

    /**
     * 我的拍卖会列表
     */
    @GetMapping(value = "/confined/my/activity/list")
    public ResponseModel myActivityList(ActivityReq.QueryReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        req.setComeFrom(AssetEnum.ComeFrom.PLATFORM.getKey());
        return ResponseModel.succ(activityFacade.getAllByPage(req));
    }

    /**
     * 获取拍卖活动详情
     */
    @GetMapping(value = "/confined/my/activity/detail")
    public ResponseModel myActivityDetail(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(activityFacade.getActivity(req));
    }

    /**
     * 获取送拍协议链接
     */
    @GetMapping(value = "/confined/delegation/signature/url")
    public ResponseModel delegationSignatureUrl(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(SystemConstant.ACCOUNT_COMPANY_TYPE.equals(accountBaseInfo.getType()) && !accountBaseInfo.isAdmin()){
            return ResponseModel.fail(ApiCallResult.NOT_COMPANY_ADMIN_ERRPR);
        }
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(activityFacade.delegationSignatureUrl(req));
    }

    /**
     * 获取补充协议链接
     */
    @GetMapping(value = "/confined/additional/signature/url")
    public ResponseModel additionalSignatureUrl(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(SystemConstant.ACCOUNT_COMPANY_TYPE.equals(accountBaseInfo.getType()) && !accountBaseInfo.isAdmin()){
            return ResponseModel.fail(ApiCallResult.NOT_COMPANY_ADMIN_ERRPR);
        }
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(activityFacade.additionalSignatureUrl(req));
    }

    /**
     * 排行榜
     */
    @GetMapping(value = "/open/activity/ranking")
    public ResponseModel activityranking(ActivityReq.ActivityId req) {
        Object object = activityFacade.getRankActivity(req);
        return ResponseModel.succ(object);
    }

    /**
     * 拍卖师和项目经理
     */
    @GetMapping(value = "/open/activity/staffAndAuctioneer")
    public ResponseModel staffAndAuctioneer(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "活动ID不能为空");
        Object object = activityFacade.staffAndAuctioneer(req);
        return ResponseModel.succ(object, PropertyFilterFactory.create(new String[]{"passwordHash", "id", "isAdmin"}));
    }

    /**
     * 个人中心 我的关注数据
     */
    @GetMapping(value = "/confined/activity/favoriteCount")
    public ResponseModel favoriteCount(ActivityReq.ActivityId req) {
//        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        //我的关注数量
        Map<String, Object> myFavoriteCount = activityFacade.favoriteCount(req);
        return ResponseModel.succ(myFavoriteCount);
    }

    /**
     * 拍卖活动分享
     */
    @GetMapping(value = "/open/activity/share")
    public ResponseModel share(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "活动ID不能为空");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (accountBaseInfo.isAccountAuth()) {
            req.setPartyId(accountBaseInfo.getPartyPrimaryId());
            req.setAccountId(accountBaseInfo.getAccountId());
            activityFacade.share(req);
        }
        return ResponseModel.succ();
    }

    /**
     * 撤回拍卖活动
     */
    @PostMapping(value = "/confined/activity/withdraw")
    public ResponseModel withdrawActivity(@RequestBody ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(activityFacade.withdrawActivity(req));
    }

    /**
     * 活动分享信息接口
     */
    @GetMapping(value = "/open/activity/share/info")
    public ResponseModel getShareInfo(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "活动ID不能为空");
        Assert.notNull(req.getType(), "type不能为空");
        if ("0".equals(req.getType())) {
            return activityFacade.getShareInfo(req);
        } else {
            return enrollingWebFacade.getShareInfo(req);
        }
    }

    /**
     * 获取活动分享图片接口
     */
    @GetMapping(value = "/open/activity/share/image")
    public ResponseModel getShareImage(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "活动ID不能为空");
        Assert.notNull(req.getType(), "type不能为空");
        Map<String, Object> data = Maps.newHashMap();
        String imageUrl;
        if ("0".equals(req.getType())) {
            imageUrl = (String) redisCachemanager.hGet(RedisKeyConstant.AUCTION_ACTIVITY_SHARE_IMAGE, req.getActivityId() + "");
        } else {
            imageUrl = (String) redisCachemanager.hGet(RedisKeyConstant.ENROLLING_ACTIVITY_SHARE_IMAGE, req.getActivityId() + "");
        }
        data.put("imageUrl", imageUrl);
        return ResponseModel.succ(data);
    }

    /**
     * 保存活动分享图片接口
     */
    @PostMapping(value = "/open/activity/save/share/image")
    public ResponseModel saveShareImage(@RequestBody ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "活动ID不能为空");
        Assert.notNull(req.getType(), "type不能为空");
        Assert.notNull(req.getImageUrl(), "imageUrl不能为空");
        if ("0".equals(req.getType())) {
            redisCachemanager.hSet(RedisKeyConstant.AUCTION_ACTIVITY_SHARE_IMAGE, req.getActivityId() + "", req.getImageUrl());
        } else {
            redisCachemanager.hSet(RedisKeyConstant.ENROLLING_ACTIVITY_SHARE_IMAGE, req.getActivityId() + "", req.getImageUrl());
        }
        return ResponseModel.succ();
    }

    /**
     * 活动服务商信息接口
     */
    @GetMapping(value = "/open/activity/service/provider")
    public ResponseModel getActivityServiceProvider(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "活动ID不能为空");
        Assert.notNull(req.getActivityType(), "activityType不能为空");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (accountBaseInfo.isDisposer()) {
            req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        }
        return activityFacade.getActivityServiceProvider(req);
    }

    /**
     * 活动服务商报名接口
     */
    @PostMapping(value = "/confined/activity/service/provider/enroll")
    public ResponseModel activityServiceProviderEnroll(@RequestBody ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "活动ID不能为空");
        Assert.notNull(req.getActivityType(), "activityType不能为空");
        Assert.notNull(req.getProviderType(), "providerType不能为空");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (ActivityServiceProviderEnum.ProviderType.Dispose.getKey().equals(req.getProviderType())) {
            if (!accountBaseInfo.isDisposer()) {
                return ResponseModel.fail("请先认证处置服务商");
            }
        }
        if (ActivityServiceProviderEnum.ProviderType.Fund.getKey().equals(req.getProviderType())) {
            if (!accountBaseInfo.isFunder()) {
                return ResponseModel.fail("请先认证资金供应商");
            }
        }
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return activityFacade.activityServiceProviderEnroll(req);
    }

    /**
     * 推广位列表接口
     */
    @GetMapping(value = "/open/activity/poster/list")
    public ResponseModel getActivityPosterList(ActivityReq.QueryReq req) {
        return ResponseModel.succ(activityFacade.getActivityPosterList(req));
    }
}
