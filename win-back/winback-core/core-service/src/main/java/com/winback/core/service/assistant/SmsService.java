package com.winback.core.service.assistant;

import java.util.Map;

/**
 * @author xdrodger
 * @Title: SmsService
 * @ProjectName winback
 * @Description:
 * @date 2019/1/24 19:11
 */
public interface SmsService {

    /**
     * 校验验证码
     */
    boolean checkSmsCode(String mobile, String smsType, String smsCode);

    /**
     * 是否可以发送验证码
     */
    boolean canSendSmsCode(String mobile, String smsType);

    /**
     * 获取验证码
     */
    String getSmsCode(String mobile, String smsType);

    /**
     * 发送验证码
     */
    boolean sendSmsCode(String mobile, String smsCode);

    /**
     * 发送注册验证码
     */
    boolean sendLoginSmsCode(String mobile, String smsCode);

    /**
     * 发送注册验证码
     */
    boolean sendRegisterSmsCode(String mobile, String smsCode);

    /**
     * 发送忘记密码验证码
     */
    boolean sendForgetPasswordSmsCode(String mobile, String smsCode);

    /**
     * 发送默认密码短信
     */
    boolean sendDefaultPasswordSms(String mobile, String defaultPassword);

    /**
     * 发送律师申请短信-后台人员
     */
    boolean sendLawyerApplySms(String mobile);
}
