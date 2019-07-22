package com.winback.core.service.assistant.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.winback.arch.common.OperatorLogModel;
import com.winback.arch.common.constant.SystemConstant;
import com.winback.arch.common.enums.EmailEnum;
import com.winback.arch.common.utils.ToolUtil;
import com.winback.core.service.assistant.EmailService;
import com.winback.core.service.assistant.ExceptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xdrodger
 * @Title: ExceptionServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/3/7 16:26
 */
@Slf4j
@Service
public class ExceptionServiceImpl implements ExceptionService {

    @Autowired
    private EmailService emailService;

    private static List<String> caseModule = new ArrayList<>();

    private static List<String> payModule = new ArrayList<>();

    private static List<String> checkModule = new ArrayList<>();

    private static List<String> userModule = new ArrayList<>();

    private static List<String> contractModule = new ArrayList<>();

    private static List<String> appletModule = new ArrayList<>();


    private static List<String> exceptionMessages = new ArrayList<>();


    static {
        caseModule.addAll(Arrays.asList("case"));
        payModule.addAll(Arrays.asList("pay"));
        checkModule.addAll(Arrays.asList("risk"));
        userModule.addAll(Arrays.asList("account", "user", "company"));
        contractModule.add("contract");
        //appletModule.add("");
        exceptionMessages.addAll(Arrays.asList("参数缺失", "参数非法", "操作失败"));
    }

    /**
     * MQ入队异常处理
     */
    @Override
    public void processEnqueueException(String queue, String message, String exception) {
        StringBuffer contentSb = new StringBuffer();
        contentSb.append("队列名称：");
        contentSb.append("<br>");
        contentSb.append(queue);
        contentSb.append("<br>");
        contentSb.append("<br>");
        contentSb.append("消息为：");
        contentSb.append("<br>");
        contentSb.append(message);
        contentSb.append("<br>");
        contentSb.append("<br>");
        contentSb.append("<br>");
        contentSb.append("异常信息为：");
        contentSb.append("<br>");
        contentSb.append(exception);
        sendEmail("MQ发送异常", contentSb.toString(), EmailEnum.IMPORTANT_LEVEL.SERIOUS, EmailEnum.MODULE_TYPE.OTHER);
    }

    /**
     * MQ出队异常处理
     */
    @Override
    public void processDequeueException(String queue, String message, String exception) {
        log.error("queue={}, message={}, dequeue exception", queue, message);
        StringBuffer contentSb = new StringBuffer();
        contentSb.append("队列名称：");
        contentSb.append("<br>");
        contentSb.append(queue);
        contentSb.append("<br>");
        contentSb.append("<br>");
        contentSb.append("消息为：");
        contentSb.append("<br>");
        contentSb.append(message);
        contentSb.append("<br>");
        contentSb.append("<br>");
        contentSb.append("<br>");
        contentSb.append("异常信息为：");
        contentSb.append("<br>");
        contentSb.append(exception);
        sendEmail("MQ出队业务处理异常", contentSb.toString(), EmailEnum.IMPORTANT_LEVEL.OTHER, EmailEnum.MODULE_TYPE.OTHER);
    }

    private void sendEmail(String title, String content, EmailEnum.IMPORTANT_LEVEL level, EmailEnum.MODULE_TYPE module) {
        emailService.sendExceptionEmail(level, module, title, content);
    }

