package com._360pai.core.aspact;

import com._360pai.arch.common.OperatorLogModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.utils.ToolUtil;
import com._360pai.core.common.constants.ExceptionEmailEnum;
import com._360pai.gateway.controller.req.email.EmailSendReq;
import com._360pai.gateway.facade.EmailFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xdrodger
 * @Title: GlobalExceptionService
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/11/19 18:07
 */
@Component
@Slf4j
public class ExceptionService {
    @Autowired
    private ExceptionEmailService exceptionEmailService;

    @Reference(version = "1.0.0")
    private EmailFacade emailFacade;

    private static List<String> auctionModule = new ArrayList<>();

    private static List<String> enrollingModule = new ArrayList<>();

    private static List<String> serviceModule = new ArrayList<>();

    private static List<String> userModule = new ArrayList<>();

    private static List<String> templateModule = new ArrayList<>();

    private static List<String> exceptionMessages = new ArrayList<>();

    static {
        auctionModule.addAll(Arrays.asList("activity", "asset", "album"));
        enrollingModule.addAll(Arrays.asList("enrolling"));
        serviceModule.addAll(Arrays.asList("comprador", "withfudig", "disposal"));
        userModule.addAll(Arrays.asList("account", "user", "company"));
        templateModule.add("template");
        exceptionMessages.addAll(Arrays.asList("参数缺失", "参数非法", "操作失败"));
    }

    public List<String> getTryCatchExceptionEmailList() {
        return exceptionEmailService.getExceptionEmailList(ExceptionEmailEnum.IMPORTANT_LEVEL.OTHER, ExceptionEmailEnum.MODULE_TYPE.TRY_CATCH);
    }

    /**
     * MQ入队异常处理
     */
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
        sendEmail("MQ发送异常", contentSb.toString(), ExceptionEmailEnum.IMPORTANT_LEVEL.BREAK_DOWN, ExceptionEmailEnum.MODULE_TYPE.OTHER);
    }

    /**
     * MQ出队异常处理
     */
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
        sendEmail("MQ出队业务处理异常", contentSb.toString(), ExceptionEmailEnum.IMPORTANT_LEVEL.OTHER, ExceptionEmailEnum.MODULE_TYPE.OTHER);
    }

    private void sendEmail(String title, String content, ExceptionEmailEnum.IMPORTANT_LEVEL level, ExceptionEmailEnum.MODULE_TYPE module) {
        List<String> list = exceptionEmailService.getExceptionEmailList(level, module);
        EmailSendReq req = new EmailSendReq();
        req.setEmail(list);
        req.setTitle(level.getValue() + module.getValue() + title);
        req.setContent(content);
        req.setSendType("2");
        emailFacade.sendEmail(req);
    }

    /**
     * 全局异常出队处理
     */
    public void processGlobalException(String message) {
        try {
            OperatorLogModel operatorLogModel = JSON.parseObject(message, OperatorLogModel.class);
            String exception= operatorLogModel.getException();
            ExceptionEmailEnum.MODULE_TYPE module = getExceptionModule(operatorLogModel);
            ExceptionEmailEnum.IMPORTANT_LEVEL level = getExceptionLevel(exception);
            log.info("全局异常处理，logId={}，level={}，module={}", operatorLogModel.getId(), level.getValue(), module.getValue());
            if (ExceptionEmailEnum.IMPORTANT_LEVEL.IGNORE.equals(level)) {
                return;
            }
            List<String> list = exceptionEmailService.getExceptionEmailList(level, module);
            EmailSendReq req = new EmailSendReq();
            req.setEmail(list);
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
            StringBuffer title = new StringBuffer();
            title.append(getExceptionEnv(operatorLogModel.getRequestUri()));
            title.append(convertApplicationName(operatorLogModel.getApplicationName()));
            title.append(level.getValue());
            title.append(module.getValue());
            title.append("全局异常处理");
            req.setTitle(title.toString());
            req.setContent(contentSb.toString());
            req.setSendType("2");
            emailFacade.sendEmail(req);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("全局异常处理发送邮件失败，msg={}", e.getMessage());
        }
    }

    private String getExceptionEnv(String requestUri) {
        if (requestUri.contains("360pai.com")) {
            return "【生产环境】";
        } else if (requestUri.contains("prev2") || requestUri.contains("test")) {
            return "【pre环境】";
        } else if (requestUri.contains("applet.360pai.xyz") || requestUri.contains("deve")) {
            return "【dev环境】";
        }
        return "【本地环境】";
    }

    private ExceptionEmailEnum.IMPORTANT_LEVEL getExceptionLevel(String exception) {

        if (exception.contains("DuplicateKeyException")) {
            return ExceptionEmailEnum.IMPORTANT_LEVEL.MINOR;
        }
        if (exception.contains("jdbc")) {
            return ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS;
        }
        if (exception.contains("NullPointException")) {
            return ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS;
        }
        if (ToolUtil.isContainChinese(exception)) {
            for (String key : exceptionMessages) {
                if(exception.contains(key)) {
                    return ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS;
                }
            }
            return ExceptionEmailEnum.IMPORTANT_LEVEL.IGNORE;
        }

        return ExceptionEmailEnum.IMPORTANT_LEVEL.OTHER;
    }

    private ExceptionEmailEnum.MODULE_TYPE getExceptionModule(OperatorLogModel operatorLogModel) {
        String requestUri = operatorLogModel.getRequestUri();
        if (containsKey(requestUri, enrollingModule)) {
            return ExceptionEmailEnum.MODULE_TYPE.ENROLLING;
        }
        if (containsKey(requestUri, auctionModule)) {
            return ExceptionEmailEnum.MODULE_TYPE.AUCTION;
        }
        if (containsKey(requestUri, serviceModule)) {
            return ExceptionEmailEnum.MODULE_TYPE.SERVICE;
        }
        if (containsKey(requestUri, userModule)) {
            return ExceptionEmailEnum.MODULE_TYPE.USER;
        }
        if (containsKey(requestUri, templateModule)) {
            return ExceptionEmailEnum.MODULE_TYPE.TEMPLATE;
        }
        return ExceptionEmailEnum.MODULE_TYPE.OTHER;
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
        if (operatorLogModel.getPartyId() != null) {
            sb.append("partyId=");
            sb.append(operatorLogModel.getPartyId());
            sb.append(";");
        }
        sb.append("requestUri=");
        sb.append(operatorLogModel.getRequestUri());
        sb.append(";");
        sb.append("]");
        return sb.toString();
    }

    private String convertApplicationName(String applicationName) {
        if (SystemConstant.APPLICATION_NAME_ADMIN.equals(applicationName)) {
            return "【管理后台】";
        } else if (SystemConstant.APPLICATION_NAME_PARTNER.equals(applicationName)) {
            return "【机构后台】";
        } else if (SystemConstant.APPLICATION_NAME_PLATFORM.equals(applicationName)) {
            return "【平台主站】";
        } else if (SystemConstant.APPLICATION_NAME_APPLET.equals(applicationName)) {
            return "【小程序】";
        }
        return applicationName;
    }
}
