package com._360pai.core.service.disposal.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.NumberValidationUtils;
import com._360pai.arch.common.utils.OrderCodeGenerator;
import com._360pai.core.common.constants.DisposalEnum;
import com._360pai.core.condition.disposal.TDisposalBiddingCondition;
import com._360pai.core.condition.disposal.TDisposalRequirementCondition;
import com._360pai.core.dao.account.TDisposeProviderDao;
import com._360pai.core.dao.disposal.TDisposalBiddingDao;
import com._360pai.core.dao.disposal.TDisposalRequirementDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.disposal.req.DisposalBiddingReq;
import com._360pai.core.facade.disposal.resp.*;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.core.model.assistant.City;
import com._360pai.core.model.disposal.TDisposalBidding;
import com._360pai.core.model.disposal.TDisposalRequirement;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.DisposeService;
import com._360pai.core.service.assistant.CityService;
import com._360pai.core.service.disposal.DisposalBiddingService;
import com._360pai.core.utils.Constant;
import com._360pai.core.utils.ServiceMessageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;

/**
 * @author xiaolei
 * @create 2018-09-14 13:46
 */
@Slf4j
@Service
public class DisposalBiddingServiceImpl implements DisposalBiddingService {

    @Autowired
    TDisposalBiddingDao disposalBiddingDao;
    @Autowired
    TDisposalRequirementDao disposalRequirementDao;
    @Autowired
    TDisposeProviderDao disposeProviderDao;
    @Autowired
    AccountService accountService;
    @Autowired
    DisposeService disposeService;
    @Autowired
    ServiceMessageUtils serviceMessageUtils;
    @Autowired
    CityService cityService;

