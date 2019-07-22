package com._360pai.core.provider;

import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.aspact.GatewayExceptionEmailService;
import com._360pai.core.common.constants.ExceptionEmailEnum;
import com._360pai.core.dao.email.GatewayEmailRecordDao;
import com._360pai.core.model.DfftPay.QueryBindMemberReq;
import com._360pai.core.model.email.GatewayEmailRecord;
import com._360pai.core.service.DfftPayService;
import com._360pai.core.service.EmailService;
import com._360pai.core.service.WxPayService;
import com._360pai.gateway.common.fddSignature.FddSignatureUtils;
import com._360pai.gateway.controller.req.email.EmailSendReq;
import com._360pai.gateway.controller.req.email.GatewayEmailRecordReq;
import com._360pai.gateway.facade.EmailFacade;
import com.alibaba.dubbo.config.annotation.Service;
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
    private GatewayEmailRecordDao gatewayEmailRecordDao;


    @Autowired
    private EmailService emailService;


    @Autowired
    private DfftPayService dfftPayService;


    @Autowired
    private GatewayExceptionEmailService gatewayExceptionEmailService;



    @Autowired
    private GatewayProperties gatewayProperties;


    @Autowired
    private WxPayService wxPayService;


    @Override
    public void sendEmail(EmailSendReq emailSendReq) {

        emailService.sendEmail(emailSendReq);

    }

    @Override
    public Integer saveEmailRecord(GatewayEmailRecordReq req) {

        GatewayEmailRecord record = new GatewayEmailRecord();
        BeanUtils.copyProperties(req, record);


        gatewayEmailRecordDao.insert(record);

        return record.getId();
    }

    @Override
    public void updateEmailRecord(GatewayEmailRecordReq req) {
        GatewayEmailRecord record = new GatewayEmailRecord();
        BeanUtils.copyProperties(req, record);

        gatewayEmailRecordDao.updateById(record);

    }




    @Override
    public void gatewayHeartBeatQuartz() {
        //东方付通连通性监控
        QueryBindMemberReq req = new QueryBindMemberReq();
        req.setMemCode(gatewayProperties.getQueryMemCode());
        req.setMemName(gatewayProperties.getQueryMemName());

        dfftPayService.queryBindMember(req);

        //法大大连通性监控
        try {
            //主动查询签章的状态
            FddSignatureUtils.invokeQuerySignStatus(gatewayProperties,gatewayProperties.getQueryContractId(),gatewayProperties.getFddAppId());
        }catch (Exception e){

            //发送系统异常邮件
            gatewayExceptionEmailService.sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.SIGNATURE,"法大大系统监控异常",null,e);

        }

        //微信支付监控
        wxPayService.queryScanPayResult(gatewayProperties.getQueryOutTradeNo(),null);



    }


}
