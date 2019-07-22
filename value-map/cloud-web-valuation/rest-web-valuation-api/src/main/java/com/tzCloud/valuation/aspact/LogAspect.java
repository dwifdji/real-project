package com.tzCloud.valuation.aspact;

import com.tzCloud.arch.common.constant.SystemConstant;
import com.tzCloud.arch.common.utils.ThreadLocalContextUtil;
import com.tzCloud.arch.web.log.AbstractLogAspect;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class LogAspect extends AbstractLogAspect {
    @Override
    protected String getApplicationName() {
        return SystemConstant.APPLICATION_NAME_PLATFORM;
    }

    @Override
    public AccountInfo getAccountInfo(HttpServletRequest request) {
        Cookie[] cookies  = request.getCookies();
        int      cookieId = -1;
        String   type     = "";
        try {
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (SystemConstant.PLATFORM_COOKIE_ID_NAME.equals(cookie.getName())) {
                        cookieId = Integer.parseInt(cookie.getValue());
                    }
                }

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        ThreadLocalContextUtil.setValue(SystemConstant.PLATFORM_COOKIE_ID_NAME, cookieId);
        return new AccountInfo(cookieId, type);
    }
}