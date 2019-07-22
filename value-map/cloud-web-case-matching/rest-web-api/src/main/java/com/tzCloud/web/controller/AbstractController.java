package com.tzCloud.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.tzCloud.arch.common.utils.OSUtil;
import com.tzCloud.arch.common.utils.ThreadLocalContextUtil;
import com.tzCloud.arch.core.redis.RedisCachemanager;
import com.tzCloud.arch.common.constant.SystemConstant;
import com.tzCloud.core.facade.account.AccountFacade;
import com.tzCloud.core.facade.account.vo.AccountInfo;
import com.tzCloud.core.facade.account.vo.MembershipCardVO;
import com.tzCloud.web.vo.LoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

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
        return (Integer) ThreadLocalContextUtil.getValue(SystemConstant.PLATFORM_COOKIE_ID_NAME);

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
        AccountInfo resp = accountFacade.getAccountInfo(loginId);
        BeanUtils.copyProperties(resp, loginInfo);
        return loginInfo;
    }

    protected final List<MembershipCardVO> loadAvailableMemberCard() {
        Integer loginId = loadCurLoginId();
        if (loginId == null || loginId.intValue() == -1) {
            log.warn("未登录或者没有获取到Cookie,不允许调用此方法");
            return new LinkedList<>();
        }
        return accountFacade.findAvailableCard(loginId);
    }
}
