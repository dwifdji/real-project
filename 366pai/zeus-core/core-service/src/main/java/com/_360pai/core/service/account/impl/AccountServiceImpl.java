package com._360pai.core.service.account.impl;

import com._360pai.arch.common.ListResp;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.exception.ExceptionEnumImpl;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.AppletEnum;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.condition.account.TAccountCondition;
import com._360pai.core.condition.fastway.TFastwayAgencyApplyCondition;
import com._360pai.core.condition.fastway.TFastwayDisposeApplyCondition;
import com._360pai.core.dao.account.*;
import com._360pai.core.dao.applet.TAppletShopDao;
import com._360pai.core.dao.assistant.BankDao;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dao.assistant.FavoriteActivityDao;
import com._360pai.core.dao.numberJump.TDebtCalculatorDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.AccountReq;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.account.resp.AccountResp;
import com._360pai.core.facade.account.vo.*;
import com._360pai.core.model.account.*;
import com._360pai.core.model.applet.TAppletShop;
import com._360pai.core.model.assistant.Bank;
import com._360pai.core.model.assistant.City;
import com._360pai.core.model.fastway.TFastwayAgencyApply;
import com._360pai.core.model.fastway.TFastwayDisposeApply;
import com._360pai.core.service.account.*;
import com._360pai.core.service.applet.TAppletMessageService;
import com._360pai.core.service.fastway.AgencyApplyService;
import com._360pai.core.service.fastway.DisposeApplyService;
import com._360pai.core.service.fastway.impl.AgencyApplyServiceImpl;
import com._360pai.core.service.fastway.impl.FundApplyServiceImpl;
import com._360pai.core.utils.RespConvertUtil;
import com._360pai.gateway.facade.WxFacade;
import com._360pai.gateway.resp.wx.MpUserInfoResp;
import com._360pai.gateway.resp.wx.OpenIdResp;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private TAccountDao tAccountDao;
    @Resource
    private BankDao     bankDao;
    @Resource
    private TAgencyDao  agencyDao;

    @Resource
    private TUserDao userDao;

    @Resource
    private TCompanyDao companyDao;

    @Resource
    private TPartyDao partyDao;

    @Resource
    private FundService fundService;

    @Resource
    private DisposeService disposeService;

    @Autowired
    private SystemProperties systemProperties;

    @Autowired
    private BankAccountDao bankAccountDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private TAccountExtBindDao accountExtBindDao;
    @Reference(version = "1.0.0")
    private WxFacade wxFacade;
    @Autowired
    private TUserApplyRecordDao userApplyRecordDao;
    @Autowired
    private TCompanyApplyRecordDao companyApplyRecordDao;

    @Autowired
    private TAppletShopDao tAppletShopDao;
    @Autowired
    private TAppletMessageService appletMessageService;
    @Autowired
    private FavoriteActivityDao favoriteActivityDao;
    @Autowired
    private TAcctDao tAcctDao;
    @Autowired
    private DisposeApplyService disposeApplyService;
    @Autowired
    private TAgencyApplyRecordDao agencyApplyRecordDao;
    @Autowired
    private AgencyApplyService agencyApplyService;
    @Autowired
    private FundApplyServiceImpl fundApplyServiceImpl;
    @Autowired
    private TDebtCalculatorDao debtCalculatorDao;

    @Override
    public TAccount selectByPrimaryKey(Integer id) {
        return tAccountDao.selectById(id);
    }

    @Override
    public TAccount findAccountByMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            throw new BusinessException(ApiCallResult.EMPTY.getCode(), "手机号不能为空");
        }
        TAccountCondition accountCondition = new TAccountCondition();
        accountCondition.setMobile(mobile);
        return tAccountDao.selectFirst(accountCondition);
    }

    @Override
    public int updateById(TAccount account) {
        account.setUpdateTime(new Date());
        return tAccountDao.updateById(account);
    }

    @Override
    public int insert(TAccount param) {
        param.setCreateTime(new Date());
        param.setUpdateTime(new Date());
        return tAccountDao.insert(param);
    }


    @Override
    public AccountBaseDto getAcctBaseByPartyIdAndType(Integer partyId, String type) {
        AccountBaseDto dto = new AccountBaseDto();
        if(AccountEnum.AcctType.AGENCY.getKey().equals(type)){
            TAgency agency = agencyDao.selectById(partyId);
            dto.setName(agency.getName());
            dto.setMobile(agency.getMobile());
            dto.setFadadaId(agency.getFadadaId());
        }else if(AccountEnum.AcctType.USER.getKey().equals(type)){
            TUser user = userDao.selectById(partyId);
            dto.setName(user.getName());
            dto.setMobile(user.getMobile());
            dto.setFadadaId(user.getFadadaId());
        }else{
            TCompany company = companyDao.selectById(partyId);
            dto.setName(company.getName());
            dto.setMobile(company.getMobile());
            dto.setFadadaId(company.getFadadaId());
        }
        return dto;
    }


    public String getDisposerApplyStatusByAccountId(Integer accountId,String type){
        TFastwayDisposeApplyCondition disposeApplyCondition = new TFastwayDisposeApplyCondition();
        if(type.equals("accountId")){
            disposeApplyCondition.setAccountId(accountId);
        }else if(type.equals("partyId")){
            disposeApplyCondition.setPartyId(accountId);
        }
        List<TFastwayDisposeApply> list = disposeApplyService.findByCondition(disposeApplyCondition);
        if(list == null || list.isEmpty()){
            return "NOAPPLY";
        }else{
            TFastwayDisposeApply fastwayDisposeApply = list.get(0);
            if ("10".equals(fastwayDisposeApply.getApplyStatus())) {
                return "PENDING";
            }else if("20".equals(fastwayDisposeApply.getApplyStatus())){
                return "APPROVED";
            }else{
                return "REJECT";
            }
        }
    }

    public String getAgencyApplyStatusByAccountId(Integer accountId,Integer partyId){
        TFastwayAgencyApplyCondition condition = new TFastwayAgencyApplyCondition();
        condition.setAccountId(accountId);
//        condition.setPartyId(partyId);
        condition.setIsDel(false);
        List<TFastwayAgencyApply> list = agencyApplyService.selectByCondition(condition);
        if(list == null || list.isEmpty()){
            return "NOAPPLY";
        }else{
            TFastwayAgencyApply fastwayAgencyApply = list.get(0);
            if ("10".equals(fastwayAgencyApply.getApplyStatus())) {
                return "PENDING";
            }else if("20".equals(fastwayAgencyApply.getApplyStatus())){
                return "APPROVED";
            }else{
                return "REJECT";
            }
        }
    }

    @Override
    public void subscribeMp(String openId) {
        MpUserInfoResp resp = wxFacade.getMpUserInfo(openId);
        if (resp == null || !"000".equals(resp.getCode())) {
            log.error("获取公众号用户信息失败");
            return;
        }
        TAccountExtBind extBind = accountExtBindDao.findMp360PaiByOpenId(openId);
        if (extBind != null) {
            extBind.setUnionId(resp.getUnionId());
            extBind.setNickName(StringEscapeUtils.escapeJava(resp.getNickName()));
            extBind.setHeadImgUrl(resp.getHeadImgUrl());
            accountExtBindDao.updateById(extBind);
        } else {
            extBind = new TAccountExtBind();
            extBind.setExtUserId(openId);
            extBind.setUnionId(resp.getUnionId());
            extBind.setNickName(StringEscapeUtils.escapeJava(resp.getNickName()));
            extBind.setHeadImgUrl(resp.getHeadImgUrl());
            extBind.setExtType(AccountEnum.ExtType.MP_360PAI.getKey());
            accountExtBindDao.insert(extBind);
        }
    }

    @Override
    public boolean checkUserIsSubscribeMp360(Integer extBindId) {
        TAccountExtBind extBind = accountExtBindDao.selectById(extBindId);
        if (StringUtils.isNotBlank(extBind.getUnionId())) {
            TAccountExtBind accountExtBind = accountExtBindDao.findMp360PaiBy(extBind.getUnionId());
            if (accountExtBind != null) {
                MpUserInfoResp resp = wxFacade.getMpUserInfo(accountExtBind.getExtUserId());
                if (resp != null && "000".equals(resp.getCode())) {
                    if (!"0".equals(resp.getSubscribe())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String getDisposerApplyStatus(Integer partyId){
        TDisposeProviderApply disposeProviderApply = disposeService.getDisposeProviderApplyByPartyId(partyId);
        if(disposeProviderApply == null){
            return getDisposerApplyStatusByAccountId(partyId,"partyId");
        }else{
            return disposeProviderApply.getStatus();
        }
    }

    @Override
    public String getAgencyApplyStatus(Integer accountId, Integer partyId, Integer agencyId) {
//        TAgencyApplyRecordCondition condition = new TAgencyApplyRecordCondition();
//        condition.setAccountId(accountId);
//        condition.setIsDel(0);
//        TAgencyApplyRecord agencyApplyRecord = agencyApplyRecordDao.selectFirst(condition);
//        if (agencyApplyRecord == null) {
//            return getAgencyApplyStatusByAccountId(accountId, partyId);
//        } else {
//            return agencyApplyRecord.getStatus();
//        }
        String agencyAuthCode = agencyApplyService.getAgencyAuthCode(accountId, partyId, agencyId);
        if (AgencyApplyServiceImpl.PENDING.equals(agencyAuthCode))
        {
            return "PENDING";
        } else if (AgencyApplyServiceImpl.APPROVED.equals(agencyAuthCode))
        {
            return "APPROVED";
        } else if (AgencyApplyServiceImpl.REJECT.equals(agencyAuthCode))
        {
            return "REJECT";
        } else
            {
                return "NOAPPLY";
            }
    }

    @Override
    public String getFundApplyStatus(Integer accountId, Integer partyId, String applyType) {
        String userAuthCode = fundApplyServiceImpl.getUserAuthCode(accountId, partyId, applyType);
        if (AgencyApplyServiceImpl.PENDING.equals(userAuthCode))
        {
            return "PENDING";
        } else if (AgencyApplyServiceImpl.APPROVED.equals(userAuthCode))
        {
            return "APPROVED";
        } else if (AgencyApplyServiceImpl.REJECT.equals(userAuthCode))
        {
            return "REJECT";
        } else
        {
            return "NOAPPLY";
        }
    }

    @Override
    public AccountBaseDto getAccountBaseByPartyId(Integer partyId) {


        AccountBaseDto accountBaseDto = new AccountBaseDto();
        if (null == partyId) {
            return accountBaseDto;
        }

        TParty party = partyDao.selectById(partyId);
        if (party == null) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }
        if (SystemConstant.ACCOUNT_USER_TYPE.equals(party.getType())) {
            // user 用户
            TUser user = userDao.selectById(party.getId());
            accountBaseDto.setMobile(user.getMobile());
            accountBaseDto.setName(user.getName());
            accountBaseDto.setAccountId(user.getAccountId());
            accountBaseDto.setDefaultAgencyId(user.getDefaultAgencyId());
            accountBaseDto.setDfftId(user.getDfftId());
            accountBaseDto.setFadadaId(user.getFadadaId());
            accountBaseDto.setPayBind(user.getPayBind() != null && user.getPayBind() == 1);
            accountBaseDto.setChannel(user.getIsChannel() != null && user.getIsChannel() == 1);
            accountBaseDto.setPartyPrimaryId(partyId);
            accountBaseDto.setAccountAuthTime(DateUtil.formatDate2Str(user.getCreateTime(), DateUtil.NORM_DATETIME_PATTERN));
            accountBaseDto.setType(party.getType());
            accountBaseDto.setAccountAuth(true);
            accountBaseDto.setBank(false);
            accountBaseDto.setIdOrLicenceNo(user.getCertificateNumber());
            accountBaseDto.setOperOffline(user.getOperOffline());
            accountBaseDto.setOperWithoutFadada(user.getOperWithoutFadada());
            accountBaseDto.setAddress(user.getAddress());
            accountBaseDto.setLegalPerson(user.getName());
            TDisposeProvider disposeProvider = disposeService.getDisposeProviderByPartyId(user.getId());
            accountBaseDto.setDisposer(disposeProvider != null && disposeProvider.getId() != null);

            //处置服务商申请状态
            accountBaseDto.setDisposerStatus(getDisposerApplyStatus(user.getId()));

            TFundProvider fundProvider = fundService.getFundProviderByPartyId(user.getId());
            accountBaseDto.setFunder(fundProvider != null && fundProvider.getId() != null);
        } else if (SystemConstant.ACCOUNT_COMPANY_TYPE.equals(party.getType())) {
            // 企业用户
            TCompany company = companyDao.selectById(partyId);
            accountBaseDto.setMobile(company.getMobile());
            accountBaseDto.setName(company.getName());
            accountBaseDto.setAccountId(company.getAccountId());
            accountBaseDto.setDefaultAgencyId(company.getDefaultAgencyId());
            accountBaseDto.setDfftId(company.getDfftId());
            accountBaseDto.setFadadaId(company.getFadadaId());
            accountBaseDto.setFadadaEmail(company.getFadadaEmail());
            accountBaseDto.setPayBind(company.getPayBind() != null && company.getPayBind() == 1);
            accountBaseDto.setChannel(company.getIsChannel() != null && company.getIsChannel() == 1);
            accountBaseDto.setPartyPrimaryId(partyId);
            accountBaseDto.setType(party.getType());
            accountBaseDto.setAccountAuthTime(DateUtil.formatDate2Str(company.getCreateTime(), DateUtil.NORM_DATETIME_PATTERN));
            accountBaseDto.setAccountAuth(true);
            accountBaseDto.setIdOrLicenceNo(company.getLicense());
            if (company.getChannelPay() == 1||PartyEnum.Category.LEASE_COMPANY.getKey().equals(party.getCategory())) {
                accountBaseDto.setBank(true);
                List<BankAccount> bankAccountList = bankAccountDao.getByPartyId(company.getId());
                if (bankAccountList != null && !bankAccountList.isEmpty()) {
                    BankAccount bankAccount = bankAccountList.get(0);
                    if (bankAccount != null) {
                        Bank bank = bankDao.getById(bankAccount.getBankId());
                        if (bank != null) {
                            accountBaseDto.setBankId(bank.getId());
                            accountBaseDto.setBankCode(bank.getCode());
                            accountBaseDto.setBankName(bank.getName());
                        } else {
                            accountBaseDto.setBankName(bankAccount.getBankName());
                        }
                        accountBaseDto.setBankAccountNo(bankAccount.getNumber());
                        accountBaseDto.setBankAccountName(bankAccount.getName());
                        accountBaseDto.setBranchBankName(bankAccount.getBranchBankName());
                    }
                }


            } else {
                accountBaseDto.setBank(false);
            }
            accountBaseDto.setIdOrLicenceNo(company.getLicense());
            accountBaseDto.setOperOffline(company.getOperOffline());
            accountBaseDto.setOperWithoutFadada(company.getOperWithoutFadada());
            accountBaseDto.setAddress(company.getAddress());
            accountBaseDto.setLegalPerson(company.getLegalPerson());
            TFundProvider fundProvider = fundService.getFundProviderByPartyId(company.getId());
            accountBaseDto.setFunder(fundProvider != null && fundProvider.getId() != null);

            TDisposeProvider disposeProvider = disposeService.getDisposeProviderByPartyId(company.getId());
            accountBaseDto.setDisposer(disposeProvider != null && disposeProvider.getId() != null);
            //处置服务商申请状态
            accountBaseDto.setDisposerStatus(getDisposerApplyStatus(company.getId()));

        }

        TAccount  acct = tAccountDao.selectById(accountBaseDto.getAccountId());
        if(AccountEnum.RegisterSource.APPLET.getKey().equals(acct.getRegisterSource())) {
            accountBaseDto.setFromApplet(true);
        }
            TAccountExtBind  accountExtBind = accountExtBindDao.findAppletByAccountId(acct.getId());
            if(accountExtBind != null){

                if(AccountEnum.InviteType.JG.getKey().equals(accountExtBind.getInviteType())){
                    accountBaseDto.setParentId(accountExtBind.getInviteCode());
                    accountBaseDto.setParentType(AccountEnum.AcctType.AGENCY.getKey());
                    TAgency tAgency = agencyDao.selectById(accountExtBind.getInviteCode());
                    if(tAgency != null){
                        accountBaseDto.setShopCommissionPercent(tAgency.getShopCommissionPercent());
                    }
                }else{
                    TAppletShop shop = tAppletShopDao.selectById(accountExtBind.getInviteCode());
                    if(shop != null){

                        if(shop.getPartyId() == null){
                            accountBaseDto.setParentId(shop.getId());
                            accountBaseDto.setParentType(AccountEnum.AcctType.SHOP.getKey());
                            accountBaseDto.setShopCommissionPercent(shop.getShopCommissionPercent());

                            if(AccountEnum.InviteType.JG.getKey().equals(shop.getInviteType())){
                                accountBaseDto.setPparentId(shop.getInviteCode());
                                accountBaseDto.setPparentType(AccountEnum.AcctType.AGENCY.getKey());
                            }else{
                                TAppletShop parentShop = tAppletShopDao.selectById(shop.getInviteCode());

                                if(parentShop.getPartyId() == null){
                                    accountBaseDto.setPparentId(parentShop.getId());
                                    accountBaseDto.setPparentType(AccountEnum.AcctType.SHOP.getKey());
                                }else{
                                    TParty parentParty = partyDao.selectById(parentShop.getPartyId());
                                    accountBaseDto.setPparentId(parentParty.getId());
                                    accountBaseDto.setPparentType(parentParty.getType());
                                }

                            }

                        }else{
                            TParty tParty = partyDao.selectById(shop.getPartyId());
                            accountBaseDto.setParentId(tParty.getId());
                            accountBaseDto.setParentType(tParty.getType());
                            accountBaseDto.setShopCommissionPercent(shop.getShopCommissionPercent());
                            if(AccountEnum.InviteType.JG.getKey().equals(shop.getInviteType())){
                                accountBaseDto.setPparentId(shop.getInviteCode());
                                accountBaseDto.setPparentType(AccountEnum.AcctType.AGENCY.getKey());
                            }else{

                                TAppletShop parentShop = tAppletShopDao.selectById(shop.getInviteCode());

                                if(parentShop.getPartyId() == null){
                                    accountBaseDto.setPparentId(parentShop.getId());
                                    accountBaseDto.setPparentType(AccountEnum.AcctType.SHOP.getKey());
                                }else{
                                    TParty parentParty = partyDao.selectById(parentShop.getPartyId());
                                    accountBaseDto.setPparentType(parentParty.getType());
                                    accountBaseDto.setPparentId(parentParty.getId());

                                }


                            }
                        }


                    }
                }
            }
        return accountBaseDto;
    }

    @Autowired
    private UserService              userService;
    @Autowired
    private AccountCompanyMapService accountCompanyMapService;
    @Autowired
    private CompanyService           companyService;

    @Override
    public TAccount getAgencyAdminAccount(Integer agencyId) {
        TAccountCondition condition = new TAccountCondition();
        condition.setAgencyId(agencyId);
        condition.setIsAgencyAdmin(1);
        return tAccountDao.selectFirst(condition);
    }

    @Override
    public PageInfoResp<AccountVo> getAccountListByPage(AccountReq.QueryReq req) {
        PageInfoResp<AccountVo> resp   = new PageInfoResp<>();
        Map<String, Object>     params = new HashMap<>();
        params.put("agencyId", req.getAgencyId());
        if (StringUtils.isNotBlank(req.getQ())) {
            params.put("q", req.getQ());
        }
        if (StringUtils.isNotBlank(req.getStatus())) {
            params.put("status", req.getStatus());
        }
        if (StringUtils.isNotBlank(req.getRegisterSource())) {
            params.put("registerSource", req.getRegisterSource());
        }
        PageInfo        pageInfo  = tAccountDao.getAccountList(req.getPage(), req.getPerPage(), params, "a.id desc");
        List<AccountVo> itemsList = new ArrayList<>();
        List<TAccount>  accounts  = pageInfo.getList();
        for (TAccount account : accounts) {
            itemsList.add(processAccount(account));
        }
        resp.setList(itemsList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public AccountVo getAccountById(AccountReq.BaseReq req) {
        TAccount account = selectByPrimaryKey(req.getAccountId());
        if (account == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        return processAccount(account);
    }

    @Override
    public PageInfoResp<AccountVo> getCompanyListByPage(AccountReq.QueryReq req) {
        PageInfoResp<AccountVo> resp   = new PageInfoResp<>();
        Map<String, Object>     params = new HashMap<>();
        params.put("companyId", req.getCompanyId());
        PageInfo        pageInfo  = tAccountDao.getCompanyAccountList(req.getPage(), req.getPerPage(), params, "a.id desc");
        List<AccountVo> itemsList = new ArrayList<>();
        List<TAccount>  accounts  = pageInfo.getList();
        for (TAccount account : accounts) {
            itemsList.add(processAccount(account));
        }
        resp.setList(itemsList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public AccountResp agencyAccountAdd(AccountReq.BaseReq req) {
        AccountResp resp       = new AccountResp();
        TAccount    curAccount = tAccountDao.selectById(req.getOperatorId());
        if (curAccount.getIsAgencyAdmin() == null || !curAccount.getIsAgencyAdmin().equals(1)) {
            throw new BusinessException("只有管理员才可以添加业务员");
        }
        TAccount account = tAccountDao.getByMobile(req.getMobile());
        if (account == null || account.getAgencyId() != null) {
            throw new BusinessException("无满足当前要求的账户,该账户不存在或者被其他机构绑定");
        }
        account.setAgencyId(curAccount.getAgencyId());
        account.setIsAgencyAdmin(0);
        int result = tAccountDao.updateById(account);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return resp;
    }

    @Override
    public AccountResp agencyAccountDelete(AccountReq.BaseReq req) {
        AccountResp resp       = new AccountResp();
        TAccount    curAccount = tAccountDao.selectById(req.getOperatorId());
        if (curAccount.getIsAgencyAdmin() == null || !curAccount.getIsAgencyAdmin().equals(1)) {
            throw new BusinessException("只有管理员才可以删除业务员");
        }
        TAccount account = tAccountDao.selectById(req.getAccountId());
        if (account == null || account.getAgencyId() == null) {
            throw new BusinessException("无满足当前要求的账户,该账户不存在或者未绑定机构");
        }
        if (curAccount.getId().equals(account.getId())) {
            throw new BusinessException("无法删除自己");
        }
        if (!curAccount.getAgencyId().equals(account.getAgencyId())) {
            throw new BusinessException("只能删除本机构的业务员");
        }
        int result = tAccountDao.unBindAgency(account.getId());
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return resp;
    }

    @Override
    public String getNotifierMobile(Integer partyId) {
        TParty party = partyDao.selectById(partyId);
        if (party == null) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }
        if (SystemConstant.ACCOUNT_USER_TYPE.equals(party.getType())) {
            TUser    user    = userDao.selectById(party.getId());
            TAccount account = tAccountDao.selectById(user.getAccountId());
            if (account != null) {
                return account.getMobile();
            }
        } else if (SystemConstant.ACCOUNT_COMPANY_TYPE.equals(party.getType())) {
            // 企业用户
            TCompany company = companyDao.selectById(partyId);
            TAccount account = tAccountDao.selectById(company.getAccountId());
            if (account != null) {
                return account.getMobile();
            }
        }
        return null;
    }

    @Override
    public String getAgencyNotifierMobile(Integer agencyId) {
        TAccountCondition condition = new TAccountCondition();
        condition.setAgencyId(agencyId);
        condition.setIsAgencyAdmin(1);
        TAccount account = tAccountDao.selectFirst(condition);
        if (account != null) {
            return account.getMobile();
        }
        return null;
    }

    @Override
    public String getShopNotifierMobile(Integer shopId) {
        TAppletShop shop = tAppletShopDao.selectById(shopId);
        if (shop != null) {
            return shop.getMobile();
        }
        return "";
    }

    @Override
    public List<String> getPlatformNotifierMobile() {
        String phones = systemProperties.getPlatformCustomerServicePhone();
        if (StringUtils.isNotEmpty(phones)) {
            return Arrays.asList(phones.split(","));
        }
        return Collections.EMPTY_LIST;
    }

    private AccountVo processAccount(TAccount account) {
        AccountVo vo = RespConvertUtil.convertToAccountVo(account);
        vo.setDefaultAgency(RespConvertUtil.convertToAgencyVo(agencyDao.selectById(account.getDefaultAgencyId())));
        if (account.getAgencyId() != null) {
            vo.setAgency(RespConvertUtil.convertToAgencyVo(agencyDao.selectById(account.getAgencyId())));
        }
        TUser user = userDao.getByAccountId(account.getId());
        if (user != null) {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);
            if (StringUtils.isNotEmpty(user.getCityId())) {
                City city = cityDao.selectById(user.getCityId());
                userVo.setCityName(city.getName());
            }
            vo.setUser(userVo);
        }
        List<TCompany> companyList = companyDao.getByAccountId(account.getId());
        if (companyList.size() > 0) {
            List<CompanyVo> companies = new ArrayList<>();
            for (TCompany company : companyList) {
                CompanyVo companyVo = new CompanyVo();
                BeanUtils.copyProperties(company, companyVo);
                if (StringUtils.isNotEmpty(company.getCityId())) {
                    companyVo.setCityName(cityDao.getName(company.getCityId()));
                }
                if (StringUtils.isNotEmpty(company.getRegisterCityId())) {
                    companyVo.setRegisterCityName(cityDao.getName(company.getRegisterCityId()));
                }
                if (StringUtils.isNotEmpty(company.getCityId())) {
                    companyVo.setProvinceId(cityDao.getProvinceId(company.getCityId()) + "");
                }
                if (StringUtils.isNotEmpty(company.getRegisterCityId())) {
                    companyVo.setRegisterProvinceId(cityDao.getProvinceId(company.getRegisterCityId()) + "");
                }
                companies.add(companyVo);
            }
            vo.setCompanies(companies);
        } else {
            vo.setCompanies(Collections.EMPTY_LIST);
        }
        return vo;
    }

    @Override
    public PartyAccount getPartyAccountById(Integer id) {
        PartyAccount vo = new PartyAccount();
        TParty party = partyDao.selectById(id);
        if (party != null) {
            vo.setId(party.getId());
            vo.setType(party.getType());
            if (PartyEnum.Type.user.name().equals(party.getType())) {
                TUser user = userDao.selectById(party.getId());
                if (user != null) {
                    vo.setCertificateNumber(user.getCertificateNumber());
                    vo.setMobile(user.getMobile());
                    vo.setName(user.getName());
                }
            } else if (PartyEnum.Type.company.name().equals(party.getType())) {
                TCompany company = companyDao.selectById(party.getId());
                if (company != null) {
                    vo.setMobile(company.getMobile());
                    vo.setName(company.getName());
                    vo.setCertificateNumber(company.getLicense());
                }
            }
        }
        return vo;
    }

    @Override
    public PageInfoResp<ApplyRecordVo> getApplyRecordList(AccountReq.BaseReq req) {
        PageInfoResp<ApplyRecordVo> resp = new PageInfoResp<>();
        Map<String, Object> params = new HashMap<>();
        params.put("accountId", req.getAccountId());

        PageInfo pageInfo = tAccountDao.getApplyRecordList(req.getPage(), req.getPerPage(), params, "");
        List<ApplyRecordVo> itemsList = new ArrayList<>();
        List<ApplyRecordVo> applyRecordVoList = pageInfo.getList();
        for (ApplyRecordVo applyRecordVo : applyRecordVoList) {
            applyRecordVo.setStatusDesc(AccountEnum.ApplyStatus.getValueByKey(applyRecordVo.getStatus()));
            itemsList.add(applyRecordVo);
        }
        resp.setList(itemsList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public AccountResp.LoginResp login(AccountReq.LoginReq req) {
        AccountResp.LoginResp resp = new AccountResp.LoginResp();
        log.info("get applet openid start, code={}", req.getCode());
        OpenIdResp openIdResp = wxFacade.getWxOpenId(req.getCode());
        log.info("get applet openid end, code={}, resp={}", req.getCode(), JSON.toJSONString(openIdResp));
        if (openIdResp == null || !openIdResp.getCode().equals("000")) {
            log.error("获取小程序openId失败，入参={}，出参={}", req.getCode(), JSON.toJSONString(openIdResp));
            throw new BusinessException("获取小程序openId失败");
        }
        TAccountExtBind accountExtBind = accountExtBindDao.findAppletByOpenId(openIdResp.getOpenId());
        if (accountExtBind == null) {
            accountExtBind = new TAccountExtBind();
            accountExtBind.setExtType(AccountEnum.ExtType.APPLET.getKey());
            accountExtBind.setExtUserId(openIdResp.getOpenId());
            accountExtBind.setUnionId(openIdResp.getUnionId());
            accountExtBind.setNickName(StringEscapeUtils.escapeJava(req.getNickName()));
            accountExtBind.setHeadImgUrl(req.getHeadImgUrl());
            int result = accountExtBindDao.insert(accountExtBind);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        } else {
            boolean update = false;
            if (StringUtils.isNotEmpty(req.getNickName()) && !req.getNickName().equals(accountExtBind.getNickName())) {
                accountExtBind.setNickName(StringEscapeUtils.escapeJava(req.getNickName()));
                update = true;
            }
            if (StringUtils.isNotEmpty(req.getHeadImgUrl()) && !req.getHeadImgUrl().equals(accountExtBind.getHeadImgUrl())) {
                accountExtBind.setHeadImgUrl(req.getHeadImgUrl());
                update = true;
            }
            if (StringUtils.isBlank(accountExtBind.getUnionId()) && StringUtils.isNotBlank(openIdResp.getUnionId())) {
                accountExtBind.setUnionId(openIdResp.getUnionId());
                update = true;
            }
            if (update) {
                accountExtBindDao.updateById(accountExtBind);
            }
        }
        resp.setLoginId(accountExtBind.getId());
        resp.setOpenId(accountExtBind.getExtUserId());
        return resp;
    }

    @Override
    public AccountExtBindIVo getAppletExtBind(Integer id) {
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(id);
        AccountExtBindIVo vo = RespConvertUtil.convertToAccountExtBindIVo(accountExtBind);
        if (accountExtBind.getAccountId() != null) {
            TAccount account = tAccountDao.selectById(accountExtBind.getAccountId());
            vo.setMobile(account.getMobile());
            vo.setAgencyId(account.getAgencyId());
            vo.setIsAgencyAdmin(account.getIsAgencyAdmin() == null ? false : account.getIsAgencyAdmin().equals(1));
            if (account.getShopId() != null) {
                TAppletShop shop = tAppletShopDao.selectById(account.getShopId());
                if (shop != null) {
                    vo.setShopId(shop.getId());
                    // 我的邀请数量
                    vo.setInviteCount(accountExtBindDao.inviteCount(AccountEnum.InviteType.DP.getKey(), shop.getId()));
                    shop.setName(StringEscapeUtils.unescapeJava(shop.getName()));
                    vo.setShopName(shop.getName());
                }
            }
        }
        if (accountExtBind.getAccountId() != null) {
            // 我关注的拍品数量
            vo.setFavorCount(favoriteActivityDao.appletFavoriteCount(accountExtBind.getCurrentPartyId(), accountExtBind.getAccountId()));
        }
        return vo;
    }

    @Override
    public AccountExtBindIVo getNumberJumpExtBind(Integer id) {
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(id);
        AccountExtBindIVo vo = RespConvertUtil.convertToAccountExtBindIVo(accountExtBind);
        if (accountExtBind.getAccountId() != null) {
            TAccount account = tAccountDao.selectById(accountExtBind.getAccountId());
            vo.setMobile(account.getMobile());
            vo.setAgencyId(account.getAgencyId());
            vo.setIsAgencyAdmin(account.getIsAgencyAdmin() == null ? false : account.getIsAgencyAdmin().equals(1));
        } else {
            TAccountExtBind extBind = accountExtBindDao.findAppletBy(accountExtBind.getUnionId());
            if (extBind != null && extBind.getAccountId() != null) {
                TAccount account = tAccountDao.selectById(extBind.getAccountId());
                vo.setMobile(account.getMobile());
                vo.setAgencyId(account.getAgencyId());
                vo.setIsAgencyAdmin(account.getIsAgencyAdmin() == null ? false : account.getIsAgencyAdmin().equals(1));
            }
        }
//        vo.setUnreadBroadcastCount(debtCalculatorDao.getUnreadBroadcastCount(accountExtBind.getId()));
        return vo;
    }

    @Override
    public AccountResp.BindAccountResp bindAccount(AccountReq.BindAccountReq req) {
        AccountResp.BindAccountResp resp = new AccountResp.BindAccountResp();
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getId());
        if (accountExtBind == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (accountExtBind.getAccountId() != null) {
            if (accountExtBind.getAccountId().equals(req.getAccountId())) {
                throw new BusinessException("请不要重复绑定");
            }
            throw new BusinessException("已绑定手机号");
        }
        TAccountExtBind accountExtBind2 = accountExtBindDao.findAppletByAccountId(req.getAccountId());
        if (accountExtBind2 != null) {
            if (accountExtBind2.getId().equals(req.getId())) {
                throw new BusinessException("请不要重复绑定");
            }
            throw new BusinessException("该手机号已绑定");
        }
        accountExtBind.setAccountId(req.getAccountId());
        accountExtBind.setInviteCode(req.getInviteCode());
        accountExtBind.setInviteType(req.getInviteType());

        List<AccountAuthVo> accountAuthList = getAccountAuthList(req.getAccountId());
        if (accountAuthList.size() == 1) {
            accountExtBind.setCurrentPartyId(Integer.parseInt(accountAuthList.get(0).getPartyId()));
        }
        int result = accountExtBindDao.updateById(accountExtBind);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        if (accountAuthList.size() == 1) {
            resp.setIsAccountAuth(true);
        } else {
            resp.setIsAccountAuth(false);
        }
        resp.setAccountAuthList(accountAuthList);
        if (req.getIsNewRegister()) {
            appletMessageService.sendAccountRegisterMessage(req.getAccountId());
        }
        return resp;
    }

    @Override
    public AccountResp selectParty(AccountReq.SelectPartyReq req) {
        AccountResp resp = new AccountResp();
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getId());
        if (accountExtBind == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (accountExtBind.getAccountId() == null) {
            throw new BusinessException("请先绑定账户");
        }
        if (accountExtBind.getCurrentPartyId() != null) {
            throw new BusinessException("已选择过认证账户");
        }
        List<AccountAuthVo> accountAuthList = getAccountAuthList(accountExtBind.getAccountId());
        Set<String> correctPartyIds = new HashSet<>();
        for (AccountAuthVo authVo : accountAuthList) {
            correctPartyIds.add(authVo.getPartyId());
        }
        if (!correctPartyIds.contains(req.getPartyId() + "")) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        accountExtBind.setCurrentPartyId(req.getPartyId());
        int result = accountExtBindDao.updateById(accountExtBind);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return resp;
    }

    @Override
    public AccountResp.AuthInfoResp getAuthInfoList(Integer accountId) {
        AccountResp.AuthInfoResp resp = new AccountResp.AuthInfoResp();
        List<AccountAuthVo> accountAuthList = getAccountAuthList(accountId);
        AppletEnum.FrontApplyStatus applyStatus = AppletEnum.FrontApplyStatus.NO_APPLY;
        if (accountAuthList.size() > 0) {
            applyStatus = AppletEnum.FrontApplyStatus.APPROVED;
        } else {
            String status = tAccountDao.getLatestApplyStatus(accountId);
            if (StringUtils.isNotEmpty(status)) {
                if (AppletEnum.FrontApplyStatus.PENDING.getKey().equals(status)) {
                    applyStatus = AppletEnum.FrontApplyStatus.PENDING;
                } else if (AppletEnum.FrontApplyStatus.REJECT.getKey().equals(status)) {
                    applyStatus = AppletEnum.FrontApplyStatus.REJECT;
                } else if (AppletEnum.FrontApplyStatus.APPROVED.getKey().equals(status)) {
                    applyStatus = AppletEnum.FrontApplyStatus.APPROVED;
                }
            }
        }
        resp.setApplyStatus(applyStatus.getKey());
        resp.setApplyStatusDesc(applyStatus.getValue());
        resp.setAccountAuthList(accountAuthList);
        return resp;
    }

    @Override
    public boolean setExtBindCurrentPartyIdIfNeed(Integer accountId, Integer partyId, PartyEnum.Type type) {
        TAccountExtBind accountExtBind = accountExtBindDao.findAppletByAccountId(accountId);
        if (accountExtBind == null) {
            return false;
        }
        if (accountExtBind.getCurrentPartyId() != null) {
            return false;
        }
        accountExtBind.setCurrentPartyId(partyId);
        int result = accountExtBindDao.updateById(accountExtBind);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void syncShopIfNeed(Integer accountId, Integer partyId, PartyEnum.Type type) {
        TAccount account = tAccountDao.selectById(accountId);
        if (account == null || account.getShopId() == null) {
            return;
        }
        TAppletShop shop = tAppletShopDao.selectById(account.getShopId());
        if (shop == null) {
            return;
        }
        if (shop.getPartyId() != null) {
            return;
        }
        shop.setPartyId(partyId);
        int result = tAppletShopDao.updateById(shop);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        // 绑定，且同步平台资金账户信息
        TAcct shopAcct = tAcctDao.getByPartyIdType(shop.getId(), AccountEnum.AcctType.SHOP.getKey());
        shopAcct.setPartyId(partyId);
        shopAcct.setType(type.name());
        result = tAcctDao.updateById(shopAcct);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
    }

    @Override
    public ListResp<String> getUnfinishedTaskList(AccountReq.BaseReq req) {
        return null;
    }

    @Override
    public Map<String, Object> checkAccountDispose(String mobile) {
        TAccount accountByMobile = findAccountByMobile(mobile);
        Optional.ofNullable(accountByMobile).orElseThrow(() -> new BusinessException("该手机号未注册"));
        Map <String, Object> map = new HashMap<>(2);
        if (accountByMobile.getCurrentPartyId() == null) {
            map.put("code", "001");
            map.put("desc", "该手机号已注册尚未认证任何身份");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("accountId",accountByMobile.getId());
            map.put("content", jsonObject);
            return map;
        }
        List<AccountAuthVo> list = getAccountAuthList(accountByMobile.getId());
        List<AccountAuthVo> resp = new LinkedList<>();
        boolean isUserAuth = false;
        for (AccountAuthVo t : list) {
            AccountAuthVo vo = new AccountAuthVo();
            BeanUtils.copyProperties(t, vo);
            TDisposeProvider disposeProviderByPartyId = disposeService.getDisposeProviderByPartyId(Integer.parseInt(t.getPartyId()));
            vo.setAccountId(accountByMobile.getId());
            vo.setName(t.getName());
            vo.setType(t.getType());
            vo.setPartyId(t.getPartyId());
            vo.setAuthDisposerDesc(disposeProviderByPartyId == null ? "未认证" : "已认证");
            if (PartyEnum.Type.user.name().equals(vo.getType())) {
                isUserAuth = true ;
            }
            if (disposeProviderByPartyId == null) {
                TDisposeProviderApply disposeProviderApplyByPartyId = disposeService.getDisposeProviderApplyByPartyId(Integer.parseInt(t.getPartyId()));
                if (disposeProviderApplyByPartyId != null
                        && disposeProviderApplyByPartyId.getStatus().equals(AccountEnum.ApplyStatus.PENDING.getKey())) {
                    vo.setAuthDisposerDesc("待审核");
                }
            }
            resp.add(vo);
        }
        map.put("list", resp);
        map.put("isUserAuth", isUserAuth);
        return map;
    }

    private List<AccountAuthVo> getAccountAuthList(Integer accountId) {
        if (accountId == null) {
            return Collections.EMPTY_LIST;
        }
        List<AccountAuthVo> accountAuthList = new ArrayList<>();
        TUser user = userDao.getByAccountId(accountId);
        if (user != null) {
            AccountAuthVo authVo = new AccountAuthVo();
            authVo.setPartyId(user.getId() + "");
            authVo.setName(user.getName());
            authVo.setType(PartyEnum.Type.user.name());
            authVo.setCertificateNumber(user.getCertificateNumber());
            authVo.setCertificateBackImg(user.getCertificateBackImg());
            authVo.setCertificateFrontImg(user.getCertificateFrontImg());
            if (StringUtils.isNotEmpty(user.getCityId())) {
                City city = cityDao.selectById(user.getCityId());
                if (city != null) {
                    authVo.setCityId(city.getId() + "");
                    authVo.setCityName(city.getName());
                    authVo.setProvinceId(city.getProvinceId() + "");
                }
            }
            authVo.setAddress(user.getAddress());
            accountAuthList.add(authVo);
        }
        List<TCompany> companyList = companyDao.getByAccountId(accountId);
        for (TCompany company : companyList) {
            AccountAuthVo authVo = new AccountAuthVo();
            authVo.setPartyId(company.getId() + "");
            authVo.setName(company.getName());
            authVo.setType(PartyEnum.Type.company.name());
            authVo.setCertificateNumber(company.getLicense());
            if (StringUtils.isNotEmpty(company.getRegisterCityId())) {
                City city = cityDao.selectById(company.getRegisterCityId());
                if (city != null) {
                    authVo.setRegisterCityId(city.getId() + "");
                    authVo.setRegisterCityName(city.getName());
                    authVo.setRegisterProvinceId(city.getProvinceId() + "");
                }
            }
            accountAuthList.add(authVo);
        }
        return accountAuthList;
    }
}