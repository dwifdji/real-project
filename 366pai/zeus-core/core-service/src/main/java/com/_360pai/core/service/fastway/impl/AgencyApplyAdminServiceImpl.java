package com._360pai.core.service.fastway.impl;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.AgencyEnum;
import com._360pai.core.common.constants.FastwayEnum;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.dao.account.TAgencyApplyRecordDao;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dao.fastway.TFastwayAgencyApplyDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.AgencyApplyReq;
import com._360pai.core.facade.disposal.req.City;
import com._360pai.core.facade.fastway.resp.AgencyAuctionDetailVO;
import com._360pai.core.model.account.TAccount;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.account.TAgencyApplyRecord;
import com._360pai.core.model.account.TCompanyApplyRecord;
import com._360pai.core.model.fastway.TFastwayAgencyApply;
import com._360pai.core.service.account.AccountBusinessService;
import com._360pai.core.service.account.AgencyService;
import com._360pai.core.service.fastway.AgencyApplyAdminService;
import com._360pai.core.service.fastway.FastwayService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author xiaolei
 * @create 2018-11-29 16:10
 */
@Service
public class AgencyApplyAdminServiceImpl extends FastwayService
        implements AgencyApplyAdminService {

    @Autowired
    private TFastwayAgencyApplyDao fastwayAgencyApplyDao;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private TAgencyApplyRecordDao agencyApplyRecordDao;
    @Autowired
    private CityDao cityDao;


    @Override
    public PageInfo findByParam(Map<String, Object> query, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TFastwayAgencyApply> byParam = fastwayAgencyApplyDao.findByParam(query);
        return new PageInfo(byParam);
    }

    @Override
    public TFastwayAgencyApply findById(Integer applyId) {
        TFastwayAgencyApply agencyApply = fastwayAgencyApplyDao.selectById(applyId);
        if (agencyApply != null) {
            agencyApply.setMobile(accountDao.selectById(agencyApply.getAccountId()).getMobile());
        }
        return agencyApply;
    }

    @Override
    public int auctionUpdate(AgencyAuctionDetailVO detailVO, Integer applyId, Integer operatorId) {
        Optional.ofNullable(applyId).orElseThrow(() -> new BusinessException("applyId不能为空"));
        checkParam(detailVO);
        TFastwayAgencyApply agencyApply = selectById(applyId);
        int count = 0;
        if (agencyApply != null && detailVO != null) {
            if (detailVO.getWorkCity() != null) {
                detailVO.setCityId(JSON.toJSONString(detailVO.getWorkCity()));
            }
            agencyApply.setApplyFiled(getJSONObject(FastwayEnum.AgencyType.AUCTION, detailVO));
            BeanUtils.copyProperties(detailVO, agencyApply);
            agencyApply.setOperatorId(operatorId);
            agencyApply.setUpdateTime(new Date());
            agencyApply.setBusinessOperatorId(detailVO.getBusinessOperatorId());
            agencyApply.setOpenAccountOperatorId(detailVO.getOpenAccountOperatorId());
            count += fastwayAgencyApplyDao.updateById(agencyApply);
        }
//        accountBusinessService.updateBusinessInfo(company.getId(), JSONObject.parseObject(JSONObject.toJSONString(detailVO)) , AccountBusinessService.BusinessType.fastway_agency);
        return count;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int auctionVerifyAccess(AgencyAuctionDetailVO detailVO, Integer applyId, Integer operatorId) {
        /**
         *  审核通过  添加企业认证信息和联拍机构资质信息  更新PartyId、current_party_id
         *  t_company_apply_record、t_company、account_company_map
         *  t_agency_apply_record、t_agency
         */
        checkParam(detailVO);

        // 已认证企业用户流程
        TFastwayAgencyApply agencyApply1 = selectById(applyId);
        if (agencyApply1.getPartyId() != null) {
            return processAgencyHasPartyId(detailVO, applyId, operatorId);
        }

        checkCompanyIDCardUnique(detailVO.getLicense());

        /**
         * 保存更新
         */
        auctionUpdate(detailVO, applyId, operatorId);

        /**
         * 更新审核状态
         */
        TFastwayAgencyApply agencyApply
                = updateApplyStatus(applyId, FastwayEnum.DisposeStatusEnum.access, operatorId);

        /**
         * 企业流程处理
         */
        Integer companyId = companyProcess(agencyApply, detailVO, operatorId);

        /**
         * 机构流程处理
         */
        agencyProcess(detailVO, operatorId, agencyApply.getAccountId());

        agencyApply.setPartyId(companyId);
        int count = fastwayAgencyApplyDao.updateById(agencyApply);
        // 发送审核通过短信
        serviceMessageUtils.fastwayAgencyApplyAccessToSms(applyId);
        accountBusinessService.updateBusinessInfo(companyId, JSONObject.parseObject(JSON.toJSONString(detailVO)) , AccountBusinessService.BusinessType.fastway_agency);
        return count;
    }

    @Override
    public int auctionVerifyDeny(String refuseReason, Integer applyId, Integer operatorId) {
        Optional.ofNullable(applyId).orElseThrow(() -> new BusinessException("applyId不能为空"));
        TFastwayAgencyApply agencyApply = selectById(applyId);
        int count =0 ;
        if (agencyApply != null) {
            agencyApply.setOperatorId(operatorId);
            agencyApply.setApplyStatus(FastwayEnum.DisposeStatusEnum.deny.getKey());
            agencyApply.setRefuseReason(refuseReason);
            agencyApply.setOperatorTime(new Date());
            agencyApply.setUpdateTime(new Date());
            count += fastwayAgencyApplyDao.updateById(agencyApply);
            // 发送审核拒绝短信
            serviceMessageUtils.fastwayAgencyApplyDenyToSms(applyId);
        }
        return count;
    }

    @Override
    public Long insertCompanyApplyRecord(TAccount tAccount, Object object, Integer operatorId, String source) {
        AgencyAuctionDetailVO detailVO  = (AgencyAuctionDetailVO) object;
        TCompanyApplyRecord applyRecord = new TCompanyApplyRecord();
        BeanUtils.copyProperties(detailVO, applyRecord);
        applyRecord.setAccountId(tAccount.getId());
        applyRecord.setMobile(tAccount.getMobile());
        applyRecord.setApplySource(PartyEnum.ApplySource.FASTWAY.getKey());
        applyRecord.setStatus(AccountEnum.ApplyStatus.APPROVED.getKey());
        City city = detailVO.getWorkCity();
        if (city.getId() != null) {
            applyRecord.setCityId(city.getId() + "");
        }
        if (city.getProvinceId() != null) {
            applyRecord.setProvinceId(city.getProvinceId() + "");
        }
        if (city.getAreaId() != null) {
            applyRecord.setAreaId(city.getAreaId() + "");
        }
        applyRecord.setOperatorId(operatorId);
//        // 注册城市
//        applyRecord.setRegisterCityId();
        // 营业执照
        applyRecord.setLicense(detailVO.getLicense());
//        // 法人
//        applyRecord.setLegalPerson();
//        // 身份证号
//        applyRecord.setIdCard();
//        // 身份证正面图
//        applyRecord.setIdCardFrontImg();
//        // 身份证反面图
//        applyRecord.setIdCardBackImg();
        if (applyRecord.getDefaultAgencyId() == null) {
            TAgency agency = agencyService.findByAgencyCode(SystemConstant.DEFAULT_AGENCY_CODE);
            if (agency != null) {
                applyRecord.setDefaultAgencyId(agency.getId());
            }
        }
        companyVerifyApplicationService.saveCompanyApplyRecord(applyRecord);
        return applyRecord.getId();
    }

    private void checkParam(AgencyAuctionDetailVO detailVO) {
        if (null != detailVO.getServeBuyerPercent() && detailVO.getServeBuyerPercent() > 100) {
            throw new BusinessException("联拍分润比例不能大于100");
        }
        if (null != detailVO.getServeSellerPercent() && detailVO.getServeSellerPercent() > 100) {
            throw new BusinessException("送拍分润比例不能大于100");
        }
        if (null != detailVO.getServeBuyerPercent()
                && null != detailVO.getServeSellerPercent()
                && (detailVO.getServeSellerPercent() + detailVO.getServeBuyerPercent() > 100)) {
            throw new BusinessException("联拍分润比例与送拍分润比例总和不能大于100");
        }
    }

    private Long insertAgencyApplyRecord(AgencyAuctionDetailVO detailVO, Integer accountId ) {
        TAgencyApplyRecord agencyApplyRecord = new TAgencyApplyRecord();
        BeanUtils.copyProperties(detailVO, agencyApplyRecord);
        agencyApplyRecord.setAccountId(accountId);
        City city = detailVO.getWorkCity();
        if (city.getId() != null) {
            agencyApplyRecord.setCityId(city.getId() + "");
        }
        if (city.getProvinceId() != null) {
            agencyApplyRecord.setProvinceId(city.getProvinceId() + "");
        }
        if (city.getAreaId() != null) {
            agencyApplyRecord.setAreaId(city.getAreaId() + "");
        }
        agencyApplyRecord.setStatus(AgencyEnum.AgencyApplyStatus.PENDING.getKey());
        agencyApplyRecord.setCreateTime(new Date());
        agencyApplyRecord.setUpdateTime(new Date());
        agencyApplyRecord.setLicense(detailVO.getLicense());
        agencyApplyRecord.setQualificationNumber(detailVO.getAuctionApproveNo());
        agencyApplyRecordDao.insert(agencyApplyRecord);
        return agencyApplyRecord.getId();
    }

    private Integer companyProcess(TFastwayAgencyApply agencyApply, AgencyAuctionDetailVO detailVO, Integer operatorId) {
        TAccount accountById = getAccountById(agencyApply.getAccountId());
        Long     aLong       = insertCompanyApplyRecord(accountById, detailVO, operatorId, agencyApply.getSource());
        Integer companyId    = insertParty(SystemConstant.ACCOUNT_COMPANY_TYPE, PartyEnum.Category.AUCTION_COMPANY.getKey());
        insertCompany(aLong, companyId, PartyEnum.Category.AUCTION_COMPANY.getKey(), agencyApply.getBusinessBegin(), agencyApply.getBusinessEnd());
        companyService.addAccountCompanyMap(accountById.getId(), companyId, detailVO.getName());
        if (accountById.getCurrentPartyId() == null) {
            accountById.setCurrentPartyId(companyId);
            accountService.updateById(accountById);
        }
        return companyId;
    }

    private void agencyProcess(AgencyAuctionDetailVO detailVO, Integer operatorId, Integer accountId) {
        Long applyRecordId = insertAgencyApplyRecord(detailVO, accountId);
        AgencyApplyReq.BaseReq req = new AgencyApplyReq.BaseReq();
        req.setId(applyRecordId);
        req.setOperatorId(operatorId);
        agencyService.agencyApplyApprove(req, false);
    }

    private TFastwayAgencyApply updateApplyStatus(Integer applyId, FastwayEnum.DisposeStatusEnum status, Integer operatorId) {
        TFastwayAgencyApply agencyApply = selectById(applyId);
        agencyApply.setOperatorId(operatorId);
        agencyApply.setApplyStatus(status.getKey());
        agencyApply.setOperatorTime(new Date());
        agencyApply.setUpdateTime(new Date());
        fastwayAgencyApplyDao.updateById(agencyApply);
        return agencyApply;
    }

    private JSONObject getJSONObject(String key, Object object) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, object);

        return jsonObject;
    }

    private TFastwayAgencyApply selectById(Integer applyId) {
        return fastwayAgencyApplyDao.selectById(applyId);
    }

    private int processAgencyHasPartyId(AgencyAuctionDetailVO detailVO, Integer applyId, Integer operatorId) {

        /**
         * 保存更新
         */
        auctionUpdate(detailVO, applyId, operatorId);

        /**
         * 更新审核状态
         */
        TFastwayAgencyApply agencyApply
                = updateApplyStatus(applyId, FastwayEnum.DisposeStatusEnum.access, operatorId);

        /**
         * 机构流程处理
         */
        agencyProcess(detailVO, operatorId, agencyApply.getAccountId());

        agencyApply.setPartyId(agencyApply.getPartyId());
        int count = fastwayAgencyApplyDao.updateById(agencyApply);
        // 发送审核通过短信
        serviceMessageUtils.fastwayAgencyApplyAccessToSms(applyId);
        accountBusinessService.updateBusinessInfo(agencyApply.getPartyId(), JSONObject.parseObject(JSON.toJSONString(detailVO)) , AccountBusinessService.BusinessType.fastway_agency);
        return count;
    }


}
