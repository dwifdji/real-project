package com.winback.gateway.common.alipay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.github.wxpay.sdk.WXPay;
import com.winback.gateway.common.wxpay.WxPayConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/27 17:58
 */
public class AliPayTest {

    public static void main(String[] args) throws Exception {
        AlipayTradeCreateResponse aliPayResponse = new AlipayTradeCreateResponse ();

        AlipayTradeCreateRequest model = new AlipayTradeCreateRequest ();



        AlipayTradeCreateRequest  aliPayRequest = new AlipayTradeCreateRequest ();
        aliPayRequest.setBizContent("{" +
                "\"out_trade_no\":\"20150320010101001\"," +
                "\"seller_id\":\"2088102146225135\"," +
                "\"total_amount\":88.88," +
                "\"discountable_amount\":8.88," +
                "\"subject\":\"Iphone6 16G\"," +
                "\"body\":\"Iphone6 16G\"," +
                "\"buyer_id\":\"2088102146225135\"," +
                "      \"goods_detail\":[{" +
                "        \"goods_id\":\"apple-01\"," +
                "\"goods_name\":\"ipad\"," +
                "\"quantity\":1," +
                "\"price\":2000," +
                "\"goods_category\":\"34543238\"," +
                "\"categories_tree\":\"124868003|126232002|126252004\"," +
                "\"body\":\"特价手机\"," +
                "\"show_url\":\"http://www.alipay.com/xxx.jpg\"" +
                "        }]," +
                "\"operator_id\":\"Yx_001\"," +
                "\"store_id\":\"NJ_001\"," +
                "\"terminal_id\":\"NJ_T_001\"," +
                "\"extend_params\":{" +
                "\"sys_service_provider_id\":\"2088511833207846\"," +
                "\"industry_reflux_info\":\"{\\\\\\\"scene_code\\\\\\\":\\\\\\\"metro_tradeorder\\\\\\\",\\\\\\\"channel\\\\\\\":\\\\\\\"xxxx\\\\\\\",\\\\\\\"scene_data\\\\\\\":{\\\\\\\"asset_name\\\\\\\":\\\\\\\"ALIPAY\\\\\\\"}}\"," +
                "\"card_type\":\"S0JP0000\"" +
                "    }," +
                "\"timeout_express\":\"90m\"," +
                "\"settle_info\":{" +
                "        \"settle_detail_infos\":[{" +
                "          \"trans_in_type\":\"cardSerialNo\"," +
                "\"trans_in\":\"A0001\"," +
                "\"summary_dimension\":\"A0001\"," +
                "\"settle_entity_id\":\"2088xxxxx;ST_0001\"," +
                "\"settle_entity_type\":\"SecondMerchant、Store\"," +
                "\"amount\":0.1" +
                "          }]" +
                "    }," +
                "\"logistics_detail\":{" +
                "\"logistics_type\":\"EXPRESS\"" +
                "    }," +
                "\"business_params\":\"{\\\"data\\\":\\\"123\\\"}\"," +
                "\"receiver_address_info\":{" +
                "\"name\":\"张三\"," +
                "\"address\":\"上海市浦东新区陆家嘴银城中路501号\"," +
                "\"mobile\":\"13120180615\"," +
                "\"zip\":\"200120\"," +
                "\"division_code\":\"310115\"" +
                "    }" +
                "  }");
            aliPayRequest.setReturnUrl("");
            aliPayRequest.setNotifyUrl("");

            //获取支付宝客户端
            AlipayClient alipayClient = AliPayClientFactory.getAliPayClient(null);

            aliPayResponse = alipayClient.execute(aliPayRequest);




            System.out.print(JSON.toJSONString(aliPayResponse));


    }
}
