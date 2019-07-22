package com.winback.core.provider.account;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.common.utils.PasswordEncryption;
import com.winback.arch.core.sysconfig.properties.SystemProperties;
import com.winback.core.commons.constants.AccountEnum;
import com.winback.core.commons.constants.SmsType;
import com.winback.core.exception.BusinessException;
import com.winback.core.facade._case.resp.CaseVo;
import com.winback.core.facade._case.vo.Case;
import com.winback.core.facade.account.AccountFacade;
import com.winback.core.facade.account.req.AdminAccountReq;
import com.winback.core.facade.account.req.AppAccountReq;
import com.winback.core.facade.account.req.AppletAccountReq;
import com.winback.core.facade.account.resp.AdminAccountResp;
import com.winback.core.facade.account.resp.AppAccountResp;
import com.winback.core.facade.account.resp.AppletAccountResp;
import com.winback.core.facade.account.vo.*;
import com.winback.core.model.account.TAccount;
import com.winback.core.model.account.TAccountExtBind;
import com.winback.core.model.account.TLawyer;
import com.winback.core.model.account.TSysStaff;
import com.winback.core.service._case.CaseService;
import com.winback.core.service.account.AccountService;
import com.winback.core.service.account.FranchiseeService;
import com.winback.core.service.account.LawyerService;
import com.winback.core.service.account.StaffService;
import com.winback.core.service.assistant.*;
import com.winback.core.service.contract.ContractService;
import com.winback.core.utils.RespConvertUtil;
import com.winback.gateway.facade.WxFacade;
import com.winback.gateway.resp.wx.OpenIdResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author xdrodger
 * @Title: AccountProvider
 * @ProjectName winback
 * @Description:
 * @date 2019/1/24 15:58
 */
@Slf4j
@Component
@Service(version = "1.0.0")
public class AccountProvider implements AccountFacade {

    @Autowired
    private AccountService accountService;
    @Autowired
    private LawyerService lawyerService;
    @Autowired
    private FranchiseeService franchiseeService;
    @Autowired
    private AppMessageService appMessageService;
    @Autowired
    private AppletMessageService appletMessageService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private CaseService caseService;
    @Autowired
    private AssistantService assistantService;
    @Reference(version = "1.0.0")
    private WxFacade wxFacade;
    @Autowired
    private SystemProperties systemProperties;
    @Autowired
    private ContractService contractService;
    @Autowired
    private CityService cityService;


