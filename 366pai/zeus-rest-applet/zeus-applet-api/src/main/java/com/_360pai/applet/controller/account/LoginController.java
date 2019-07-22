package com._360pai.applet.controller.account;

import com._360pai.applet.controller.AbstractController;
import com._360pai.applet.controller.account.req.AccountRequest;
import com._360pai.applet.controller.account.resp.AccountBaseInfo;
import com._360pai.applet.controller.account.vo.ProfileInfo;
import com._360pai.applet.shiro.ShiroAuthService;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.NumberValidationUtils;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.common.utils.ToolUtil;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.AppletEnum;
import com._360pai.core.common.constants.SmsType;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.req.AccountReq;
import com._360pai.core.facade.account.resp.AccountResp;
import com._360pai.core.facade.account.resp.AcctResp;
import com._360pai.core.facade.account.resp.AgencyResp;
import com._360pai.core.facade.account.vo.AccountExtBindIVo;
import com._360pai.core.facade.applet.AppletFacade;
import com._360pai.core.facade.applet.vo.ShopVo;
import com._360pai.core.facade.shop.ShopFacade;
import com._360pai.core.facade.shop.req.ShopReq;
import com._360pai.gateway.common.alisms.AliSmsTemplateEnums;
import com._360pai.gateway.controller.req.alisms.FAliSmsSendReq;
import com._360pai.gateway.facade.AliSmsFacade;
import com._360pai.gateway.resp.AliSendSmsResp;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: LoginController
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/22 15:00
 */
@RestController
@Slf4j
public class LoginController extends AbstractController {
    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;

    @Reference(version = "1.0.0")
    private AppletFacade appletFacade;
    @Resource
    private RedisCachemanager redisCachemanager;
    @Autowired
    private ShiroAuthService shiroAuthService;
    @Reference(version = "1.0.0")
    private AliSmsFacade aliSmsFacade;
    @Autowired
    private SystemProperties systemProperties;
    @Reference(version = "1.0.0")
    private ShopFacade shopFacade;

