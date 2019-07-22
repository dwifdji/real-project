package com._360pai.core.service.fastway.impl;

import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.FastwayEnum;
import com._360pai.core.condition.account.TFundProviderApplyCondition;
import com._360pai.core.condition.fastway.TFastwayFundApplyCondition;
import com._360pai.core.dao.account.TFundProviderApplyDao;
import com._360pai.core.dao.fastway.TFastwayFundApplyDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.assistant.vo.CityVo;
import com._360pai.core.model.account.TAccount;
import com._360pai.core.model.account.TCompany;
import com._360pai.core.model.account.TFundProviderApply;
import com._360pai.core.model.account.TUser;
import com._360pai.core.model.fastway.TFastwayFundApply;
import com._360pai.core.service.fastway.FastwayService;
import com._360pai.core.service.fastway.FundApplyService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author xiaolei
 * @create 2018-12-07 10:07
 */
@Service
public class FundApplyServiceImpl extends FastwayService implements FundApplyService {

    public static final String UNSUBMITTED = "00";
    public static final String PENDING = "10";
    public static final String APPROVED = "20";
    public static final String REJECT = "30";
    @Autowired
    private TFastwayFundApplyDao fundApplyDao;
    @Autowired
    private TFundProviderApplyDao fundProviderApplyDao;

    @Override
    public int userApply(JSONObject applyFiled, Integer accountId, String source, Integer partyId) {
        checkHasApply(accountId, partyId, FastwayEnum.FundType.User);
        checkUserIDCardUnique(applyFiled.getJSONObject(FastwayEnum.FundType.User).getString("certificateNumber"), partyId);
        TFastwayFundApply fundApply = new TFastwayFundApply();
        fundApply.setPartyId(validatePartyId(partyId));
        fundApply.setApplyFiled(applyFiled);
        fundApply.setAccountId(accountId);
        fundApply.setApplyType(FastwayEnum.FundType.User);
        fundApply.setSource(source);
        int count = fundApplyDao.insert(fundApply);
        // 用户申请发送客服邮件
        serviceMessageUtils.fastwayFundApply(fundApply.getId());
        return count;
    }

    @Override
    public int companyApply(JSONObject applyFiled, Integer accountId, String source, Integer partyId) {
//        checkHasApply(accountId, partyId, FastwayEnum.FundType.Company);
//        checkCompanyIDCardUnique(applyFiled.getJSONObject(FastwayEnum.FundType.Company).getString("license"));
        TFastwayFundApply fundApply = new TFastwayFundApply();
        fundApply.setPartyId(validatePartyId(partyId));
        fundApply.setApplyFiled(applyFiled);
        fundApply.setAccountId(accountId);
        fundApply.setApplyType(FastwayEnum.FundType.Company);
        fundApply.setSource(source);
        int count = fundApplyDao.insert(fundApply);
        // 用户申请发送客服邮件
        serviceMessageUtils.fastwayFundApply(fundApply.getId());
        return count;
    }


    @Override
    public List<TFastwayFundApply> selectByCondition(TFastwayFundApplyCondition condition) {
        return fundApplyDao.selectList(condition);
    }

    @Override
    public Map<String, Object> userFundAuthInfo(Integer accountId) {
        Map<String, Object> result = new HashMap<>();
        TUser userByAccountId = userService.findUserByAccountId(accountId);
        String userAuthCode   = getUserAuthCode(accountId, userByAccountId == null ? null : userByAccountId.getId(), FastwayEnum.FundType.User);
        if (userByAccountId != null) {
            userByAccountId.setCityVo(getCityVo(userByAccountId));
        }
        result.put("userInfo", userByAccountId);
        result.put("authCode", userAuthCode);
        return result;
    }

    @Override
    public Map<String, Object> companyFundAuthInfo(Integer accountId) {
        Map<String, Object> result = new HashMap<>();
        List<TCompany> applyStatusByAccountId = fundApplyDao.findApplyStatusByAccountId(accountId);
//        if (CollectionUtils.isNotEmpty(applyStatusByAccountId)) {
//            applyStatusByAccountId.removeIf(t -> FastwayEnum.DisposeStatusEnum.waitting.getKey().equals(t.getFundApplyStatus())
//                    || FastwayEnum.DisposeStatusEnum.access.getKey().equals(t.getFundApplyStatus()));
//        }
        applyStatusByAccountId.removeIf(t -> FastwayEnum.DisposeStatusEnum.waitting.getKey().equals(t.getFundApplyStatus())
                || FastwayEnum.DisposeStatusEnum.access.getKey().equals(t.getFundApplyStatus()));
        applyStatusByAccountId.forEach(t -> t.setCityVo(getCityVo(t)));
        result.put("list", applyStatusByAccountId);
        return result;
    }

    @Override
    public Long insertCompanyApplyRecord(TAccount tAccount, Object object, Integer operatorId, String source) { return null; }

    private Integer validatePartyId(Integer partyId) {
        if (partyId != null) {
            return partyId == 0? null : partyId;
        }
        return null;
    }

    private void checkHasApply(Integer accountId, Integer partyId, String applyType) {
        TFastwayFundApplyCondition condition = new TFastwayFundApplyCondition();
        condition.setAccountId(accountId);
//        condition.setPartyId(partyId);
//        condition.setApplyStatus(FastwayEnum.DisposeStatusEnum.waitting.getKey());
        condition.setApplyType(applyType);
        condition.setIsDel(false);
        List<TFastwayFundApply> applyList = selectByCondition(condition);
        if (CollectionUtils.isNotEmpty(applyList))
            applyList.forEach(t -> {
                if (t.getApplyStatus().equals(FastwayEnum.DisposeStatusEnum.waitting.getKey())) {
                    throw new BusinessException("您的申请已提交，请等待审核");
                }
                if ( t.getApplyStatus().equals(FastwayEnum.DisposeStatusEnum.access.getKey())) {
                    throw new BusinessException("您的审核已通过，请勿重复提交");
                }
            });
    }

    public String getUserAuthCode(Integer accountId, Integer partyId, String applyType)
    {
        TFundProviderApply tFundProviderApply = null;
        if (partyId != null) {
            TFundProviderApplyCondition condition = new TFundProviderApplyCondition();
            condition.setPartyId(partyId);
            tFundProviderApply = fundProviderApplyDao.selectFirst(condition);
        }
        if (tFundProviderApply != null)
        {
            String status = tFundProviderApply.getStatus();
            if (AccountEnum.ApplyStatus.PENDING.getKey().equals(status))
            {
                return PENDING;
            } else if (AccountEnum.ApplyStatus.APPROVED.getKey().equals(status))
            {
                return APPROVED;
            } else if (AccountEnum.ApplyStatus.REJECT.getKey().equals(status))
            {
                return REJECT;
            }
        } else
        {
            TFastwayFundApplyCondition condition1 = new TFastwayFundApplyCondition();
            condition1.setAccountId(accountId);
            condition1.setPartyId(partyId);
            condition1.setApplyType(applyType);
            condition1.setIsDel(false);
            TFastwayFundApply fundApply = fundApplyDao.selectFirst(condition1);
            return fundApply == null ? UNSUBMITTED : fundApply.getApplyStatus();
        }
        return UNSUBMITTED;
    }
}
