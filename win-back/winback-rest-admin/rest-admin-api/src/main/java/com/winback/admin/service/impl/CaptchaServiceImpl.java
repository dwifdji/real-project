package com.winback.admin.service.impl;

import com.winback.admin.service.CaptchaService;
import com.winback.arch.common.constant.SystemConstant;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.core.redis.RedisCachemanager;
import com.winback.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xdrodger
 * @Title: CaptchaServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/1/24 19:46
 */
@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Resource
    private RedisCachemanager redisCachemanager;

    private String sessionKey = SystemConstant.ADMIN_KAPTCHA_SESSION_KEY;

    @Override
    public boolean checkCaptcha(String sessionId, String clientCaptcha) {
        log.info("sessionId={}, clientCaptcha={}", sessionId,  clientCaptcha);
        if (StringUtils.isEmpty(sessionId)) {
            throw new BusinessException(ApiCallResult.KAPTCHA_ERRPR);
        }
        if (StringUtils.isEmpty(clientCaptcha)) {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        // 判断验证码是否正确
        String serverCaptcha = (String) redisCachemanager.get(sessionKey + sessionId);
        if (!clientCaptcha.equals(serverCaptcha)) {
            return false;
        }
        redisCachemanager.del(sessionKey + sessionId);
        return true;
    }
}
