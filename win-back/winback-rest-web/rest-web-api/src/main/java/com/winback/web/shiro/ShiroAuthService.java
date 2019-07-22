package com.winback.web.shiro;

import com.alibaba.fastjson.JSONObject;
import com.winback.arch.common.constant.RedisKeyConstant;
import com.winback.arch.common.constant.SystemConstant;
import com.winback.arch.common.utils.RequestUtils;
import com.winback.arch.core.redis.RedisCachemanager;
import com.winback.arch.core.sysconfig.properties.SystemProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Slf4j
@Service
public class ShiroAuthService {

    /**
     * redis缓存管理器
     */
    @Resource
    private RedisCachemanager redisCacheManager;
    @Autowired
    private SystemProperties systemProperties;

    private String sessionKey = SystemConstant.APP_SESSION_KEY;
    private String cookieIdName = SystemConstant.APP_COOKIE_ID_NAME;
    private String cookieTicketName = SystemConstant.APP_COOKIE_TICKET_NAME;


    protected String cacheKey(String id) {
        return id;
    }

    public boolean isLogin(String id, String ticket) {
        try {
            String session = (String) redisCacheManager.hGet(sessionKey, cacheKey(id));

            if (StringUtils.isNoneEmpty(session)) {
                JSONObject o = JSONObject.parseObject(session);
                boolean isLogin = o.getString(cookieTicketName).equals(ticket);
                if (!isLogin) {
                    log.warn("访问需要授权的接口，Id:[{}],ticket:[{}],授权未通过返回401错误，正确的ticket是{}", id, ticket, o.getString(cookieTicketName));
                }
                return isLogin;
            } else {
                log.warn("访问需要授权的接口，Id:[{}],ticket:[{}],授权未通过返回401错误，session is null", id, ticket);
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
        return getFromCookie(request, cookieIdName);
    }

    public String getTicketFromCookie(HttpServletRequest request) {
        return getFromCookie(request, cookieTicketName);
    }


    public void removeTicket(Integer id) {
        redisCacheManager.hDel(sessionKey, cacheKey(String.valueOf(id)));

        redisCacheManager.hDel(RedisKeyConstant.APP_DEVICE, cacheKey(String.valueOf(id)));

    }

    public String saveTicket(HttpServletRequest request, HttpServletResponse response, Integer id) {
        String ticket;
        if (systemProperties.getSso()) {
            ticket = getAndSaveTicket(id);
        } else {
            String cacheStr = (String) redisCacheManager.hGet(sessionKey, cacheKey(String.valueOf(id)));
            if (StringUtils.isNotEmpty(cacheStr)) {
                JSONObject cacheObj = (JSONObject) JSONObject.parse(cacheStr);
                ticket = cacheObj.getString(cookieTicketName);
            } else {
                ticket = getAndSaveTicket(id);
            }
        }

        // 设置customId cookie
        Cookie idCk = new Cookie(cookieIdName, id.toString());
        idCk.setPath("/");

        // 设置ticket cookie
        Cookie ticketCk = new Cookie(cookieTicketName, ticket);
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

    private String getAndSaveTicket(Integer id) {
        // 获取并保存 ticket
        String ticket = createTicket();
        JSONObject o = new JSONObject();
        o.put(cookieIdName, id);
        o.put(cookieTicketName, ticket);
        // 登录状态移动端永久有效，缓存不设置失效时间
        redisCacheManager.hSet(sessionKey, cacheKey(String.valueOf(id)), o.toJSONString());
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
