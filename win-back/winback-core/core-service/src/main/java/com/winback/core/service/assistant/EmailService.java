package com.winback.core.service.assistant;

import com.winback.arch.common.enums.EmailEnum;

import java.io.File;
import java.util.List;

/**
 * @author xdrodger
 * @Title: EmailService
 * @ProjectName winback
 * @Description:
 * @date 2019/3/7 15:47
 */
public interface EmailService {

    void sendExceptionEmail(EmailEnum.IMPORTANT_LEVEL importantLevel, EmailEnum.MODULE_TYPE moduleType, String title, String content);

    void sendContractDownloadEmail(String email, List<String> filesUrl);

    void sendAppExceptionEmail(EmailEnum.IMPORTANT_LEVEL importantLevel,EmailEnum.MODULE_TYPE moduleType, String reqParam ,String title ,String exceptionInfo);

    void sendAdminExceptionEmail(EmailEnum.IMPORTANT_LEVEL importantLevel,EmailEnum.MODULE_TYPE moduleType, String reqParam ,String title ,String exceptionInfo);

    void sendAppletExceptionEmail(EmailEnum.IMPORTANT_LEVEL importantLevel,EmailEnum.MODULE_TYPE moduleType, String reqParam ,String title ,String exceptionInfo);
}
