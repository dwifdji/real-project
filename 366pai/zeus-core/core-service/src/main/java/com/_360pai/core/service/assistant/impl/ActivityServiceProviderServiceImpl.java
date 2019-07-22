package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.common.constants.ActivityServiceProviderEnum;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.dao.account.*;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.dao.asset.AssetDao;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dao.assistant.ProvinceDao;
import com._360pai.core.dao.assistant.TActivityServiceProviderDao;
import com._360pai.core.dao.enrolling.EnrollingActivityDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.vo.ActivityServiceProvider;
import com._360pai.core.facade.account.vo.DisposeProvider;
import com._360pai.core.facade.account.vo.FundProvider;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.model.account.*;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.assistant.TActivityServiceProvider;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.service.account.DisposeService;
import com._360pai.core.service.assistant.ActivityServiceProviderService;
import com._360pai.core.service.assistant.CityService;
import com._360pai.core.service.disposal.DisposeShowService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author xdrodger
 * @Title: ActivityServiceProviderServiceImpl
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-05-21 14:23
 */
@Slf4j
@Service
public class ActivityServiceProviderServiceImpl implements ActivityServiceProviderService {
    @Autowired
    private GatewayProperties gatewayProperties;
    @Autowired
    private AuctionActivityDao auctionActivityDao;
    @Autowired
    private DisposeService disposeService;
    @Autowired
    private EnrollingActivityDao enrollingActivityDao;
    @Autowired
    private TActivityServiceProviderDao activityServiceProviderDao;
    @Autowired
    private TDisposeProviderDao disposeProviderDao;
    @Autowired
    private TFundProviderDao fundProviderDao;
    @Autowired
    private TPartyDao partyDao;
    @Autowired
    private TCompanyDao companyDao;
    @Autowired
    private TUserDao userDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private ProvinceDao provinceDao;
    @Autowired
    private AssetDao assetDao;
    @Autowired
    private DisposeShowService disposeShowService;
    @Autowired
    private TAccountDao accountDao;
    @Autowired
    private CityService cityService;

