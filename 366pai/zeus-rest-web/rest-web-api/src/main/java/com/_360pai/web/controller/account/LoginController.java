package com._360pai.web.controller.account;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.PasswordEncryption;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.common.constants.SmsType;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.req.AccountReq;
import com._360pai.core.facade.account.resp.*;
import com._360pai.core.facade.activity.AuctionFacade;
import com._360pai.gateway.common.alisms.AliSmsTemplateEnums;
import com._360pai.gateway.controller.req.alisms.FAliSmsSendReq;
import com._360pai.gateway.facade.AliSmsFacade;
import com._360pai.gateway.resp.AliSendSmsResp;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.req.AccountRequest;
import com._360pai.web.shiro.ShiroAuthenService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/open", produces = "application/json;charset=UTF-8")
public class LoginController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;

    @Reference(version = "1.0.0")
    private AuctionFacade auctionFacade;

    @Reference(version = "1.0.0")
    private AliSmsFacade aliSmsFacade;

    @Resource
    private RedisCachemanager redisCachemanager;

    @Resource
    private ShiroAuthenService shiroAuthenService;

    @Autowired
    private Producer captchaProducer;

    /**
     * 注册
     */

    @PostMapping(value = "/register")
    public ResponseModel register(@RequestBody AccountRequest params, HttpServletResponse response) {

        LOGGER.info("开始调用 register，请求参数:{}", JSON.toJSONString(params));

        String mobile = params.getMobile();
        String password = params.getPassword();
        String agenyCode = params.getAgencyCode();
        String smsCode = params.getSmsCode();

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(smsCode)
                || StringUtils.isEmpty(password)) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        // 校验是否已注册
        AccountResp accountResp = accountFacade.findAccountByMobile(mobile);
        if (accountResp != null && accountResp.getId() != null) {
            return ResponseModel.fail(ApiCallResult.HASREGISTER);
        }

        AgencyResp agencyResp = null;

        AccountReq req = new AccountReq();

        // 校验agencyCode 是否存在
        if (StringUtils.isEmpty(agenyCode)) {
            agencyResp = accountFacade.getAgencyByCode(SystemConstant.DEFAULT_AGENCY_CODE);
            req.setRegisterSource(AccountEnum.RegisterSource.WEB.getKey());
        } else {
            agencyResp = accountFacade.getAgencyByCode(agenyCode);
            if (agencyResp == null || agencyResp.getId() == null) {
                return ResponseModel.fail(ApiCallResult.NOAGENCY);
            }
            req.setRegisterSource(AccountEnum.RegisterSource.AGENCY.getKey());
        }

        // 校验 验证码
        if (!checkSmsCode(mobile, SmsType.SMS_REGISTER_TYPE.getKey(), smsCode)) {
            return ResponseModel.fail(ApiCallResult.VERIFICATION);
        }

        // 清除验证码
        removeSmsCode(mobile, SmsType.SMS_REGISTER_TYPE.getKey());

        //注册

        req.setMobile(mobile);
        req.setPassword(password);
        req.setDefaultAgencyId(agencyResp.getId());
        req.setSource(params.getSource());
        boolean registFlag = accountFacade.registerAccount(req);
        if (!registFlag) {
            return ResponseModel.fail();
        }
        if (params.isFastway()) {
            accountResp = accountFacade.findAccountByMobile(mobile);
            shiroAuthenService.saveTicket(getCurRequest(),response, accountResp.getId(), accountResp.getAgencyId(), SystemConstant.ACCOUNT_COMMON_TYPE);
        }
        return ResponseModel.succ();
    }


    /**
     * 忘记密码
     */

    @PostMapping(value = "/forgetPassword")
    public ResponseModel forgetPassword(@RequestBody AccountRequest params) {
        String mobile = params.getMobile();
        String password = params.getPassword();
        String smsCode = params.getSmsCode();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(smsCode)) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        // 校验手机号是否注册
        AccountResp accountResp = accountFacade.findAccountByMobile(mobile);
        if (accountResp != null && accountResp.getId() != null) {
            // 校验 验证码
            if (checkSmsCode(mobile, SmsType.SMS_FORGET_PWD_TYPE.getKey(), smsCode)) {
                // 删除 验证码
                removeSmsCode(mobile, SmsType.SMS_FORGET_PWD_TYPE.getKey());
                // 更新密码
                AccountReq accountReq = new AccountReq();
                accountReq.setId(accountResp.getId());
                try {
                    accountReq.setPassword(PasswordEncryption.getEncryptedPassword(password));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }
                boolean flag = accountFacade.updateAccountById(accountReq);
                if (flag) {
                    return ResponseModel.succ();
                } else {
                    return ResponseModel.fail();
                }
            } else {
                return ResponseModel.fail(ApiCallResult.VERIFICATION);
            }

        } else {
            return ResponseModel.fail(ApiCallResult.NOREGISTER);
        }
    }

    /**
     * 用户登录
     */
    @PostMapping(value = "/login")
    public ResponseModel login(@RequestBody AccountRequest accountRequest, HttpServletResponse response) {
        String mobile = accountRequest.getMobile();
        String password = accountRequest.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountResp accountResp = accountFacade.findAccountByMobile(mobile);
        if (accountResp == null || accountResp.getId() == null) {
            return ResponseModel.fail(ApiCallResult.NOREGISTER);
        }
        try {
            if (!PasswordEncryption.authenticate(password, accountResp.getPassword())) {
                return ResponseModel.fail(ApiCallResult.ERROR_PASSWORD);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ResponseModel.fail(ApiCallResult.FAILURE);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return ResponseModel.fail(ApiCallResult.FAILURE);
        }
        //保存 ticket
        Integer accountId = accountResp.getId();
        shiroAuthenService.saveTicket(getCurRequest(),response, accountId, accountResp.getAgencyId(), SystemConstant.ACCOUNT_COMMON_TYPE);
        return ResponseModel.succ();
    }


    /**
     * 发短信，注册短信、忘记密码短信、登录短信、spv申请
     */
    @PostMapping(value = "/sendSmsCode")
    public ResponseModel sendSmsCode(HttpServletRequest request, HttpServletResponse response, @RequestBody AccountRequest params) {
        LOGGER.info("开始调用 sendSmsCode，请求参数:{}", JSON.toJSONString(params));
        String mobile = params.getMobile();
        String smsType = params.getSmsType();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(smsType)) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountResp accountResp = accountFacade.findAccountByMobile(mobile);
        //注册类型
        if (smsType.equals(SmsType.SMS_REGISTER_TYPE.getKey())) {
            //校验是否已经注册，已注册返回错误
            if (accountResp != null && accountResp.getId() != null) {
                return ResponseModel.fail(ApiCallResult.HASREGISTER);
            }

        }
        //登录、忘记密码、修改密码、修改手机号类型
        if (smsType.equals(SmsType.SMS_LOGIN_TYPE.getKey())
                || smsType.equals(SmsType.SMS_FORGET_PWD_TYPE.getKey())
                || smsType.equals(SmsType.SMS_MODIFY_PWD_TYPE.getKey())
                || smsType.equals(SmsType.SMS_MODIFY_MOBILE_TYPE.getKey())
        ) {
            if (accountResp == null || accountResp.getId() == null) {
                return ResponseModel.fail(ApiCallResult.NOREGISTER);

            }
        }

        //spv 短信
        if(smsType.equals(SmsType.SMS_SPV_APPLY_TYPE.getKey())){
            if(!accountFacade.checkSpvIsPendingOrApproved(mobile)){
                return ResponseModel.fail(ApiCallResult.SPV_CAN_NOT_SEND_SMS);
            }
        }
        if (SmsType.isNeedCaptcha(params.getSmsType())) {
            String clientCaptcha = params.getCaptcha();
            LOGGER.info("sessionId={}", request.getRequestedSessionId());
            // 判断验证码是否正确
            String serverCaptcha = (String) redisCachemanager.get(SystemConstant.KAPTCHA_SESSION_KEY + request.getRequestedSessionId());
            if (!clientCaptcha.equals(serverCaptcha)) {
                return ResponseModel.fail(ApiCallResult.KAPTCHA_ERRPR);
            }
            redisCachemanager.del(SystemConstant.KAPTCHA_SESSION_KEY + request.getRequestedSessionId());
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
        LOGGER.info("开始调用 aliSmsFacade sendSms，请求参数:{}", JSON.toJSONString(sendReq));
        AliSendSmsResp resp = aliSmsFacade.sendSms(sendReq);
        LOGGER.info("结束调用 aliSmsFacade sendSms，请求参数:{}，返回结果:{}", JSON.toJSONString(sendReq), JSON.toJSONString(resp));
        if (resp != null && ApiCallResult.SUCCESS.getCode().equals(resp.getCode())) {
            return ResponseModel.succ();
        }
        return ResponseModel.fail(ApiCallResult.SEND_SMS_CODE_FAIL);
    }

    /**
     * 检验手机号是否能注册  true能,false不能
     */

    @PostMapping(value = "/checkMobile")
    public ResponseModel checkMobile(@RequestBody AccountRequest params) {
        LOGGER.info("开始调用checkMobile，请求参数:{}", JSON.toJSONString(params));
        if (StringUtils.isEmpty(params.getMobile())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        } else {
            AccountResp accountResp = accountFacade.findAccountByMobile(params.getMobile());
            if (accountResp != null && accountResp.getId() != null) {
                return ResponseModel.fail(ApiCallResult.HASREGISTER);
            }
        }
        return ResponseModel.succ();
    }

    @GetMapping(value = "/dealOnlineActivityData")
    public ResponseModel dealOnlineActivityData(){
        LOGGER.info("开始调用 dealOnlineActivityData");
        Integer count =auctionFacade.dealOnlineActivityData();
        return ResponseModel.succ(count);
    }

    /**
     * 获取图形验证码
     */
    @GetMapping("/getKaptchaImage")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        // create the text for the image
        String capText = captchaProducer.createText();
        // store the text in the session
        //将验证码存到session

        String sessionId = request.getSession().getId();
        LOGGER.info("sessionId={}, kaptcha={}", sessionId, capText);
        redisCachemanager.set(SystemConstant.KAPTCHA_SESSION_KEY + sessionId, capText, 600);
        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }
}
