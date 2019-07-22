package com._360pai.partner.controller.account;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.PasswordEncryption;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.common.constants.SmsType;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.req.AgencyReq;
import com._360pai.core.facade.account.resp.AccountResp;
import com._360pai.core.facade.account.resp.AgencyResp;
import com._360pai.gateway.common.alisms.AliSmsTemplateEnums;
import com._360pai.gateway.controller.req.alisms.FAliSmsSendReq;
import com._360pai.gateway.facade.AliSmsFacade;
import com._360pai.gateway.resp.AliSendSmsResp;
import com._360pai.partner.controller.AbstractController;
import com._360pai.partner.controller.account.req.AccountRequest;
import com._360pai.partner.controller.account.resp.AccountBaseInfo;
import com._360pai.partner.shiro.ShiroAuthService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: LoginController
 * @ProjectName zeus
 * @Description:
 * @date 17/09/2018 11:17
 */
@RestController
public class LoginController extends AbstractController {
    public static final Logger LOGGER = LoggerFactory.getLogger(AccountAgencyController.class);

    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;
    @Resource
    private ShiroAuthService agencyShiroAuthenService;
    @Resource
    private RedisCachemanager redisCachemanager;
    @Reference(version = "1.0.0")
    private AliSmsFacade aliSmsFacade;
    @Autowired
    private Producer captchaProducer;
    /**
     * 机构登录
     */
    @PostMapping(value = "/agency/login")
    public ResponseModel login(@RequestBody AccountRequest accountRequest, HttpServletResponse response){
        String mobile = accountRequest.getMobile();
        Assert.notNull(accountRequest.getMobile(), "mobile 参数不能为空");
        String password = accountRequest.getPassword();
        String smsCode = accountRequest.getSmsCode();
        AccountResp accountResp = accountFacade.findAccountByMobile(mobile);
        if(accountResp == null || accountResp.getId() == null){
            return ResponseModel.fail(ApiCallResult.NOREGISTER);
        }
        if (accountResp.getAgencyId() == null) {
            return ResponseModel.fail("该账号尚未绑定机构，无法登录");
        }
        Integer accountId = accountResp.getId();
        //密码登录
        if(StringUtils.isEmpty(smsCode)){
            if(StringUtils.isEmpty(password)){
                return ResponseModel.fail(ApiCallResult.EMPTY);
            }
            try {
                if(!PasswordEncryption.authenticate(password,accountResp.getPassword())){
                    return ResponseModel.fail(ApiCallResult.ERROR_PASSWORD);
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }else{
            //短信验证码登录
            if(!checkSmsCode(mobile,SmsType.AGENCY_SMS_LOGIN_TYPE.getKey(),smsCode)){
                return ResponseModel.fail(ApiCallResult.VERIFICATION);
            }
            //清除验证码
            removeSmsCode(mobile, SmsType.AGENCY_SMS_LOGIN_TYPE.getKey());
        }
        agencyShiroAuthenService.saveTicket(getCurRequest(),response,accountId, accountResp.getAgencyId(), SystemConstant.ACCOUNT_AGENCY_TYPE);
        AgencyResp agency = accountFacade.getAgencyById(accountResp.getAgencyId());
        Map<String, Object> data = new HashMap<>();
        data.put("agencyCode", agency.getCode());
        return ResponseModel.succ(data);
    }

    /**
     * 退出
     */
    @PostMapping(value = "/agency/logout")
    public ResponseModel logout(@RequestBody AccountRequest params) {
        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();
        agencyShiroAuthenService.removeTicket(accountInfo.getAccountId(),accountInfo.getType());
        return ResponseModel.succ();
    }

    /**
     * 发短信，注册短信、忘记密码短信、登录短信
     */

    @PostMapping(value = "agency/sendSmsCode")
    public ResponseModel sendSmsCode(HttpServletRequest request, HttpServletResponse response, @RequestBody AccountRequest params) {
        LOGGER.info("开始调用 sendSmsCode，请求参数:{}", JSON.toJSONString(params));
        String mobile = params.getMobile();
        String smsType = params.getSmsType();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(smsType)) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountResp accountResp = accountFacade.findAccountByMobile(mobile);
        //登录
        if(smsType.equals(SmsType.AGENCY_SMS_LOGIN_TYPE.getKey())){
            if(accountResp == null || accountResp.getId() == null){
                return ResponseModel.fail(ApiCallResult.NOREGISTER);
            }
        }
        if (SmsType.isNeedCaptcha(params.getSmsType())) {
            String clientCaptcha = params.getCaptcha();
            LOGGER.info("sessionId={}", request.getRequestedSessionId());
            // 判断验证码是否正确
            String serverCaptcha = (String) redisCachemanager.get(SystemConstant.AGENCY_KAPTCHA_SESSION_KEY + request.getRequestedSessionId());
            if (!clientCaptcha.equals(serverCaptcha)) {
                return ResponseModel.fail(ApiCallResult.KAPTCHA_ERRPR);
            }
            redisCachemanager.del(SystemConstant.AGENCY_KAPTCHA_SESSION_KEY + request.getRequestedSessionId());
        }
        //是否超过发送验证码的间隔时间
        if(redisCachemanager.get(cacheSendSmskey(SystemConstant.CACHE_KEY_PREFIX_SEND_VERIFY_INTERVAL_CODE,mobile,smsType)) != null){
            return ResponseModel.fail(ApiCallResult.CAN_NOT_SEND_VERIFY_CODE);
        }
        String smsCode = RandomNumberGenerator.numberGenerator(6);
        //保存验证码至缓存300秒失效
        redisCachemanager.set(cacheSendSmskey(SystemConstant.CACHE_KEY_PREFIX_PHONE_VERIFY_CODE,mobile,smsType), smsCode,SystemConstant.CACHE_TIMEOUT_PHONE_VERIFY_CODE);
        //保存验证码间隔至缓存 60秒 失效
        redisCachemanager.set(cacheSendSmskey(SystemConstant.CACHE_KEY_PREFIX_SEND_VERIFY_INTERVAL_CODE,mobile,smsType),SystemConstant.CACHE_TIMEOUT_SEND_VERIFY_INTERVAL_CODE,SystemConstant.CACHE_TIMEOUT_SEND_VERIFY_INTERVAL_CODE);

        Map<String,String> smsParamMap = new HashMap<String,String>();
        smsParamMap.put("code", smsCode);
        smsParamMap.put("duration", "5");
        FAliSmsSendReq sendReq = new FAliSmsSendReq();
        sendReq.setPhoneNumber(params.getMobile());
        sendReq.setTemplateCode(AliSmsTemplateEnums.SMS_SEND_CODE.getCode());
        sendReq.setTemplateParam(JSON.toJSONString(smsParamMap));
        LOGGER.info("开始调用 aliSmsFacade sendSms，请求参数:{}",JSON.toJSONString(sendReq));
        AliSendSmsResp resp = aliSmsFacade.sendSms(sendReq);
        LOGGER.info("结束调用 aliSmsFacade sendSms，请求参数:{}，返回结果:{}",JSON.toJSONString(sendReq),JSON.toJSONString(resp));
        if(resp != null &&  ApiCallResult.SUCCESS.getCode().equals(resp.getCode())){
            return ResponseModel.succ();
        }
        return ResponseModel.fail(ApiCallResult.SEND_SMS_CODE_FAIL);
    }

    /**
     * 登录账户信息
     */
    @GetMapping(value = "/agency/profile")
    public ResponseModel profile(AgencyReq.BaseReq req) {
        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();
        req.setAccountId(accountInfo.getAccountId());
        req.setAgencyId(accountInfo.getAgencyId());
        return ResponseModel.succ(accountFacade.agencyProfile(req));
    }

    /**
     * 获取图形验证码
     */
    @GetMapping("/agency/getKaptchaImage")
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
        request.getSession().setAttribute("capText", capText);
        LOGGER.info("sessionId={}, kaptcha={}", sessionId, capText);
        redisCachemanager.set(SystemConstant.AGENCY_KAPTCHA_SESSION_KEY + sessionId, capText, 600);
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
