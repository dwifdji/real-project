package com.tzCloud.gateway.service.email;

import com.tzCloud.gateway.controller.req.email.EmailSendReq;

/**
 * 描述：邮件接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/16 9:59
 */
public interface EmailService {


    void sendEmail(EmailSendReq emailSendReq);
}
