package com.winback.core.aspact;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.winback.arch.common.constant.MQKeyConstant;
import com.winback.core.service.assistant.EmailService;
import com.winback.core.service.assistant.ExceptionService;
import com.winback.gateway.controller.req.alisms.FAliSmsSendReq;
import com.winback.gateway.facade.EmailFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述：发送短信或者邮件Mq
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/30 18:17
 */
@Component
@Slf4j
public class GatewayMqSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Reference(version = "1.0.0")
    private ExceptionService exceptionService;


    private boolean enqueue(String queue, String message, long delay) {
        log.info("delay_queue={}, message={}, delay={}, enqueue",queue, message, delay);
        try {
            rabbitTemplate.convertAndSend(MQKeyConstant.DELAY_EXCHANGE, queue, message, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setHeader("x-delay", delay * 1000);
                    return message;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.error("delay_queue={}, message={}, enqueue fail", queue, message);
            exceptionService.processEnqueueException(queue, message, e.getMessage());
            return false;
        }
        return true;
    }

    private boolean enqueue(String queue, String message) {
        log.info("queue={}, message={}, enqueue",queue, message);
        try {
            rabbitTemplate.convertAndSend(queue, message);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("queue={}, message={}, enqueue fail", queue, message);
            exceptionService.processEnqueueException(queue, message, e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 发送短信
     */
    public void sendSms(FAliSmsSendReq req) {
        enqueue(MQKeyConstant.SEND_SMS_QUEUE, JSON.toJSONString(req));
    }

    /**
     * 合同订单支付超时
     */
    public boolean contractOrderPayTimeOut(String message, long delay) {
        return enqueue(MQKeyConstant.CONTRACT_ORDER_PAY_TIME_OUT_QUEUE, message, delay);
    }

    /**
     * 合同订单即将支付超时
     */
    public boolean contractOrderBeAboutToPayTimeOut(String message, long delay) {
        return enqueue(MQKeyConstant.CONTRACT_ORDER_BE_ABOUT_TO_PAY_TIME_OUT_QUEUE, message, delay);
    }

    /**
     * 案件风控审核通过
     */
    public boolean caseRiskCheckSuccess(String message) {
        return enqueue(MQKeyConstant.CASE_RISK_CHECK_SUCCESS_QUEUE, message);
    }

    /**
     * 案件完成
     */
    public boolean caseSuccess(String message) {
        return enqueue(MQKeyConstant.CASE_SUCCESS_QUEUE, message);
    }
}