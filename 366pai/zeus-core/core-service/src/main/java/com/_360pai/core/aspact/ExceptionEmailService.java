package com._360pai.core.aspact;

import com._360pai.core.common.constants.ExceptionEmailEnum;
import com._360pai.core.model.assistant.TSmsEmailConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 描述：异常邮件发送Mq
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 */
@Component
@Slf4j
public class ExceptionEmailService extends EmailService {


    /**
     *发送异常邮件
     * @param level 异常等级
     * @param module 异常模块
     * @param title 异常标题
     * @param reqParam 请求参数
     * @param content 异常信息
     *
     */
    public void sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL level, ExceptionEmailEnum.MODULE_TYPE module,String title ,String reqParam, String  content) {


        try{

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

            List<String> emailList = getExceptionEmailList(level,module);

            if(emailList==null||emailList.size()<1){
                log.error("未配置发送报警邮件参数，参数：{}",module);

                return;
            }

            title = level.getValue()+module.getValue()+title;

            sendExceptionEmail(emailList,title,contentSb.toString());

        }catch (Exception e){

            log.error("发送报警邮件异常异常信息为:{}",e);

        }


    }

    /**
     *
     *根据模块获取发送的信息
     */
    public List<String> getExceptionEmailList(ExceptionEmailEnum.IMPORTANT_LEVEL level, ExceptionEmailEnum.MODULE_TYPE module) {

        List<String> emailList = new ArrayList<>();

        TSmsEmailConfig config = configSmsEmailConfig(module.getType());

        if(config==null){
            return null;
        }

        String emailStr;

        //根据重要等级发送邮件
        if(ExceptionEmailEnum.IMPORTANT_LEVEL.BREAK_DOWN.equals(level)||ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS.equals(level)){

            emailStr = config.getServiceEmail();
        }else{
            emailStr = config.getAuditorEmail();
        }

        if(StringUtils.isNotBlank(emailStr)) {
            emailList.addAll(Arrays.asList(emailStr.split(",")));

        }
        return emailList;
     }


    /**
     *
     *异常信息转换为字符串
     */
    public  String exceptionToStr(Exception e) {
        //StringWriter输出异常信息
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();

    }


}
