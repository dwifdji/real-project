package com._360pai.core.aspact;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.common.constants.ExceptionEmailEnum;
import com._360pai.core.facade.assistant.CommonFacade;
import com._360pai.core.service.EmailService;
import com._360pai.gateway.common.dfftpay.PayResultEnums;
import com._360pai.gateway.controller.req.dfftpay.UnifiedPayResp;
import com._360pai.gateway.controller.req.email.EmailSendReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 描述：第三方服务异常邮件通知
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/11/06 12:26
 */
@Component
@Slf4j
public class GatewayExceptionEmailService {

    @Autowired
    private EmailService emailService;


    @Reference(version = "1.0.0")
    private CommonFacade commonFacade;

    @Autowired
    private GatewayProperties gatewayProperties;




    private void sendEmail(ExceptionEmailEnum.IMPORTANT_LEVEL level, ExceptionEmailEnum.MODULE_TYPE module, String title , String reqParam, String  content) {

        try{
            List<String> emailList = getEmailsList(level,module);
            
            if(emailList==null||emailList.size()<1){
                return;
            }
            

            EmailSendReq emailSendReq = new EmailSendReq();

            StringBuffer contentSb = new StringBuffer();
            contentSb.append("请求参数为：");
            contentSb.append("<br>");
            contentSb.append(reqParam);
            contentSb.append("<br>");
            contentSb.append("<br>");
            contentSb.append("<br>");

            contentSb.append("异常信息为：");
            contentSb.append("<br>");
            contentSb.append(content);

            title = getExceptionEnv(gatewayProperties.getPayUrl())+level.getValue()+module.getValue()+title;

            emailSendReq.setSendType("10");
            emailSendReq.setEmail(emailList);
            emailSendReq.setTitle(title);
            emailSendReq.setContent(contentSb.toString());
            emailService.sendEmail(emailSendReq);


        }catch (Exception e){

            log.error("发送报警邮件异常异常信息为:{}",e);

        }




     }


    private String getExceptionEnv(String requestUri) {
        if (requestUri.contains("www.easternpay.com.cn")) {
            return "【生产环境】";
        } else if (requestUri.contains("uat.easternpay.com.cn")) {
            return "【pre环境】";
        }
        return "【本地环境】";
    }

    //获取异常邮件配置
    private List<String> getEmailsList(ExceptionEmailEnum.IMPORTANT_LEVEL level, ExceptionEmailEnum.MODULE_TYPE module) {
        List<String> list = new ArrayList<>();

        String emailConfig = commonFacade.getEmailConfig(module.getType());

        if(StringUtils.isEmpty(emailConfig)){
            return null;
        }


        JSONObject jsonObject = JSONObject.parseObject(emailConfig);


        String emails;

        //根据严重等级取发送人员
        if(ExceptionEmailEnum.IMPORTANT_LEVEL.BREAK_DOWN.equals(level)||ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS.equals(level)){

            emails =  jsonObject.getString("serviceEmail");
        }else{
            emails =  jsonObject.getString("auditorEmail");
        }

        if(StringUtils.isNotBlank(emails)){
            list.addAll(Arrays.asList(emails.split(",")));
        }

        return list;
        
        
    }


    /**
     *
     *发送异常邮件
     */
    public void sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL level, ExceptionEmailEnum.MODULE_TYPE module, String title, String req, Exception e) {

        sendEmail(level,module,title,JSON.toJSONString(req),exceptionToStr(e));

    }



    /**
     *
     *异常信息转换为字符串
     */
    private   String exceptionToStr(Exception e) {
        //StringWriter输出异常信息
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();

    }




    /**
     *
     *发送提醒邮件
     */
    public void sendRemindEmail(ExceptionEmailEnum.MODULE_TYPE module, String title, String req, String resp) {

        sendEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.MINOR,module,title,req,resp);

    }


    /**
     *
     *支付提醒邮件
     *
     */
    protected void sendPayRemindEmail(Object req, UnifiedPayResp resp) {

        try {
            //当支付状态不为支付成功 或者等待支付时
            if(!PayResultEnums.PAY_SUCCESS.getCode().equals(resp.getCode())&&!PayResultEnums.PAY_NOTICE.getCode().equals(resp.getCode())){

                new Thread(()->sendRemindEmail(ExceptionEmailEnum.MODULE_TYPE.PAY,"东方付通接口不为成功状态",JSON.toJSONString(req),JSON.toJSONString(resp))).start();
            }
        }catch (Exception e){
            log.error("发送提醒邮件异常，异常信息为：{}",e);
        }


    }




    /**
     *
     *法大大提醒邮件
     *
     */
    protected void sendFddRemindEmail(Object req, Object resp,String code) {

        try {
            //当支付状态不为支付成功 或者等待支付时
            if(!ApiCallResult.SUCCESS.getCode().equals(code)){

                new Thread(()->sendRemindEmail(ExceptionEmailEnum.MODULE_TYPE.SIGNATURE,"法大大接口不为成功状态",JSON.toJSONString(req),JSON.toJSONString(resp))).start();
            }
        }catch (Exception e){
            log.error("发送提醒邮件异常，异常信息为：{}",e);
        }


    }



    /**
     *
     *邮件提醒邮件
     *
     */
    protected void sendSmsRemindEmail(Object req, Object resp,String code) {

        try {
            //当支付状态不为支付成功 或者等待支付时
            if(!ApiCallResult.SUCCESS.getCode().equals(code)){

                new Thread(()->sendRemindEmail(ExceptionEmailEnum.MODULE_TYPE.SMS,"短信发送不成功",JSON.toJSONString(req),JSON.toJSONString(resp))).start();
            }
        }catch (Exception e){
            log.error("发送提醒邮件异常，异常信息为：{}",e);
        }


    }

}
