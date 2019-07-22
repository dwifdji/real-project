package com._360pai.core.provider.account;

import com._360pai.arch.common.ListResp;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.exception.ExceptionEnumImpl;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.PasswordEncryption;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.common.utils.ToolUtil;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.condition.account.TUserApplyRecordCondition;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.req.*;
import com._360pai.core.facade.account.resp.*;
import com._360pai.core.facade.account.vo.*;
import com._360pai.core.facade.activity.AuctionFacade;
import com._360pai.core.facade.activity.req.AuctionReq;
import com._360pai.core.facade.activity.resp.DfftResp;
import com._360pai.core.facade.assistant.vo.CityVo;
import com._360pai.core.facade.fastway.resp.CompanyFundDetailVO;
import com._360pai.core.facade.fastway.resp.UserFundDetailVO;
import com._360pai.core.model.account.*;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.payment.AuctionOrder;
import com._360pai.core.service.account.*;
import com._360pai.core.service.activity.AuctionActivityService;
import com._360pai.core.service.assistant.*;
import com._360pai.core.service.disposal.DisposalRequirementService;
import com._360pai.core.service.enrolling.EnrollingActivityService;
import com._360pai.core.service.enrolling.EnrollingDepositService;
import com._360pai.core.service.enrolling.FavoriteEnrollingActivityService;
import com._360pai.core.service.enrolling.NotifyPartyEnrollingActivityService;
import com._360pai.core.service.fastway.impl.FundApplyServiceImpl;
import com._360pai.core.service.payment.AuctionOrderService;
import com._360pai.core.utils.BusinessUtil;
import com._360pai.gateway.common.alisms.AliSmsTemplateEnums;
import com._360pai.gateway.controller.req.alisms.FAliSmsSendReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

@Component
@Service(version = "1.0.0")
public class AccountProvider implements AccountFacade {
    public static final Logger LOGGER = LoggerFactory.getLogger(AccountProvider.class);

    @Resource
    private AccountService accountService;

    @Resource
    private AgencyService agencyService;

    @Resource
    private AccountCompanyMapService accountCompanyMapService;

    @Resource
    private UserService userService;

    @Resource
    private CompanyService companyService;

    @Resource
    private PartyService partyService;

    @Resource
    private UserVerifyApplicationService userVerifyApplicationService;

    @Resource
    private CompanyVerifyApplicationService companyVerifyApplicationService;

    @Resource
    private AgencyApplicationService agencyApplicationService;

    @Resource
    private FundService fundService;
    @Resource
    private DisposeService disposeService;
    @Autowired
    private PartyBlackListActionService partyBlackListActionService;
    @Autowired
    private PartyChannelAgentService partyChannelAgentService;
    @Autowired
    private BankService bankService;
    @Autowired
    private SpvService spvService;
    @Autowired
    private GatewayMqSender gatewayMqSender;
    @Autowired
    private SystemProperties systemProperties;
    @Autowired
    private SmsHelperService smsHelperService;
    @Autowired
    private TBankService tBankService;
    @Autowired
    private AcctService acctService;

    @Autowired
    private AccountBusinessService accountBusinessService;
    @Autowired
    private FavoriteActivityService favoriteActivityService;
    @Autowired
    private FavoriteEnrollingActivityService favoriteEnrollingActivityService;
    @Autowired
    private NotifyPartyActivityService notifyPartyActivityService;
    @Autowired
    private NotifyPartyEnrollingActivityService notifyPartyEnrollingActivityService;
    @Autowired
    private DepositService depositService;
    @Autowired
    private EnrollingDepositService enrollingDepositService;
    @Autowired
    private AuctionOrderService auctionOrderService;
    @Autowired
    private TAccountViewRecordService tAccountViewRecordService;
    @Autowired
    private EnrollingActivityService enrollingActivityService;
    @Autowired
    private AuctionActivityService auctionActivityService;
    @Autowired
    private DisposalRequirementService disposalRequirementService;
    @Autowired
    private FundApplyServiceImpl fundApplyServiceImpl;
    @Reference(version = "1.0.0")
    private AuctionFacade auctionFacade;
    @Autowired
    private CityService cityService;

    @Override
    public AccountResp getAccount(Long id) {
        AccountResp accountResp = new AccountResp();
        TAccount account = accountService.selectByPrimaryKey(id.intValue());
        BeanUtils.copyProperties(account, accountResp);
        if (account.getIsAgencyAdmin() != null && account.getIsAgencyAdmin().equals(1)) {
            accountResp.setCanCheckReservePrice(true);
        }
        return accountResp;
    }

