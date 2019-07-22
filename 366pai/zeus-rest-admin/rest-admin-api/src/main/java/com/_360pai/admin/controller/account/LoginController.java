package com._360pai.admin.controller.account;

import com._360pai.admin.controller.AbstractController;
import com._360pai.admin.controller.account.req.AccountRequest;
import com._360pai.admin.shiro.ShiroAuthService;
import com._360pai.admin.shiro.PermissionService;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.PasswordEncryption;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.common.constants.SmsType;
import com._360pai.core.common.constants.StaffEnum;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.StaffFacade;
import com._360pai.core.facade.account.req.StaffReq;
import com._360pai.core.facade.account.resp.StaffResp;
import com._360pai.core.facade.account.vo.StaffVo;
import com._360pai.gateway.common.alisms.AliSmsTemplateEnums;
import com._360pai.gateway.controller.req.alisms.FAliSmsSendReq;
import com._360pai.gateway.facade.AliSmsFacade;
import com._360pai.gateway.resp.AliSendSmsResp;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @date 17/09/2018 09:24
 */
@RestController
public class LoginController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;
    @Reference(version = "1.0.0")
    private StaffFacade staffFacade;

    @Reference(version = "1.0.0")
    private AliSmsFacade aliSmsFacade;

    @Resource
    private RedisCachemanager redisCachemanager;

    @Resource
    private ShiroAuthService adminShiroAuthenService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private Producer captchaProducer;

    /**
     * 员工登录
     */
    @PostMapping(value = "/admin/login")
    public ResponseModel login(@RequestBody AccountRequest accountRequest, HttpServletResponse response){
        String mobile = accountRequest.getMobile();
        String password = accountRequest.getPassword();
        String smsCode = accountRequest.getSmsCode();

        if (StringUtils.isEmpty(mobile) || (StringUtils.isEmpty(password) && StringUtils.isEmpty(smsCode))) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        StaffReq.BaseReq req = new StaffReq.BaseReq();
        req.setMobile(mobile);
        StaffResp.BasicResp resp = staffFacade.getStaffBasic(req);
        if(resp == null || resp.getId() == null){
            return ResponseModel.fail(ApiCallResult.NOREGISTER);
        }
        if (StaffEnum.Status.DISABLED.getKey().equals(resp.getStatus())) {
            return ResponseModel.fail(ApiCallResult.ACCOUNT_DISABLED);
        }
        if (StringUtils.isEmpty(smsCode)) {
            if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
                return ResponseModel.fail(ApiCallResult.EMPTY);
            }
            try {
                if(!PasswordEncryption.authenticate(password,resp.getPasswordHash())){
                    return ResponseModel.fail(ApiCallResult.ERROR_PASSWORD);
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        } else {
            //短信验证码登录
            if(checkSmsCode(mobile,SmsType.ADMIN_SMS_LOGIN_TYPE.getKey(),smsCode)){
                //清除验证码
                removeSmsCode(mobile,SmsType.ADMIN_SMS_LOGIN_TYPE.getKey());
            }else{
                return ResponseModel.fail(ApiCallResult.VERIFICATION);
            }
        }
        //保存 ticket
        Integer loginId = resp.getId();
        adminShiroAuthenService.saveTicket(response,loginId,SystemConstant.ACCOUNT_ADMIN_STAFF_TYPE, resp.getIsAdmin());
        return ResponseModel.succ(permissionService.getPermission(loginId + "", resp.getIsAdmin()));
    }

    /**
     * 修改密码
     */
    @PostMapping(value = "/admin//modify/password")
    public ResponseModel modifyPassword(@RequestBody StaffReq.ModifyPasswordReq req) {
        req.setStaffId(loadCurLoginId());
        return ResponseModel.succ(staffFacade.staffModifyPassword(req));
    }

    /**
     * 退出
     */
    @PostMapping(value = "/admin//logout")
    public ResponseModel logout(@RequestBody AccountRequest params) {
        adminShiroAuthenService.removeTicket(loadCurLoginId(), SystemConstant.ACCOUNT_ADMIN_STAFF_TYPE);
        return ResponseModel.succ();
    }

    /**
     * 发短信，注册短信、忘记密码短信、登录短信
     */

    @PostMapping(value = "/admin/sendSmsCode")
    public ResponseModel sendSmsCode(HttpServletRequest request, HttpServletResponse response, @RequestBody AccountRequest params) {
        LOGGER.info("开始调用 sendSmsCode，请求参数:{}", JSON.toJSONString(params));
        String mobile = params.getMobile();
        String smsType = params.getSmsType();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(smsType)) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        StaffReq.BaseReq req = new StaffReq.BaseReq();
        req.setMobile(mobile);
        StaffResp.BasicResp resp = staffFacade.getStaffBasic(req);

        //登录、忘记密码、修改密码、修改手机号类型
        if(smsType.equals(SmsType.ADMIN_SMS_LOGIN_TYPE.getKey())){
            if(resp == null || resp.getId() == null){
                return ResponseModel.fail(ApiCallResult.NOREGISTER);
            }
        } else {
            return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
        }
        if (SmsType.isNeedCaptcha(params.getSmsType())) {
            String clientCaptcha = params.getCaptcha();
            // 判断验证码是否正确
            String serverCaptcha = (String) redisCachemanager.get(SystemConstant.ADMIN_KAPTCHA_SESSION_KEY + request.getRequestedSessionId());
            if (!clientCaptcha.equals(serverCaptcha)) {
                return ResponseModel.fail(ApiCallResult.KAPTCHA_ERRPR);
            }
            redisCachemanager.del(SystemConstant.ADMIN_KAPTCHA_SESSION_KEY + request.getRequestedSessionId());
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
        AliSendSmsResp smsResp = aliSmsFacade.sendSms(sendReq);
        LOGGER.info("结束调用 aliSmsFacade sendSms，请求参数:{}，返回结果:{}",JSON.toJSONString(sendReq),JSON.toJSONString(resp));
        if(smsResp != null &&  ApiCallResult.SUCCESS.getCode().equals(smsResp.getCode())){
            return ResponseModel.succ();
        }
        return ResponseModel.fail(ApiCallResult.SEND_SMS_CODE_FAIL);
    }

    protected String cacheSendSmskey(String pre,String mobile,String type){
        return pre+mobile+"_"+type;
    }

    /**
     * 校验验证码
     */
    protected boolean checkSmsCode(String mobile,String smsType,String smsCode){
        Object code = redisCachemanager.get(cacheSendSmskey(SystemConstant.CACHE_KEY_PREFIX_PHONE_VERIFY_CODE,mobile,smsType));
        String redisSmsCode =String.valueOf(code);
        return smsCode.equals(redisSmsCode);
    }

    /**
     * 清除验证码
     */
    protected void removeSmsCode(String mobile,String smsType){
        redisCachemanager.del(cacheSendSmskey(SystemConstant.CACHE_KEY_PREFIX_PHONE_VERIFY_CODE,mobile,""));
    }

    /**
     * 登录账户信息
     */
    @GetMapping(value = "/admin/profile")
    public ResponseModel profile(StaffReq.BaseReq req) {
        req.setStaffId(loadCurLoginId());
        StaffResp.DetailResp resp = staffFacade.getStaff(req);
        StaffVo staffVo = resp.getStaff();
        Map<String, Object> data = new HashMap<>();
        data.put("id", staffVo.getId());
        data.put("mobile", staffVo.getMobile());
        data.put("name", staffVo.getId());
        data.put("isAdmin", staffVo.getIsAdmin());
        data.put("permission", permissionService.getPermission(staffVo.getId() + "", staffVo.getIsAdmin()));
        return ResponseModel.succ(data);
    }

    /**
     * 获取图形验证码
     */
    @GetMapping("/admin/getKaptchaImage")
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
        redisCachemanager.set(SystemConstant.ADMIN_KAPTCHA_SESSION_KEY + sessionId, capText, 600);
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
