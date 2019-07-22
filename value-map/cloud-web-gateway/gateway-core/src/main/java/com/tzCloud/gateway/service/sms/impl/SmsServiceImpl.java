package com.tzCloud.gateway.service.sms.impl;

import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.arch.core.sysconfig.properties.GatewayProperties;
import com.tzCloud.gateway.dao.sms.TGatewaySmsRecordDao;
import com.tzCloud.gateway.model.sms.TGatewaySmsRecord;
import com.tzCloud.gateway.service.sms.SmsService;
import com.tzCloud.gateway.common.alisms.AliSmsTemplateEnums;
import com.tzCloud.gateway.common.alisms.AliSmsUtils;
import com.tzCloud.gateway.controller.req.alisms.FAliSmsSendReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 描述：短信接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/16 9:59
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {


    @Autowired
    private GatewayProperties gatewayProperties;

    @Autowired
    private TGatewaySmsRecordDao gatewaySmsRecordDao;

    @Override
    public ResponseModel sendSms(FAliSmsSendReq req) {

        //参数检查
        if(StringUtils.isEmpty(req.getPhoneNumber())||StringUtils.isEmpty(req.getTemplateCode())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        //检查短信开关
        if("0".equals(gatewayProperties.getAliSmsOpen())){
            ResponseModel resp = new ResponseModel();
            resp.setCode(ApiCallResult.SUCCESS.getCode());
            resp.setDesc("短信开关关闭！");
            new Thread(()->insertSmsRecord(req,resp)).start();
            return resp;
        }

        ResponseModel resp =AliSmsUtils.sendSms(gatewayProperties,req.getPhoneNumber(),req.getTemplateCode(),req.getTemplateParam());

        //插入短信记录表
        new Thread(()->insertSmsRecord(req,resp)).start();

        return resp;
    }


    /**
     *短信发送记录
     * @param
     * @param
     */
    private void insertSmsRecord(FAliSmsSendReq req,ResponseModel resp) {

        try {
            TGatewaySmsRecord record = new TGatewaySmsRecord();
            record.setPhone(req.getPhoneNumber());
            record.setTemplateCode(req.getTemplateCode());
            record.setTemplateParam(req.getTemplateParam());
            record.setMsg(resp.getDesc());
            record.setStatus(resp.getCode());
            record.setCreateTime(new Date());
            record.setTemplateContent(AliSmsTemplateEnums.getDesc(req.getTemplateCode()));
            gatewaySmsRecordDao.insert(record);

        } catch (Exception e) {
            log.error("插入短信记录表异常，异常信息为：{}",e);
        }

    }
}
