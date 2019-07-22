package com._360pai.core.service.activity.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.activity.AuctionActivityShareStatsCondition;
import com._360pai.core.dao.account.TAccountDao;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.dao.activity.AuctionActivityShareStatsDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.resp.ActivityResp;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.activity.AuctionActivityShareStats;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.AgencyPortalActivityService;
import com._360pai.core.service.activity.AuctionActivityShareStatsService;
import com._360pai.core.service.activity.AuctionActivityViewCountService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/16 14:35
 */
@Service
@Slf4j
public class AuctionActivityShareStatsServiceImpl implements AuctionActivityShareStatsService {

    @Resource
    private AuctionActivityShareStatsDao auctionActivityShareStatsDao;
    @Resource
    private AuctionActivityViewCountService auctionActivityViewCountService;
    @Resource
    private AuctionActivityDao auctionActivityDao;
    @Resource
    private AccountService accountService;
    @Resource
    private AgencyPortalActivityService agencyPortalActivityService;
    @Autowired
    private TAccountDao accountDao;

    @Override
    public PageInfoResp<PartyAccount> getSharePartyAccountsByPage(ActivityReq.ActivityId req) {
        PageInfoResp<PartyAccount> resp = new PageInfoResp<>();
        AuctionActivityShareStatsCondition condition = new AuctionActivityShareStatsCondition();
        condition.setActivityId(req.getActivityId());
        PageInfo pageInfo = getAllByActivityId(req.getPage(), req.getPerPage(), condition, "");
        List list = pageInfo.getList();
        List<PartyAccount> partyVos = new ArrayList<>();
        for (Object item : list) {
            AuctionActivityShareStats activityShareStats = (AuctionActivityShareStats) item;
            PartyAccount partyVo;
            if (activityShareStats.getPartyId() == null) {
                partyVo = new PartyAccount();
                partyVo.setMobile(accountDao.getMobile(activityShareStats.getAccountId()));
                partyVo.setType(SystemConstant.ACCOUNT_COMMON_TYPE);
            } else {
                partyVo = accountService.getPartyAccountById(activityShareStats.getPartyId());
            }
            partyVo.setRecordAt(activityShareStats.getCreatedAt());
            partyVos.add(partyVo);
        }
        resp.setList(partyVos);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public PageInfo getAllByActivityId(int page, int perPage, AuctionActivityShareStatsCondition condition, String orderBy) {
        PageHelper.startPage(page, perPage);
        if (StringUtils.isNotBlank(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        return new PageInfo<>(auctionActivityShareStatsDao.selectList(condition));
    }

    @Override
    public void activityView(Integer activityId, String agencyCode,Integer incrementViewCountNum) {
        log.info("浏览量增加数量接口 活动ID为：{}，机构CODE为：{}", activityId, agencyCode);
        if (activityId == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "活动ID参数不能为空");
        }
        //二级机构浏览量
        agencyPortalActivityService.updateViewCountByAgencyCodeAndActivityId(agencyCode,activityId,incrementViewCountNum);
        //主站机构浏览量
        auctionActivityViewCountService.updateViewCountByActivityId(activityId,incrementViewCountNum);
    }

    @Override
    public ActivityResp share(ActivityReq.ActivityId req) {
        ActivityResp resp = new ActivityResp();
        AuctionActivity auctionActivity = auctionActivityDao.selectById(req.getActivityId());
        if (auctionActivity == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        auctionActivityShareStatsDao.createIfNotExist(req.getActivityId(), req.getAccountId(), req.getPartyId());
        return resp;
    }
}