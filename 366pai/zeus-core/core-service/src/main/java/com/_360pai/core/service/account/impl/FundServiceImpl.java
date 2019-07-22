package com._360pai.core.service.account.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.FastwayEnum;
import com._360pai.core.condition.account.TFundProviderApplyCondition;
import com._360pai.core.condition.account.TFundProviderCondition;
import com._360pai.core.dao.account.*;
import com._360pai.core.dao.assistant.AreaDao;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dao.assistant.ProvinceDao;
import com._360pai.core.dao.assistant.StaffDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.FundProviderApplyReq;
import com._360pai.core.facade.account.req.FundProviderReq;
import com._360pai.core.facade.account.resp.FundProviderApplyResp;
import com._360pai.core.facade.account.resp.FundProviderResp;
import com._360pai.core.facade.account.vo.FundProviderApplyVo;
import com._360pai.core.facade.account.vo.FundProviderVo;
import com._360pai.core.facade.disposal.req.City;
import com._360pai.core.facade.fastway.resp.CompanyFundDetailVO;
import com._360pai.core.facade.fastway.resp.UserFundDetailVO;
import com._360pai.core.model.account.*;
import com._360pai.core.model.fastway.TFastwayFundApply;
import com._360pai.core.service.account.AccountBusinessService;
import com._360pai.core.service.account.FundService;
import com._360pai.core.utils.ReqConvertUtil;
import com._360pai.core.utils.RespConvertUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by RuQ on 2018/8/27 15:45
 */

@Service
public class FundServiceImpl implements FundService {

    @Autowired
    private TFundProviderApplyDao tFundProviderApplyDao;
    @Autowired
    private TFundProviderDao tFundProviderDao;
    @Autowired
    private TAccountDao accountDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private AccountBusinessService accountBusinessService;
    @Autowired
    private TCompanyDao companyDao;
    @Autowired
    private TUserDao userDao;
    @Autowired
    private TAgencyDao agencyDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private ProvinceDao provinceDao;
    @Autowired
    private AreaDao areaDao;

    @Override
    public boolean saveFundApply(TFundProviderApply fundProviderApply) {
        return tFundProviderApplyDao.insert(fundProviderApply) == 1;
    }

    @Override
    public List<TFundProviderApply> getApplyRecordByAccountId(Integer accountId, String status) {
        TFundProviderApplyCondition condition = new TFundProviderApplyCondition();
        condition.setAccountId(accountId);
        condition.setStatus(status);
        return tFundProviderApplyDao.selectList(condition);
    }

    @Override
    public boolean saveFund(TFundProvider fundProvider) {
        return tFundProviderDao.insert(fundProvider) == 1;
    }

    @Override
    public boolean updateFundApply(TFundProviderApply fundProviderApply) {
        return tFundProviderApplyDao.updateById(fundProviderApply) == 1;
    }

    @Override
    public boolean updateFundApply(TFundProvider fundProvider) {
        return tFundProviderDao.updateById(fundProvider) == 1;
    }

    @Override
    public TFundProvider getFundProviderByAccountId(Integer accountId) {
        TFundProviderCondition condition = new TFundProviderCondition();
        condition.setAccountId(accountId);
        return tFundProviderDao.selectOneResult(condition);
    }

    @Override
    public TFundProvider getFundProviderByPartyId(Integer partyId) {
        TFundProviderCondition condition = new TFundProviderCondition();
        condition.setPartyId(partyId);
        return tFundProviderDao.selectOneResult(condition);
    }

