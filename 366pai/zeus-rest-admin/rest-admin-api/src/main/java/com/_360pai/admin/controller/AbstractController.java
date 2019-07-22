package com._360pai.admin.controller;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.utils.OSUtil;
import com._360pai.arch.common.utils.ThreadLocalContextUtil;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.facade.account.AccountFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/7 16:05
 */
@Controller
public class AbstractController {

    @Resource
    private RedisCachemanager redisCachemanager;


    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);


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
     * 描述 目前返回固定值
     *
     * @author : whisky_vip
     * @date : 2018/9/7 16:29
     */
    protected final Integer loadCurLoginId() {
        return (Integer) ThreadLocalContextUtil.getValue(SystemConstant.ADMIN_COOKIE_ACCOUNT_ID_NAME);

    }
}
