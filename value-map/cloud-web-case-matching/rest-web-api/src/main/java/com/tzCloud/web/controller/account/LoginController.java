package com.tzCloud.web.controller.account;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tzCloud.arch.common.PlatformReq;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.core.common.constants.SmsType;
import com.tzCloud.core.facade.account.AccountFacade;
import com.tzCloud.core.facade.account.req.PlatformAccountReq;
import com.tzCloud.core.facade.account.resp.PlatformAccountResp;
import com.tzCloud.web.controller.AbstractController;
import com.tzCloud.web.service.CaptchaService;
import com.tzCloud.web.shiro.ShiroAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xdrodger
 * @Title: LoginController
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019-04-19 13:44
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
    @PostMapping(value = "/open/loginOrRegister")
    public ResponseModel loginOrRegister(@RequestBody PlatformAccountReq.LoginReq req, HttpServletResponse response) {
        if (StringUtils.isEmpty(req.getMobile()) || (StringUtils.isEmpty(req.getPassword()) && StringUtils.isEmpty(req.getSmsCode()))) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        PlatformAccountResp.AccountResp resp = accountFacade.loginOrRegister(req);
        shiroAuthService.saveTicket(getCurRequest(), response, resp.getLoginId());
        return ResponseModel.succ(resp);
    }

    /**
     * 退出登录接口
     */
    @PostMapping(value = "/confined/account/logout")
    public ResponseModel logout(@RequestBody PlatformReq req) {
        shiroAuthService.removeTicket(loadCurLoginId());
        return ResponseModel.succ();
    }

    /**
     * 发短信接口
     */
    @PostMapping(value = "/open/sendSmsCode")
    public ResponseModel sendSmsCode(@RequestBody PlatformAccountReq.SmsReq req, HttpServletRequest request, HttpServletResponse response) {
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
     * 忘记密码接口
     */
    @PostMapping(value = "/open/forgetPassword")
    public ResponseModel modifyPassword(@RequestBody PlatformAccountReq.ForgetPasswordReq req, HttpServletResponse response) {
        if (StringUtils.isEmpty(req.getMobile()) || StringUtils.isEmpty(req.getPassword()) || StringUtils.isEmpty(req.getSmsCode())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        PlatformAccountResp.AccountResp resp = accountFacade.forgetPassword(req);
        shiroAuthService.saveTicket(getCurRequest(),response, resp.getLoginId());
        return ResponseModel.succ();
    }
}
