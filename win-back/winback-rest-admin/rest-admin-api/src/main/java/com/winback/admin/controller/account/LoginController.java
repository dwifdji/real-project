package com.winback.admin.controller.account;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.winback.admin.controller.AbstractController;
import com.winback.admin.service.CaptchaService;
import com.winback.admin.shiro.PermissionService;
import com.winback.admin.shiro.ShiroAuthService;
import com.winback.admin.vo.Profile;
import com.winback.arch.common.AdminReq;
import com.winback.arch.common.AppReq;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.commons.constants.SmsType;
import com.winback.core.facade.account.AccountFacade;
import com.winback.core.facade.account.SysFacade;
import com.winback.core.facade.account.req.AdminAccountReq;
import com.winback.core.facade.account.req.AppAccountReq;
import com.winback.core.facade.account.resp.AdminAccountResp;
import com.winback.core.facade.account.vo.Staff;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    private PermissionService permissionService;
    /**
     * 用户信息服务
     */
    @Reference(version = "1.0.0")
    private SysFacade sysFacade;

    /**
     * 登录接口
     */
    @PostMapping(value = "/open/login")
    public ResponseModel login(@RequestBody AdminAccountReq.LoginReq req, HttpServletResponse response) {
        if (StringUtils.isEmpty(req.getMobile()) || (StringUtils.isEmpty(req.getPassword()) && StringUtils.isEmpty(req.getSmsCode()))) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AdminAccountResp.AccountResp resp = accountFacade.login(req);
        shiroAuthService.saveTicket(getCurRequest(), response, resp.getLoginId(), resp.getAdminFlag());
        Profile profile = new Profile();
        Staff staff = sysFacade.getStaff(resp.getLoginId());
        BeanUtils.copyProperties(staff, profile);
        Map<String, Object> data = (Map<String, Object>) JSON.toJSON(profile);
        data.put("permission", permissionService.getPermission(resp.getLoginId() + "", resp.getAdminFlag()));
        return ResponseModel.succ(data);
    }


    /**
     * 退出登录接口
     */
    @PostMapping(value = "/confined/account/logout")
    public ResponseModel logout(@RequestBody AdminReq req) {
        shiroAuthService.removeTicket(loadCurLoginId());
        return ResponseModel.succ();
    }

    /**
     * 发短信接口
     */
    @PostMapping(value = "/open/sendSmsCode")
    public ResponseModel sendSmsCode(@RequestBody AdminAccountReq.SmsReq req, HttpServletRequest request, HttpServletResponse response) {
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