    @Override
    public ResponseModel getActivityServiceProvider(ActivityReq.ActivityId req) {
        if (!ActivityServiceProviderEnum.ActivityType.contains(req.getActivityType())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        Map<String, Object> data = new HashMap<>();
        List<FundProvider> fundProviders = new ArrayList<>();
        List<DisposeProvider> disposeProviders = new ArrayList<>();
        if (ActivityServiceProviderEnum.ActivityType.Auction.getKey().equals(req.getActivityType())) {
            AuctionActivity activity = auctionActivityDao.selectById(req.getActivityId());
            if (activity == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            Asset asset = assetDao.selectById(activity.getAssetId());
            String[] split = asset.getCityId().split(",");
            Integer cityId = null;
            if (split.length> 0) {
                if (StringUtils.isNotBlank(split[0])) {
                    if (split.length == 1) {
                        cityId = Integer.valueOf(split[0]);
                        TDisposeProvider disposeProvider = activityServiceProviderDao.getFirstLevelDisposeProvider(cityId);
                        if (disposeProvider != null) {
                            TActivityServiceProvider firstLevel = activityServiceProviderDao.getAuctionDisposeProvider(req.getActivityId(), disposeProvider.getId());
                            if (firstLevel == null) {
                                firstLevel = new TActivityServiceProvider();
                                firstLevel.setActivityId(req.getActivityId());
                                firstLevel.setActivityType(req.getActivityType());
                                firstLevel.setProviderId(disposeProvider.getId());
                                firstLevel.setProviderType(ActivityServiceProviderEnum.ProviderType.Dispose.getKey());
                                firstLevel.setSource(ActivityServiceProviderEnum.Source.Web.getKey());
                                firstLevel.setOrderNum(0);
                                firstLevel.setCityPartnerFlag(true);
                                activityServiceProviderDao.insert(firstLevel);
                            } else if (!firstLevel.getCityPartnerFlag()) {
                                firstLevel.setCityPartnerFlag(true);
                                activityServiceProviderDao.updateById(firstLevel);
                            }
                        }
                    }
                }
            }
            List<TDisposeProvider> tDisposeProviders = activityServiceProviderDao.getAuctionDisposeProvider(req.getActivityId());
            disposeProviders = disposeService.getDisposeProviders(tDisposeProviders);
        }
        if (ActivityServiceProviderEnum.ActivityType.Enrolling.getKey().equals(req.getActivityType())) {
            EnrollingActivity activity = enrollingActivityDao.selectById(req.getActivityId());
            if (activity == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            if (StringUtils.isNotBlank(activity.getFundProvider())) {
                FundProvider fundProvider = new FundProvider();
                fundProvider.setName(gatewayProperties.getEnrollingImportFundProvider());
                fundProvider.setPhone(gatewayProperties.getEnrollingImportFundPhone());
                fundProviders.add(fundProvider);
            } else {
                int count = activityServiceProviderDao.countFundBy(req.getActivityId(), req.getActivityType());
                if (count > 0) {
                    FundProvider fundProvider = new FundProvider();
                    fundProvider.setName(gatewayProperties.getEnrollingImportFundProvider());
                    fundProvider.setPhone(gatewayProperties.getEnrollingImportFundPhone());
                    fundProviders.add(fundProvider);
                }
            }
            boolean sameCityFlag = false;
            if (req.getPartyId() != null) {
                TDisposeProvider disposeProvider = disposeProviderDao.getByPartyId(req.getPartyId());
                if (disposeProvider != null) {
                    if (StringUtils.isNotBlank(activity.getCityId())) {
                        List<String> activityCityIds = new ArrayList<>(Arrays.asList(activity.getCityId().split(",")));
                        TParty party = partyDao.selectById(disposeProvider.getPartyId());
                        if (PartyEnum.Type.company.name().equals(party.getType())) {
                            TCompany company = companyDao.selectById(disposeProvider.getPartyId());
                            if (company != null) {
                                if (StringUtils.isNotBlank(company.getCityId())) {
                                    if (activityCityIds.contains(company.getCityId())) {
                                        sameCityFlag = true;
                                    }
                                }
                            }
                        } else {
                            TUser user = userDao.selectById(disposeProvider.getPartyId());
                            if (user != null) {
                                if (StringUtils.isNotBlank(user.getCityId())) {
                                    if (activityCityIds.contains(user.getCityId())) {
                                        sameCityFlag = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            data.put("sameCityFlag", sameCityFlag);
            disposeProviders = disposeService.getDisposeProviders(activityServiceProviderDao.getEnrollingDisposeProvider(req.getActivityId()));
        }
        boolean disposedFlag = false;
        boolean fundedFlag = false;
        if (req.getPartyId() != null) {
            TDisposeProvider disposeProvider = disposeProviderDao.getByPartyId(req.getPartyId());
            if (disposeProvider != null) {
                TActivityServiceProvider activityServiceProvider = activityServiceProviderDao.findDisposeBy(req.getActivityId(), req.getActivityType(), disposeProvider.getId());
                if (activityServiceProvider != null) {
                    disposedFlag = true;
                }
            }
            TFundProvider fundProvider = fundProviderDao.getByPartyId(req.getPartyId());
            if (fundProvider != null) {
                TActivityServiceProvider activityServiceProvider = activityServiceProviderDao.findFundBy(req.getActivityId(), req.getActivityType(), fundProvider.getId());
                if (activityServiceProvider != null) {
                    fundedFlag = true;
                }
            }
        }
        data.put("disposedFlag", disposedFlag);
        data.put("fundedFlag", fundedFlag);
        data.put("fundProviders", fundProviders);
        data.put("disposeProviders", disposeProviders);
        return ResponseModel.succ(data);
    }

    @Override
    public ResponseModel activityServiceProviderEnroll(ActivityReq.ActivityId req) {
        if (!ActivityServiceProviderEnum.ProviderType.contains(req.getProviderType())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!ActivityServiceProviderEnum.ActivityType.contains(req.getActivityType())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (ActivityServiceProviderEnum.ActivityType.Auction.getKey().equals(req.getActivityType())) {
            AuctionActivity activity = auctionActivityDao.selectById(req.getActivityId());
            if (activity == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
        }
        if (ActivityServiceProviderEnum.ActivityType.Enrolling.getKey().equals(req.getActivityType())) {
            EnrollingActivity activity = enrollingActivityDao.selectById(req.getActivityId());
            if (activity == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
        }
        Integer providerId = null;
        if (ActivityServiceProviderEnum.ProviderType.Fund.getKey().equals(req.getProviderType())) {
            TFundProvider fundProvider = fundProviderDao.getByPartyId(req.getPartyId());
            if (fundProvider == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            providerId = fundProvider.getId();
            TActivityServiceProvider activityServiceProvider = activityServiceProviderDao.findFundBy(req.getActivityId(), req.getActivityType(), providerId);
            if (activityServiceProvider != null) {
                throw new BusinessException("已配资，无需重复操作");
            }
        }
        if (ActivityServiceProviderEnum.ProviderType.Dispose.getKey().equals(req.getProviderType())) {
            TDisposeProvider disposeProvider = disposeProviderDao.getByPartyId(req.getPartyId());
            if (disposeProvider == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            providerId = disposeProvider.getId();
            TActivityServiceProvider activityServiceProvider = activityServiceProviderDao.findDisposeBy(req.getActivityId(), req.getActivityType(), providerId);
            if (activityServiceProvider != null) {
                throw new BusinessException("已处置，无需重复操作");
            }
        }


        TActivityServiceProvider activityServiceProvider = new TActivityServiceProvider();
        activityServiceProvider.setActivityId(req.getActivityId());
        activityServiceProvider.setActivityType(req.getActivityType());
        activityServiceProvider.setProviderId(providerId);
        activityServiceProvider.setProviderType(req.getProviderType());
        activityServiceProvider.setSource(ActivityServiceProviderEnum.Source.Web.getKey());
        int result = activityServiceProviderDao.insert(activityServiceProvider);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return ResponseModel.succ();
    }

    @Transactional
    @Override
    public ResponseModel activityServiceProviderSetup(ActivityReq.ActivityServiceProviderReq req) {
        if (!ActivityServiceProviderEnum.ActivityType.contains(req.getActivityType())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (ActivityServiceProviderEnum.ActivityType.Auction.getKey().equals(req.getActivityType())) {
            AuctionActivity activity = auctionActivityDao.selectById(req.getActivityId());
            if (activity == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
        }
        if (ActivityServiceProviderEnum.ActivityType.Enrolling.getKey().equals(req.getActivityType())) {
            EnrollingActivity activity = enrollingActivityDao.selectById(req.getActivityId());
            if (activity == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            if (req.getFundProviderName() != null) {
                activity.setFundProvider(req.getFundProviderName());
                enrollingActivityDao.updateById(activity);
            }
        }
        if (req.getDisposeProviderId() != null) {
            TDisposeProvider disposeProvider = disposeProviderDao.selectById(req.getDisposeProviderId());
            if (disposeProvider == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            TActivityServiceProvider activityServiceProvider = activityServiceProviderDao.findDisposeBy(req.getActivityId(), req.getActivityType(), req.getDisposeProviderId());
            if (activityServiceProvider != null) {
                throw new BusinessException("已是该活动处置服务商");
            }
            activityServiceProviderDao.deleteBackDisposeProvider(req.getActivityId(), req.getActivityType());
            activityServiceProvider = new TActivityServiceProvider();
            activityServiceProvider.setActivityId(req.getActivityId());
            activityServiceProvider.setActivityType(req.getActivityType());
            activityServiceProvider.setProviderId(disposeProvider.getId());
            activityServiceProvider.setProviderType(ActivityServiceProviderEnum.ProviderType.Dispose.getKey());
            activityServiceProvider.setSource(ActivityServiceProviderEnum.Source.Admin.getKey());
            activityServiceProvider.setOrderNum(0);
            int result = activityServiceProviderDao.insert(activityServiceProvider);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        return ResponseModel.succ();
    }

    @Override
    public ResponseModel cancelActivityServiceProvider(ActivityReq.ActivityServiceProviderReq req) {
        if (!ActivityServiceProviderEnum.ActivityType.contains(req.getActivityType())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (ActivityServiceProviderEnum.ActivityType.Auction.getKey().equals(req.getActivityType())) {
            AuctionActivity activity = auctionActivityDao.selectById(req.getActivityId());
            if (activity == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
        }
        if (ActivityServiceProviderEnum.ActivityType.Enrolling.getKey().equals(req.getActivityType())) {
            EnrollingActivity activity = enrollingActivityDao.selectById(req.getActivityId());
            if (activity == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
        }
        TDisposeProvider disposeProvider = disposeProviderDao.selectById(req.getDisposeProviderId());
        if (disposeProvider == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TActivityServiceProvider activityServiceProvider = activityServiceProviderDao.findDisposeBy(req.getActivityId(), req.getActivityType(), disposeProvider.getId());
        if (activityServiceProvider != null) {
            activityServiceProvider.setDeleteFlag(true);
            int result = activityServiceProviderDao.updateById(activityServiceProvider);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        return ResponseModel.succ();
    }

    @Override
    public ResponseModel updateActivityServiceProvider(ActivityReq.ActivityServiceProviderReq req) {
        TActivityServiceProvider activityServiceProvider = activityServiceProviderDao.selectById(req.getId());
        if (activityServiceProvider == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        activityServiceProvider.setOrderNum(req.getOrderNum());
        int result = activityServiceProviderDao.updateById(activityServiceProvider);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return ResponseModel.succ();
    }

    @Override
    public ResponseModel getDisposeActivityServiceProviderEnrollRecord(ActivityReq.ActivityServiceProviderReq req) {
        PageInfoResp<ActivityServiceProvider> resp = new PageInfoResp<>();
        PageInfo<TActivityServiceProvider> pageInfo = activityServiceProviderDao.getActivityServiceDisposeProviderEnrollRecord(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req));
        List<ActivityServiceProvider> resultList = new LinkedList<>();
        if (ActivityServiceProviderEnum.ActivityType.Auction.getKey().equals(req.getActivityType()) && req.getPage() == 1) {
            AuctionActivity activity = auctionActivityDao.selectById(req.getActivityId());
            if (activity == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            Asset asset = assetDao.selectById(activity.getAssetId());
            String[] split = asset.getCityId().split(",");
            Integer cityId = null;
            if (split.length> 0) {
                if (StringUtils.isNotBlank(split[0])) {
                    if (split.length == 1) {
                        cityId = Integer.valueOf(split[0]);
                        TDisposeProvider disposeProvider = activityServiceProviderDao.getFirstLevelDisposeProvider(cityId);
                        if (disposeProvider != null) {
                            TActivityServiceProvider firstLevel = activityServiceProviderDao.getAuctionDisposeProvider(req.getActivityId(), disposeProvider.getId());
                            if (firstLevel == null) {
                                firstLevel = new TActivityServiceProvider();
                                firstLevel.setActivityId(req.getActivityId());
                                firstLevel.setActivityType(req.getActivityType());
                                firstLevel.setProviderId(disposeProvider.getId());
                                firstLevel.setProviderType(ActivityServiceProviderEnum.ProviderType.Dispose.getKey());
                                firstLevel.setSource(ActivityServiceProviderEnum.Source.Web.getKey());
                                firstLevel.setOrderNum(0);
                                firstLevel.setCityPartnerFlag(true);
                                activityServiceProviderDao.insert(firstLevel);
                            } else if (!firstLevel.getCityPartnerFlag()) {
                                firstLevel.setCityPartnerFlag(true);
                                activityServiceProviderDao.updateById(firstLevel);
                            }
                        }
                    }
                }
            }
        }
        for (TActivityServiceProvider item : pageInfo.getList()) {
            ActivityServiceProvider vo = new ActivityServiceProvider();
            BeanUtils.copyProperties(item, vo);
            TDisposeProvider tDisposeProvider = disposeProviderDao.selectById(item.getProviderId());
            if (tDisposeProvider != null) {
                DisposeProvider disposeProvider = disposeService.getDisposeProvider(tDisposeProvider);
                vo.setRegion(disposeProvider.getBusinessRegionDetail());
                vo.setPhone(tDisposeProvider.getContactMobile());
                vo.setName(tDisposeProvider.getCompanyName());
            }
            resultList.add(vo);
        }
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setList(resultList);
        return ResponseModel.succ(resp);
    }

    @Override
    public ResponseModel getFundActivityServiceProviderEnrollRecord(ActivityReq.ActivityServiceProviderReq req) {
        PageInfoResp<ActivityServiceProvider> resp = new PageInfoResp<>();
        PageInfo<TActivityServiceProvider> pageInfo = activityServiceProviderDao.getActivityServiceFundProviderEnrollRecord(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req));
        List<ActivityServiceProvider> resultList = new ArrayList<>();
        for (TActivityServiceProvider item : pageInfo.getList()) {
            ActivityServiceProvider vo = new ActivityServiceProvider();
            BeanUtils.copyProperties(item, vo);
            TFundProvider fundProvider = fundProviderDao.selectById(item.getProviderId());
            if (fundProvider != null) {
                TParty party = partyDao.selectById(fundProvider.getPartyId());
                if (PartyEnum.Type.company.name().equals(party.getType())) {
                    TCompany company = companyDao.selectById(fundProvider.getPartyId());
                    vo.setRegion(cityService.getCityName(company));
                } else {
                    TUser user = userDao.selectById(fundProvider.getPartyId());
                    vo.setRegion(cityService.getCityName(user));
                }
                vo.setName(fundProvider.getCompanyName());
                vo.setPhone(accountDao.getMobile(fundProvider.getAccountId()));
            }
            resultList.add(vo);
        }
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setList(resultList);
        return ResponseModel.succ(resp);
    }

    @Override
    public ResponseModel getBackgroundActivityServiceProvider(ActivityReq.ActivityServiceProviderReq req) {
        if (!ActivityServiceProviderEnum.ActivityType.contains(req.getActivityType())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (ActivityServiceProviderEnum.ActivityType.Auction.getKey().equals(req.getActivityType())) {
            AuctionActivity activity = auctionActivityDao.selectById(req.getActivityId());
            if (activity == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
        }
        Map<String, Object> data = new HashMap<>();
        if (ActivityServiceProviderEnum.ActivityType.Enrolling.getKey().equals(req.getActivityType())) {
            EnrollingActivity activity = enrollingActivityDao.selectById(req.getActivityId());
            if (activity == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            data.put("fundProviderName", activity.getFundProvider());
        }
        List<TDisposeProvider> list = activityServiceProviderDao.getDisposeProvider(req.getActivityId(), req.getActivityType());
        if (list.size() > 0) {
            data.put("disposeProvider", disposeService.getDisposeProvider(list.get(0)));
        }
        return ResponseModel.succ(data);
    }

    @Override
    public List<TDisposeProvider> getDisposeProvider(Integer activityId, String activityType) {
        return activityServiceProviderDao.getDisposeProvider(activityId, activityType);
    }

    @Override
    public List<TDisposeProvider> getEnrollingDisposeProvider(Integer activityId) {
        return activityServiceProviderDao.getEnrollingDisposeProvider(activityId);
    }

    private List<FundProvider> getFundProviders() {
        List<FundProvider> fundProviders = new ArrayList<>();
        return fundProviders;
    }
}
