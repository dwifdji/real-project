package com.tzCloud.valuation.controller;



import com.alibaba.dubbo.config.annotation.Reference;
import com.tzCloud.arch.common.constant.SystemConstant;
import com.tzCloud.arch.common.utils.OSUtil;
import com.tzCloud.arch.common.utils.ThreadLocalContextUtil;
import com.tzCloud.arch.core.redis.RedisCachemanager;
import com.tzCloud.core.facade.account.AccountFacade;
import com.tzCloud.core.facade.account.vo.AccountInfo;
import com.tzCloud.core.facade.account.vo.MembershipCardVO;
import com.tzCloud.valuation.vo.LoginInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by RuQ on 2018/8/19 18:16
 */
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
     * 日志
     */

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);



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
            LOGGER.warn("未登录或者没有获取到Cookie,不允许调用此方法");
            return loginInfo;
        }
        AccountInfo resp = accountFacade.getAccountInfo(loginId);
        BeanUtils.copyProperties(resp, loginInfo);
        return loginInfo;
    }

    protected final List<MembershipCardVO> loadAvailableMemberCard() {
        Integer loginId = loadCurLoginId();
        if (loginId == null || loginId.intValue() == -1) {
            LOGGER.warn("未登录或者没有获取到Cookie,不允许调用此方法");
            return new LinkedList<>();
        }
        return accountFacade.findAvailableCard(loginId);
    }



}
