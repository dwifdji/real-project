package com.winback.applet.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.winback.applet.vo.LoginInfo;
import com.winback.arch.common.constant.SystemConstant;
import com.winback.arch.common.utils.OSUtil;
import com.winback.arch.common.utils.ThreadLocalContextUtil;
import com.winback.arch.core.redis.RedisCachemanager;
import com.winback.core.facade.account.AccountFacade;
import com.winback.core.facade.account.SysFacade;
import com.winback.core.facade.account.vo.AccountInfo;
import com.winback.core.facade.account.vo.AppletAccountInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * @author xdrodger
 * @Title: AbstractController
 * @ProjectName winback
 * @Description:
 * @date 2019/1/24 15:55
 */
@Slf4j
@Controller
public class AbstractController {

    @Resource
    private RedisCachemanager redisCachemanager;

    /**
     * 用户信息服务
     */
    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;
    /**
     * 用户信息服务
     */
    @Reference(version = "1.0.0")
    private SysFacade sysFacade;

    /**
     * 当前的HttpServletRequest
     */
    @Autowired
    private HttpServletRequest curRequest;

    /**
     * 获取curRequest
     *
     * @return the curRequest
     */
    protected HttpServletRequest getCurRequest() {
        return curRequest;
    }

    /**
     * 获取当前请求者Ip
     *
     * @return loginUserIp
     */
    protected String getCurRequestIp() {
        return OSUtil.getIpAddress(curRequest);
    }

    protected String getSelfIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取当前登录id
     */
    protected final Integer loadCurLoginId() {
        return (Integer) ThreadLocalContextUtil.getValue(SystemConstant.APPLET_COOKIE_ID_NAME);

    }

    /**
     * 获取当前登录信息
     */
    protected final LoginInfo loadCurLoginInfo() {
        LoginInfo loginInfo = new LoginInfo();
        Integer loginId = loadCurLoginId();
        if (loginId == null || loginId.intValue() == -1) {
            log.warn("未登录或者没有获取到Cookie,不允许调用此方法");
            return loginInfo;
        }
        AppletAccountInfo resp = accountFacade.getAppletAccountInfo(loginId);
        BeanUtils.copyProperties(resp, loginInfo);
        return loginInfo;
    }
}
