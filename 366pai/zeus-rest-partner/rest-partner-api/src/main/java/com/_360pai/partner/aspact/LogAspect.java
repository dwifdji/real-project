package com._360pai.partner.aspact;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.web.log.AbstractLogAspect;
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
    @Override
    protected String getApplicationName() {
        return SystemConstant.APPLICATION_NAME_PARTNER;
    }

    @Override
    public AccountInfo getAccountInfo(HttpServletRequest request) {

        int      cookieId = -1;
        String   type     = "";
        Cookie[] cookies  = request.getCookies();
        try {
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (SystemConstant.AGENCY_COOKIE_ACCOUNT_ID_NAME.equals(cookie.getName())) {
                        cookieId = Integer.parseInt(cookie.getValue());
                    }
                    if (SystemConstant.AGENCY_COOKIE_ACCOUNT_TYPE.equals(cookie.getName())) {
                        type = cookie.getValue();
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return new AccountInfo(cookieId, type);
    }
}