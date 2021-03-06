package com.winback.gateway.facade;

import com.winback.gateway.controller.req.email.EmailSendReq;
import com.winback.gateway.controller.req.email.GatewayEmailRecordReq;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 15:30
 */
public interface EmailFacade {


    /**
     *
     *发送邮件
     */
    void sendEmail(EmailSendReq emailSendReq);


    /**
     *
     *保存短信
     */
    Integer saveEmailRecord(GatewayEmailRecordReq req);


    /**f
     *
     *更新发送的邮件信息
     */
    void updateEmailRecord(GatewayEmailRecordReq req);









}
