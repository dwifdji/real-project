package com.tzCloud.gateway.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.gateway.service.sms.SmsService;
import com.tzCloud.gateway.controller.req.alisms.FAliSmsSendReq;
import com.tzCloud.gateway.facade.AliSmsFacade;
import com.tzCloud.gateway.resp.AliSendSmsResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 15:44
 */
@Component
@Service(version = "1.0.0")
public class AliSmsProvider implements AliSmsFacade {


    @Autowired
    private SmsService smsService;
    @Override
    public AliSendSmsResp sendSms(FAliSmsSendReq freq) {

        AliSendSmsResp resp = new AliSendSmsResp();

        FAliSmsSendReq req = new FAliSmsSendReq();
        req.setPhoneNumber(freq.getPhoneNumber());
        req.setTemplateCode(freq.getTemplateCode());
        req.setTemplateParam(freq.getTemplateParam());
        req.setPartyId(freq.getPartyId());
        ResponseModel responseModel =smsService.sendSms(req);


        resp.setCode(responseModel.getCode());
        resp.setDesc(responseModel.getDesc());
        return resp;
    }

}
