package com.winback.arch.common.utils;

import com.winback.arch.common.ComEmailSendReq;
import com.winback.arch.common.enums.EmailEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * 描述：系统异常报警邮件工具类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/3/7 9:26
 */
@Slf4j
@Component
public class ExceptionEmailUtil {



    /**
     * app 相关异常报警
     * @importantLevel 重要等级
     * @reqParam 请求参数
     * @title 邮件标题  可为空
     * @exceptionInfo e  异常信息
     *@ e  异常信息
     */
    public static void appExceptionEmail(EmailEnum.IMPORTANT_LEVEL importantLevel,EmailEnum.MODULE_TYPE moduleType, String reqParam ,String title ,String exceptionInfo, Exception e) {


        ExceptionEmailUtil.sendComExceptionEmail(moduleType,EmailEnum.WEB.APP,importantLevel,reqParam,title,e,exceptionInfo);

    }



    /**
     * Admin 后台管理报警
     * @importantLevel 重要等级
     * @reqParam 请求参数
     * @title 邮件标题  可为空
     * @exceptionInfo e  异常信息
     *@ e  异常信息
     */
    public static void adminExceptionEmail(EmailEnum.IMPORTANT_LEVEL importantLevel,EmailEnum.MODULE_TYPE moduleType, String reqParam ,String title ,String exceptionInfo, Exception e) {


        ExceptionEmailUtil.sendComExceptionEmail(moduleType,EmailEnum.WEB.ADMIN,importantLevel,reqParam,title,e,exceptionInfo);

    }



    /**
     * applet 小程序
     * @importantLevel报警 重要等级
     * @reqParam 请求参数
     * @title 邮件标题  可为空
     * @exceptionInfo e  异常信息
     *@ e  异常信息
     */
    public static void appletExceptionEmail(EmailEnum.IMPORTANT_LEVEL importantLevel,EmailEnum.MODULE_TYPE moduleType, String reqParam ,String title ,String exceptionInfo, Exception e) {


        ExceptionEmailUtil.sendComExceptionEmail(moduleType,EmailEnum.WEB.APPLET,importantLevel,reqParam,title,e,exceptionInfo);

    }






    /**
     * 发送异常邮件
     *
     * @return
     */
    private static void sendComExceptionEmail(EmailEnum.MODULE_TYPE moduleType,EmailEnum.WEB web,EmailEnum.IMPORTANT_LEVEL importantLevel ,String reqParam ,String title ,Exception e,String exceptionInfo) {


        StringBuffer contentSb = new StringBuffer();
        contentSb.append("请求参数为：");
        contentSb.append("<br>");
        contentSb.append(reqParam);
        contentSb.append("<br>");
        contentSb.append("<br>");
        contentSb.append("<br>");

        contentSb.append("异常信息为：");
        contentSb.append("<br>");
        contentSb.append(exceptionInfo);
        if(e!=null){
            contentSb.append(exceptionToStr(e));

        }

        ComEmailSendReq comEmailSendReq = new ComEmailSendReq();
        comEmailSendReq.setBusType(EmailEnum.BUS_TYPE.EXCEPTION);
        comEmailSendReq.setModuleType(moduleType);
        comEmailSendReq.setImportantLevel(importantLevel);
        comEmailSendReq.setWeb(web);
        comEmailSendReq.setTitle(StringUtils.isEmpty(title)?"异常报警":title);
        comEmailSendReq.setContent(contentSb.toString());
        ComEmailUtil.sendEmail(comEmailSendReq);



    }



    /**
     *
     *异常信息转换为字符串
     */
    public static String exceptionToStr(Exception e) {
        //StringWriter输出异常信息
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();

    }



}
