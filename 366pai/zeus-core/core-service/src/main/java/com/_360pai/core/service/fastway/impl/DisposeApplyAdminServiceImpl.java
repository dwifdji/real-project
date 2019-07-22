package com._360pai.core.service.fastway.impl;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.exception.ExceptionEnumImpl;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.DisposalEnum;
import com._360pai.core.common.constants.FastwayEnum;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.dao.account.TDisposeProviderApplyDao;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dao.fastway.TFastwayDisposeApplyDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.DisposeProviderApplyReq;
import com._360pai.core.model.account.*;
import com._360pai.core.model.fastway.TFastwayDisposeApply;
import com._360pai.core.service.account.AccountBusinessService;
import com._360pai.core.service.fastway.DisposeApplyAdminService;
import com._360pai.core.service.fastway.FastwayService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author xiaolei
 * @create 2018-11-26 15:19
 */
@Service
public class DisposeApplyAdminServiceImpl extends FastwayService
        implements DisposeApplyAdminService {

    @Autowired
    private TFastwayDisposeApplyDao  fastwayDisposeApplyDao;
    @Autowired
    private TDisposeProviderApplyDao tDisposeProviderApplyDao;
    @Autowired
    private AccountBusinessService   accountBusinessService;
    @Autowired
    private CityDao cityDao;

    @Override
    public PageInfo findByParam(Map<String, Object> query, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TFastwayDisposeApply> byParam = fastwayDisposeApplyDao.findByParam(query);
        return new PageInfo(byParam);
    }

    @Override
    public TFastwayDisposeApply findById(Integer applyId) {
        return fastwayDisposeApplyDao.selectById(applyId);
    }

    @Override
    public int lawyerUpdate(Integer applyId, JSONObject applyFiled, Integer operatorId) {
        TFastwayDisposeApply disposeApply = fastwayDisposeApplyDao.selectById(applyId);
        if (null != applyFiled) {
            JSONObject jsonObject = applyFiled.getJSONObject(FastwayEnum.DisposeType.LAW_OFFICE);
            disposeApply.setApplyFiled(applyFiled);
            disposeApply.setBusinessOperatorId(jsonObject.getInteger("businessOperatorId"));
            disposeApply.setOpenAccountOperatorId(jsonObject.getInteger("openAccountOperatorId"));
        }
        disposeApply.setOperatorId(operatorId);
        disposeApply.setUpdateTime(new Date());
        int count = fastwayDisposeApplyDao.updateById(disposeApply);
        return count;
    }

    @Override
    public int lawOfficeUpdate(Integer applyId, JSONObject applyFiled, Integer operatorId) {
        TFastwayDisposeApply disposeApply = fastwayDisposeApplyDao.selectById(applyId);
        if (null != applyFiled) {
            JSONObject jsonObject = applyFiled.getJSONObject(FastwayEnum.DisposeType.LAW_OFFICE);
            disposeApply.setApplyFiled(applyFiled);
            disposeApply.setBeginDate(jsonObject.getDate("beginDate"));
            disposeApply.setEndDate(jsonObject.getDate("endDate"));
            disposeApply.setRemark(jsonObject.getString("remark"));
            disposeApply.setBusinessOperatorId(jsonObject.getInteger("businessOperatorId"));
            disposeApply.setOpenAccountOperatorId(jsonObject.getInteger("openAccountOperatorId"));
        }
        disposeApply.setOperatorId(operatorId);
        disposeApply.setUpdateTime(new Date());
        int count = fastwayDisposeApplyDao.updateById(disposeApply);
        accountBusinessService.updateBusinessInfo(disposeApply.getPartyId(), JSONObject.parseObject(JSONObject.toJSONString(applyFiled)) , AccountBusinessService.BusinessType.fastway_dispose);
        return count;
    }

    /**
     * 通过后生成判断是律师还是律所，律师则生成个人和处置服务商的申请记录
     * 律所则生成企业和处置服务商的申请记录
     * 更新fastway的partyId
     *
     * t_user_apply_record、t_user
     * t_dispose_provider_apply、t_dispose_provider
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int lawyerVerify(String applyStatus, Integer applyId, Integer operatorId, String refuseReason, Integer openAccountOperatorId, Integer businessOperatorId) {

        int count = 0;
        TFastwayDisposeApply apply = fastwayDisposeApplyDao.selectById(applyId);
        if (applyStatus.equals(FastwayEnum.DisposeStatusEnum.access.getKey())) {
            if (apply.getPartyId() != null) {
                return lawyerApplyHasPartyId(apply, applyId, operatorId, openAccountOperatorId,businessOperatorId);
            }
            else {

                // 查询是否申请过user用户

                TUser userByAccountId = userService.findUserByAccountId(apply.getAccountId());

                if (userByAccountId != null) throw new BusinessException(ExceptionEnumImpl.MOBILE_HAS_AUTH);

                JSONObject applyFiled = apply.getApplyFiled().getJSONObject(FastwayEnum.DisposeType.LAWYER);
                applyFiled.put("openAccountOperatorId", openAccountOperatorId);
                applyFiled.put("businessOperatorId", businessOperatorId);
                // 校验用户身份证号是否已认证
                checkUserIDCardUnique(applyFiled.getString("cardNo"));

                apply.setId(applyId);
                apply.setOperatorId(operatorId);
                apply.setOperatorTime(new Date());
                apply.setUpdateTime(new Date());
                apply.setApplyStatus(FastwayEnum.DisposeStatusEnum.access.getKey());
                apply.setOpenAccountOperatorId(openAccountOperatorId);
                apply.setBusinessOperatorId(businessOperatorId);
                count += fastwayDisposeApplyDao.updateById(apply);
                /**
                 * 状态修改
                 */
                disposeApplyAccess(apply, operatorId);
                /**
                 * 用户业务处理
                 */
                Integer userId = userProcess(apply.getAccountId(), applyFiled, operatorId);
                /**
                 * 处置服务商业务处理
                 */
                disposeProcess(apply, applyFiled, operatorId, userId);

                apply.setPartyId(userId);
                count += fastwayDisposeApplyDao.updateById(apply);
                // 审核通过发送短信
                serviceMessageUtils.fastwayDisposeApplyAccessToSms(applyId);
                accountBusinessService.updateBusinessInfo(userId, applyFiled, AccountBusinessService.BusinessType.fastway_dispose);
            }

        }

        if (applyStatus.equals(FastwayEnum.DisposeStatusEnum.deny.getKey())) {
            disposeApplyDeny(apply, operatorId, refuseReason);
        }
        return count;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int lawOfficeVerify(String applyStatus, Integer applyId, Integer operatorId, String refuseReason, Integer openAccountOperatorId, Integer businessOperatorId) {
        int count = 0;
        TFastwayDisposeApply apply = fastwayDisposeApplyDao.selectById(applyId);

        if (FastwayEnum.DisposeStatusEnum.containKey(applyStatus)) {
            if (applyStatus.equals(FastwayEnum.DisposeStatusEnum.access.getKey())) {
                if (apply.getPartyId() != null) {
                    // 企业已认证
                    return lawOfficeApplyHasPartyId(apply, operatorId, openAccountOperatorId, businessOperatorId);
                } else {
                    /**
                     * 通过后生成判断是律师还是律所，律师则生成个人和处置服务商的申请记录
                     * 律所则生成企业和处置服务商的申请记录
                     */
                    JSONObject applyFiled = apply.getApplyFiled().getJSONObject(FastwayEnum.DisposeType.LAW_OFFICE);
                    applyFiled.put("openAccountOperatorId", openAccountOperatorId);
                    applyFiled.put("businessOperatorId", businessOperatorId);
                    // 校验公司是否已认证
                    checkCompanyIDCardUnique(applyFiled.getString("socialCreditCode"));
                    /**
                     * 状态修改
                     */
                    disposeApplyAccess(apply, operatorId);
                    /**
                     *  企业业务处理
                     */
                    Integer companyId = companyProcess(apply, applyFiled, operatorId);

                    apply.setId(applyId);
                    apply.setOperatorId(operatorId);
                    apply.setOperatorTime(new Date());
                    apply.setApplyStatus(FastwayEnum.DisposeStatusEnum.access.getKey());
                    apply.setOpenAccountOperatorId(openAccountOperatorId);
                    apply.setBusinessOperatorId(businessOperatorId);
                    count = fastwayDisposeApplyDao.updateById(apply);

                    /**
                     * 处置服务商业务处理
                     */
                    disposeProcess(apply, applyFiled, operatorId, companyId);
                    apply.setPartyId(companyId);
                    apply.setUpdateTime(new Date());
                    count += fastwayDisposeApplyDao.updateById(apply);
                    // 审核通过发送短信
                    serviceMessageUtils.fastwayDisposeApplyAccessToSms(applyId);
                    accountBusinessService.updateBusinessInfo(companyId, applyFiled , AccountBusinessService.BusinessType.fastway_dispose);
                }
            }

            if (applyStatus.equals(FastwayEnum.DisposeStatusEnum.deny.getKey())) {
                disposeApplyDeny(apply, operatorId, refuseReason);
            }
            return count;
        }

        return count;
    }

    @Override
    public Long insertCompanyApplyRecord(TAccount tAccount, Object object, Integer operatorId, String source) {
        JSONObject applyFiled = (JSONObject) object;
        TCompanyApplyRecord applyRecord = new TCompanyApplyRecord();
        applyRecord.setAccountId(tAccount.getId());
        applyRecord.setApplySource(PartyEnum.ApplySource.FASTWAY.getKey());
        applyRecord.setMobile(tAccount.getMobile());
        applyRecord.setName(applyFiled.getString("lawOffice"));
        applyRecord.setAddress(applyFiled.getString("workAddress"));
        applyRecord.setStatus(AccountEnum.ApplyStatus.APPROVED.getKey());
        applyRecord.setRegisterAddress(applyFiled.getString("registeredAddress"));
        applyRecord.setCityId(applyFiled.getJSONObject("workCity").getString("id"));
        applyRecord.setProvinceId(applyFiled.getJSONObject("workCity").getString("provinceId"));
        applyRecord.setAreaId(applyFiled.getJSONObject("workCity").getString("areaId"));
        applyRecord.setAuthorizationImg(applyFiled.getString("adminAuthFile"));
        applyRecord.setLicenseImg(applyFiled.getString("businessLicense"));
        applyRecord.setOperatorId(operatorId);
//        // 注册城市
//        applyRecord.setRegisterCityId();
        // 营业执照
        applyRecord.setLicense(applyFiled.getString("socialCreditCode"));
//        // 法人
//        applyRecord.setLegalPerson();
//        // 身份证号
//        applyRecord.setIdCard();
//        // 身份证正面图
//        applyRecord.setIdCardFrontImg();
//        // 身份证反面图
//        applyRecord.setIdCardBackImg();
//        // 开户许可证
//        applyRecord.setAccountLicense();
        applyRecord.setOpenAccountOperatorId(applyFiled.getInteger("openAccountOperatorId"));
        applyRecord.setBusinessOperatorId(applyFiled.getInteger("businessOperatorId"));
        if (applyRecord.getDefaultAgencyId() == null) {
            TAgency agency = agencyService.findByAgencyCode(SystemConstant.DEFAULT_AGENCY_CODE);
            if (agency != null) {
                applyRecord.setDefaultAgencyId(agency.getId());
            }
        }
        companyVerifyApplicationService.saveCompanyApplyRecord(applyRecord);
        return applyRecord.getId();
    }

    private int lawyerApplyHasPartyId(TFastwayDisposeApply apply, Integer applyId, Integer operatorId, Integer openAccountOperatorId, Integer businessOperatorId) {
        int count = 0;
        JSONObject applyFiled = apply.getApplyFiled().getJSONObject(FastwayEnum.DisposeType.LAWYER);
        applyFiled.put("openAccountOperatorId", openAccountOperatorId);
        applyFiled.put("businessOperatorId", businessOperatorId);
        // 校验用户身份证号是否已认证
//        checkUserIDCardUnique(applyFiled.getString("cardNo"));

        apply.setId(applyId);
        apply.setOperatorId(operatorId);
        apply.setOperatorTime(new Date());
        apply.setUpdateTime(new Date());
        apply.setApplyStatus(FastwayEnum.DisposeStatusEnum.access.getKey());
        apply.setOpenAccountOperatorId(openAccountOperatorId);
        apply.setBusinessOperatorId(businessOperatorId);
        count += fastwayDisposeApplyDao.updateById(apply);
        /**
         * 处置服务商业务处理
         */
        disposeProcess(apply, applyFiled, operatorId, apply.getPartyId());

        // 审核通过发送短信
        serviceMessageUtils.fastwayDisposeApplyAccessToSms(applyId);
        accountBusinessService.updateBusinessInfo(apply.getPartyId(), applyFiled, AccountBusinessService.BusinessType.fastway_dispose);
        return count;
    }

    private int lawOfficeApplyHasPartyId(TFastwayDisposeApply apply, Integer operatorId, Integer openAccountOperatorId, Integer businessOperatorId) {
        int count = 0;
        JSONObject applyFiled = apply.getApplyFiled().getJSONObject(FastwayEnum.DisposeType.LAW_OFFICE);
        applyFiled.put("openAccountOperatorId", openAccountOperatorId);
        applyFiled.put("businessOperatorId", businessOperatorId);
        apply.setOperatorId(operatorId);
        apply.setOperatorTime(new Date());
        apply.setApplyStatus(FastwayEnum.DisposeStatusEnum.access.getKey());
        apply.setOpenAccountOperatorId(openAccountOperatorId);
        apply.setBusinessOperatorId(businessOperatorId);
        apply.setUpdateTime(new Date());
        count += fastwayDisposeApplyDao.updateById(apply);

        disposeProcess(apply, applyFiled, operatorId, apply.getPartyId());

        // 审核通过发送短信
        serviceMessageUtils.fastwayDisposeApplyAccessToSms(apply.getId());
        accountBusinessService.updateBusinessInfo(apply.getPartyId(), applyFiled , AccountBusinessService.BusinessType.fastway_dispose);
        return count;
    }

    private void disposeApplyAccess(TFastwayDisposeApply apply, Integer operatorId) {
        apply.setId(apply.getId());
        apply.setOperatorId(operatorId);
        apply.setOperatorTime(new Date());
        apply.setUpdateTime(new Date());
        apply.setApplyStatus(FastwayEnum.DisposeStatusEnum.access.getKey());
        fastwayDisposeApplyDao.updateById(apply);
    }

    private void disposeApplyDeny(TFastwayDisposeApply apply, Integer operatorId, String refuseReason) {
        apply.setId(apply.getId());
        apply.setRefuseReason(refuseReason);
        apply.setOperatorId(operatorId);
        apply.setOperatorTime(new Date());
        apply.setUpdateTime(new Date());
        apply.setApplyStatus(FastwayEnum.DisposeStatusEnum.deny.getKey());
        fastwayDisposeApplyDao.updateById(apply);
        // 审核拒绝发送短信
        serviceMessageUtils.fastwayDisposeApplyDenyToSms(apply.getId());
    }

    private Integer userProcess(Integer accountId, JSONObject detailVO, Integer operatorId) {
        TAccount accountById = getAccountById(accountId);
        Long     userApplyId = insertUserApplyRecord(detailVO, accountById, operatorId);
        Integer  userId      = insertParty(SystemConstant.ACCOUNT_USER_TYPE, "NORMAL_USER");
        insertUser(userApplyId, userId);
        if (accountById.getCurrentPartyId() == null) {
            accountById.setCurrentPartyId(userId);
            accountService.updateById(accountById);
        }
        return userId;
    }

    private void disposeProcess(TFastwayDisposeApply disposeApply, JSONObject applyFiled, Integer operatorId, Integer partyId) {
        Integer disposeApplyId = 0;
        if (disposeApply.getApplyType().equals(FastwayEnum.DisposeType.LAWYER)) {
            disposeApplyId = insertDisposeApply(applyFiled, disposeApply, partyId, operatorId);
        } else if (disposeApply.getApplyType().equals(FastwayEnum.DisposeType.LAW_OFFICE)) {
            disposeApplyId = insertLawOfficeDisposeApply(applyFiled, disposeApply, partyId, operatorId);
        }
        DisposeProviderApplyReq.BaseReq req = new DisposeProviderApplyReq.BaseReq();
        req.setApplyId(disposeApplyId);
        req.setOperatorId(operatorId);
        disposeService.approveDisposeProviderApply(req);
    }

    private Integer companyProcess(TFastwayDisposeApply disposeApply, JSONObject applyFiled, Integer operatorId) {
        TAccount accountById = getAccountById(disposeApply.getAccountId());
        Long     aLong       = insertCompanyApplyRecord(accountById, applyFiled, operatorId, disposeApply.getSource());
        Integer companyId    = insertParty(SystemConstant.ACCOUNT_COMPANY_TYPE, PartyEnum.Category.DISPOSE_PROVIDER.getKey());
        insertCompany(aLong, companyId, PartyEnum.Category.AUCTION_COMPANY.getKey(), disposeApply.getBeginDate(), disposeApply.getEndDate());
        companyService.addAccountCompanyMap(accountById.getId(), companyId, applyFiled.getString("lawOffice"));
        if (accountById.getCurrentPartyId() == null) {
            accountById.setCurrentPartyId(companyId);
            accountService.updateById(accountById);
        }
        return companyId;
    }


    private Integer insertLawOfficeDisposeApply(JSONObject applyFiled, TFastwayDisposeApply apply, Integer companyId, Integer operatorId) {
        TDisposeProviderApply disposeRecord = new TDisposeProviderApply();
        disposeRecord.setAccountId(apply.getAccountId());
        disposeRecord.setPartyId(companyId);
        disposeRecord.setCompanyName(applyFiled.getString("lawOffice"));
        disposeRecord.setCompanyType(DisposalEnum.DisposeType.LAW_OFFICE.getValue());
        disposeRecord.setDisposeType(DisposalEnum.DisposeType.LAW_OFFICE.getKey());
        disposeRecord.setRegisterAddress(applyFiled.getString("registeredAddress"));
        disposeRecord.setContactName(applyFiled.getString("contactName"));
        disposeRecord.setContactMobile(applyFiled.getString("contactMobile"));
        disposeRecord.setQualificationUrl(applyFiled.getString("lawOfficeCredential"));
//        disposeRecord.setWorkYear(new BigDecimal(applyFiled.getString("workedYear")));
        disposeRecord.setIntroduction(applyFiled.getString("introduction"));
//        disposeRecord.setCaseUrl(applyFiled.getString("pastCases"));
        disposeRecord.setProvideService(getAllProviderService());
        disposeRecord.setStatus(AccountEnum.ApplyStatus.PENDING.getKey());
        disposeRecord.setOperatorId(operatorId);
        disposeRecord.setRegion(applyFiled.getJSONObject("workCity").getString("id"));
        disposeRecord.setRegionProvince(applyFiled.getJSONObject("workCity").getString("provinceId"));
        disposeRecord.setRegionArea(applyFiled.getJSONObject("workCity").getString("areaId"));
        //if (StringUtils.isNotEmpty(disposeRecord.getRegion())) {
        //    Integer provinceId = cityDao.getProvinceId(disposeRecord.getRegion());
        //    if (provinceId != null) {
        //        disposeRecord.setRegionProvince(provinceId + "");
        //    }
        //}
        disposeRecord.setCreateTime(new Date());
        disposeRecord.setUpdateTime(new Date());
        disposeRecord.setOpenAccountOperatorId(apply.getOpenAccountOperatorId());
        disposeRecord.setBusinessOperatorId(apply.getBusinessOperatorId());
        tDisposeProviderApplyDao.insert(disposeRecord);
        return disposeRecord.getId();
    }

    private Integer insertDisposeApply(JSONObject applyFiled, TFastwayDisposeApply apply, Integer userId, Integer operatorId) {
        TDisposeProviderApply disposeRecord = new TDisposeProviderApply();
        disposeRecord.setAccountId(apply.getAccountId());
        disposeRecord.setPartyId(userId);
        disposeRecord.setCompanyName(applyFiled.getString("cardName"));
        disposeRecord.setCompanyType(DisposalEnum.DisposeType.LAWYER.getValue());
        disposeRecord.setDisposeType(DisposalEnum.DisposeType.LAWYER.getKey());
        disposeRecord.setRegisterAddress(applyFiled.getString("contactAddress"));
        disposeRecord.setContactName(applyFiled.getString("cardName"));
        disposeRecord.setContactMobile(applyFiled.getString("contactMobile"));
        disposeRecord.setQualificationUrl(applyFiled.getString("lawyerCredential"));
        try {
            disposeRecord.setWorkYear(new BigDecimal(applyFiled.getString("workedYear")));
        } catch (NumberFormatException e) {
            throw new BusinessException("请正确填写从业年限");
        }
        disposeRecord.setIntroduction(applyFiled.getString("introduction"));
        disposeRecord.setCaseUrl(applyFiled.getString("pastCases"));
        disposeRecord.setProvideService(getAllProviderService());
        disposeRecord.setStatus(AccountEnum.ApplyStatus.PENDING.getKey());
        disposeRecord.setOperatorId(operatorId);
        disposeRecord.setRegion(applyFiled.getJSONObject("residentCity").getString("id"));
        disposeRecord.setRegionProvince(applyFiled.getJSONObject("residentCity").getString("provinceId"));
        disposeRecord.setRegionArea(applyFiled.getJSONObject("residentCity").getString("areaId"));
        //if (StringUtils.isNotEmpty(disposeRecord.getRegion())) {
        //    Integer provinceId = cityDao.getProvinceId(disposeRecord.getRegion());
        //    if (provinceId != null) {
        //        disposeRecord.setRegionProvince(provinceId + "");
        //    }
        //}
        disposeRecord.setCreateTime(new Date());
        disposeRecord.setUpdateTime(new Date());
        disposeRecord.setOpenAccountOperatorId(apply.getOpenAccountOperatorId());
        disposeRecord.setBusinessOperatorId(apply.getBusinessOperatorId());
        disposeRecord.setLawOffice(applyFiled.getString("lawOffice"));
        tDisposeProviderApplyDao.insert(disposeRecord);
        return disposeRecord.getId();
    }


    private String getAllProviderService() {
        DisposalEnum.RequirementType[] values = DisposalEnum.RequirementType.values();
        StringBuilder sb = new StringBuilder();
        for (DisposalEnum.RequirementType tmp : values) {
            if (!tmp.equals(DisposalEnum.RequirementType.PINGGU)) {
                sb.append(tmp.getKey());
                sb.append(",");
            }
        }
        return sb.substring(0, sb.length()-1);
    }

    private Long insertUserApplyRecord(JSONObject applyFiled, TAccount tAccount, Integer operatorId) {
        TUserApplyRecord applyRecord = new TUserApplyRecord();
        applyRecord.setMobile(tAccount.getMobile());
        applyRecord.setAccountId(tAccount.getId());
        applyRecord.setApplySource(PartyEnum.ApplySource.FASTWAY.getKey());
        applyRecord.setStatus(AccountEnum.ApplyStatus.APPROVED.getKey());
        applyRecord.setCertificateNumber(applyFiled.getString("cardNo"));
        applyRecord.setCertificateFrontImg(applyFiled.getString("cardImg1"));
        applyRecord.setCertificateBackImg(applyFiled.getString("cardImg2"));
        applyRecord.setName(applyFiled.getString("cardName"));
        applyRecord.setAddress(applyFiled.getString("contactAddress"));
        applyRecord.setCityId(applyFiled.getJSONObject("residentCity").getString("id"));
        applyRecord.setProvinceId(applyFiled.getJSONObject("residentCity").getString("provinceId"));
        applyRecord.setAreaId(applyFiled.getJSONObject("residentCity").getString("areaId"));
        applyRecord.setOperatorId(operatorId);
        applyRecord.setOpenAccountOperatorId(applyFiled.getInteger("openAccountOperatorId"));
        applyRecord.setBusinessOperatorId(applyFiled.getInteger("businessOperatorId"));
        if (applyRecord.getDefaultAgencyId() == null) {
            TAgency agency = agencyService.findByAgencyCode(SystemConstant.DEFAULT_AGENCY_CODE);
            if (agency != null) {
                applyRecord.setDefaultAgencyId(agency.getId());
            }
        }
        int result = userVerifyApplicationService.saveUserApplyRecord(applyRecord);
        return result > 0 ? applyRecord.getId() : result;
    }

}
