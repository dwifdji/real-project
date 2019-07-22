package com.winback.gateway.service.pay.impl;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.winback.arch.common.enums.EmailEnum;
import com.winback.arch.common.utils.DateUtil;
import com.winback.arch.common.utils.ExceptionEmailUtil;
import com.winback.arch.core.sysconfig.properties.GatewayProperties;
import com.winback.gateway.dao.pay.TGatewayPayRecordDao;
import com.winback.gateway.model.pay.TGatewayPayRecord;
import com.winback.gateway.service.pay.WxPayService;
import com.winback.gateway.common.constants.PayEnum;
import com.winback.gateway.common.constants.PayResultEnums;
import com.winback.gateway.common.wxpay.*;
import com.winback.gateway.controller.req.wxpay.AppPayReq;
import com.winback.gateway.controller.req.wxpay.AppletPayReq;
import com.winback.gateway.controller.req.wxpay.ScanPayReq;
import com.winback.gateway.controller.req.wxpay.ScanResultNoticeReq;
import com.winback.gateway.dao.pay.TGatewayPayRecordDao;
import com.winback.gateway.model.pay.TGatewayPayRecord;
import com.winback.gateway.resp.wxpay.AppPayResp;
import com.winback.gateway.resp.wxpay.AppletPayResp;
import com.winback.gateway.resp.wxpay.ScanPayResp;
import com.winback.gateway.service.pay.WxPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 描述：微信支付服务实现类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/28 11:03
 */
@Service
public class WxPayServiceImpl implements WxPayService {

    private  final Logger logger = LoggerFactory.getLogger(WxPayServiceImpl.class);

    @Autowired
    private GatewayProperties gatewayProperties;


    @Autowired
    private TGatewayPayRecordDao gatewayPayRecordDao;


    /**
     *
     * 扫码支付
     */
    @Override
    public ScanPayResp scanPay(ScanPayReq req) {


        ScanPayResp scanPayResp = new ScanPayResp();

        try {
            WXPay wxpay = getWXPay();

            if(wxpay==null){
                return ScanPayResp.failure(PayResultEnums.INIT_ERROR.getCode(),PayResultEnums.INIT_ERROR.getDesc());
            }

            //组装请求参数
            Map<String, String> payParam = getPayParam(req);

            logger.info("发起微信支付请求，请求参数为：{}",JSON.toJSONString(payParam));

            //发起支付
            Map<String, String> resp = wxpay.unifiedOrder(payParam);

            logger.info("微信支付返回参数为：{}",JSON.toJSONString(resp));

            saveRecord(payParam,resp,PayEnum.BUSINESS_TYPE.SCAN_PAY.getType());


            //解析返回参数
            scanPayResp = getScanPayResp(resp,wxpay,"1");


        } catch (Exception e) {


            scanPayResp.setCode(PayResultEnums.SYS_EXCEPTION.getCode());
            scanPayResp.setDesc(PayResultEnums.SYS_EXCEPTION.getDesc());
        }

        return scanPayResp;
    }

    /**
     *
     *保存请求记录
     */
    private void saveRecord(Map<String,String> payParam, Map<String,String> resp,String payType) {

        try {

            TGatewayPayRecord record = new TGatewayPayRecord();

            record.setRespParam(JSON.toJSONString(resp));
            record.setReqParam(JSON.toJSONString(payParam));
            record.setType(PayEnum.PAY_TYPE.WX_PAY.getType());
            record.setBusinessType(payType);
            record.setCreateTime(DateUtil.getSysTime());
            record.setOrderNo(payParam.get("product_id"));

            gatewayPayRecordDao.insert(record);

        }catch (Exception e){

            logger.error("保存微信记录异常，异常信息为：{}",e);
        }

    }


    private WXPay getWXPay() {

        try {
            //初始化环境参数
            WxPayConfig config = new WxPayConfig(gatewayProperties);

            WXPay wxpay = new WXPay(config);

            return wxpay;
        }catch (Exception e){

            logger.error("初始化微信支付参数异常，异常信息为:{}",e);

             return null;
         }

    }



    private WXPay getWXAppletPay() {

        try {
            //初始化环境参数
            WxAppletPayConfig config = new WxAppletPayConfig(gatewayProperties);

            WXPay wxpay = new WXPay(config);

            return wxpay;
        }catch (Exception e){

            logger.error("初始化微信支付参数异常，异常信息为:{}",e);

            return null;
        }

    }

