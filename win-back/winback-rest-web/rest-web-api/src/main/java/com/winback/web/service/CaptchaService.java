package com.winback.web.service;

/**
 * @author xdrodger
 * @Title: CaptchaService
 * @ProjectName winback
 * @Description:
 * @date 2019/1/24 19:46
 */
public interface CaptchaService {
    boolean checkCaptcha(String sessionId, String clientCaptcha);
}
