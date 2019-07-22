package com._360pai.core.service.account.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.exception.ExceptionEnumImpl;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.ToolUtil;
import com._360pai.core.aspact.EmailService;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.DisposalEnum;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.common.constants.SmsEmailConfig;
import com._360pai.core.condition.account.TDisposeProviderApplyCondition;
import com._360pai.core.condition.account.TDisposeProviderCondition;
import com._360pai.core.dao.account.*;
import com._360pai.core.dao.assistant.AreaDao;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dao.assistant.ProvinceDao;
import com._360pai.core.dao.assistant.StaffDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.AccountReq;
import com._360pai.core.facade.account.req.DisposeProviderApplyReq;
import com._360pai.core.facade.account.req.DisposeProviderReq;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.account.resp.DisposeProviderApplyResp;
import com._360pai.core.facade.account.resp.DisposeProviderResp;
import com._360pai.core.facade.account.vo.DisposeProvider;
import com._360pai.core.facade.account.vo.DisposeProviderApplyVo;
import com._360pai.core.facade.account.vo.DisposeProviderVo;
import com._360pai.core.model.account.*;
import com._360pai.core.model.assistant.Province;
import com._360pai.core.model.assistant.TSmsEmailConfig;
import com._360pai.core.service.account.*;
import com._360pai.core.service.assistant.CityService;
import com._360pai.core.utils.ReqConvertUtil;
import com._360pai.core.utils.RespConvertUtil;
import com._360pai.core.utils.ServiceMessageUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by RuQ on 2018/8/27 17:03
 */

@Service
public class DisposeServiceImpl implements DisposeService {


    @Autowired
    private TDisposeProviderApplyDao tDisposeProviderApplyDao;
    @Autowired
    private TDisposeProviderDao tDisposeProviderDao;
    @Autowired
    private TAccountDao accountDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountBusinessService accountBusinessService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private TCompanyApplyRecordDao tCompanyApplyRecordDao;
    @Autowired
    private TCompanyDao tCompanyDao;
    @Autowired
    private AcctService acctService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private TUserApplyRecordDao tUserApplyRecordDao;
    @Autowired
    private TUserDao tUserDao;
    @Autowired
    private UserService userService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private ServiceMessageUtils serviceMessageUtils;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private ProvinceDao provinceDao;
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CityService cityService;

    @Override
    public boolean saveDisposeApply(TDisposeProviderApply disposeProviderApply) {
        return tDisposeProviderApplyDao.insert(disposeProviderApply) == 1;
    }

    @Override
    public List<TDisposeProviderApply> getApplyRecordByAccountId(Integer accountId, String status) {
        TDisposeProviderApplyCondition condition = new TDisposeProviderApplyCondition();
        condition.setAccountId(accountId);
        condition.setStatus(status);
        return tDisposeProviderApplyDao.selectList(condition);
    }

    @Override
    public boolean saveDispose(TDisposeProvider disposeProvider) {
        return tDisposeProviderDao.insert(disposeProvider) == 1;
    }

    @Override
    public boolean updateDisposeApply(TDisposeProviderApply disposeProviderApply) {
        return tDisposeProviderApplyDao.updateById(disposeProviderApply) == 1;
    }

    @Override
    public boolean updateDispose(TDisposeProvider disposeProvider) {
        return tDisposeProviderDao.updateById(disposeProvider) == 1;
    }



    @Override
    public TDisposeProvider getDisposeProviderByPartyId(Integer partyId) {
        if (partyId == null ) {
            return null;
        }
        TDisposeProviderCondition condition = new TDisposeProviderCondition();
        condition.setPartyId(partyId);
        List<TDisposeProvider> tDisposeProviders = tDisposeProviderDao.selectList(condition);
        if (CollectionUtils.isNotEmpty(tDisposeProviders)) {
            return tDisposeProviders.get(0);
        }
        return null;
    }

