package com.winback.gateway.service.sms.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.winback.arch.common.HttpUtils;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.core.sysconfig.properties.GatewayProperties;
import com.winback.gateway.common.alisms.SmsSendRequest;
import com.winback.gateway.common.alisms.SmsSendResponse;
import com.winback.gateway.dao.sms.TGatewaySmsRecordDao;
import com.winback.gateway.model.sms.TGatewaySmsRecord;
import com.winback.gateway.service.sms.SmsService;
import com.winback.gateway.common.alisms.AliSmsTemplateEnums;
import com.winback.gateway.common.alisms.AliSmsUtils;
import com.winback.gateway.controller.req.alisms.FAliSmsSendReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

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
            return respClose(req);
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


    /**
     *
     *创蓝短信
     */
    @Override
    public ResponseModel send235Sms(FAliSmsSendReq req) {

        //参数检查
        if(StringUtils.isEmpty(req.getPhoneNumber())||StringUtils.isEmpty(req.getTemplateCode())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

         //检查短信开关
        if("0".equals(gatewayProperties.getAliSmsOpen())){

            return respClose(req);
        }

        //根据模板code 查询模板信息
        String  template = AliSmsTemplateEnums.getTemplate(req.getTemplateCode());

        //模板参数替换
        if(StringUtils.isNotEmpty(req.getTemplateParam())){
            template = generateTemplate(JSONObject.parseObject(req.getTemplateParam()),template);
        }

        String sendMsg =gatewayProperties.getcLanSignName()+template;


        SmsSendRequest smsSingleRequest = new SmsSendRequest(gatewayProperties.getcLanAccount(), gatewayProperties.getcLanPassword(), sendMsg, req.getPhoneNumber(),"true");

        String requestJson = JSON.toJSONString(smsSingleRequest);

        String response = HttpUtils.sendSmsByPost(gatewayProperties.getcLanUrl(), requestJson);

        //解析返回参数
        ResponseModel resp = getResponseModel(response);


        //插入短信记录表
        new Thread(()->insertSmsRecord(req,resp)).start();

        return resp;
    }

    private ResponseModel getResponseModel(String response) {

        ResponseModel responseModel = new ResponseModel();

        SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);

        if("0".equals(smsSingleResponse.getCode())){
            return ResponseModel.succ();
        }

        responseModel.setCode(smsSingleResponse.getCode());
        responseModel.setDesc(smsSingleResponse.getErrorMsg());

        return responseModel;
    }

    private String generateTemplate(JSONObject param, String template){

        if(param==null){
            return template;
        }

        Map<String, Object> jsonMap = JSONObject.toJavaObject(param, Map.class);

        for (Object s : jsonMap.keySet()) {
            template = template.replaceAll("\\$\\{".concat(s.toString()).concat("\\}")
                    , jsonMap.get(s.toString()).toString());
        }
        return template;
    }







    private ResponseModel respClose(FAliSmsSendReq req) {
        ResponseModel resp = new ResponseModel();
        resp.setCode(ApiCallResult.SUCCESS.getCode());
        resp.setDesc("短信开关关闭！");
        new Thread(()->insertSmsRecord(req,resp)).start();
        return resp;

    }
}
