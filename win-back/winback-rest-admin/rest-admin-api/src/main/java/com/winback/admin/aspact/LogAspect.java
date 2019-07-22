package com.winback.admin.aspact;

import com.winback.arch.common.constant.SystemConstant;
import com.winback.arch.common.utils.ThreadLocalContextUtil;
import com.winback.arch.web.log.AbstractLogAspect;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 描述:日志记录AOP实现
 *
 * @author : whisky_vip
 * @date : 2018/8/23 13:18
 */
@Aspect
@Component
public class LogAspect extends AbstractLogAspect {

    private String cookieIdName = SystemConstant.ADMIN_COOKIE_ID_NAME;

    @Override
    protected String getApplicationName() {
        return SystemConstant.APPLICATION_NAME_ADMIN;
    }

    @Override
    public AccountInfo getAccountInfo(HttpServletRequest request) {
        Cookie[] cookies  = request.getCookies();
        Integer      cookieId = null;
        String   type     = "";
        try {
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (cookieIdName.equals(cookie.getName())) {
                        cookieId = Integer.parseInt(cookie.getValue());
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        ThreadLocalContextUtil.setValue(cookieIdName, cookieId);
        return new AccountInfo(cookieId, type);
    }

}