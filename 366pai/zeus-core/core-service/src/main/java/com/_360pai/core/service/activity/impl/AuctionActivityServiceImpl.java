package com._360pai.core.service.activity.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.JsonUtil;
import com._360pai.arch.common.utils.NumberToCN;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.core.file.QiNiuUtil;
import com._360pai.arch.core.file.QrUtil;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.*;
import com._360pai.core.condition.activity.ActivityBlackListCityCondition;
import com._360pai.core.condition.activity.ActivityWhiteListCityCondition;
import com._360pai.core.condition.activity.AuctionActivityCondition;
import com._360pai.core.condition.activity.BidCondition;
import com._360pai.core.condition.agreement.DealAgreementCondition;
import com._360pai.core.condition.asset.AssetLeaseDataCondition;
import com._360pai.core.condition.assistant.FavoriteActivityCondition;
import com._360pai.core.condition.lease.TLeaseApplyCondition;
import com._360pai.core.condition.payment.AuctionOrderCondition;
import com._360pai.core.dao.account.BankAccountDao;
import com._360pai.core.dao.account.TAgencyDao;
import com._360pai.core.dao.account.TPartyDao;
import com._360pai.core.dao.activity.*;
import com._360pai.core.dao.agreement.AdditionalAgreementDao;
import com._360pai.core.dao.agreement.DealAgreementDao;
import com._360pai.core.dao.asset.AssetDao;
import com._360pai.core.dao.asset.AssetDataDao;
import com._360pai.core.dao.asset.AssetLeaseDataDao;
import com._360pai.core.dao.asset.AssetRejectRecordDao;
import com._360pai.core.dao.assistant.*;
import com._360pai.core.dao.lease.TLeaseApplyDao;
import com._360pai.core.dto.SellerPayInfo;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.AcctReq;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.facade.activity.req.ActivityJointReq;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.resp.ActivityResp;
import com._360pai.core.facade.activity.vo.AgencyUnionDataVo;
import com._360pai.core.facade.activity.vo.AuctionActivityVo;
import com._360pai.core.facade.activity.vo.AuctionJointVo;
import com._360pai.core.facade.activity.vo.SearchAuctionActivityVo;
import com._360pai.core.facade.asset.vo.AssetVo;
import com._360pai.core.model.account.*;
import com._360pai.core.model.activity.*;
import com._360pai.core.model.agreement.AdditionalAgreement;
import com._360pai.core.model.agreement.DealAgreement;
import com._360pai.core.model.agreement.DelegationAgreement;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.asset.AssetData;
import com._360pai.core.model.asset.AssetLeaseData;
import com._360pai.core.model.assistant.*;
import com._360pai.core.model.lease.TLeaseApply;
import com._360pai.core.model.payment.AuctionOrder;
import com._360pai.core.service.account.*;
import com._360pai.core.service.activity.*;
import com._360pai.core.service.agreement.AdditionalAgreementService;
import com._360pai.core.service.agreement.DealAgreementService;
import com._360pai.core.service.agreement.DelegationAgreementService;
import com._360pai.core.service.asset.AssetAuthorizationService;
import com._360pai.core.service.asset.AssetService;
import com._360pai.core.service.assistant.*;
import com._360pai.core.service.order.ServiceOrderService;
import com._360pai.core.service.payment.AuctionOrderService;
import com._360pai.core.utils.ReqConvertUtil;
import com._360pai.core.utils.RespConvertUtil;
import com._360pai.core.utils.ServiceActivityUtils;
import com._360pai.core.vo.assistant.ProvinceCityVo;
import com._360pai.gateway.common.dfftpay.PayEnums;
import com._360pai.gateway.common.dfftpay.PayResultEnums;
import com._360pai.gateway.controller.req.dfftpay.LockOrReleaseOrDirectReq;
import com._360pai.gateway.controller.req.dfftpay.UnifiedPayReq;
import com._360pai.gateway.controller.req.dfftpay.UnifiedPayResp;
import com._360pai.gateway.controller.req.wx.WXACodeUnLimitReq;
import com._360pai.gateway.facade.PayFacade;
import com._360pai.gateway.facade.WxFacade;
import com._360pai.gateway.resp.wxpay.WXACodeUnLimitResp;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.list.TreeList;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author whisky_vip
 */
@Slf4j
@Service
public class AuctionActivityServiceImpl implements AuctionActivityService {

    @Reference(version = "1.0.0")
    private PayFacade payFacade;

    public static final Logger LOGGER = LoggerFactory.getLogger(AuctionActivityServiceImpl.class);

    @Autowired
    private AuctionActivityDao           auctionActivityDao;
    @Autowired
    private AssetDao                     assetDao;
    @Autowired
    private AssetDataDao                 assetDataDao;
    @Autowired
    private CityService                  cityService;
    @Autowired
    private TAgencyDao                   agencyDao;
    @Autowired
    private AssetRejectRecordDao         assetRejectRecordDao;
    @Autowired
    private ActivityBlackListCityDao     activityBlackListCityDao;
    @Autowired
    private ActivityWhiteListCityDao     activityWhiteListCityDao;
    @Autowired
    private AgencyPortalService          agencyPortalService;
    @Autowired
    private AssetService                 assetService;
    @Autowired
    private DelegationAgreementService   delegationAgreementService;
    @Autowired
    private DealAgreementService         dealAgreementService;
    @Autowired
    private AuctionOrderService          auctionOrderService;
    @Autowired
    private StaffService                 staffService;
    @Autowired
    private AgencyPortalActivityService  agencyPortalActivityService;
    @Autowired
    private DepositService               depositService;
    @Autowired
    private DepositDao                   depositDao;
    @Autowired
    private AccountService               accountService;
    @Autowired
    private SystemProperties             systemProperties;
    @Autowired
    private ActivityRejectRecordDao      activityRejectRecordDao;
    @Autowired
    private FavoriteActivityDao          favoriteActivityDao;
    @Autowired
    private NotifyPartyActivityDao       notifyPartyActivityDao;
    @Autowired
    private AuctionActivityShareStatsDao auctionActivityShareStatsDao;

    @Autowired
    private ServiceOrderService serviceOrderService;
    @Autowired
    private StaffDao            staffDao;

    @Autowired
    private AuctionActivityService          auctionActivityService;
    @Autowired
    private AdditionalAgreementDao          additionalAgreementDao;
    @Autowired
    private AdditionalAgreementService      additionalAgreementService;
    @Resource
    private RedisCachemanager               redisCachemanager;
    @Autowired
    private SmsHelperService                smsHelperService;
    @Autowired
    private AuctionActivityViewCountService auctionActivityViewCountService;

    @Autowired
    private AuctionStepService auctionStepService;

    @Autowired
    private SpvService spvService;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private AssetAuthorizationService assetAuthorizationService;


    @Autowired
    private GatewayMqSender mqSender;
    @Autowired
    private TAccountViewRecordService  tAccountViewRecordService;
    @Autowired
    private PlatformBroadcastDao       platformBroadcastDao;
    @Autowired
    private CityDao                    cityDao;
    @Autowired
    private ProvinceDao                provinceDao;
    @Autowired
    private QiNiuUtil                  qiNiuUtil;
    @Autowired
    private GatewayProperties          gatewayProperties;
    @Reference(version = "1.0.0")
    private WxFacade                   wxFacade;
    @Autowired
    private TPartyDao                  partyDao;
    @Autowired
    private ServiceActivityUtils       serviceActivityUtils;
    @Autowired
    private BankAccountDao bankAccountDao;
    @Autowired
    private BankDao bankDao;
    @Autowired
    private TLeaseApplyDao tLeaseApplyDao;
    @Autowired
    private AssetLeaseDataDao assetLeaseDataDao;
    @Autowired
    private TActivityPosterDao activityPosterDao;
    @Autowired
    private AssistantService assistantService;

    @Autowired
    private BidService bidService;

    @Resource
    private DealAgreementDao dealAgreementDao;



    private static DateFormat df = new SimpleDateFormat(DateUtil.NORM_DATETIME_PATTERN);

    private static final String unSignProtocol = "00";
    private static final String signedProtocol = "10";
    private static final String cantProtocol   = "20";

    @Override
    public AuctionActivity getById(Integer activityId) {
        AuctionActivityCondition condition = new AuctionActivityCondition();
        condition.setId(activityId);
        return auctionActivityDao.selectOneResult(condition);
    }

