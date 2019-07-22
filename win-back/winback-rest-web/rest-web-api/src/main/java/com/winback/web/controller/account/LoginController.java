package com.winback.web.controller.account;

import com.alibaba.dubbo.config.annotation.Reference;
import com.winback.arch.common.AppReq;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.commons.constants.SmsType;
import com.winback.core.facade.account.AccountFacade;
import com.winback.core.facade.account.req.AppAccountReq;
import com.winback.core.facade.account.resp.AppAccountResp;
import com.winback.web.controller.AbstractController;
import com.winback.web.service.CaptchaService;
import com.winback.web.shiro.ShiroAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: LoginController
 * @ProjectName winback
 * @Description:
 * @date 2019/1/24 16:22
 */
@Slf4j
@RestController
public class LoginController extends AbstractController {

    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;
    @Autowired
    private ShiroAuthService shiroAuthService;
    @Autowired
    private CaptchaService captchaService;

    /**
     * 登录接口
     */
    @PostMapping(value = "/open/login")
    public ResponseModel login(@RequestBody AppAccountReq.LoginReq req, HttpServletResponse response) {
        if (StringUtils.isEmpty(req.getMobile()) || (StringUtils.isEmpty(req.getPassword()) && StringUtils.isEmpty(req.getSmsCode()))) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AppAccountResp.AccountResp resp = accountFacade.login(req);
        shiroAuthService.saveTicket(getCurRequest(), response, resp.getLoginId());
        return ResponseModel.succ();
    }

    /**
     * 退出登录接口
     */
    @PostMapping(value = "/confined/account/logout")
    public ResponseModel logout(@RequestBody AppReq req) {
        shiroAuthService.removeTicket(loadCurLoginId());
        return ResponseModel.succ();
    }

    /**
     * 发短信接口
     */
    @PostMapping(value = "/open/sendSmsCode")
    public ResponseModel sendSmsCode(@RequestBody AppAccountReq.SmsReq req, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(req.getMobile()) || StringUtils.isEmpty(req.getSmsType())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        if (SmsType.isNeedCaptcha(req.getSmsType())) {
            if (!captchaService.checkCaptcha(request.getRequestedSessionId(), req.getCaptcha())) {
                return ResponseModel.fail(ApiCallResult.KAPTCHA_ERRPR);
            }
        }
        String smsCode = accountFacade.sendSmsCode(req);
        log.info("smsCode={}", smsCode);
        return ResponseModel.succ();
    }

    /**
     * 检验手机号是否能注册接口  true能,false不能
     */

    @PostMapping(value = "/open/checkMobile")
    public ResponseModel checkMobile(@RequestBody AppAccountReq.CheckMobileReq req) {
        if (StringUtils.isEmpty(req.getMobile())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("hasRegister", accountFacade.hasRegister(req.getMobile()));
        return ResponseModel.succ(data);
    }

    /**
     * 注册接口
     */
    @PostMapping(value = "/open/register")
    public ResponseModel register(@RequestBody AppAccountReq.RegisterReq req, HttpServletResponse response) {
        if (StringUtils.isEmpty(req.getMobile()) || StringUtils.isEmpty(req.getSmsCode())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AppAccountResp.AccountResp resp = accountFacade.register(req);
        shiroAuthService.saveTicket(getCurRequest(),response, resp.getLoginId());
        return ResponseModel.succ();
    }

    /**
     * 忘记密码接口
     */
    @PostMapping(value = "/open/forgetPassword")
    public ResponseModel modifyPassword(@RequestBody AppAccountReq.ForgetPasswordReq req, HttpServletResponse response) {
        if (StringUtils.isEmpty(req.getMobile()) || StringUtils.isEmpty(req.getPassword()) || StringUtils.isEmpty(req.getSmsCode())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AppAccountResp.AccountResp resp =accountFacade.forgetPassword(req);
        shiroAuthService.saveTicket(getCurRequest(),response, resp.getLoginId());
        return ResponseModel.succ();
    }
}
