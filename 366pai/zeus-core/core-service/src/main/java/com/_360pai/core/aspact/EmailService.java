package com._360pai.core.aspact;

import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.condition.assistant.TSmsEmailConfigCondition;
import com._360pai.core.dao.assistant.TSmsEmailConfigDao;
import com._360pai.core.model.assistant.TSmsEmailConfig;
import com._360pai.gateway.controller.req.email.EmailSendReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 描述：服务类 发送邮件服务类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/10/12 12:53
 */
@Component
@Slf4j
public class EmailService {


    public   static final String AUDITOR ="1";//平台审核人


    public  static final String CUSTOMER_SERVICE ="2";//平台客服



    @Autowired
    private  GatewayMqSender gatewayMqSender;


    @Autowired
    private TSmsEmailConfigDao tSmsEmailConfigDao;



    /**
     * redis缓存管理器
     */
    @Resource
    private RedisCachemanager redisCacheManager;















      /**
       *
       *发送邮件
       */
    public void sendEmail(List<String> emailList,String title,String content){

        if(notSend(content)){
            return;
        }


        if (CollectionUtils.isNotEmpty(emailList) &&  StringUtils.isNotBlank(content)){
            EmailSendReq req = new EmailSendReq();

            List<String> list = new ArrayList<>();

            req.setCopyEmail(list);

            //内容发送
            req.setSendType(EmailSendReq.CONTENT_TYPE);
            req.setTitle(title);
            req.setContent(content);
            req.setEmail(emailList);

            gatewayMqSender.sendEmail(req);
        }

    }

    private boolean notSend(String content) {

        try {
            if(content.contains("No provider available from registry")){
                String  noProvider = "noProvider";

                Object value= redisCacheManager.get(noProvider);

                if(value==null){
                    Long timeOut = 5*60L;
                    redisCacheManager.set(noProvider,"No provider available from registry" ,timeOut);
                }else{
                    return true;
                }

            }


            return false;
        }catch (Exception e){

            return false;
        }
    }


    /**
     *
     *发送异常报警邮件
     */
    void sendExceptionEmail(List<String> emailList,String title,String content){

        if (CollectionUtils.isNotEmpty(emailList) &&  StringUtils.isNotBlank(content)){
            EmailSendReq req = new EmailSendReq();

            List<String> list = new ArrayList<>();
            req.setCopyEmail(list);
            //内容发送
            req.setSendType(EmailSendReq.CONTENT_TYPE);
            req.setTitle(title);
            req.setContent(content);
            req.setEmail(emailList);
            gatewayMqSender.sendExceptionEmail(req);
        }

    }

    /**
     *
     *获取发送的邮件
     */
     List<String> getEmailList(String busType, String serviceType) {

        TSmsEmailConfig config = configSmsEmailConfig(busType);

        List<String> emailList = new ArrayList<>();

        String emailStr;

        if(config==null){
            log.error("t_sms_email_config表未配置类型， ,type:{}" ,busType);
            return emailList;
        }

        //1为平台审核人
        if("1".equals(serviceType)){
            emailStr= config.getAuditorEmail();
        }else{
            emailStr= config.getServiceEmail();

        }
        if(StringUtils.isNotBlank(emailStr)){
            emailList.addAll(Arrays.asList(emailStr.split(",")));
        }
        return emailList;
    }

    public TSmsEmailConfig configSmsEmailConfig(String busType) {

        TSmsEmailConfigCondition configCondition = new TSmsEmailConfigCondition();
        configCondition.setBusType(busType);
        configCondition.setStatus("1");

        TSmsEmailConfig config = tSmsEmailConfigDao.selectFirst(configCondition);



        return config;
    }


    public String getServicePhone(String busType) {
        TSmsEmailConfigCondition configCondition = new TSmsEmailConfigCondition();
        configCondition.setBusType(busType);
        configCondition.setStatus("1");
        TSmsEmailConfig config = tSmsEmailConfigDao.selectFirst(configCondition);
        if (config != null) {
            return config.getServicePhone();
        }
        return "";
    }


}
