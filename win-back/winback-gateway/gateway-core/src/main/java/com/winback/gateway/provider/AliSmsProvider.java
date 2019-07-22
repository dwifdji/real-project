package com.winback.gateway.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.common.enums.EmailEnum;
import com.winback.arch.common.utils.ExceptionEmailUtil;
import com.winback.arch.core.sysconfig.properties.GatewayProperties;
import com.winback.gateway.service.sms.SmsService;
import com.winback.gateway.controller.req.alisms.FAliSmsSendReq;
import com.winback.gateway.facade.AliSmsFacade;
import com.winback.gateway.resp.AliSendSmsResp;
import com.winback.gateway.service.sms.SmsService;
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
public class AliSmsProvider implements AliSmsFacade {


    @Autowired
    private SmsService smsService;

    @Autowired
    private GatewayProperties  gatewayProperties;


    @Override
    public AliSendSmsResp sendSms(FAliSmsSendReq freq) {

        AliSendSmsResp resp = new AliSendSmsResp();

        FAliSmsSendReq req = new FAliSmsSendReq();
        req.setPhoneNumber(freq.getPhoneNumber());
        req.setTemplateCode(freq.getTemplateCode());
        req.setTemplateParam(freq.getTemplateParam());
        req.setPartyId(freq.getPartyId());
        ResponseModel responseModel = new ResponseModel();
        try {

            //判断短信路由 添加创蓝短信接入
            responseModel = "cLan".equals(gatewayProperties.getSmsRoute())?smsService.send235Sms(req):smsService.sendSms(req);

        }catch (Exception e){
            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.SMS, JSON.toJSONString(req),"发送邮件异常", null,e);
            responseModel.setCode(ApiCallResult.EXCEPTION.getCode());
            responseModel.setCode(ApiCallResult.EXCEPTION.getDesc());

        }

        resp.setCode(responseModel.getCode());
        resp.setDesc(responseModel.getDesc());
        //短信发送不成功提示
        if(!ApiCallResult.SUCCESS.getCode().equals(resp.getCode())){
            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.IGNORE,EmailEnum.MODULE_TYPE.SMS, JSON.toJSONString(req),"短信发送不成功", JSON.toJSONString(resp),null);

        }

        return resp;
    }

}