    /**
     * 全局异常出队处理
     */
    @Override
    public void processGlobalException(String message) {
        try {
            OperatorLogModel operatorLogModel = JSON.parseObject(message, OperatorLogModel.class);
            String exception= operatorLogModel.getException();
            EmailEnum.MODULE_TYPE module = getExceptionModule(operatorLogModel);
            EmailEnum.IMPORTANT_LEVEL level = getExceptionLevel(exception);
            log.info("全局异常处理，logId={}，level={}，module={}", operatorLogModel.getId(), level.getValue(), module.getValue());
            if (EmailEnum.IMPORTANT_LEVEL.IGNORE.equals(level)) {
                return;
            }
            StringBuffer title = new StringBuffer();
            title.append("全局异常处理");
            StringBuffer contentSb = new StringBuffer();
            contentSb.append("基本信息：");
            contentSb.append("<br>");
            contentSb.append(getExceptionBaseInfo(operatorLogModel));
            contentSb.append("<br>");
            contentSb.append("<br>");
            contentSb.append("请求参数为：");
            contentSb.append("<br>");
            contentSb.append(operatorLogModel.getParameter());
            contentSb.append("<br>");
            contentSb.append("<br>");
            contentSb.append("<br>");
            contentSb.append("异常信息为：");
            contentSb.append("<br>");
            contentSb.append(operatorLogModel.getException());
            if (SystemConstant.APPLICATION_NAME_ADMIN.equals(operatorLogModel.getApplicationName())) {
                emailService.sendAdminExceptionEmail(level, module, operatorLogModel.getParameter(), title.toString(), contentSb.toString());
            } else if (SystemConstant.APPLICATION_NAME_APP.equals(operatorLogModel.getApplicationName())) {
                emailService.sendAppExceptionEmail(level, module, operatorLogModel.getParameter(), title.toString(), contentSb.toString());
            } else if (SystemConstant.APPLICATION_NAME_APPLET.equals(operatorLogModel.getApplicationName())){
                emailService.sendAppletExceptionEmail(level, module, operatorLogModel.getParameter(), title.toString(), contentSb.toString());
            } else {
                title = new StringBuffer();
                title.append(getExceptionEnv(operatorLogModel.getRequestUri()));
                title.append(convertApplicationName(operatorLogModel.getApplicationName()));
                title.append(level.getValue());
                title.append(module.getValue());
                title.append("全局异常处理");
                emailService.sendExceptionEmail(level, module, title.toString(), contentSb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("全局异常处理发送邮件失败，msg={}", e.getMessage());
        }
    }

    @Override
    public void processTryCatchException(Integer id, Exception e) {
        processTryCatchException("【id=" + id + "】", Throwables.getStackTraceAsString(e));
    }

    private void processTryCatchException(String title, String content) {
        emailService.sendExceptionEmail(EmailEnum.IMPORTANT_LEVEL.MINOR, EmailEnum.MODULE_TYPE.TRY_CATCH, title, content);
    }

    private String getExceptionEnv(String requestUri) {
        if (requestUri.contains("360yhl.com")) {
            return "【生产环境】";
        } else if (requestUri.contains("test")) {
            return "【test环境】";
        } else if (requestUri.contains("deve")) {
            return "【dev环境】";
        }
        return "【本地环境】";
    }

    private EmailEnum.IMPORTANT_LEVEL getExceptionLevel(String exception) {

        if (exception.contains("DuplicateKeyException")) {
            return EmailEnum.IMPORTANT_LEVEL.MINOR;
        }
        if (exception.contains("jdbc")) {
            return EmailEnum.IMPORTANT_LEVEL.SERIOUS;
        }
        if (exception.contains("NullPointException")) {
            return EmailEnum.IMPORTANT_LEVEL.SERIOUS;
        }
        if (ToolUtil.isContainChinese(exception)) {
            for (String key : exceptionMessages) {
                if(exception.contains(key)) {
                    return EmailEnum.IMPORTANT_LEVEL.SERIOUS;
                }
            }
            return EmailEnum.IMPORTANT_LEVEL.IGNORE;
        }

        return EmailEnum.IMPORTANT_LEVEL.OTHER;
    }

    private EmailEnum.MODULE_TYPE getExceptionModule(OperatorLogModel operatorLogModel) {
        String requestUri = operatorLogModel.getRequestUri();
        if (containsKey(requestUri, caseModule)) {
            return EmailEnum.MODULE_TYPE.CASE;
        }
        if (containsKey(requestUri, payModule)) {
            return EmailEnum.MODULE_TYPE.PAY;
        }
        if (containsKey(requestUri, checkModule)) {
            return EmailEnum.MODULE_TYPE.CHECK;
        }
        if (containsKey(requestUri, userModule)) {
            return EmailEnum.MODULE_TYPE.USER;
        }
        if (containsKey(requestUri, contractModule)) {
            return EmailEnum.MODULE_TYPE.CONTRACT;
        }
        //if (containsKey(requestUri, appletModule)) {
        //    return EmailEnum.MODULE_TYPE.APPLET;
        //}
        return EmailEnum.MODULE_TYPE.OTHER;
    }

    private boolean containsKey(String requestUri, List<String> keys) {
        for (String key : keys) {
            if(requestUri.contains(key)) {
                return true;
            }
        }
        return false;
    }

    private String getExceptionBaseInfo(OperatorLogModel operatorLogModel) {
        StringBuffer sb = new StringBuffer();
        sb.append("id=");
        sb.append(operatorLogModel.getId());
        sb.append("[");
        sb.append("loginId=");
        if (operatorLogModel.getAccountId() != null) {
            sb.append(operatorLogModel.getAccountId());
        }
        sb.append(";");
        sb.append("requestUri=");
        sb.append(operatorLogModel.getRequestUri());
        sb.append(";");
        sb.append("]");
        return sb.toString();
    }

    private String convertApplicationName(String applicationName) {
        if (SystemConstant.APPLICATION_NAME_ADMIN.equals(applicationName)) {
            return "【管理后台】";
        } else if (SystemConstant.APPLICATION_NAME_APP.equals(applicationName)) {
            return "【APP】";
        } else if (SystemConstant.APPLICATION_NAME_APPLET.equals(applicationName)) {
            return "【小程序】";
        }
        return applicationName;
    }
}