    @Override
    public FundProviderApplyResp fundProviderApply(FundProviderApplyReq.CreateReq req) {
        FundProviderApplyResp resp = new FundProviderApplyResp();
        if (tFundProviderApplyDao.hasPendingApply(req.getPartyId())) {
            throw new BusinessException(ApiCallResult.FAILURE, "有等待审核的申请，暂时无法重新申请");
        }
        TFundProvider fundProvider = tFundProviderDao.getByPartyId(req.getPartyId());
        if (fundProvider != null) {
            throw new BusinessException(ApiCallResult.FAILURE, "该账户已经是资金供应商");
        }
        TFundProviderApply applyRecord = ReqConvertUtil.convertToFundProviderApply(req);
        int result = tFundProviderApplyDao.insert(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return resp;
    }

    @Override
    public PageInfoResp<FundProviderApplyVo> getFundProviderApplyListByPage(FundProviderApplyReq.QueryReq req) {
        PageInfoResp<FundProviderApplyVo> resp = new PageInfoResp<>();
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(req.getQ())) {
            params.put("q", req.getQ());
        }
        if (StringUtils.isNotBlank(req.getStatus())) {
            params.put("status", req.getStatus());
        }
        PageInfo pageInfo = tFundProviderApplyDao.getListByPage(req.getPage(), req.getPerPage(), params, "pa.id desc");
        List<FundProviderApplyVo> itemsList = new ArrayList<>();
        List<TFundProviderApply> applyRecords = pageInfo.getList();
        for (TFundProviderApply applyRecord : applyRecords) {
            try {
                FundProviderApplyVo vo = RespConvertUtil.convertToFundProviderApplyVo(applyRecord);
                TAccount account = accountDao.selectById(applyRecord.getAccountId());
                vo.setMobile(account.getMobile());
                if (applyRecord.getOperatorId() != null) {
                    vo.setOperator(RespConvertUtil.convertToStaff(staffDao.selectById(applyRecord.getOperatorId())));
                }
                itemsList.add(vo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        resp.setList(itemsList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public FundProviderApplyResp.DetailResp getFundProviderApply(FundProviderApplyReq.BaseReq req) {
        FundProviderApplyResp.DetailResp resp = new FundProviderApplyResp.DetailResp();
        TFundProviderApply applyRecord;
        if (req.getApplyId() != null) {
            applyRecord = tFundProviderApplyDao.selectById(req.getApplyId());
        } else if (req.getPartyId() != null) {
            TFundProviderApplyCondition condition = new TFundProviderApplyCondition();
            condition.setPartyId(req.getPartyId());
            applyRecord = tFundProviderApplyDao.selectFirst(condition);
            if (applyRecord == null) {
                throw new BusinessException(ApiCallResult.FAILURE, "没有申请记录");
            }
        } else {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        if (applyRecord == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        FundProviderApplyVo vo = RespConvertUtil.convertToFundProviderApplyVo(applyRecord);
        TAccount account = accountDao.selectById(applyRecord.getAccountId());
        vo.setMobile(account.getMobile());
        if (applyRecord.getOperatorId() != null) {
            vo.setOperator(RespConvertUtil.convertToStaff(staffDao.selectById(applyRecord.getOperatorId())));
        }
        if (applyRecord.getOpenAccountOperatorId() != null) {
            vo.setOpenAccountOperator(staffDao.getName(applyRecord.getOpenAccountOperatorId()));
        }
        if (applyRecord.getBusinessOperatorId() != null) {
            vo.setBusinessOperator(staffDao.getName(applyRecord.getBusinessOperatorId()));
        }
        resp.setApplyRecord(vo);
        return resp;
    }

    @Override
    public FundProviderApplyResp approveFundProviderApply(FundProviderApplyReq.BaseReq req) {
        FundProviderApplyResp resp = new FundProviderApplyResp();
        TFundProviderApply applyRecord = tFundProviderApplyDao.selectById(req.getApplyId());
        if (applyRecord == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!AccountEnum.ApplyStatus.PENDING.getKey().equals(applyRecord.getStatus())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TFundProvider fundProvider = tFundProviderDao.getByPartyId(applyRecord.getPartyId());
        if (fundProvider != null) {
            throw new BusinessException(ApiCallResult.FAILURE, "该账户已经是资金供应商");
        }
        applyRecord.setOperatorId(req.getOperatorId());
        applyRecord.setStatus(AccountEnum.ApplyStatus.APPROVED.getKey());
        applyRecord.setUpdateTime(new Date());
        int result = tFundProviderApplyDao.updateById(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        fundProvider = new TFundProvider();
        BeanUtils.copyProperties(applyRecord, fundProvider);
        fundProvider.setId(null);
        fundProvider.setCreateTime(new Date());
        fundProvider.setUpdateTime(new Date());
        result = tFundProviderDao.insert(fundProvider);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        accountBusinessService.updateBusinessInfo(applyRecord.getPartyId(), JSONObject.parseObject(JSONObject.toJSONString(applyRecord)) , AccountBusinessService.BusinessType.fund);
        return resp;
    }

    @Override
    public void updateFundProviderApply(FundProviderApplyReq.UpdateReq req) {
        TFundProviderApply applyRecord = tFundProviderApplyDao.selectById(req.getApplyId());
        if (applyRecord == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        applyRecord = ReqConvertUtil.convertToFundProviderApply(req);
        int result = tFundProviderApplyDao.updateById(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
    }

    @Override
    public FundProviderApplyResp rejectFundProviderApply(FundProviderApplyReq.BaseReq req) {
        FundProviderApplyResp resp = new FundProviderApplyResp();
        TFundProviderApply applyRecord = tFundProviderApplyDao.selectById(req.getApplyId());
        if (applyRecord == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!AccountEnum.ApplyStatus.PENDING.getKey().equals(applyRecord.getStatus())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        applyRecord.setOperatorId(req.getOperatorId());
        applyRecord.setReason(req.getReason());
        applyRecord.setStatus(AccountEnum.ApplyStatus.REJECT.getKey());
        applyRecord.setUpdateTime(new Date());
        int result = tFundProviderApplyDao.updateById(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return resp;
    }

    @Override
    public PageInfoResp<FundProviderVo> getFundProviderListByPage(FundProviderReq.QueryReq req) {
        PageInfoResp<FundProviderVo> resp = new PageInfoResp<>();
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(req.getQ())) {
            params.put("q", req.getQ());
        }
        if (StringUtils.isNotBlank(req.getStatus())) {
            params.put("status", req.getStatus());
        }
        PageInfo pageInfo = tFundProviderDao.getListByPage(req.getPage(), req.getPerPage(), params, "p.id desc");
        List<FundProviderVo> itemsList = new ArrayList<>();
        List<TFundProvider> providers = pageInfo.getList();
        for (TFundProvider provider : providers) {
            try {
                FundProviderVo vo = RespConvertUtil.convertToFundProviderVo(provider);
                if (FastwayEnum.FundType.Company.equals(provider.getFundType())) {
                    TCompany company = companyDao.selectById(provider.getPartyId());
                    vo.setCompanyName(company.getName());
                } else {
                    TUser user = userDao.selectById(provider.getPartyId());
                    vo.setCompanyName(user.getName());
                }
                TAccount account = accountDao.selectById(provider.getAccountId());
                vo.setMobile(account.getMobile());
                itemsList.add(vo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        resp.setList(itemsList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public FundProviderResp.DetailResp getFundProvider(FundProviderReq.BaseReq req) {
        FundProviderResp.DetailResp resp = new FundProviderResp.DetailResp();
        TFundProvider provider;
        if (req.getProviderId() != null) {
            provider = tFundProviderDao.selectById(req.getProviderId());
        } else if (req.getPartyId() != null) {
            provider = tFundProviderDao.getByPartyId(req.getPartyId());
        } else {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        if (provider == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        FundProviderVo vo = RespConvertUtil.convertToFundProviderVo(provider);
        TAccount account = accountDao.selectById(provider.getAccountId());
        vo.setMobile(account.getMobile());
        if (vo.getOpenAccountOperatorId() != null) {
            vo.setOpenAccountOperator(staffDao.getName(vo.getOpenAccountOperatorId()));
        }
        if (vo.getBusinessOperatorId() != null) {
            vo.setBusinessOperator(staffDao.getName(vo.getBusinessOperatorId()));
        }
        resp.setProvider(vo);
        return resp;
    }

    @Transactional
    @Override
    public FundProviderResp updateFundProvider(FundProviderReq.UpdateReq req) {
        FundProviderResp resp = new FundProviderResp();
        TFundProvider provider = tFundProviderDao.selectById(req.getId());
        if (provider == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TFundProvider updateProvider = ReqConvertUtil.convertToFundProvider(req, provider);
        int result = tFundProviderDao.updateById(updateProvider);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        //if (FastwayEnum.FundType.Company.equals(provider.getFundType())) {
        //    TCompany company = companyDao.selectById(provider.getPartyId());
        //    if (company != null) {
        //        TCompany updateCompany = new TCompany();
        //        updateCompany.setId(company.getId());
        //        updateCompany.setName(req.getName());
        //        //if (StringUtils.isNotEmpty(req.getLicense()) && !company.getLicense().equals(req.getLicense())) {
        //        //    if (companyDao.isExistLicense(req.getLicense())) {
        //        //        throw new BusinessException("营业执照已存在");
        //        //    }
        //        //    updateCompany.setLicense(req.getLicense());
        //        //}
        //        updateCompany.setLicenseImg(req.getLicenseImg());
        //        updateCompany.setQualifiedBegin(req.getQualifiedBegin());
        //        updateCompany.setQualifiedEnd(req.getQualifiedEnd());
        //        updateCompany.setCityId(req.getCityId());
        //        updateCompany.setAddress(req.getAddress());
        //        result = companyDao.updateById(updateCompany);
        //        if (result == 0) {
        //            throw new BusinessException(ApiCallResult.FAILURE);
        //        }
        //    }
        //} else {
        //    TUser user = userDao.selectById(provider.getPartyId());
        //    if (user != null) {
        //        TUser updateUser = new TUser();
        //        updateUser.setId(user.getId());
        //        updateUser.setName(req.getName());
        //        //if (StringUtils.isNotEmpty(req.getCertificateNumber()) && !user.getCertificateNumber().equals(req.getCertificateNumber())) {
        //        //    if (userDao.isExistCertificateNumber(req.getCertificateNumber())) {
        //        //        throw new BusinessException("营业执照已存在");
        //        //    }
        //        //    updateUser.setCertificateNumber(req.getCertificateNumber());
        //        //}
        //        updateUser.setCertificateBackImg(req.getCertificateBackImg());
        //        updateUser.setCertificateFrontImg(req.getCertificateFrontImg());
        //        updateUser.setCityId(req.getCityId());
        //        updateUser.setDefaultAgencyId(req.getDefaultAgencyId());
        //        result = userDao.updateById(updateUser);
        //        if (result == 0) {
        //            throw new BusinessException(ApiCallResult.FAILURE);
        //        }
        //    }
        //}
        provider = tFundProviderDao.selectById(req.getId());
        accountBusinessService.updateBusinessInfo(provider.getPartyId(), JSONObject.parseObject(JSONObject.toJSONString(provider)) , AccountBusinessService.BusinessType.fund);
        return resp;
    }

    @Override
    public ResponseModel getFundProvider(Integer providerId) {
        TFundProvider provider = tFundProviderDao.selectById(providerId);
        if (provider == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (FastwayEnum.FundType.Company.equals(provider.getFundType())) {
            CompanyFundDetailVO data = getCompanyFundProvider(providerId);
            return ResponseModel.succ(data);
        } else {
            UserFundDetailVO data = getUserFundProvider(providerId);
            return ResponseModel.succ(data);
        }

    }

    private CompanyFundDetailVO getCompanyFundProvider(Integer providerId) {
        TFundProvider provider = tFundProviderDao.selectById(providerId);
        if (provider == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        processTFundProvider(provider);
        CompanyFundDetailVO vo = new CompanyFundDetailVO();
        BeanUtils.copyProperties(provider, vo);
        if (StringUtils.isNotEmpty(provider.getAnnuaReturnMin())) {
            vo.setAnnuaReturnMin(new BigDecimal(provider.getAnnuaReturnMin()));
        }
        if (StringUtils.isNotEmpty(provider.getAnnuaReturnMax())) {
            vo.setAnnuaReturnMax(new BigDecimal(provider.getAnnuaReturnMax()));
        }
        vo.setProviderAreas(RespConvertUtil.revertCityStr(provider.getProviderArea()));
        vo.setApplyStatus(AccountEnum.ApplyStatus.APPROVED.getKey());
        vo.setApplyStatusDesc(AccountEnum.ApplyStatus.APPROVED.getValue());
        vo.setName(provider.getCompanyName());
        vo.setRegisterMobile(accountDao.getMobile(provider.getAccountId()));
        if (provider.getOpenAccountOperatorId() != null) {
            vo.setOpenAccountOperator(staffDao.getName(provider.getOpenAccountOperatorId()));
        }
        if (provider.getBusinessOperatorId() != null) {
            vo.setBusinessOperator(staffDao.getName(provider.getBusinessOperatorId()));
        }
        TCompany company = companyDao.selectById(provider.getPartyId());
        vo.setLicense(company.getLicense());
        vo.setLicenseImg(company.getLicenseImg());
        vo.setQualifiedBegin(company.getQualifiedBegin());
        vo.setQualifiedEnd(company.getQualifiedEnd());
        vo.setAuthorizationImg(company.getAuthorizationImg());
        vo.setAddress(company.getAddress());
        vo.setName(company.getName());
//        vo.setRegisterAddress(company.getRegisterAddress());
        City city = new City();
        if (StringUtils.isNotBlank(company.getProvinceId())) {
            city.setProvinceId(company.getProvinceId());
            city.setProvinceName(provinceDao.getName(city.getProvinceId()));
        }
        if (StringUtils.isNotBlank(company.getCityId())) {
            city.setId(company.getCityId());
            city.setName(cityDao.getName(city.getId()));
        }
        if (StringUtils.isNotBlank(company.getAreaId())) {
            city.setAreaId(company.getAreaId());
            city.setAreaName(areaDao.getName(city.getAreaId()));
        }
        vo.setCityBean(city);
        return vo;
    }

    private UserFundDetailVO getUserFundProvider(Integer providerId) {
        TFundProvider provider = tFundProviderDao.selectById(providerId);
        if (provider == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        processTFundProvider(provider);
        UserFundDetailVO vo = new UserFundDetailVO();
        BeanUtils.copyProperties(provider, vo);
        if (StringUtils.isNotEmpty(provider.getAnnuaReturnMin())) {
            vo.setAnnuaReturnMin(new BigDecimal(provider.getAnnuaReturnMin()));
        }
        if (StringUtils.isNotEmpty(provider.getAnnuaReturnMax())) {
            vo.setAnnuaReturnMax(new BigDecimal(provider.getAnnuaReturnMax()));
        }
        vo.setProviderAreas(RespConvertUtil.revertCityStr(provider.getProviderArea()));
        vo.setApplyStatus(AccountEnum.ApplyStatus.APPROVED.getKey());
        vo.setApplyStatusDesc(AccountEnum.ApplyStatus.APPROVED.getValue());
        vo.setName(provider.getCompanyName());
        vo.setMobile(accountDao.getMobile(provider.getAccountId()));
        if (provider.getOpenAccountOperatorId() != null) {
            vo.setOpenAccountOperator(staffDao.getName(provider.getOpenAccountOperatorId()));
        }
        if (provider.getBusinessOperatorId() != null) {
            vo.setBusinessOperator(staffDao.getName(provider.getBusinessOperatorId()));
        }
        TUser user = userDao.selectById(provider.getPartyId());
        vo.setCertificateNumber(user.getCertificateNumber());
        vo.setCertificateFrontImg(user.getCertificateFrontImg());
        vo.setCertificateBackImg(user.getCertificateBackImg());
        vo.setAddress(user.getAddress());
        vo.setMobile(user.getMobile());
        vo.setDefaultAgency(agencyDao.getName(user.getDefaultAgencyId()));
        vo.setDefaultAgencyId(user.getDefaultAgencyId());
        City city = new City();
        if (StringUtils.isNotBlank(user.getProvinceId())) {
            city.setProvinceId(user.getProvinceId());
            city.setProvinceName(provinceDao.getName(city.getProvinceId()));
        }
        if (StringUtils.isNotBlank(user.getCityId())) {
            city.setId(user.getCityId());
            city.setName(cityDao.getName(city.getId()));
        }
        if (StringUtils.isNotBlank(user.getAreaId())) {
            city.setAreaId(user.getAreaId());
            city.setAreaName(areaDao.getName(city.getAreaId()));
        }
        vo.setCityBean(city);
        return vo;
    }

    private void processTFundProvider(TFundProvider model) {
        if (model.getProviderMinAmount() != null) {
            model.setProviderMinAmount(model.getProviderMinAmount().divide(new BigDecimal(10000)));
        }
        if (model.getProviderMaxAmount() != null) {
            model.setProviderMaxAmount(model.getProviderMaxAmount().divide(new BigDecimal(10000)));
        }
        if (model.getSingleMinAmount() != null) {
            model.setSingleMinAmount(model.getSingleMinAmount().divide(new BigDecimal(10000)));
        }
        if (model.getSingleMaxAmount() != null) {
            model.setSingleMaxAmount(model.getSingleMaxAmount().divide(new BigDecimal(10000)));
        }
        if (model.getProviderMinAmount() != null && BigDecimal.ZERO.compareTo(model.getProviderMinAmount()) == 0) {
            model.setProviderMinAmount(null);
        }
        if (model.getProviderMaxAmount() != null && BigDecimal.ZERO.compareTo(model.getProviderMaxAmount()) == 0) {
            model.setProviderMaxAmount(null);
        }
        if (model.getSingleMinAmount() != null && BigDecimal.ZERO.compareTo(model.getSingleMinAmount()) == 0) {
            model.setSingleMinAmount(null);
        }
        if (model.getSingleMaxAmount() != null && BigDecimal.ZERO.compareTo(model.getSingleMaxAmount()) == 0) {
            model.setSingleMaxAmount(null);
        }
        if (model.getProviderMinMonth() != null && BigDecimal.ZERO.compareTo(model.getProviderMinMonth()) == 0) {
            model.setProviderMinMonth(null);
        }
        if (model.getProviderMaxMonth() != null && BigDecimal.ZERO.compareTo(model.getProviderMaxMonth()) == 0) {
            model.setProviderMaxMonth(null);
        }
    }

    @Override
    public ResponseModel getFundProviderApply(Integer applyId) {
        TFundProviderApply apply = tFundProviderApplyDao.selectById(applyId);
        if (apply == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (FastwayEnum.FundType.Company.equals(apply.getFundType())) {
            CompanyFundDetailVO data = getCompanyFundProviderApply(applyId);
            return ResponseModel.succ(data);
        } else {
            UserFundDetailVO data = getUserFundProviderApply(applyId);
            return ResponseModel.succ(data);
        }
    }

    private CompanyFundDetailVO getCompanyFundProviderApply(Integer applyId) {
        TFundProviderApply apply = tFundProviderApplyDao.selectById(applyId);
        if (apply == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        processTFundProviderApply(apply);
        CompanyFundDetailVO vo = new CompanyFundDetailVO();
        BeanUtils.copyProperties(apply, vo);
        if (StringUtils.isNotEmpty(apply.getAnnuaReturnMin())) {
            vo.setAnnuaReturnMin(new BigDecimal(apply.getAnnuaReturnMin()));
        }
        if (StringUtils.isNotEmpty(apply.getAnnuaReturnMax())) {
            vo.setAnnuaReturnMax(new BigDecimal(apply.getAnnuaReturnMax()));
        }
        vo.setProviderAreas(RespConvertUtil.revertCityStr(apply.getProviderArea()));
        vo.setApplyStatus(apply.getStatus());
        vo.setApplyStatusDesc(AccountEnum.ApplyStatus.getValueByKey(apply.getStatus()));
        vo.setName(apply.getCompanyName());
        vo.setRegisterMobile(accountDao.getMobile(apply.getAccountId()));
        if (apply.getOpenAccountOperatorId() != null) {
            vo.setOpenAccountOperator(staffDao.getName(apply.getOpenAccountOperatorId()));
        }
        if (apply.getBusinessOperatorId() != null) {
            vo.setBusinessOperator(staffDao.getName(apply.getBusinessOperatorId()));
        }
        if (apply.getPartyId() != null) {
            TCompany company = companyDao.selectById(apply.getPartyId());
            vo.setLicense(company.getLicense());
            vo.setLicenseImg(company.getLicenseImg());
            vo.setQualifiedBegin(company.getQualifiedBegin());
            vo.setQualifiedEnd(company.getQualifiedEnd());
            vo.setAuthorizationImg(company.getAuthorizationImg());
            vo.setAddress(company.getAddress());
            City city = new City();
            if (StringUtils.isNotBlank(company.getProvinceId())) {
                city.setProvinceId(company.getProvinceId());
                city.setProvinceName(provinceDao.getName(city.getProvinceId()));
            }
            if (StringUtils.isNotBlank(company.getCityId())) {
                city.setId(company.getCityId());
                city.setName(cityDao.getName(city.getId()));
            }
            if (StringUtils.isNotBlank(company.getAreaId())) {
                city.setAreaId(company.getAreaId());
                city.setAreaName(areaDao.getName(city.getAreaId()));
            }
            vo.setCityBean(city);
        }
        return vo;
    }

    private UserFundDetailVO getUserFundProviderApply(Integer applyId) {
        TFundProviderApply apply = tFundProviderApplyDao.selectById(applyId);
        if (apply == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        processTFundProviderApply(apply);
        UserFundDetailVO vo = new UserFundDetailVO();
        BeanUtils.copyProperties(apply, vo);
        if (StringUtils.isNotEmpty(apply.getAnnuaReturnMin())) {
            vo.setAnnuaReturnMin(new BigDecimal(apply.getAnnuaReturnMin()));
        }
        if (StringUtils.isNotEmpty(apply.getAnnuaReturnMax())) {
            vo.setAnnuaReturnMax(new BigDecimal(apply.getAnnuaReturnMax()));
        }
        vo.setProviderAreas(RespConvertUtil.revertCityStr(apply.getProviderArea()));
        vo.setApplyStatus(apply.getStatus());
        vo.setApplyStatusDesc(AccountEnum.ApplyStatus.getValueByKey(apply.getStatus()));
        vo.setName(apply.getCompanyName());
        if (vo.getCityId() != null) {
            vo.setCityBean(JSONObject.parseObject(vo.getCityId(), City.class));
        }
        vo.setMobile(accountDao.getMobile(apply.getAccountId()));
        if (apply.getOpenAccountOperatorId() != null) {
            vo.setOpenAccountOperator(staffDao.getName(apply.getOpenAccountOperatorId()));
        }
        if (apply.getBusinessOperatorId() != null) {
            vo.setBusinessOperator(staffDao.getName(apply.getBusinessOperatorId()));
        }
        if (apply.getPartyId() != null) {
            TUser user = userDao.selectById(apply.getPartyId());
            vo.setCertificateNumber(user.getCertificateNumber());
            vo.setCertificateFrontImg(user.getCertificateFrontImg());
            vo.setCertificateBackImg(user.getCertificateBackImg());
            vo.setAddress(user.getAddress());
            vo.setDefaultAgency(agencyDao.getName(user.getDefaultAgencyId()));
            City city = new City();
            if (StringUtils.isNotBlank(user.getProvinceId())) {
                city.setProvinceId(user.getProvinceId());
                city.setProvinceName(provinceDao.getName(city.getProvinceId()));
            }
            if (StringUtils.isNotBlank(user.getCityId())) {
                city.setId(user.getCityId());
                city.setName(cityDao.getName(city.getId()));
            }
            if (StringUtils.isNotBlank(user.getAreaId())) {
                city.setAreaId(user.getAreaId());
                city.setAreaName(areaDao.getName(city.getAreaId()));
            }
            vo.setCityBean(city);
        }
        return vo;
    }

    private void processTFundProviderApply(TFundProviderApply model) {
        if (model.getProviderMinAmount() != null) {
            model.setProviderMinAmount(model.getProviderMinAmount().divide(new BigDecimal(10000)));
        }
        if (model.getProviderMaxAmount() != null) {
            model.setProviderMaxAmount(model.getProviderMaxAmount().divide(new BigDecimal(10000)));
        }
        if (model.getSingleMinAmount() != null) {
            model.setSingleMinAmount(model.getSingleMinAmount().divide(new BigDecimal(10000)));
        }
        if (model.getSingleMaxAmount() != null) {
            model.setSingleMaxAmount(model.getSingleMaxAmount().divide(new BigDecimal(10000)));
        }
        if (model.getProviderMinAmount() != null && BigDecimal.ZERO.compareTo(model.getProviderMinAmount()) == 0) {
            model.setProviderMinAmount(null);
        }
        if (model.getProviderMaxAmount() != null && BigDecimal.ZERO.compareTo(model.getProviderMaxAmount()) == 0) {
            model.setProviderMaxAmount(null);
        }
        if (model.getSingleMinAmount() != null && BigDecimal.ZERO.compareTo(model.getSingleMinAmount()) == 0) {
            model.setSingleMinAmount(null);
        }
        if (model.getSingleMaxAmount() != null && BigDecimal.ZERO.compareTo(model.getSingleMaxAmount()) == 0) {
            model.setSingleMaxAmount(null);
        }
        if (model.getProviderMinMonth() != null && BigDecimal.ZERO.compareTo(model.getProviderMinMonth()) == 0) {
            model.setProviderMinMonth(null);
        }
        if (model.getProviderMaxMonth() != null && BigDecimal.ZERO.compareTo(model.getProviderMaxMonth()) == 0) {
            model.setProviderMaxMonth(null);
        }
    }
}