    @Override
    public PageInfoResp findBiddingInfoListPage(DisposalBiddingReq.GetBiddingInfoReq req) {

        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<TDisposalBidding> list = disposalBiddingDao.findBiddingInfoList(req.getPartyId());
        PageInfo<TDisposalBidding> pageInfo = new PageInfo<>(list);

        List<BiddingProgressResp> respList = new LinkedList<>();
        for (TDisposalBidding tmp : list) {
            BiddingProgressResp biddingProgressResp = new BiddingProgressResp();
            BeanUtils.copyProperties(tmp, biddingProgressResp);
            biddingProgressResp.setBiddingId(tmp.getId());
            biddingProgressResp.setBidCost(NumberValidationUtils.formatPrice(tmp.getBidCost()));
            respList.add(biddingProgressResp);
        }
        PageInfoResp<BiddingProgressResp> pageInfoResp = new PageInfoResp<>();
        pageInfoResp.setTotal(pageInfo.getTotal());
        pageInfoResp.setList(respList);
        pageInfoResp.setHasNextPage(pageInfo.isHasNextPage());
        return pageInfoResp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addBiddingInfo(DisposalBiddingReq.AddBiddingReq req) {

        TDisposalRequirementCondition condition = new TDisposalRequirementCondition();
        condition.setId(req.getDisposalId());
        condition.setDisposalNo(req.getDisposalNo());
        condition.setIsDel(0);
        TDisposalRequirement query = disposalRequirementDao.selectFirst(condition);
        if (query == null) {
            return 0;
        }
        // 单个人不要重复投标
        TDisposalBiddingCondition biddingCondition         = new TDisposalBiddingCondition();
        biddingCondition.setDisposalId(req.getDisposalId());
        biddingCondition.setPartyId(req.getPartyId());
        biddingCondition.setIsDel(0);
        List<TDisposalBidding>    biddingInfoList = disposalBiddingDao.selectList(biddingCondition);
        if (CollectionUtils.isNotEmpty(biddingInfoList)){
            return -1;
        }
        TDisposalBidding bidding = new TDisposalBidding();
        BeanUtils.copyProperties(req, bidding);
        bidding.setBidCost(req.getBidCost().toPlainString());
        bidding.setBidNo(OrderCodeGenerator.INSTANCE.getOrderCode(Constant.DisposalCons.TB_PREFIX));
        bidding.setCreateTime(new Date());
        bidding.setUpdateTime(new Date());
        disposalBiddingDao.insert(bidding);
        query.setBiddingNum(query.getBiddingNum() + 1);
        int count = disposalRequirementDao.updateById(query);
        if (query.getIsPlatform() == 1) {
            serviceMessageUtils.disposalRequirementPlatformBid(query.getId());
        } else {
            serviceMessageUtils.disposalRequirementNotPlatformBid(query.getId());
        }
        return count;
    }

    @Override
    public BiddingInfoResp findBiddingDetailInfo(DisposalBiddingReq.GetBiddingInfoReq req) {
        TDisposalBidding bidding = disposalBiddingDao.selectById(req.getBiddingId());
        BiddingInfoResp resp = new BiddingInfoResp();
        if (bidding != null) {
            BeanUtils.copyProperties(bidding, resp);
        }
        return resp;
    }

    @Override
    public PageInfoResp findInvestmentInfo(DisposalBiddingReq.GetBiddingInfoReq req) {
        PageHelper.startPage(req.getPage(),req.getPerPage());
        List<TDisposalBidding>          resultList = disposalBiddingDao.findInvestmentInfo();
        PageInfo<TDisposalBidding>      pageInfo   = new PageInfo<>(resultList);
        List<DisposalServiceInvestInfo> vos        = new LinkedList<>();
        for (TDisposalBidding tmp : resultList) {
            DisposalServiceInvestInfo vo = new DisposalServiceInvestInfo();
            BeanUtils.copyProperties(tmp, vo);
            vo.setCompanyName(Optional.ofNullable(tmp.getCompanyName()).orElse(getName(tmp.getPartyId())));
            vos.add(vo);
        }
        PageInfoResp<DisposalServiceInvestInfo> pageResult = new PageInfoResp<>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setList(vos);
        pageResult.setHasNextPage(pageInfo.isHasNextPage());
        return pageResult;
    }

    @Override
    public Map<String, Object> findDisposalProgramDetail(Integer biddingId) {

        // 获取投标信息
        BiddingProgramInfo programInfo = getBiddingByBiddingId(biddingId);

        // 获取投标公司信息
        CompanyInfo companyInfo        = getDisposeCompanyByPartyId(programInfo.getPartyId());

        programInfo.setCompanyName(companyInfo.getCompanyName());

        Map<String, Object> map = new HashMap<>(2);

        map.put("programInfo", programInfo);

        if (programInfo.getBidStatus().equals(String.valueOf(DisposalEnum.BiddingStatus.SUCCESS.getKey()))) {

            map.put("companyInfo", companyInfo);
        }

        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirmBid(Integer biddingId, Integer partyId) {
        TDisposalBidding bidding = disposalBiddingDao.selectById(biddingId);
        if (null != bidding && bidding.getIsDel() == 0
                && bidding.getBidStatus().equals(DisposalEnum.BiddingStatus.BIDDINGING.getKey())) {

            TDisposalRequirement requirement = disposalRequirementDao.selectById(bidding.getDisposalId());
            if (requirement == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            if (!requirement.getPartyId().equals(partyId)) {
                throw new BusinessException("非本人需求单，无法确认中标");
            }
            requirement.setDisposalStatus(DisposalEnum.RequirementStatus.SUCCESS.getValue());
            requirement.setUpdateTime(new Date());
            // 确认需求单竞标成功
            int i = disposalRequirementDao.confirmBid(requirement);
            if (i > 0) {
                TDisposalBidding condition2 = new TDisposalBidding();
                condition2.setBidStatus(String.valueOf(DisposalEnum.BiddingStatus.SUCCESS.getKey()));
                condition2.setUpdateTime(new Date());
                condition2.setId(biddingId);
                // 确认投标单成功
                disposalBiddingDao.updateById(condition2);
                TDisposalBidding condition3 = new TDisposalBidding();
                condition3.setDisposalId(bidding.getDisposalId());
                condition3.setBidStatus(String.valueOf(DisposalEnum.BiddingStatus.DONE.getKey()));
                condition3.setUpdateTime(new Date());
                // 确认其他投标单流标
                disposalBiddingDao.updateManuallyFinished(condition3);
                sendBiddingMessage(biddingId, bidding.getDisposalId());
            }
        }
        return true ;
    }

    private void sendBiddingMessage(Integer biddingId, Integer requirementId) {
        try {
            serviceMessageUtils.disposalSuccessToBid(biddingId);
            TDisposalBiddingCondition condition = new TDisposalBiddingCondition();
            condition.setDisposalId(requirementId);
            condition.setBidStatus(String.valueOf(DisposalEnum.BiddingStatus.DONE.getKey()));
            condition.setIsDel(0);
            List<TDisposalBidding> tDisposalBiddings = disposalBiddingDao.selectList(condition);
            for (TDisposalBidding tmp : tDisposalBiddings) {
                serviceMessageUtils.disposalBiddingFail(tmp.getId());
            }
        } catch (Exception e) {
            log.error("处置确认中标发送message异常，disposalId:{}",requirementId);
        }
    }

    @Override
    public boolean biddingFlag(Integer disposalId, Integer partyId) {
        TDisposalBiddingCondition condition = new TDisposalBiddingCondition();
        condition.setDisposalId(disposalId);
        condition.setPartyId(partyId);
        condition.setIsDel(0);
        List<TDisposalBidding> tDisposalBiddings = disposalBiddingDao.selectList(condition);
        return CollectionUtils.isNotEmpty(tDisposalBiddings);
    }

    private BiddingProgramInfo getBiddingByBiddingId(Integer biddingId) {

        TDisposalBidding bidding = disposalBiddingDao.selectById(biddingId);

        BiddingProgramInfo programInfo = new BiddingProgramInfo();

        BeanUtils.copyProperties(bidding, programInfo);

        programInfo.setBiddingId(biddingId);

        programInfo.setBidCost(NumberValidationUtils.formatPrice(bidding.getBidCost()));

        return programInfo;
    }

    private CompanyInfo getDisposeCompanyByPartyId(Integer partyId) {

        TDisposeProvider company = disposeService.getDisposeProviderByPartyId(partyId);

        CompanyInfo companyInfo  = new CompanyInfo();

        BeanUtils.copyProperties(company, companyInfo);

        companyInfo.setRegisterCapital(NumberValidationUtils.formatPrice(company.getRegisterCapital()));
        companyInfo.setRegisterAddress(Optional.ofNullable(companyInfo.getRegisterAddress()).orElse("-"));
        companyInfo.setRegion(getRegion(company.getRegion()));
        companyInfo.setCompanyType(DisposalEnum.DisposeType.getValueByKey(company.getDisposeType()));
        companyInfo.setContactName(Optional.ofNullable(companyInfo.getContactName()).orElse(getName(company.getPartyId())));
        companyInfo.setCompanyName(Optional.ofNullable(companyInfo.getCompanyName()).orElse(companyInfo.getContactName()));
        return companyInfo;
    }

    private String getRegion(String cityId) {
        if (cityId != null) {
            City byCityId = cityService.getByCityId(Integer.valueOf(cityId));
            if (byCityId == null)
                return "-";
            else
                return byCityId.getName();
        }
        return "-";
    }

    private String getName(Integer partyId) {
        AccountBaseDto accountBaseByPartyId = accountService.getAccountBaseByPartyId(partyId);
        return accountBaseByPartyId.getName();
    }
}