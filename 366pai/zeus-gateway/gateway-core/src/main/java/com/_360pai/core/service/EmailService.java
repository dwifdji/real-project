package com._360pai.core.service;

import com._360pai.gateway.controller.req.email.EmailSendReq;

/**
 * 描述：阿里短信服务接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 14:41
 */
public interface EmailService {



    /**
     * @param emailSendReq 发送的请求
     * @time 2018/8/7 14:41
\     */
    void sendEmail(EmailSendReq emailSendReq);
}
