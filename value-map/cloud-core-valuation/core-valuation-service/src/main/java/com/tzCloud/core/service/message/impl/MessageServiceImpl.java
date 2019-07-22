package com.tzCloud.core.service.message.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.tzCloud.core.dto.com.PushMsgDto;
import com.tzCloud.core.model.message.TMapMessageTemplate;
import com.tzCloud.core.service.message.MessageService;
import com.tzCloud.gateway.controller.req.email.EmailSendReq;
import com.tzCloud.gateway.facade.EmailFacade;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    @Reference(version = "1.0.0")
    private EmailFacade emailFacade;

    /**
     *
     *邮件发送
     */
    public void sendEmail(TMapMessageTemplate template, PushMsgDto msg) {

        //获取发送人列表
        List<String> emailList =  getEmailList(template.getPushIds(),msg.getTargetList());
        if(emailList==null||emailList.size()<1){
            return;
        }
        //模板信息
        String context = generateTemplate(msg.getParam(),template.getContent());

        EmailSendReq emailSendReq = new EmailSendReq();
        emailSendReq.setContent(context);
        emailSendReq.setTitle(StringUtils.isEmpty(template.getTitle())?"系统邮件":template.getTitle());
        emailSendReq.setEmail(emailList);
        emailSendReq.setSendType(template.getSendType());

        emailFacade.sendEmail(emailSendReq);

    }

    private String generateTemplate(JSONObject param, String template){

        if(param==null){
            return template;
        }

        Map<String, Object> jsonMap = JSONObject.toJavaObject(param, Map.class);

        for (Object s : jsonMap.keySet()) {
            template = template.replaceAll("\\$\\{".concat(s.toString()).concat("\\}")
                    , jsonMap.get(s.toString()).toString());
        }
        return template;
    }

    /**
     *
     *获取发送的邮件信息
     */
    private List<String> getEmailList(String pushIds, List<String> targetList) {

        if(targetList!=null&&targetList.size()>0){

            return targetList;
        }

        return Arrays.asList(pushIds.split(","));

    }

}
