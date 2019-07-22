package com._360pai.core.service.account.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.condition.account.TCompanyApplyRecordCondition;
import com._360pai.core.condition.account.TCompanyCondition;
import com._360pai.core.dao.account.*;
import com._360pai.core.dao.assistant.*;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.AccountReq;
import com._360pai.core.facade.account.req.CompanyReq;
import com._360pai.core.facade.account.resp.CompanyResp;
import com._360pai.core.facade.account.vo.CompanyApplyRecordVo;
import com._360pai.core.facade.account.vo.CompanyMemberVo;
import com._360pai.core.facade.account.vo.CompanyVo;
import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.model.account.*;
import com._360pai.core.model.assistant.Bank;
import com._360pai.core.model.assistant.City;
import com._360pai.core.model.assistant.Staff;
import com._360pai.core.service.account.AccountBusinessService;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.AgencyService;
import com._360pai.core.service.account.CompanyService;
import com._360pai.core.service.assistant.SmsHelperService;
import com._360pai.core.utils.ReqConvertUtil;
import com._360pai.core.utils.RespConvertUtil;
import com._360pai.gateway.common.fddSignature.FddSignatureUtils;
import com._360pai.gateway.controller.req.fdd.FCommResp;
import com._360pai.gateway.controller.req.fdd.FOpenMemberReq;
import com._360pai.gateway.controller.req.fdd.FOpenMemberResp;
import com._360pai.gateway.controller.req.fdd.UpdateMemInfoReq;
import com._360pai.gateway.facade.FddSignatureFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/17 11:01
 */
@Slf4j
@Service
public class CompanyServiceImpl	implements CompanyService{

	@Autowired
	private TCompanyDao companyDao;
	@Autowired
    private TCompanyApplyRecordDao companyApplyRecordDao;
    @Resource
    private AgencyService agencyService;
    @Autowired
    private PartyChannelAgentDao partyChannelAgentDao;
    @Autowired
    private AccountService accountService;
    @Autowired
    private BankDao bankDao;
    @Resource
    private TAccountDao tAccountDao;
    @Reference(version = "1.0.0")
    private FddSignatureFacade fddSignatureFacade;
    @Autowired
    private TPartyDao partyDao;
    @Autowired
    private PartyBlackListActionDao partyBlackListActionDao;
    @Autowired
    private AccountCompanyMapDao accountCompanyMapDao;
    @Autowired
    private TUserDao userDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private SmsHelperService smsHelperService;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private GatewayMqSender mqSender;
    @Autowired
    private TAgencyDao agencyDao;
    @Autowired
    private AccountBusinessService accountBusinessService;
    @Autowired
    private ProvinceDao provinceDao;
    @Autowired
    private AreaDao areaDao;

    @Override
    public TCompany findCompanyById(Integer companyId) {
        return companyDao.selectById(companyId);
    }

    @Override
    public List<TCompany> findCompanyByAccountId(Integer accountId) {
        TCompanyCondition condition = new TCompanyCondition();
        condition.setAccountId(accountId);
        return companyDao.selectList(condition);
    }

    @Override
    public List<TCompany> findCompanyByMobile(String mobile) {
        TCompanyCondition condition = new TCompanyCondition();
        condition.setMobile(mobile);
        return companyDao.selectList(condition);
    }

    @Override
	public int updateCompanyById(TCompany company) {
		return companyDao.updateById(company);
	}


    @Override
    public TCompany findCompanyByLicence(String license) {
        TCompanyCondition condition = new TCompanyCondition();
        condition.setLicense(license);
        return companyDao.selectFirst(condition);
    }


    @Override
    public boolean saveCompany(TCompany company) {
        return companyDao.insert(company) == 1;
    }

