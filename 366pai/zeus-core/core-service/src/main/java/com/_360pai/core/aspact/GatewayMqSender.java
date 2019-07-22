package com._360pai.core.aspact;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.common.constants.ExceptionEmailEnum;
import com._360pai.gateway.controller.req.alisms.FAliSmsSendReq;
import com._360pai.gateway.controller.req.email.EmailSendReq;
import com._360pai.gateway.controller.req.email.GatewayEmailRecordReq;
import com._360pai.gateway.facade.EmailFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
    private EmailFacade emailFacade;
    @Autowired
    private ExceptionService exceptionService;

    private boolean enqueue(String queue, String message, long delay) {
        log.info("delay_queue={}, message={}, delay={}, enqueue",queue, message, delay);
        try {
            rabbitTemplate.convertAndSend(SystemConstant.RABBITMQ_DELAY_EXCHANGE, queue, message, new MessagePostProcessor() {
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
        enqueue(SystemConstant.RABBITMQ_SEND_SMS_QUEUE, JSON.toJSONString(req));
    }


    /**
     *
     *Mq 发送邮件接口
     * @param
     */
    public void sendEmail(EmailSendReq req) {
        Integer id = 0;
        try {
            id = saveEmailRecord(req);
            req.setId(id);
            String message = JSON.toJSONString(req);

            log.info("queue={}, message={}, enqueue", SystemConstant.RABBITMQ_SEND_EMAIL_QUEUE, message);
            this.rabbitTemplate.convertAndSend(SystemConstant.RABBITMQ_SEND_EMAIL_QUEUE, message);
        } catch (Exception e) {
            updateRecord(id,e.getMessage());
            log.error("发送邮件异常，邮件信息为：{}，异常信息为：{}", JSON.toJSONString(req), e);
            exceptionService.processEnqueueException(SystemConstant.RABBITMQ_SEND_EMAIL_QUEUE, JSON.toJSONString(req), e.getMessage());
        }
    }

    /**
     *
     *保存发送邮件请求
     */
    private Integer saveEmailRecord(EmailSendReq req) {

        Integer id = 0;
        try {
            GatewayEmailRecordReq recordReq = new GatewayEmailRecordReq();

            recordReq.setContent(req.getContent());
            recordReq.setCopyEmail(JSON.toJSONString(req.getCopyEmail()));
            recordReq.setSendType(req.getSendType());
            recordReq.setEmail(JSON.toJSONString(req.getEmail()));
            recordReq.setTemplateCode(req.getTemplateCode());
            recordReq.setTemplateParam(req.getTemplateParam());
            recordReq.setCreateTime(DateUtil.getSysTime());
            recordReq.setTitle(req.getTitle());

            id = emailFacade.saveEmailRecord(recordReq);


            return id;
        }catch (Exception e){

            log.error("保存邮件信息异常，异常信息为：{}",e);

            updateRecord(id,e.getMessage());

        }

        return id;

    }

    private void updateRecord(Integer id,String msg) {

        try {
            GatewayEmailRecordReq recordReq = new GatewayEmailRecordReq();
            recordReq.setId(id);
            recordReq.setStatus("111");
            recordReq.setMsg(msg);
            recordReq.setUpdateTime(DateUtil.getSysTime());
            emailFacade.updateEmailRecord(recordReq);
        }catch (Exception e){
            log.error("更新邮件异常，异常信息为：{}",e);
        }

    }

    /**
     *
     * 发送异常监控邮件
     * @param
     */
    public void sendExceptionEmail(EmailSendReq req) {
        Integer id=0;
        String message = JSON.toJSONString(req);
        try{
            req.setSendType("10");
            id = saveEmailRecord(req);
            req.setId(id);
            log.info("queue={}, message={}, enqueue", SystemConstant.RABBITMQ_SEND_EXCEPTION_EMAIL_QUEUE, message);
            this.rabbitTemplate.convertAndSend(SystemConstant.RABBITMQ_SEND_EXCEPTION_EMAIL_QUEUE, message);
        } catch (Exception e){
            updateRecord(id,e.getMessage());
            log.error("发送异常监控邮件异常，邮件信息为：{}，异常信息为：{}",JSON.toJSONString(req),e);
            exceptionService.processEnqueueException(SystemConstant.RABBITMQ_SEND_EXCEPTION_EMAIL_QUEUE, message, e.getMessage());
        }

    }

    /**
     *
     * 发送try/catch异常监控邮件
     * @param
     */
    public void sendTryCatchExceptionEmail(Integer id, Exception e) {
        sendTryCatchExceptionEmail("【id=" + id + "】", Throwables.getStackTraceAsString(e));
    }

    /**
     *
     * 发送try/catch异常监控邮件
     * @param
     */
    public void sendTryCatchExceptionEmail(Long id, Exception e) {
        sendTryCatchExceptionEmail("【id=" + id + "】", Throwables.getStackTraceAsString(e));
    }

    /**
     *
     * 发送try/catch异常监控邮件
     * @param
     */
    private void sendTryCatchExceptionEmail(String title, String content) {
        EmailSendReq req = new EmailSendReq();
        List<String> list = new ArrayList<>();
        req.setCopyEmail(list);
        //内容发送
        req.setSendType(EmailSendReq.CONTENT_TYPE);
        StringBuffer sb = new StringBuffer(ExceptionEmailEnum.IMPORTANT_LEVEL.OTHER.getValue());
        sb.append(ExceptionEmailEnum.MODULE_TYPE.OTHER.getValue());
        sb.append(title);
        sb.append(" try/catch异常");
        req.setTitle(sb.toString());
        req.setContent(content);
        req.setEmail(exceptionService.getTryCatchExceptionEmailList());
        sendExceptionEmail(req);
    }

    /**
     * 测试延迟队列入队
     */
    public boolean testDelayEnqueue(String message, long delay) {
        return enqueue(SystemConstant.RABBITMQ_TEST_DELAY_QUEUE, message, delay);
    }

    /**
     * 测试延迟队列入队
     */
    public boolean testEnqueue(String message) {
        return enqueue(SystemConstant.RABBITMQ_TEST_QUEUE, message);
    }

    /**
     * 拍卖活动结束队列入队
     */
    public boolean auctionActivityEndEnqueue(String message, long delay) {
        return enqueue(SystemConstant.RABBITMQ_AUCTION_ACTIVITY_END_QUEUE, message, delay);
    }

    /**
     * 拍卖活动降价队列入队
     */
    public boolean auctionActivityDutchPriceEnqueue(String message, long delay) {
        return enqueue(SystemConstant.RABBITMQ_AUCTION_ACTIVITY_DUTCH_PRICE_QUEUE, message, delay);
    }

    /**
     * 拍卖订单支付超时队列入队
     */
    public boolean auctionOrderPayTimeoutEnqueue(String message, long delay) {
        return enqueue(SystemConstant.RABBITMQ_AUCTION_ORDER_PAY_TIMEOUT_QUEUE, message, delay);
    }

    /**
     * 拍卖活动签署成交协议超时队列入队
     */
    public boolean auctionActivitySignTimeoutEnqueue(String message, long delay) {
        return enqueue(SystemConstant.RABBITMQ_AUCTION_ACTIVITY_SIGN_TIMEOUT_KEY, message, delay);
    }

    /**
     * 预招商活动报名结束队列入队
     */
    public boolean enrollingActivitySignUpEndEnqueue(String message, long delay) {
        return enqueue(SystemConstant.RABBITMQ_ENROLLING_ACTIVITY_SIGN_UP_END_QUEUE, message, delay);
    }

    /**
     * 处置服务商提醒到期时间队列入队
     */
    public boolean disposalDeadlineEnqueue(String message, long delay) {
        return enqueue(SystemConstant.RABBITMQ_DISPOSAL_DEADLINE_QUEUE, message, delay);
    }

    /**
     * 一级合伙人接单到期队列入队
     */
    public boolean providerSurveyDeadlineEnqueue(String message, long delay) {
        return enqueue(SystemConstant.RABBITMQ_PROVIDER_SURVEY_QUEUE, message, delay);
    }

    /**
     * 预招商活动结束支付时间
     */
    public boolean enrollingEndPayEnqueue(String message, long delay) {
        return enqueue(SystemConstant.ENROLLING_END_PAY_TIME_QUEUE, message, delay);
    }

    /**
     * 账户注册入队
     */
    public boolean accountRegisterEnqueue(String message) {
        return enqueue(SystemConstant.RABBITMQ_ACCOUNT_REGISTER_QUEUE, message);
    }

    /**
     * 用户认证申请通过队列入队
     */
    public boolean userApplyApproveEnqueue(String message) {
        return enqueue(SystemConstant.RABBITMQ_USER_APPLY_APPROVE_QUEUE, message);
    }

    /**
     * 企业认证申请通过队列入队
     */
    public boolean companyApplyApproveEnqueue(String message) {
        return enqueue(SystemConstant.RABBITMQ_COMPANY_APPLY_APPROVE_QUEUE, message);
    }

    /**
     * 下线注册完成 （第二次，3天内并未完成认证）队列入队
     */
    public boolean subordinateAccountRegisterSecondEnqueue(String message, long delay) {
        return enqueue(SystemConstant.RABBITMQ_SUBORDINATE_ACCOUNT_REGISTER_SECOND_QUEUE, message);
    }

    /**
     * 下线认证完成（第二次）队列入队
     */
    public boolean subordinateAuthFinishSecondEnqueue(String message, long delay) {
        return enqueue(SystemConstant.RABBITMQ_SUBORDINATE_AUTH_FINISH_SECOND_QUEUE, message);
    }

    /**
     * 处置服务支付时间
     */
    public boolean disposalPayExpiredEnqueue(String message, long delay) {
        return enqueue(SystemConstant.RABBITMQ_DISPOSAL_PAY_EXPIRED, message, delay);
    }

    /**
     * 配资服务支付时间
     */
    public boolean withfudigPayExpiredEnqueue(String message, long delay) {
        return enqueue(SystemConstant.RABBITMQ_WITHFUDIG_PAY_EXPIRED, message, delay);
    }

    /**
     * 置业服务支付时间
     */
    public boolean compradorPayExpiredEnqueue(String message, long delay) {
        return enqueue(SystemConstant.RABBITMQ_COMPRADOR_PAY_EXPIRED, message, delay);
    }

}