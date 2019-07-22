package com.winback.core.service.assistant.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.winback.arch.common.ComEmailSendReq;
import com.winback.arch.common.enums.EmailEnum;
import com.winback.arch.common.utils.ComEmailUtil;
import com.winback.arch.common.utils.ExceptionEmailUtil;
import com.winback.core.service.assistant.EmailService;
import com.winback.gateway.controller.req.email.EmailSendReq;
import com.winback.gateway.facade.EmailFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author xdrodger
 * @Title: EmailServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/3/7 15:47
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    @Reference(version = "1.0.0")
    private EmailFacade emailFacade;

    @Override
    public void sendExceptionEmail(EmailEnum.IMPORTANT_LEVEL importantLevel, EmailEnum.MODULE_TYPE moduleType, String title, String content) {
        ComEmailSendReq req = new ComEmailSendReq();
        req.setBusType(EmailEnum.BUS_TYPE.EXCEPTION);
        req.setTitle(title);
        req.setContent(content);
        req.setModuleType(moduleType);
        req.setImportantLevel(importantLevel);
        ComEmailUtil.sendEmail(req);
    }

    @Override
    public void sendContractDownloadEmail(String email, List<String> filesUrl) {
        EmailSendReq req = new EmailSendReq();
        req.setSendType("2");
        req.setTitle("【合同商城】您下载的合同已发送，请查收");
        req.setContent("附件是您下载的合同，请注意查收。");
        req.setEmail(Arrays.asList(email));
        req.setFilesUrl(filesUrl);
        emailFacade.sendEmail(req);
    }

    @Override
    public void sendAppExceptionEmail(EmailEnum.IMPORTANT_LEVEL importantLevel, EmailEnum.MODULE_TYPE moduleType, String reqParam, String title, String exceptionInfo) {
        ExceptionEmailUtil.appExceptionEmail(importantLevel, moduleType, reqParam, title, exceptionInfo, null);
    }

    @Override
    public void sendAdminExceptionEmail(EmailEnum.IMPORTANT_LEVEL importantLevel, EmailEnum.MODULE_TYPE moduleType, String reqParam, String title, String exceptionInfo) {
        ExceptionEmailUtil.adminExceptionEmail(importantLevel, moduleType, reqParam, title, exceptionInfo, null);
    }

    @Override
    public void sendAppletExceptionEmail(EmailEnum.IMPORTANT_LEVEL importantLevel, EmailEnum.MODULE_TYPE moduleType, String reqParam, String title, String exceptionInfo) {
        ExceptionEmailUtil.appletExceptionEmail(importantLevel, moduleType, reqParam, title, exceptionInfo, null);
    }
}
