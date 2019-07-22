package com._360pai.core.provider.disposal;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.utils.NumberValidationUtils;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.common.constants.DisposalEnum;
import com._360pai.core.condition.disposal.TDisposalBiddingCondition;
import com._360pai.core.condition.disposal.TDisposalRequirementCondition;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.DisposeProviderApplyReq;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.account.vo.AccountAuthVo;
import com._360pai.core.facade.disposal.DisposalAdminFacade;
import com._360pai.core.facade.disposal.req.DisposalRequirementReq;
import com._360pai.core.model.account.TAccount;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.core.model.disposal.TDisposalBidding;
import com._360pai.core.model.disposal.TDisposalRequirement;
import com._360pai.core.service.account.*;
import com._360pai.core.service.disposal.DisposalAdminService;
import com._360pai.core.service.disposal.DisposalRequirementService;
import com._360pai.core.utils.Constant;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * @author xiaolei
 * @create 2018-09-17 09:36
 */
@Component
@Service(version = "1.0.0")
public class DisposalAdminProvider implements DisposalAdminFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisposalAdminProvider.class);

    @Autowired
    private DisposalAdminService disposalAdminService;
    @Autowired
    private DisposalRequirementService disposalRequirementService;
    @Autowired
    private DisposeService disposeService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AgencyService agencyService;

    @Override
    public PageInfoResp findDisposalByAdmin(DisposalRequirementReq.GetPublishInfoReq req) {
        TDisposalRequirementCondition condition = new TDisposalRequirementCondition();
        if (StringUtils.isNotBlank(req.getDisposalStatus())) {
            condition.setDisposalStatus(req.getDisposalStatus());
        }
        if (StringUtils.isNotBlank(req.getDisposalName())) {
            condition.setDisposalName(req.getDisposalName());
        }
        PageInfoResp pageInfoResp =
                disposalAdminService.findDisposalByAdmin(condition, req.getPage(), req.getPerPage());
        return pageInfoResp;
    }

    @Override
    public Map<String, Object> findDisposalById(Integer disposalId) {
        TDisposalRequirement disposalById = disposalAdminService.findDisposalById(disposalId);
        Map<String, Object> result = new HashMap<>();
        result.put("disposalName", disposalById.getIsPlatform() == 1 ? disposalById.getPlatformNo() : disposalById.getDisposalName());
        result.put("disposalNo", disposalById.getDisposalNo());
        result.put("caseDescription", disposalById.getCaseDescription());
        result.put("requireDescription", disposalById.getRequireDescription());
        result.put("period", disposalById.getPeriod());
        result.put("cost", NumberValidationUtils.formatPrice(disposalById.getCost()));
        result.put("biddingNotice", disposalById.getBiddingNotice());
        result.put("disposalId", disposalById.getId());
        result.put("disposalType", disposalById.getDisposalType());
        result.put("disposalTypeDesc", DisposalEnum.RequirementType.getDescByKey(disposalById.getDisposalType()));
        result.put("assetId", disposalById.getAssetId());
        if (AssetEnum.ComeFrom.AGENCY.getKey().equals(disposalById.getComeFrom())) {
            TAgency agency = agencyService.findByAgencyId(disposalById.getPartyId());
            result.put("partyName", agency.getName());
        } else {
            result.put("partyName", getPartyName(disposalById.getPartyId()));
        }
        result.put("deadline", disposalById.getDeadline());// 截至日期
        result.put("publishTime", disposalById.getPublishTime());// 发布日期
        return result;
    }

    @Override
    public PageUtils.Page findBiddingByDisposalId(DisposalRequirementReq.GetBiddingList req) {
        Assert.notNull(req.getDisposalId(), "相关参数为空");
        TDisposalBiddingCondition condition = new TDisposalBiddingCondition();
        condition.setDisposalId(req.getDisposalId());
        condition.setBidStatus(req.getBidStatus());
        condition.setPartyName(req.getPartyName());
        condition.setBidTime(req.getBidTime());
        PageUtils.Page page
                = disposalAdminService.findBiddingByDisposalId(condition, req.getPage(), req.getPerPage());
        return page ;
    }

    @Override
    @Transactional
    public boolean updateDisposalSuccess(String communicateContent, Integer biddingId, Integer operatorId) {
        TDisposalBidding bidding = new TDisposalBidding();
        bidding.setDisposalType(Constant.DisposalStatus.BID_SUCCESS);
        bidding.setCommunicatContent(communicateContent);
        bidding.setOperatorId(operatorId);
        // 撮合成功
        disposalAdminService.dealSuccess(bidding);
        // 更新处置单状态
        disposalRequirementService.updateRequirementStatusByBiddingId(Constant.DisposalStatus.REQUIRE_SUCCESS, biddingId,
                operatorId);
        // 更新其他标单状态
        disposalRequirementService.updateBiddingStatusByBiddingId(Constant.DisposalStatus.BID_DONE, biddingId,
                operatorId);
        return true;
    }

    @Override
    public boolean updateDisposalVerifyStatus(DisposalRequirementReq.AdminOperateInfo req) {
        int tag = disposalAdminService.verifyRequirementStatus(req.getVerifyContent(), req.getDisposalId(),
                req.getOperatorVerifyId(), req.getDisposalStatus());
        return tag > 0;
    }

    @Override
    public boolean addBiddingNotice(String biddingNotice, Integer operatorNoticeId, Integer disposalId) {
        int tag = disposalAdminService.addBiddingNotice(biddingNotice, operatorNoticeId, disposalId);
        return tag > 0;
    }

    @Override
    public boolean manuallyCompleted(Integer operatorId, Integer disposalId) {
        return disposalAdminService.manuallyCompleted(operatorId, disposalId);
    }

    @Override
    public PageInfoResp findBiddingInfoList(DisposalRequirementReq.GetBiddingList req) {
        TDisposalBiddingCondition condition = new TDisposalBiddingCondition();
        condition.setDisposalId(req.getDisposalId());
        if (null != req.getBidTime()) {
            condition.setCreateTime(req.getBidTime());
        }
        if (StringUtils.isNotBlank(req.getBidStatus())) {
            condition.setBidStatus(req.getBidStatus());
        }
        if (StringUtils.isNotBlank(req.getPartyName())) {
            condition.setCompanyName(req.getPartyName());
        }
        return disposalAdminService.findBiddingInfoList(condition, req.getPage(), req.getPerPage());
    }

    @Override
    public Map<String, Object> checkAccountDispose(String mobile) {
        return accountService.checkAccountDispose(mobile);
    }

    @Override
    public int disposeProviderAdminCreate(DisposeProviderApplyReq.CreateReq req) {

        int count = 0;
        if (req.getPartyId() == null) {
            // 账户未认证
            count += disposeService.addDisposeProviderNoPartyId(req);
        } else {
            // 账户已认证
            count += disposeService.addDisposeProviderHasPartyId(req);
        }

        return count;
    }

    private String getPartyName(Integer partyId) {
        AccountBaseDto account = accountService.getAccountBaseByPartyId(partyId);
        if (account.isDisposer()) {
            TDisposeProvider disposeProvider = disposeService.getDisposeProviderByPartyId(account.getPartyPrimaryId());
            return disposeProvider.getCompanyName();
        } else {
            return account.getName();
        }
    }
}
