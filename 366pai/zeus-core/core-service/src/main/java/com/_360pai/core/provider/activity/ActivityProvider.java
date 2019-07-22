package com._360pai.core.provider.activity;

import com._360pai.arch.common.ListResp;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.common.constants.ActivityServiceProviderEnum;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.vo.ActivityPoster;
import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.facade.activity.ActivityFacade;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.req.FavoriteActivityReq;
import com._360pai.core.facade.activity.resp.ActivityResp;
import com._360pai.core.facade.activity.vo.AuctionActivityVo;
import com._360pai.core.facade.activity.vo.BidRecord;
import com._360pai.core.facade.applet.req.AuctionReq;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.asset.AssetProperty;
import com._360pai.core.model.asset.TAssetCategory;
import com._360pai.core.model.assistant.FavoriteActivity;
import com._360pai.core.service.account.AgencyPortalActivityService;
import com._360pai.core.service.account.AgencyService;
import com._360pai.core.service.activity.AuctionActivityService;
import com._360pai.core.service.activity.AuctionActivityShareStatsService;
import com._360pai.core.service.activity.BidService;
import com._360pai.core.service.agreement.AdditionalAgreementService;
import com._360pai.core.service.agreement.DelegationAgreementService;
import com._360pai.core.service.asset.AssetCategoryService;
import com._360pai.core.service.asset.AssetPropertyService;
import com._360pai.core.service.assistant.*;
import com._360pai.core.utils.RespConvertUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author : whisky_vip
 * @date : 2018/8/15 18:31
 */
@Component
@Service(version = "1.0.0")
public class ActivityProvider implements ActivityFacade {

    @Autowired
    private AuctionActivityService           auctionActivityService;
    @Autowired
    private BidService                       bidService;
    @Autowired
    private FavoriteActivityService          favoriteActivityService;
    @Autowired
    private NotifyPartyActivityService       notifyPartyActivityService;
    @Autowired
    private DepositService                   depositService;
    @Autowired
    private AuctionActivityShareStatsService auctionActivityShareStatsService;
    @Autowired
    private AgencyPortalActivityService agencyPortalActivityService;
    @Autowired
    private DelegationAgreementService  delegationAgreementService;
    @Autowired
    private AdditionalAgreementService  additionalAgreementService;
    @Autowired
    private AssetPropertyService        assetPropertyService;
    @Autowired
    private AssetCategoryService        assetCategoryService;
    @Autowired
    private RedisCachemanager           redisCachemanager;
    @Autowired
    private DetailViewRecodeService     detailViewRecodeService;

    @Autowired
    private AgencyService agencyService;
    @Autowired
    private ActivityServiceProviderService activityServiceProviderService;
    @Autowired
    private ActivityPosterService activityPosterService;

    @Override
    public ActivityResp getActivity(ActivityReq.ActivityId req) {
        return auctionActivityService.getActivity(req);
    }

    @Override
    public PageInfoResp<AuctionActivityVo> getAllByPage(ActivityReq.QueryReq req) {
        return auctionActivityService.getAllByPage(req);
    }

    @Override
    public ActivityResp platformApprove(ActivityReq.ActivityId req) {
        return auctionActivityService.platformApprove(req);
    }

    @Override
    public ActivityResp platformReject(ActivityReq.ActivityId req) {
        return auctionActivityService.platformReject(req);
    }

    @Override
    public ActivityResp agencyApprove(ActivityReq.ActivityId req) {
        return auctionActivityService.agencyApprove(req);
    }

    @Override
    public ActivityResp agencyReject(ActivityReq.ActivityId req) {
        return auctionActivityService.agencyReject(req);
    }

    @Override
    public PageInfoResp<BidRecord> getAllBidRecordsByPage(ActivityReq.ActivityId req) {
        return bidService.getAllBidRecordsByPage(req);
    }

    @Override
    public Object getById(Integer activityId) {
        return auctionActivityService.getById(activityId);
    }

    @Override
    public PageInfoResp<PartyAccount> getParticipatingPartiesByPage(ActivityReq.ActivityId req) {
        return depositService.getParticipatingPartiesByPage(req);
    }

    @Override
    public PageInfoResp<PartyAccount> getFavoredPartyAccountsByPage(ActivityReq.ActivityId req) {
        return favoriteActivityService.getFavoredPartyAccountsByPage(req);
    }

    @Override
    public PageInfoResp<PartyAccount> getNotifiedPartyAccountsByPage(ActivityReq.ActivityId req) {
        return notifyPartyActivityService.getNotifiedPartyAccountsByPage(req);
    }

    @Override
    public PageInfoResp<PartyAccount> getSharePartyAccountsByPage(ActivityReq.ActivityId req) {
        return auctionActivityShareStatsService.getSharePartyAccountsByPage(req);
    }