    /**
     *
     *解析返回参数
     */
    private ScanPayResp getScanPayResp(Map<String,String> resp, WXPay wxpay, String type ) throws Exception {
        ScanPayResp scanPayResp = new ScanPayResp();
        //请求响应成功
        if(WxPayResultEnums.RETURN_CODE_SUCCESS.getCode().equals(resp.get("return_code"))){
            //返回验签
            if(!wxpay.isResponseSignatureValid(resp)){
                scanPayResp.setCode(PayResultEnums.RESP_SIGN_ERROR.getCode());
                scanPayResp.setDesc(PayResultEnums.RESP_SIGN_ERROR.getDesc());
                return scanPayResp;
            }
            //业务请求响应成功
            if(WxPayResultEnums.RETURN_CODE_SUCCESS.getCode().equals(resp.get("result_code"))){
                scanPayResp.setCode(PayResultEnums.PAY_NOTICE.getCode());
                scanPayResp.setDesc(PayResultEnums.PAY_NOTICE.getDesc());
                scanPayResp.setCode_url(resp.get("code_url"));
                if(type.equals("2")){

                    String tradeState = resp.get("trade_state");
                    String payCode = PayEnum.PAY_QUERY_STATUS.getDesc(tradeState);
                    scanPayResp.setCode(payCode);
                    scanPayResp.setDesc(PayResultEnums.getDesc(payCode));
                }
            }else{
                scanPayResp.setCode(PayResultEnums.BUS_ERROR.getCode());
                scanPayResp.setDesc(resp.get("err_code_des"));
            }
        }else{
            scanPayResp.setCode(PayResultEnums.BUS_ERROR.getCode());
            scanPayResp.setDesc(resp.get("return_msg"));

        }

        return scanPayResp;
    }

    /**
     *
     *组装请求参数
     */
    private Map<String,String> getPayParam(ScanPayReq req) {

        //设置请求参数
        Map<String, String> payParam = new HashMap<String, String>();
        payParam.put("body", req.getBody());
        payParam.put("out_trade_no", req.getOut_trade_no());
        payParam.put("fee_type", "CNY");
        payParam.put("total_fee", req.getTotal_fee());
        payParam.put("spbill_create_ip", "127.0.0.1");
        payParam.put("notify_url", gatewayProperties.getWxPayNotifyUrl());
        payParam.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        payParam.put("product_id", req.getProduct_id());
        payParam.put("nonce_str", UUID.randomUUID().toString().replaceAll("-",""));

        return payParam;
    }



    @Override
    public ScanPayResp queryScanPayResult(String outTradeNo) {

        ScanPayResp resp = new ScanPayResp();
        try {
            WXPay wxpay = getWXPay();

            Map<String,String> map = getQueryMap(outTradeNo);

            Map<String, String> respMap = wxpay.orderQuery(map);

            resp=getScanPayResp(respMap,wxpay,"2");

        }catch (Exception e){



            logger.error("查询微信订单异常，异常信息为{}",e);
            resp.setCode(PayResultEnums.SYS_EXCEPTION.getCode());
            resp.setDesc(PayResultEnums.SYS_EXCEPTION.getDesc());
        }

        return resp;
    }



    private Map<String,String> getQueryMap(String outTradeNo) {
        Map<String,String> queryMap = new HashMap<>();

        queryMap.put("out_trade_no",outTradeNo);

        return  queryMap;


    }

    private Map<String,String> getQueryParam(ScanResultNoticeReq req) {

        Map<String,String> queryParam = new HashMap<>();




        return queryParam;

    }


