package com._360pai.gateway.common.wxpay;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPay;

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

        WxPayConfig config = new WxPayConfig(null,null);

        WXPay wxpay = new WXPay(config);

        //设置请求参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", "微信支付测试");
        data.put("out_trade_no", "NO_11010010101152");
        data.put("device_info", "");
        data.put("fee_type", "CNY");
        data.put("total_fee", "1500000");
        data.put("spbill_create_ip", "192.168.1.34");
        data.put("notify_url", "http://wuchuanqi.free.idcfengye.com/wxpay/payNotifyUrl");
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        data.put("product_id", "12");

            //发起支付
            Map<String, String> resp = wxpay.unifiedOrder(data);
            //获取二维码URL
            String code_url = resp.get("code_url");

            System.out.print(JSON.toJSONString(resp));


            System.out.print( wxpay.isResponseSignatureValid(resp));

            //返回二维码

         } catch (Exception e) {
            e.printStackTrace();
        }




    }
}