    @Override
    public boolean registerAccount(AccountReq params) {
        TAccount account = new TAccount();
        account.setMobile(params.getMobile());
        account.setRegisterSource(params.getRegisterSource());
        account.setSource(params.getSource());
        account.setDefaultAgencyId(params.getDefaultAgencyId());
        String encryptedPassword = null;
        try {
            encryptedPassword = PasswordEncryption.getEncryptedPassword(params.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        //加密密码
        account.setPassword(encryptedPassword);
        int result = accountService.insert(account);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        JSONObject message = new JSONObject();
        message.put("mobile", params.getMobile());
        if (params.isDefaultPassword()) {
            message.put("password", params.getPassword());
        }
        gatewayMqSender.accountRegisterEnqueue(message.toJSONString());
        return true;
    }


    @Override
    public AccountResp findAccountByMobile(String mobile) {
        TAccount accountByMobile = accountService.findAccountByMobile(mobile);

        if (null == accountByMobile) {
            return null;
        }

        AccountResp resp = new AccountResp();

        BeanUtils.copyProperties(accountByMobile, resp);
        return resp;
    }

    @Override
    public AgencyResp getAgencyByCode(String code) {
        AgencyResp resp = new AgencyResp();
        TAgency agency = agencyService.findByAgencyCode(code);
        BeanUtils.copyProperties(agency, resp);
        return resp;
    }

    @Override
    public AgencyResp getAgencyById(Integer id) {
        AgencyResp resp = new AgencyResp();
        TAgency agency = agencyService.findByAgencyId(id);
        if (agency != null) {
            BeanUtils.copyProperties(agency, resp);
        }
        return resp;
    }

    @Override
    public UserResp getUserById(Integer userId) {
        UserResp userResp = new UserResp();
        TUser user = userService.findUserById(userId);
        if (user != null) {
            BeanUtils.copyProperties(user, userResp);
            userResp.setCertificateNumber(ToolUtil.maskCertificateNumber(user.getCertificateNumber()));
            return userResp;
        }
        return userResp;
    }

    @Override
    public UserResp getUserByAccountId(Integer accountId) {
        UserResp userResp = new UserResp();
        TUser user = userService.findUserByAccountId(accountId);
        if (user != null) {
            BeanUtils.copyProperties(user, userResp);
            if (StringUtils.isNotBlank(user.getCityId()) ) {
                CityVo cityVo = new CityVo();
                cityVo.setId(Integer.valueOf(user.getCityId()));
                cityVo.setName(cityService.getCityName(Integer.valueOf(user.getCityId()) ));
                userResp.setCityVo(cityVo);
            }
            return userResp;
        }
        return null;
    }

    @Override
    public CompanyResp getCompanyByCompanyId(Integer companyId) {
        CompanyResp vo = new CompanyResp();
        TCompany company = companyService.findCompanyById(companyId);
        if (company != null) {
            BeanUtils.copyProperties(company, vo);
            if (StringUtils.isNotBlank(vo.getCityId())) {
                vo.setCityName(cityService.getCityName(Integer.parseInt(vo.getCityId())));
            }
            if (StringUtils.isNotBlank(vo.getProvinceId())) {
                vo.setProvinceName(cityService.getProvinceName(Integer.parseInt(vo.getProvinceId())));
            }
            if (StringUtils.isNotBlank(vo.getAreaId())) {
                vo.setAreaName(cityService.getAreaName(Integer.parseInt(vo.getAreaId())));
            }
            if (StringUtils.isNotBlank(vo.getRegisterCityId())) {
                vo.setRegisterCityName(cityService.getCityName(Integer.parseInt(vo.getRegisterCityId())));
            }
            if (StringUtils.isNotBlank(vo.getRegisterProvinceId())) {
                vo.setRegisterProvinceName(cityService.getProvinceName(Integer.parseInt(vo.getRegisterProvinceId())));
            }
            if (StringUtils.isNotBlank(vo.getRegisterAreaId())) {
                vo.setRegisterAreaName(cityService.getAreaName(Integer.parseInt(vo.getRegisterAreaId())));
            }
            return vo;
        }
        return null;
    }

    @Override
    public List<CompanyResp> getCompanyByAccountId(Integer accountId) {
        List<CompanyResp> respList = new ArrayList<CompanyResp>();
        List<TCompany> companyList = companyService.findCompanyByAccountId(accountId);
        for (TCompany company : companyList) {
            CompanyResp resp = new CompanyResp();
            BeanUtils.copyProperties(company, resp);
            respList.add(resp);
        }
        return respList;
    }

    @Override
    public PartyResp getPartyById(Integer id) {
        PartyResp resp = new PartyResp();
        TParty party = partyService.findPartyById(id);
        if (party != null) {
            BeanUtils.copyProperties(party, resp);
            return resp;
        }
        return null;
    }


    @Override
    public String getDisposerApplyStatus(Integer partyId) {
        return accountService.getDisposerApplyStatus(partyId);
    }

    @Override
    public String getAgencyApplyStatus(Integer accountId, Integer partyId, Integer agencyId) {
        return accountService.getAgencyApplyStatus(accountId, partyId, agencyId);
    }

    @Override
    public String getFundApplyStatus(Integer accountId, Integer partyId, String applyType) {
        return accountService.getFundApplyStatus(accountId, partyId, applyType);
    }

    @Override
    public boolean updateAccountById(AccountReq accountReq) {
        TAccount account = new TAccount();
        BeanUtils.copyProperties(accountReq, account);
        return accountService.updateById(account) == 1;
    }

    @Override
    public boolean updateUserById(UserReq req) {
        TUser user = new TUser();
        BeanUtils.copyProperties(req, user);
        return userService.updateUserById(user) == 1;
    }

    @Override
    public boolean updateCompanyById(CompanyReq req) {
        TCompany company = new TCompany();
        BeanUtils.copyProperties(req, company);
        return companyService.updateCompanyById(company) == 1;
    }

    @Override
    public boolean applyUserAuth(ApplyUserAuthReq req) {
        //判断手机号是否已申请 正在审核
        TUserApplyRecordCondition condition = new TUserApplyRecordCondition();
        condition.setMobile(req.getMobile());
        PageInfo<TUserApplyRecord> pageInfo = userVerifyApplicationService.getUserApplyRecord(condition, 1, 10, "id desc");
        if (pageInfo != null && pageInfo.getList() != null && pageInfo.getList().size() != 0 && pageInfo.getList().get(0) != null && pageInfo.getList().get(0).getStatus().equals("PENDING")) {
            LOGGER.error("个人认证,正在审核,勿重复提交,param:{}", JSON.toJSONString(req));
            throw new BusinessException(ExceptionEnumImpl.MOBILE_BEING_AUTH);
        }
        //判断手机号是否认证过
        TUser user = userService.findUserByMobile(req.getMobile());
        if (user != null && user.getId() != null) {
            LOGGER.error("个人认证,手机号已认证,param:{}", JSON.toJSONString(req));
            throw new BusinessException(ExceptionEnumImpl.MOBILE_HAS_AUTH);
        }

        //判断身份证是否被认证过
        TUser param = new TUser();
        param.setCertificateNumber(req.getCertificateNumber());
        List<TUser> list = userService.findUser(param);
        if (list != null && !list.isEmpty()) {
            LOGGER.error("个人认证,身份证已认证,param:{}", JSON.toJSONString(req));
            throw new BusinessException(ExceptionEnumImpl.IDCARD_HAS_AUTH);
        }
        TUserApplyRecord applyRecord = new TUserApplyRecord();
        BeanUtils.copyProperties(req, applyRecord);
        if (applyRecord.getDefaultAgencyId() == null) {
            TAgency agency = agencyService.findByAgencyCode(SystemConstant.DEFAULT_AGENCY_CODE);
            if (agency != null) {
                applyRecord.setDefaultAgencyId(agency.getId());
            }
        }
        int result = userVerifyApplicationService.saveUserApplyRecord(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        sendUserApplyNotify(applyRecord);
        return true;
    }

    private void sendUserApplyNotify(TUserApplyRecord applyRecord) {
        try {
            // 发送给用户
            smsHelperService.userApplyNotify(applyRecord.getMobile(), applyRecord.getName());
            // 发送给客服
            List<String> notifierMobileList = new ArrayList<>();
            notifierMobileList.addAll(Arrays.asList(systemProperties.getPlatformCustomerServicePhone().split(",")));
            for (String notifierMobile : notifierMobileList) {
                smsHelperService.userApplyToPlatformNotify(notifierMobile, applyRecord.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("个人认证申请发送短信失败，mobile={}", applyRecord.getMobile());
            gatewayMqSender.sendTryCatchExceptionEmail(applyRecord.getId().intValue(), e);
        }
    }

    @Override
    public boolean applyCompanyAuth(ApplyCompanyAuthReq req) {
        TCompany company = companyService.findCompanyByLicence(req.getLicense());
        if (company != null) {
            throw new BusinessException(ExceptionEnumImpl.COMPANY_HAS_AUTH);
        }
        TCompanyApplyRecord applyRecord = new TCompanyApplyRecord();
        BeanUtils.copyProperties(req, applyRecord);
        if (applyRecord.getDefaultAgencyId() == null) {
            TAgency agency = agencyService.findByAgencyCode(SystemConstant.DEFAULT_AGENCY_CODE);
            if (agency != null) {
                applyRecord.setDefaultAgencyId(agency.getId());
            }
        }

        boolean result = companyVerifyApplicationService.saveCompanyApplyRecord(applyRecord);
        if (!result) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        sendCompanyApplyNotify(applyRecord);
        return true;
    }

    private void sendCompanyApplyNotify(TCompanyApplyRecord applyRecord) {
        try {
            // 发送给用户
            smsHelperService.companyApplyNotify(applyRecord.getMobile(), applyRecord.getName());
            // 发送给客服
            List<String> notifierMobileList = new ArrayList<>();
            notifierMobileList.addAll(Arrays.asList(systemProperties.getPlatformCustomerServicePhone().split(",")));
            for (String notifierMobile : notifierMobileList) {
                smsHelperService.companyApplyToPlatformNotify(notifierMobile, applyRecord.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            gatewayMqSender.sendTryCatchExceptionEmail(applyRecord.getId().intValue(), e);
        }
    }

    @Override
    public boolean applyAgencyAuth(ApplyAgencyAuthReq req) {
        TAgencyApplyRecord agencyApplyRecord = new TAgencyApplyRecord();
        BeanUtils.copyProperties(req, agencyApplyRecord);
        return agencyApplicationService.saveAgencyApplyRecord(agencyApplyRecord);
    }

    @Override
    public boolean applyFundAuth(FundProviderApplyReq req) {
        TFundProviderApply fundProviderApply = new TFundProviderApply();
        BeanUtils.copyProperties(req, fundProviderApply);
        return fundService.saveFundApply(fundProviderApply);
    }

    @Override
    public boolean applyDisposeAuth(DisposeProviderApplyReq req) {
        TDisposeProviderApply disposeProviderApply = new TDisposeProviderApply();
        BeanUtils.copyProperties(req, disposeProviderApply);
        return disposeService.saveDisposeApply(disposeProviderApply);
    }

    @Override
    @Transactional
    public boolean verifyUser(ApplyUserAuthReq req, String operation) {
        //判断手机号是否认证过

        TUserApplyRecord applyRecord = userVerifyApplicationService.getUserApplyRecordById(req.getId());
        if (!AccountEnum.ApplyStatus.PENDING.getKey().equals(applyRecord.getStatus())) {
            throw new BusinessException("请勿重复审核");
        }
        TUser user = userService.findUserByMobile(applyRecord.getMobile());
        if (user != null && user.getId() != null) {
            LOGGER.error("个人认证,手机号已注册,param:{}", JSON.toJSONString(req));
            throw new BusinessException(ExceptionEnumImpl.MOBILE_HAS_AUTH);
        }

        //判断身份证是否被认证过
        TUser param = new TUser();
        param.setCertificateNumber(applyRecord.getCertificateNumber());
        List<TUser> list = userService.findUser(param);
        if (list != null && !list.isEmpty()) {
            LOGGER.error("个人认证,身份证已注册,param:{}", JSON.toJSONString(req));
            throw new BusinessException(ExceptionEnumImpl.IDCARD_HAS_AUTH);

        }
        //拒绝,更新申请表状态
        if (SystemConstant.OPERATION_REJECT.equals(operation)) {
            updateUserApplyStatusReject(req);
        } else if (SystemConstant.OPERATION_APPROVE.equals(operation)) {
            if (req.getCertificateBegin() == null || req.getCertificateEnd() == null) {
                throw new BusinessException("身份证有效期不能为空");
            }
            //通过
            //更新申请表状态
            TUserApplyRecord updateRecord = new TUserApplyRecord();
            updateRecord.setId(req.getId());
            updateRecord.setStatus("APPROVED");
            updateRecord.setOperatorId(req.getOperatorId());
            updateRecord.setCertificateBegin(req.getCertificateBegin());
            updateRecord.setCertificateEnd(req.getCertificateEnd());
            updateRecord.setOpenAccountOperatorId(req.getOpenAccountOperatorId());
            updateRecord.setBusinessOperatorId(req.getBusinessOperatorId());
            userVerifyApplicationService.updateUserApplyRecord(updateRecord);
            //插入party表
            Integer userId = insertParty(SystemConstant.ACCOUNT_USER_TYPE, PartyEnum.Category.NORMAL_USER.getKey(), applyRecord.getApplySource());
            //插入user表
            insertUser(req.getId(), userId);

            TAccount account = accountService.selectByPrimaryKey(applyRecord.getAccountId());
            if (account.getCurrentPartyId() == null) {
                account.setCurrentPartyId(userId);
                accountService.updateById(account);
            }
            JSONObject data = new JSONObject();
            data.put("applyId", applyRecord.getId());
            data.put("partyId", userId);
            gatewayMqSender.userApplyApproveEnqueue(data.toJSONString());
            accountBusinessService.updateBusinessInfo(userId, JSONObject.parseObject(JSON.toJSONString(updateRecord)), AccountBusinessService.BusinessType.user);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean verifyCompany(ApplyCompanyAuthReq req, String operation) {
        //判断营业执照是否已被认证
        TCompanyApplyRecord applyRecord = companyVerifyApplicationService.findCompanyApplyRecordById(req.getId());
        if (!AccountEnum.ApplyStatus.PENDING.getKey().equals(applyRecord.getStatus())) {
            throw new BusinessException("请勿重复审核");
        }
        TCompany company = companyService.findCompanyByLicence(applyRecord.getLicense());
        if (company != null) {
            throw new BusinessException(ExceptionEnumImpl.COMPANY_HAS_AUTH);
        }

        //拒绝,更新申请表状态
        if (SystemConstant.OPERATION_REJECT.equals(operation)) {
            updateCompanyApplyStatusReject(req);
        } else if (SystemConstant.OPERATION_APPROVE.equals(operation)) {
            if (req.getQualifiedBegin() == null || req.getQualifiedEnd() == null) {
                throw new BusinessException("营业期限不能为空");
            }
            if (StringUtils.isEmpty(req.getCategory())) {
                throw new BusinessException("企业类型不能为空");
            }
            //通过
            //更新申请表状态
            TCompanyApplyRecord updateRecord = new TCompanyApplyRecord();
            BeanUtils.copyProperties(req, updateRecord);
            updateRecord.setStatus("APPROVED");
            updateRecord.setOpenAccountOperatorId(req.getOpenAccountOperatorId());
            updateRecord.setBusinessOperatorId(req.getBusinessOperatorId());
            companyVerifyApplicationService.updateCompanyApplyRecord(updateRecord);
            // 插入party表
            Integer companyId = insertParty(SystemConstant.ACCOUNT_COMPANY_TYPE, req.getCategory(), applyRecord.getApplySource());
            // 插入company表
            insertCompany(req.getId(), companyId, req.getCategory(), req.getQualifiedBegin(), req.getQualifiedEnd());
            TUser user = userService.findUserByAccountId(applyRecord.getAccountId());
            companyService.addAccountCompanyMap(applyRecord.getAccountId(), companyId, user == null ? applyRecord.getName() : user.getName());
            TAccount account = accountService.selectByPrimaryKey(applyRecord.getAccountId());
            if (account.getCurrentPartyId() == null) {
                account.setCurrentPartyId(companyId);
                accountService.updateById(account);
            }
            JSONObject data = new JSONObject();
            data.put("applyId", applyRecord.getId());
            data.put("partyId", companyId);
            gatewayMqSender.companyApplyApproveEnqueue(data.toJSONString());
            accountBusinessService.updateBusinessInfo(companyId, JSONObject.parseObject(JSON.toJSONString(updateRecord)), AccountBusinessService.BusinessType.company);
        }

        return true;
    }

    @Override
    @Transactional
    public boolean verifyAgency(ApplyAgencyAuthReq req, String operation) {
        //判断营业执照是否已被认证
        TAgencyApplyRecord applyRecord = agencyApplicationService.getAgencyApplyById(req.getId());
        if (!AccountEnum.ApplyStatus.PENDING.getKey().equals(applyRecord.getStatus())) {
            throw new BusinessException("请勿重复审核");
        }
        TAgency agency = agencyService.findByLicense(applyRecord.getLicense());
        if (agency != null) {
            throw new BusinessException(ExceptionEnumImpl.AGENCY_HAS_AUTH);
        }
        //判断手机号是否已拥有机构
        TAgency tAgency = agencyService.findByMobile(applyRecord.getMobile());
        if (tAgency != null) {
            throw new BusinessException(ExceptionEnumImpl.AGENCY_HAS_AUTH);
        }

        //拒绝,更新申请表状态
        if (SystemConstant.OPERATION_REJECT.equals(operation)) {
            updateAgencyApplyStatusReject(req);
        } else if (SystemConstant.OPERATION_APPROVE.equals(operation)) {
            //通过
            //更新申请表状态

            TAgencyApplyRecord record = new TAgencyApplyRecord();
            BeanUtils.copyProperties(req, record);
            record.setStatus("APPROVED");
            agencyApplicationService.updateAgencyApplyRecord(record);

            //插入agency表
            TAgency agenc = new TAgency();
            BeanUtils.copyProperties(req, agenc);
            agenc.setDfftId(BusinessUtil.genDfftId());
            agencyService.saveAgency(agency);
            //更新account表
            Integer agencyId = agenc.getId();
            TAccount account = new TAccount();
            account.setAgencyId(agencyId);
            account.setIsAgencyAdmin(1);
            accountService.updateById(account);
            sendAgencyApplyApproveNotify(applyRecord);
            accountBusinessService.updateBusinessInfo(null, JSONObject.parseObject(JSON.toJSONString(record)), AccountBusinessService.BusinessType.agency);
        }

        return true;
    }

    private void sendAgencyApplyApproveNotify(TAgencyApplyRecord applyRecord) {
        try {
            // 发送给用户
            FAliSmsSendReq smsSendReq = new FAliSmsSendReq();
            smsSendReq.setPhoneNumber(applyRecord.getMobile());
            smsSendReq.setTemplateCode(AliSmsTemplateEnums.AGENCY_CERTIFICATION_PASS.getCode());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("company_name", applyRecord.getName());
            smsSendReq.setTemplateParam(jsonObject.toJSONString());
            gatewayMqSender.sendSms(smsSendReq);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("机构认证申请通过发送短信失败，mobile={}", applyRecord.getMobile());
            gatewayMqSender.sendTryCatchExceptionEmail(applyRecord.getId().intValue(), e);
        }
    }


    @Override
    public AccountBaseDto getAccoutBaseByPartyId(Integer partyId) {
        return accountService.getAccountBaseByPartyId(partyId);
    }

    @Override
    public boolean verifyFund(FundProviderApplyReq req, String operation) {
        return false;
    }

    @Override
    public boolean verifyDispose(DisposeProviderApplyReq req, String operation) {
        return false;
    }


    private void updateUserApplyStatusReject(ApplyUserAuthReq req) {
        TUserApplyRecord userApplyRecord = new TUserApplyRecord();
        userApplyRecord.setId(req.getId());
        userApplyRecord.setReason(req.getReason());
        userApplyRecord.setOperatorId(req.getOperatorId());
        userApplyRecord.setStatus("REJECT");
        userVerifyApplicationService.updateUserApplyRecord(userApplyRecord);
    }

    private void updateCompanyApplyStatusReject(ApplyCompanyAuthReq req) {
        TCompanyApplyRecord companyApplyRecord = new TCompanyApplyRecord();
        companyApplyRecord.setId(req.getId());
        companyApplyRecord.setReason(req.getReason());
        companyApplyRecord.setOperatorId(req.getOperatorId());
        companyApplyRecord.setStatus("REJECT");
        companyVerifyApplicationService.updateCompanyApplyRecord(companyApplyRecord);
    }


    private void updateAgencyApplyStatusReject(ApplyAgencyAuthReq req) {
        TAgencyApplyRecord agencyApplyRecord = new TAgencyApplyRecord();
        agencyApplyRecord.setId(req.getId());
        agencyApplyRecord.setOperatorId(req.getOperatorId());
        agencyApplyRecord.setStatus("REJECT");
        agencyApplicationService.updateAgencyApplyRecord(agencyApplyRecord);
    }


    private Integer insertParty(String type, String category, String applySource) {
        TParty party = new TParty();
        party.setType(type);
        party.setCategory(category);
        party.setApplySource(applySource);
        partyService.saveParty(party);
        return party.getId();
    }

    private void insertUser(Long applyId, Integer userId) {
        TUser tUser = new TUser();
        TUserApplyRecord tUserApplyRecord = userVerifyApplicationService.getUserApplyRecordById(applyId);
        BeanUtils.copyProperties(tUserApplyRecord, tUser);
        tUser.setId(userId);
        tUser.setDfftId(BusinessUtil.genDfftId());
        userService.saveUser(tUser);
    }

    private void insertCompany(Long applyId, Integer companyId, String category, Date qualifiedBegin, Date qualifiedEnd) {
        TCompany tCompany = new TCompany();
        TCompanyApplyRecord tCompanyApplyRecord = companyVerifyApplicationService.findCompanyApplyRecordById(applyId);
        BeanUtils.copyProperties(tCompanyApplyRecord, tCompany);
        tCompany.setId(companyId);
        tCompany.setDfftId(BusinessUtil.genDfftId());
        tCompany.setCategory(category);
        tCompany.setQualifiedBegin(qualifiedBegin);
        tCompany.setQualifiedEnd(qualifiedEnd);
        tCompany.setFadadaEmail(RandomNumberGenerator.wordGenerator(10) + "@360pai.com");
        companyService.saveCompany(tCompany);
    }


    @Override
    public List<AgencyResp> searchAgency(String cityId, String name) {
        List<TAgency> agencyList = agencyService.searchAgency(cityId, name);
        List<AgencyResp> respList = new ArrayList<AgencyResp>();
        for (TAgency agency : agencyList) {
            AgencyResp resp = new AgencyResp();
            BeanUtils.copyProperties(agency, resp);
            respList.add(resp);
        }
        return respList;
    }

    @Override
    public PageInfoResp<AccountVo> getAccountListByPage(AccountReq.QueryReq req) {
        return accountService.getAccountListByPage(req);
    }

    @Override
    public AccountResp.DetailResp getAccountByAccountId(AccountReq.BaseReq req) {
        AccountResp.DetailResp resp = new AccountResp.DetailResp();
        resp.setAccount(accountService.getAccountById(req));
        return resp;
    }

    @Override
    public PageInfoResp<UserApplyRecordVo> getUserApplyRecordListByPage(AccountReq.QueryReq req) {
        return userService.getUserApplyRecordListByPage(req);
    }

    @Override
    public UserApplyResp.DetailResp getUserApplyRecordById(AccountReq.BaseReq req) {
        UserApplyResp.DetailResp resp = new UserApplyResp.DetailResp();
        resp.setApplyRecord(userService.getUserApplyRecordById(req));
        return resp;
    }

    @Override
    public PageInfoResp<CompanyApplyRecordVo> getCompanyApplyRecordListByPage(AccountReq.QueryReq req) {
        return companyService.getCompanyApplyRecordListByPage(req);
    }

    @Override
    public CompanyApplyResp.DetailResp getCompanyApplyRecordById(AccountReq.BaseReq req) {
        CompanyApplyResp.DetailResp resp = new CompanyApplyResp.DetailResp();
        resp.setApplyRecord(companyService.getCompanyApplyRecordById(req));
        return resp;
    }

    @Override
    public PageInfoResp<UserVo> getUserListByPage(UserReq.QueryReq req) {
        return userService.getUserListByPage(req);
    }

    @Override
    public UserResp.DetailResp getUserById(AccountReq.BaseReq req) {
        UserResp.DetailResp resp = new UserResp.DetailResp();
        resp.setUser(userService.getUserById(req));
        return resp;
    }

    @Override
    public PageInfoResp<CompanyVo> getCompanyListByPage(CompanyReq.QueryReq req) {
        return companyService.getCompanyListByPage(req);
    }

    @Override
    public CompanyResp.DetailResp getCompanyById(AccountReq.BaseReq req) {
        CompanyResp.DetailResp resp = new CompanyResp.DetailResp();
        resp.setCompany(companyService.getCompanyById(req));
        return resp;
    }

    @Override
    public UserResp updateUser(UserReq.UpdateReq req) {
        return userService.updateUser(req);
    }

    @Override
    public CompanyResp updateCompany(CompanyReq.UpdateReq req) {
        return companyService.updateCompany(req);
    }

    @Override
    public CompanyResp changeAdmin(CompanyReq.ChangeAdminReq req) {
        return companyService.changeAdmin(req);
    }

    @Override
    public PageInfoResp<AccountVo> getCompanyAccountListByPage(AccountReq.QueryReq req) {
        return accountService.getCompanyListByPage(req);
    }

    @Override
    public CompanyResp createChannelPayCompany(CompanyReq.CreateChannelPayCompanyReq req) {
        return companyService.createChannelPayCompany(req);
    }

    @Override
    public PageInfoResp<AgencyVo> getAgencyListByPage(AgencyReq.QueryReq req) {
        return agencyService.getAgencyListByPage(req);
    }

    @Override
    public AgencyResp.DetailResp getAgencyById(AgencyReq.BaseReq req) {
        return agencyService.getAgencyById(req);
    }

    @Override
    public PartyBlackListActionResp partyLockInBlackList(PartyBlackListActionReq.BaseReq req) {
        return partyBlackListActionService.partyLockInBlackList(req);
    }

    @Override
    public PartyBlackListActionResp partyReleaseFromBlackList(PartyBlackListActionReq.BaseReq req) {
        return partyBlackListActionService.partyReleaseFromBlackList(req);
    }

    @Override
    public PageInfoResp<PartyBlackListActionVo> getPartyBlackListActionListByPage(PartyBlackListActionReq.BaseReq req) {
        return partyBlackListActionService.getPartyBlackListActionListByPage(req);
    }

    @Override
    public PartyChannelAgentResp partySetChannelAgent(PartyChannelAgentReq.BaseReq req) {
        return partyChannelAgentService.partySetChannelAgent(req);
    }

    @Override
    public PartyChannelAgentResp partySelectChannelAgent(PartyChannelAgentReq.BaseReq req) {
        return partyChannelAgentService.partySelectChannelAgent(req);
    }

    @Override
    public PartyChannelAgentResp partyCancelSelectChannelAgent(PartyChannelAgentReq.BaseReq req) {
        return partyChannelAgentService.partyCancelSelectChannelAgent(req);
    }

    @Override
    public CompanyResp companySetChannelPay(CompanyReq.BaseReq req) {
        return companyService.companySetChannelPay(req);
    }

    @Override
    public AgencyApplyResp agencyApply(AgencyApplyReq.CreateReq req) {
        return agencyService.agencyApply(req);
    }

    @Override
    public PageInfoResp<AgencyApplyRecordVo> getAgencyApplyListByPage(AgencyApplyReq.QueryReq req) {
        return agencyService.getAgencyApplyListByPage(req);
    }

    @Override
    public AgencyApplyResp.DetailResp getAgencyApplyRecordById(AgencyApplyReq.BaseReq req) {
        return agencyService.getAgencyApplyRecordById(req);
    }

    @Override
    public AgencyApplyResp agencyApplyUpdate(AgencyApplyReq.UpdateReq req) {
        return agencyService.agencyApplyUpdate(req);
    }

    @Override
    public AgencyApplyResp agencyApplyApprove(AgencyApplyReq.BaseReq req) {
        return agencyService.agencyApplyApprove(req, true);
    }

    @Override
    public AgencyApplyResp agencyApplyReject(AgencyApplyReq.BaseReq req) {
        return agencyService.agencyApplyReject(req);
    }

    @Override
    public AgencyResp updateAgency(AgencyReq.UpdateReq req) {
        return agencyService.updateAgency(req);
    }

    @Override
    public AgencyResp updateAgency(AgencyReq.UpdateDfftOrFadadaReq req) {
        return agencyService.updateAgency(req);
    }

    @Override
    public AgencyResp updateAgency(AgencyReq.AgencyUpdateReq req) {
        return agencyService.updateAgency(req);
    }

    @Override
    public AgencyResp agencySetChannelAgent(AgencyReq.BaseReq req) {
        return agencyService.setChannelAgent(req);
    }

    @Override
    public AgencyResp agencySelectChannelAgent(AgencyReq.BaseReq req) {
        return agencyService.selectChannelAgent(req);
    }

    @Override
    public AgencyResp agencyCancelSelectChannelAgent(AgencyReq.BaseReq req) {
        return agencyService.cancelSelectChannelAgent(req);
    }

    @Override
    public AgencyResp agencyPortalOffline(AgencyReq.BaseReq req) {
        return agencyService.agencyPortalOffline(req);
    }

    @Override
    public AgencyResp agencyPortalOnline(AgencyReq.BaseReq req) {
        return agencyService.agencyPortalOnline(req);
    }

    @Override
    public AccountResp agencyAccountAdd(AccountReq.BaseReq req) {
        return accountService.agencyAccountAdd(req);
    }

    @Override
    public AccountResp agencyAccountDelete(AccountReq.BaseReq req) {
        return accountService.agencyAccountDelete(req);
    }

    @Override
    public AgencyResp.DfftResp agencyPaymentAccountBalance(AgencyReq.BaseReq req) {
        return agencyService.paymentAccountBalance(req);
    }

    @Override
    public FundProviderApplyResp fundProviderApply(FundProviderApplyReq.CreateReq req) {
        return fundService.fundProviderApply(req);
    }

    @Override
    public PageInfoResp<FundProviderApplyVo> getFundProviderApplyListByPage(FundProviderApplyReq.QueryReq req) {
        return fundService.getFundProviderApplyListByPage(req);
    }

    @Override
    public FundProviderApplyResp.DetailResp getFundProviderApply(FundProviderApplyReq.BaseReq req) {
        return fundService.getFundProviderApply(req);
    }

    @Override
    public FundProviderApplyResp approveFundProviderApply(FundProviderApplyReq.UpdateReq req) {
        fundService.updateFundProviderApply(req);
        FundProviderApplyReq.BaseReq applyReq =  new FundProviderApplyReq.BaseReq();
        applyReq.setApplyId(req.getApplyId());
        applyReq.setOperatorId(req.getOperatorId());
        return fundService.approveFundProviderApply(applyReq);
    }

    @Override
    public FundProviderApplyResp rejectFundProviderApply(FundProviderApplyReq.BaseReq req) {
        return fundService.rejectFundProviderApply(req);
    }

    @Override
    public PageInfoResp<FundProviderVo> getFundProviderListByPage(FundProviderReq.QueryReq req) {
        return fundService.getFundProviderListByPage(req);
    }

    @Override
    public FundProviderResp.DetailResp getFundProvider(FundProviderReq.BaseReq req) {
        return fundService.getFundProvider(req);
    }

    @Override
    public FundProviderResp updateFundProvider(FundProviderReq.UpdateReq req) {
        return fundService.updateFundProvider(req);
    }

    @Override
    public DisposeProviderApplyResp disposeProviderApply(DisposeProviderApplyReq.CreateReq req) {
        return disposeService.disposeProviderApply(req);
    }

    @Override
    public PageInfoResp<DisposeProviderApplyVo> getDisposeProviderApplyListByPage(DisposeProviderApplyReq.QueryReq req) {
        return disposeService.getDisposeProviderApplyListByPage(req);
    }

    @Override
    public DisposeProviderApplyResp.DetailResp getDisposeProviderApply(DisposeProviderApplyReq.BaseReq req) {
        return disposeService.getDisposeProviderApply(req);
    }

    @Override
    public DisposeProviderApplyResp approveDisposeProviderApply(DisposeProviderApplyReq.BaseReq req) {
        return disposeService.approveDisposeProviderApply(req);
    }

    @Override
    public DisposeProviderApplyResp rejectDisposeProviderApply(DisposeProviderApplyReq.BaseReq req) {
        return disposeService.rejectDisposeProviderApply(req);
    }

    @Override
    public PageInfoResp<DisposeProviderVo> getDisposeProviderListByPage(DisposeProviderReq.QueryReq req) {
        return disposeService.getDisposeProviderListByPage(req);
    }

    @Override
    public DisposeProviderResp.DetailResp getDisposeProvider(DisposeProviderReq.BaseReq req) {
        return disposeService.getDisposeProvider(req);
    }

    @Override
    public DisposeProviderResp updateDisposeProvider(DisposeProviderReq.UpdateReq req) {
        return disposeService.updateDisposeProvider(req);
    }


    @Override
    public PageInfo getPartnerAgencyList(AgencyReq.QueryReq req) {
        return agencyService.getPartnerAgencyList(req.getPage(), req.getPerPage());
    }

    @Override
    public ListResp<BankAccountVo> getBankAccounts(BankAccountReq.BaseReq req) {
        return bankService.getBankAccounts(req);
    }

    @Override
    public BankAccountResp addBankAccount(BankAccountReq.CreateReq req) {
        return bankService.addBankAccount(req);
    }

    @Override
    public BankAccountResp updateBankAccount(BankAccountReq.UpdateReq req) {
        return bankService.updateBankAccount(req);
    }

    @Override
    public BankAccountResp deleteBankAccount(BankAccountReq.BaseReq req) {
        return bankService.deleteBankAccount(req);
    }

    private void checkSpv(String mobile, String license, Integer companyId) {
        TCompany tCompany = companyService.findCompanyById(companyId);
        if (tCompany == null) {
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }

        TUser user = userService.findUserByMobile(mobile);
        if (user != null && user.getId() != null) {
            throw new BusinessException(ExceptionEnumImpl.AUTH_CANNOT_SPV);
        }
        List<TCompany> companyList = companyService.findCompanyByMobile(mobile);
        if (companyList != null && !companyList.isEmpty() && companyList.size() > 0) {
            throw new BusinessException(ExceptionEnumImpl.AUTH_CANNOT_SPV);
        }

        List<TSpvApply> spvApprovedList = spvService.getSpvApplyByMobileAndStatus(mobile, "APPROVED");
        if (spvApprovedList != null && !spvApprovedList.isEmpty()) {
            throw new BusinessException(ExceptionEnumImpl.BEING_SPV_ERROR);
        }

        List<TSpvApply> spvApprovedLicenseList = spvService.getSpvApplyByLicenseAndStatus(license, "APPROVED");
        if (spvApprovedLicenseList != null && !spvApprovedLicenseList.isEmpty()) {
            throw new BusinessException(ExceptionEnumImpl.BEING_SPV_ERROR);
        }
    }


    @Override
    @Transactional
    public boolean saveSpvApply(Integer companyId, String mobile, String license, String name) {

        checkSpv(mobile, license, companyId);

        List<TSpvApply> spvPendingList = spvService.getSpvApplyByMobileAndStatus(mobile, "PENDING");
        if (spvPendingList != null && !spvPendingList.isEmpty()) {
            throw new BusinessException(ExceptionEnumImpl.BEING_SPV_ERROR);
        }

        List<TSpvApply> spvApprovedLicenseList = spvService.getSpvApplyByLicenseAndStatus(license, "PENDING");
        if (spvApprovedLicenseList != null && !spvApprovedLicenseList.isEmpty()) {
            throw new BusinessException(ExceptionEnumImpl.BEING_SPV_ERROR);
        }

        TSpvApply spvApply = new TSpvApply();
        spvApply.setCompanyId(companyId);
        spvApply.setMobile(mobile);
        spvApply.setLicense(license);
        spvApply.setName(name);
        return spvService.saveSpvApply(spvApply);
    }

    @Override
    @Transactional
    public boolean updateSpv(SpvReq req) {
        TSpv spv = new TSpv();
        BeanUtils.copyProperties(req, spv);
        return spvService.updateSpv(spv);
    }

    @Override
    public List<SpvResp> getSpvListByCompanyId(Integer companyId) {
        TCompany company = companyService.findCompanyById(companyId);
        List<TSpv> spvList = spvService.getSpvByCompanyId(companyId);
        List<SpvResp> respList = new ArrayList<SpvResp>();

        if(spvList == null || spvList.size() == 0 || spvList.isEmpty()){
            return respList;
        }

        if(company.getVirtual() != 1){
            SpvResp resp = new SpvResp();
            resp.setId(0);
            resp.setName(company.getName());
            respList.add(resp);
        }
            for (TSpv spv : spvList) {
            SpvResp resp = new SpvResp();
            //BeanUtils.copyProperties(spv, resp);
            resp.setId(spv.getId());
            resp.setName(spv.getName());
            respList.add(resp);
        }
        return respList;
    }

    private void transSpvApplyVo(SpvVo spvVo, TSpvApply spvApply, String source) {
        spvVo.setName(spvApply.getName());
        spvVo.setLicense(spvApply.getLicense());
        spvVo.setMobile(spvApply.getMobile());
        spvVo.setCreateTime(DateUtil.getNormDateStr(spvApply.getCreateTime()));
        if (spvApply.getStatus().equals("PENDING") || spvApply.getStatus().equals("REJECT")) {
            spvVo.setPayBind(false);
            spvVo.setFddBind(false);
            spvVo.setDfftDescrip("未开通");
            spvVo.setFddDescrip("未开通");
            if (spvApply.getStatus().equals("PENDING")) {
                spvVo.setStatusDescrip("等待平台审核");
                if (source.equals("admin")) {
                    spvVo.setOperate(new String[]{"审核"});
                    spvVo.setId(spvApply.getId());
                }
            }
            if (spvApply.getStatus().equals("REJECT")) {
                spvVo.setStatusDescrip("已拒绝");
            }

        } else {
            TSpv spv = spvService.getSpvByMobile(spvApply.getMobile());
            spvVo.setPayBind(spv.getPayBind() == 1);
            spvVo.setFddBind(!StringUtils.isEmpty(spv.getFddId()));
            spvVo.setDfftDescrip(spvVo.isPayBind() ? "已开通" : "未开通");
            spvVo.setFddDescrip(spvVo.isFddBind() ? "已开通" : "未开通");
            if (source.equals("platform")) {
                spvVo.setOperate(new String[]{"查看"});
                spvVo.setId(spv.getId());
            }

            if (spvVo.isPayBind() && spvVo.isFddBind()) {
                spvVo.setStatusDescrip("启用");
            } else {
                if (source.equals("platform")) {
                    spvVo.setOperate(new String[]{"查看", "开通"});
                }
            }

            if (!spvVo.isPayBind() && !spvVo.isFddBind()) {
                spvVo.setStatusDescrip("需开通签章支付");
            }

            if (spvVo.isPayBind() && !spvVo.isFddBind()) {
                spvVo.setStatusDescrip("需开通签章");
            }

            if (!spvVo.isPayBind() && spvVo.isFddBind()) {
                spvVo.setStatusDescrip("需开通支付");
            }

        }

    }

    @Override
    public PageInfoResp<SpvVo> getSpvListByPage(Integer companyId, Integer page, Integer perPage, String source) {
        PageInfoResp<SpvVo> pageInfoResp = new PageInfoResp<SpvVo>();
        List<SpvVo> spvVoList = new ArrayList<SpvVo>();
        PageInfo<TSpvApply> applyPageInfo = spvService.getSpvListByPage(companyId, page, perPage);
        if (applyPageInfo != null && applyPageInfo.getList() != null && !applyPageInfo.getList().isEmpty()) {
            for (TSpvApply spvApply : applyPageInfo.getList()) {
                SpvVo spvVo = new SpvVo();
                transSpvApplyVo(spvVo, spvApply, source);
                spvVoList.add(spvVo);
            }
        }
        pageInfoResp.setHasNextPage(applyPageInfo.isHasNextPage());
        pageInfoResp.setTotal(applyPageInfo.getTotal());
        pageInfoResp.setList(spvVoList);
        return pageInfoResp;
    }

    @Override
    public SpvResp getSpvById(Integer spvId) {
        TSpv spv = spvService.getSpvById(spvId);
        SpvResp resp = new SpvResp();
        BeanUtils.copyProperties(spv, resp);
        return resp;
    }

    @Override
    public boolean checkSpvIsPendingOrApproved(String mobile) {
        TUser user = userService.findUserByMobile(mobile);
        if (user != null && user.getId() != null) {
            throw new BusinessException(ExceptionEnumImpl.AUTH_CANNOT_SPV);
        }
        List<TCompany> companyList = companyService.findCompanyByMobile(mobile);
        if (companyList != null && !companyList.isEmpty() && companyList.size() > 0) {
            throw new BusinessException(ExceptionEnumImpl.AUTH_CANNOT_SPV);
        }

        List<TSpvApply> spvApprovedList = spvService.getSpvApplyByMobileAndStatus(mobile, "APPROVED");
        if (spvApprovedList != null && !spvApprovedList.isEmpty()) {
            throw new BusinessException(ExceptionEnumImpl.BEING_SPV_ERROR);
        }

        List<TSpvApply> spvPendingList = spvService.getSpvApplyByMobileAndStatus(mobile, "PENDING");
        if (spvApprovedList != null && !spvApprovedList.isEmpty()) {
            throw new BusinessException(ExceptionEnumImpl.BEING_SPV_ERROR);
        }
        return true;
    }

    @Override
    public PageInfoResp<CompanyMemberVo> getCompanyMemberList(CompanyReq.BaseReq req) {
        return companyService.getCompanyMemberList(req);
    }

    @Override
    public CompanyResp addCompanyMember(CompanyReq.AddMemberReq req) {
        return companyService.addCompanyMember(req);
    }

    @Override
    public CompanyResp deleteCompanyMember(CompanyReq.DeleteMemberReq req) {
        return companyService.deleteCompanyMember(req);
    }

    @Override
    public AgencyResp.ProfileResp agencyProfile(AgencyReq.BaseReq req) {
        return agencyService.profile(req);
    }

    @Override
    public AgencyResp agencySetCanCheckReservePrice(AgencyReq.BaseReq req) {
        return agencyService.setCanCheckReservePrice(req);
    }

    @Override
    public Integer getCompanyIdByAccountId(Integer accountId) {
        AccountCompanyMap accountCompanyMap = accountCompanyMapService.getCompanyByAccountId(accountId);
        if(accountCompanyMap != null){
            return accountCompanyMap.getCompanyId();
        }
        return null;
    }

    @Override
    public void openElectronicSignatureNotify(String mobile) {
        smsHelperService.openElectronicSignatureNotify(mobile);
    }

    @Override
    public void openEasternPayNotify(String mobile) {
        smsHelperService.openEasternPayNotify(mobile);
    }

    @Override
    public boolean checkCanApllyAuth(Integer accountId, String type) {
        if(SystemConstant.ACCOUNT_USER_TYPE.equals(type)){
            List<TUserApplyRecord> pendingList = userVerifyApplicationService.getApplyRecordByAccountId(accountId,"PENDING");
            if(pendingList != null && !pendingList.isEmpty()){
                throw new BusinessException(ExceptionEnumImpl.MOBILE_BEING_AUTH);
            }
        }else if(SystemConstant.ACCOUNT_COMPANY_TYPE.equals(type)){
             List<TCompanyApplyRecord> pendingList = companyVerifyApplicationService.getApplyRecordByAccountId(accountId,"PENDING");
            if(pendingList != null && !pendingList.isEmpty()){
                throw new BusinessException(ExceptionEnumImpl.MOBILE_BEING_AUTH);
            }
        }else if(SystemConstant.ACCOUNT_DISPOSER_TYPE.equals(type)){
            List<TDisposeProviderApply> pendingList = disposeService.getApplyRecordByAccountId(accountId,"PENDING");
            if(pendingList != null && !pendingList.isEmpty()){
                throw new BusinessException(ExceptionEnumImpl.MOBILE_BEING_AUTH);
            }
        }else if(SystemConstant.ACCOUNT_FUNDER_TYPE.equals(type)){
            List<TFundProviderApply> pendingList = fundService.getApplyRecordByAccountId(accountId,"PENDING");
            if(pendingList != null && !pendingList.isEmpty()){
                throw new BusinessException(ExceptionEnumImpl.MOBILE_BEING_AUTH);
            }
        }
        return true;
    }

    @Override
 	public CompanyResp getCompanyByMemCode(String memCode) {

        CompanyResp companyResp = new CompanyResp();
        TCompany company = companyService.findCompanyByMemCode(memCode);
        if (company != null) {
            BeanUtils.copyProperties(company, companyResp);
            return companyResp;
        }
        return null;
     }
    @Override
   	public ResponseModel getApplyRecordList(AccountReq.BaseReq req) {
        PageInfoResp<ApplyRecordVo> pageInfoResp = accountService.getApplyRecordList(req);
        Map<String, Object> data = new HashMap<>();
        data.put("total", pageInfoResp.getTotal());
        data.put("hasNextPage", pageInfoResp.isHasNextPage());
        data.put("list", pageInfoResp.getList());
        data.put("canApplyUser", true);
        List<ApplyRecordVo> list = pageInfoResp.getList();
        for (ApplyRecordVo recordVo : list) {
            if (PartyEnum.Type.user.name().equals(recordVo.getType()) && (AccountEnum.ApplyStatus.APPROVED.getKey().equals(recordVo.getStatus()) || AccountEnum.ApplyStatus.PENDING.getKey().equals(recordVo.getStatus()))) {
                data.put("canApplyUser", false);
                break;
            }
        }
        return ResponseModel.succ(data);
    }

    @Override
    public AccountResp.LoginResp login(AccountReq.LoginReq req) {
        return accountService.login(req);
    }

    @Override
    public AccountExtBindIVo getAppletExtBind(Integer id) {
        return accountService.getAppletExtBind(id);
    }

    @Override
    public AccountExtBindIVo getNumberJumpExtBind(Integer id) {
        return accountService.getNumberJumpExtBind(id);
    }

    @Override
    public AccountResp.BindAccountResp bindAccount(AccountReq.BindAccountReq req) {
        return accountService.bindAccount(req);
    }

    @Override
    public AccountResp selectParty(AccountReq.SelectPartyReq req) {
        return accountService.selectParty(req);
    }

    @Override
    public AccountResp.AuthInfoResp getAuthInfoList(Integer accountId) {
        return accountService.getAuthInfoList(accountId);
    }

    @Transactional
    @Override
    public boolean partyOperateOffline(PartyReq.OperateOfflineReq req) {
        return partyService.operateOffline(req);
    }

    @Override
    public ListResp<TBankAccountVo> getTBankAccounts(TBankAccountReq.BaseReq req) {
        return tBankService.getTBankAccounts(req);
    }

    @Override
    public ResponseModel addTBankAccount(TBankAccountReq.CreateReq req) {
        return tBankService.addTBankAccount(req);
    }

    @Override
    public ResponseModel unbindTBankAccount(TBankAccountReq.BaseReq req) {
        return tBankService.unbindTBankAccount(req);
    }

    @Override
    public PageInfoResp<InviteCommissionVo> getInviteCommissionListByPage(AcctReq.QueryReq req) {
        return acctService.getInviteCommissionListByPage(req);
    }

    @Override
    public InviteCommissionVo getInviteCommission(AcctReq.BaseReq req) {
        return acctService.getInviteCommission(req);
    }

    @Override
    public AcctResp getAcct(Integer partyId, String type) {
        AcctResp resp = new AcctResp();
        TAcct acct = acctService.findAcctByPartyIdAndType(partyId, type);
        if (acct != null) {
            BeanUtils.copyProperties(acct, resp);
        }
        return resp;
    }

    @Override
    public PageInfoResp<AcctDetailVo> getFrontAcctDetailListByPage(AcctReq.QueryReq req) {
        return acctService.getFrontAcctDetailListByPage(req);
    }

    @Override
    public PageInfoResp<AcctDetailVo> getAcctDetailListByPage(AcctReq.QueryReq req) {
        return acctService.getAcctDetailListByPage(req);
    }

    @Override
    public ResponseModel getAcctListByPage(AcctReq.QueryReq req) {
        return acctService.getAcctListByPage(req);
    }

    @Override
    public AcctVo getAcct(AcctReq.BaseReq req) {
        return acctService.getAcct(req);
    }

    @Override
    public AcctDetailVo getFrontAcctDetail(AcctReq.BaseReq req) {
        return acctService.getFrontAcctDetail(req);
    }

    @Override
    public PageInfoResp<CommissionVo> getMyCommissionListByPage(AcctReq.QueryReq req) {
        return acctService.getMyCommissionListByPage(req);
    }

    @Override
    public ListResp<TBankAccountVo> getPlatformTBankAccounts(TBankAccountReq.BaseReq req) {
        return tBankService.getPlatformTBankAccounts(req);
    }

    @Override
    public ResponseModel addPlatformTBankAccount(TBankAccountReq.PlatformCreateReq req) {
        return tBankService.addPlatformTBankAccount(req);
    }

    @Override
    public ResponseModel updatePlatformTBankAccount(TBankAccountReq.PlatformUpdateReq req) {
        return tBankService.updatePlatformTBankAccount(req);
    }

    @Override
    public ResponseModel togglePlatformTBankAccountStatus(TBankAccountReq.BaseReq req) {
        return tBankService.togglePlatformTBankAccountStatus(req);
    }

    @Override
    public ResponseModel getFirstVerifyWithdrawListByPage(AcctReq.QueryReq req) {
        return acctService.getFirstVerifyWithdrawListByPage(req);
    }

    @Override
    public WithdrawAcctDetailVo getFirstVerifyWithdrawDetail(AcctReq.BaseReq req) {
        return acctService.getFirstVerifyWithdrawDetail(req);
    }

    @Override
    public ResponseModel getInvoiceVerifyWithdrawListByPage(AcctReq.QueryReq req) {
        return acctService.getInvoiceVerifyWithdrawListByPage(req);
    }

    @Override
    public WithdrawAcctDetailVo getInvoiceVerifyWithdrawDetail(AcctReq.BaseReq req) {
        return acctService.getInvoiceVerifyWithdrawDetail(req);
    }


    @Override
    public ListResp<UnfinishedTaskVo> getUnfinishedTaskList(AccountReq.BaseReq req) {
        ListResp<UnfinishedTaskVo> resp = new ListResp<>();
        List<UnfinishedTaskVo> list = new ArrayList<>();
        // 已登录未认证用户提示
        if (req.getPartyId() == null) {
            UnfinishedTaskVo taskVo = new UnfinishedTaskVo();
            taskVo.setMessage("您当前未进行<a style='color: #1e4197;text-decoration: underline;' target='_blank' href='/member/profile/apply'>个人/企业认证</a>，无法进行参拍");
            taskVo.setType("1");
            list.add(taskVo);
        }
        if (req.getPartyId() != null) {
            AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(req.getPartyId());
            if (StringUtils.isEmpty(accountBaseDto.getFadadaId()) && !accountBaseDto.isPayBind()) {
                UnfinishedTaskVo taskVo = new UnfinishedTaskVo();
                taskVo.setMessage("您当前未开通支付账户和电子签章无法参拍，请<a style='color: #1e4197;text-decoration: underline;' target='_blank' href='/member/profile'>马上开通</a>>>");
                taskVo.setType("2");
                list.add(taskVo);
            }
            if (StringUtils.isEmpty(accountBaseDto.getFadadaId()) && accountBaseDto.isPayBind()) {
                UnfinishedTaskVo taskVo = new UnfinishedTaskVo();
                taskVo.setMessage("您当前未开通电子签章无法参拍，请<a style='color: #1e4197;text-decoration: underline;' target='_blank' href='/member/profile'>马上开通</a>>>");
                taskVo.setType("2");
                list.add(taskVo);
            }
            if (StringUtils.isNotEmpty(accountBaseDto.getFadadaId()) && !accountBaseDto.isPayBind()) {
                UnfinishedTaskVo taskVo = new UnfinishedTaskVo();
                taskVo.setMessage("您当前未开通支付账户无法参拍，请<a style='color: #1e4197;text-decoration: underline;' target='_blank' href='/member/profile'>马上开通</a>>>");
                taskVo.setType("2");
                list.add(taskVo);
            }
        }
        // 拍卖活动相关
        List<AuctionActivity> activityList = favoriteActivityService.getBeginIn5MinuteList(req.getAccountId(), req.getPartyId());
        for (AuctionActivity activity : activityList) {
            UnfinishedTaskVo taskVo = new UnfinishedTaskVo();
            taskVo.setMessage("您关注的" + activity.getAssetName() + "拍卖会即将开始，请<a style='color: #1e4197;text-decoration: underline;' target='_blank' href='/productDetail?id=" + activity.getId() + "'>立即参与</a>>>");
            taskVo.setType("3");
            list.add(taskVo);

        }
        activityList = favoriteActivityService.getEndIn5MinuteList(req.getAccountId(), req.getPartyId());
        for (AuctionActivity activity : activityList) {
            UnfinishedTaskVo taskVo = new UnfinishedTaskVo();
            taskVo.setMessage("您关注的" + activity.getAssetName() + "拍卖会即将结束，请<a style='color: #1e4197;text-decoration: underline;' target='_blank' href='/productDetail?id=" + activity.getId() + "'>立即参与</a>>>");
            taskVo.setType("4");
            list.add(taskVo);
        }
        activityList = notifyPartyActivityService.getBeginIn5MinuteList(req.getAccountId(), req.getPartyId());
        for (AuctionActivity activity : activityList) {
            UnfinishedTaskVo taskVo = new UnfinishedTaskVo();
            taskVo.setMessage("您设置提醒的" + activity.getAssetName() + "拍卖会即将开始，请<a style='color: #1e4197;text-decoration: underline;' target='_blank' href='/productDetail?id=" + activity.getId() + "'>立即参与</a>>>");
            taskVo.setType("5");
            list.add(taskVo);
        }
        activityList = notifyPartyActivityService.getEndIn5MinuteList(req.getAccountId(), req.getPartyId());
        for (AuctionActivity activity : activityList) {
            UnfinishedTaskVo taskVo = new UnfinishedTaskVo();
            taskVo.setMessage("您设置提醒的" + activity.getAssetName() + "拍卖会即将结束，请<a style='color: #1e4197;text-decoration: underline;' target='_blank' href='/productDetail?id=" + activity.getId() + "'>立即参与</a>>>");
            taskVo.setType("6");
            list.add(taskVo);
        }
        // 预招商相关
        List<EnrollingActivity> enrollingActivityList = favoriteEnrollingActivityService.getBeginIn5MinuteList(req.getAccountId(), req.getPartyId());
        for (EnrollingActivity enrollingActivity : enrollingActivityList) {
            UnfinishedTaskVo taskVo = new UnfinishedTaskVo();
            taskVo.setMessage("您关注的" + enrollingActivity.getName() + "招商会即将开始，请<a style='color: #1e4197;text-decoration: underline;' target='_blank' href='/loanDetail?activityId=" + enrollingActivity.getId() + "'>立即参与</a>>>");
            taskVo.setType("3");
            list.add(taskVo);
        }
        enrollingActivityList = favoriteEnrollingActivityService.getEndIn5MinuteList(req.getAccountId(), req.getPartyId());
        for (EnrollingActivity enrollingActivity : enrollingActivityList) {
            UnfinishedTaskVo taskVo = new UnfinishedTaskVo();
            taskVo.setMessage("您关注的" + enrollingActivity.getName() + "招商会即将结束，请<a style='color: #1e4197;text-decoration: underline;' target='_blank' href='/loanDetail?activityId=" + enrollingActivity.getId() + "'>立即参与</a>>>");
            taskVo.setType("4");
            list.add(taskVo);
        }
        enrollingActivityList = notifyPartyEnrollingActivityService.getBeginIn5MinuteList(req.getAccountId(), req.getPartyId());
        for (EnrollingActivity enrollingActivity : enrollingActivityList) {
            UnfinishedTaskVo taskVo = new UnfinishedTaskVo();
            taskVo.setMessage("您设置提醒的" + enrollingActivity.getName() + "招商会即将开始，请<a style='color: #1e4197;text-decoration: underline;' target='_blank' href='/loanDetail?activityId=" + enrollingActivity.getId() + "'>立即参与</a>>>");
            taskVo.setType("5");
            list.add(taskVo);
        }
        enrollingActivityList = notifyPartyEnrollingActivityService.getEndIn5MinuteList(req.getAccountId(), req.getPartyId());
        for (EnrollingActivity enrollingActivity : enrollingActivityList) {
            UnfinishedTaskVo taskVo = new UnfinishedTaskVo();
            taskVo.setMessage("您设置提醒的" + enrollingActivity.getName() + "招商会即将结束，请<a style='color: #1e4197;text-decoration: underline;' target='_blank' href='/loanDetail?activityId=" + enrollingActivity.getId() + "'>立即参与</a>>>");
            taskVo.setType("6");
            list.add(taskVo);
        }
        if (req.getPartyId() != null) {
            activityList = depositService.getBeginIn5MinuteList(req.getPartyId());
            for (AuctionActivity activity : activityList) {
                UnfinishedTaskVo taskVo = new UnfinishedTaskVo();
                taskVo.setMessage("您参与的" + activity.getAssetName() + "拍卖会即将开始，请<a style='color: #1e4197;text-decoration: underline;' target='_blank' href='/productDetail?id=" + activity.getId() + "'>立即参与</a>>>");
                taskVo.setType("7");
                list.add(taskVo);
            }
            activityList = depositService.getEndIn5MinuteList(req.getPartyId());
            for (AuctionActivity activity : activityList) {
                UnfinishedTaskVo taskVo = new UnfinishedTaskVo();
                taskVo.setMessage("您参与的" + activity.getAssetName() + "拍卖会即将结束，请<a style='color: #1e4197;text-decoration: underline;' target='_blank' href='/productDetail?id=" + activity.getId() + "'>立即参与</a>>>");
                taskVo.setType("8");
                list.add(taskVo);
            }
            enrollingActivityList = enrollingDepositService.getBeginIn5MinuteList(req.getPartyId());
            for (EnrollingActivity enrollingActivity : enrollingActivityList) {
                UnfinishedTaskVo taskVo = new UnfinishedTaskVo();
                taskVo.setMessage("您参与的" + enrollingActivity.getName() + "招商会即将开始，请<a style='color: #1e4197;text-decoration: underline;' target='_blank' href='/productDetail?id=" + enrollingActivity.getId() + "'>立即参与</a>>>");
                taskVo.setType("7");
                list.add(taskVo);
            }
            enrollingActivityList = enrollingDepositService.getEndIn5MinuteList(req.getPartyId());
            for (EnrollingActivity enrollingActivity : enrollingActivityList) {
                UnfinishedTaskVo taskVo = new UnfinishedTaskVo();
                taskVo.setMessage("您参与的" + enrollingActivity.getName() + "招商会即将结束，请<a style='color: #1e4197;text-decoration: underline;' target='_blank' href='/productDetail?id=" + enrollingActivity.getId() + "'>立即参与</a>>>");
                taskVo.setType("8");
                list.add(taskVo);
            }
            List<AuctionOrder> orderList = auctionOrderService.getBuyerNeedToPaidList(req.getPartyId());
            for (AuctionOrder order : orderList) {
                try {
                    AuctionActivity activity = auctionActivityService.getById(order.getActivityId());
                    AuctionReq auctionReq = new AuctionReq();
                    auctionReq.setOrderId(order.getId());
                    auctionReq.setPartyId(req.getPartyId());
                    DfftResp dfftResp = auctionFacade.pay(auctionReq);
                    if (dfftResp == null) {
                        continue;
                    }
                    JSONObject parameters = new JSONObject();
                    parameters.put("orderId", order.getId() + "");
                    parameters.put("param", dfftResp.getParam());
                    parameters.put("url", dfftResp.getUrl());
                    UnfinishedTaskVo taskVo = new UnfinishedTaskVo();
                    taskVo.setMessage("您参与的" + activity.getAssetName() + "拍卖会尾款尚未缴纳，请");
                    taskVo.setType("9");
                    taskVo.setParameters(parameters);
                    list.add(taskVo);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        resp.setList(list);
        return resp;
    }

    @Override
    @Transactional
    public boolean verifySpv(Integer spvApplyId, String operation, Integer operatorId) {
        if (SystemConstant.OPERATION_REJECT.equals(operation)) {
            //拒绝
            spvService.updateSpvApply(spvApplyId, "REJECT", operatorId);

        } else if (SystemConstant.OPERATION_APPROVE.equals(operation)) {
            TSpvApply spvApply = spvService.getSpvApplyById(spvApplyId);
            Integer companyId = spvApply.getCompanyId();
            String mobile = spvApply.getMobile();
            checkSpv(mobile, operation, companyId);

            if (!spvApply.getStatus().equals("PENDING")) {
                throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
            }

            spvService.updateSpvApply(spvApplyId, "APPROVED", operatorId);
            TSpv spv = new TSpv();
            spv.setName(spvApply.getName());
            spv.setMobile(spvApply.getMobile());
            spv.setLicense(spvApply.getLicense());
            spv.setCompanyId(spvApply.getCompanyId());
            spv.setDfftId(BusinessUtil.genDfftId());
            try {
                spvService.saveSpv(spv);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return true;
    }

    @Override
    public List<CompanyResp> getCompanyListByEmployAccountId(Integer accountId) {
        List<CompanyResp> companyList = new ArrayList<CompanyResp>();
        List<AccountCompanyMap> companyMapList = accountCompanyMapService.getCompanyListByAccountId(accountId);
        if(companyMapList != null){
            for(AccountCompanyMap companyMap : companyMapList){
                TCompany company = companyService.findCompanyById(companyMap.getCompanyId());
                if(company != null){
                    CompanyResp companyResp = new CompanyResp();
                    BeanUtils.copyProperties(company, companyResp);
                    companyList.add(companyResp);
                }

            }
        }

        return companyList;
    }

    @Override
    public String getDisposerApplyStatusByAccountId(Integer accountId, String type) {
        return accountService.getDisposerApplyStatusByAccountId(accountId,type);
    }

    @Override
    public ResponseModel getFundProvider(Integer providerId) {
        return fundService.getFundProvider(providerId);
    }

    @Override
    public ResponseModel getFundProviderApply(Integer applyId) {
        return fundService.getFundProviderApply(applyId);
    }

    @Override
    public PageInfoResp<DisposeProvider> getRecommendDisposeProviderList(AccountReq.QueryReq req) {
        return disposeService.getRecommendDisposeProviderList(req);
    }

    @Override
    public void subscribeMp(String openId) {
        accountService.subscribeMp(openId);
    }

    @Override
    public ResponseModel getAgencyList(AgencyReq.BaseReq req) {
        return agencyService.getAgencyList(req);
    }

    @Override
    public ResponseModel searchAgencyList(AgencyReq.QueryReq req) {
        return agencyService.searchAgencyList(req);
    }

    @Override
    public boolean checkUserIsSubscribeMp360(Integer extBindId) {
        return accountService.checkUserIsSubscribeMp360(extBindId);
    }

    @Override
    public ResponseModel getViewRecords(AcctReq.ViewRecordRequest viewRecordRequest) {
        PageInfoResp pageResult = new PageInfoResp<>();
        PageInfo pageInfo = new PageInfo();

        if(viewRecordRequest.getPartyId() != null) {
           tAccountViewRecordService.updateActivityByPartyId(viewRecordRequest);
        }

        if(viewRecordRequest.getType().equals(AccountEnum.ViewType.ENROLLING.getKey())) {
            pageInfo = enrollingActivityService.getActivitysByAccountId(viewRecordRequest);

            pageResult.setTotal(pageInfo.getTotal());
            pageResult.setList(pageInfo.getList());
            pageResult.setHasNextPage(pageInfo.isHasNextPage());

        }else if(viewRecordRequest.getType().equals(AccountEnum.ViewType.AUCTION.getKey())) {

            pageInfo= auctionActivityService.getAuctionActivityByAccountId(viewRecordRequest);
            pageResult.setTotal(pageInfo.getTotal());
            pageResult.setList(pageInfo.getList());
            pageResult.setHasNextPage(pageInfo.isHasNextPage());


        }else if(viewRecordRequest.getType().equals(AccountEnum.ViewType.DISPOSAL.getKey())){
            pageInfo = disposalRequirementService.getDisposalActivityByAccountId(viewRecordRequest);
            pageResult.setTotal(pageInfo.getTotal());
            pageResult.setList(pageInfo.getList());
            pageResult.setHasNextPage(pageInfo.isHasNextPage());

        }
        return ResponseModel.succ(pageResult);
    }
}
