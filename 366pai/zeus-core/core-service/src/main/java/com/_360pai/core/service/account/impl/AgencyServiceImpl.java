package com._360pai.core.service.account.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.AgencyEnum;
import com._360pai.core.condition.account.TAgencyApplyRecordCondition;
import com._360pai.core.condition.account.TAgencyCondition;
import com._360pai.core.dao.account.*;
import com._360pai.core.dao.assistant.AreaDao;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dao.assistant.ProvinceDao;
import com._360pai.core.dao.assistant.StaffDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.AgencyApplyReq;
import com._360pai.core.facade.account.req.AgencyReq;
import com._360pai.core.facade.account.resp.AgencyApplyResp;
import com._360pai.core.facade.account.resp.AgencyResp;
import com._360pai.core.facade.account.vo.AgencyApplyRecordVo;
import com._360pai.core.facade.account.vo.AgencyVo;
import com._360pai.core.model.account.*;
import com._360pai.core.model.assistant.City;
import com._360pai.core.service.account.AccountBusinessService;
import com._360pai.core.service.account.AgencyService;
import com._360pai.core.service.assistant.SmsHelperService;
import com._360pai.core.utils.ReqConvertUtil;
import com._360pai.core.utils.RespConvertUtil;
import com._360pai.gateway.common.dfftpay.PayResultEnums;
import com._360pai.gateway.controller.req.dfftpay.FQueryBindMemberReq;
import com._360pai.gateway.controller.req.dfftpay.FQueryBindMemberResp;
import com._360pai.gateway.controller.req.fdd.FOpenMemberReq;
import com._360pai.gateway.controller.req.fdd.FOpenMemberResp;
import com._360pai.gateway.facade.DfftPayFacade;
import com._360pai.gateway.facade.FddSignatureFacade;
import com._360pai.gateway.resp.QueryBalanceResp;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author zxiao
 * @Title: AgencyServiceImpl
 * @ProjectName zeus-parent
 * @Description: 机构
 * @date 2018/8/10 10:00
 */
@Slf4j
@Service
public class AgencyServiceImpl implements AgencyService {
    @Autowired
    private TAgencyDao agencyDao;
    @Autowired
    private AgencyPortalDao agencyPortalDao;
    @Autowired
    private TAgencyApplyRecordDao agencyApplyRecordDao;
    @Autowired
    private TAccountDao accountDao;
    @Autowired
    private GatewayMqSender gatewayMqSender;
    @Autowired
    private SystemProperties systemProperties;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private TCompanyDao companyDao;
    @Autowired
    private AgencyChannelAgentDao agencyChannelAgentDao;
    @Reference(version = "1.0.0")
    private FddSignatureFacade fddSignatureFacade;
    @Reference(version = "1.0.0")
    private DfftPayFacade dfftPayFacade;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private SmsHelperService smsHelperService;
    @Autowired
    private TAcctDao acctDao;
    @Autowired
    private AccountBusinessService accountBusinessService;
    @Autowired
    private ProvinceDao provinceDao;
    @Autowired
    private AreaDao areaDao;
    @Resource
    private RedisCachemanager redisCachemanager;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public TAgency findByAgencyId(Integer id) {
        return agencyDao.selectById(id);
    }

    @Override
    public TAgency findByAgencyCode(String code) {
        TAgencyCondition condition = new TAgencyCondition();
        condition.setCode(code);
        return agencyDao.selectFirst(condition);
    }

    @Override
    public TAgency findByLicense(String license) {
        TAgencyCondition condition = new TAgencyCondition();
        condition.setLicense(license);
        return agencyDao.selectFirst(condition);
    }

    @Override
    public TAgency findByMobile(String mobile) {
        TAgencyCondition condition = new TAgencyCondition();
        condition.setMobile(mobile);
        return agencyDao.selectFirst(condition);
    }

    @Override
    public boolean saveAgency(TAgency tAgency) {
        return agencyDao.insert(tAgency) == 1;
    }

