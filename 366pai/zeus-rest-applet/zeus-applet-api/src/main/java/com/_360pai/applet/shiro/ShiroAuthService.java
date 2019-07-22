package com._360pai.applet.shiro;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.utils.RequestUtils;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Service
public class ShiroAuthService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroAuthService.class);

    /**
     * redis缓存管理器
     */
    @Resource
    private RedisCachemanager redisCacheManager;
    @Autowired
    private SystemProperties  systemProperties;


    protected String cacheKey(String id) {
        return id;
    }

    public boolean isLogin(String id, String ticket) {


        try {
            String session = (String) redisCacheManager.hGet(SystemConstant.APPLET_MOBILE_AUTHEN_SESSION_CACHE_KEY, cacheKey(id));

            if (StringUtils.isNoneEmpty(session)) {
                JSONObject o = JSONObject.parseObject(session);
                boolean isLogin = o.getString(SystemConstant.APPLET_COOKIE_ACCOUNT_TICKET_NAME).equals(ticket);
                if (!isLogin) {
                    LOGGER.warn("访问需要授权的接口，Id:[{}],type:[{}],ticket:[{}],授权未通过返回401错误，正确的ticket是{}", id, ticket, o.getString(SystemConstant.AGENCY_COOKIE_ACCOUNT_TICKET_NAME));
                }
                return isLogin;
            } else {
                LOGGER.warn("访问需要授权的接口，Id:[{}],type:[{}],ticket:[{}],授权未通过返回401错误，session is null", id, ticket);
            }


        } catch (Exception e) {

            return false;
        }
        return false;
    }

    public String getFromCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return "-1";
    }

    public String getIdFromCookie(HttpServletRequest request) {
        return getFromCookie(request, SystemConstant.APPLET_COOKIE_ACCOUNT_ID_NAME);
    }

    public String getTicketFromCookie(HttpServletRequest request) {
        return getFromCookie(request, SystemConstant.APPLET_COOKIE_ACCOUNT_TICKET_NAME);
    }

    public String getTypeFromCookie(HttpServletRequest request) {
        return getFromCookie(request, SystemConstant.APPLET_COOKIE_ACCOUNT_TYPE);
    }


    public void removeTicket(Integer id) {
        redisCacheManager.hDel(SystemConstant.APPLET_MOBILE_AUTHEN_SESSION_CACHE_KEY, cacheKey(String.valueOf(id)));
    }

    public String saveTicket(HttpServletRequest request, HttpServletResponse response, Integer id, Integer agencyId) {
        String ticket;
        if (systemProperties.getSso()) {
            ticket = getAndSaveTicket(id, agencyId);
        } else {
            String cacheStr = (String) redisCacheManager.hGet(SystemConstant.APPLET_MOBILE_AUTHEN_SESSION_CACHE_KEY, cacheKey(String.valueOf(id)));
            if (StringUtils.isNotEmpty(cacheStr)) {
                JSONObject cacheObj = (JSONObject) JSONObject.parse(cacheStr);
                ticket = cacheObj.getString(SystemConstant.APPLET_COOKIE_ACCOUNT_TICKET_NAME);
            } else {
                ticket = getAndSaveTicket(id, agencyId);
            }
        }

        // 设置customId cookie
        Cookie idCk = new Cookie(SystemConstant.APPLET_COOKIE_ACCOUNT_ID_NAME, id.toString());
        idCk.setPath("/");

        // 设置ticket cookie
        Cookie ticketCk = new Cookie(SystemConstant.APPLET_COOKIE_ACCOUNT_TICKET_NAME, ticket);
        ticketCk.setPath("/");

        StringBuffer url            = request.getRequestURL();
        String       tempContextUrl = RequestUtils.getDomain(url.toString());
        if (StringUtils.isNotBlank(tempContextUrl)) {
            idCk.setDomain(tempContextUrl);
            ticketCk.setDomain(tempContextUrl);
        }
        response.addCookie(idCk);
        response.addCookie(ticketCk);
        return ticket;
    }

    private String getAndSaveTicket(Integer id, Integer agencyId) {
        // 获取并保存 ticket
        String ticket = createTicket();
        JSONObject o = new JSONObject();
        o.put(SystemConstant.APPLET_COOKIE_ACCOUNT_ID_NAME, id);
        o.put(SystemConstant.APPLET_COOKIE_ACCOUNT_TICKET_NAME, ticket);
        o.put("agencyId", agencyId);
        // 登录状态移动端永久有效，缓存不设置失效时间
        redisCacheManager.hSet(SystemConstant.APPLET_MOBILE_AUTHEN_SESSION_CACHE_KEY, cacheKey(String.valueOf(id)), o.toJSONString());

        savePlatformTicket(id, agencyId, ticket);
        return ticket;
    }

    private void savePlatformTicket(Integer id, Integer agencyId, String ticket) {
        JSONObject o2 = new JSONObject();
        o2.put(SystemConstant.COOKIE_ACCOUNT_ID_NAME, id);
        o2.put(SystemConstant.COOKIE_ACCOUNT_TYPE, SystemConstant.ACCOUNT_COMMON_TYPE);
        o2.put(SystemConstant.COOKIE_ACCOUNT_TICKET_NAME, ticket);
        o2.put("agencyId", agencyId);
        // 登录状态移动端永久有效，缓存不设置失效时间
        redisCacheManager.hSet(SystemConstant.MOBILE_AUTHEN_SESSION_CACHE_KEY, cacheKey(String.valueOf(id)), o2.toJSONString());
    }

    /**
     * 创建ticket
     *
     * @return ticket
     */
    private String createTicket() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