    @Override
    public ActivityResp forceWithdraw(ActivityReq.ActivityId req) {
        return auctionActivityService.forceWithdraw(req);
    }

    @Override
    public void activityView(ActivityReq.ActivityId req) {
        auctionActivityShareStatsService.activityView(req.getActivityId(), req.getAgencyCode(), 1);
        //插入浏览记录
        detailViewRecodeService.insertActivityWebRecode(req.getActivityId(), req.getAccountId(), req.getPartyId());
    }

    @Override
    public void activityFavor(ActivityReq.ActivityId req) {

      /*  if (StringUtils.isNotBlank(req.getAgencyCode())) {

            TAgency agency = agencyService.findByAgencyCode(req.getAgencyCode());
            if(agency!=null&&AssetEnum.FavorType.WEB.equals(req.getType())){
                req.setResourceId(agency.getId());
            }
         }*/
        favoriteActivityService.activityFavor(req.getActivityId(), req.getPartyId(), req.getAgencyCode(), req.getType(),req.getResourceId(), req.getAccountId());
    }

    @Override
    public int cancelFavor(ActivityReq.ActivityId req) {
        return favoriteActivityService.cancelFavor(req.getIds(), req.getPartyId());
    }

    @Override
    public PageInfo myFavor(FavoriteActivityReq.Query req) {
        return favoriteActivityService.myFavor(req);
    }

    @Override
    public int unFavor(ActivityReq.ActivityId req) {

       /* if (StringUtils.isNotBlank(req.getAgencyCode())) {
            TAgency agency = agencyService.findByAgencyCode(req.getAgencyCode());
            if(agency!=null&&AssetEnum.FavorType.WEB.equals(req.getType())){
                req.setResourceId(agency.getId());
            }
        }*/
        return favoriteActivityService.unFavor(req.getPartyId(), req.getActivityId(),req.getType(),req.getResourceId(), req.getAccountId());
    }

    @Override
    public PageInfo activityList(ActivityReq.QueryReq req) {
        return auctionActivityService.activityList(req.getPage(), req.getPerPage(), req.getAssetPropertyName(), req.getActivityName(), req.getAgencyName());
    }

    @Override
    public PageInfoResp<AuctionActivityVo> getAgencyPortalActivityListByPage(ActivityReq.QueryReq req) {
        return auctionActivityService.getAgencyPortalActivityListByPage(req);
    }

    @Override
    public PageInfoResp<AuctionActivityVo> getAvailablePlatformActivityListByPage(ActivityReq.QueryReq req) {
        return auctionActivityService.getAvailablePlatformActivityListByPage(req);
    }

    @Override
    public ActivityResp publishUnion(ActivityReq.ActivityId req) {
        return agencyPortalActivityService.publishUnion(req);
    }

    @Override
    public ActivityResp cancelUnion(ActivityReq.ActivityId req) {
        return agencyPortalActivityService.cancelUnion(req);
    }

    @Override
    public PageInfo search(ActivityReq.Search req) {
        //拍卖大厅搜索列表  是否查询了连拍机构的列表
        if(StringUtils.isNotEmpty(req.getAgencyCode())){
            Map<String,String> agencyMap = getAgencyInfo(req.getAgencyCode());
            req.setJoint(agencyMap.get("joint"));
            req.setJointAgencyId(agencyMap.get("jointAgencyId"));
        }
        return auctionActivityService.search(req);
    }


    @Override
    public PageInfoResp<PartyAccount> getAgencyParticipatingPartiesByPage(ActivityReq.ActivityId req) {
        return depositService.getAgencyParticipatingPartiesByPage(req);
    }

    @Override
    public ActivityResp updateActivity(ActivityReq.UpdateReq req) {
        return auctionActivityService.updateActivity(req);
    }

    @Override
    public Object activityDetail(ActivityReq.ActivityId req) {
        return auctionActivityService.activityDetail(req.getPage(), req.getPerPage(), req.getActivityId(), req.getPartyId(), req.getAccountId());
    }

    @Override
    public ActivityResp delegationSignatureUrl(ActivityReq.ActivityId req) {
        return delegationAgreementService.delegationSignatureUrl(req);
    }

    @Override
    public ActivityResp additionalSignatureUrl(ActivityReq.ActivityId req) {
        return additionalAgreementService.additionalSignatureUrl(req);
    }

    @Override
    public ActivityResp signDelegationAgreementCallBack(ActivityReq.SignDelegationAgreementCallBackReq req) {
        return delegationAgreementService.signCallBack(req);
    }

    @Override
    public ActivityResp signAdditionalAgreementCallBack(ActivityReq.SignAdditionalAgreementCallBackReq req) {
        return additionalAgreementService.signCallBack(req);
    }

