package com._360pai.core.service.impl;


import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.aspact.GatewayExceptionEmailService;
import com._360pai.core.common.constants.ExceptionEmailEnum;
import com._360pai.core.dao.sms.GatewaySmsRecordDao;
import com._360pai.core.model.sms.AliSmsSendReq;
import com._360pai.core.model.sms.GatewaySmsRecord;
import com._360pai.core.service.AliSmsService;
import com._360pai.gateway.common.alisms.AliSmsTemplateEnums;
import com._360pai.gateway.common.alisms.AliSmsUtils;
import com._360pai.gateway.common.alisms.SmsSendResp;
import com._360pai.gateway.resp.AliSendSmsResp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 15:00
 */
@Service
public class AliSmsServiceImpl extends  GatewayExceptionEmailService implements AliSmsService {

    private  final Logger logger = LoggerFactory.getLogger(AliSmsServiceImpl.class);


    @Autowired
    private GatewaySmsRecordDao gatewaySmsRecordDao;

    @Autowired
    private GatewayProperties gatewayProperties;




    @Override
    public AliSendSmsResp sendSms(AliSmsSendReq req) {

        logger.info("请求短信发送服务，请求参数为:{}",JSON.toJSONString(req));

        AliSendSmsResp resp= new AliSendSmsResp();

        if("0".equals(gatewayProperties.getAliSmsOpen())){
            resp.setCode(ApiCallResult.SUCCESS.getCode());
            resp.setDesc("短信开关关闭！");
            new Thread(()->insertSmsRecord(req,resp)).start();
            //发送提示邮件
            sendSmsRemindEmail(req,resp,resp.getCode());

            return resp;
        }

        //参数校验
        if(StringUtils.isEmpty(req.getPhoneNumber())||StringUtils.isEmpty(req.getTemplateCode())){
            resp.setCode(ApiCallResult.EMPTY.getCode());
            resp.setDesc(ApiCallResult.EMPTY.getDesc());
            new Thread(()->insertSmsRecord(req,resp)).start();
            return resp;
        }

        //校验模板参数是不是超过了20 字符
        req.setTemplateParam(filterParam(req.getTemplateParam()));




        try {
            SmsSendResp smsResp =AliSmsUtils.sendSms(gatewayProperties,req.getPhoneNumber(),req.getTemplateCode(),req.getTemplateParam());

            resp.setCode(smsResp.getCode());
            resp.setDesc(smsResp.getDesc());

            new Thread(()->insertSmsRecord(req,resp)).start();
            logger.info("短信服务返回，返回参数为：{}",JSON.toJSONString(resp));
        }catch (Exception e){

            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.SMS,"阿里云短信发送",JSON.toJSONString(req),e);
            logger.info("阿里短信异常，异常信息为：{}",e);

        }

        //发送提示邮件
        sendSmsRemindEmail(req,resp,resp.getCode());

        return resp;
    }

    /**
     *
     *超过20字符过滤
     */
    private String filterParam(String templateParam) {

        try {
            JSONObject json = JSON.parseObject(templateParam);

            for(String str:json.keySet()){

                if(json.get(str).toString().length()>20){
                    json.put(str,json.get(str).toString().substring(0, 17)+"...");
                }

            }

            return json.toJSONString();
        }catch (Exception e){
            logger.error("截取发送短信字符串异常，异常信息为：{}",e);
            return templateParam;

        }

    }


    /**
     *短信发送记录
     * @param
     * @param
      */
    private void insertSmsRecord(AliSmsSendReq req,AliSendSmsResp resp) {

        try{
            GatewaySmsRecord record = new GatewaySmsRecord();
            record.setPhone(req.getPhoneNumber());
            record.setTemplateCode(req.getTemplateCode());
            record.setTemplateParam(req.getTemplateParam());
            record.setMsg(resp.getDesc());
            record.setStatus(resp.getCode());
            record.setCreateTime(new Date());
            record.setTemplateContent(AliSmsTemplateEnums.getDesc(req.getTemplateCode()));
            gatewaySmsRecordDao.insert(record);

        }catch (Exception e){
            logger.error("插入短信记录表异常，异常信息为：{}",e);
        }




    }


}