    /**
     * 获取服务器地址
     *
     * @return Ip地址
     */
    public String getServerIp() {
        // 获取操作系统类型
        String sysType = System.getProperties().getProperty("os.name");
        String ip;
        if (sysType.toLowerCase().startsWith("win")) {  // 如果是Windows系统，获取本地IP地址
            String localIP = null;
            try {
                localIP = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {

            }
            if (localIP != null) {
                return localIP;
            }
        } else {
            ip = getIpByEthNum("eth0"); // 兼容Linux
            if (ip != null) {
                return ip;
            }
        }
        return "获取服务器IP错误";
    }

    /**
     * 根据网络接口获取IP地址
     * @param ethNum 网络接口名，Linux下是eth0
     * @return
     */
    private String getIpByEthNum(String ethNum) {
        try {
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (ethNum.equals(netInterface.getName())) {
                    Enumeration addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = (InetAddress) addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            logger.error("获取服务器IP异常，异常信息为：{}", e);
        }
        return "获取服务器IP错误";
    }


    /**
     *
     *微信小程序支付
     */
    @Override
    public AppletPayResp appletPay(AppletPayReq req) {

        AppletPayResp scanPayResp = new AppletPayResp();

        try {
            WXPay wxpay = getWXAppletPay();

            if(wxpay==null){
                return AppletPayResp.failure(PayResultEnums.INIT_ERROR.getCode(),PayResultEnums.INIT_ERROR.getDesc());
            }

            //组装请求参数
            Map<String, String> payParam = getAppletPayParam(req);

            logger.info("微信小程序请求，请求参数为：{}",JSON.toJSONString(payParam));

            //发起支付
            Map<String, String> resp = wxpay.unifiedOrder(payParam);

            logger.info("微信小程序支付返回参数为：{}",JSON.toJSONString(resp));

            saveRecord(payParam,resp,PayEnum.BUSINESS_TYPE.APPLET_PAY.getType());

            scanPayResp = getAppletResp(resp,wxpay);


        } catch (Exception e) {

            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.PAY, JSON.toJSONString(req),"微信小程序支付异常", null,e);

            scanPayResp.setCode(PayResultEnums.SYS_EXCEPTION.getCode());
            scanPayResp.setDesc(PayResultEnums.SYS_EXCEPTION.getDesc());
        }

        return scanPayResp;


     }

    private AppletPayResp getAppletResp(Map<String,String> resp,WXPay wxpay) throws Exception{

        AppletPayResp scanPayResp = new AppletPayResp();
        //请求响应成功
        if(WxPayResultEnums.RETURN_CODE_SUCCESS.getCode().equals(resp.get("return_code"))){
            //返回验签
            if(!wxpay.isResponseSignatureValid(resp)){
                return AppletPayResp.failure(PayResultEnums.RESP_SIGN_ERROR.getCode(),PayResultEnums.RESP_SIGN_ERROR.getDesc());
            }
            //业务请求响应成功
            if(WxPayResultEnums.RETURN_CODE_SUCCESS.getCode().equals(resp.get("result_code"))){
                scanPayResp.setCode(PayResultEnums.PAY_NOTICE.getCode());
                scanPayResp.setDesc(PayResultEnums.PAY_NOTICE.getDesc());
                scanPayResp.setPrepayId(resp.get("prepay_id"));

            }else{
                scanPayResp.setCode(PayResultEnums.BUS_ERROR.getCode());
                scanPayResp.setDesc(resp.get("err_code_des"));
            }
        }else{
            scanPayResp.setCode(PayResultEnums.BUS_ERROR.getCode());
            scanPayResp.setDesc(resp.get("return_msg"));

        }

        return scanPayResp;

     }

    /**
     *
     *获取微信小程序支付请求参数
     */
    private Map<String,String> getAppletPayParam(AppletPayReq req) {

        Map<String,String> payParam = new HashMap<>();


        payParam.put("body", req.getBody());
        payParam.put("out_trade_no", req.getOut_trade_no());
        payParam.put("fee_type", "CNY");
        payParam.put("total_fee", req.getTotal_fee());
        payParam.put("spbill_create_ip", "127.0.0.1");
        payParam.put("notify_url", gatewayProperties.getWxPayNotifyUrl());
        payParam.put("trade_type", "JSAPI");  // 此处指定为小程序支付
        payParam.put("product_id", req.getProduct_id());
        payParam.put("openid", req.getOpen_id());
        payParam.put("nonce_str", UUID.randomUUID().toString().replaceAll("-",""));


        return payParam;
    }


    @Override
    public AppPayResp appPay(AppPayReq appPayReq) {
        AppPayResp appPayResp = new AppPayResp();

        try{
            WXPay wxpay = getWxAppPayConfig(gatewayProperties);
            if(wxpay==null){
                return AppPayResp.failure(PayResultEnums.INIT_ERROR.getCode(),PayResultEnums.INIT_ERROR.getDesc());
            }
            
            //封装传参
            Map<String, String> params = getAppPayParams(appPayReq);
            logger.info("微信app支付预支付订单调用接口传参为{}", JSON.toJSONString(params));

            Map<String, String> result = wxpay.unifiedOrder(params);
            logger.info("微信app支付预支付订单调用接口返回参数为{}", JSON.toJSONString(result));

            //保存预支付订单接口
            saveRecord(params,result,PayEnum.BUSINESS_TYPE.APP_PAY.getType());

            //校验签名，并封装返回参数
            appPayResp = getAppPayResp(result, wxpay, null);
        }catch (Exception e){
            //异常邮件报警
            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.PAY, JSON.toJSONString(appPayReq),"微信支付异常", null,e);


            logger.info("微信app支付预支付订单调用接口异常{}", e);
        }
        return appPayResp;
    }

