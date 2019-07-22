package com._360pai.core.aspact;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.core.model.sms.AliSmsSendReq;
import com._360pai.core.service.AliSmsService;
import com._360pai.gateway.controller.req.alisms.FAliSmsSendReq;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/30 18:26
 */
@Component
@Slf4j
public class AliSmsMqReceiver {

    @Autowired
    private  AliSmsService aliSmsService;


    @RabbitListener(queues = SystemConstant.RABBITMQ_SEND_SMS_QUEUE)
    public void process(String message) {
        log.info("queue={}, message={}, dequeue",SystemConstant.RABBITMQ_SEND_SMS_QUEUE, message);
        FAliSmsSendReq sendReq = JSON.parseObject(message, FAliSmsSendReq.class);
        AliSmsSendReq req = new AliSmsSendReq();
        req.setPartyId(sendReq.getPartyId());
        req.setTemplateParam(sendReq.getTemplateParam());
        req.setTemplateCode(sendReq.getTemplateCode());
        req.setPhoneNumber(sendReq.getPhoneNumber());
        aliSmsService.sendSms(req);
    }




}
