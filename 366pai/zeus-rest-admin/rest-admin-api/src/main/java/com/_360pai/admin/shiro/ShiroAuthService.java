package com._360pai.admin.shiro;

import com._360pai.arch.common.constant.SystemConstant;
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
    private SystemProperties systemProperties;

    protected String cacheKey(String type,String id){
        return type+"_"+id;
    }

    public boolean isLogin(String id,String type, String ticket) {


        try {
            String session = (String) redisCacheManager.hGet(SystemConstant.ADMIN_MOBILE_AUTHEN_SESSION_CACHE_KEY, cacheKey(type,id));

            if (StringUtils.isNoneEmpty(session)) {
                JSONObject o = JSONObject.parseObject(session);

                boolean isLogin = o.getString(SystemConstant.ADMIN_COOKIE_ACCOUNT_TICKET_NAME).equals(ticket);

                if (!isLogin) {
                    LOGGER.warn("访问需要授权的接口，Id:[{}],type:[{}],ticket:[{}],授权未通过返回401错误，正确的ticket是{}", id, type,ticket, o.getString(SystemConstant.ADMIN_COOKIE_ACCOUNT_TICKET_NAME));
                }
                return isLogin;
            } else {
                LOGGER.warn("访问需要授权的接口，Id:[{}],type:[{}],ticket:[{}],授权未通过返回401错误，session is null", id, type,ticket);            }


        } catch (Exception e) {

            return false;
        }
        return false;
    }

    public String getTicket(String id) {
        try {
            String session = (String) redisCacheManager.hGet(SystemConstant.ADMIN_MOBILE_AUTHEN_SESSION_CACHE_KEY, id);
            if (StringUtils.isNoneEmpty(session)) {
                JSONObject o = JSONObject.parseObject(session);
                return o.getString(SystemConstant.ADMIN_COOKIE_ACCOUNT_TICKET_NAME);
            }
        } catch (Exception e) {
            return "";
        }
        return "";
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
        return getFromCookie(request, SystemConstant.ADMIN_COOKIE_ACCOUNT_ID_NAME);
    }

    public String getTicketFromCookie(HttpServletRequest request) {
        return getFromCookie(request, SystemConstant.ADMIN_COOKIE_ACCOUNT_TICKET_NAME);
    }

    public String getTypeFromCookie(HttpServletRequest request) {
        return getFromCookie(request, SystemConstant.ADMIN_COOKIE_ACCOUNT_TYPE);
    }


    public void removeTicket(Integer id,String type){
        redisCacheManager.hDel(SystemConstant.ADMIN_MOBILE_AUTHEN_SESSION_CACHE_KEY,cacheKey(type,String.valueOf(id)));
    }

    public void saveTicket(HttpServletResponse response, Integer id,String type, Boolean isAdmin) {
        String ticket;
        if (systemProperties.getSso()) {
            ticket = getAndSaveTicket(id, type, isAdmin);
        } else {
            String cacheStr = (String) redisCacheManager.hGet(SystemConstant.ADMIN_MOBILE_AUTHEN_SESSION_CACHE_KEY, cacheKey(type,String.valueOf(id)));
            if (StringUtils.isNotEmpty(cacheStr)) {
                JSONObject cacheObj = (JSONObject) JSONObject.parse(cacheStr);
                ticket = cacheObj.getString(SystemConstant.ADMIN_COOKIE_ACCOUNT_TICKET_NAME);
            } else {
                ticket = getAndSaveTicket(id, type, isAdmin);
            }
        }
        // 设置customId cookie
        Cookie idCk = new Cookie(SystemConstant.ADMIN_COOKIE_ACCOUNT_ID_NAME, id.toString());
        idCk.setPath("/");
        response.addCookie(idCk);

        Cookie typeCk = new Cookie(SystemConstant.ADMIN_COOKIE_ACCOUNT_TYPE, type);
        typeCk.setPath("/");
        response.addCookie(typeCk);

        // 设置ticket cookie
        Cookie ticketCk = new Cookie(SystemConstant.ADMIN_COOKIE_ACCOUNT_TICKET_NAME, ticket);
        ticketCk.setPath("/");
        response.addCookie(ticketCk);
    }

    private String getAndSaveTicket(Integer id,String type, Boolean isAdmin) {
        // 获取并保存 ticket
        String ticket = createTicket();

        JSONObject o = new JSONObject();
        o.put(SystemConstant.ADMIN_COOKIE_ACCOUNT_ID_NAME, id);
        o.put(SystemConstant.ADMIN_COOKIE_ACCOUNT_TYPE, type);
        o.put(SystemConstant.ADMIN_COOKIE_ACCOUNT_TICKET_NAME, ticket);
        o.put("isAdmin", isAdmin);

        // 登录状态移动端永久有效，缓存不设置失效时间
        redisCacheManager.hSet(SystemConstant.ADMIN_MOBILE_AUTHEN_SESSION_CACHE_KEY, cacheKey(type,String.valueOf(id)), o.toJSONString());
        return ticket;
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
