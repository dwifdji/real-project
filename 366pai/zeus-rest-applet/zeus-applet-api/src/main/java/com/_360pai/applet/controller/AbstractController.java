package com._360pai.applet.controller;

import com._360pai.applet.controller.account.resp.AccountBaseInfo;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.OSUtil;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.account.vo.AccountExtBindIVo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;


/**
 * Created by RuQ on 2018/8/19 18:16
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

    protected final String loadCurLoginOpenId() {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        return accountBaseInfo.getOpenId();
    }

    /**
     * 获取当前登录的User 找不到user会抛出用户不存在的JJJR2BusinessException
     *
     * @return loadCurLoginUser
     */
    protected final AccountBaseInfo loadCurLoginAccountInfo() {
        Cookie[] cookies = curRequest.getCookies();
        AccountBaseInfo accountBaseInfo = new AccountBaseInfo();
        int cookieId = -1;
        String type = "";
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (SystemConstant.APPLET_COOKIE_ACCOUNT_ID_NAME.equals(cookie.getName())) {
                    cookieId = Integer.parseInt(cookie.getValue());
                }
            }
            if (cookieId == -1) {
                return accountBaseInfo;
            }
            Integer accountId = cookieId;
            //仅是  account 用户
            AccountExtBindIVo resp = accountFacade.getAppletExtBind(cookieId);
            BeanUtils.copyProperties(resp, accountBaseInfo);
            accountBaseInfo.setExtBindId(resp.getId());
            accountBaseInfo.setAvatarUrl(resp.getHeadImgUrl());
            accountBaseInfo.setOpenId(resp.getExtUserId());
            accountBaseInfo.setShopId(resp.getShopId());
            if (resp.getCurrentPartyId() == null) {
                accountBaseInfo.setType(SystemConstant.ACCOUNT_COMMON_TYPE);
                accountBaseInfo.setIsFddBind(false);
                accountBaseInfo.setIsPayBind(false);
                accountBaseInfo.setIsAccountAuth(false);
            } else {
                AccountBaseDto dto = accountFacade.getAccoutBaseByPartyId(resp.getCurrentPartyId());
                BeanUtils.copyProperties(dto, accountBaseInfo);
                accountBaseInfo.setAccountId(resp.getAccountId());
                accountBaseInfo.setAccountAuthName(dto.getName());
                accountBaseInfo.setIsPayBind(dto.isPayBind());
                if (StringUtils.isNotEmpty(dto.getFadadaId())) {
                    accountBaseInfo.setIsFddBind(true);
                }
                accountBaseInfo.setIsAccountAuth(true);
            }
            return accountBaseInfo;
        }

        LOGGER.warn("未登录或者没有获取到Cookie,不允许调用此方法");
        return new AccountBaseInfo();
    }

    protected String cacheSendSmskey(String pre, String mobile, String type) {
        return pre + mobile + "_" + type;
    }

    /**
     * 校验验证码
     */
    protected boolean checkSmsCode(String mobile, String smsType, String smsCode) {
        Object code = redisCachemanager.get(cacheSendSmskey(SystemConstant.CACHE_KEY_PREFIX_PHONE_VERIFY_CODE, mobile, smsType));
        String redisSmsCode = String.valueOf(code);
        return smsCode.equals(redisSmsCode);
    }

    /**
     * 清除验证码
     */
    protected void removeSmsCode(String mobile, String smsType) {
        redisCachemanager.del(cacheSendSmskey(SystemConstant.CACHE_KEY_PREFIX_PHONE_VERIFY_CODE, mobile, ""));
    }

    protected void isAuth() {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        boolean accountAuth = accountBaseInfo.getIsAccountAuth();
        if (!accountAuth) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "请前往个人中心认证");
        }
    }

}