    @Override
    public Object getRankActivity(ActivityReq.ActivityId req) {
        req.setPage(1);
        req.setPerPage(8);

        //拍卖大厅搜索列表  是否查询了连拍机构的列表
        if(StringUtils.isNotEmpty(req.getAgencyCode())){
            Map<String,String> agencyMap = getAgencyInfo(req.getAgencyCode());
            req.setJoint(agencyMap.get("joint"));
            req.setJointAgencyId(agencyMap.get("jointAgencyId"));

        }

        return auctionActivityService.getRankActivity(req);
    }

    private Map<String,String> getAgencyInfo(String agencyCode) {

        Map agencyMap = new HashMap();

        try {
            TAgency agency =  agencyService.findByAgencyCode(agencyCode);
            if(agency!=null){
                agencyMap.put("jointAgencyId",agency.getId().toString());
                agencyMap.put("joint",agency.getIsJoint().toString());

            }
        }catch (Exception e){

        }

        return agencyMap;
    }

    @Override
    public Map<Object, Map<Object, Object>> getSystemDict() {
        Map<Object, Map<Object, Object>> map                = new LinkedHashMap<>();
        String                           cacheAssetProperty = (String) redisCachemanager.get(RedisKeyConstant.SYSTEM_DICT_ASSET_PROPERTY);
        if (StringUtils.isEmpty(cacheAssetProperty)) {
            Map<Object, Object> propertyMap       = new LinkedHashMap<>();
            List<AssetProperty> assetPropertyList = assetPropertyService.getAssetPropertyListByType(null);
            for (AssetProperty assetProperty : assetPropertyList) {
                propertyMap.put(assetProperty.getId() + "", assetProperty.getName());
            }
            if (!propertyMap.isEmpty()) {
                map.put("assetProperty", propertyMap);
            }
            redisCachemanager.set(RedisKeyConstant.SYSTEM_DICT_ASSET_PROPERTY, JSON.toJSONString(propertyMap), 10800);
        } else {
            Map<Object, Object> propertyMap = JSON.parseObject(cacheAssetProperty, Map.class);
            map.put("assetProperty", propertyMap);
        }
        String cacheAssetCategory = (String) redisCachemanager.get(RedisKeyConstant.SYSTEM_DICT_ASSET_CATEGORY);
        if (StringUtils.isEmpty(cacheAssetCategory)) {
            Map<Object, Object>  categoryMap       = new LinkedHashMap<>();
            List<TAssetCategory> assetCategoryList = assetCategoryService.getAuctionAssetCategoryList();
            for (TAssetCategory assetCategory : assetCategoryList) {
                categoryMap.put(assetCategory.getId() + "", assetCategory.getName());
            }
            // 特殊的租赁权处理
            categoryMap.put("-1", "租赁权拍卖");
            if (!categoryMap.isEmpty()) {
                map.put("assetCategory", categoryMap);
            }
            redisCachemanager.set(RedisKeyConstant.SYSTEM_DICT_ASSET_CATEGORY, JSON.toJSONString(categoryMap), 10800);
        } else {
            Map<Object, Object> categoryMap = JSON.parseObject(cacheAssetCategory, Map.class);
            map.put("assetCategory", categoryMap);
        }
        return map;
    }

    @Override
    public Object staffAndAuctioneer(ActivityReq.ActivityId req) {
        return auctionActivityService.staffAndAuctioneer(req);
    }

    @Override
    public Map<String, Object> favoriteCount(ActivityReq.ActivityId req) {
        return favoriteActivityService.favoriteCount(req.getAccountId(), req.getPartyId());
    }

    @Override
    public ActivityResp bindStaff(ActivityReq.ActivityId req) {
        return auctionActivityService.bindStaff(req);
    }

    @Override
    public ActivityResp unbindStaff(ActivityReq.ActivityId req) {
        return auctionActivityService.unbindStaff(req);
    }

    @Override
    public ActivityResp share(ActivityReq.ActivityId req) {
        return auctionActivityShareStatsService.share(req);
    }

    @Override
    public ActivityResp.DelegationAgreement getDelegationAgreement(ActivityReq.ActivityId req) {
        ActivityResp.DelegationAgreement resp = new ActivityResp.DelegationAgreement();
        resp.setAgreement(RespConvertUtil.convertToFileInfo(delegationAgreementService.getByActivityId(req.getActivityId())));
        return resp;
    }

    @Override
    public ActivityResp modifyVisibility(ActivityReq.ActivityId req) {
        return auctionActivityService.modifyVisibility(req);
    }

    @Override
    public Map<String, Object> unionData(ActivityReq.ActivityId req) {
        return auctionActivityService.unionData(req);
    }

    @Override
    public BigDecimal checkReservePrice(Integer activityId) {
        return auctionActivityService.checkReservePrice(activityId);
    }