    @Override
    public AppAccountResp.AccountResp login(AppAccountReq.LoginReq req) {
        TAccount account = accountService.findByMobile(req.getMobile());
        if (account == null) {
            throw new BusinessException(ApiCallResult.NOREGISTER);
        }
        if (StringUtils.isNotEmpty(req.getPassword())) {
            try {
                if (!PasswordEncryption.authenticate(req.getPassword(), account.getPassword())) {
                    throw new BusinessException(ApiCallResult.ERROR_PASSWORD);
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        } else if (StringUtils.isNotEmpty(req.getSmsCode())) {
            //短信验证码登录
            if (!smsService.checkSmsCode(req.getMobile(), SmsType.APP_LOGIN.getKey(), req.getSmsCode())) {
                throw new BusinessException(ApiCallResult.VERIFICATION);
            }
        } else {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        assistantService.saveDevice(account.getId(), req.getDevice());
        AppAccountResp.AccountResp resp = new AppAccountResp.AccountResp();
        resp.setLoginId(account.getId());
        return resp;
    }

    @Override
    public AppAccountResp.AccountResp register(AppAccountReq.RegisterReq req) {
        AppAccountResp.AccountResp resp = new AppAccountResp.AccountResp();
        TAccount account = accountService.findByMobile(req.getMobile());
        if (account != null) {
            throw new BusinessException(ApiCallResult.HASREGISTER);
        }
        if (!smsService.checkSmsCode(req.getMobile(), SmsType.APP_REGISTER.getKey(), req.getSmsCode())) {
            throw new BusinessException(ApiCallResult.VERIFICATION);
        }
        boolean defaultpassword = false;
        if (StringUtils.isBlank(req.getPassword())) {
            req.setPassword(systemProperties.getDefaultRegisterPassword());
            defaultpassword = true;
        }
        account = accountService.register(req);
        assistantService.saveDevice(account.getId(), req.getDevice());
        if (defaultpassword) {
            smsService.sendDefaultPasswordSms(req.getMobile(), systemProperties.getDefaultRegisterPassword());
        }
        resp.setLoginId(account.getId());
        return resp;
    }

    @Override
    public String sendSmsCode(AppAccountReq.SmsReq req) {
        boolean hasRegister = hasRegister(req.getMobile());
        String smsCode = "";
        if (SmsType.APP_LOGIN.getKey().equals(req.getSmsType())) {
            if (!hasRegister) {
                throw new BusinessException(ApiCallResult.NOREGISTER);
            }
            //是否超过发送验证码的间隔时间
            if(smsService.canSendSmsCode(req.getMobile(), req.getSmsType())){
                throw new BusinessException(ApiCallResult.CAN_NOT_SEND_VERIFY_CODE);
            }
            smsCode = smsService.getSmsCode(req.getMobile(), req.getSmsType());
            smsService.sendLoginSmsCode(req.getMobile(), smsCode);
        } else if (SmsType.APP_REGISTER.getKey().equals(req.getSmsType())) {
            if (hasRegister) {
                throw new BusinessException(ApiCallResult.HASREGISTER);
            }
            //是否超过发送验证码的间隔时间
            if(smsService.canSendSmsCode(req.getMobile(), req.getSmsType())){
                throw new BusinessException(ApiCallResult.CAN_NOT_SEND_VERIFY_CODE);
            }
            smsCode = smsService.getSmsCode(req.getMobile(), req.getSmsType());
            smsService.sendRegisterSmsCode(req.getMobile(), smsCode);
        } else if (SmsType.APP_FORGET_PASSWORD.getKey().equals(req.getSmsType())) {
            if (!hasRegister) {
                throw new BusinessException(ApiCallResult.NOREGISTER);
            }
            //是否超过发送验证码的间隔时间
            if(smsService.canSendSmsCode(req.getMobile(), req.getSmsType())){
                throw new BusinessException(ApiCallResult.CAN_NOT_SEND_VERIFY_CODE);
            }
            smsCode = smsService.getSmsCode(req.getMobile(), req.getSmsType());
            smsService.sendForgetPasswordSmsCode(req.getMobile(), smsCode);
        }
        return smsCode;
    }

    @Override
    public AccountInfo getAccountInfo(Integer accountId) {
        AccountInfo resp = new AccountInfo();
        TAccount account = accountService.findById(accountId);
        if (account != null) {
            BeanUtils.copyProperties(account, resp);
        }
        resp.setLawyerApplyStatus(lawyerService.getApplyStatus(accountId));
        resp.setFranchiseeApplyStatus(franchiseeService.getApplyStatus(accountId));
        if (AccountEnum.ApplyStatus.APPROVED.getKey().equals(resp.getLawyerApplyStatus())) {
            resp.setLawyerFlag(true);
        }
        if (AccountEnum.ApplyStatus.APPROVED.getKey().equals(resp.getFranchiseeApplyStatus())) {
            resp.setFranchiseeFlag(true);
        }
        resp.setUnreadMessageCount(appMessageService.getUnreadMessageCount(accountId));
        resp.setUnreadConnectCount(appMessageService.getUnreadConnectCount(accountId,resp.isLawyerFlag()));
        resp.setUnreadMessageAll(resp.getUnreadMessageCount()+resp.getUnreadConnectCount());
        if (resp.isLawyerFlag()) {
            TLawyer tLawyer = lawyerService.findByAccountId(accountId);
            Lawyer lawyer = RespConvertUtil.convertToLawyer(tLawyer);
            lawyer.setBusinessProvinceName(cityService.getProvinceName(lawyer.getBusinessProvinceCode()));
            lawyer.setBusinessCityName(cityService.getCityName(lawyer.getBusinessCityCode()));
            lawyer.setBusinessAreaName(cityService.getAreaName(lawyer.getBusinessAreaCode()));
            resp.setHeadImgUrl(lawyer.getHeadImgUrl());
            resp.setLawyer(lawyer);
        }
        return resp;
    }

    @Override
    public AppAccountResp.AccountResp forgetPassword(AppAccountReq.ForgetPasswordReq req) {
        TAccount account = accountService.findByMobile(req.getMobile());
        if (account == null) {
            throw new BusinessException(ApiCallResult.NOREGISTER);
        }
        if (!smsService.checkSmsCode(req.getMobile(), SmsType.APP_FORGET_PASSWORD.getKey(), req.getSmsCode())) {
            throw new BusinessException(ApiCallResult.VERIFICATION);
        }
        accountService.modifyPassword(account.getId(), req.getPassword());
        AppAccountResp.AccountResp resp = new AppAccountResp.AccountResp();
        resp.setLoginId(account.getId());
        return resp;
    }

    @Override
    public AppAccountResp.AccountResp modifyPassword(AppAccountReq.ModifyPasswordReq req) {
        accountService.modifyPassword(req.getLoginId(), req.getPassword());
        AppAccountResp.AccountResp resp = new AppAccountResp.AccountResp();
        resp.setLoginId(req.getLoginId());
        return resp;
    }

    @Override
    public AppAccountResp.ApplyResp lawyerApply(AppAccountReq.LawyerApplyReq req) {
        return lawyerService.lawyerApply(req);
    }

    @Override
    public AppAccountResp.ApplyResp franchiseeApply(AppAccountReq.FranchiseeApplyReq req) {
        return franchiseeService.franchiseeApply(req);
    }

    @Override
    public AppAccountResp.AccountResp updateAccount(AppAccountReq.AccountUpdateReq req) {
        TAccount account = accountService.findById(req.getLoginId());
        if (StringUtils.isNotEmpty(req.getNickName())) {
            account.setNickName(req.getNickName());
        }
        if (StringUtils.isNotEmpty(req.getHeadImgUrl())) {
            account.setHeadImgUrl(req.getHeadImgUrl());
        }
        accountService.update(account);
        AppAccountResp.AccountResp resp = new AppAccountResp.AccountResp();
        resp.setLoginId(account.getId());
        return resp;
    }

    @Override
    public boolean hasRegister(String mobile) {
        TAccount account = accountService.findByMobile(mobile);
        return account != null ? true : false;
    }

    @Override
    public PageInfoResp<Customer> getInviteCustomerListByPage(AppAccountReq.FranchiseeQueryReq req) {
        return franchiseeService.getInviteCustomerListByPage(req);
    }

    @Override
    public PageInfoResp<Case> getInviteCaseListByPage(AppAccountReq.FranchiseeQueryReq req) {
        return franchiseeService.getInviteCaseListByPage(req);
    }

    @Override
    public PageInfoResp<AppMessage> getAppMessageListByPage(AppAccountReq.MessageReq req) {
        return appMessageService.getAppMessageListByPage(req);
    }

    @Override
    public AppMessage getAppMessage(AppAccountReq.MessageReq req) {
        return appMessageService.getAppMessage(req);
    }

    @Override
    public AdminAccountResp.AccountResp login(AdminAccountReq.LoginReq req) {
        TSysStaff staff = staffService.findByMobile(req.getMobile());
        if (staff == null) {
            throw new BusinessException(ApiCallResult.NOREGISTER);
        }
        if (!staff.getStatus()) {
            throw new BusinessException("账号已禁用");
        }
        if (StringUtils.isNotEmpty(req.getPassword())) {
            try {
                if (!PasswordEncryption.authenticate(req.getPassword(), staff.getPassword())) {
                    throw new BusinessException(ApiCallResult.ERROR_PASSWORD);
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        } else if (StringUtils.isNotEmpty(req.getSmsCode())) {
            //短信验证码登录
            if (!smsService.checkSmsCode(req.getMobile(), SmsType.ADMIN_LOGIN.getKey(), req.getSmsCode())) {
                throw new BusinessException(ApiCallResult.VERIFICATION);
            }
        } else {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        AdminAccountResp.AccountResp resp = new AdminAccountResp.AccountResp();
        resp.setLoginId(staff.getId());
        resp.setAdminFlag(staff.getAdminFlag());
        return resp;
    }

    @Override
    public PageInfoResp<Account> getAccountListByPage(AdminAccountReq.AccountQueryReq req) {
        return accountService.getAccountListByPage(req);
    }

    @Override
    public PageInfoResp<Party> getPartyListByPage(AdminAccountReq.PartyQueryReq req) {
        return accountService.getPartyListByPage(req);
    }

    @Override
    public PageInfoResp<LawyerApplyRecord> getLawyerApplyRecordListByPage(AdminAccountReq.LawyerQueryReq req) {
        return lawyerService.getLawyerApplyRecordListByPage(req);
    }

    @Override
    public Integer lawyerApplyApprove(AdminAccountReq.LawyerVerifyReq req) {
        return lawyerService.applyApprove(req);
    }

    @Override
    public void lawyerApplyReject(AdminAccountReq.LawyerVerifyReq req) {
        lawyerService.applyReject(req);
    }

    @Override
    public PageInfoResp<Lawyer> getLawyerListByPage(AdminAccountReq.LawyerQueryReq req) {
        return lawyerService.getLawyerListByPage(req);
    }

    @Override
    public Integer lawyerUpdate(AdminAccountReq.LawyerUpdateReq req) {
        return lawyerService.lawyerUpdate(req);
    }

    @Override
    public PageInfoResp<LawFirm> getLawFirmListByPage(AdminAccountReq.LawFirmQueryReq req) {
        return lawyerService.getLawFirmListByPage(req);
    }

    @Override
    public Integer lawFirmAdd(AdminAccountReq.LawFirmAddReq req) {
        return lawyerService.lawFirmAdd(req);
    }

    @Override
    public Integer lawFirmUpdate(AdminAccountReq.LawFirmUpdateReq req) {
        return lawyerService.lawFirmUpdate(req);
    }

    @Override
    public PageInfoResp<FranchiseeApplyRecord> getFranchiseeApplyRecordListByPage(AdminAccountReq.FranchiseeQueryReq req) {
        return franchiseeService.getApplyRecordListByPage(req);
    }

    @Override
    public Integer franchiseeApplyApprove(AdminAccountReq.FranchiseeVerifyReq req) {
        return franchiseeService.franchiseeApplyApprove(req);
    }

    @Override
    public void franchiseeApplyReject(AdminAccountReq.FranchiseeVerifyReq req) {
        franchiseeService.franchiseeApplyReject(req);
    }

    @Override
    public PageInfoResp<Franchisee> getFranchiseeListByPage(AdminAccountReq.FranchiseeQueryReq req) {
        return franchiseeService.getFranchiseeListByPage(req);
    }

    @Override
    public Integer franchiseeUpdate(AdminAccountReq.FranchiseeUpdateReq req) {
        return franchiseeService.franchiseeUpdate(req);
    }

    @Override
    public PageInfoResp<Customer> getFranchiseeInviteCustomerListByPage(AdminAccountReq.FranchiseeQueryReq req) {
        return franchiseeService.getInviteCustomerListByPage(req);
    }

    @Override
    public PageInfoResp<Case> getFranchiseeInviteCaseListByPage(AdminAccountReq.FranchiseeQueryReq req) {
        return franchiseeService.getInviteCaseListByPage(req);
    }

    @Override
    public AppletAccountResp.LoginResp login(AppletAccountReq.LoginReq req) {
        AppletAccountResp.LoginResp resp = new AppletAccountResp.LoginResp();
        log.info("get applet openid start, code={}", req.getCode());
        OpenIdResp openIdResp = wxFacade.getWxOpenId(req.getCode());
        log.info("get applet openid end, code={}, resp={}", req.getCode(), JSON.toJSONString(openIdResp));
        if (openIdResp == null || !openIdResp.getCode().equals("000")) {
            log.error("获取小程序openId失败，入参={}，出参={}", req.getCode(), JSON.toJSONString(openIdResp));
            throw new BusinessException("获取小程序openId失败");
        }
        TAccountExtBind accountExtBind = accountService.findAppletByOpenId(openIdResp.getOpenId());
        if (accountExtBind == null) {
            accountExtBind = new TAccountExtBind();
            accountExtBind.setExtType(AccountEnum.ExtType.APPLET.getKey());
            accountExtBind.setExtUserId(openIdResp.getOpenId());
            accountExtBind.setNickName(req.getNickName());
            accountExtBind.setHeadImgUrl(req.getHeadImgUrl());
            int result = accountService.saveAppletExtBind(accountExtBind);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        } else {
            boolean update = false;
            if (StringUtils.isNotEmpty(req.getNickName()) && !req.getNickName().equals(accountExtBind.getNickName())) {
                accountExtBind.setNickName(req.getNickName());
                update = true;
            }
            if (StringUtils.isNotEmpty(req.getHeadImgUrl()) && !req.getHeadImgUrl().equals(accountExtBind.getHeadImgUrl())) {
                accountExtBind.setHeadImgUrl(req.getHeadImgUrl());
                update = true;
            }
            if (update) {
                accountService.updateAppletExtBind(accountExtBind);
            }
        }
        resp.setLoginId(accountExtBind.getId());
        resp.setOpenId(accountExtBind.getExtUserId());
        return resp;
    }

    @Override
    public String sendSmsCode(AppletAccountReq.SmsReq req) {
        //是否超过发送验证码的间隔时间
        if(smsService.canSendSmsCode(req.getMobile(), req.getSmsType())){
            throw new BusinessException(ApiCallResult.CAN_NOT_SEND_VERIFY_CODE);
        }
        String smsCode = smsService.getSmsCode(req.getMobile(), req.getSmsType());
        smsService.sendSmsCode(req.getMobile(), smsCode);
        return smsCode;
    }

    @Override
    public AppletAccountResp.AccountResp bindAccount(AppletAccountReq.BindAccountReq req) {
        if (!smsService.checkSmsCode(req.getMobile(), SmsType.APPLET_BIND_ACCOUNT.getKey(), req.getSmsCode())) {
            throw new BusinessException(ApiCallResult.VERIFICATION);
        }
        return accountService.bindAccount(req);
    }

    @Override
    public AppletAccountInfo getAppletAccountInfo(Integer extBindId) {
        TAccountExtBind extBind = accountService.findAppletExtBind(extBindId);
        AppletAccountInfo accountInfo = new AppletAccountInfo();
        BeanUtils.copyProperties(extBind, accountInfo);
        accountInfo.setUnreadMessageCount(appletMessageService.getUnreadMessageCount(extBind.getId()));
        if (extBind.hasBindAccount()) {
            TAccount account = accountService.findById(extBind.getAccountId());
            if (account != null) {
                accountInfo.setMobile(account.getMobile());
            }
            accountInfo.setLatestDownloadEmail(contractService.getLatestEmail(extBind.getAccountId()));
            accountInfo.setFavoriteContractCount(contractService.favoriteContractCount(extBind.getAccountId()));
        } else {
            accountInfo.setFavoriteContractCount(contractService.appletFavoriteContractCount(extBind.getId()));
        }
        return accountInfo;
    }

    @Override
    public PageInfoResp<AppletMessage> getAppletMessageList(AppletAccountReq.MessageReq req) {
        return appletMessageService.getAppletMessageList(req);
    }

    @Override
    public AppletMessage getAppletMessage(AppletAccountReq.MessageReq req) {
        return appletMessageService.getAppletMessage(req);
    }

    @Override
    public String sendSmsCode(AdminAccountReq.SmsReq req) {
        //是否超过发送验证码的间隔时间
        if(smsService.canSendSmsCode(req.getMobile(), req.getSmsType())){
            throw new BusinessException(ApiCallResult.CAN_NOT_SEND_VERIFY_CODE);
        }
        String smsCode = smsService.getSmsCode(req.getMobile(), req.getSmsType());
        smsService.sendSmsCode(req.getMobile(), smsCode);
        return smsCode;
    }

    @Override
    public void uploadContacts(AppAccountReq.UploadContactsReq req) {
        accountService.uploadContacts(req);
    }

    @Override
    public Integer updateAccount(AdminAccountReq.AccountUpdateReq req) {
        return accountService.updateAccount(req);
    }

    @Override
    public PageInfoResp<ProjectManager> getProjectManagerListByPage(AdminAccountReq.AccountQueryReq req) {
        return accountService.getProjectManagerListByPage(req);
    }
}
