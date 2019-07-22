package com._360pai.core.service.account.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.account.TAgencyCondition;
import com._360pai.core.condition.activity.AgencyPortalActivityCondition;
import com._360pai.core.dao.account.AgencyPortalDao;
import com._360pai.core.dao.account.TAgencyDao;
import com._360pai.core.dao.activity.AgencyPortalActivityDao;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.resp.ActivityResp;
import com._360pai.core.model.account.AgencyPortal;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.activity.AgencyPortalActivity;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.AgencyPortalActivityService;
import com._360pai.core.service.activity.AgencyPortalService;
import com._360pai.core.service.assistant.SmsHelperService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AgencyPortalActivityServiceImpl implements AgencyPortalActivityService {

    @Autowired
    private AgencyPortalActivityDao agencyPortalActivityDao;
    @Autowired
    private AuctionActivityDao      auctionActivityDao;
    @Autowired
    private AgencyPortalService     agencyPortalService;
    @Autowired
    private AccountService          accountService;
    @Autowired
    private SmsHelperService        smsHelperService;
    @Resource
    private TAgencyDao              agencyDao;
    @Resource
    private AgencyPortalDao         agencyPortalDao;

    @Override
    public AgencyPortalActivity getByAgencyIdActivityId(Integer agencyId, Integer activityId) {
        AgencyPortalActivityCondition condition = new AgencyPortalActivityCondition();
        condition.setAgencyId(agencyId);
        condition.setActivityId(activityId);
        return agencyPortalActivityDao.selectFirst(condition);
    }

    @Override
    public AgencyPortalActivity getByAgencyPortalIdActivityId(Integer agencyPortalId, Integer activityId) {
        AgencyPortalActivityCondition condition = new AgencyPortalActivityCondition();
        condition.setAgencyPortalId(agencyPortalId);
        condition.setActivityId(activityId);
        return agencyPortalActivityDao.selectFirst(condition);
    }

    @Override
    public ActivityResp publishUnion(ActivityReq.ActivityId req) {
        ActivityResp    resp            = new ActivityResp();
        AuctionActivity auctionActivity = auctionActivityDao.getById(req.getActivityId());
        if (auctionActivity == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!AuctionActivity.UNIONABLE_STATUS.contains(auctionActivity.getStatus())) {
            throw new BusinessException("该活动无法被联拍, 只有上线并且未结束的活动可被联拍");
        }
        AgencyPortal         agencyPortal   = agencyPortalService.getByAgencyId(req.getAgencyId());
        AgencyPortalActivity portalActivity = getByAgencyPortalIdActivityId(agencyPortal.getId(), req.getActivityId());
        if (portalActivity == null) {
            portalActivity = new AgencyPortalActivity();
            portalActivity.setAgencyPortalId(agencyPortal.getId());
            portalActivity.setAgencyId(agencyPortal.getAgencyId());
            portalActivity.setActivityId(req.getActivityId());
            portalActivity.setViewCount(0);
            int row = agencyPortalActivityDao.insert(portalActivity);
            if (row == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            // 机构管理员
            String mobile = accountService.getAgencyNotifierMobile(req.getAgencyId());
            smsHelperService.agencyUnionActivityNotify(mobile, auctionActivity.getAssetName());
        }
        resp.setActivityId(req.getActivityId());
        return resp;
    }

    @Override
    public ActivityResp cancelUnion(ActivityReq.ActivityId req) {
        ActivityResp    resp            = new ActivityResp();
        AuctionActivity auctionActivity = auctionActivityDao.getById(req.getActivityId());
        if (auctionActivity == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        AgencyPortal         agencyPortal   = agencyPortalService.getByAgencyId(req.getAgencyId());
        AgencyPortalActivity portalActivity = getByAgencyPortalIdActivityId(agencyPortal.getId(), req.getActivityId());
        if (portalActivity != null) {
            int row = agencyPortalActivityDao.deleteNotify(portalActivity.getId());
            if (row == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        resp.setActivityId(req.getActivityId());
        return resp;
    }

    @Override
    public List<AgencyPortalActivity> getByActivity(Integer activityId) {
        return agencyPortalActivityDao.getByActivity(activityId);
    }

    @Override
    public int updateViewCountByAgencyCodeAndActivityId(String agencyCode, Integer activityId, Integer incrementViewCountNum) {
        int result = 0;
        if (StringUtils.isNotBlank(agencyCode)) {
            TAgencyCondition condition = new TAgencyCondition();
            condition.setCode(agencyCode);
            TAgency agency = agencyDao.selectOneResult(condition);
            log.info("查询出的机构信息为：{}", JSON.toJSONString(agency));
            if (agency != null) {
                AgencyPortal agencyPortal = agencyPortalDao.getByAgencyId(agency.getId());
                log.info("查询出的机构状态信息为：{}", JSON.toJSONString(agencyPortal));
                if (agencyPortal != null) {
                    AgencyPortalActivityCondition condition1 = new AgencyPortalActivityCondition();
                    condition1.setAgencyId(agency.getId());
                    condition1.setActivityId(activityId);
                    AgencyPortalActivity agencyPortalActivity = agencyPortalActivityDao.selectOneResult(condition1);
                    log.info("查询出的机构浏览信息为：{}", JSON.toJSONString(agencyPortalActivity));
                    if (null == agencyPortalActivity) {
                        AgencyPortalActivity portalActivity = new AgencyPortalActivity();
                        portalActivity.setViewCount(incrementViewCountNum);
                        portalActivity.setActivityId(activityId);
                        portalActivity.setAgencyId(agency.getId());
                        portalActivity.setAgencyPortalId(agencyPortal.getId());
                        portalActivity.setCreatedAt(new Date());
                        result = agencyPortalActivityDao.insert(portalActivity);
                    } else {
                        if (agencyPortalActivity.getViewCount() == null) {
                            agencyPortalActivity.setViewCount(incrementViewCountNum);
                        } else {
                            agencyPortalActivity.setViewCount(agencyPortalActivity.getViewCount() + incrementViewCountNum);
                        }
                        result = agencyPortalActivityDao.updateById(agencyPortalActivity);
                    }
                }
            }
        }
        return result;
    }
}