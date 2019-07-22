package com.winback.test;

import com.gexin.fastjson.JSON;
import com.winback.gateway.common.alisms.AliSmsTemplateEnums;
import com.winback.gateway.common.constants.PayEnum;
import com.winback.gateway.controller.req.alisms.FAliSmsSendReq;
import com.winback.gateway.controller.req.pay.PayReq;
import com.winback.gateway.controller.req.pay.QueryReq;
import com.winback.gateway.facade.AliSmsFacade;
import com.winback.gateway.facade.PayFacade;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * @author wuchuanqi
 * @create 2018-11-29 16:00
 */
public class SmsTest extends TestBase{

    @Resource
    private AliSmsFacade aliSmsFacade;

    /**
     * 微信app支付
     *
     */
    @Test
    public void sendSms() {


        FAliSmsSendReq req  = new FAliSmsSendReq();

        Map<String,String> smsParamMap = new HashMap<>();
        smsParamMap.put("code","123456");


        req.setPhoneNumber("15651617585");
        req.setTemplateCode(AliSmsTemplateEnums.LOGIN_CODE.getCode());
        req.setTemplateParam(JSON.toJSONString(smsParamMap));

        System.out.println("返回参数为....."+JSON.toJSONString(aliSmsFacade.sendSms(req)));
    }










}