    @Override
    public PageInfoResp<CompanyApplyRecordVo> getCompanyApplyRecordListByPage(AccountReq.QueryReq req) {
        PageInfoResp<CompanyApplyRecordVo> resp = new PageInfoResp<>();
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(req.getQ())) {
            params.put("q", req.getQ());
        }
        if (StringUtils.isNotBlank(req.getStatus())) {
            params.put("status", req.getStatus());
        }
        if (StringUtils.isNotBlank(req.getApplySource())) {
            params.put("applySource", req.getApplySource());
        }
        PageInfo pageInfo = companyApplyRecordDao.getListByPage(req.getPage(), req.getPerPage(), params, "r.id desc");
        List<CompanyApplyRecordVo> itemsList = new ArrayList<>();
        List<TCompanyApplyRecord> applyRecords = pageInfo.getList();
        for (TCompanyApplyRecord applyRecord : applyRecords) {
            try {
                itemsList.add(processCompanyApplyRecord(applyRecord));
            } catch (Exception e) {
                e.printStackTrace();
                mqSender.sendTryCatchExceptionEmail(applyRecord.getId().intValue(), e);
            }
        }
        resp.setList(itemsList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public CompanyApplyRecordVo getCompanyApplyRecordById(AccountReq.BaseReq req) {
        TCompanyApplyRecordCondition condition = new TCompanyApplyRecordCondition();
        condition.setId(Long.valueOf(req.getId()));
        TCompanyApplyRecord applyRecord = companyApplyRecordDao.selectFirst(condition);
        if (applyRecord == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        return processCompanyApplyRecord(applyRecord);
    }

    @Override
    public PageInfoResp<CompanyVo> getCompanyListByPage(CompanyReq.QueryReq req) {
        PageInfoResp<CompanyVo> resp = new PageInfoResp<>();
        Map<String, Object> params = (Map<String, Object>) JSON.toJSON(req);
        PageInfo pageInfo = companyDao.getListByPage(req.getPage(), req.getPerPage(), params, "t.id desc");
        List<CompanyVo> itemsList = new ArrayList<>();
        List<TCompany> companies = pageInfo.getList();
        for (TCompany company : companies) {
            try {
                itemsList.add(processCompany(company));
            } catch (Exception e) {
                e.printStackTrace();
                mqSender.sendTryCatchExceptionEmail(company.getId(), e);
            }
        }
        resp.setList(itemsList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public CompanyVo getCompanyById(AccountReq.BaseReq req) {
        TCompany company = companyDao.getById(req.getPartyId());
        if (company == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        return processCompany(company);
    }

    @Override
    public CompanyResp updateCompany(CompanyReq.UpdateReq req) {
        CompanyResp resp = new CompanyResp();
        TCompany company = companyDao.selectById(req.getId());
        if (company == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (req.getOpenAccountOperatorId() != null) {
            TAgency agency = agencyDao.getByLicense(company.getLicense());
            if (agency != null) {
                agency.setOpenAccountOperatorId(req.getOpenAccountOperatorId());
                agencyDao.updateById(agency);
            }
        }
        if (req.getBusinessOperatorId() != null) {
            TAgency agency = agencyDao.getByLicense(company.getLicense());
            if (agency != null) {
                agency.setBusinessOperatorId(req.getBusinessOperatorId());
                agencyDao.updateById(agency);
            }
        }
        if (StringUtils.isNotEmpty(req.getCategory()) && !company.getCategory().equals(req.getCategory())) {
            TParty party = partyDao.selectById(company.getId());
            if (party != null) {
                party.setCategory(req.getCategory());
                partyDao.updateById(party);
            }
        }
        TCompany updateCompany = ReqConvertUtil.convertToCompany(req);
        boolean success = companyDao.updateById(updateCompany) > 0 ? true : false;
        if (!success) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        company = companyDao.selectById(req.getId());
        accountBusinessService.updateBusinessInfo(company.getId(), JSONObject.parseObject(JSONObject.toJSONString(company)) , AccountBusinessService.BusinessType.company);
        return resp;
    }

    @Transactional
    @Override
    public CompanyResp createChannelPayCompany(CompanyReq.CreateChannelPayCompanyReq req) {
        CompanyResp resp = new CompanyResp();
        TCompany company = ReqConvertUtil.convertToCompany(req);
        if (companyDao.isExistLicense(company.getLicense())) {
            throw new BusinessException("公司已经注册");
        }
        if (!tAccountDao.isExistMobile(company.getMobile())) {
            throw new BusinessException("手机号未注册");
        }
        TAccount account = tAccountDao.getByMobile(company.getMobile());
        company.setAccountId(account.getId());
        TParty party = new TParty();
        party.setType(PartyEnum.Type.company.name());
        party.setCategory(company.getCategory());
        int result = partyDao.insert(party);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        company.setId(party.getId());
        company.setFadadaEmail(RandomNumberGenerator.wordGenerator(10) + "@360pai.com");
        result = companyDao.insert(company);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        if (account.getCurrentPartyId() == null) {
            account.setCurrentPartyId(company.getId());
            account.setUpdateTime(new Date());
            result = tAccountDao.updateById(account);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        AccountCompanyMap accountCompanyMap = new AccountCompanyMap();
        accountCompanyMap.setName(company.getName());
        accountCompanyMap.setCompanyId(company.getId());
        accountCompanyMap.setAccountId(account.getId());
        result = accountCompanyMapDao.insert(accountCompanyMap);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        openFadada(company);
        resp.setId(company.getId());
        return resp;
    }

    private void openFadada(TCompany company) {
        try {
            // 开通法大大
            FOpenMemberReq fOpenMemberReq = new FOpenMemberReq();
            fOpenMemberReq.setCustomer_type("2");
            fOpenMemberReq.setId_card(company.getLicense());
            fOpenMemberReq.setCustomer_name(company.getName());
            fOpenMemberReq.setMobile(company.getMobile());
            fOpenMemberReq.setEmail(company.getFadadaEmail());
            FOpenMemberResp fOpenMemberResp = fddSignatureFacade.openMember(fOpenMemberReq);
            if (fOpenMemberResp == null || !fOpenMemberResp.getCode().equals("000")) {
                log.error("开通法大大失败，入参={}，出参={}", JSON.toJSONString(fOpenMemberReq), JSON.toJSONString(fOpenMemberResp));
            } else {
                company.setFadadaId(fOpenMemberResp.getCustomer_id());
                int result = companyDao.updateById(company);
                if (result == 0) {
                    log.error("更新法大大信息失败，companyId={}，fadadaId={}", company.getId(), fOpenMemberResp.getCustomer_id());
                }
                smsHelperService.openElectronicSignatureNotify(company.getMobile());
            }
        } catch (Exception e) {
            e.printStackTrace();
            mqSender.sendTryCatchExceptionEmail(company.getId(), e);
        }
    }

    @Override
    public CompanyResp companySetChannelPay(CompanyReq.BaseReq req) {
        CompanyResp resp = new CompanyResp();
        TCompany company = companyDao.selectById(req.getPartyId());
        if (company == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        //if (company.getPayBind().equals(1)) {
        //    throw new BusinessException(ApiCallResult.FAILURE, "公司已经绑定了东方付通，不能再支持通道支付");
        //}
        company.setChannelPay(1);
        company.setUpdateTime(new Date());
        int result = companyDao.updateById(company);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return resp;
    }

    @Override
    public PageInfoResp<CompanyMemberVo> getCompanyMemberList(CompanyReq.BaseReq req) {
        PageInfoResp<CompanyMemberVo> resp = new PageInfoResp<>();
        TCompany company = companyDao.selectById(req.getPartyId());
        Map<String, Object> params = new HashMap<>();
        params.put("companyId", company.getId());
        PageInfo pageInfo = accountCompanyMapDao.getListByPage(req.getPage(), req.getPerPage(), params, "");
        resp.setList(pageInfo.getList());
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public CompanyResp addCompanyMember(CompanyReq.AddMemberReq req) {
        CompanyResp resp = new CompanyResp();
        TCompany company = companyDao.selectById(req.getPartyId());
        if (!company.getAccountId().equals(req.getAccountId())) {
            throw new BusinessException("不是公司管理员，无法添加成员");
        }
        TAccount account = accountService.findAccountByMobile(req.getMobile());
        if (account == null) {
            throw new BusinessException("账号未注册");
        }
        TUser user = userDao.getByAccountId(account.getId());
        if (user == null) {
            throw new BusinessException("非个人认证账号");
        }
        if (!user.getName().equals(req.getName())) {
            throw new BusinessException("姓名不匹配");
        }
        int result;
        AccountCompanyMap accountCompanyMap = accountCompanyMapDao.getByAccountIdCompanyId(account.getId(), req.getPartyId());
        if (accountCompanyMap == null) {
            accountCompanyMap = new AccountCompanyMap();
            accountCompanyMap.setAccountId(account.getId());
            accountCompanyMap.setCompanyId(req.getPartyId());
            accountCompanyMap.setName(req.getName());
            result = accountCompanyMapDao.insert(accountCompanyMap);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        } else if (accountCompanyMap.getIsDelete()) {
            accountCompanyMap.setIsDelete(false);
            result = accountCompanyMapDao.updateById(accountCompanyMap);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        } else {
            throw new BusinessException("请不要重复添加");
        }
        return resp;
    }

    @Override
    public CompanyResp deleteCompanyMember(CompanyReq.DeleteMemberReq req) {
        CompanyResp resp = new CompanyResp();
        TCompany company = companyDao.selectById(req.getPartyId());
        if (!company.getAccountId().equals(req.getAccountId())) {
            throw new BusinessException("不是公司管理员，无法删除成员");
        }
        if (company.getAccountId().equals(req.getMemberId())) {
            throw new BusinessException("无法删除公司管理员");
        }
        AccountCompanyMap accountCompanyMap = accountCompanyMapDao.getByAccountIdCompanyId(req.getMemberId(), req.getPartyId());
        if (accountCompanyMap != null && !accountCompanyMap.getIsDelete()) {
            accountCompanyMap.setIsDelete(true);
            accountCompanyMap.setUpdateTime(new Date());
            int result = accountCompanyMapDao.updateById(accountCompanyMap);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            TAccount account = tAccountDao.selectById(accountCompanyMap.getAccountId());
            if (account != null) {
                TUser user = userDao.getByAccountId(account.getId());
                Integer currentPartyId;
                if (user != null) {
                    currentPartyId = user.getId();
                } else {
                    List<AccountCompanyMap> accountCompanyMaps = accountCompanyMapDao.getByAccountId(account.getId());
                    if (accountCompanyMaps.size() > 0) {
                        currentPartyId = accountCompanyMaps.get(0).getCompanyId();
                    } else {
                        account.setCurrentPartyId(null);
                        currentPartyId = null;
                    }
                }
                result = tAccountDao.updateCurrentPartyId(account.getId(), currentPartyId);
                if (result == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
            }
        }
        return resp;
    }

    @Override
    public int addAccountCompanyMap(Integer accountId, Integer companyId, String name) {
        AccountCompanyMap accountCompanyMap = accountCompanyMapDao.getByAccountIdCompanyId(accountId, companyId);
        int result;
        if (accountCompanyMap == null) {
            accountCompanyMap = new AccountCompanyMap();
            accountCompanyMap.setAccountId(accountId);
            accountCompanyMap.setCompanyId(companyId);
            accountCompanyMap.setName(name);
            result = accountCompanyMapDao.insert(accountCompanyMap);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        } else if (accountCompanyMap.getIsDelete()) {
            accountCompanyMap.setIsDelete(false);
            result = accountCompanyMapDao.updateById(accountCompanyMap);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        return 1;
    }

    @Transactional
    @Override
    public CompanyResp changeAdmin(CompanyReq.ChangeAdminReq req) {
        CompanyResp resp = new CompanyResp();
        TCompany company = companyDao.selectById(req.getCompanyId());
        if (company == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TAccount account = tAccountDao.selectById(req.getAccountId());
        if (account == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        company.setAccountId(account.getId());
        company.setUpdateTime(new Date());
        int result = companyDao.updateById(company);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        if (StringUtils.isNotEmpty(company.getFadadaId())) {
            UpdateMemInfoReq updateMemInfoReq = new UpdateMemInfoReq();
            updateMemInfoReq.setCustomer_id(company.getFadadaId());
            updateMemInfoReq.setMobile(account.getMobile());
            FCommResp fCommResp = fddSignatureFacade.updateMemInfo(updateMemInfoReq);
            if (fCommResp == null || !fCommResp.getCode().equals("000")) {
                log.error("更新企业法大大信息失败，入参={}，出参={}", JSON.toJSONString(updateMemInfoReq), JSON.toJSONString(fCommResp));
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        return resp;
    }

    private CompanyApplyRecordVo processCompanyApplyRecord(TCompanyApplyRecord applyRecord) {
        CompanyApplyRecordVo vo = RespConvertUtil.convertToCompanyApplyRecordVo(applyRecord);
        vo.setStatusDesc(AccountEnum.ApplyStatus.getValueByKey(applyRecord.getStatus()));
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
        vo.setDefaultAgency(RespConvertUtil.convertToAgencyVo(agencyService.findByAgencyId(applyRecord.getDefaultAgencyId())));
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

    private CompanyVo processCompany(TCompany company) {
        CompanyVo vo = RespConvertUtil.convertToCompanyVo(company);
        vo.setAuditNum(company.getLeaseNum());
        TParty party = partyDao.selectById(company.getId());
        if (party != null) {
            if (party.getIsChannelAgent() != null && party.getIsChannelAgent()) {
                vo.setIsChannel(1);
            } else {
                vo.setIsChannel(0);
            }
            if (party.getIsInBlackList() != null && party.getIsInBlackList()) {
                PartyBlackListAction partyBlackListAction = partyBlackListActionDao.getLatestByPartyId(party.getId());
                if (partyBlackListAction != null) {
                    vo.setLatestInBlackListAt(partyBlackListAction.getCreatedAt());
                }
                vo.setIsInBlackList(party.getIsInBlackList());
            } else {
                vo.setIsInBlackList(false);
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
        vo.setDefaultAgency(RespConvertUtil.convertToAgencyVo(agencyService.findByAgencyId(company.getDefaultAgencyId())));
        if (company.getAgencyId() != null) {
            vo.setAgency(RespConvertUtil.convertToAgencyVo(agencyService.findByAgencyId(company.getAgencyId())));
        }
        PartyChannelAgent partyChannelAgent = partyChannelAgentDao.getByPartyId(company.getId());
        if (partyChannelAgent != null) {
            vo.setCommissionPercentChannelAgent(partyChannelAgent.getCommissionPercentChannelAgent());
            vo.setMyChannelAgentId(partyChannelAgent.getChannelAgentPartyId());
            PartyAccount channelAgentParty = accountService.getPartyAccountById(partyChannelAgent.getChannelAgentPartyId());
            if (channelAgentParty != null) {
                vo.setMyChannelAgentName(channelAgentParty.getName());
            }
        }
        if (company.getBankId() != null) {
            Bank bank = bankDao.getById(company.getBankId());
            vo.setBankName(bank.getName());
        }
        if (StringUtils.isNotEmpty(company.getCityId())) {
            vo.setProvinceId(cityDao.getProvinceId(company.getCityId()) + "");
        }
        if (StringUtils.isNotEmpty(company.getRegisterCityId())) {
            vo.setRegisterProvinceId(cityDao.getProvinceId(company.getRegisterCityId()) + "");
        }
        Staff staff = staffDao.getByMobile(company.getMobile());
        if (staff == null) {
            vo.setIsStaff("0");
        } else {
            vo.setIsStaff("1");
        }
        if (company.getOpenAccountOperatorId() != null) {
            vo.setOpenAccountOperator(staffDao.getName(company.getOpenAccountOperatorId()));
        }
        if (company.getBusinessOperatorId() != null) {
            vo.setBusinessOperator(staffDao.getName(company.getBusinessOperatorId()));
        }
        TCompanyApplyRecord applyRecord = companyApplyRecordDao.getApprovedByLicense(company.getLicense());
        if (applyRecord != null) {
            vo.setApplySource(applyRecord.getApplySource());
            vo.setApplySourceDesc(PartyEnum.ApplySource.getValueByKey(applyRecord.getApplySource()));
        } else {
            vo.setApplySource(PartyEnum.ApplySource.ADMIN.getKey());
            vo.setApplySourceDesc(PartyEnum.ApplySource.ADMIN.getValue());
        }
        return vo;
    }

    /**
     *
     *根据东方付id 返回公司信息
     */
    @Override
    public TCompany findCompanyByMemCode(String memCode) {

        TCompanyCondition condition = new TCompanyCondition();
        condition.setDfftId(memCode);
        return companyDao.selectFirst(condition);
     }
}