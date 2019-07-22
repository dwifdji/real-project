package com._360pai.gateway.common.dfftpay;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.easterpay.util.EastPayUtil;
import com.itrus.cvm.CVM;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com._360pai.gateway.common.dfftpay.MapRemoveNullUtil.removeNullValue;

/**
 * 描述：东方付通调用工具类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 16:30
 */
public class DfftPayUtils {

    private  static final Logger logger = LoggerFactory.getLogger(DfftPayUtils.class);


    /**
     * 后台类  查询交易签名验签测试
     */
    public static ResponseModel requestPay(GatewayProperties gatewayProperties,Map<String,String> map) throws Exception{


            String filePath = DfftPayUtils.class.getClassLoader().getResource(gatewayProperties.getPayFileName()).getPath();//获取

            CVM.config(filePath);


            ResponseModel sign = sign(gatewayProperties,map,"single");

            if(!sign.getCode().equals(PayResultEnums.REQUEST_SUCCESS.getCode())){

                return sign;
            }
            if("mock".equals(gatewayProperties.getIsMock())&&gatewayProperties.getPayUrl().contains("uat.easternpay.com.cn")){

                String responseJson = HttpUt.retrieveResponseFromServer(gatewayProperties.getMockUrl(), "order="+JSON.toJSONString(map));

                return  new ResponseModel(PayResultEnums.REQUEST_SUCCESS.getCode(),PayResultEnums.REQUEST_SUCCESS.getDesc(),responseJson);

             }

            String requestJson = sign.getContent().toString();


            String responseJson = HttpUt.retrieveResponseFromServer(gatewayProperties.getPayUrl(), "order="+requestJson);

            //当为查询的时候返回格式要处理
            if("05011".equals(map.get("payType"))){

                try {
                    JSONArray jsonArray = JSON.parseArray(responseJson);

                    responseJson =jsonArray.get(0).toString();
                }catch (Exception e){

                    logger.info("查询返回不为JSONArray格式，返回参数为：{}",responseJson);
                }


            }

            logger.info("结束请求东方付通，返回参数：{}",responseJson);

             if(!responseJson.startsWith("{") && !responseJson.endsWith("}")){
                 logger.error("东方付通返回页面信息，返回参数：{}",responseJson);
                 //获取页面返回错误码

                 String  code = getWebRespCode(responseJson);
                 if(!StringUtils.isEmpty(code)){

                     String desc =PayResultEnums.getDesc(code);

                     if(!StringUtils.isEmpty(desc)){
                         return new ResponseModel(code,desc);
                     }
                 }

                 return new ResponseModel(PayResultEnums.WEB_RESP_ERROR.getCode(),PayResultEnums.WEB_RESP_ERROR.getDesc());

            }
            ObjectMapper objectMapper = new ObjectMapper();

            map = objectMapper.readValue(responseJson, Map.class);
            String signature = map.get("signature");

            //校验签章结果
            if(!EastPayUtil.verifySignedDataByDfft(responseJson, signature)){

                logger.error("东方付通返回验签失败，返回参数：{}",responseJson);


                return new ResponseModel(PayResultEnums.SIGN_ERROR.getCode(),PayResultEnums.SIGN_ERROR.getDesc());

            }


            return  new ResponseModel(PayResultEnums.REQUEST_SUCCESS.getCode(),PayResultEnums.REQUEST_SUCCESS.getDesc(),responseJson);


    }

    /**
     *
     *签名
     * @param
     * @param
     */
    public static ResponseModel sign(GatewayProperties gatewayProperties,Map<String, String> map,String type) throws Exception{


            String filePath = DfftPayUtils.class.getClassLoader().getResource(gatewayProperties.getPayFileName()).getPath();//获取

            CVM.config(filePath);

            map.put("mallID", gatewayProperties.getPayMallID());

            removeNullValue(map);

            ObjectMapper objectMapper = new ObjectMapper();

            String json = objectMapper.writeValueAsString(map);

            String path = DfftPayUtils.class.getClassLoader().getResource(gatewayProperties.getPayPath()).getPath();//获取

            String signature = EastPayUtil.signDataDetached(path, gatewayProperties.getPayPassword(), json);




            if(signature == null || "".equals(signature) || signature.contains("初始化失败")){

                logger.error("验签失败,验签参数：{}",json);

                return new ResponseModel(PayResultEnums.REQ_SIGN_ERROR.getCode(),PayResultEnums.REQ_SIGN_ERROR.getDesc());
            }
            map.put("signature", signature);



            if("mock".equals(gatewayProperties.getIsMock())&&gatewayProperties.getPayUrl().contains("uat.easternpay.com.cn")&&"01010".equals(map.get("payType"))){

                String responseJson = HttpUt.retrieveResponseFromServer(gatewayProperties.getMockUrl(), "order="+JSON.toJSONString(map));

                System.out.print(responseJson);
            }


            if("batch".equals(type)){

                return new ResponseModel(PayResultEnums.REQUEST_SUCCESS.getCode(),PayResultEnums.REQUEST_SUCCESS.getDesc(),map);
            }

            String requestJson = objectMapper.writeValueAsString(map);

            logger.info("开始请求东方付通，请求参数：{}",requestJson);

             requestJson =URLEncoder.encode(EastPayUtil.encodebase64(requestJson),"UTF-8");


            return  new ResponseModel(PayResultEnums.REQUEST_SUCCESS.getCode(),PayResultEnums.REQUEST_SUCCESS.getDesc(),requestJson);





  }






        /**
         *
         *正则表达式解析页面返回参数
         * @param responseJson 返回参数
           */
    private static String getWebRespCode(String responseJson) {

        try{
            //正则表达式提取错误码
            String regex = "\\d{6}";
            Pattern pa = Pattern.compile(regex);
            Matcher ma = pa.matcher(responseJson);
            if (ma.find()) {

                return  ma.group(0);
             }

         }catch (Exception e){
            logger.error("解析网页返回错误码异常，异常信息为：{}",e);
            return null;
        }
        return null;
    }

}
