package com.winback.gateway.service.email;

import com.winback.gateway.condition.email.TGatewayEmailConfigCondition;
import com.winback.gateway.controller.req.email.EmailSendReq;
import com.winback.gateway.model.email.TGatewayEmailConfig;

/**
 * 描述：邮件接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/16 9:59
 */
public interface EmailService {


    void sendEmail(EmailSendReq emailSendReq);


    TGatewayEmailConfig getEmailConfig(TGatewayEmailConfigCondition condition);
}