    /**
     * 登录
     */
    @PostMapping(value = "/open/login")
    public ResponseModel login(@RequestBody AccountReq.LoginReq req, HttpServletResponse response) {
        if (StringUtils.isEmpty(req.getCode())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountResp.LoginResp resp = accountFacade.login(req);
        //保存 ticket
        String ticket = shiroAuthService.saveTicket(getCurRequest(),response, resp.getLoginId(), null);
        Map<String, Object> data = new HashMap<>();
        data.put("applet_id", resp.getLoginId());
        data.put("applet_ticket", ticket);
        return ResponseModel.succ(data);
    }

    /**
     * 判断账户是否认证接口
     */
    @GetMapping(value = "/confined/check/account/auth")
    public ResponseModel checkAccountAuth() {
        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();
        Map<String, Object> data = new HashMap<>();
        data.put("isAccountAuth", accountInfo.getIsAccountAuth());
        return ResponseModel.succ(data);
    }

    /**
     * 获取账户信息
     */
    @GetMapping(value = "/confined/profile")
    public ResponseModel profile() {
        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();
        ProfileInfo profileInfo = new ProfileInfo();
        BeanUtils.copyProperties(accountInfo, profileInfo);
        if (accountInfo.getShopId() != null) {
            profileInfo.setShopId(accountInfo.getShopId() + "");
            profileInfo.setInviteCode(AccountEnum.InviteType.DP.getKey() + accountInfo.getShopId());
        } else {
            profileInfo.setShopId("");
        }
        if (accountInfo.getAccountId() != null) {
            profileInfo.setIsAccountBind(true);
        }
        if (accountInfo.getCurrentPartyId() != null) {
            profileInfo.setApplyStatus(AppletEnum.FrontApplyStatus.APPROVED.getKey());
            profileInfo.setApplyStatusDesc(AppletEnum.FrontApplyStatus.APPROVED.getValue());
        } else if (accountInfo.getAccountId() != null) {
            AccountResp.AuthInfoResp authInfoResp = accountFacade.getAuthInfoList(accountInfo.getAccountId());
            profileInfo.setAccountAuthList(authInfoResp.getAccountAuthList());
            profileInfo.setApplyStatus(authInfoResp.getApplyStatus());
            profileInfo.setApplyStatusDesc(authInfoResp.getApplyStatusDesc());
        } else {
            profileInfo.setApplyStatus(AppletEnum.FrontApplyStatus.NO_APPLY.getKey());
            profileInfo.setApplyStatusDesc(AppletEnum.FrontApplyStatus.NO_APPLY.getValue());
        }
        if (accountInfo.getCurrentPartyId() == null && accountInfo.getAccountId() != null) {
            AccountResp.AuthInfoResp authInfoResp = accountFacade.getAuthInfoList(accountInfo.getAccountId());
            profileInfo.setAccountAuthList(authInfoResp.getAccountAuthList());
        }
        if (accountInfo.getShopId() != null) {
            profileInfo.setOpenShopStatus(AppletEnum.OpenShopStatus.OPENED.getKey());
            profileInfo.setOpenShopStatusDesc(AppletEnum.OpenShopStatus.OPENED.getValue());
        } else {
            profileInfo.setOpenShopStatus(AppletEnum.OpenShopStatus.NOT_OPENED.getKey());
            profileInfo.setOpenShopStatusDesc(AppletEnum.OpenShopStatus.NOT_OPENED.getValue());
        }
        AcctResp acctResp = null;
        if (accountInfo.getCurrentPartyId() != null) {
            acctResp = accountFacade.getAcct(accountInfo.getCurrentPartyId(), accountInfo.getType());
        } else if (accountInfo.getShopId() != null) {
            acctResp = accountFacade.getAcct(accountInfo.getShopId(), AccountEnum.AcctType.SHOP.getKey());
        }
        if (acctResp != null && acctResp.getId() != null) {
            profileInfo.setAccountAmount(acctResp.getTotalAmt().toPlainString());
            profileInfo.setAvailableAmount(acctResp.getAvailAmt().toPlainString());
            profileInfo.setLockAmount(acctResp.getLockAmt().toPlainString());
        }
        profileInfo.setAccountAmountDesc(NumberValidationUtils.formatPrice(profileInfo.getAccountAmount()));
        profileInfo.setAvailableAmountDesc(NumberValidationUtils.formatPrice(profileInfo.getAvailableAmount()));
        profileInfo.setLockAmountDesc(NumberValidationUtils.formatPrice(profileInfo.getLockAmount()));
        return ResponseModel.succ(profileInfo);
    }

    /**
     * 发短信
     */
    @PostMapping(value = "/open/sendSmsCode")
    public ResponseModel sendSmsCode(HttpServletRequest request, HttpServletResponse response, @RequestBody AccountRequest params) {
        log.info("开始调用 sendSmsCode，请求参数:{}", JSON.toJSONString(params));
        String mobile = params.getMobile();
        String smsType = params.getSmsType();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(smsType)) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        //是否超过发送验证码的间隔时间
        if (redisCachemanager.get(cacheSendSmskey(SystemConstant.CACHE_KEY_PREFIX_SEND_VERIFY_INTERVAL_CODE, mobile, smsType)) != null) {
            return ResponseModel.fail(ApiCallResult.CAN_NOT_SEND_VERIFY_CODE);
        }
        String smsCode = RandomNumberGenerator.numberGenerator(6);
        //保存验证码至缓存300秒失效
        redisCachemanager.set(cacheSendSmskey(SystemConstant.CACHE_KEY_PREFIX_PHONE_VERIFY_CODE, mobile, smsType), smsCode, SystemConstant.CACHE_TIMEOUT_PHONE_VERIFY_CODE);
        //保存验证码间隔至缓存 60秒 失效
        redisCachemanager.set(cacheSendSmskey(SystemConstant.CACHE_KEY_PREFIX_SEND_VERIFY_INTERVAL_CODE, mobile, smsType), SystemConstant.CACHE_TIMEOUT_SEND_VERIFY_INTERVAL_CODE, SystemConstant.CACHE_TIMEOUT_SEND_VERIFY_INTERVAL_CODE);

        Map<String, String> smsParamMap = new HashMap<String, String>();
        smsParamMap.put("code", smsCode);
        smsParamMap.put("duration", "5");
        FAliSmsSendReq sendReq = new FAliSmsSendReq();
        sendReq.setPhoneNumber(params.getMobile());
        sendReq.setTemplateCode(AliSmsTemplateEnums.SMS_SEND_CODE.getCode());
        sendReq.setTemplateParam(JSON.toJSONString(smsParamMap));
        log.info("开始调用 aliSmsFacade sendSms，请求参数:{}", JSON.toJSONString(sendReq));
        AliSendSmsResp resp = aliSmsFacade.sendSms(sendReq);
        log.info("结束调用 aliSmsFacade sendSms，请求参数:{}，返回结果:{}", JSON.toJSONString(sendReq), JSON.toJSONString(resp));
        if (resp != null && ApiCallResult.SUCCESS.getCode().equals(resp.getCode())) {
             return ResponseModel.succ();
        }
        return ResponseModel.fail(ApiCallResult.SEND_SMS_CODE_FAIL);
    }

