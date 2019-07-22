package com.winback.applet.controller.account;

import com.alibaba.dubbo.config.annotation.Reference;
import com.winback.applet.controller.AbstractController;
import com.winback.applet.service.CaptchaService;
import com.winback.applet.shiro.ShiroAuthService;
import com.winback.arch.common.AppReq;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.commons.constants.SmsType;
import com.winback.core.facade.account.AccountFacade;
import com.winback.core.facade.account.req.AppAccountReq;
import com.winback.core.facade.account.req.AppletAccountReq;
import com.winback.core.facade.account.resp.AppletAccountResp;
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
    public ResponseModel login(@RequestBody AppletAccountReq.LoginReq req, HttpServletResponse response) {
        if (StringUtils.isEmpty(req.getCode())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AppletAccountResp.LoginResp resp = accountFacade.login(req);
        String ticket = shiroAuthService.saveTicket(getCurRequest(), response, resp.getLoginId());
        Map<String, Object> data = new HashMap<>();
        data.put("_applet_id", resp.getLoginId());
        data.put("_applet_ticket", ticket);
        return ResponseModel.succ(data);
    }

    /**
     * 退出登录接口shopping/cart/item/add
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
    public ResponseModel sendSmsCode(@RequestBody AppletAccountReq.SmsReq req, HttpServletRequest request, HttpServletResponse response) {
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

}
