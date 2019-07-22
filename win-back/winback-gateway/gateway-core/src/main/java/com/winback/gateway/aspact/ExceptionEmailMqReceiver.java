package com.winback.gateway.aspact;


import com.alibaba.fastjson.JSON;
import com.winback.arch.common.ComEmailSendReq;
import com.winback.arch.common.constant.SystemConstant;
import com.winback.arch.common.enums.EmailEnum;
import com.winback.arch.common.utils.ExceptionEmailUtil;
import com.winback.arch.core.sysconfig.properties.GatewayProperties;
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

    @Autowired
    private GatewayProperties gatewayProperties;


    @RabbitListener(queues = SystemConstant.RABBITMQ_SEND_EXCEPTION_EMAIL_QUEUE)
    public void process(String message) {
        log.info("queue={}, message={}, dequeue",SystemConstant.RABBITMQ_SEND_EXCEPTION_EMAIL_QUEUE, message);

        try {
            ComEmailSendReq comEmailSendReq = JSON.parseObject(message, ComEmailSendReq.class);

            //根据模块获取发送邮件的配置
            List<String> emailList = getConfigEmail(comEmailSendReq);

            if(emailList==null||emailList.size()<1){

                return;
            }

            EmailSendReq  req = new EmailSendReq();
            req.setEmail(emailList);
            req.setTitle(getExceptionTitle(comEmailSendReq));
            req.setContent(comEmailSendReq.getContent());
            emailService.sendEmail(req);
        }catch (Exception e){
            log.error("发送异常报警邮件异常，异常信息为：{}",e);

            sendExceptionEmail(e);

        }

     }

    private void sendExceptionEmail(Exception e) {
        List<String> email = new ArrayList<>();
        email.add("backend@360pai.com");
        EmailSendReq  exceptionReq = new EmailSendReq();
        exceptionReq.setContent(ExceptionEmailUtil.exceptionToStr(e));
        exceptionReq.setTitle("发送邮件异常");
        exceptionReq.setEmail(email);
        emailService.sendEmail(exceptionReq);
    }

    /**
      *拼接异常信息的标题
      */
    private String getExceptionTitle(ComEmailSendReq comEmailSendReq) {

        StringBuffer buffer = new StringBuffer();

        buffer.append(getEnvironment());
        buffer.append(comEmailSendReq.getWeb()==null?EmailEnum.WEB.APP.getValue():comEmailSendReq.getWeb().getValue());
        buffer.append(comEmailSendReq.getImportantLevel()==null?EmailEnum.IMPORTANT_LEVEL.OTHER.getValue():comEmailSendReq.getImportantLevel().getValue());
        buffer.append(comEmailSendReq.getModuleType().getValue());
        buffer.append(comEmailSendReq.getTitle());

        return buffer.toString();
    }


    private String getEnvironment() {

        return "admin_tech@360yhl.com".equals(gatewayProperties.getSenderAccount())?EmailEnum.ENVIRONMENT.ONLINE.getValue():EmailEnum.ENVIRONMENT.TEST.getValue();
     }


    /**
      *
      *获取的配置信息
      */
    private List<String> getConfigEmail(ComEmailSendReq comEmailSendReq) {
        TGatewayEmailConfigCondition condition = new TGatewayEmailConfigCondition();
        condition.setBusType(comEmailSendReq.getBusType().getKey());
        condition.setType(comEmailSendReq.getModuleType().getType());
        condition.setDeleteFlag(false);

        TGatewayEmailConfig config = emailService.getEmailConfig(condition);

        if(config==null||StringUtils.isEmpty(config.getEmails())){
            return null;
        }

        return Arrays.asList(config.getEmails().split(","));
    }


}