    @Override
    public AppPayResp queryAppPay(AppPayReq appPayReq) {

        AppPayResp resp = new AppPayResp();

        try {
            WXPay wxPay = getWxAppPayConfig(gatewayProperties);

            Map<String, String> params = new HashMap<>();
            params.put("out_trade_no", appPayReq.getOut_trade_no());
            logger.info("微信app支付查询订单的参数为{}", JSON.toJSONString(params));

            Map<String, String> result = wxPay.orderQuery(params);
            logger.info("微信app支付查询订单返回结果为{}", JSON.toJSONString(result));

            resp = getAppPayResp(result, wxPay, appPayReq.getOut_trade_no());
        } catch (Exception e) {
            resp.setCode(PayResultEnums.SYS_EXCEPTION.getCode());
            resp.setDesc(PayResultEnums.SYS_EXCEPTION.getDesc());
            //异常邮件报警
            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.PAY, JSON.toJSONString(appPayReq),"微信查询异常", null,e);
        }
        return resp;
    }


    private AppPayResp getAppPayResp(Map<String, String> result,  WXPay wxpay, String orderNum) throws Exception {
        String returnCode = result.get("return_code");
        if(WxPayResultEnums.RETURN_CODE_SUCCESS.getCode().equals(returnCode)) {
            //校验签名是否有效
            if(!wxpay.isResponseSignatureValid(result)) {
                return AppPayResp.failure(PayResultEnums.SIGN_ERROR.getCode(),result.get("return_msg"));
            }

            //返回下单结果或者是订单查询结果
            if(WxPayResultEnums.RETURN_CODE_SUCCESS.getCode().equals(result.get("result_code"))) {
                if(orderNum == null) {
                    return  AppPayResp.successful(PayResultEnums.PAY_NOTICE.getCode(),
                            PayResultEnums.PAY_NOTICE.getDesc(),result.get("prepay_id"));
                }else{
                    String tradeState = result.get("trade_state");
                    String payCode = PayEnum.PAY_QUERY_STATUS.getDesc(tradeState);

                    return AppPayResp.successfulQuery(payCode, PayResultEnums.getDesc(payCode));
                }
            }else {
                return AppPayResp.failure(PayResultEnums.BUS_ERROR.getCode(),result.get("err_code_des"));
            }

        }else{
            return AppPayResp.failure(PayResultEnums.BUS_ERROR.getCode(), result.get("return_msg"));
        }
    }


    private Map<String, String> getAppPayParams(AppPayReq appPayReq) {
        Map<String, String> params = new HashMap<>();
        params.put("body", appPayReq.getBody());
        params.put("out_trade_no", appPayReq.getOut_trade_no());
        params.put("fee_type", "CNY");
        params.put("total_fee", appPayReq.getTotal_fee());
        params.put("spbill_create_ip", "127.0.0.1");
        params.put("notify_url", gatewayProperties.getWxPayNotifyUrl()); //支付之后的回调函数
        params.put("trade_type", "APP");  // 类型为app支付
        params.put("product_id", appPayReq.getProduct_id());
        params.put("nonce_str", UUID.randomUUID().toString().replaceAll("-",""));
        return params;
    }

    /**
     * 获取微信支付工具对象
     * @param gatewayProperties
     * @return
     */
    private WXPay getWxAppPayConfig(GatewayProperties gatewayProperties) {
        try{
            WxAppPayConfig wxAppPayConfig = new WxAppPayConfig(gatewayProperties);
            WXPay wxPay = new WXPay(wxAppPayConfig);

            return wxPay;
        }catch (Exception e){


            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.PAY, JSON.toJSONString(gatewayProperties),"初始化微信支付异常", null,e);

            logger.error("初始化微信支付工具对象异常{}", e);
        }
        return null;
    }

}
