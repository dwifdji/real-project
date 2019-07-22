package com._360pai.partner.controller;

import java.net.InetAddress;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.account.resp.AccountResp;
import com._360pai.partner.controller.account.resp.AccountBaseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.utils.OSUtil;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.resp.AgencyResp;
import com._360pai.partner.controller.account.resp.AgencyBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;


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

	protected final AccountBaseInfo loadCurLoginAccountInfo() {
		Cookie[] cookies = curRequest.getCookies();
		AccountBaseInfo accountBaseInfo = new AccountBaseInfo();
		int cookieId = -1;
		String type = "";
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (SystemConstant.AGENCY_COOKIE_ACCOUNT_ID_NAME.equals(cookie.getName())) {
					cookieId = Integer.parseInt(cookie.getValue());
				}
				if (SystemConstant.AGENCY_COOKIE_ACCOUNT_TYPE.equals(cookie.getName())) {
					type = cookie.getValue();
				}
			}
			if(cookieId == -1){
				return accountBaseInfo;
			}
			AccountResp resp = accountFacade.getAccount((long)cookieId);
			accountBaseInfo.setAccountId(resp.getId());
			accountBaseInfo.setDefaultAgencyId(resp.getDefaultAgencyId());
			accountBaseInfo.setAgencyId(resp.getAgencyId());
			accountBaseInfo.setMobile(resp.getMobile());
			accountBaseInfo.setType(type);
			accountBaseInfo.setName(resp.getMobile());
			accountBaseInfo.setAccountAuth(false);
			accountBaseInfo.setBank(false);
			accountBaseInfo.setCanCheckReservePrice(resp.getCanCheckReservePrice());
			if (resp.getIsAgencyAdmin() != null && resp.getIsAgencyAdmin().equals(1)) {
				accountBaseInfo.setIsAgencyAdmin(true);
			}
			return accountBaseInfo;
		}

		LOGGER.warn("未登录或者没有获取到Cookie,不允许调用此方法");
		return new AccountBaseInfo();
	}

    /**
     * 获取登陆机构的基本信息
     * @return
     */
    protected AgencyBaseInfo loadCurLoginAgency() {
		AccountBaseInfo accountBaseInfo =loadCurLoginAccountInfo();
		AgencyBaseInfo agencyBaseInfo = new AgencyBaseInfo();
		AgencyResp agencyModel = accountFacade.getAgencyById(accountBaseInfo.getAgencyId());
		agencyBaseInfo.setAddress(agencyModel.getAddress());
		agencyBaseInfo.setAgencyId(agencyModel.getId());
		agencyBaseInfo.setCode(agencyModel.getCode());
		agencyBaseInfo.setMobile(agencyModel.getMobile());
		return agencyBaseInfo;
    }

    
    /**
     * 获取登陆机构的id
     * @return
     */
    protected Integer loadCurLoginAgencyId() {
		AccountBaseInfo accountBaseInfo =loadCurLoginAccountInfo();
        return accountBaseInfo.getAgencyId();
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
}