    @Override
    public boolean modifyReservePrice(ActivityReq.ModifyReservePriceReq req) {
        return auctionActivityService.modifyReservePrice(req);
    }

    @Override
    public PageInfoResp exportActivityList(ActivityReq.ActivityId req) {
        return auctionActivityService.exportActivityList(req);
    }

    @Override
    public ActivityResp withdrawActivity(ActivityReq.ActivityId req) {
        return auctionActivityService.withdrawActivity(req);
    }

    @Override
    public ResponseModel getConcernedFlag(AuctionReq.AuctionInfoReq req) {
        Map<String, Object> result = new HashMap<>();
        List<FavoriteActivity>  favoriteActivitys = favoriteActivityService.getConcernedFlag(req.getShopId(), req.getAuctionId());
        if (favoriteActivitys != null && favoriteActivitys.size() > 0) {
            result.put("concernedFlag", "1");
        }else{
            result.put("concernedFlag", "0");
        }

        return ResponseModel.succ(result);
    }

    @Override
    public PageInfo getMyShopFavor(AuctionReq.AuctionInfoReq req) {

        return favoriteActivityService.getMyShopFavor(req);
    }


    @Override
    public ResponseModel getJointList(ActivityReq.JointListReq req) {
        //
        PageInfo jointVos = auctionActivityService.getJointList(req);

        return ResponseModel.succ(jointVos);
    }

    @Override
    public ResponseModel saveOrCancelJoint(ActivityReq.JointReq req) {

        auctionActivityService.saveOrCancelJoint(req);

        return ResponseModel.succ();
    }


    @Override
    public List<Map<String, Object>> getDownloadJointList(ActivityReq.JointListReq req) {


        return auctionActivityService.getDownloadJointList(req);
    }

    @Override
    public ResponseModel getShareInfo(ActivityReq.ActivityId req) {
        return ResponseModel.succ(auctionActivityService.getShareInfo(req));
    }

    @Override
    public ResponseModel getActivityServiceProvider(ActivityReq.ActivityId req) {
        return activityServiceProviderService.getActivityServiceProvider(req);
    }

    @Override
    public ResponseModel activityServiceProviderEnroll(ActivityReq.ActivityId req) {
        return activityServiceProviderService.activityServiceProviderEnroll(req);
    }

    @Override
    public ResponseModel activityServiceProviderSetup(ActivityReq.ActivityServiceProviderReq req) {
        return activityServiceProviderService.activityServiceProviderSetup(req);
    }

    @Override
    public ResponseModel cancelActivityServiceProvider(ActivityReq.ActivityServiceProviderReq req) {
        return activityServiceProviderService.cancelActivityServiceProvider(req);
    }

    @Override
    public ResponseModel updateActivityServiceProvider(ActivityReq.ActivityServiceProviderReq req) {
        return activityServiceProviderService.updateActivityServiceProvider(req);
    }

    @Override
    public ResponseModel getActivityServiceProviderEnrollRecord(ActivityReq.ActivityServiceProviderReq req) {
        if (!ActivityServiceProviderEnum.ProviderType.contains(req.getProviderType())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (ActivityServiceProviderEnum.ProviderType.Dispose.getKey().equals(req.getProviderType())) {
            return activityServiceProviderService.getDisposeActivityServiceProviderEnrollRecord(req);
        } else {
            return activityServiceProviderService.getFundActivityServiceProviderEnrollRecord(req);
        }
    }

    @Override
    public ResponseModel getBackgroundActivityServiceProvider(ActivityReq.ActivityServiceProviderReq req) {
        return activityServiceProviderService.getBackgroundActivityServiceProvider(req);
    }

    @Override
    public PageInfoResp<ActivityPoster> getBackendActivityPosterList(ActivityReq.QueryReq req) {
        return activityPosterService.getBackendActivityPosterList(req);
    }

    @Override
    public ActivityPoster getActivityPoster(ActivityReq.QueryReq req) {
        return activityPosterService.getActivityPoster(req);
    }

    @Override
    public Integer addActivityPoster(ActivityReq.ActivityPosterAddReq req) {
        return activityPosterService.addActivityPoster(req);
    }

    @Override
    public Integer updateActivityPoster(ActivityReq.ActivityPosterUpdateReq req) {
        return activityPosterService.updateActivityPoster(req);
    }

    @Override
    public Integer deleteActivityPoster(ActivityReq.QueryReq req) {
        return activityPosterService.deleteActivityPoster(req);
    }

    @Override
    public Object getActivityPosterAssetCategoryList() {
        return activityPosterService.getActivityPosterAssetCategoryList();
    }

    @Override
    public ListResp<JSONObject> getActivityPosterList(ActivityReq.QueryReq req) {
        return activityPosterService.getActivityPosterList(req);
    }
}
