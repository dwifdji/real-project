package com.winback.arch.common.utils;

import com.alibaba.fastjson.JSON;
import com.winback.arch.common.ComEmailSendReq;
import com.winback.arch.common.constant.SystemConstant;
import com.winback.arch.common.enums.EmailEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 描述：通用邮件发送类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/3/7 9:26
 */
@Slf4j
@Component
public class ComEmailUtil {

    @Autowired
    private AmqpTemplate rabbitTemplate;


    private static ComEmailUtil utils;

    /**
     * 发送Mq邮件
     *
     * @return
     */
    public static void sendEmail(ComEmailSendReq req) {

        try {

            String queue = EmailEnum.BUS_TYPE.BUSINESS.equals(req.getBusType())?SystemConstant.RABBITMQ_SEND_EMAIL_QUEUE:SystemConstant.RABBITMQ_SEND_EXCEPTION_EMAIL_QUEUE;

            String message = JSON.toJSONString(req);

            log.info("queue={}, message={}, enqueue", queue, message);

            utils.rabbitTemplate.convertAndSend(queue, message);
        } catch (Exception e) {
             log.error("发送邮件异常，邮件信息为：{}，异常信息为：{}", JSON.toJSONString(req), e);
         }

     }


    @PostConstruct
    public void init() {
        utils = this;
        utils.rabbitTemplate = this.rabbitTemplate;
    }


}