    @Override
    public PageInfoResp<AgencyVo> getAgencyListByPage(AgencyReq.QueryReq req) {
        PageInfoResp<AgencyVo> resp = new PageInfoResp<>();
        Map<String, Object> params = (Map<String, Object>) JSON.toJSON(req);
        PageInfo pageInfo = agencyDao.getListByPage(req.getPage(), req.getPerPage(), params, "a.id desc");
        List<AgencyVo> itemsList = new ArrayList<>();
        List<TAgency> agencies = pageInfo.getList();
        for (TAgency agency : agencies) {
            try {
                itemsList.add(processAgency(agency));
            } catch (Exception e) {
                e.printStackTrace();
                gatewayMqSender.sendTryCatchExceptionEmail(agency.getId(), e);
            }
        }
        resp.setList(itemsList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public AgencyResp.DetailResp getAgencyById(AgencyReq.BaseReq req)  {
        AgencyResp.DetailResp resp = new AgencyResp.DetailResp();
        TAgency agency = agencyDao.selectById(req.getAgencyId());
        if (agency == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        resp.setAgency(processAgency(agency));
        return resp;
    }

    private AgencyVo processAgency(TAgency agency) {
        AgencyVo vo = RespConvertUtil.convertToAgencyVo(agency);
        AgencyChannelAgent agencyChannelAgent = agencyChannelAgentDao.getByAgencyId(agency.getId());
        if (agencyChannelAgent != null) {
            TAgency channelAgency = agencyDao.selectById(agencyChannelAgent.getChannelAgentAgencyId());
            if (channelAgency != null) {
                vo.setMyChannelAgentName(channelAgency.getName());
            }
        }
        if (agency.getPayBind() != null && agency.getPayBind().equals(0)) {
            try {
                FQueryBindMemberReq fQueryBindMemberReq = new FQueryBindMemberReq();
                fQueryBindMemberReq.setMemCode(agency.getDfftId());
                fQueryBindMemberReq.setMemName(agency.getName());
                FQueryBindMemberResp fQueryBindMemberResp = dfftPayFacade.queryBindMember(fQueryBindMemberReq);
                if(fQueryBindMemberResp != null && fQueryBindMemberResp.getCode().equals(PayResultEnums.MEM_BOUND.getCode())){
                    agency.setPayBind(1);
                    if (StringUtils.isNotEmpty(agency.getFadadaId())) {
                        agency.setWebsiteStatus(AgencyEnum.AgencyPortalStatus.ONLINE.getKey());
                        AgencyPortal agencyPortal = agencyPortalDao.getByAgencyId(agency.getId());
                        agencyPortal.setStatus(AgencyEnum.AgencyPortalStatus.ONLINE.getKey());
                        agencyPortalDao.updateById(agencyPortal);
                    }
                    agency.setUpdateTime(new Date());
                    agencyDao.updateById(agency);
                }
            } catch (Exception e) {
                e.printStackTrace();
                gatewayMqSender.sendTryCatchExceptionEmail(agency.getId(), e);
            }
        }
        if (StringUtils.isNotBlank(vo.getCityId())) {
            vo.setCityName(cityDao.getName(vo.getCityId()));
        }
        if (StringUtils.isNotBlank(vo.getProvinceId())) {
            vo.setProvinceName(provinceDao.getName(vo.getProvinceId()));
        }
        if (StringUtils.isNotBlank(vo.getAreaId())) {
            vo.setAreaName(areaDao.getName(vo.getAreaId()));
        }
        if (StringUtils.isNotBlank(vo.getRegisterCityId())) {
            vo.setRegisterCityName(cityDao.getName(vo.getRegisterCityId()));
        }
        if (StringUtils.isNotBlank(vo.getRegisterProvinceId())) {
            vo.setRegisterProvinceName(provinceDao.getName(vo.getRegisterProvinceId()));
        }
        if (StringUtils.isNotBlank(vo.getRegisterAreaId())) {
            vo.setRegisterAreaName(areaDao.getName(vo.getRegisterAreaId()));
        }
        if (agency.getOpenAccountOperatorId() != null) {
            vo.setOpenAccountOperator(staffDao.getName(agency.getOpenAccountOperatorId()));
        }
        if (agency.getBusinessOperatorId() != null) {
            vo.setBusinessOperator(staffDao.getName(agency.getBusinessOperatorId()));
        }
        return vo;
    }

    @Override
    public AgencyApplyResp agencyApply(AgencyApplyReq.CreateReq req) {
        AgencyApplyResp resp = new AgencyApplyResp();
        TAccount account = accountDao.selectById(req.getAccountId());
        if (account == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (account.getAgencyId() != null) {
            throw new BusinessException("该账号已经关联机构，不能再申请其他机构");
        }
        if (agencyDao.isExistMobile(account.getMobile())) {
            throw new BusinessException("手机号已经关联机构，申请失败");
        }
        TAgencyApplyRecord applyRecord = ReqConvertUtil.convertToAgencyApplyRecord(req);
        applyRecord.setMobile(account.getMobile());
        int result = agencyApplyRecordDao.insert(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        sendAgencyApplyNotify(account, applyRecord);
        return resp;
    }

    private void sendAgencyApplyNotify(TAccount account, TAgencyApplyRecord applyRecord) {
        try {
            List<String> notifierMobileList = new ArrayList<>();
            notifierMobileList.addAll(Arrays.asList(systemProperties.getPlatformCustomerServicePhone().split(",")));
            notifierMobileList.add(account.getMobile());
            for (String notifierMobile : notifierMobileList) {
                smsHelperService.agencyApplyNotify(notifierMobile, applyRecord.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("机构申请通发送短信失败，applyId={}", applyRecord.getId());
            gatewayMqSender.sendTryCatchExceptionEmail(applyRecord.getId().intValue(), e);
        }
    }

    @Override
    public PageInfoResp<AgencyApplyRecordVo> getAgencyApplyListByPage(AgencyApplyReq.QueryReq req) {
        PageInfoResp<AgencyApplyRecordVo> resp = new PageInfoResp<>();
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(req.getQ())) {
            params.put("q", req.getQ());
        }
        if (req.getStatus() != null) {
            params.put("status", req.getStatus());
        }
        PageInfo pageInfo = agencyApplyRecordDao.getListByPage(req.getPage(), req.getPerPage(), params, "r.id desc");
        List<AgencyApplyRecordVo> itemsList = new ArrayList<>();
        List<TAgencyApplyRecord> applyRecords = pageInfo.getList();
        for (TAgencyApplyRecord applyRecord : applyRecords) {
            try {
                itemsList.add(processAgencyApplyRecord(applyRecord));
            } catch (Exception e) {
                e.printStackTrace();
                gatewayMqSender.sendTryCatchExceptionEmail(applyRecord.getId().intValue(), e);
            }
        }
        resp.setList(itemsList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public AgencyApplyResp.DetailResp getAgencyApplyRecordById(AgencyApplyReq.BaseReq req) {
        AgencyApplyResp.DetailResp resp = new AgencyApplyResp.DetailResp();
        TAgencyApplyRecord applyRecord;
        if (req.getId() != null) {
            applyRecord = agencyApplyRecordDao.selectById(req.getId());
        } else if (req.getAccountId() != null) {
            TAgencyApplyRecordCondition condition = new TAgencyApplyRecordCondition();
            condition.setAccountId(req.getAccountId());
            applyRecord = agencyApplyRecordDao.selectFirst(condition);
            if (applyRecord == null) {
                throw new BusinessException(ApiCallResult.FAILURE, "没有申请记录");
            }
        } else {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        AgencyApplyRecordVo vo = processAgencyApplyRecord(applyRecord);
        if (AccountEnum.ApplyStatus.PENDING.getKey().equals(applyRecord.getStatus())) {
            if (applyRecord.getOpenAccountOperatorId() == null  && applyRecord.getBusinessOperatorId() == null) {
                TCompany company = companyDao.getByLicense(applyRecord.getLicense());
                if (company != null) {
                    vo.setOpenAccountOperator(staffDao.getName(company.getOpenAccountOperatorId()));
                    vo.setOpenAccountOperatorId(company.getOpenAccountOperatorId());
                    vo.setBusinessOperator(staffDao.getName(company.getBusinessOperatorId()));
                    vo.setBusinessOperatorId(company.getBusinessOperatorId());
                }
            }
        }
        resp.setApplyRecord(vo);
        return resp;
    }

    @Override
    public TAgencyApplyRecord getAgencyApplyRecordById(Integer id) {
        return agencyApplyRecordDao.selectById(id);
    }

    @Override
    public AgencyApplyResp agencyApplyUpdate(AgencyApplyReq.UpdateReq req) {
        AgencyApplyResp resp = new AgencyApplyResp();
        TAgencyApplyRecord applyRecord = agencyApplyRecordDao.selectById(req.getId());
        if (applyRecord == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (StringUtils.isNotEmpty(applyRecord.getCode())) {
            req.setCode(null);
        } else {
            if (StringUtils.isNotEmpty(req.getCode())) {
                if (agencyDao.isExistCode(req.getCode())) {
                    throw new BusinessException("该机构代码已被占用");
                }
            }
        }
        applyRecord = ReqConvertUtil.convertToAgencyApplyRecord(req);
        int result = agencyApplyRecordDao.updateById(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        resp.setId(req.getId());
        accountBusinessService.updateBusinessInfo(null, JSONObject.parseObject(JSONObject.toJSONString(applyRecord)) , AccountBusinessService.BusinessType.agency);
        return resp;
    }

    @Transactional
    @Override
    public AgencyApplyResp agencyApplyApprove(AgencyApplyReq.BaseReq req, boolean needSms) {
        AgencyApplyResp resp = new AgencyApplyResp();
        TAgencyApplyRecord applyRecord = agencyApplyRecordDao.selectById(req.getId());
        if (applyRecord == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (StringUtils.isBlank(applyRecord.getLicense())) {
            throw new BusinessException("营业执照号不能为空");
        }
        if (agencyDao.isExistLicense(applyRecord.getLicense())) {
            throw new BusinessException("该营业执照号已被占用");
        }
        if (StringUtils.isBlank(applyRecord.getCode())) {
            throw new BusinessException("机构代码不能为空");
        }
        if (agencyDao.isExistCode(applyRecord.getCode())) {
            throw new BusinessException("该机构代码已被占用");
        }
        if (agencyDao.isExistMobile(applyRecord.getMobile())) {
            throw new BusinessException("手机号已经关联机构，创建失败");
        }
        applyRecord.setStatus(AgencyEnum.AgencyApplyStatus.APPROVED.getKey());
        applyRecord.setOperatorId(req.getOperatorId());
        applyRecord.setUpdateTime(new Date());
        int result = agencyApplyRecordDao.updateById(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        TAgency agency = agencyDao.createFromApply(applyRecord);
        TAcct acct = new TAcct();
        acct.setPartyId(agency.getId());
        acct.setType(AccountEnum.AcctType.AGENCY.getKey());
        result = acctDao.insert(acct);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        TAccount account = accountDao.selectById(applyRecord.getAccountId());
        account.setAgencyId(agency.getId());
        account.setIsAgencyAdmin(1);
        result = accountDao.updateById(account);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        syncSignAndPayInfo(agency, account);
        AgencyPortal agencyPortal = new AgencyPortal();
        agencyPortal.setStatus(AgencyEnum.AgencyPortalStatus.ONLINE.getKey());
        agencyPortal.setAgencyId(agency.getId());
        result = agencyPortalDao.insert(agencyPortal);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        try {
            BoundListOperations boundListOperations = redisTemplate.boundListOps(RedisKeyConstant.AGENCY_LIST);
            if (redisTemplate.hasKey(RedisKeyConstant.AGENCY_LIST)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", agency.getId());
                jsonObject.put("name", agency.getName());
                jsonObject.put("code", agency.getCode());
                jsonObject.put("logoUrl", agency.getLogoUrl());
                jsonObject.put("cityId", agency.getCityId());
                jsonObject.put("provinceId", agency.getProvinceId());
                boundListOperations.rightPush(jsonObject);
                redisTemplate.expireAt(RedisKeyConstant.AGENCY_LIST, DateUtil.getEndDate(new Date()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        accountBusinessService.updateBusinessInfo(null, JSONObject.parseObject(JSONObject.toJSONString(agency)) , AccountBusinessService.BusinessType.agency);
        if (needSms)
            smsHelperService.agencyApplyPassNotify(agency.getMobile(), agency.getName(), agency.getCode());
        return resp;
    }

    private void syncSignAndPayInfo(TAgency agency, TAccount account) {
        try {
            // 根据公司的营业执照号查询公司
            TCompany company = companyDao.getByLicense(agency.getLicense());
            if (company == null) {
                return;
            }
            int result;
            // 关联公司 同步支付信息
            agency.setDfftId(company.getDfftId());
            agency.setPayBind(company.getPayBind());
            // 同步 company 签章信息到 agency
            if (StringUtils.isNotBlank(company.getFadadaId())) {
                agency.setFadadaId(company.getFadadaId());
                agency.setFadadaEmail(company.getFadadaEmail());
                agency.setUpdateTime(new Date());
                result = agencyDao.updateById(agency);
                if (result == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
            } else { // 申请签章并且同步到company
                // 开通法大大
                FOpenMemberReq fOpenMemberReq = new FOpenMemberReq();
                fOpenMemberReq.setCustomer_type("2");
                fOpenMemberReq.setId_card(agency.getLicense());
                fOpenMemberReq.setCustomer_name(agency.getName());
                fOpenMemberReq.setMobile(agency.getMobile());
                fOpenMemberReq.setEmail(agency.getFadadaEmail());
                FOpenMemberResp fOpenMemberResp = fddSignatureFacade.openMember(fOpenMemberReq);
                if (fOpenMemberResp == null || !fOpenMemberResp.getCode().equals("000")) {
                    log.error("机构申请通过，开通法大大失败，agencyId={}，req{}，resp={}", agency.getId(), JSON.toJSONString(fOpenMemberReq), JSON.toJSONString(fOpenMemberResp));
                    throw new BusinessException("开通法大大失败");
                }
                log.info("机构申请通过，开通法大大成功，agencyId={}，req{}，resp={}", agency.getId(), JSON.toJSONString(fOpenMemberReq), JSON.toJSONString(fOpenMemberResp));
                agency.setFadadaId(fOpenMemberResp.getCustomer_id());
                result = agencyDao.updateById(agency);
                if (result == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
                company.setFadadaId(fOpenMemberResp.getCustomer_id());
                company.setFadadaEmail(agency.getFadadaEmail());
                company.setUpdateTime(new Date());
                result = companyDao.updateById(company);
                if (result == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            gatewayMqSender.sendTryCatchExceptionEmail(agency.getId(), e);
        }
    }

    private TCompany getSuitedCompany(Integer accountId) {
        List<TCompany> companyRespList = companyDao.getByAccountId(accountId);
        if (companyRespList != null && !companyRespList.isEmpty()) {
            for (int i = 0; i < companyRespList.size(); i++) {
                if (companyRespList.get(i).getPayBind() != null && companyRespList.get(i).getPayBind() == 1) {
                    return companyRespList.get(i);
                } else if (i == companyRespList.size() - 1) {
                    return companyRespList.get(i);
                }
            }
        }
        return null;
    }

    @Override
    public AgencyApplyResp agencyApplyReject(AgencyApplyReq.BaseReq req) {
        AgencyApplyResp resp = new AgencyApplyResp();
        TAgencyApplyRecord applyRecord = agencyApplyRecordDao.selectById(req.getId());
        if (applyRecord == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        applyRecord.setStatus(AgencyEnum.AgencyApplyStatus.REJECT.getKey());
        applyRecord.setOperatorId(req.getOperatorId());
        applyRecord.setRemark(req.getRemark());
        applyRecord.setUpdateTime(new Date());
        int result = agencyApplyRecordDao.updateById(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return resp;
    }

    @Override
    public AgencyResp updateAgency(AgencyReq.UpdateReq req) {
        AgencyResp resp = new AgencyResp();
        TAgency agency = agencyDao.selectById(req.getId());
        if (agency == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (req.getOpenAccountOperatorId() != null) {
            TCompany company = companyDao.getByLicense(agency.getLicense());
            if (company != null) {
                company.setOpenAccountOperatorId(req.getOpenAccountOperatorId());
                companyDao.updateById(company);
            }
        }
        if (req.getBusinessOperatorId() != null) {
            TCompany company = companyDao.getByLicense(agency.getLicense());
            if (company != null) {
                company.setBusinessOperatorId(req.getBusinessOperatorId());
                companyDao.updateById(company);
            }
        }
        agency = ReqConvertUtil.convertToAgency(req, agency);
        int result = agencyDao.updateById(agency);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        accountBusinessService.updateBusinessInfo(null, JSONObject.parseObject(JSONObject.toJSONString(agency)) , AccountBusinessService.BusinessType.agency);
        return resp;
    }

    @Override
    public AgencyResp updateAgency(AgencyReq.AgencyUpdateReq req) {
        AgencyResp resp = new AgencyResp();
        TAgency agency = agencyDao.selectById(req.getId());
        if (agency == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        agency = ReqConvertUtil.convertToAgency(req);
        int result = agencyDao.updateById(agency);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return resp;
    }

    @Override
    public AgencyResp updateAgency(AgencyReq.UpdateDfftOrFadadaReq req) {
        AgencyResp resp = new AgencyResp();
        TAgency agency = agencyDao.selectById(req.getId());
        if (agency == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (req.getPayBind() != null) {
            agency.setPayBind(req.getPayBind());
        }
        if (StringUtils.isNotEmpty(req.getFadadaId())) {
            agency.setFadadaId(req.getFadadaId());
        }
        agency.setUpdateTime(new Date());
        int result = agencyDao.updateById(agency);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return resp;
    }

    @Override
    public AgencyResp setChannelAgent(AgencyReq.BaseReq req) {
        AgencyResp resp = new AgencyResp();
        TAgency agency = agencyDao.selectById(req.getAgencyId());
        if (agency == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!req.getIsChannelAgent()) {
            List<AgencyChannelAgent> list = agencyChannelAgentDao.getByChannelAgentAgencyId(req.getAgencyId());
            if (list.size() > 0) {
                throw new BusinessException("渠道下有下级机构，无法取消");
            }
        }
        agency.setIsChannelAgent(req.getIsChannelAgent() ? 1 : 0);
        agency.setUpdateTime(new Date());
        int result = agencyDao.updateById(agency);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return resp;
    }

    @Override
    public AgencyResp selectChannelAgent(AgencyReq.BaseReq req) {
        AgencyResp resp = new AgencyResp();
        TAgency agency = agencyDao.selectById(req.getAgencyId());
        if (agency == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TAgency channelAgentAgency = agencyDao.selectById(req.getChannelAgentAgencyId());
        if (channelAgentAgency == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (channelAgentAgency.getIsChannelAgent() == null || !channelAgentAgency.getIsChannelAgent().equals(1)) {
            throw new BusinessException("该机构并非渠道代理商，请先设置该机构为渠道代理商");
        }
        if (agency.getId().equals(channelAgentAgency.getId())) {
            throw new BusinessException("不可以设置自己为自己的代理商");
        }
        AgencyChannelAgent agencyChannelAgent = agencyChannelAgentDao.getByAgencyId(req.getAgencyId());
        int result;
        if (agencyChannelAgent == null) {
            agencyChannelAgent = new AgencyChannelAgent();
            agencyChannelAgent.setAgencyId(req.getAgencyId());
            agencyChannelAgent.setChannelAgentAgencyId(req.getChannelAgentAgencyId());
            result = agencyChannelAgentDao.insert(agencyChannelAgent);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        } else if (!agencyChannelAgent.getChannelAgentAgencyId().equals(channelAgentAgency.getId())) {
            agencyChannelAgent.setChannelAgentAgencyId(channelAgentAgency.getId());
            result = agencyChannelAgentDao.updateById(agencyChannelAgent);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        return resp;
    }

    @Override
    public AgencyResp cancelSelectChannelAgent(AgencyReq.BaseReq req) {
        AgencyResp resp = new AgencyResp();
        TAgency agency = agencyDao.selectById(req.getAgencyId());
        if (agency == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        AgencyChannelAgent agencyChannelAgent = agencyChannelAgentDao.getByAgencyId(req.getAgencyId());
        if (agencyChannelAgent != null) {
            int result = agencyChannelAgentDao.deleteById(agencyChannelAgent.getId());
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        return resp;
    }

    @Override
    public AgencyResp agencyPortalOffline(AgencyReq.BaseReq req) {
        AgencyResp resp = new AgencyResp();
        TAgency agency = agencyDao.selectById(req.getAgencyId());
        if (agency == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        AgencyPortal agencyPortal = agencyPortalDao.getByAgencyId(req.getAgencyId());
        int result;
        if (agencyPortal == null) {
            agencyPortal = new AgencyPortal();
            agencyPortal.setAgencyId(req.getAgencyId());
            agencyPortal.setStatus(AgencyEnum.AgencyPortalStatus.CLOSED.getKey());
            result = agencyPortalDao.insert(agencyPortal);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            agency.setWebsiteStatus(AgencyEnum.AgencyPortalStatus.CLOSED.getKey());
            agency.setUpdateTime(new Date());
            result = agencyDao.updateById(agency);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        } else if (!AgencyEnum.AgencyPortalStatus.CLOSED.equals(agencyPortal.getStatus())) {
            agencyPortal.setStatus(AgencyEnum.AgencyPortalStatus.CLOSED.getKey());
            result = agencyPortalDao.updateById(agencyPortal);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            agency.setWebsiteStatus(AgencyEnum.AgencyPortalStatus.CLOSED.getKey());
            agency.setUpdateTime(new Date());
            result = agencyDao.updateById(agency);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        return resp;
    }

    @Transactional
    @Override
    public AgencyResp agencyPortalOnline(AgencyReq.BaseReq req) {
        AgencyResp resp = new AgencyResp();
        TAgency agency = agencyDao.selectById(req.getAgencyId());
        if (agency == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        AgencyPortal agencyPortal = agencyPortalDao.getByAgencyId(req.getAgencyId());
        int result;
        if (agencyPortal == null) {
            agencyPortal = new AgencyPortal();
            agencyPortal.setAgencyId(req.getAgencyId());
            agencyPortal.setStatus(AgencyEnum.AgencyPortalStatus.ONLINE.getKey());
            result = agencyPortalDao.insert(agencyPortal);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            agency.setWebsiteStatus(AgencyEnum.AgencyPortalStatus.ONLINE.getKey());
            agency.setUpdateTime(new Date());
            result = agencyDao.updateById(agency);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        } else if (!AgencyEnum.AgencyPortalStatus.ONLINE.equals(agencyPortal.getStatus())) {
            agencyPortal.setStatus(AgencyEnum.AgencyPortalStatus.ONLINE.getKey());
            result = agencyPortalDao.updateById(agencyPortal);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            agency.setWebsiteStatus(AgencyEnum.AgencyPortalStatus.ONLINE.getKey());
            agency.setUpdateTime(new Date());
            result = agencyDao.updateById(agency);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        return resp;
    }

    @Override
    public AgencyResp.DfftResp paymentAccountBalance(AgencyReq.BaseReq req) {
        AgencyResp.DfftResp resp = new AgencyResp.DfftResp();
        TAgency agency = agencyDao.selectById(req.getAgencyId());
        if (agency.getPayBind() != null && agency.getPayBind().equals(1)) {
            FQueryBindMemberReq fQueryBindMemberReq = new FQueryBindMemberReq();
            fQueryBindMemberReq.setMemCode(agency.getDfftId());
            QueryBalanceResp queryBalanceResp = dfftPayFacade.queryBalance(fQueryBindMemberReq);
            if (queryBalanceResp == null || !queryBalanceResp.getCode().equals("000000")) {
                throw new BusinessException(ApiCallResult.FAILURE, "查询东方付通余额失败");
            }
            resp.setFreeAmt(queryBalanceResp.getFreeAmt());
            resp.setLockedAmt(queryBalanceResp.getLockedAmt());
            resp.setTotalAmt(queryBalanceResp.getTotalAmt());
        } else {
            resp.setFreeAmt("0.00");
            resp.setLockedAmt("0.00");
            resp.setTotalAmt("0.00");
        }
        TAcct acct = acctDao.getByPartyIdType(req.getAgencyId(),AccountEnum.AcctType.AGENCY.getKey());
        if(acct != null){
            resp.setTotalAmt360(acct.getTotalAmt().setScale(2, BigDecimal.ROUND_HALF_UP)+"");
            resp.setLockedAmt360(acct.getLockAmt().setScale(2,BigDecimal.ROUND_HALF_UP)+"");
            resp.setFreeAmt360(acct.getAvailAmt().setScale(2,BigDecimal.ROUND_HALF_UP)+"");
        }
        return resp;
    }

    @Override
    public PageInfo getPartnerAgencyList(int page, int perPage) {
        PageHelper.startPage(page, perPage);
        List<TAgency> tAgencies = agencyDao.selectAll();
        List<Map> list = new ArrayList<>();
        for (TAgency agency : tAgencies) {
            Map<String, Object> map = new HashMap();
            map.put("id", agency.getId());
            map.put("logoUrl", agency.getLogoUrl());
            map.put("code", agency.getCode());
            list.add(map);
        }
        return new PageInfo<>(list);
    }

    @Override
    public AgencyResp.ProfileResp profile(AgencyReq.BaseReq req) {
        AgencyResp.ProfileResp resp = new AgencyResp.ProfileResp();
        TAccount account = accountDao.selectById(req.getAccountId());
        resp.setAccountId(account.getId());
        resp.setMobile(account.getMobile());
        if (account.getIsAgencyAdmin() != null && account.getIsAgencyAdmin().equals(1)) {
            resp.setIsAgencyAdmin(true);
        } else {
            resp.setIsAgencyAdmin(false);
        }
        if (account.getIsAgencyAdmin() != null && account.getIsAgencyAdmin().equals(1)) {
            resp.setCanCheckReservePrice(true);
        } else {
            resp.setCanCheckReservePrice(account.getCanCheckReservePrice());
        }
        return resp;
    }

    @Override
    public AgencyResp setCanCheckReservePrice(AgencyReq.BaseReq req) {
        AgencyResp resp = new AgencyResp();
        TAccount account = accountDao.selectById(req.getAccountId());
        if (account.getAgencyId() == null || !account.getAgencyId().equals(req.getAgencyId())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        account.setCanCheckReservePrice(req.getCanCheckReservePrice());
        account.setUpdateTime(new Date());
        int result = accountDao.updateById(account);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return resp;
    }


    private AgencyApplyRecordVo processAgencyApplyRecord(TAgencyApplyRecord applyRecord) {
        AgencyApplyRecordVo vo = RespConvertUtil.convertToAgencyApplyRecordVo(applyRecord);
        if (StringUtils.isNotBlank(vo.getCityId())) {
            vo.setCityName(cityDao.getName(vo.getCityId()));
        }
        if (StringUtils.isNotBlank(vo.getProvinceId())) {
            vo.setProvinceName(provinceDao.getName(vo.getProvinceId()));
        }
        if (StringUtils.isNotBlank(vo.getAreaId())) {
            vo.setAreaName(areaDao.getName(vo.getAreaId()));
        }
        if (StringUtils.isNotBlank(vo.getRegisterCityId())) {
            vo.setRegisterCityName(cityDao.getName(vo.getRegisterCityId()));
        }
        if (StringUtils.isNotBlank(vo.getRegisterProvinceId())) {
            vo.setRegisterProvinceName(provinceDao.getName(vo.getRegisterProvinceId()));
        }
        if (StringUtils.isNotBlank(vo.getRegisterAreaId())) {
            vo.setRegisterAreaName(areaDao.getName(vo.getRegisterAreaId()));
        }
        if (applyRecord.getOperatorId() != null) {
            vo.setOperator(RespConvertUtil.convertToStaff(staffDao.selectById(applyRecord.getOperatorId())));
        }
        if (applyRecord.getOpenAccountOperatorId() != null) {
            vo.setOpenAccountOperator(staffDao.getName(applyRecord.getOpenAccountOperatorId()));
        }
        if (applyRecord.getBusinessOperatorId() != null) {
            vo.setBusinessOperator(staffDao.getName(applyRecord.getBusinessOperatorId()));
        }
        return vo;
    }

    @Override
    public List<TAgency> searchAgency(String cityId, String name) {
        TAgencyCondition condition = new TAgencyCondition();
        condition.setCityId(cityId);
        condition.setName(name);
        condition.setWebsiteStatus(AgencyEnum.AgencyPortalStatus.ONLINE.getKey());
        return agencyDao.selectList(condition);
    }

    @Override
    public void updateAgency(Map<String, Object> map) {
        TAgency t = new TAgency();

        t.setId(Integer.valueOf(map.get("id").toString()));
        t.setIsJoint(Integer.valueOf(map.get("isJoint").toString()));

        agencyDao.updateById(t);
    }


    @Override
    public Map<String, Object> getDefaultAgency() {
        Map<String, Object> result = new HashMap<>();
        result.put("serverAgencyId", systemProperties.getAgencyId());
        result.put("serverAgencyName", systemProperties.getAgencyName());
        return result;
    }


    @Override
    public ResponseModel getAgencyList(AgencyReq.BaseReq req) {
        List<Map<String, Object>> itemList = agencyDao.getOnlineList((Map<String, Object>) JSON.toJSON(req));
        Map<String, List<Map<String, Object>>> provinceMap = new LinkedHashMap<>();
        for (Map<String, Object> item : itemList) {
            String provinceName = (String) item.get("provinceName");
            List<Map<String, Object>> list;
            if (provinceMap.containsKey(provinceName)) {
                list = provinceMap.get(provinceName);
                list.add(item);
            } else {
                list = new LinkedList<>();
                list.add(item);
                provinceMap.put(provinceName,list);
            }
        }
        List<JSONObject> letterList = new LinkedList<>();
        List<JSONObject> resultList = new LinkedList<>();

        for (Map.Entry<String, List<Map<String, Object>>> entry : provinceMap.entrySet()) {
            List<Map<String, Object>> subItemList = entry.getValue();
            JSONObject province = new JSONObject();
            province.put("provinceName", subItemList.get(0).get("provinceName"));
            province.put("provinceId", subItemList.get(0).get("provinceId"));
            String provincePinyin = (String) subItemList.get(0).get("provincePinyin");
            province.put("firstLetter", provincePinyin.substring(0,1).toUpperCase());
            Map<String, List<Map<String, Object>>> cityMap = new LinkedHashMap<>();
            for (Map<String, Object> item : subItemList) {
                String cityName = (String) item.get("cityName");
                List<Map<String, Object>> list;
                if (cityMap.containsKey(cityName)) {
                    list = cityMap.get(cityName);
                    list.add(item);
                } else {
                    list = new LinkedList<>();
                    list.add(item);
                    cityMap.put(cityName,list);
                }
            }
            province.put("cityList", getCityList(cityMap));
            resultList.add(province);
        }
        Map<String, List<JSONObject>> letterMap = new LinkedHashMap<>();
        for (JSONObject item : resultList) {
            String firstLetter = item.getString("firstLetter");
            List<JSONObject> list;
            if (letterMap.containsKey(firstLetter)) {
                list = letterMap.get(firstLetter);
                list.add(item);
            } else {
                list = new LinkedList<>();
                list.add(item);
                letterMap.put(firstLetter,list);
            }
        }
        for (Map.Entry<String, List<JSONObject>> entry : letterMap.entrySet()) {
            JSONObject letter = new JSONObject();
            letter.put("letter", entry.getKey());
            letter.put("provinces", entry.getValue());
            letterList.add(letter);
        }
        return ResponseModel.succ(letterList);
    }

    @Override
    public ResponseModel searchAgencyList(AgencyReq.QueryReq req) {
        Map<String, Object> data = new HashMap<>();
        List<JSONObject> resultList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(req.getQ())) {
            params.put("agencyName", req.getQ());
        }
        if (StringUtils.isBlank(req.getQ())) {
            BoundListOperations boundListOperations = redisTemplate.boundListOps(RedisKeyConstant.AGENCY_LIST);
            if (!redisTemplate.hasKey(RedisKeyConstant.AGENCY_LIST)) {
                TAgencyCondition condition = new TAgencyCondition();
                condition.setWebsiteStatus(AgencyEnum.AgencyPortalStatus.ONLINE.getKey());
                condition.setDeleteFlag(false);
                List<TAgency> list = agencyDao.selectList(condition);
                for (TAgency item : list) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", item.getId());
                    jsonObject.put("name", item.getName());
                    jsonObject.put("code", item.getCode());
                    jsonObject.put("logoUrl", item.getLogoUrl());
                    jsonObject.put("cityId", item.getCityId());
                    jsonObject.put("provinceId", item.getProvinceId());
                    resultList.add(jsonObject);
                }
                Collections.shuffle(resultList);
                for (Object o : resultList) {
                    JSONObject jsonObject = (JSONObject) o;
                    boundListOperations.leftPush(jsonObject);
                }
                redisTemplate.expireAt(RedisKeyConstant.AGENCY_LIST, DateUtil.getEndDate(new Date()));
            }
            int start = req.getPerPage() * (req.getPage() - 1); // 因为redis中list元素位置基数是0
            int end = start + req.getPerPage() - 1;
            long total = boundListOperations.size();
            resultList = boundListOperations.range(start, end);
            data.put("list", resultList);
            data.put("total", total);
            data.put("hasNextPage", (total % req.getPerPage() == 0 ? total / req.getPerPage() : (total / req.getPerPage() + 1) ) > req.getPage());
            return ResponseModel.succ(data);
        } else {
            PageInfo<Map<String, Object>> pageInfo = agencyDao.searchOnlineList(req.getPage(), req.getPerPage(), params, "");
            boolean matchRegion = false;
            if (StringUtils.isNotBlank(req.getQ()) && pageInfo.getTotal() == 0) {
                params.remove("agencyName");
                params.put("regionName", req.getQ());
                pageInfo = agencyDao.searchOnlineList(req.getPage(), req.getPerPage(), params, "");
                if (pageInfo.getTotal() > 0) {
                    matchRegion = true;
                }
            }
            for (Map<String, Object> item : pageInfo.getList()) {
                JSONObject province = new JSONObject();
                province.put("id", item.get("id"));
                province.put("name", item.get("name"));
                province.put("code", item.get("code"));
                province.put("logoUrl", item.get("logoUrl"));
                province.put("cityId", item.get("cityId"));
                province.put("provinceId", item.get("provinceId"));
                resultList.add(province);
            }
            data.put("list", resultList);
            data.put("total", pageInfo.getTotal());
            data.put("hasNextPage", pageInfo.isHasNextPage());
            data.put("matchRegion", matchRegion);
            return ResponseModel.succ(data);
        }
    }

    private List<JSONObject> getCityList(Map<String, List<Map<String, Object>>> cityMap) {
        List<JSONObject> resultList = new LinkedList<>();
        for (Map.Entry<String, List<Map<String, Object>>> entry : cityMap.entrySet()) {
            List<Map<String, Object>> subItemList = entry.getValue();
            JSONObject city = new JSONObject();
            city.put("cityId", subItemList.get(0).get("cityId"));
            city.put("cityName", subItemList.get(0).get("cityName"));
            List<JSONObject> agencyList = new LinkedList<>();
            for (Map<String, Object> item : subItemList) {
                JSONObject agency = new JSONObject();
                agency.put("id", item.get("id"));
                agency.put("name", item.get("name"));
                agency.put("code", item.get("code"));
                agency.put("logoUrl", item.get("logoUrl"));
                agency.put("productCount", item.get("productCount"));
                agencyList.add(agency);
            }
            city.put("agencyList", agencyList);
            resultList.add(city);
        }
        return resultList;
    }
}
