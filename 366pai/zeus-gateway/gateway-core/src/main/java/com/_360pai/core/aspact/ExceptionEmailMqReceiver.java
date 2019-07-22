package com._360pai.core.aspact;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.core.service.EmailService;
import com._360pai.gateway.controller.req.email.EmailSendReq;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述：异常信息邮件发送
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/11/06 12:26
 */
@Component
@Slf4j
 public class ExceptionEmailMqReceiver {

    @Autowired
    private EmailService emailService;


    @RabbitListener(queues = SystemConstant.RABBITMQ_SEND_EXCEPTION_EMAIL_QUEUE)
    public void process(String message) {
        log.info("queue={}, message={}, dequeue",SystemConstant.RABBITMQ_SEND_EXCEPTION_EMAIL_QUEUE, message);
        emailService.sendEmail(JSON.parseObject(message, EmailSendReq.class));

     }




}
