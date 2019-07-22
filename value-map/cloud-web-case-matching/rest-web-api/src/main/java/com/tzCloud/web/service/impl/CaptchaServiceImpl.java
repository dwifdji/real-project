package com.tzCloud.web.service.impl;

import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.arch.core.redis.RedisCachemanager;
import com.tzCloud.arch.common.constant.SystemConstant;
import com.tzCloud.core.exception.BusinessException;
import com.tzCloud.web.service.CaptchaService;
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

    private String sessionKey = SystemConstant.PLATFORM_KAPTCHA_SESSION_KEY;

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
