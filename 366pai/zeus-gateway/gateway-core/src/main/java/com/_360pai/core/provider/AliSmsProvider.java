package com._360pai.core.provider;

import com._360pai.core.model.sms.AliSmsSendReq;
import com._360pai.core.service.AliSmsService;
import com._360pai.gateway.facade.AliSmsFacade;
import com._360pai.gateway.controller.req.alisms.FAliSmsSendReq;
import com._360pai.gateway.resp.AliSendSmsResp;
import com.alibaba.dubbo.config.annotation.Service;
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
    private AliSmsService aliSmsService;
    @Override
    public AliSendSmsResp sendSms(FAliSmsSendReq freq) {

        AliSmsSendReq req = new AliSmsSendReq();
        req.setPhoneNumber(freq.getPhoneNumber());
        req.setTemplateCode(freq.getTemplateCode());
        req.setTemplateParam(freq.getTemplateParam());
        req.setPartyId(freq.getPartyId());
        AliSendSmsResp resp =aliSmsService.sendSms(req);

        return resp;
    }

}
