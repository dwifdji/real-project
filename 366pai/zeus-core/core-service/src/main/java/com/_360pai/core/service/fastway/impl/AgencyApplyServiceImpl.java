package com._360pai.core.service.fastway.impl;

import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.FastwayEnum;
import com._360pai.core.condition.account.TAgencyApplyRecordCondition;
import com._360pai.core.condition.account.TAgencyCondition;
import com._360pai.core.condition.fastway.TFastwayAgencyApplyCondition;
import com._360pai.core.dao.account.TAgencyApplyRecordDao;
import com._360pai.core.dao.account.TAgencyDao;
import com._360pai.core.dao.fastway.TFastwayAgencyApplyDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.account.TAccount;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.account.TAgencyApplyRecord;
import com._360pai.core.model.account.TCompany;
import com._360pai.core.model.fastway.TFastwayAgencyApply;
import com._360pai.core.service.fastway.AgencyApplyService;
import com._360pai.core.service.fastway.FastwayService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author xiaolei
 * @create 2018-11-29 14:37
 */
@Service
public class AgencyApplyServiceImpl extends FastwayService implements AgencyApplyService {

    public static final String UNSUBMITTED = "00";
    public static final String PENDING = "10";
    public static final String APPROVED = "20";
    public static final String REJECT = "30";
    @Autowired
    private TFastwayAgencyApplyDao agencyApplyDao;
    @Autowired
    private TAgencyDao agencyDao;
    @Autowired
    private TAgencyApplyRecordDao tAgencyApplyRecordDao;

    @Override
    public int auctionApply(JSONObject applyFiled, Integer accountId, String source, Integer partyId) {
        checkHasApply(accountId);
//        checkCompanyIDCardUnique(applyFiled.getJSONObject(FastwayEnum.AgencyType.AUCTION).getString("license"));
        TFastwayAgencyApply agencyApply = new TFastwayAgencyApply();
        agencyApply.setApplyFiled(applyFiled);
        agencyApply.setAccountId(accountId);
        agencyApply.setPartyId(partyId);
        agencyApply.setApplyType(FastwayEnum.AgencyType.AUCTION);
        agencyApply.setSource(source);
        agencyApply.setCode(applyFiled.getJSONObject(FastwayEnum.AgencyType.AUCTION).getString("code"));
        int count = agencyApplyDao.insert(agencyApply);
        // 用户申请发送客服邮件
        serviceMessageUtils.fastwayAgencyAuctionApply(agencyApply.getId());
        return count;
    }

    @Override
    public List<TFastwayAgencyApply> selectByCondition(TFastwayAgencyApplyCondition condition) {
        return agencyApplyDao.selectList(condition);
    }

    @Override
    public List<TCompany> findByAccountId(Integer accountId) {
        return agencyApplyDao.findByAccountId(accountId);
    }

    @Override
    public String getAgencyAuthCode(Integer accountId, Integer partyId, Integer agencyId) {
        if (agencyId != null) {
            // 已认证
            return APPROVED;
        }

        // 去查询 t_agency_apply_record 记录
        TAgencyApplyRecordCondition condition1 = new TAgencyApplyRecordCondition();
        condition1.setAccountId(accountId);
        condition1.setIsDel(0);
        TAgencyApplyRecord agencyApplyRecord = tAgencyApplyRecordDao.selectFirst(condition1);
        if (agencyApplyRecord != null) {
            if (agencyApplyRecord.getStatus().equals(AccountEnum.ApplyStatus.PENDING.getKey())) {
                return PENDING;
            } else if (agencyApplyRecord.getStatus().equals(AccountEnum.ApplyStatus.REJECT.getKey())){
                return REJECT;
            }
        } else {
            TFastwayAgencyApplyCondition condition = new TFastwayAgencyApplyCondition();
            condition.setAccountId(accountId);
//            condition.setPartyId(partyId);
            condition.setIsDel(false);
            // 判断审核中和未认证
            TFastwayAgencyApply agencyApply = agencyApplyDao.selectFirst(condition);
            if (agencyApply != null) {
                return agencyApply.getApplyStatus();
            }
        }
        // 默认是未认证，被拒绝用户可以重新提交审核
        return UNSUBMITTED;
    }

    @Override
    public void checkAgencyAbbr(String abbr) {

        // 校验只包含字母和数字
        if (!Pattern.matches("^[A-Za-z0-9]+$", abbr))
        {
            throw new BusinessException("只允许输入字母和数字") ;
        }
        // 校验长度
        if (abbr.length() > 15)
        {
            throw new BusinessException("字符超限，15个字符以内") ;
        }
        if (abbr.equalsIgnoreCase("www"))
        {
            throw new BusinessException("子站域名不合法，请重新输入");
        }
        // 校验是否已存在
        TAgencyCondition condition = new TAgencyCondition();
        condition.setCode(abbr);
        condition.setDeleteFlag(false);
        List<TAgency> tAgencies = agencyDao.selectList(condition);
        if (!tAgencies.isEmpty()) throw new BusinessException("子站域名已存在，请重新输入");
    }

    private void checkHasApply(Integer accountId) {
        TFastwayAgencyApplyCondition condition = new TFastwayAgencyApplyCondition();
        condition.setAccountId(accountId);
        condition.setApplyStatus(FastwayEnum.DisposeStatusEnum.waitting.getKey());
        condition.setIsDel(false);
        List<TFastwayAgencyApply> applyList = selectByCondition(condition);
        if (CollectionUtils.isNotEmpty(applyList))
            throw new BusinessException("您的申请已提交，请等待审核");
    }

    @Override
    public Long insertCompanyApplyRecord(TAccount tAccount, Object object, Integer operatorId, String source) { return null; }
}
