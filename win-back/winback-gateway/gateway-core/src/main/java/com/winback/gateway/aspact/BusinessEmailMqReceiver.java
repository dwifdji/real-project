package com.winback.gateway.aspact;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.winback.arch.common.ComEmailSendReq;
import com.winback.arch.common.constant.SystemConstant;
import com.winback.arch.common.utils.ExceptionEmailUtil;
import com.winback.gateway.condition.email.TGatewayEmailConfigCondition;
import com.winback.gateway.controller.req.email.EmailSendReq;
import com.winback.gateway.model.email.TGatewayEmailConfig;
import com.winback.gateway.service.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 描述：异常信息邮件发送
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/11/06 12:26
 */
@Component
@Slf4j
 public class BusinessEmailMqReceiver {

    @Autowired
    private EmailService emailService;


    @RabbitListener(queues = SystemConstant.RABBITMQ_SEND_EMAIL_QUEUE)
    public void process(String message) {
        log.info("queue={}, message={}, dequeue",SystemConstant.RABBITMQ_SEND_EMAIL_QUEUE, message);
        try {
            ComEmailSendReq comEmailSendReq = JSON.parseObject(message, ComEmailSendReq.class);

            //发送人为空时取模板信息发送
            if(comEmailSendReq.getEmailList()==null||comEmailSendReq.getEmailList().size()<1){

                TGatewayEmailConfig config = getTGatewayEmailConfig(comEmailSendReq);
                if(config ==null){
                    return;
                }

                //发送模板邮件
                sendTemplateEmail(comEmailSendReq,config);

                return;
            }

            EmailSendReq  req = new EmailSendReq();
            req.setEmail(comEmailSendReq.getEmailList());
            req.setTitle(comEmailSendReq.getTitle());
            req.setContent(comEmailSendReq.getContent());
            req.setFiles(comEmailSendReq.getFiles());
            emailService.sendEmail(req);
        } catch (Exception e) {
            log.error("发送异常报警邮件异常，异常信息为：{}",e);

            List<String> email = new ArrayList<>();
            email.add("backend@360pai.com");
            EmailSendReq  exceptionReq = new EmailSendReq();
            exceptionReq.setContent(ExceptionEmailUtil.exceptionToStr(e));
            exceptionReq.setTitle("发送邮件异常");
            exceptionReq.setEmail(email);
            emailService.sendEmail(exceptionReq);

         }

     }

    private void sendTemplateEmail(ComEmailSendReq comEmailSendReq, TGatewayEmailConfig config) {

        EmailSendReq  req = new EmailSendReq();
        req.setEmail(Arrays.asList(config.getEmails().split(",")));
        req.setTitle(StringUtils.isEmpty(comEmailSendReq.getTitle())?config.getTitle():comEmailSendReq.getTitle());
        req.setContent(StringUtils.isEmpty(comEmailSendReq.getContent())?getTemplateContent(comEmailSendReq.getTemplateParam(),config.getContent()):comEmailSendReq.getContent());
        req.setFiles(comEmailSendReq.getFiles());

        if(req.getEmail()==null||req.getEmail().size()<1||StringUtils.isEmpty(req.getContent())){
            return;
        }
        emailService.sendEmail(req);

    }

    private String getTemplateContent(JSONObject templateParam, String content) {

        if(templateParam==null){
            return null;
        }

        Map<String, Object> jsonMap = JSONObject.toJavaObject(templateParam, Map.class);

        for (Object s : jsonMap.keySet()) {
            content = content.replaceAll("\\$\\{".concat(s.toString()).concat("\\}")
                    , jsonMap.get(s.toString()).toString());
        }
        return content;

     }

    private TGatewayEmailConfig getTGatewayEmailConfig(ComEmailSendReq comEmailSendReq) {

        TGatewayEmailConfigCondition condition = new TGatewayEmailConfigCondition();
        condition.setBusType(comEmailSendReq.getBusType().getKey());
        condition.setType(comEmailSendReq.getBusTemplate().getKey());
        condition.setDeleteFlag(false);

        return emailService.getEmailConfig(condition);
     }


}