    /**
     * 绑定账户接口
     */
    @PostMapping(value = "/confined/bind/account")
    public ResponseModel registerOrLogin(@RequestBody AccountRequest params) {
        String mobile = params.getMobile();
        String smsCode = params.getSmsCode();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(smsCode)) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        // 校验 验证码
        if (!checkSmsCode(mobile, SmsType.SMS_BIND_ACCOUNT_TYPE.getKey(), smsCode)) {
            return ResponseModel.fail(ApiCallResult.VERIFICATION);
        }
        // 清除验证码
        removeSmsCode(mobile, SmsType.SMS_BIND_ACCOUNT_TYPE.getKey());
        Boolean isNewRegister = false;
        // 校验是否已注册
        AccountResp accountResp = accountFacade.findAccountByMobile(mobile);
        if (accountResp == null || accountResp.getId() == null) {
            AccountReq req = new AccountReq();
            Integer defaultAgencyId = null;
            if (StringUtils.isNotEmpty(params.getInviteCode()) && params.getInviteCode().startsWith(AccountEnum.InviteType.JG.getKey())) {
                String agencyId = params.getInviteCode().replace(AccountEnum.InviteType.JG.getKey(), "");
                if (StringUtils.isNotEmpty(agencyId)) {
                    defaultAgencyId = Integer.parseInt(agencyId);
                }
            }
            if (defaultAgencyId == null) {
                AgencyResp agencyResp = accountFacade.getAgencyByCode(SystemConstant.DEFAULT_AGENCY_CODE);
                if (agencyResp != null) {
                    defaultAgencyId = agencyResp.getId();
                }
            }
            req.setRegisterSource(AccountEnum.RegisterSource.APPLET.getKey());

            //注册
            req.setMobile(mobile);
            req.setPassword(systemProperties.getAppletDefaultRegisterPassword());
            req.setDefaultAgencyId(defaultAgencyId);
            req.setSource("applet");
            req.setDefaultPassword(true);
            boolean registFlag = accountFacade.registerAccount(req);
            if (!registFlag) {
                return ResponseModel.fail();
            }
            isNewRegister = true;
        }
        accountResp = accountFacade.findAccountByMobile(mobile);
        AccountReq.BindAccountReq bindAccountReq = new AccountReq.BindAccountReq();
        bindAccountReq.setId(accountBaseInfo.getExtBindId());
        bindAccountReq.setAccountId(accountResp.getId());
        bindAccountReq.setIsNewRegister(isNewRegister);
        String inviteCode = null;
        String inviteType = null;
        if (isNewRegister) {
            if (StringUtils.isNotEmpty(params.getInviteCode())) {
                if (params.getInviteCode().startsWith(AccountEnum.InviteType.JG.getKey())) {
                    inviteType = AccountEnum.InviteType.JG.getKey();
                    inviteCode = params.getInviteCode().replace(AccountEnum.InviteType.JG.getKey(), "");
                }
                if (params.getInviteCode().startsWith(AccountEnum.InviteType.DP.getKey())) {
                    if (!(AccountEnum.InviteType.DP.getKey() + SystemConstant.DEFAULT_APPLET_SHOP).equals(params.getInviteCode())) {
                        inviteType = AccountEnum.InviteType.DP.getKey();
                        inviteCode = params.getInviteCode().replace(AccountEnum.InviteType.DP.getKey(), "");
                    }
                }
            }
        }
        if (StringUtils.isEmpty(inviteCode)) {
            AgencyResp agencyResp = accountFacade.getAgencyByCode(SystemConstant.DEFAULT_AGENCY_CODE);
            if (agencyResp != null) {
                inviteCode = agencyResp.getId() + "";
                inviteType = AccountEnum.InviteType.JG.getKey();
            }
        }
        bindAccountReq.setInviteCode(Integer.parseInt(inviteCode));
        bindAccountReq.setInviteType(inviteType);
        accountFacade.bindAccount(bindAccountReq);
        return profile();
    }

    /**
     * 选择账户角色接口
     */
    @PostMapping(value = "/confined/select/party")
    public ResponseModel selectParty(@RequestBody AccountReq.SelectPartyReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setId(accountBaseInfo.getExtBindId());
        accountFacade.selectParty(req);
        return profile();
    }
}
