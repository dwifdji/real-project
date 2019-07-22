package com._360pai.core.service.fastway.impl;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.FastwayEnum;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.dao.account.TFundProviderApplyDao;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dao.fastway.TFastwayFundApplyDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.FundProviderApplyReq;
import com._360pai.core.facade.assistant.vo.CityVo;
import com._360pai.core.facade.disposal.req.City;
import com._360pai.core.facade.fastway.resp.CompanyFundDetailVO;
import com._360pai.core.facade.fastway.resp.UserFundDetailVO;
import com._360pai.core.facade.fastway.vo.FundBasisVO;
import com._360pai.core.model.account.*;
import com._360pai.core.model.fastway.TFastwayAgencyApply;
import com._360pai.core.model.fastway.TFastwayFundApply;
import com._360pai.core.service.account.AccountBusinessService;
import com._360pai.core.service.fastway.FastwayService;
import com._360pai.core.service.fastway.FundApplyAdminService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author xiaolei
 * @create 2018-12-07 11:10
 */
@Service
public class FundApplyAdminServiceImpl extends FastwayService
        implements FundApplyAdminService {

    @Autowired
    private TFastwayFundApplyDao fundApplyDao;
    @Autowired
    private TFundProviderApplyDao tFundProviderApplyDao;
    @Autowired
    private CityDao cityDao;

    @Override
    public PageInfo findByParam(Map<String, Object> query, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TFastwayAgencyApply> byParam = fundApplyDao.findByParam(query);
        return new PageInfo(byParam);
    }

    @Override
    public TFastwayFundApply findById(Integer applyId) {
        TFastwayFundApply fundApply = fundApplyDao.selectById(applyId);
        if (fundApply != null ) {
            if (fundApply.getApplyType().equals(FastwayEnum.FundType.User)) {
                fundApply.setMobile(accountDao.selectById(fundApply.getAccountId()).getMobile());
            } else if (fundApply.getApplyType().equals(FastwayEnum.FundType.Company)) {
                fundApply.setMobile(fundApply.getApplyFiled().getJSONObject(FastwayEnum.FundType.Company).getString("mobile"));
            }
        }
        return fundApply;
    }


    @Override
    public int fundUpdate(FundBasisVO basisVO, Integer applyId, Integer operatorId) {
        Optional.ofNullable(applyId).orElseThrow(() -> new BusinessException("applyId不能为空"));
        TFastwayFundApply fundApply = selectById(applyId);
        int count = 0;
        if (fundApply != null && basisVO != null) {
            checkParam(basisVO);
            fundApply.setApplyFiled(getJSONObject(fundApply.getApplyType(), basisVO));
            BeanUtils.copyProperties(basisVO, fundApply);
            fundApply.setOperatorId(operatorId);
            fundApply.setUpdateTime(new Date());
            count += fundApplyDao.updateById(fundApply);
//            accountBusinessService.updateBusinessInfo(company.getId(), JSONObject.parseObject(JSONObject.toJSONString(basisVO)) , AccountBusinessService.BusinessType.fastway_fund);
        }
        return count;
    }

    @Override
    public int fundVerifyDeny(String refuseReason, Integer applyId, Integer operatorId) {
        Optional.ofNullable(applyId).orElseThrow(() -> new BusinessException("applyId不能为空"));
        TFastwayFundApply fundApply = selectById(applyId);
        int count =0 ;
        if (fundApply != null) {
            fundApply.setOperatorId(operatorId);
            fundApply.setApplyStatus(FastwayEnum.DisposeStatusEnum.deny.getKey());
            fundApply.setRefuseReason(refuseReason);
            fundApply.setOperatorTime(new Date());
            fundApply.setUpdateTime(new Date());
            count += fundApplyDao.updateById(fundApply);
            // 发送审核拒绝短信
            serviceMessageUtils.fastwayFundApplyDenyToSms(applyId);
        }
        return count;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int userVerifyAccess(FundBasisVO basisVO, Integer applyId, Integer operatorId) {

        UserFundDetailVO detailVO = (UserFundDetailVO) basisVO;

        /**
         * 保存更新
         */
        fundUpdate(basisVO, applyId, operatorId);
        /**
         * 更新审核状态
         */
        TFastwayFundApply fundApply
                = updateApplyStatus(applyId, FastwayEnum.DisposeStatusEnum.access, operatorId);

        if (basisVO == null) {
            detailVO = fundApply.getApplyFiled().getJSONObject(FastwayEnum.FundType.User).toJavaObject(UserFundDetailVO.class);
        }

        Integer userId;
        if (fundApply.getPartyId() != null) {

            userId = fundApply.getPartyId();
        } else {
            /**
             * 用户认证流程处理
             */
            userId = userProcess(detailVO, operatorId, fundApply.getAccountId());
            /**
             * 更新快速通道partyId
             */
            fundApply.setPartyId(userId);
        }

        /**
         * 资金服务商流程处理
         */
        fundProcess(detailVO, operatorId, fundApply.getAccountId(), userId);

        int count = fundApplyDao.updateById(fundApply);
        accountBusinessService.updateBusinessInfo(userId, JSONObject.parseObject(JSONObject.toJSONString(basisVO)) , AccountBusinessService.BusinessType.fastway_fund);
        serviceMessageUtils.fastwayFundApplyAccessToSms(applyId);
        return count;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int companyVerifyAccess(FundBasisVO basisVO, Integer applyId, Integer operatorId) {
        CompanyFundDetailVO detailVO = (CompanyFundDetailVO) basisVO;
        /**
         * 保存更新
         */
        fundUpdate(basisVO, applyId, operatorId);
        /**
         * 更新审核状态
         */
        TFastwayFundApply fundApply
                = updateApplyStatus(applyId, FastwayEnum.DisposeStatusEnum.access, operatorId);

        if (basisVO == null) {
            detailVO = fundApply.getApplyFiled().getJSONObject(FastwayEnum.FundType.Company).toJavaObject(CompanyFundDetailVO.class);
        }

        Integer companyId;
        if (fundApply.getPartyId() != null) {
            companyId = fundApply.getPartyId();
        } else {
            /**
             * 企业认证流程
             */
            companyId = companyProcess(fundApply, detailVO, operatorId);
            /**
             * 更新快速通道partyId
             */
            fundApply.setPartyId(companyId);
        }


        /**
         * 资金服务商流程
         */
        fundProcess(detailVO, operatorId, fundApply.getAccountId(), companyId);

        int count = fundApplyDao.updateById(fundApply);
        accountBusinessService.updateBusinessInfo(companyId, JSONObject.parseObject(JSONObject.toJSONString(basisVO)) , AccountBusinessService.BusinessType.fastway_fund);
        serviceMessageUtils.fastwayFundApplyAccessToSms(applyId);
        return count;
    }

    private Integer companyProcess(TFastwayFundApply fundApply, CompanyFundDetailVO detailVO, Integer operatorId) {
        TAccount accountById = getAccountById(fundApply.getAccountId());
        checkCompanyIDCardUnique(detailVO.getLicense());
        Long     aLong       = insertCompanyApplyRecord(accountById, detailVO, operatorId, fundApply.getSource());
        Integer companyId    = insertParty(SystemConstant.ACCOUNT_COMPANY_TYPE, PartyEnum.Category.FUND_PROVIDER.getKey());
        insertCompany(aLong, companyId, PartyEnum.Category.AUCTION_COMPANY.getKey(), detailVO.getQualifiedBegin(), detailVO.getQualifiedEnd());
        companyService.addAccountCompanyMap(accountById.getId(), companyId, detailVO.getName());
        if (accountById.getCurrentPartyId() == null) {
            accountById.setCurrentPartyId(companyId);
            accountService.updateById(accountById);
        }
        return companyId;
    }

    private void fundProcess(FundBasisVO basisVO, Integer operatorId, Integer accountId, Integer partyId) {
        Integer applyId = insertFundApplyRecord(basisVO, accountId, partyId);
        FundProviderApplyReq.BaseReq req = new FundProviderApplyReq.BaseReq();
        req.setApplyId(applyId);
        req.setOperatorId(operatorId);
        fundService.approveFundProviderApply(req);
    }

    private Integer insertFundApplyRecord(FundBasisVO basisVO, Integer accountId, Integer partyId) {
        TFundProviderApply apply = new TFundProviderApply();
        BeanUtils.copyProperties(basisVO, apply);
        if (basisVO.getAnnuaReturnMin() != null) {
            apply.setAnnuaReturnMin(basisVO.getAnnuaReturnMin().toPlainString());
        }
        if (basisVO.getAnnuaReturnMax() != null) {
            apply.setAnnuaReturnMax(basisVO.getAnnuaReturnMax().toPlainString());
        }
        if (apply.getProviderMinAmount() != null) {
            apply.setProviderMinAmount(apply.getProviderMinAmount().multiply(new BigDecimal(10000)));
        }
        if (apply.getProviderMaxAmount() != null) {
            apply.setProviderMaxAmount(apply.getProviderMaxAmount().multiply(new BigDecimal(10000)));
        }
        if (apply.getSingleMinAmount() != null) {
            apply.setSingleMinAmount(apply.getSingleMinAmount().multiply(new BigDecimal(10000)));
        }
        if (apply.getSingleMaxAmount() != null) {
            apply.setSingleMaxAmount(apply.getSingleMaxAmount().multiply(new BigDecimal(10000)));
        }
        if (basisVO instanceof CompanyFundDetailVO) {
            CompanyFundDetailVO companyFundDetailVO = (CompanyFundDetailVO) basisVO;
            apply.setCompanyName(companyFundDetailVO.getName());
            apply.setCompanyType(companyFundDetailVO.getCompanyType());
            apply.setRegisterAddress(companyFundDetailVO.getRegisterAddress());
            apply.setRegisterCapital(companyFundDetailVO.getRegisterCapital());
            apply.setFundType(FastwayEnum.FundType.Company);
            apply.setReason(companyFundDetailVO.getRemark());
        }
        if (basisVO instanceof UserFundDetailVO) {
            UserFundDetailVO userFundDetailVO = (UserFundDetailVO) basisVO;
            apply.setCompanyName(userFundDetailVO.getName());
            apply.setFundType(FastwayEnum.FundType.User);
            apply.setReason(userFundDetailVO.getRemark());
        }
        apply.setProviderArea(getCityStr(basisVO.getProviderAreas()));
        apply.setStatus(AccountEnum.ApplyStatus.PENDING.getKey());
        apply.setPartyId(partyId);
        apply.setAccountId(accountId);
        apply.setCreateTime(new Date());
        apply.setUpdateTime(new Date());
        tFundProviderApplyDao.insert(apply);
        return apply.getId();
    }

    private static String getCityStr(List<CityVo> cities) {
        return JSON.toJSONString(cities == null ? Collections.EMPTY_LIST : cities);
    }

    private Integer userProcess(UserFundDetailVO detailVO, Integer operatorId, Integer accountId) {
        TAccount accountById = getAccountById(accountId);
        checkUserIDCardUnique(detailVO.getCertificateNumber());
        Long     userApplyId = insertUserApplyRecord(detailVO, accountById, operatorId);
        Integer  userId      = insertParty(SystemConstant.ACCOUNT_USER_TYPE, "NORMAL_USER");
        insertUser(userApplyId, userId);
        if (accountById.getCurrentPartyId() == null) {
            accountById.setCurrentPartyId(userId);
            accountService.updateById(accountById);
        }
        return userId;
    }

    private Long insertUserApplyRecord(UserFundDetailVO detailVO, TAccount tAccount, Integer operatorId) {
        TUserApplyRecord applyRecord = new TUserApplyRecord();
        applyRecord.setMobile(tAccount.getMobile());
        applyRecord.setAccountId(tAccount.getId());
        applyRecord.setApplySource(PartyEnum.ApplySource.FASTWAY.getKey());
        applyRecord.setStatus(AccountEnum.ApplyStatus.APPROVED.getKey());
        applyRecord.setCertificateNumber(detailVO.getCertificateNumber());
        applyRecord.setCertificateFrontImg(detailVO.getCertificateFrontImg());
        applyRecord.setCertificateBackImg(detailVO.getCertificateBackImg());
        applyRecord.setName(detailVO.getName());
        applyRecord.setAddress(detailVO.getAddress());
        City city = detailVO.getCityBean();
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
        applyRecord.setBusinessOperatorId(detailVO.getBusinessOperatorId());
        applyRecord.setOpenAccountOperatorId(detailVO.getOpenAccountOperatorId());
        if (applyRecord.getDefaultAgencyId() == null) {
            TAgency agency = agencyService.findByAgencyCode(SystemConstant.DEFAULT_AGENCY_CODE);
            if (agency != null) {
                applyRecord.setDefaultAgencyId(agency.getId());
            }
        }
        int result = userVerifyApplicationService.saveUserApplyRecord(applyRecord);
        return result > 0 ? applyRecord.getId() : result;
    }

    private TFastwayFundApply updateApplyStatus(Integer applyId, FastwayEnum.DisposeStatusEnum status, Integer operatorId) {
        TFastwayFundApply fundApply = selectById(applyId);
        fundApply.setOperatorId(operatorId);
        fundApply.setApplyStatus(status.getKey());
        fundApply.setOperatorTime(new Date());
        fundApply.setUpdateTime(new Date());
        fundApplyDao.updateById(fundApply);
        return fundApply;
    }

    private JSONObject getJSONObject(String key, Object object) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, object);
        return jsonObject;
    }

    private void checkParam(FundBasisVO detailVO) {

        if (null != detailVO.getProviderMinAmount() && null != detailVO.getProviderMaxAmount()) {
            if (detailVO.getProviderMinAmount().compareTo(detailVO.getProviderMaxAmount()) >= 1
                    || detailVO.getProviderMinAmount().compareTo(BigDecimal.ZERO) <= -1
                    || detailVO.getProviderMaxAmount().compareTo(BigDecimal.ZERO) <= -1) {
                throw new BusinessException("请正确填写配资额度");
            }
        }

        if (null != detailVO.getProviderMinCost() && null != detailVO.getProviderMaxCost()) {
            if (detailVO.getProviderMinCost().compareTo(detailVO.getProviderMaxCost()) >= 1
                    || detailVO.getProviderMinCost().compareTo(BigDecimal.ZERO) <= -1
                    || detailVO.getProviderMaxCost().compareTo(BigDecimal.ZERO) <= -1) {
                throw new BusinessException("请正确填写配资成本");
            }
        }

        if(null != detailVO.getProviderMinMonth() && null != detailVO.getProviderMaxMonth()) {
            if (detailVO.getProviderMinMonth().compareTo(detailVO.getProviderMaxMonth()) >= 1
                    || detailVO.getProviderMinMonth().compareTo(BigDecimal.ZERO) <= -1
                    || detailVO.getProviderMaxMonth().compareTo(BigDecimal.ZERO) <= -1) {
                throw new BusinessException("请正确填写配资期限");
            }
        }
    }

    private TFastwayFundApply selectById(Integer applyId) {
        return fundApplyDao.selectById(applyId);
    }


    @Override
    public Long insertCompanyApplyRecord(TAccount tAccount, Object object, Integer operatorId, String source) {
        CompanyFundDetailVO detailVO  = (CompanyFundDetailVO) object;
        TCompanyApplyRecord applyRecord = new TCompanyApplyRecord();
        BeanUtils.copyProperties(detailVO, applyRecord);
        applyRecord.setAccountId(tAccount.getId());
        applyRecord.setMobile(tAccount.getMobile());
        applyRecord.setApplySource(PartyEnum.ApplySource.FASTWAY.getKey());
        applyRecord.setStatus(AccountEnum.ApplyStatus.APPROVED.getKey());
        City city = detailVO.getCityBean();
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
}
