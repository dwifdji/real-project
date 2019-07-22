package com._360pai.core.service.disposal.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.utils.NumberValidationUtils;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.*;
import com._360pai.core.condition.disposal.TDisposalBiddingCondition;
import com._360pai.core.condition.disposal.TDisposalRequirementCondition;
import com._360pai.core.dao.account.TAgencyDao;
import com._360pai.core.dao.account.TDisposeProviderDao;
import com._360pai.core.dao.disposal.TDisposalBiddingDao;
import com._360pai.core.dao.disposal.TDisposalRequirementDao;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.disposal.resp.AdminBiddingInfoResp;
import com._360pai.core.facade.disposal.resp.BiddingInfoResp;
import com._360pai.core.facade.disposal.resp.RequirementProgressForAdminResp;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.disposal.TDisposalBidding;
import com._360pai.core.model.disposal.TDisposalRequirement;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.assistant.TBackstageOperationRecodService;
import com._360pai.core.service.disposal.DisposalAdminService;
import com._360pai.core.service.disposal.DisposalRequirementService;
import com._360pai.core.utils.ServiceMessageUtils;
import com._360pai.core.utils.ServicePayUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com._360pai.core.common.constants.DisposalEnum.RequirementStatus.*;

/**
 * @author xiaolei
 * @create 2018-09-15 18:47
 */
@Service
public class DisposalAdminServiceImpl implements DisposalAdminService {

    private final static String REQUIREMENT_AUDIT_PASS   = "服务处置审核通过";
    private final static String REQUIREMENT_AUDIT_NOPASS = "服务处置审核拒绝";

    @Autowired
    private TDisposalRequirementDao         disposalRequirementDao;
    @Autowired
    private TDisposalBiddingDao             disposalBiddingDao;
    @Autowired
    private TDisposeProviderDao             disposeProviderDao;
    @Autowired
    private AccountService                  accountService;
    @Autowired
    private ServiceMessageUtils             serviceMessageUtils;
    @Autowired
    private ServicePayUtils                 servicePayUtils;
    @Autowired
    private TBackstageOperationRecodService tBackstageOperationRecodService;
    @Autowired
    private DisposalRequirementService      disposalRequirementService;
    @Autowired
    private GatewayMqSender                 mqSender;
    @Resource
    private RedisCachemanager               redisCachemanager;
    @Autowired
    private TAgencyDao                      agencyDao;

    @Override
    public PageInfoResp findDisposalByAdmin(TDisposalRequirementCondition condition,
                                            int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TDisposalRequirement>            disposalList = disposalRequirementDao.findDisposalConditionAdmin(condition);
        PageInfo<TDisposalRequirement>        pageInfo     = new PageInfo<>(disposalList);
        List<RequirementProgressForAdminResp> list         = new LinkedList<>();
        RequirementProgressForAdminResp       dto;
        for (TDisposalRequirement tmp : disposalList) {
            dto = new RequirementProgressForAdminResp();
            BeanUtils.copyProperties(tmp, dto);
            // 设置发布人的电话号码
            if (AssetEnum.ComeFrom.AGENCY.getKey().equals(tmp.getComeFrom())) {
                TAgency agency = agencyDao.selectById(tmp.getPartyId());
                dto.setSourceMobile(agency.getMobile());
                dto.setSourceName(agency.getName());
            } else {
                AccountBaseDto tAccount = accountService.getAccountBaseByPartyId(tmp.getPartyId());
                dto.setSourceMobile(tAccount == null ? "" : tAccount.getMobile());
                dto.setSourceName(tAccount == null ? "" : tAccount.getName());
            }
            dto.setDisposalId(tmp.getId());
            dto.setPublishTime(tmp.getCreateTime());
            dto.setDisposalName(tmp.getIsPlatform() == 1 ? tmp.getPlatformNo() : tmp.getDisposalName());
            list.add(dto);
        }
        PageInfoResp<RequirementProgressForAdminResp> pageInfoResp = new PageInfoResp<>();
        pageInfoResp.setHasNextPage(pageInfo.isHasNextPage());
        pageInfoResp.setList(list);
        pageInfoResp.setTotal(pageInfo.getTotal());
        return pageInfoResp;
    }

    @Override
    public TDisposalRequirement findDisposalById(Integer disposalId) {
        return disposalRequirementDao.selectById(disposalId);
    }

    @Override
    public PageUtils.Page findBiddingByDisposalId(TDisposalBiddingCondition req, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<TDisposalBidding>     biddingByDisposalId = disposalRequirementDao.findBiddingByDisposalId(req);
        PageInfo<TDisposalBidding> pageInfo            = new PageInfo<>(biddingByDisposalId);

        List<BiddingInfoResp> list = new LinkedList<>();
        BiddingInfoResp       dto;
        for (TDisposalBidding tmp : biddingByDisposalId) {
            dto = new BiddingInfoResp();
            BeanUtils.copyProperties(tmp, dto);
            list.add(dto);
        }
        PageUtils.Page<BiddingInfoResp> pageResult = new PageUtils.Page<>();
        pageResult.setTotal((int) pageInfo.getTotal());
        pageResult.setList(list);
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setPage(pageNum);
        pageResult.setPerPage(pageSize);
        return pageResult;
    }

    @Override
    public int dealSuccess(TDisposalBidding condition) {
        return disposalBiddingDao.updateById(condition);
    }