    @Override
    public PageInfoResp<AuctionActivityVo> getAllByPage(ActivityReq.QueryReq req) {
        PageInfoResp<AuctionActivityVo> resp   = new PageInfoResp<>();
        Map<String, Object>             params = new HashMap<>();
        params.put("agencyId", req.getAgencyId());
        params.put("partyId", req.getPartyId());
        params.put("validStatus", AuctionActivity.VALID_STATUS);
        params.put("comeFrom", req.getComeFrom());
        params.put("name", req.getName());

        if (StringUtils.isNotBlank(req.getQ())) {
            params.put("q", req.getQ());
        }
        if (StringUtils.isNotBlank(req.getStatus())) {
            params.put("status", req.getStatus());
        }
        if (StringUtils.isNotBlank(req.getAgencyName())) {
            params.put("agencyName", req.getAgencyName());
        }
        if (StringUtils.isNotBlank(req.getPartyType()) && StringUtils.isNotBlank(req.getPartyName())) {
            params.put("partyType", req.getPartyType());
            params.put("partyName", req.getPartyName());
        }
        if (StringUtils.isNotBlank(req.getCategoryGroupId())) {
            params.put("categoryGroupId", req.getCategoryGroupId());
        }

        if (StringUtils.isNotBlank(req.getCategoryId())) {
            params.put("categoryId", req.getCategoryId());
        }

        if (StringUtils.isNotBlank(req.getMode())) {
            params.put("mode", req.getMode());
        }
        if (StringUtils.isNotBlank(req.getCreatedAtFrom()) && StringUtils.isNotBlank(req.getCreatedAtTo())) {
            params.put("createdAtFrom", req.getCreatedAtFrom());
            params.put("createdAtTo", req.getCreatedAtTo());
        }
        if (StringUtils.isNotBlank(req.getPreviewAtFrom()) && StringUtils.isNotBlank(req.getPreviewAtTo())) {
            params.put("previewAtFrom", req.getPreviewAtFrom());
            params.put("previewAtTo", req.getPreviewAtTo());
        }
        if (StringUtils.isNotBlank(req.getBeginAtFrom()) && StringUtils.isNotBlank(req.getBeginAtTo())) {
            params.put("beginAtFrom", req.getBeginAtFrom());
            params.put("beginAtTo", req.getBeginAtTo());
        }
        if (StringUtils.isNotBlank(req.getVisibilityLevel())) {
            params.put("visibilityLevel", req.getVisibilityLevel());
        }
        if (req.getExcludeAppletHallActivity() != null) {
            params.put("excludeAppletHallActivity", req.getExcludeAppletHallActivity());
        }
        if (req.getHallId() != null) {
            params.put("hallId", req.getHallId());
        }
        if (req.getExcludeAssetPropertyActivity() != null) {
            params.put("excludeAssetPropertyActivity", req.getExcludeAssetPropertyActivity());
        }
        if (req.getExcludeAlbumActivity() != null) {
            params.put("excludeAlbumActivity", req.getExcludeAlbumActivity());
        }
        if (req.getFrontFlag() != null) {
            params.put("frontFlag", req.getFrontFlag());
        }
        PageInfo                pageInfo          = auctionActivityDao.getAuctionActivityList(req.getPage(), req.getPerPage(), params, "aa.id desc");
        List<AuctionActivityVo> itemsList         = new ArrayList<>();
        List<AuctionActivity>   auctionActivities = pageInfo.getList();
        for (AuctionActivity auctionActivity : auctionActivities) {
            try {
                itemsList.add(processAuctionActivity(auctionActivity));
            } catch (Exception e) {
                e.printStackTrace();
                mqSender.sendTryCatchExceptionEmail(auctionActivity.getId(), e);
            }
        }
        resp.setList(itemsList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    public PageInfo getAllByPage(int page, int perPage, AuctionActivityCondition condition, String orderBy) {
        PageHelper.startPage(page, perPage);
        if (StringUtils.isNotBlank(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        List<AuctionActivity> list = auctionActivityDao.selectList(condition);
        return new PageInfo<>(list);
    }

    @Override
    public List<AuctionActivity> getByAssetId(Integer assetId) {
        PageHelper.orderBy("status asc,created_at desc");
        AuctionActivityCondition condition = new AuctionActivityCondition();
        condition.setAssetId(assetId);
        return auctionActivityDao.selectList(condition);
    }

    @Override
    public List<AuctionActivity> getOnlinedActivity(String status) {
        AuctionActivityCondition condition = new AuctionActivityCondition();
        condition.setStatus(status);
        return auctionActivityDao.selectList(condition);
    }

    @Override
    public String specialDetail(Integer activityId) {
        return auctionActivityDao.specialDetail(activityId);
    }

    @Override
    public PageInfo activityList(int page, int perPage, String assetPropertyName, String activityName, String agencyName) {
        PageHelper.startPage(page, perPage);
        List<Map> maps = auctionActivityDao.activityList(assetPropertyName, activityName, agencyName);
        return new PageInfo<>(maps);
    }

    @Override
    public AuctionActivityVo getActivityById(Integer activityId) {
        AuctionActivity auctionActivity = auctionActivityDao.selectById(activityId);
        if (auctionActivity == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        return processAuctionActivity(auctionActivity);
    }

    @Override
    public ActivityResp getActivity(ActivityReq.ActivityId req) {
        ActivityResp    resp            = new ActivityResp();
        AuctionActivity auctionActivity = auctionActivityDao.selectById(req.getActivityId());
        if (auctionActivity == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        resp.setActivity(processAuctionActivity(auctionActivity));
        return resp;
    }

    @Override
    public PageInfoResp<AuctionActivityVo> getAgencyPortalActivityListByPage(ActivityReq.QueryReq req) {
        PageInfoResp<AuctionActivityVo> resp   = new PageInfoResp<>();
        Map<String, Object>             params = new HashMap<>();
        params.put("hiddenStatus", AuctionActivity.HIDDEN_STATUS);
        AgencyPortal agencyPortal = agencyPortalService.getByAgencyId(req.getAgencyId());
        if (agencyPortal == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        params.put("agencyPortalId", agencyPortal.getId());
        if (StringUtils.isNotBlank(req.getStatus())) {
            params.put("status", req.getStatus());
        }
        if (StringUtils.isNotBlank(req.getCategoryGroupId())) {
            params.put("categoryGroupId", req.getCategoryGroupId());
        }
        if (StringUtils.isNotBlank(req.getQ())) {
            params.put("q", req.getQ());
        }
        PageInfo                pageInfo          = auctionActivityDao.getAgencyPortalActivityList(req.getPage(), req.getPerPage(), params, "pa.id desc");
        List<AuctionActivityVo> itemsList         = new ArrayList<>();
        List<AuctionActivity>   auctionActivities = pageInfo.getList();
        for (AuctionActivity auctionActivity : auctionActivities) {
            try {
                AuctionActivityVo auctionActivityVo = processAuctionActivity(auctionActivity);
                auctionActivityVo.setViewCount(auctionActivityViewCountService.viewCount(auctionActivity.getId()));
                AgencyPortalActivity agencyPortalActivity = agencyPortalActivityService.getByAgencyPortalIdActivityId(agencyPortal.getId(), auctionActivity.getId());
                auctionActivityVo.setMyViewCount(agencyPortalActivity.getViewCount());
                auctionActivityVo.setUnionParticipantNumber(depositService.countParticipantNumber(auctionActivity.getId(), agencyPortal.getAgencyId()));
                itemsList.add(auctionActivityVo);
            } catch (Exception e) {
                e.printStackTrace();
                mqSender.sendTryCatchExceptionEmail(auctionActivity.getId(), e);
            }
        }

        resp.setList(itemsList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public PageInfoResp<AuctionActivityVo> getAvailablePlatformActivityListByPage(ActivityReq.QueryReq req) {
        PageInfoResp<AuctionActivityVo> resp         = new PageInfoResp<>();
        Map<String, Object>             params       = new HashMap<>();
        AgencyPortal                    agencyPortal = agencyPortalService.getByAgencyId(req.getAgencyId());
        if (agencyPortal == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        params.put("agencyPortalId", agencyPortal.getId());
        if (StringUtils.isNotBlank(req.getQ())) {
            params.put("q", req.getQ());
        }
        if (StringUtils.isNotBlank(req.getUnionStatus())) {
            params.put("unionStatus", req.getUnionStatus());
        }
        if (StringUtils.isNotBlank(req.getCategoryGroupId())) {
            params.put("categoryGroupId", req.getCategoryGroupId());
        }
        if (req.getPropertyId() != null) {
            params.put("propertyId", req.getPropertyId());
        }
        PageInfo                pageInfo          = auctionActivityDao.getAvailablePlatformActivityList(req.getPage(), req.getPerPage(), params, "t.id desc");
        List<AuctionActivityVo> itemsList         = new ArrayList<>();
        List<AuctionActivity>   auctionActivities = pageInfo.getList();
        for (AuctionActivity auctionActivity : auctionActivities) {
            try {
                AuctionActivityVo    auctionActivityVo    = processAuctionActivity(auctionActivity);
                AgencyPortalActivity agencyPortalActivity = agencyPortalActivityService.getByAgencyPortalIdActivityId(agencyPortal.getId(), auctionActivity.getId());
                if (agencyPortalActivity != null) {
                    auctionActivityVo.setUnitedStatus(ActivityEnum.UnionStatus.UNITED.getKey());
                } else {
                    auctionActivityVo.setUnitedStatus(ActivityEnum.UnionStatus.NOT_UNITED.getKey());
                }
                itemsList.add(auctionActivityVo);
            } catch (Exception e) {
                e.printStackTrace();
                mqSender.sendTryCatchExceptionEmail(auctionActivity.getId(), e);
            }
        }
        resp.setList(itemsList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public PageInfo search(ActivityReq.Search req) {

        //获取查询参数
        Map<String, Object> params = getSearchParams(req);

        log.info("搜索条件为：{}", JSON.toJSONString(params));
        PageInfo pageInfo = auctionActivityDao.search(req.getPage(), req.getPerPage(), params);
        if (StringUtils.isNotEmpty(req.getTodayFlag()) && pageInfo.getList().size() == 0) {
            params.remove("todayTime");
            params.put("orderBy", "recentUpload");
            params.put("activityStatus", "online");
            pageInfo = auctionActivityDao.search(1, 10, params);
            pageInfo.setHasNextPage(false);
            pageInfo.setTotal(pageInfo.getList().size());
        }
        List<SearchAuctionActivityVo> list = pageInfo.getList();
        log.info("搜索结果为：{}", JSON.toJSONString(list));
        for (SearchAuctionActivityVo vo : list) {
            Integer count = depositDao.getDepositCount(vo.getActivityId());
            vo.setDepositCount(count);
            if("-1".equals(vo.getCategoryId())){
                vo.setCategoryName("租赁权拍卖");
            }
        }
        return pageInfo;
    }

    private Map<String, Object> getSearchParams(ActivityReq.Search req) {
        Map<String, Object> params = new HashMap<>();

        if (StringUtils.isNotBlank(req.getQuery())) {
            params.put("query", req.getQuery());
        }

        if (StringUtils.isNotBlank(req.getActivityMode())) {
            params.put("activityMode", req.getActivityMode());
        }

        if (StringUtils.isNotBlank(req.getAgencyCode())) {
            params.put("agencyCode", req.getAgencyCode());
        }

        if (StringUtils.isNotBlank(req.getActivityStatus())) {
            params.put("activityStatus", req.getActivityStatus());
        }

        if (null != req.getCategoryId()) {
            if (req.getCategoryId().intValue() == -1) { // 租赁权拍卖类型
                req.setPartyCategoryId(null);
                req.setBusTypeName(null);
            } else {
                req.setAssetLeaseType(null);
                req.setLeaseAreaType(null);
            }
            params.put("categoryId", req.getCategoryId());
        }
        if (null != req.getProvinceId()) {
            params.put("provinceId", req.getProvinceId());
        }
        if (null != req.getCityId()) {
            params.put("cityId", req.getCityId());
        }
        if (null != req.getAreaId()) {
            params.put("areaId", req.getAreaId());
        }

        if (null != req.getPartyCategoryId()) {
            if (1 == req.getPartyCategoryId()) {
                params.put("partyCategory", PartyEnum.Category.BANK_COMPANY.getKey());
            } else if (2 == req.getPartyCategoryId()) {
                params.put("partyCategory", PartyEnum.Category.AMC_COMPANY.getKey());
            } else if (3 == req.getPartyCategoryId()) {
                params.put("partyCategory", PartyEnum.Category.FOLK_ASSET_COMPANY.getKey());
            } else if (4 == req.getPartyCategoryId()) {
                params.put("partyCategory", PartyEnum.Category.NORMAL_USER.getKey());
            } else if (5 == req.getPartyCategoryId()) {
                params.put("partyCategory", PartyEnum.Category.AUCTION_COMPANY.getKey());
            } else if (6 == req.getPartyCategoryId()) {
                params.put("partyCategory", PartyEnum.Category.NORMAL_COMPANY.getKey());
            }
        }

        if (StringUtils.isNotEmpty(req.getTodayFlag())) {
            params.put("todayTime", req.getTodayFlag());
        }

        if (null != req.getPropertyNameId()) {
            params.put("propertyNameId", req.getPropertyNameId());
        }
        if (StringUtils.isNotEmpty(req.getBusTypeName())) {
            params.put("busTypeName", req.getBusTypeName());
        }

        PageHelper.startPage(req.getPage(), req.getPerPage());


        //if (StringUtils.isNotEmpty(req.getOrderBy())) {
        //    params.put("orderVar1", req.getOrderBy().split("_")[0]);
        //    params.put("orderVar2", req.getOrderBy().split("_")[1]);
        //}
        params.put("orderBy", StringUtils.isEmpty(req.getOrderBy()) ? "default" : req.getOrderBy());

        if (null != req.getJoint()) {
            params.put("joint", req.getJoint());
        }


        if (null != req.getJointAgencyId()) {
            params.put("jointAgencyId", req.getJointAgencyId());
        }

        //添加资产类表字段
        if (null != req.getAssetClass()) {
            params.put("assetClass", req.getAssetClass());
        }

        if (StringUtils.isNotBlank(req.getLeaseAreaType())) {
            params.put("leaseAreaType", req.getLeaseAreaType());
        }

        if ( StringUtils.isNotBlank(req.getAssetLeaseType())) {
            params.put("assetLeaseType", req.getAssetLeaseType());
        }

        if (StringUtils.isNotBlank(req.getTownId())) {
            params.put("townId", req.getTownId());
        }
        if (StringUtils.isNotBlank(req.getBeginPrice())) {
            params.put("beginPrice", req.getBeginPrice());
        }
        if (StringUtils.isNotBlank(req.getEndPrice())) {
            params.put("endPrice", req.getEndPrice());
        }
        setActivityPosterParam(params, req);

        return params;
    }

    private void setActivityPosterParam(Map<String, Object> params, ActivityReq.Search req) {
        if (req.getActivityPosterId() == null) {
            return;
        }
        TActivityPoster activityPoster = activityPosterDao.selectById(req.getActivityPosterId());
        if (activityPoster == null) {
            return;
        }
        if (activityPoster.getAutoFlag()) {
            if (StringUtils.isNotBlank(activityPoster.getCategory())) {
                List<String> categoryIdList = new ArrayList<>();
                List<String> categoryList = Arrays.asList(activityPoster.getCategory().split(","));
                for (String item : categoryList) {
                    if (item.startsWith(ActivityServiceProviderEnum.ActivityType.Auction.getKey())) {
                        categoryIdList.add(item.split("_")[1]);
                    }
                }
                if (categoryIdList.size() > 0) {
                    params.put("categoryIdList", categoryIdList);
                } else {
                    params.put("categoryIdList", Arrays.asList(SystemConstant.NONE));
                }
            } else {
                params.put("categoryIdList", Arrays.asList(SystemConstant.NONE));
            }
            if (StringUtils.isNotBlank(activityPoster.getStatus())) {
                List<String> status2List = new ArrayList<>();
                List<String> statusList = Arrays.asList(activityPoster.getStatus().split(","));
                for (String item : statusList) {
                    if (item.startsWith(ActivityServiceProviderEnum.ActivityType.Auction.getKey())) {
                        status2List.add(item.split("_")[1]);
                    }
                }
                if (status2List.size() > 0) {
                    params.put("statusList", status2List);
                } else {
                    params.put("statusList", Arrays.asList(SystemConstant.NONE));
                }
            } else {
                params.put("statusList", Arrays.asList(SystemConstant.NONE));
            }
            if (StringUtils.isNotBlank(activityPoster.getBusType())) {
                List<String> busTypeListStr = Arrays.asList(activityPoster.getBusType().split(","));
                Set<String> busTypeSet = new HashSet<>();
                for (String item : busTypeListStr) {
                    busTypeSet.add(item);
                    String key = AssetEnum.LeaseAssetType.getKeyByValue(item);
                    if (StringUtils.isNotBlank(key)) {
                        busTypeSet.add(key);
                    }
                }
                if (busTypeSet.size() > 0) {
                    params.put("busTypeList", busTypeSet);
                } else {
                    params.put("busTypeList", Arrays.asList(SystemConstant.NONE));
                }
            } else {
                params.put("busTypeList", Arrays.asList(SystemConstant.NONE));
            }
            if (activityPoster.getProvinceId() != null) {
                params.put("activityPosterProvinceId", activityPoster.getProvinceId());
            }
            if (activityPoster.getCityId() != null) {
                params.put("activityPosterCityId", activityPoster.getCityId());
            }
            if (activityPoster.getAreaId() != null) {
                params.put("activityPosterAreaId", activityPoster.getAreaId());
            }
        } else {
            if (StringUtils.isNotBlank(activityPoster.getActivityIds())) {
                JSONArray jsonArray = JSON.parseArray(activityPoster.getActivityIds());
                List<Integer> activityIdList = new ArrayList<>();
                for (Object item : jsonArray) {
                    JSONObject jsonObject = (JSONObject) item;
                    if (ActivityServiceProviderEnum.ActivityType.Auction.getKey().equals(jsonObject.getString("activityType"))) {
                        activityIdList.add(jsonObject.getIntValue("activityId"));
                    }
                }
                if (activityIdList.size() > 0) {
                    params.put("activityIdList", activityIdList);
                } else {
                    params.put("activityIdList", Arrays.asList(-1));
                }
            } else {
                params.put("activityIdList", Arrays.asList(-1));
            }
        }
    }

    /**
     * 机构审核通过
     */
    @Transactional
    @Override
    public ActivityResp agencyApprove(ActivityReq.ActivityId req) {
        ActivityResp    resp            = new ActivityResp();
        AuctionActivity auctionActivity = auctionActivityDao.selectById(req.getActivityId());
        if (auctionActivity == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!ActivityEnum.Status.AGENCY_PENDING.equals(auctionActivity.getStatus())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        Asset asset = assetDao.selectById(auctionActivity.getAssetId());
        if (!asset.getStatus().equals(AssetEnum.Status.DELIVERING)) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        int result = assetDao.updateStatus(asset.getId(), AssetEnum.Status.APPROVED);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        result = auctionActivityDao.updateStatus(auctionActivity.getId(), ActivityEnum.Status.PLATFORM_REVIEWING);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        sendAgencyApproveNotify(asset);
        resp.setActivityId(req.getActivityId());
        return resp;
    }

    private void sendAgencyApproveNotify(Asset asset) {
        try {
            TAgency agency = agencyDao.selectById(asset.getAgencyId());
            // 个人或公司管理员
            if (AssetEnum.ComeFrom.PLATFORM.getKey().equals(asset.getComeFrom())) {
                smsHelperService.agencySuccessfulReview(accountService.getNotifierMobile(asset.getPartyId()), asset.getName(), agency.getName());
            }
            List<String> notifierMobiles = new ArrayList<>();
            // 平台客服
            notifierMobiles.addAll(accountService.getPlatformNotifierMobile());
            for (String notifierMobile : notifierMobiles) {
                smsHelperService.platformReviewNotify(notifierMobile, asset.getName(), agency.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("机构审核通过发送短信失败，assetId={}，errorMsg={}", asset.getId(), e.getMessage());
            mqSender.sendTryCatchExceptionEmail(asset.getId(), e);
        }
    }

    @Transactional
    @Override
    public ActivityResp agencyReject(ActivityReq.ActivityId req) {
        ActivityResp    resp            = new ActivityResp();
        AuctionActivity auctionActivity = getById(req.getActivityId());
        Asset           asset           = assetDao.selectById(auctionActivity.getAssetId());
        if (!asset.getStatus().equals(AssetEnum.Status.DELIVERING)) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        int result = assetDao.updateStatus(asset.getId(), AssetEnum.Status.REJECT);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        result = auctionActivityDao.updateStatus(auctionActivity.getId(), ActivityEnum.Status.AGENCY_REJECT);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        result = assetRejectRecordDao.save(asset.getId(), req.getReason());
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        resp.setActivityId(req.getActivityId());
        return resp;
    }

    @Transactional
    @Override
    public ActivityResp platformApprove(ActivityReq.ActivityId req) {
        ActivityResp    resp     = new ActivityResp();
        AuctionActivity activity = auctionActivityDao.selectById(req.getActivityId());
        if (!activity.getStatus().equals(ActivityEnum.Status.PLATFORM_REVIEWING)) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        Asset asset = assetDao.selectById(activity.getAssetId());
        updateAssetOnlinedIfNeed(req, asset);
        //租赁权 修改 租赁权 审核通过直接上线状态
        Boolean isLease = asset.getCategoryId()!=null&&"-1".equals(String.valueOf(asset.getCategoryId()));
        if (AssetEnum.ComeFrom.AGENCY.getKey().equals(asset.getComeFrom())||isLease) {
            // 标的状态为正在拍卖
            asset.setStatus(AssetEnum.Status.SELLING);
            asset.setUpdatedAt(new Date());
            int result = assetDao.updateById(asset);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            result = auctionActivityDao.updateStatus(req.getActivityId(), ActivityEnum.Status.ONLINE, req.getOperatorId());
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }

            if(AssetEnum.ComeFrom.AGENCY.getKey().equals(asset.getComeFrom())){
                PlatformBroadcast platformBroadcast = new PlatformBroadcast();
                platformBroadcast.setActivityId(activity.getId());
                platformBroadcast.setStatus(ActivityEnum.ActivityBroadcastStatus.UP_FOR_AUCTION.getKey());
                platformBroadcast.setCreatedAt(new Date());
                result = platformBroadcastDao.insert(platformBroadcast);
                if (result == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
            }
            // 设置活动过期时间
            assistantService.setActivityExpireTime(activity);
        } else {
            int result = auctionActivityDao.updateStatus(req.getActivityId(), ActivityEnum.Status.PLATFORM_APPROVED, req.getOperatorId());
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            delegationAgreementService.generateContract(req.getActivityId());
        }
        sendPlatformApproveNotify(asset,isLease);
        return resp;
    }

    private void updateAssetOnlinedIfNeed(ActivityReq.ActivityId req, Asset asset) {
        if (!AssetEnum.ComeFrom.PLATFORM.getKey().equals(asset.getComeFrom())) {
            return;
        }


        if(asset.getCategoryId()!=null&&"-1".equals(String.valueOf(asset.getCategoryId()))){
            if(!req.getOnlined().equals(0)||req.getBankOfflineFlag()){
                throw new BusinessException("租赁权拍卖只能选择线下普通类支付方式！");
            }
        }
        AccountBaseDto seller = accountService.getAccountBaseByPartyId(asset.getPartyId());
        if (req.getOnlined().equals(0) && !seller.getOperOffline()) {
            throw new BusinessException("请先设置委托人为线下支付方式");
        }
        if (req.getOnlined().equals(1) && !seller.isPayBind()) {
            throw new BusinessException("请先开通东方付通账户");
        }
        asset.setOnlined(req.getOnlined());
        if (req.getOnlined().equals(0) && req.getBankOfflineFlag()) {
            if (!bankAccountDao.hasBankAccount(req.getPartyId())) {
                throw new BusinessException("请先设置委托人银行账户");
            }
            asset.setBankOfflineFlag(req.getBankOfflineFlag());
        }
        asset.setUpdatedAt(new Date());
        int result = assetDao.updateById(asset);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);

        }
    }

    private void sendPlatformApproveNotify(Asset asset,Boolean isLease) {
        try {
            if (AssetEnum.ComeFrom.PLATFORM.getKey().equals(asset.getComeFrom())) {

                //租赁权发送短信
                if(isLease){
                    smsHelperService.platformReviewLeasePassedNotify(accountService.getNotifierMobile(asset.getPartyId()), asset.getName());
                }else {
                    // 个人或公司管理员
                    smsHelperService.platformReviewPassedNotify(accountService.getNotifierMobile(asset.getPartyId()), asset.getName());
                }
            } else {
                // 机构管理员
                smsHelperService.platformReviewPassedNotify(accountService.getAgencyNotifierMobile(asset.getPartyId()), asset.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("平台审核通过发送短信失败，assetId={}，errorMsg={}", asset.getId(), e.getMessage());
            mqSender.sendTryCatchExceptionEmail(asset.getId(), e);
        }
    }

    @Transactional
    @Override
    public ActivityResp platformReject(ActivityReq.ActivityId req) {
        ActivityResp    resp            = new ActivityResp();
        AuctionActivity auctionActivity = auctionActivityDao.selectById(req.getActivityId());
        if (!auctionActivity.getStatus().equals(ActivityEnum.Status.PLATFORM_REVIEWING)) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        int result = auctionActivityDao.updateStatus(req.getActivityId(), ActivityEnum.Status.PLATFORM_REJECTED, req.getOperatorId());
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        result = activityRejectRecordDao.save(req.getActivityId(), req.getReason());
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        resp.setActivityId(req.getActivityId());
        return resp;
    }

    @Transactional
    @Override
    public ActivityResp forceWithdraw(ActivityReq.ActivityId req) {
        ActivityResp    resp            = new ActivityResp();
        AuctionActivity auctionActivity = auctionActivityDao.selectById(req.getActivityId());
        if (auctionActivity == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        Date now = new Date();
        if (auctionActivity.getBeginAt().before(now)) {
            throw new BusinessException("只有正式拍卖之前的活动是可以强行撤回的");
        }
        // 修改活动状态
        int result = auctionActivityDao.updateStatus(req.getActivityId(), ActivityEnum.Status.CANCELLED, req.getOperatorId());
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        // 修改标的状态
        Asset asset = assetDao.selectById(auctionActivity.getAssetId());
        result = assetDao.updateStatus(asset.getId(), AssetEnum.Status.WITHDRAW);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }

        SellerPayInfo sellerInfo = getSellerPayInfo(asset);

        if (asset.getOnlined() == 1 && !sellerInfo.isBank()) {
            //线上，释放保证金
            List<Deposit> depositList = depositService.getDepositByActivityId(auctionActivity.getId());
            for (Deposit deposit : depositList) {
                if (deposit.getStatus().equals(SystemConstant.DEPOSIT_PAY_STATUS_ONLINE_LOCKED)) {
                    relaseDeposit(deposit);
                }
            }
        }

        return resp;
    }


    private SellerPayInfo getSellerPayInfo(Asset asset) {
        SellerPayInfo sellerPayInfo = new SellerPayInfo();
        if ("0".equals(asset.getComeFrom())) {
            //平台
            AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(asset.getPartyId());
            sellerPayInfo.setMobile(accountBaseDto.getMobile());
            if (String.valueOf(asset.getSpvId()).equals("0")) {

                sellerPayInfo.setBank(accountBaseDto.isBank());
                sellerPayInfo.setDfftId(accountBaseDto.getDfftId());
                sellerPayInfo.setFddId(accountBaseDto.getFadadaId());
                sellerPayInfo.setName(accountBaseDto.getName());
            } else {
                TSpv spv = spvService.getSpvById(asset.getSpvId());
                sellerPayInfo.setFddId(spv.getFddId());
                sellerPayInfo.setName(spv.getName());
                sellerPayInfo.setBank(false);
                sellerPayInfo.setDfftId(spv.getDfftId());


            }

        } else if ("1".equals(asset.getComeFrom())) {
            //机构
            TAgency agency = agencyService.findByAgencyId(asset.getPartyId());
            sellerPayInfo.setMobile(agency.getMobile());
            sellerPayInfo.setBank(false);
            sellerPayInfo.setName(agency.getName());
            sellerPayInfo.setDfftId(agency.getDfftId());
            sellerPayInfo.setFddId(agency.getFadadaId());

        }
        return sellerPayInfo;
    }

    private void relaseDeposit(Deposit deposit) {
        UnifiedPayReq unifiedPayReq = invokeGatewayReleaseReq(deposit.getPartyId(), deposit.getAmount(), deposit.getId());
        LOGGER.info("开始调用 payFacade unifiedPay,参数:{}", JSON.toJSONString(unifiedPayReq));
        UnifiedPayResp payResp = payFacade.unifiedPay(unifiedPayReq);
        LOGGER.info("结束调用 payFacade unifiedPay,参数:{}，结果:{}", JSON.toJSONString(unifiedPayReq), JSON.toJSONString(payResp));
        insertAuctionStep(deposit.getActivityId(), null, deposit.getPartyId(), "强制撤销保证金释放", JSON.toJSONString(unifiedPayReq),
                JSON.toJSONString(payResp), null, PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode()) ? "SUCCESS" : "FAIL");
        if ((payResp != null && PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode()))) {
            updateDeposit(deposit.getId(), SystemConstant.DEPOSIT_PAY_STATUS_ONLINE_RELEASED, "保证金释放成功", payResp.getPayOrder());
        }
    }

    private boolean updateDeposit(Long depositId, String status, String remark, String payId) {
        Deposit deposit = new Deposit();
        deposit.setPayId(payId);
        deposit.setStatus(status);
        deposit.setId(depositId);
        deposit.setRemark(remark);
        return depositService.updateDepositById(deposit);
    }

    public void insertAuctionStep(Integer activityId, Long orderId, Integer partyId, String step, String req, String resp, String excep, String status) {
        TAuctionStepRecord record = new TAuctionStepRecord();
        record.setActivityId(activityId);
        if (partyId != null) {
            record.setPartyId(partyId);
        }
        if (orderId != null) {
            record.setOrderId(orderId);
        }
        record.setStep(step);
        record.setReq(req);
        record.setResp(resp);
        record.setCoreException(excep);
        record.setStatus(status);
        auctionStepService.saveAuctionStep(record);
    }

    private UnifiedPayReq invokeGatewayReleaseReq(Integer buyerPartyId, BigDecimal amount, Long depositId) {

        Deposit deposit = depositService.getDepositById(depositId);

        UnifiedPayReq dfftReq = new UnifiedPayReq();
        dfftReq.setAmount(amount);
        dfftReq.setPartyId(buyerPartyId);
        dfftReq.setBusId(String.valueOf(depositId));


        LockOrReleaseOrDirectReq lockOrReleaseOrDirectReq = new LockOrReleaseOrDirectReq();
        AccountBaseDto           buyerInfo                = accountService.getAccountBaseByPartyId(buyerPartyId);
        lockOrReleaseOrDirectReq.setPayMemCode(buyerInfo.getDfftId());
        lockOrReleaseOrDirectReq.setPayMemName(buyerInfo.getName());
        dfftReq.setPayBusCode(PayEnums.PAY_BUS_CODE.RELEASE_DEPOSIT.getType());
        dfftReq.setPayType(PayEnums.PAY_TYPE.RELEASE_DEPOSIT.getType());
        dfftReq.setJumpPay(PayEnums.JUMP_PAY_TYPE.AUTO_PAY.getType());
        lockOrReleaseOrDirectReq.setOriginalPayID(deposit.getPayId());
        lockOrReleaseOrDirectReq.setPayMemName(null);
        lockOrReleaseOrDirectReq.setPayMemCode(null);
        dfftReq.setPayParam(lockOrReleaseOrDirectReq);
        return dfftReq;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ActivityResp updateActivity(ActivityReq.UpdateReq req) {
        ActivityResp    resp            = new ActivityResp();
        AuctionActivity auctionActivity = auctionActivityDao.selectById(req.getId());
        if (auctionActivity == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        auctionActivity = ReqConvertUtil.convertToActivity(req);
        int result = auctionActivityDao.updateById(auctionActivity);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        activityWhiteListCityDao.syncCityIdListByActivityId(auctionActivity.getId(), req.getWhitelistCities());
        activityBlackListCityDao.syncCityIdListByActivityId(auctionActivity.getId(), req.getBlacklistCities());
        return resp;
    }

    @Override
    public Object activityDetail(Integer page, Integer perPage, Integer activityId, Integer partyId, Integer accountId) {
        if (activityId == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "活动ID参数不能为空");
        }
        Map<String, Object> mapList = auctionActivityDao.activityDetail(activityId);

        if (mapList == null) {
            log.info("请求活动编号为：{}", activityId);
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "活动不存在");
        }

        String detail = (String) mapList.get("detail");
        if(StringUtils.isNotBlank(detail)) {
            detail = detail.replaceAll("font-size: \\d*px;", "font-size: 16px;");
        }
        mapList.put("detail", detail);
        Long       orderId    = (Long) mapList.get("orderId");
        Integer    assetId    = (Integer) mapList.get("assetId");
        JSONObject jsonObject = new JSONObject();
        if (orderId != null) {
            DealAgreement activityOrder = dealAgreementService.getByOrderId(orderId,AuctionOfflineEnum.ContractType.DEAL.getKey());
            if (activityOrder != null) {
                jsonObject.put("downLoadUrl", activityOrder.getDownloadUrl());
                jsonObject.put("viewpdfUrl", activityOrder.getViewpdfUrl());
            }
        }
        String activityMode   = (String) mapList.get("activityMode");
        String activityStatus = (String) mapList.get("activityStatus");
        if (activityStatus == null) {
            log.info("查询活动详情失败 当前活动的状态为：{}，查询的详情为：{}", mapList.get("statusStr"), JSON.toJSONString(mapList));
            return null;
        }
        //不是此三个状态的不能查看  在平台页面上
        if (!(activityStatus.equals(ActivityEnum.Status.ONLINE.getKey()) ||
                activityStatus.equals(ActivityEnum.Status.SUCCESS.getKey()) ||
                activityStatus.equals(ActivityEnum.Status.FAILED.getKey()))) {
            log.info("查询活动的数据为：{}", JSON.toJSONString(mapList));
            return null;
        }
        String mode = ActivityEnum.ActivityMode.getKeyByValue(activityMode);
        //黑名单城市
        ActivityBlackListCityCondition condition = new ActivityBlackListCityCondition();
        condition.setActivityId(activityId);
        List<ActivityBlackListCity> activityBlackListCities = activityBlackListCityDao.selectList(condition);
        //白名单城市
        ActivityWhiteListCityCondition whiteListCityCondition = new ActivityWhiteListCityCondition();
        whiteListCityCondition.setActivityId(activityId);
        List<ActivityWhiteListCity> activityWhiteListCities = activityWhiteListCityDao.selectList(whiteListCityCondition);
        mapList.put("delegationAgreement", jsonObject);
        mapList.put("activityBlackListCities", activityBlackListCities);
        mapList.put("activityWhiteListCities", activityWhiteListCities);
        // 获取 关注数 和 提醒数
        Integer favorCount     = serviceActivityUtils.getFocusNum(activityId);
        Integer notifiersCount = serviceActivityUtils.getReminderNum(activityId);
        if (accountId == null) {
            mapList.put("favor_count", favorCount);
            mapList.put("favor_status", false);
            mapList.put("notifiers_count", notifiersCount);
            mapList.put("notify_me_status", false);
        } else {
            FavoriteActivityCondition condition1 = new FavoriteActivityCondition();
            condition1.setPartyId(partyId);
            condition1.setAccountId(accountId);
            condition1.setType("0");
            condition1.setActivityId(activityId);
            FavoriteActivity    favoriteActivity    = favoriteActivityDao.selectFirst(condition1);
            NotifyPartyActivity notifyPartyActivity = notifyPartyActivityDao.selectByPartyIdAndActivityId(partyId, activityId, accountId);
            mapList.put("favor_status", favoriteActivity != null);
            mapList.put("favor_count", favorCount);
            mapList.put("notifiers_count", notifiersCount);
            mapList.put("notify_me_status", notifyPartyActivity != null);
        }


        //设置浏览量
        mapList.put("viewCount", auctionActivityViewCountService.viewCount(activityId));

        ActivityReq.ActivityId req = new ActivityReq.ActivityId();
        req.setActivityId(activityId);
        req.setPage(page);
        req.setPerPage(perPage);
        Map<String, Object> map = auctionActivityService.unionData(req);

        int depositCount = depositService.countParticipantNumber(activityId);
        mapList.put("depositCount", depositCount);
        Date                     beginAt      = (Date) mapList.get("beginAt");
        ShopEnum.NewOnlineStatus onlineStatus = RespConvertUtil.getOnlineStatus(activityStatus, beginAt);
        if (onlineStatus == null) {
            log.info("查询活动详情失败 当前活动的状态1为==============={}", mapList.get("statusStr"));
            return null;
        } else {
            mapList.put("statusStr", onlineStatus.getValue());
            mapList.put("activityStatus", onlineStatus.getKey());
        }

        if("-1".equals(String.valueOf(mapList.get("categoryId")))){
            mapList.put("assetcategoryName","租赁权拍卖");
            mapList.put("leaseCommission","首月租金");
            //获取租赁权状态
            setLeaseDataResult(mapList);

            //租赁权不连拍
            if(mapList.get("jointStatus")!=null&&"1".equals(String.valueOf(mapList.get("jointStatus")))){
                map = getNotJointMap();
            }
        }

        Deposit deposit = null;
        try {
            deposit = depositService.getByActivityIdPartId(activityId, partyId);
        } catch (Exception e) {
            log.info("查询保证金参数，活动ID为：{}，用户ID为：{}", activityId, partyId);
        }

        AuctionOrder order   = auctionOrderService.getFirstByActivityId(activityId);
        Integer      buyerId = null;
        if (order != null) {
            buyerId = order.getBuyerId();
            log.info("购买人ID为：{}，登录人ID为：{}", buyerId, partyId);
        }

        //详情按钮控制
        setBannerStatus(activityStatus,mapList,deposit,buyerId,beginAt,partyId);

        Asset asset = assetService.getAsset(assetId);
        log.info("查询的标的信息为{}", JSON.toJSONString(asset));
        if (asset == null) {
            return null;
        }
        BigDecimal evaluationReport        = asset.getEvaluationReport();
        BigDecimal tuneReport              = asset.getTuneReport();
        Boolean    tuneReportAuthorization = asset.getTuneReportAuthorization();

        //评估
        if (evaluationReport != null) {
            mapList.put("evaluationReport", true);
            mapList.put("evaluationReportStatus", evaluationReport);

        } else {
            mapList.put("evaluationReport", false);
        }

        //尽调
        if (tuneReportAuthorization != null) {
            if (tuneReportAuthorization) {
                if (tuneReport != null) {
                    mapList.put("tuneReport", tuneReport);
                    if (null != partyId) {
                        log.info("查询尽调报告是否已支付的参数为：登录人ID为：{}，活动ID为：{}", partyId.longValue(), activityId);
                        AccountBaseDto accountBaseByPartyId = accountService.getAccountBaseByPartyId(partyId);
                        boolean        orderStatus          = serviceOrderService.adjustedPayStatus(Long.valueOf(accountBaseByPartyId.getAccountId()), activityId);
                        log.info("查询尽调报告支付结果为：{}", orderStatus);
                        if (orderStatus) {
                            mapList.put("wxOrderStatus", true);
                        } else {
                            mapList.put("wxOrderStatus", false);
                        }
                    }
                    mapList.put("tuneReportStatus", true);
                } else {
                    mapList.put("tuneReportStatus", false);
                }
            } else {
                mapList.put("tuneReportStatus", false);
            }
        } else {
            mapList.put("tuneReportStatus", false);
        }
        String reportSource = assetAuthorizationService.getReportSource(activityId);
        mapList.put("tuneReportSource", reportSource);
        mapList.put("tuneReportSourceDesc", SurveyReportEnum.ReportSourceEnum.getValueByKey(reportSource));
        Date date = ((Date) mapList.get("reducedAt"));
        if (date != null) {
            mapList.put("reducedTime", ((Date) mapList.get("reducedAt")).getTime());
        }

        Date incrementAt = ((Date) mapList.get("incrementAt"));
        if (incrementAt != null) {
            mapList.put("incrementTime", ((Date) mapList.get("incrementAt")).getTime());
        }


        mapList.put("agencys", map);
        mapList.put("dataTime", System.currentTimeMillis());
        mapList.put("beginTime", ((Date) mapList.get("beginAt")).getTime());
        BigDecimal reservePrice = (BigDecimal) mapList.get("reservePrice");

        //一口价明标和一口价暗标起拍价 = 保留价  1/4
        if (activityMode.equals(ActivityEnum.ActivityMode.PUBLIC.getKey()) ||
                activityMode.equals(ActivityEnum.ActivityMode.SEALED.getKey())) {
            mapList.put("startingPrice", reservePrice);
        }
        if (null != reservePrice) {
            mapList.put("reservePrice", "有");
        } else {
            mapList.put("reservePrice", "无");
        }
        String preemptivePerson = (String) mapList.get("preemptivePerson");
        if (null != preemptivePerson) {
            mapList.put("preemptivePerson", "有");
        } else {
            mapList.put("preemptivePerson", "无");
        }

        mapList.put("endTime", ((Date) mapList.get("endAt")).getTime());
        mapList.put("modeStr", mode);
        if (asset.getBankOfflineFlag()) {
            BankAccount bankAccount = bankAccountDao.getLatestByPartyId(asset.getPartyId());
            if (bankAccount != null) {
                mapList.put("offlineBankAccountNumber", bankAccount.getNumber());
                mapList.put("offlineBankAccountName", bankAccount.getName());
                mapList.put("offlineBankName", bankDao.getName(bankAccount.getBankId()));
            }
        } else {
            mapList.put("offlineBankAccountNumber", systemProperties.getOfflineBankAccountNumber());
            mapList.put("offlineBankAccountName", systemProperties.getOfflineBankAccountName());
            mapList.put("offlineBankName", systemProperties.getOfflineBankName());
        }


        if (partyId == null) {
            mapList.put("dialogFlag", "0");
        } else {
            List<Deposit> undealDepositList = depositService.selectNoDealYX(activityId);
            if (undealDepositList == null || undealDepositList.isEmpty()) {
                mapList.put("dialogFlag", "0");
            } else {
                AuctionActivity activity = auctionActivityDao.getById(activityId);
                for (Deposit undealDeposit : undealDepositList) {
                    if (activity.getCurrentLevel() == undealDeposit.getLevel()
                            && String.valueOf(partyId).equals(String.valueOf(undealDeposit.getPayId()))) {
                        mapList.put("dialogFlag", "1");
                        mapList.put("dialogUserName", undealDeposit.getCode());
                        mapList.put("dialogUserLevel", undealDeposit.getLevel());
                    } else {
                        mapList.put("dialogFlag", "0");
                    }
                }
            }
        }

        //新增足迹浏览记录
        if (accountId != null) {
            String newPartyId = partyId == null ? null : partyId.toString();
            tAccountViewRecordService.findAndSaveViewRecord(accountId.toString(), newPartyId, activityId.toString(), AccountEnum.ViewType.AUCTION.getKey());
        }

        //添加最新的出价人
        mapList.put("bidder",getBidderInfo(activityId,partyId));

        //添加是否签署的标志
        putSignFlagInfo(mapList,partyId,order);


        return mapList;
    }

    private void putSignFlagInfo(Map<String,Object> mapList,Integer partyId,AuctionOrder order) {

        mapList.put("buyerSignFlag","0");
        mapList.put("sellerSignFlag","0");

        try{

            Long  orderId    = (Long) mapList.get("orderId");
            if (orderId != null&&partyId !=null) {
                DealAgreement activityOrder = dealAgreementService.getByOrderId(orderId,AuctionOfflineEnum.ContractType.DEAL.getKey());
                if(activityOrder !=null){
                    mapList.put("buyerSignFlag",!activityOrder.getBuyerSigned()&&order.getBuyerId()==partyId?"1":"0");
                    mapList.put("sellerSignFlag",!activityOrder.getSellerSigned()&&order.getSellerId()==partyId?"1":"0");
                }
            }

        }catch (Exception e){

        }


    }



    /**
     *
     *获取最新出价人
     */
    private String getBidderInfo(Integer activityId, Integer partyId) {

        try {
            BidCondition condition = new BidCondition();
            condition.setActivityId(activityId);
            PageInfo pageInfo = bidService.getAllByActivityId(1,1,condition," id desc ");

            List<Bid> list =pageInfo.getList();

            if(list.size()<1){
                return null;
            }

            AuctionActivity auctionActivity = auctionActivityService.getById(activityId);

            //当拍卖为一口价暗标时不展示 或者不为上线状态时
            if (ActivityEnum.ActivityMode.SEALED.getKey().equals(auctionActivity.getMode()) ||
                    auctionActivity.getMode().equals(ActivityEnum.ActivityMode.PUBLIC.getKey())||!ActivityEnum.Status.ONLINE.equals(auctionActivity.getStatus())) {

                return null;
            }

            //当出价人为自己时
            if(partyId!=null&&partyId==list.get(0).getPartyId()){
                return "我";
            }

            Deposit deposit = depositService.getByActivityIdPartId(list.get(0).getActivityId(), list.get(0).getPartyId());


            return deposit.getCode();
        }catch (Exception e){

        }

        return null;


    }

    private Map<String,Object> getNotJointMap() {
        Map<String,Object> resp = new HashMap<>();

        List<AgencyUnionDataVo> list = new ArrayList<>();
        resp.put("hasNextPage", false);
        resp.put("total", 0L);
        resp.put("list", list);

        return resp;
    }


    private void setLeaseBannerStatus(Map<String,Object> mapList, Integer partyId, String subStatus) {

        TLeaseApply tLeaseApply=null;

        if(partyId!=null){
            TLeaseApplyCondition tLeaseApplyCondition = new TLeaseApplyCondition();
            tLeaseApplyCondition.setActivityId((Integer) mapList.get("activityId"));
            tLeaseApplyCondition.setPartId(partyId);
            tLeaseApplyCondition.setDeleteFlag(false);
            tLeaseApply = tLeaseApplyDao.selectOneResult(tLeaseApplyCondition);
        }

        //当用户提交了意向报名时
        if(tLeaseApply != null){
            if(LeaseEnum.ApplyStatus.PENDING.getKey().equals(tLeaseApply.getStatus())&&ShopEnum.NewOnlineStatus.UPCOMING.getKey().equals(mapList.get("activityStatus"))) {
                mapList.put("bannerStatus", LeaseEnum.LeaseBannerStatus.QUALIFICATION_REVIEW.getKey());
                mapList.put("bannerStatusStr",  LeaseEnum.LeaseBannerStatus.QUALIFICATION_REVIEW.getValue());
            //审核拒绝 或者正在拍卖了 还没有审核过 都算拒绝
            }else if(LeaseEnum.ApplyStatus.REJECT.getKey().equals(tLeaseApply.getStatus())||(LeaseEnum.ApplyStatus.PENDING.getKey().equals(tLeaseApply.getStatus())&&ShopEnum.NewOnlineStatus.SALE.getKey().equals(mapList.get("activityStatus")))){
                mapList.put("bannerStatus", LeaseEnum.LeaseBannerStatus.QUALIFICATION_REVIEW_REJECT.getKey());
                mapList.put("bannerStatusStr",  LeaseEnum.LeaseBannerStatus.QUALIFICATION_REVIEW_REJECT.getValue());
                mapList.put("rejectReason",  tLeaseApply.getReason());//拒绝原因

            }else{
                mapList.put("bannerStatus", ActivityEnum.AuctionStatus.NOT_DEPOSIT.getKey());
                mapList.put("bannerStatusStr", ActivityEnum.AuctionStatus.NOT_DEPOSIT.getValue());
            }

        }else{

            if(AssetEnum.LeaseStatus.REGISTRATION_PERIOD.getKey().equals(subStatus)){
                mapList.put("bannerStatus", LeaseEnum.LeaseBannerStatus.INTENTION_REGISTRATION.getKey());
                mapList.put("bannerStatusStr",  LeaseEnum.LeaseBannerStatus.INTENTION_REGISTRATION.getValue());
            }else if(AssetEnum.LeaseStatus.LEASE_APPLY_END.getKey().equals(subStatus)||AssetEnum.LeaseStatus.QUALIFICATION_REVIEW.getKey().equals(subStatus)||ShopEnum.NewOnlineStatus.SALE.getKey().equals((String) mapList.get("activityStatus"))) {
                mapList.put("bannerStatus", LeaseEnum.LeaseBannerStatus.LEASE_APPLY_END.getKey());
                mapList.put("bannerStatusStr",  LeaseEnum.LeaseBannerStatus.LEASE_APPLY_END.getValue());
            }else{
                mapList.put("bannerStatus", ActivityEnum.AuctionStatus.NOT_DEPOSIT.getKey());
                mapList.put("bannerStatusStr", ActivityEnum.AuctionStatus.NOT_DEPOSIT.getValue());
            }
        }

    }

    private void setBannerStatus(String activityStatus,Map<String, Object> mapList,Deposit deposit,Integer   buyerId,Date beginAt ,Integer partyId) {
        Date newTime = new Date();

        //判断当前活动的状态
        if (activityStatus.equals(ActivityEnum.Status.ONLINE.getKey()) && deposit == null) {
            // 是否是租赁权拍卖，如果是就走特殊处理否则就按照正常逻辑处理
            Integer categoryId = (Integer)mapList.get("categoryId");
            String subStatus = (String) mapList.get("subStatus");
            if("-1".equals(String.valueOf(categoryId)) && StringUtils.isNotBlank(subStatus)) {
                setLeaseBannerStatus(mapList, partyId, subStatus);
            }else{
                mapList.put("bannerStatus", ActivityEnum.AuctionStatus.NOT_DEPOSIT.getKey());
                mapList.put("bannerStatusStr", ActivityEnum.AuctionStatus.NOT_DEPOSIT.getValue());
            }
        } else if (DateUtil.getMarginMilliseconds(newTime, beginAt) < 0 && activityStatus.equals(ActivityEnum.Status.ONLINE.getKey())) {
            if (deposit != null) {
                if (!deposit.getStatus().equals(DepositEnum.Status.INIT.getKey())) {
                    log.info("保证金订单状态为1：{}，活动状态为：{},时间状态为：{}:{}", deposit.getStatus(), activityStatus, DateUtil.formatDate2Str(newTime, DateUtil.NORM_DATETIME_PATTERN), DateUtil.formatDate2Str(beginAt, DateUtil.NORM_DATETIME_PATTERN));
                    mapList.put("bannerStatus", ActivityEnum.AuctionStatus.DEPOSIT.getKey());
                    mapList.put("bannerStatusStr", ActivityEnum.AuctionStatus.DEPOSIT.getValue());
                } else {
                    mapList.put("bannerStatus", ActivityEnum.AuctionStatus.NOT_DEPOSIT.getKey());
                    mapList.put("bannerStatusStr", ActivityEnum.AuctionStatus.NOT_DEPOSIT.getValue());
                }
            } else {
                mapList.put("bannerStatus", ActivityEnum.AuctionStatus.NOT_DEPOSIT.getKey());
                mapList.put("bannerStatusStr", ActivityEnum.AuctionStatus.NOT_DEPOSIT.getValue());
            }
        } else if (DateUtil.getMarginMilliseconds(newTime, beginAt) > 0 && activityStatus.equals(ActivityEnum.Status.ONLINE.getKey())) {
            if (deposit != null) {
                if (!deposit.getStatus().equals(DepositEnum.Status.INIT.getKey())) {
                    log.info("保证金订单状态2为：{}，活动状态为：{},时间状态为：{}-{}", deposit.getStatus(), activityStatus, DateUtil.formatDate2Str(newTime, DateUtil.NORM_DATETIME_PATTERN), DateUtil.formatDate2Str(beginAt, DateUtil.NORM_DATETIME_PATTERN));
                    mapList.put("bannerStatus", ActivityEnum.AuctionStatus.DEPOSIT_ONLINE.getKey());
                    mapList.put("bannerStatusStr", ActivityEnum.AuctionStatus.DEPOSIT_ONLINE.getValue());
                } else {
                    mapList.put("bannerStatus", ActivityEnum.AuctionStatus.NOT_DEPOSIT.getKey());
                    mapList.put("bannerStatusStr", ActivityEnum.AuctionStatus.NOT_DEPOSIT.getValue());
                }
            } else {
                mapList.put("bannerStatus", ActivityEnum.AuctionStatus.NOT_DEPOSIT.getKey());
                mapList.put("bannerStatusStr", ActivityEnum.AuctionStatus.NOT_DEPOSIT.getValue());
            }
        } else if (activityStatus.equals(ActivityEnum.Status.SUCCESS.getKey())) {
            if (deposit != null && buyerId != null && buyerId.equals(partyId)) {
                if (!deposit.getStatus().equals(DepositEnum.Status.INIT.getKey())) {
                    log.info("保证金订单状态3为：{}，活动状态为：{},时间状态为：{}-{}", deposit.getStatus(), activityStatus, DateUtil.formatDate2Str(newTime, DateUtil.NORM_DATETIME_PATTERN), DateUtil.formatDate2Str(beginAt, DateUtil.NORM_DATETIME_PATTERN));
                    mapList.put("bannerStatus", ActivityEnum.AuctionStatus.SUCCESS.getKey());
                    mapList.put("bannerStatusStr", ActivityEnum.AuctionStatus.SUCCESS.getValue());
                } else {
                    mapList.put("bannerStatus", ActivityEnum.AuctionStatus.FAILED.getKey());
                    mapList.put("bannerStatusStr", ActivityEnum.AuctionStatus.FAILED.getValue());
                }
            } else {
                mapList.put("bannerStatus", ActivityEnum.AuctionStatus.FAILED.getKey());
                mapList.put("bannerStatusStr", ActivityEnum.AuctionStatus.FAILED.getValue());
            }
        } else if (activityStatus.equals(ActivityEnum.Status.FAILED.getKey())) {
            mapList.put("bannerStatus", ActivityEnum.AuctionStatus.FAILED.getKey());
            mapList.put("bannerStatusStr", ActivityEnum.AuctionStatus.FAILED.getValue());
        } else {
            mapList.put("bannerStatus", ActivityEnum.AuctionStatus.NOT_DEPOSIT.getKey());
            mapList.put("bannerStatusStr", ActivityEnum.AuctionStatus.NOT_DEPOSIT.getValue());
        }
        if (mapList.get("bannerStatus") == null || StringUtils.isEmpty((String) mapList.get("bannerStatusStr"))) {
            log.info("当前查询出来的数据为：{}", JSON.toJSONString(mapList));
         }
    }

    private void setLeaseDataResult(Map<String, Object> mapList) {
        String subStatus = null;
        String subStatusDesc;
        String startPriceDay;

        Date applyEndTime = (Date) mapList.get("applyEndTime");
        Date beginAt = (Date) mapList.get("beginAt");
        Date previewAt = (Date) mapList.get("previewAt");

        Date nowDate = new Date();
        if(DateUtil.getMarginMilliseconds(applyEndTime, nowDate) < 0 &&
                DateUtil.getMarginMilliseconds(beginAt, nowDate) > 0) {
                subStatus = AssetEnum.LeaseStatus.QUALIFICATION_REVIEW.getKey();

         }else if(DateUtil.getMarginMilliseconds(previewAt, nowDate) < 0 &&
                DateUtil.getMarginMilliseconds(applyEndTime, nowDate) > 0){
                subStatus = AssetEnum.LeaseStatus.REGISTRATION_PERIOD.getKey();
         }

        subStatusDesc = AssetEnum.LeaseStatus.getParentValueByKey(subStatus);

        BigDecimal startingPriceNum = (BigDecimal) mapList.get("startingPrice");
        BigDecimal currentPriceNum = mapList.get("currentPrice") == null ? null : (BigDecimal) mapList.get("currentPrice");
        if(currentPriceNum != null && !startingPriceNum.equals(currentPriceNum)) {
            startingPriceNum = currentPriceNum;
        }

        Integer leaseArea = (Integer) mapList.get("leaseArea");
        BigDecimal leaseAreaNum = new BigDecimal(leaseArea);
        BigDecimal yearNum = new BigDecimal("365");
        // 乘法
        BigDecimal multiply = leaseAreaNum.multiply(yearNum);
        // 除法
        BigDecimal divide = startingPriceNum.divide(multiply, 2, RoundingMode.HALF_UP);

        startPriceDay = divide.toString();

        mapList.put("startPriceDay", startPriceDay);
        if(StringUtils.isNotEmpty(subStatus)){
            mapList.put("subStatus", subStatus);
            mapList.put("subStatusDesc", subStatusDesc);
        }

    }

    /**
     * 功能描述:  规则为 根据浏览量倒叙查询
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/26 13:07
     */
    @Override
    public Object getRankActivity(ActivityReq.ActivityId req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());

        Map<String, Object> params = new HashMap<>();
        params.put("agencyCode", req.getAgencyCode());
        params.put("joint", req.getJoint());
        params.put("jointAgencyId", req.getJointAgencyId());
        params.put("type", req.getType());

        List<AuctionActivityVo> auctionActivities = auctionActivityDao.getRankActivity(params);
        List<Map>               mapList           = new TreeList<>();
        for (AuctionActivityVo auctionActivity : auctionActivities) {
            Asset asset = assetService.getAsset(auctionActivity.getAssetId());
            if (asset == null) {
                continue;
            }
            log.info("标的信息为：{}", JSON.toJSONString(asset));
            Map<String, Object> map = new HashMap<>();
            map.put("activityId", auctionActivity.getId());
            JSONObject extra = asset.getExtra();
            log.info("图片信息为：{}", JSON.toJSONString(extra));
            map.put("imageUrl", extra.getJSONArray("images").get(0));
            map.put("startingPrice", auctionActivity.getStartingPrice());
            map.put("mode", auctionActivity.getMode());
            map.put("name", auctionActivity.getAssetName());
            String cityId = asset.getCityId();
            if (StringUtils.isNotEmpty(cityId) && !"null".equals(cityId)) {
                String[] split = cityId.split(",");
                map.put("city", cityService.getByCityId(Integer.parseInt(split[0])).getName());
            }


            map.put("modeStr", ActivityEnum.ActivityMode.getKeyByValue(auctionActivity.getMode()));
            mapList.add(map);
        }
        return new PageInfo<>(mapList);
    }

    @Override
    public Asset getAssetByActivityId(Integer activityId) {

        AuctionActivity activity = auctionActivityDao.getById(activityId);
        return assetService.getAsset(activity.getAssetId());
    }

    @Override
    public Object staffAndAuctioneer(ActivityReq.ActivityId req) {
        AuctionActivity auctionActivity = auctionActivityDao.selectById(req.getActivityId());
        if (auctionActivity == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        Map<String, Object> maps = new HashMap<>();
        if (StringUtils.isNotBlank(req.getAgencyCode())) {
            TAgency subWebsite = agencyDao.getByCode(req.getAgencyCode());
            if (subWebsite == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            if (auctionActivity.getAgencyId().equals(subWebsite.getId())) { // 子站点和送拍机构同一个
                Map<String, Object> map = new HashMap<>();
                map.put("auctioneerName", auctionActivity.getAuctioneerName());
                map.put("auctioneerPhone", auctionActivity.getAuctioneerPhone());
                Staff staff = new Staff();
                staff.setName(auctionActivity.getAuctioneerName());
                staff.setMobile(auctionActivity.getAuctioneerPhone());
                maps.put("auctioneerMsg", map);
                maps.put("staffMsg", staff);
            } else {
                String trustee = "";
                if (StringUtils.isNotBlank(subWebsite.getTrustee())) {
                    trustee = subWebsite.getTrustee().substring(0,1) + "先生";
                } else {
                    trustee = subWebsite.getName();
                }
                Map<String, Object> map = new HashMap<>();
                map.put("auctioneerName", trustee);
                map.put("auctioneerPhone", subWebsite.getTrusteePhone());
                Staff staff = new Staff();
                staff.setName(trustee);
                staff.setMobile(subWebsite.getTrusteePhone());
                maps.put("auctioneerMsg", map);
                maps.put("staffMsg", staff);
            }
        } else {
            Integer staffId = auctionActivity.getStaffId();
            Staff staff = null;
            if (staffId != null) {
                staff = staffService.getById(staffId);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("auctioneerName", auctionActivity.getAuctioneerName());
            map.put("auctioneerPhone", auctionActivity.getAuctioneerPhone());
            map.put("auctioneerQq", auctionActivity.getAuctioneerQq());

            maps.put("auctioneerMsg", map);
            maps.put("staffMsg", staff);
        }

        return maps;
    }

    @Override
    public ActivityResp bindStaff(ActivityReq.ActivityId req) {
        ActivityResp    resp            = new ActivityResp();
        AuctionActivity auctionActivity = auctionActivityDao.selectById(req.getActivityId());
        if (auctionActivity == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        Staff staff = staffDao.selectById(req.getStaffId());
        if (staff == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (auctionActivity.getStaffId() == null || !auctionActivity.getStaffId().equals(req.getStaffId())) {
            int result = auctionActivityDao.bindStaff(req.getActivityId(), req.getStaffId());
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        return resp;
    }

    @Override
    public ActivityResp unbindStaff(ActivityReq.ActivityId req) {
        ActivityResp    resp            = new ActivityResp();
        AuctionActivity auctionActivity = auctionActivityDao.selectById(req.getActivityId());
        if (auctionActivity == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (auctionActivity.getStaffId() != null) {
            int result = auctionActivityDao.unbindStaff(req.getActivityId());
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        return resp;
    }

    @Override
    public List<Integer> getNeedRepairAuctionActivity() {
        return auctionActivityDao.getNeedRepairList();
    }

    @Transactional
    @Override
    public boolean repairAuctionActivity(Integer activityId) {
        AuctionActivity activity = auctionActivityDao.selectById(activityId);
        int             result;
        log.error("活动，activityId={}，status={}", activity.getId(), activity.getStatus().getKey());
        // 平台审核通过，未签署协议，活动时间已经结束
        if (ActivityEnum.Status.PLATFORM_APPROVED.equals(activity.getStatus()) && activity.getEndAt().before(new Date())) {
            repairPlatformApprovedActivity(activity);
        } else if (ActivityEnum.Status.ONLINE.equals(activity.getStatus()) && activity.getEndAt().before(new Date())) {
            // 活动已上线，活动时间已经结束，异常
            int num = depositDao.getDepositCount(activity.getId());
            if (num > 0) {
                return false;
            }
            Asset asset = assetDao.selectById(activity.getAssetId());
            if (asset == null) {
                return false;
            }
            if (!AssetEnum.Status.SELLING.equals(asset.getStatus())) {
                log.error("标的状态异常，assetId={}，status={}", asset.getId(), asset.getStatus().getKey());
                return false;
            }
            activity.setStatus(ActivityEnum.Status.FAILED);
            activity.setUpdatedAt(new Date());
            result = auctionActivityDao.updateById(activity);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            asset.setStatus(AssetEnum.Status.FAILED);
            asset.setUpdatedAt(new Date());
            result = assetDao.updateById(asset);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        } else {
            log.error("活动状态状态异常，activityId={}，status={}", activity.getId(), activity.getStatus().getKey());
            return false;
        }
        return true;
    }

    private boolean repairPlatformApprovedActivity(AuctionActivity activity) {
        Asset asset = assetDao.selectById(activity.getAssetId());
        if (!AssetEnum.Status.APPROVED.equals(asset.getStatus())) {
            log.error("标的状态异常，assetId={}，status={}", asset.getId(), asset.getStatus().getKey());
            return false;
        }
        activity.setStatus(ActivityEnum.Status.CANCELLED);
        activity.setUpdatedAt(new Date());
        int result = auctionActivityDao.updateById(activity);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        asset.setStatus(AssetEnum.Status.WITHDRAW);
        asset.setUpdatedAt(new Date());
        result = assetDao.updateById(asset);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return true;
    }

    @Override
    public ActivityResp modifyVisibility(ActivityReq.ActivityId req) {
        ActivityResp    resp            = new ActivityResp();
        AuctionActivity auctionActivity = auctionActivityDao.selectById(req.getActivityId());
        if (auctionActivity == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (auctionActivity.getVisibilityLevel().equals(req.getVisibilityLevel())) {
            return resp;
        }
        if (ActivityEnum.VisibilityLevel.PLATFORM_DEFAULT.getKey().equals(req.getVisibilityLevel()) || ActivityEnum.VisibilityLevel.PLATFORM_INVISIBLE.getKey().equals(req.getVisibilityLevel())) {
            auctionActivity.setVisibilityLevel(req.getVisibilityLevel());
            auctionActivity.setOperatorId(req.getOperatorId());
            auctionActivity.setOperatorAt(new Date());
            auctionActivity.setUpdatedAt(new Date());
            int result = auctionActivityDao.updateById(auctionActivity);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        } else {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        return resp;
    }

    @Override
    public Map<String, Object> unionData(ActivityReq.ActivityId req) {

        //获取标的信息
        AuctionActivity auctionActivity = auctionActivityDao.selectById(req.getActivityId());
        if (auctionActivity == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }

        Asset asset = assetDao.selectById(auctionActivity.getAssetId());

        Map<String, Object> joinMap = new HashMap<>();

        joinMap.put("activityId", req.getActivityId());

        PageHelper.startPage(req.getPage(), req.getPerPage());


        //如果是机构上拍 没有连拍 全网连拍就只有自己
        List<AgencyUnionDataVo> list;

        if (asset.getJointStatus() == 1) {
            list = auctionActivityDao.getSelfJointList(joinMap);

        } else {
            list = auctionActivityDao.getAgencyJointList(joinMap);
        }

        Map<String, Object> resp = new HashMap<>();


        PageInfo pageInfo = new PageInfo<>(list);
        resp.put("hasNextPage", pageInfo.isHasNextPage());
        resp.put("total", pageInfo.getTotal());
        resp.put("list", pageInfo.getList());
        if (req.getPage() == 1) {
            resp = getAddResp(resp, req);
        }
        return resp;


    }


    private Map<String, Object> unionDataback(ActivityReq.ActivityId req) {

        Map<String, Object> resp            = new HashMap<>();
        AuctionActivity     auctionActivity = auctionActivityDao.selectById(req.getActivityId());
        if (auctionActivity == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        PageInfo pageInfo;
        if (StringUtils.isNotEmpty(req.getAgencyCode())) {
            Map<String, Object> params = new HashMap<>();
            params.put("activityId", req.getActivityId());
            TAgency agency = agencyDao.getByCode(req.getAgencyCode());
            params.put("agencyId", agency.getId());
            params.put("cityId", agency.getCityId());
            pageInfo = auctionActivityDao.getAgencyUnionDataList(req.getPage(), req.getPerPage(), params, "");
        } else {
            if (req.getPage() == 1) {
                resp = getAddResp(resp, req);
            }
            Map<String, Object> params = new HashMap<>();
            params.put("activityId", req.getActivityId());
            pageInfo = auctionActivityDao.getUnionDataList(req.getPage(), req.getPerPage(), params, "");
        }
        resp.put("hasNextPage", pageInfo.isHasNextPage());
        resp.put("total", pageInfo.getTotal());
        resp.put("list", pageInfo.getList());
        return resp;
    }


    private Map<String, Object> getAddResp(Map<String, Object> resp, ActivityReq.ActivityId req) {
        resp.put("viewCount", auctionActivityViewCountService.viewCount(req.getActivityId()));
        resp.put("participantNumber", depositDao.getDepositCount(req.getActivityId()));
        resp.put("favorCount", serviceActivityUtils.getFocusNum(req.getActivityId()));
        resp.put("notifiersCount", serviceActivityUtils.getReminderNum(req.getActivityId()));
        resp.put("sharePartiesCount", auctionActivityShareStatsDao.activityShareCount(req.getActivityId()));

        return resp;

    }


    @Override
    public BigDecimal checkReservePrice(Integer activityId) {
        AuctionActivity auctionActivity = auctionActivityDao.selectById(activityId);
        if (auctionActivity == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        return auctionActivity.getReservePrice();
    }

    @Transactional
    @Override
    public boolean modifyReservePrice(ActivityReq.ModifyReservePriceReq req) {
        AuctionActivity auctionActivity = auctionActivityDao.selectById(req.getActivityId());
        if (auctionActivity == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        DelegationAgreement delegationAgreement = delegationAgreementService.getByActivityId(req.getActivityId());
        if (delegationAgreement == null) {
            throw new BusinessException("活动还没有签订委托协议");
        }
        if (delegationAgreement.getAllSigned() == null || !delegationAgreement.getAllSigned()) {
            throw new BusinessException("委托协议没有签订");
        }
        if (ActivityEnum.Status.ONLINE.equals(auctionActivity.getStatus()) && auctionActivity.getBeginAt().before(new Date())) {
            throw new BusinessException("活动已经开始，无法生成补充协议");
        }
        AdditionalAgreement additionalAgreement = additionalAgreementDao.getByActivityId(req.getActivityId());
        if (additionalAgreement != null) {
            throw new BusinessException("已经修改过保留价，无法进行二次修改");
        }
        additionalAgreementService.generateContract(req.getActivityId(), req.getReservePrice());
        auctionActivity.setReservePrice(req.getReservePrice());
        auctionActivity.setStatus(ActivityEnum.Status.PLATFORM_APPROVED);
        auctionActivity.setUpdatedAt(new Date());
        auctionActivity.setOperatorId(req.getOperatorId());
        auctionActivity.setOperatorAt(new Date());
        int result = auctionActivityDao.updateById(auctionActivity);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        Asset asset = assetDao.selectById(auctionActivity.getAssetId());
        if (asset == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        asset.setReservePrice(req.getReservePrice());
        asset.setStatus(AssetEnum.Status.APPROVED);
        asset.setUpdatedAt(new Date());
        result = assetDao.updateById(asset);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        AssetData assetData = assetDataDao.findAssetData(asset.getId());
        if (assetData == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改标的失败");
        }
        JSONObject content = assetData.getContent();

        JSONArray templateData = content.getJSONArray("templateDate");
        JSONArray newData      = new JSONArray();
        for (int i = 0; i < templateData.size(); i++) {
            JSONObject jsonObject = templateData.getJSONObject(i);
            String     keyStr     = jsonObject.getString("key");
            String     key        = formatKey(keyStr);
            if ("reservePrice".equals(key)) {
                JSONArray newVal = new JSONArray();
                newVal.add(asset.getReservePrice());
                jsonObject.put("val", newVal);
            }
            newData.add(jsonObject);
        }
        content.put("templateDate", newData);
        assetData.setContent(content);

        int i = assetDataDao.updateById(assetData);
        if (i <= 0) {
            log.info("修改保留价失败：标的Data数据为：{},标的数据为：{}", JSON.toJSONString(assetData), JSON.toJSONString(asset));
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改失败");
        }
        // 清除redis key
        redisCachemanager.del(SystemConstant.AUCTION_EXPIRE_PRE_KEY + auctionActivity.getId());
        redisCachemanager.del(SystemConstant.AUCTION_DUTCH_PRICE_PRE_KEY + auctionActivity.getId());
        return true;
    }

    @Override
    public int addParticipantNumber(Integer activityId) {
        return auctionActivityDao.addParticipantNumber(activityId);
    }

    @Override
    public PageInfoResp exportActivityList(ActivityReq.ActivityId req) {
        PageInfoResp              resp     = new PageInfoResp();
        PageInfo                  pageInfo = auctionActivityDao.exportActivityList(req.getPage(), req.getPerPage());
        List<Map<String, Object>> list     = pageInfo.getList();
        for (Map<String, Object> item : list) {
            try {
                item.put("提交审核时间", df.format(item.get("提交审核时间")));
                item.put("上拍时间", df.format(item.get("上拍时间")));
                item.put("拍卖开始时间", df.format(item.get("拍卖开始时间")));
                item.put("拍卖结束时间", df.format(item.get("拍卖结束时间")));
                String modeDesc = ActivityEnum.ActivityMode.getKeyByValue((String) item.get("拍卖方式"));
                item.put("拍卖方式", modeDesc);
                String statusDesc = ActivityEnum.Status.getKeyByValue((String) item.get("活动状态"));
                item.put("活动状态", statusDesc);
                String contentStr = (String) item.get("content");
                item.put("债权本金", getDebtPrincipal(contentStr));
                item.put("债权利息", getDebtInterest(contentStr));

                String cityName = (String) item.get("所在城市");
                String provinceName = "";
                if (StringUtils.isNotBlank(cityName)) {
                    City city = cityDao.findByName(cityName);
                    if (city != null) {
                        provinceName = provinceDao.getName(city.getProvinceId());
                    }
                }
                item.put("所在省份", provinceName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        resp.setList(list);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Transactional
    @Override
    public ActivityResp withdrawActivity(ActivityReq.ActivityId req) {
        ActivityResp    resp     = new ActivityResp();
        AuctionActivity activity = auctionActivityDao.selectById(req.getActivityId());
        if (activity == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!ActivityEnum.Status.PLATFORM_REVIEWING.equals(activity.getStatus())
                && !ActivityEnum.Status.PLATFORM_REJECTED.equals(activity.getStatus())
                && !ActivityEnum.Status.PLATFORM_APPROVED.equals(activity.getStatus())) {
            throw new BusinessException("只有等待平台审核或者平台审核拒绝或者待签协议的的活动是可以撤回的");
        }
        Asset asset = assetDao.selectById(activity.getAssetId());
        if (!req.getPartyId().equals(asset.getPartyId())) {
            throw new BusinessException("只能撤回自己的活动");
        }
        // 修改活动状态
        int result = auctionActivityDao.updateStatus(req.getActivityId(), ActivityEnum.Status.CANCELLED, req.getOperatorId());
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        // 修改标的状态
        result = assetDao.updateStatus(asset.getId(), AssetEnum.Status.WITHDRAW);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return resp;
    }

    private String getDebtPrincipal(String contentStr) {
        if (StringUtils.isEmpty(contentStr)) {
            return "";
        }
        try {
            JSONObject content      = JSON.parseObject(contentStr);
            JSONArray  templateData = content.getJSONArray("templateDate");
            if (templateData != null) {
                for (Object o : templateData) {
                    JSONObject json = (JSONObject) o;
                    String     key  = json.getString("key");
                    JSONArray  val;
                    // 债权本金
                    if ("debtPrincipal000".equals(key)) {
                        val = json.getJSONArray("val");
                        if (val != null && val.size() > 0) {
                            Object f = val.get(0);
                            return (String) f;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getDebtInterest(String contentStr) {
        if (StringUtils.isEmpty(contentStr)) {
            return "";
        }
        try {
            JSONObject content      = JSON.parseObject(contentStr);
            JSONArray  templateData = content.getJSONArray("templateDate");
            if (templateData != null) {
                for (Object o : templateData) {
                    JSONObject json = (JSONObject) o;
                    String     key  = json.getString("key");
                    JSONArray  val;
                    // 债权利息
                    if ("debtInterest000".equals(key)) {
                        val = json.getJSONArray("val");
                        if (val != null && val.size() > 0) {
                            Object f = val.get(0);
                            return (String) f;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static final Pattern KEY_PATTERN = Pattern.compile("[\\d]");

    private String formatKey(String key) {
        Matcher matcher = KEY_PATTERN.matcher(key);
        return (matcher.replaceAll("").trim());
    }

    private AuctionActivityVo processAuctionActivity(AuctionActivity auctionActivity) {
        AuctionActivityVo auctionActivityVo = RespConvertUtil.convertToAuctionActivityVo(auctionActivity);

        AssetVo asset = assetService.getAssetById(auctionActivity.getAssetId());
        auctionActivityVo.setAsset(asset);
        auctionActivityVo.setCityName(asset.getCityName());
        if (StringUtils.isNotEmpty(auctionActivityVo.getCityName())) {
            auctionActivityVo.setCityName(asset.getCityName());
        }
        //party 相关信息
        PartyAccount seller = asset.getSeller();
        if (seller != null) {
            auctionActivityVo.setSeller(seller);
            auctionActivityVo.setSellerName(seller.getName());
        }
        if (auctionActivity.getStaffId() != null) {
            Staff        staff   = staffService.getById(auctionActivity.getStaffId());
            PartyAccount staffVo = new PartyAccount();
            staffVo.setId(staff.getId());
            staffVo.setName(staff.getName());
            staffVo.setMobile(staff.getMobile());
            staffVo.setQq(staff.getQq());
            auctionActivityVo.setStaff(staffVo);
        }
        PartyAccount auctioneer = new PartyAccount();
        auctioneer.setName(auctionActivity.getAuctioneerName());
        auctioneer.setMobile(auctionActivity.getAuctioneerPhone());
        auctioneer.setQq(auctionActivity.getAuctioneerQq());
        auctionActivityVo.setAuctioneer(auctioneer);
        if (StringUtils.isEmpty(auctionActivityVo.getCategoryName())) {
            auctionActivityVo.setCategoryName(auctionActivityDao.getCategoryName(auctionActivity.getId()));
        }
        DelegationAgreement delegationAgreement = delegationAgreementService.getByActivityId(auctionActivity.getId());
        if (delegationAgreement != null) {
            auctionActivityVo.setSentAgreement(RespConvertUtil.convertToFileInfo(delegationAgreement));
        }
        auctionActivityVo.setReportProtocolStatus(signStatusConvert(assetAuthorizationService.getAuthSource(auctionActivity.getId()), delegationAgreement, auctionActivityVo.getStatus()));
        AdditionalAgreement additionalAgreement = additionalAgreementDao.getByActivityId(auctionActivity.getId());
        if (additionalAgreement != null) {
            auctionActivityVo.setAdditionalAgreement(RespConvertUtil.convertToFileInfo(additionalAgreement));
        }
        if (ActivityEnum.Status.PLATFORM_APPROVED.equals(auctionActivity.getStatus())) { // 待签协议
            if (delegationAgreement != null && !delegationAgreement.getSigned()) {
                auctionActivityVo.setNeedToSign("1");
            }
            if (additionalAgreement != null && !additionalAgreement.getSigned()) {
                auctionActivityVo.setNeedToSign("2");
            }
        } else {
            auctionActivityVo.setNeedToSign("0");
        }
        AuctionOrder auctionOrder = auctionOrderService.getFirstByActivityId(auctionActivity.getId());
        if (auctionOrder != null) {
            DealAgreement dealAgreement = dealAgreementService.getByOrderId(auctionOrder.getId(),AuctionOfflineEnum.ContractType.DEAL.getKey());
            if (dealAgreement != null) {
                auctionActivityVo.setDealAgreement(RespConvertUtil.convertToFileInfo(dealAgreement));
            }
            PartyAccount buyer = accountService.getPartyAccountById(auctionOrder.getBuyerId());
            buyer.setContact(buyer.getName());
            auctionActivityVo.setBuyer(buyer);
            auctionActivityVo.setOrderId(auctionOrder.getId() + "");
        }
        //agency
        TAgency agency = agencyDao.selectById(auctionActivity.getAgencyId());
        if (agency != null) {
            auctionActivityVo.setAgency(RespConvertUtil.convertToAgencyVo(agency));
            auctionActivityVo.setAgencyName(agency.getName());
        }
        if (auctionActivity.getOperatorId() != null) {
            auctionActivityVo.setOperator(staffDao.getName(auctionActivity.getOperatorId()));
        }
        // 是否是租赁权是租赁权就修改状态
        if(auctionActivityVo.getCategoryId() != null && "-1".equals(auctionActivityVo.getCategoryId().toString())) {
            setLeaseActivityStatus(auctionActivityVo,asset);
        }

        return auctionActivityVo;
    }

    private void setLeaseActivityStatus(AuctionActivityVo auctionActivityVo, AssetVo asset) {

        AssetLeaseDataCondition assetLeaseDataCondition = new AssetLeaseDataCondition();
        assetLeaseDataCondition.setAssetId(auctionActivityVo.getAssetId());
        assetLeaseDataCondition.setDeleteFlag(false);
        AssetLeaseData assetLeaseData = assetLeaseDataDao.selectOneResult(assetLeaseDataCondition);
        Date nowDate = new Date();

        asset.getStatus();
        // 等待发布状态
        if(AssetEnum.Status.PENDING.getKey().equals(asset.getStatus())&&AssetEnum.LeaseStatus.WAITING_RELEASE.getKey().equals(asset.getSubStatus())){
            auctionActivityVo.setStatus(AssetEnum.LeaseStatus.WAITING_RELEASE.getKey());
            auctionActivityVo.setStatusDesc(AssetEnum.LeaseStatus.WAITING_RELEASE.getParentValue());
        } else if(ActivityEnum.Status.ONLINE.getKey().equals(auctionActivityVo.getStatus())&&DateUtil.getMarginMilliseconds(assetLeaseData.getPreviewAt(), nowDate) < 0 &&
                DateUtil.getMarginMilliseconds(assetLeaseData.getApplyEndTime(), nowDate) > 0) {
            auctionActivityVo.setStatus(AssetEnum.LeaseStatus.REGISTRATION_PERIOD.getKey());
            auctionActivityVo.setStatusDesc(AssetEnum.LeaseStatus.REGISTRATION_PERIOD.getValue());

        }else if(ActivityEnum.Status.ONLINE.getKey().equals(auctionActivityVo.getStatus())&&DateUtil.getMarginMilliseconds(assetLeaseData.getApplyEndTime(), nowDate) < 0 &&
                DateUtil.getMarginMilliseconds(assetLeaseData.getApprovalEndTime(), nowDate) > 0) {
            auctionActivityVo.setStatus(AssetEnum.LeaseStatus.QUALIFICATION_REVIEW.getKey());
            auctionActivityVo.setStatusDesc(AssetEnum.LeaseStatus.QUALIFICATION_REVIEW.getValue());
        }
    }

    private boolean updateActivityStatus(Integer activityId, ActivityEnum.Status status) {
        AuctionActivity auctionActivity = new AuctionActivity();
        auctionActivity.setId(activityId);
        auctionActivity.setStatus(status);
        auctionActivity.setUpdatedAt(new Date());
        return auctionActivityDao.updateById(auctionActivity) > 0 ? true : false;
    }

    @Override
    public boolean updateActivityById(AuctionActivity activity) {
        return auctionActivityDao.updateById(activity) == 1;
    }

    private String signStatusConvert(String reportSource, DelegationAgreement byActivityId, String activityStatus) {


        /**
         *  代签协议之前：不可点击
         *  代签协议状态：请先签署委托协议
         *  签署委托协议 and 一天之内： 允许签署尽调协议
         *  一天之后: 不可点击
         */

        if (reportSource != null) {
            // 已签署尽调协议
            return SurveyReportEnum.ProtocolStatusEnum.SIGNED.getKey();
        }

        // 未签署委托协议
        if (byActivityId == null || !byActivityId.getAllSigned()) {

            if (activityStatus.equals(ActivityEnum.Status.PLATFORM_APPROVED.getKey())) {

                return SurveyReportEnum.ProtocolStatusEnum.CAN_NOT1.getKey();
            } else {

                return SurveyReportEnum.ProtocolStatusEnum.CAN_NOT3.getKey();
            }
        }

        // 已签署委托协议
        if (!DateUtil.isToday(byActivityId.getAllSignedAt())) {

            return SurveyReportEnum.ProtocolStatusEnum.CAN_NOT2.getKey();
        } else {

            return SurveyReportEnum.ProtocolStatusEnum.NOT_SIGNED.getKey();
        }


//        if (byActivityId == null) {
//            return SurveyReportEnum.ProtocolStatusEnum.CAN_NOT1.getKey();
//        }
//        if (reportSource != null) {
//            // 已签署尽调协议
//            return SurveyReportEnum.ProtocolStatusEnum.SIGNED.getKey();
//        } else {
//            // 如果签署了委托协议
//            if (byActivityId.getAllSigned()) {
//                // 已过了允许签署尽调协议日期
//                if (!DateUtil.isToday(byActivityId.getAllSignedAt())) {
//                    return SurveyReportEnum.ProtocolStatusEnum.CAN_NOT2.getKey();
//                }
//            } else {
//                // 未签署委托协议
//                return SurveyReportEnum.ProtocolStatusEnum.CAN_NOT1.getKey();
//            }
//
//            // 条件满足，允许签协议
//            return SurveyReportEnum.ProtocolStatusEnum.NOT_SIGNED.getKey();
//        }
    }


    @Override
    public PageInfo getJointList(ActivityReq.JointListReq req) {


        ActivityJointReq jointReq = new ActivityJointReq();

        BeanUtils.copyProperties(req, jointReq);

        TAgency agency = agencyService.findByAgencyId(req.getAgencyId());

        jointReq.setIsJoint(agency.getIsJoint());


        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<AuctionJointVo> jointVoList = auctionActivityDao.getAuctionJointList(jointReq);

        List<ProvinceCityVo> cityList = getProvinceCity();

        jointVoList = getJointVoList(jointVoList, cityList, agency, jointReq);

        return new PageInfo<>(jointVoList);
    }

    private List<ProvinceCityVo> getProvinceCity() {

        return cityService.getAllProvinceCityList();

    }

    private boolean agencyJoint(Integer agencyId) {
        //获取机构信息看看是否设置了 手动联拍
        TAgency agency = agencyService.findByAgencyId(agencyId);
        if (agency != null && 1 == agency.getIsJoint()) {

            return true;
        }
        return false;
    }

    private String getCityName(String cityId, List<ProvinceCityVo> cityList) {

        String strCity = "";

        List<String> ids;

        try {

            ids = Arrays.asList(cityId.split(","));

        } catch (Exception e) {
            log.error("转换城市id异常，异常信息为：{}", e);

            return null;

        }
        if (ids == null || ids.size() < 1) {
            return null;
        }

        for (String id : ids) {
            for (ProvinceCityVo city : cityList) {

                if (id.equals(city.getCityId().toString())) {
                    strCity = strCity + city.getProvinceName() + " " + city.getCityName() + "、";
                }
            }
        }

        if (strCity.length() < 1) {
            return strCity;
        }

        return strCity.substring(0, strCity.length() - 1);


    }


    @Override
    public void saveOrCancelJoint(ActivityReq.JointReq req) {
        TAgency agency = agencyService.findByAgencyId(req.getAgencyId());

        //根据id 获取信息
        req.setIsJoint(agency.getIsJoint());
        List<JointActivityMap> mapList = getJointMapInfoList(req);


        //保存连拍信息
        if (("1".equals(req.getType()) && 1 == agency.getIsJoint()) || ("2".equals(req.getType()) && 0 == agency.getIsJoint())) {

            saveJointActivityMap(req, mapList);

            //更新连拍信息
        } else {
            if ("1".equals(req.getType()) && 0 == agency.getIsJoint()) {
                req.setIsDel(1);
            }
            updateJointActivityMap(req, mapList);
        }

    }

    private void updateJointActivityMap(ActivityReq.JointReq req, List<JointActivityMap> mapList) {


        List<Integer> ids = new ArrayList<>();

        for (JointActivityMap map : mapList) {
            ids.add(map.getId());

        }

        if (ids.size() > 0) {
            Map map = new HashMap();
            map.put("isDel", req.getIsDel());
            map.put("ids", ids);

            auctionActivityDao.batchUpdateJoint(map);
        }

    }

    private void saveJointActivityMap(ActivityReq.JointReq req, List<JointActivityMap> mapList) {

        List<JointActivityMap> saveMapList = getSaveMapList(req, mapList);

        if (saveMapList.size() > 0) {
            auctionActivityDao.batchSaveJoint(saveMapList);

        }

        List<Integer> ids = new ArrayList<>();
        if (mapList.size() > 0) {
            for (JointActivityMap map : mapList) {
                ids.add(map.getId());
            }
        }
        if (ids.size() > 0) {
            Map map = new HashMap();
            map.put("ids", ids);
            map.put("isDel", 0);
            auctionActivityDao.batchUpdateJoint(map);
        }


    }

    private List<JointActivityMap> getSaveMapList(ActivityReq.JointReq req, List<JointActivityMap> mapList) {

        TAgency agency = agencyService.findByAgencyId(req.getAgencyId());


        List<JointActivityMap> saveMapList = new ArrayList<>();

        for (String id : req.getIds()) {
            Boolean hasDate = false;
            for (JointActivityMap jointActivityMap : mapList) {
                if (id.equals(String.valueOf(jointActivityMap.getActivityId()))) {
                    hasDate = true;
                }
            }
            if (!hasDate) {
                JointActivityMap map = new JointActivityMap();

                map.setActivityId(Integer.valueOf(id));
                map.setAgencyId(req.getAgencyId());
                map.setIsDel(0);
                map.setCreateTime(DateUtil.getSysTime());
                map.setType(agency.getIsJoint());
                saveMapList.add(map);
            }

        }


        return saveMapList;
    }

    private List<JointActivityMap> getJointMapInfoList(ActivityReq.JointReq req) {

        List<Integer> ids = new ArrayList<>();

        for (String id : req.getIds()) {
            ids.add(Integer.valueOf(id));
        }
        Map map = new HashMap();
        map.put("agencyId", req.getAgencyId());
        map.put("ids", ids);
        map.put("type", req.getIsJoint());
        return auctionActivityDao.getJointActivityMapList(map);
    }


    @Override
    public List<Map<String, Object>> getDownloadJointList(ActivityReq.JointListReq req) {


        ActivityJointReq jointReq = new ActivityJointReq();

        BeanUtils.copyProperties(req, jointReq);

        TAgency agency = agencyService.findByAgencyId(req.getAgencyId());

        jointReq.setIsJoint(agency.getIsJoint());


        List<AuctionJointVo> jointVoList = auctionActivityDao.getAuctionJointList(jointReq);

        List<ProvinceCityVo> cityList = getProvinceCity();


        jointVoList = getJointVoList(jointVoList, cityList, agency, jointReq);


        return JsonUtil.beanListToMapList(jointVoList);
    }

    @Override
    public PageInfo getAuctionActivityByAccountId(AcctReq.ViewRecordRequest viewRecordRequest) {

        return auctionActivityDao.getAuctionActivityByAccountId(viewRecordRequest);
    }

    @Override
    public Map<String, Object> getShareInfo(ActivityReq.ActivityId req) {
        Map<String, Object> data     = new HashMap<>();
        AuctionActivity     activity = auctionActivityDao.selectById(req.getActivityId());
        if (activity == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        Asset asset = assetDao.selectById(activity.getAssetId());
        if (asset == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (AssetEnum.ComeFrom.AGENCY.getKey().equals(asset.getComeFrom())) {
            data.put("partyCategoryName", "拍卖公司");
        } else {
            TParty party = partyDao.selectById(asset.getPartyId());
            if (party != null) {
                data.put("partyCategoryName", PartyEnum.Category.getFrontNameByKey(party.getCategory()));
            } else {
                data.put("partyCategoryName", "");
            }
        }
        data.put("category", assetDao.getCategoryName(asset.getId()));
        data.put("name", activity.getAssetName());
        JSONObject extra = asset.getExtra();
        if (extra != null) {
            JSONArray images = extra.getJSONArray("images");
            if (images != null && images.size() > 0) {
                data.put("imageUrl", images.getString(0));
            }
        }
        data.put("amountDesc", "起拍价");
        data.put("amount", NumberToCN.simpleFormatAmount(activity.getStartingPrice()));
        data.putAll(NumberToCN.simpleFormatAmountMap(activity.getStartingPrice()));
        if (req.isAppletFlag()) {
            data.put("qrCodeUrl", getAppletQrCode(req.getActivityId(), req.getShopId()));
        } else {
            //data.put("qrCodeUrl", getQrUrl(req.getUrl()));
            data.put("qrCodeUrl", getAppletQrCode(req.getActivityId(), SystemConstant.DEFAULT_APPLET_SHOP));
        }
        return data;
    }

    private String getQrUrl(String content) {
        File uploadFile = new File(QrUtil.createQrCode(content));
        if (!uploadFile.exists()) {
            return "";
        }
        String returnUrl = "";
        try {
            returnUrl = qiNiuUtil.uploadToPublic(uploadFile, RandomNumberGenerator.wordGenerator(30));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(returnUrl)) {
            return "";
        }
        return "https://" + gatewayProperties.getDomain() + "/" + returnUrl;
    }

    private String getAppletQrCode(Integer activityId, Integer shopId) {
        String cache = (String) redisCachemanager.hGet(RedisKeyConstant.APPLET_AUCTION_ACTIVITY_SHARE_IMAGE, getScene(activityId, shopId));
        if (StringUtils.isNotEmpty(cache)) {
            return cache;
        }
        WXACodeUnLimitReq wxaCodeUnLimitReq = new WXACodeUnLimitReq();
        wxaCodeUnLimitReq.setPage("pages/auction/auction");
        wxaCodeUnLimitReq.setScene(getScene(activityId, shopId));
        log.info("获取店铺小程序二维码，入参={}", JSON.toJSONString(wxaCodeUnLimitReq));
        WXACodeUnLimitResp wxaCodeUnLimitResp = wxFacade.getWXACodeUnLimit(wxaCodeUnLimitReq);
        if (wxaCodeUnLimitResp == null || !wxaCodeUnLimitResp.getCode().equals("000")) {
            log.error("获取店铺拍品详情小程序二维码失败，入参={}，出参={}", JSON.toJSONString(wxaCodeUnLimitReq), JSON.toJSONString(wxaCodeUnLimitResp));
            return "";
        }
        redisCachemanager.hSet(RedisKeyConstant.APPLET_AUCTION_ACTIVITY_SHARE_IMAGE, getScene(activityId, shopId), wxaCodeUnLimitResp.getImgUrl());
        return wxaCodeUnLimitResp.getImgUrl();
    }

    private String getScene(Integer activityId, Integer shopId) {
        if (shopId != null) {
            return activityId + "-" + shopId;
        }
        return activityId + "-";
    }

    private List<AuctionJointVo> getJointVoList(List<AuctionJointVo> jointVoList, List<ProvinceCityVo> cityList, TAgency agency, ActivityJointReq jointReq) {

        for (AuctionJointVo vo : jointVoList) {
            vo.setJointStatusDesc("未联拍");
            vo.setJointStatus("2");
            if ("1".equals(jointReq.getType())) {
                vo.setJointStatusDesc("已联拍");
                vo.setJointStatus("1");
            }

            if (AssetEnum.ComeFrom.AGENCY.getKey().equals(vo.getComeFrom()) && String.valueOf(agency.getId()).equals(vo.getPartyId())) {
                vo.setJointStatusDesc("自有拍品");
                vo.setJointStatus("3");

            }

            ShopEnum.NewOnlineStatus onlineStatus = RespConvertUtil.getOnlineStatus(vo.getStatus(), DateUtil.strToDateLong(vo.getBeginAt()));
            vo.setStatus("FAILURE");
            vo.setStatusDesc("未知状态");
            if (onlineStatus != null) {
                vo.setStatus(onlineStatus.getKey());
                vo.setStatusDesc(onlineStatus.getValue());
            }
            vo.setAuctionType(ActivityEnum.ActivityMode.getKeyByValue(vo.getAuctionType()));
            // 新增租赁权之后的判断
            String categoryType = null;
            if("-1".equals(vo.getAssetCategoryGroupId())) {
                categoryType = "租赁权拍卖";
            }else if("2".equals(vo.getAssetCategoryGroupId())) {
                categoryType = "债权拍卖";
            }else{
                categoryType = "物权拍卖";
            }

            vo.setCategoryType(categoryType);
            vo.setCityName(getCityName(vo.getCityId(), cityList));
        }


        return jointVoList;
    }

}