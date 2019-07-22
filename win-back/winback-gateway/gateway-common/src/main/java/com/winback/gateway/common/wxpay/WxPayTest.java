package com.winback.gateway.common.wxpay;

import com.alibaba.fastjson.JSON;
import com.winback.arch.common.HttpUtils;
import com.winback.gateway.common.alisms.SmsVariableRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/27 17:58
 */
public class WxPayTest {

    public static void main(String[] args) throws Exception {
        try {

         /*   String account = "N6555636";

            String password = "aneGvico2rd0e2";

            String smsSingleRequestServerUrl = "http://smssh1.253.com/msg/send/json";
            // 短信内容
            String msg = "【赢回来】您的验证码是1111111，请在5分钟内使用，切勿泄露。";
            //手机号码
            String phone = "15651617585";
            //状态报告
            String report= "true";

            SmsSendRequest smsSingleRequest = new SmsSendRequest(account, password, msg, phone,report);

            String requestJson = JSON.toJSONString(smsSingleRequest);



            String response = HttpUtils.sendSmsByPost(smsSingleRequestServerUrl, requestJson);



            System.out.println("before request string is: " + response);*/


            String sendUrl = "http://smssh1.253.com/msg/variable/json";

            String account = "N6555636";

            String password = "aneGvico2rd0e2";

            String msg = "【赢回来】您的验证码是{$var}，请在5分钟内使用，切勿泄露。";

            String params = "15651617585,11012";



            SmsVariableRequest smsVariableRequest=new SmsVariableRequest(account, password, msg, params, "true");

            String requestJson = JSON.toJSONString(smsVariableRequest);

            String  resp = HttpUtils.sendSmsByPost(sendUrl,requestJson);

            System.out.println("返回参数为："+resp);

         } catch (Exception e) {
            e.printStackTrace();
        }




    }
}
