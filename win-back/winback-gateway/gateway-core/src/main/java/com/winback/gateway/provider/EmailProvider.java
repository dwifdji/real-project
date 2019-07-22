package com.winback.gateway.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.winback.arch.core.sysconfig.properties.GatewayProperties;
import com.winback.gateway.dao.email.TGatewayEmailRecordDao;
import com.winback.gateway.model.email.TGatewayEmailRecord;
import com.winback.gateway.service.email.EmailService;
import com.winback.gateway.service.pay.WxPayService;
import com.winback.gateway.controller.req.email.EmailSendReq;
import com.winback.gateway.controller.req.email.GatewayEmailRecordReq;
import com.winback.gateway.dao.email.TGatewayEmailRecordDao;
import com.winback.gateway.facade.EmailFacade;
import com.winback.gateway.model.email.TGatewayEmailRecord;
import com.winback.gateway.service.email.EmailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 15:44
 */
@Component
@Service(version = "1.0.0")
public class EmailProvider implements EmailFacade {


    @Autowired
    private TGatewayEmailRecordDao gatewayEmailRecordDao;

    @Autowired
    private EmailService emailService;


    @Override
    public void sendEmail(EmailSendReq emailSendReq) {

        emailService.sendEmail(emailSendReq);

    }

    @Override
    public Integer saveEmailRecord(GatewayEmailRecordReq req) {

        TGatewayEmailRecord record = new TGatewayEmailRecord();
        BeanUtils.copyProperties(req, record);


        gatewayEmailRecordDao.insert(record);

        return record.getId();
    }

    @Override
    public void updateEmailRecord(GatewayEmailRecordReq req) {
        TGatewayEmailRecord record = new TGatewayEmailRecord();
        BeanUtils.copyProperties(req, record);

        gatewayEmailRecordDao.updateById(record);

    }






}