    @Override
    public int verifyRequirementStatus(String verify, Integer disposalId,
                                       Integer operatorVerifyId,
                                       String disposalStatus) {
        TDisposalRequirement condition = new TDisposalRequirement();
        condition.setVerifyContent(verify);
        condition.setId(disposalId);
        condition.setDisposalStatus(disposalStatus);
        condition.setOperatorVerifyId(operatorVerifyId);
        Boolean noNeedPay = false;
        if (PASS_FOR_PAY.getValue().equals(disposalStatus)) {
            condition.setPublishTime(new Date());
            // 是否需要支付 ，不需要支付，则前端展示状态
            noNeedPay = servicePayUtils.noNeedPay(operatorVerifyId, ServiceConfigEnum.DISPOSAL_REQUIREMENT_PRICE, ServiceOrderEnum.OrderType.DIPOSAL_REQUIREMENT);
            if (noNeedPay) {
                condition.setDisposalStatus(DisposalEnum.RequirementStatus.BEGINNING.getValue());
            }
        }
        condition.setUpdateTime(new Date());
        int i = disposalRequirementDao.updateById(condition);
        if (i > 0) {

            tBackstageOperationRecodService.insertRecode(BackstageOperationEnum.Type.DISPOSAL_AUDIT, disposalId.longValue(), REQUIREMENT_AUDIT_PASS, operatorVerifyId);

            // 审核通过
            if (PASS_FOR_PAY.getValue().equals(disposalStatus) || BEGINNING.getValue().equals(disposalStatus)) {
                // 发短信
                serviceMessageUtils.disposalRequirementApplyPassNotify(disposalId);
                if (noNeedPay) {
                    setDisposalDeadline(disposalId);
                } else {
                    setDisposalPayExpired(disposalId);
                }
            }
            // 审核不通过
            if (NO_PASS.getValue().equals(disposalStatus)) {
                serviceMessageUtils.disposalRequirementApplyNotPassNotify(disposalId);
            }
        }
        return i;
    }

    private void setDisposalDeadline(Integer id) {
        TDisposalRequirement requirementDetail = disposalRequirementService.findRequirementDetail(id);
        // 设置过期时间处置key
        long timeout = (requirementDetail.getDeadline().getTime() - System.currentTimeMillis()) / 1000;
        //mqSender.disposalDeadlineEnqueue(id + "", timeout);
        redisCachemanager.set(SystemConstant.DISPOSAL_DEADLINE_SMS + id + "", id + "", timeout);
    }

    private void setDisposalPayExpired(Integer id) {
        mqSender.disposalPayExpiredEnqueue(id + "", servicePayUtils.getServicePayExpiredTime() * 60);
    }


    @Override
    public int addBiddingNotice(String biddingNotice, Integer operatorNoticeId, Integer disposalId) {
        TDisposalRequirement condition = new TDisposalRequirement();
        condition.setBiddingNotice(biddingNotice);
        condition.setOperatorNoticeId(operatorNoticeId);
        condition.setUpdateTime(new Date());
        condition.setId(disposalId);
        return disposalRequirementDao.updateById(condition);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean manuallyCompleted(Integer operatorId, Integer disposalId) {
        TDisposalRequirement condition = new TDisposalRequirement();
        condition.setOperatorFinishId(operatorId);
        condition.setDisposalStatus(DisposalEnum.RequirementStatus.FINISH.getValue());
        condition.setUpdateTime(new Date());
        condition.setId(disposalId);
        int i = disposalRequirementDao.updateManuallyFinished(condition);
        if (i > 0) {
            TDisposalBidding condition2 = new TDisposalBidding();
            condition2.setOperatorId(operatorId);
            condition2.setDisposalId(disposalId);
            condition2.setBidStatus(String.valueOf(DisposalEnum.BiddingStatus.DONE.getKey()));
            condition2.setUpdateTime(new Date());
            disposalBiddingDao.updateManuallyFinished(condition2);
        }
        return true;
    }

    @Override
    public PageInfoResp findBiddingInfoList(TDisposalBiddingCondition condition, int page, int perPage) {
        PageHelper.startPage(page, perPage);
        List<TDisposalBidding>     biddingInfoList = disposalBiddingDao.findBiddingInfoListByCondition(condition);
        PageInfo<TDisposalBidding> pageInfo        = new PageInfo<>(biddingInfoList);
        List<AdminBiddingInfoResp> list            = new LinkedList<>();
        AdminBiddingInfoResp       resp;
        for (TDisposalBidding tmp : biddingInfoList) {
            resp = new AdminBiddingInfoResp();
            BeanUtils.copyProperties(tmp, resp);
            resp.setBidCost(NumberValidationUtils.formatPrice(tmp.getBidCost()));
            if (DisposalEnum.DisposeType.LAWYER.getKey().equals(tmp.getDisposeType())
                    && StringUtils.isBlank(tmp.getCompanyName())) {
                resp.setCompanyName(getName(tmp.getPartyId()));
            }
            list.add(resp);
        }
        PageInfoResp<AdminBiddingInfoResp> pageInfoResp = new PageInfoResp<>();
        pageInfoResp.setTotal(pageInfo.getTotal());
        pageInfoResp.setList(list);
        pageInfoResp.setHasNextPage(pageInfo.isHasNextPage());
        return pageInfoResp;
    }

    private String getName(Integer partyId) {
        AccountBaseDto accountBaseByPartyId = accountService.getAccountBaseByPartyId(partyId);
        return accountBaseByPartyId.getName();
    }

}
