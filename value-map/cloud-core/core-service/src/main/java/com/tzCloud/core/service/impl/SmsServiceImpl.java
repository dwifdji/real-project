package com.tzCloud.core.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.tzCloud.arch.common.constant.SystemConstant;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.arch.common.utils.RandomNumberGenerator;
import com.tzCloud.arch.core.redis.RedisCachemanager;
import com.tzCloud.core.exception.BusinessException;
import com.tzCloud.core.service.SmsService;
import com.tzCloud.gateway.common.alisms.AliSmsTemplateEnums;
import com.tzCloud.gateway.controller.req.alisms.FAliSmsSendReq;
import com.tzCloud.gateway.facade.AliSmsFacade;
import com.tzCloud.gateway.resp.AliSendSmsResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: SmsServiceImpl
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/4/24 13:10
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {
    @Resource
    private RedisCachemanager redisCachemanager;
    @Reference(version = "1.0.0")
    private AliSmsFacade aliSmsFacade;

    protected String cacheSendSmskey(String pre, String mobile, String type) {
        return pre + mobile + "_" + type;
    }


    private void sendSmsCode(String mobile, String templateCode, Map<String,String> smsParamMap) {
        FAliSmsSendReq sendReq = new FAliSmsSendReq();
        sendReq.setPhoneNumber(mobile);
        sendReq.setTemplateCode(templateCode);
        sendReq.setTemplateParam(JSON.toJSONString(smsParamMap));
        log.info("开始调用 aliSmsFacade sendSms，请求参数:{}",JSON.toJSONString(sendReq));
        AliSendSmsResp resp = aliSmsFacade.sendSms(sendReq);
        log.info("结束调用 aliSmsFacade sendSms，请求参数:{}，返回结果:{}",JSON.toJSONString(sendReq),JSON.toJSONString(resp));
        if(resp == null ||  !ApiCallResult.SUCCESS.getCode().equals(resp.getCode())){
            throw new BusinessException(ApiCallResult.SEND_SMS_CODE_FAIL);
        }
    }

    @Override
    public boolean checkSmsCode(String mobile, String smsType, String smsCode) {
        Object code = redisCachemanager.get(cacheSendSmskey(SystemConstant.CACHE_KEY_PREFIX_PHONE_VERIFY_CODE, mobile, smsType));
        String redisSmsCode = String.valueOf(code);
        if (smsCode.equals(redisSmsCode)) {
            redisCachemanager.del(cacheSendSmskey(SystemConstant.CACHE_KEY_PREFIX_PHONE_VERIFY_CODE, mobile, smsType));
            return true;
        }
        return false;
    }

    @Override
    public boolean canSendSmsCode(String mobile, String smsType) {
        //是否超过发送验证码的间隔时间
        if(redisCachemanager.get(cacheSendSmskey(SystemConstant.CACHE_KEY_PREFIX_SEND_VERIFY_INTERVAL_CODE,mobile,smsType)) != null){
            return true;
        }
        return false;
    }

    @Override
    public String getSmsCode(String mobile, String smsType) {
        String smsCode = RandomNumberGenerator.numberGenerator(6);
        //保存验证码至缓存300秒失效
        redisCachemanager.set(cacheSendSmskey(SystemConstant.CACHE_KEY_PREFIX_PHONE_VERIFY_CODE,mobile,smsType), smsCode,SystemConstant.CACHE_TIMEOUT_PHONE_VERIFY_CODE);
        //保存验证码间隔至缓存 60秒 失效
        redisCachemanager.set(cacheSendSmskey(SystemConstant.CACHE_KEY_PREFIX_SEND_VERIFY_INTERVAL_CODE,mobile,smsType),SystemConstant.CACHE_TIMEOUT_SEND_VERIFY_INTERVAL_CODE,SystemConstant.CACHE_TIMEOUT_SEND_VERIFY_INTERVAL_CODE);
        return smsCode;
    }

    @Override
    public boolean sendSmsCode(String mobile, String smsCode) {
        Map<String,String> smsParamMap = new HashMap<String,String>();
        smsParamMap.put("code", smsCode);
        smsParamMap.put("duration", "5");
        sendSmsCode(mobile, AliSmsTemplateEnums.SMS_SEND_CODE.getCode(), smsParamMap);
        return true;
    }

    @Override
    public boolean sendForgetPasswordSmsCode(String mobile, String smsCode) {
        // todo
        return false;
    }

    @Override
    public boolean sendDefaultPasswordSms(String mobile, String defaultPassword) {
        // todo
        return false;
    }
}