    @Override
    public DisposeProviderApplyResp disposeProviderApply(DisposeProviderApplyReq.CreateReq req) {
        DisposeProviderApplyResp resp = new DisposeProviderApplyResp();
        if (tDisposeProviderApplyDao.hasPendingApply(req.getPartyId())) {
            throw new BusinessException(ApiCallResult.FAILURE, "有等待审核的申请，暂时无法重新申请");
        }
        TDisposeProvider disposeProvider = tDisposeProviderDao.getByPartyId(req.getPartyId());
        if (disposeProvider != null) {
            throw new BusinessException(ApiCallResult.FAILURE, "该账户已经是处置服务商");
        }
        TDisposeProviderApply applyRecord = ReqConvertUtil.convertToDisposeProviderApply(req);
        applyRecord.setContactName(Optional.ofNullable(applyRecord.getContactName()).orElse(getName(applyRecord.getPartyId())));
        if (StringUtils.isNotEmpty(applyRecord.getRegion())) {
            Integer region = Integer.parseInt(applyRecord.getRegion());
            Integer provinceId = cityDao.getProvinceId(region);
            if (provinceId != null) {
                applyRecord.setRegionProvince(provinceId + "");
            }
        }
        if (DisposalEnum.DisposeType.LAWYER.getKey().equals(req.getDisposeType())) {
            applyRecord.setCompanyName(applyRecord.getContactName());
            applyRecord.setCompanyType(DisposalEnum.DisposeType.LAWYER.getValue());
        }
        int result = tDisposeProviderApplyDao.insert(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return resp;
    }

    @Override
    public PageInfoResp<DisposeProviderApplyVo> getDisposeProviderApplyListByPage(DisposeProviderApplyReq.QueryReq req) {
        PageInfoResp<DisposeProviderApplyVo> resp = new PageInfoResp<>();
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(req.getQ())) {
            params.put("q", req.getQ());
        }
        if (StringUtils.isNotBlank(req.getStatus())) {
            params.put("status", req.getStatus());
        }
        PageInfo pageInfo = tDisposeProviderApplyDao.getListByPage(req.getPage(), req.getPerPage(), params, "pa.id desc");
        List<DisposeProviderApplyVo> itemsList = new ArrayList<>();
        List<TDisposeProviderApply> applyRecords = pageInfo.getList();
        for (TDisposeProviderApply applyRecord : applyRecords) {
            try {
                DisposeProviderApplyVo vo = RespConvertUtil.convertToDisposeProviderApplyVo(applyRecord);
                TAccount account = accountDao.selectById(applyRecord.getAccountId());
                vo.setMobile(account.getMobile());
                if (applyRecord.getOperatorId() != null) {
                    vo.setOperator(RespConvertUtil.convertToStaff(staffDao.selectById(applyRecord.getOperatorId())));
                }
                if (StringUtils.isBlank(vo.getCompanyType()) ) {
                    vo.setCompanyType(DisposalEnum.DisposeType.getValueByKey(applyRecord.getDisposeType()));
                }
                vo.setRegisterAddress("--");
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
    public DisposeProviderApplyResp.DetailResp getDisposeProviderApply(DisposeProviderApplyReq.BaseReq req) {
        DisposeProviderApplyResp.DetailResp resp = new DisposeProviderApplyResp.DetailResp();
        TDisposeProviderApply applyRecord;
        if (req.getApplyId() != null) {
            applyRecord = tDisposeProviderApplyDao.selectById(req.getApplyId());
        } else if (req.getPartyId() != null) {
            TDisposeProviderApplyCondition condition = new TDisposeProviderApplyCondition();
            condition.setPartyId(req.getPartyId());
            applyRecord = tDisposeProviderApplyDao.selectFirst(condition);
            if (applyRecord == null) {
                throw new BusinessException(ApiCallResult.FAILURE, "没有申请记录");
            }
        } else {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        if (applyRecord == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        DisposeProviderApplyVo vo = RespConvertUtil.convertToDisposeProviderApplyVo(applyRecord);
        TAccount account = accountDao.selectById(applyRecord.getAccountId());
        vo.setMobile(account.getMobile());
        if (applyRecord.getOperatorId() != null) {
            vo.setOperator(RespConvertUtil.convertToStaff(staffDao.selectById(applyRecord.getOperatorId())));
        }
        if (StringUtils.isBlank(vo.getCompanyType()) ) {
            vo.setCompanyType(DisposalEnum.DisposeType.getValueByKey(applyRecord.getDisposeType()));
        }
        if (vo.getOpenAccountOperatorId() != null) {
            vo.setOpenAccountOperator(staffDao.getName(vo.getOpenAccountOperatorId()));
        }
        if (vo.getBusinessOperatorId() != null) {
            vo.setBusinessOperator(staffDao.getName(vo.getBusinessOperatorId()));
        }
        vo.setRegionName(cityDao.getName(applyRecord.getRegion()));
        vo.setRegionProvinceName(provinceDao.getName(applyRecord.getRegionProvince()));
        vo.setRegionAreaName(areaDao.getName(applyRecord.getRegionArea()));
        resp.setApplyRecord(vo);
        return resp;
    }

    @Transactional
    @Override
    public DisposeProviderApplyResp approveDisposeProviderApply(DisposeProviderApplyReq.BaseReq req) {
        DisposeProviderApplyResp resp = new DisposeProviderApplyResp();
        TDisposeProviderApply applyRecord = tDisposeProviderApplyDao.selectById(req.getApplyId());
        if (applyRecord == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!AccountEnum.ApplyStatus.PENDING.getKey().equals(applyRecord.getStatus())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TDisposeProvider disposeProvider = tDisposeProviderDao.getByPartyId(applyRecord.getPartyId());
        if (disposeProvider != null) {
            throw new BusinessException(ApiCallResult.FAILURE, "该账户已经是处置服务商");
        }
        applyRecord.setOperatorId(req.getOperatorId());
        applyRecord.setStatus(AccountEnum.ApplyStatus.APPROVED.getKey());
        applyRecord.setUpdateTime(new Date());
        applyRecord.setOpenAccountOperatorId(req.getOpenAccountOperatorId());
        applyRecord.setBusinessOperatorId(req.getBusinessOperatorId());
        int result = tDisposeProviderApplyDao.updateById(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        disposeProvider = new TDisposeProvider();
        BeanUtils.copyProperties(applyRecord, disposeProvider);
        disposeProvider.setId(null);
        disposeProvider.setCreateTime(new Date());
        disposeProvider.setUpdateTime(new Date());
        result = tDisposeProviderDao.insert(disposeProvider);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        accountBusinessService.updateBusinessInfo(applyRecord.getPartyId(), JSONObject.parseObject(JSONObject.toJSONString(applyRecord)) , AccountBusinessService.BusinessType.dispose);
        serviceMessageUtils.disposeAdminCreateToSms(disposeProvider.getAccountId());
        return resp;
    }

    @Override
    public DisposeProviderApplyResp rejectDisposeProviderApply(DisposeProviderApplyReq.BaseReq req) {
        DisposeProviderApplyResp resp = new DisposeProviderApplyResp();
        TDisposeProviderApply applyRecord = tDisposeProviderApplyDao.selectById(req.getApplyId());
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
        int result = tDisposeProviderApplyDao.updateById(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return resp;
    }

    @Override
    public PageInfoResp<DisposeProviderVo> getDisposeProviderListByPage(DisposeProviderReq.QueryReq req) {
        PageInfoResp<DisposeProviderVo> resp = new PageInfoResp<>();
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(req.getQ())) {
            params.put("q", req.getQ());
        }
        if (StringUtils.isNotBlank(req.getStatus())) {
            params.put("status", req.getStatus());
        }
        PageInfo pageInfo = tDisposeProviderDao.getListByPage(req.getPage(), req.getPerPage(), params, "p.id desc");
        List<DisposeProviderVo> itemsList = new ArrayList<>();
        List<TDisposeProvider> providers = pageInfo.getList();
        for (TDisposeProvider provider : providers) {
            DisposeProviderVo vo = RespConvertUtil.convertToDisposeProviderVo(provider);
            TAccount account = accountDao.selectById(provider.getAccountId());
            vo.setMobile(account.getMobile());
            if (StringUtils.isBlank(vo.getCompanyType()) ) {
                vo.setCompanyType(DisposalEnum.DisposeType.getValueByKey(vo.getDisposeType()));
            }
            if (StringUtils.isBlank(provider.getCompanyName())) {
                vo.setCompanyName("个人");
            }
            vo.setRegisterAddress("--");
            itemsList.add(vo);
        }
        resp.setList(itemsList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public DisposeProviderResp.DetailResp getDisposeProvider(DisposeProviderReq.BaseReq req) {
        DisposeProviderResp.DetailResp resp = new DisposeProviderResp.DetailResp();
        TDisposeProvider provider;
        if (req.getProviderId() != null) {
            provider = tDisposeProviderDao.selectById(req.getProviderId());
        } else if (req.getPartyId() != null) {
            provider = tDisposeProviderDao.getByPartyId(req.getPartyId());
        } else {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        if (provider == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        DisposeProviderVo vo = RespConvertUtil.convertToDisposeProviderVo(provider);
        TAccount account = accountDao.selectById(provider.getAccountId());
        vo.setMobile(account.getMobile());
        if (StringUtils.isBlank(vo.getCompanyType()) ) {
            vo.setCompanyType(DisposalEnum.DisposeType.getValueByKey(vo.getDisposeType()));
        }
        if (DisposalEnum.DisposeType.LAWYER.getKey().equals(vo.getDisposeType())) {
            if (StringUtils.isBlank(vo.getCompanyName())) {
                vo.setCompanyName(vo.getContactName());
            }
            TDisposeProviderApplyCondition condition = new TDisposeProviderApplyCondition();
            condition.setPartyId(provider.getPartyId());
            List<TDisposeProviderApply> tDisposeProviderApplies = tDisposeProviderApplyDao.selectList(condition);
            if (CollectionUtils.isNotEmpty(tDisposeProviderApplies)) {
                String lawOffice = tDisposeProviderApplies.get(0).getLawOffice();
                vo.setLawOffice(lawOffice);
            }
        }
        vo.setRegisterAddress(Optional.ofNullable(vo.getRegisterAddress()).orElse("--"));
        vo.setDisposeTypeDesc(DisposalEnum.DisposeType.getValueByKey(vo.getDisposeType()));
        if (vo.getOpenAccountOperatorId() != null) {
            vo.setOpenAccountOperator(staffDao.getName(vo.getOpenAccountOperatorId()));
        }
        if (vo.getBusinessOperatorId() != null) {
            vo.setBusinessOperator(staffDao.getName(vo.getBusinessOperatorId()));
        }
        if (DisposalEnum.DisposeType.LAWYER.getKey().equals(provider.getDisposeType())) {
           vo.setUser(RespConvertUtil.convertToUserVo(userService.findUserById(provider.getPartyId())));
        } else {
            vo.setCompany(RespConvertUtil.convertToCompanyVo(companyService.findCompanyById(provider.getPartyId())));
        }
        vo.setRegionName(cityDao.getName(provider.getRegion()));
        vo.setRegionProvinceName(provinceDao.getName(provider.getRegionProvince()));
        vo.setRegionAreaName(areaDao.getName(provider.getRegionArea()));
        resp.setProvider(vo);
        return resp;
    }

    @Override
    public DisposeProviderResp updateDisposeProvider(DisposeProviderReq.UpdateReq req) {
        DisposeProviderResp resp = new DisposeProviderResp();
        TDisposeProvider provider = tDisposeProviderDao.selectById(req.getId());
        if (provider == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        provider = ReqConvertUtil.convertToDisposeProvider(req, provider);
        int result = tDisposeProviderDao.updateById(provider);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        accountBusinessService.updateBusinessInfo(provider.getPartyId(), JSONObject.parseObject(JSONObject.toJSONString(provider)) , AccountBusinessService.BusinessType.dispose);
        return resp;
    }

    @Override
    public TDisposeProviderApply getDisposeProviderApplyByPartyId(Integer partyId) {
        TDisposeProviderApplyCondition condition = new TDisposeProviderApplyCondition();
        condition.setPartyId(partyId);
        List<TDisposeProviderApply> tDisposeProviderApplies = tDisposeProviderApplyDao.selectList(condition);
        if (CollectionUtils.isNotEmpty(tDisposeProviderApplies)) {
            return tDisposeProviderApplies.get(0);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addDisposeProviderNoPartyId(DisposeProviderApplyReq.CreateReq req) {

        TAccount tAccount = accountDao.selectById(req.getAccountId());

        if (req.getDisposeType().equals(DisposalEnum.DisposeType.LAW_OFFICE.getKey())
                || req.getDisposeType().equals(DisposalEnum.DisposeType.EVALUATE_AGENCY.getKey()) ) {
            Optional.ofNullable(req.getCertificateNumber()).ifPresent((idCard) -> checkCompanyIDCardUnique(idCard));
            TCompanyApplyRecord record = new TCompanyApplyRecord();
            BeanUtils.copyProperties(req, record);
            record.setLicense(StringUtils.isBlank(req.getCertificateNumber()) ? null : req.getCertificateNumber());
            record.setMobile(tAccount.getMobile());
            record.setName(req.getCompanyName());
            record.setStatus(AccountEnum.ApplyStatus.APPROVED.getKey());
            record.setApplySource(PartyEnum.ApplySource.ADMIN.getKey());
            record.setCityId(req.getRegion());
            record.setProvinceId(req.getRegionProvince());
            record.setAreaId(req.getRegionArea());
            if (record.getDefaultAgencyId() == null) {
                TAgency agency = agencyService.findByAgencyCode(SystemConstant.DEFAULT_AGENCY_CODE);
                if (agency != null) {
                    record.setDefaultAgencyId(agency.getId());
                }
            }
            tCompanyApplyRecordDao.insert(record);
            // 生成partyId
            Integer companyId = insertParty(SystemConstant.ACCOUNT_COMPANY_TYPE, PartyEnum.Category.DISPOSE_PROVIDER.getKey());
            // 生成tCompany 记录
            TCompany company = new TCompany();
            company.setId(companyId);
            BeanUtils.copyProperties(record, company);
            company.setCategory(PartyEnum.Category.DISPOSE_PROVIDER.getKey());
            tCompanyDao.insert(company);
            // 更新account currentPartyId
            updateAccountCurrentPartyId(tAccount, companyId);
            // 生成companyMap
            companyService.addAccountCompanyMap(tAccount.getId(), companyId, company.getName());
            // 生成TAcct记录
            acctSave(companyId, AccountEnum.AcctType.COMPANY.getKey());
            req.setPartyId(companyId);
        }

        // 如果认证是个人则认证个人信息
        if (req.getDisposeType().equals(DisposalEnum.DisposeType.LAWYER.getKey())) {
            checkUserIDCardUnique(req.getCertificateNumber());
            TUserApplyRecord record = new TUserApplyRecord();
            BeanUtils.copyProperties(req, record);
            record.setMobile(tAccount.getMobile());
            record.setAddress(req.getRegisterAddress());
            record.setStatus(AccountEnum.ApplyStatus.APPROVED.getKey());
            record.setName(req.getCompanyName());
            record.setApplySource(PartyEnum.ApplySource.ADMIN.getKey());
            record.setCityId(req.getRegion());
            record.setCityId(req.getRegion());
            record.setProvinceId(req.getRegionProvince());
            record.setAreaId(req.getRegionArea());
            if (record.getDefaultAgencyId() == null) {
                TAgency agency = agencyService.findByAgencyCode(SystemConstant.DEFAULT_AGENCY_CODE);
                if (agency != null) {
                    record.setDefaultAgencyId(agency.getId());
                }
            }
            tUserApplyRecordDao.insert(record);
            // 生成partyId
            Integer userId = insertParty(SystemConstant.ACCOUNT_USER_TYPE, PartyEnum.Category.DISPOSE_PROVIDER.getKey());
            //生成tUser 记录
            TUser tUser = new TUser();
            tUser.setId(userId);
            BeanUtils.copyProperties(record, tUser);
            tUserDao.insert(tUser);
            // 更新account currentPartyId
            updateAccountCurrentPartyId(tAccount, userId);
            // 生成TAcct记录
            acctSave(userId, AccountEnum.AcctType.USER.getKey());
            req.setPartyId(userId);
        }

        addDisposeProviderHasPartyId(req);

        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addDisposeProviderHasPartyId(DisposeProviderApplyReq.CreateReq req) {
        // 生成处置服务商申请记录、处置服务商记录
        TDisposeProviderApply apply = new TDisposeProviderApply();
        BeanUtils.copyProperties(req, apply);
        apply.setOperatorId(req.getOperatorId());
        apply.setStatus(AccountEnum.ApplyStatus.PENDING.getKey());
        apply.setCompanyType(DisposalEnum.DisposeType.getValueByKey(req.getDisposeType()));
//        apply.setProvideService(getAllProviderService());
        apply.setProvideService(listConvertToString(req.getProvideServices()));
        if (req.getDisposeType().equals(DisposalEnum.DisposeType.EVALUATE_AGENCY.getKey())) {
            apply.setProvideService(DisposalEnum.RequirementType.PINGGU.getKey());
        }
        if (req.getDisposeType().equals(DisposalEnum.DisposeType.LAWYER.getKey())) {
            TUser tUser = tUserDao.selectById(req.getPartyId());
            apply.setContactName(tUser.getName());
            apply.setCompanyName(tUser.getName());
        }

//        else {
//            TCompany company = tCompanyDao.selectById(req.getPartyId());
//            apply.setCompanyName(company.getName());
//        }
        if (StringUtils.isNotEmpty(apply.getRegion())) {
            Integer provinceId = cityDao.getProvinceId(apply.getRegion());
            if (provinceId != null) {
                apply.setRegionProvince(provinceId + "");
            }
        }
        apply.setCreateTime(new Date());
        apply.setUpdateTime(new Date());
        tDisposeProviderApplyDao.insert(apply);
        DisposeProviderApplyReq.BaseReq baseReq = new DisposeProviderApplyReq.BaseReq();
        baseReq.setApplyId(apply.getId());
        baseReq.setOperatorId(req.getOperatorId());
        approveDisposeProviderApply(baseReq);
        return 1;
    }

    @Override
    public PageInfoResp<DisposeProvider> getRecommendDisposeProviderList(AccountReq.QueryReq req) {
        PageInfoResp<DisposeProvider> resp = new PageInfoResp<>();
        PageInfo pageInfo = tDisposeProviderDao.getRecommendList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "t.id desc");
        List<TDisposeProvider> list = pageInfo.getList();
        List<DisposeProvider> itemsList = getDisposeProviders(list);
        resp.setList(itemsList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public List<DisposeProvider> getDisposeProviders(List<TDisposeProvider> list) {
        String customerManager = "";
        String customerManagerMobile = "";
        TSmsEmailConfig config = emailService.configSmsEmailConfig(SmsEmailConfig.Bus_Type_25.getKey());
        if (config != null) {
            customerManager = config.getServiceEmail();
            customerManagerMobile = config.getServicePhone();
        }
        List<DisposeProvider> itemsList = new ArrayList<>();
        for (TDisposeProvider item : list) {
            DisposeProvider vo = getDisposeProvider(item);
            vo.setCustomerManager(customerManager);
            vo.setCustomerManagerMobile(customerManagerMobile);

            itemsList.add(vo);
        }
        return itemsList;
    }

    @Override
    public DisposeProvider getDisposeProvider(TDisposeProvider item) {
        DisposeProvider vo = RespConvertUtil.convertToDisposeProvider(item);
        vo.setContactMobile(ToolUtil.maskContact(item.getContactMobile()));
        if (DisposalEnum.DisposeType.LAW_OFFICE.getKey().equals(item.getDisposeType())) {
            TCompany company = tCompanyDao.selectById(item.getPartyId());
            if (company != null && company.getQualifiedBegin() != null) {
                vo.setWorkYear(DateUtil.getYearMargin(company.getQualifiedBegin()) + "");
            }
            if (company != null) {
                String cityName = cityDao.getName(company.getCityId());
                if (StringUtils.isNotBlank(cityName)) {
                    vo.setBusinessRegion(cityName);
                } else {
                    String provinceName = provinceDao.getName(company.getProvinceId());
                    if (StringUtils.isNotBlank(provinceName)) {
                        vo.setBusinessRegion(provinceName);
                    } else {
                        vo.setBusinessRegion("--");
                    }
                }
                vo.setBusinessRegionDetail(cityService.getCityName(company));
            }
        } else {
            TUser user = tUserDao.selectById(item.getPartyId());
            if (user != null) {
                String cityName = cityDao.getName(user.getCityId());
                if (StringUtils.isNotBlank(cityName)) {
                    vo.setBusinessRegion(cityName);
                } else {
                    String provinceName = provinceDao.getName(user.getProvinceId());
                    if (StringUtils.isNotBlank(provinceName)) {
                        vo.setBusinessRegion(provinceName);
                    } else {
                        vo.setBusinessRegion("--");
                    }
                }
                vo.setBusinessRegionDetail(cityService.getCityName(user));
            }
        }
        if (DisposalEnum.DisposeType.LAW_OFFICE.getKey().equals(item.getDisposeType())) {
            vo.setName(ToolUtil.maskLawOfficeName(item.getCompanyName()));
        } else {
            vo.setName(ToolUtil.maskLawyerName(item.getCompanyName()));
        }
        vo.setLawOffice(ToolUtil.maskLawOfficeName(item.getLawOffice()));
        return vo;
    }

    private String getName(Integer partyId) {
        AccountBaseDto accountBaseByPartyId = accountService.getAccountBaseByPartyId(partyId);
        return accountBaseByPartyId.getName();
    }

    private Integer insertParty(String type, String category) {
        TParty party = new TParty();
        party.setType(type);
        party.setCategory(category);
        partyService.saveParty(party);
        return party.getId();
    }

    private void acctSave(Integer partyId, String type) {
        TAcct acct = new TAcct();
        acct.setPartyId(partyId);
        acct.setType(type);
        acctService.saveTAcct(acct);
    }

    private void updateAccountCurrentPartyId(TAccount tAccount, Integer partyId) {
        if (tAccount.getCurrentPartyId() == null) {
            tAccount.setCurrentPartyId(partyId);
            accountService.updateById(tAccount);
        }
    }

    protected void checkUserIDCardUnique(String idCard) {
        //判断身份证是否被认证过
        TUser param = new TUser();
        param.setCertificateNumber(idCard);
        List<TUser> list = userService.findUser(param);
        if (list != null && !list.isEmpty()) {
            throw new BusinessException(ExceptionEnumImpl.IDCARD_HAS_AUTH);
        }
    }

    protected void checkCompanyIDCardUnique(String idCard) {
        TCompany company = companyService.findCompanyByLicence(idCard);
        if (company != null) {
            throw new BusinessException(ExceptionEnumImpl.COMPANY_HAS_AUTH);
        }
    }

    private static String listConvertToString(List<String> list) {
        if (list != null && !list.isEmpty()) {
            Iterator<String> itr = list.iterator();
            StringBuffer str = new StringBuffer();
            while (itr.hasNext()) {
                str.append(itr.next());
                if (itr.hasNext()) {
                    str.append(",");
                }
            }
            return str.toString();
        }
        return "";
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
}
