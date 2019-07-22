package com._360pai;

import com._360pai.gateway.common.alisms.AliSmsTemplateEnums;
import com._360pai.gateway.controller.req.alisms.FAliSmsSendReq;
import com._360pai.gateway.facade.AliSmsFacade;
import com._360pai.gateway.resp.AliSendSmsResp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;


public class AliSmsTest extends BaseJunit {

    @Autowired
    private AliSmsFacade aliSmsFacade;




    /**
     *
     * 短信发送接口测试
     * @param
     * @param
     */
    @Test
    public void testSendAliSms() {

        JSONObject json = new JSONObject();
        json.put("personal_name","吴传奇吴传奇吴传奇吴传奇吴传奇吴传奇吴传奇吴传奇吴传奇吴传奇");

        FAliSmsSendReq req = new FAliSmsSendReq();

        req.setPhoneNumber("15651617585");
        req.setTemplateCode(AliSmsTemplateEnums.USER_APPLY__FOR_CUSTOMER_SERVICE.getCode());
        req.setTemplateParam(json.toJSONString());

        AliSendSmsResp resp = aliSmsFacade.sendSms(req);

        System.out.println(JSON.toJSONString(resp));
    }


    @Test
    public void FilterRichTextUtil() {


        String str = "91291212.1212";

        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");


        System.out.println("返回参数为："+pattern.matcher(str).matches());

    }


    private String formatStr(String str) {
        if(StringUtils.isEmpty(str)){
            return  "无";
        }

        if(str.contains(".00")){
            return str.substring(0, str.length() - 3);
        }

        return str;
    }



}
