package com._360pai.core.service.impl;


import com._360pai.arch.common.HttpUtils;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.aspact.GatewayExceptionEmailService;
import com._360pai.core.common.constants.ExceptionEmailEnum;
import com._360pai.core.dao.pay.GatewayPayRecordDao;
import com._360pai.core.model.pay.GatewayPayRecord;
import com._360pai.core.service.WxPayService;
import com._360pai.gateway.common.dfftpay.PayEnums;
import com._360pai.gateway.common.dfftpay.PayResultEnums;
import com._360pai.gateway.common.wxpay.WxAppletPayConfig;
import com._360pai.gateway.common.wxpay.WxPayConfig;
import com._360pai.gateway.common.wxpay.WxPayResultEnums;
import com._360pai.gateway.controller.req.wxpay.AppletPayReq;
import com._360pai.gateway.controller.req.wxpay.ScanPayReq;
import com._360pai.gateway.controller.req.wxpay.ScanResultNoticeReq;
import com._360pai.gateway.resp.wx.OpenIdResp;
import com._360pai.gateway.resp.wxpay.AppletPayResp;
import com._360pai.gateway.resp.wxpay.ScanPayResp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private  GatewayProperties gatewayProperties;


    @Autowired
    private GatewayPayRecordDao gatewayPayRecordDao;


    @Autowired
    private GatewayExceptionEmailService gatewayExceptionEmailService;


    /**
     *
     * 扫码支付
     */
    @Override
    public ScanPayResp scanPay(ScanPayReq req) {


        ScanPayResp scanPayResp = new ScanPayResp();

        try {
            WXPay wxpay = getWXPay(null);

            if(wxpay==null){
                return ScanPayResp.failure(PayResultEnums.INIT_ERROR.getCode(),PayResultEnums.INIT_ERROR.getDesc());
            }

            //组装请求参数
            Map<String, String> payParam = getPayParam(req);

            logger.info("发起微信支付请求，请求参数为：{}",JSON.toJSONString(payParam));

            //发起支付
            Map<String, String> resp = wxpay.unifiedOrder(payParam);

            logger.info("微信支付返回参数为：{}",JSON.toJSONString(resp));

            saveRecord(payParam,resp,PayEnums.PAY_TYPE.SCAN_PAY.getType());


            //解析返回参数
            scanPayResp = getScanPayResp(resp,wxpay,"1");


        } catch (Exception e) {

            gatewayExceptionEmailService.sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.PAY,"微信扫码支付异常",JSON.toJSONString(req),e);

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

            GatewayPayRecord record = new GatewayPayRecord();

            record.setResponseParam(JSON.toJSONString(resp));
            record.setRequestParam(JSON.toJSONString(payParam));
            record.setPayType(payType);
            record.setResponseCode(resp.get("return_code"));
            record.setCreateTime(DateUtil.getSysTime());
            record.setBusId(payParam.get("product_id"));

            gatewayPayRecordDao.insert(record);

        }catch (Exception e){

            logger.error("保存微信记录异常，异常信息为：{}",e);
        }

    }


    private WXPay getWXPay(String channel) {

        try {
            //初始化环境参数
            WxPayConfig config = new WxPayConfig(gatewayProperties,channel);

            WXPay wxpay = new WXPay(config);

            return wxpay;
        }catch (Exception e){

            logger.error("初始化微信支付参数异常，异常信息为:{}",e);

             return null;
         }

    }



    private WXPay getWXAppletPay(AppletPayReq req) {

        try {
            //初始化环境参数
            WxAppletPayConfig config = new WxAppletPayConfig(gatewayProperties,req.getChannel());

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
    private ScanPayResp getScanPayResp(Map<String,String> resp,WXPay wxpay,String type ) throws Exception {
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

                    String payCode = PayEnums.PAY_QUERY_STATUS.getDesc(tradeState);
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
    public ScanPayResp queryScanPayResult(String outTradeNo,String channel) {

        ScanPayResp resp = new ScanPayResp();
        try {
            WXPay wxpay = getWXPay(channel);

            Map<String,String> map = getQueryMap(outTradeNo);

            Map<String, String> respMap = wxpay.orderQuery(map);

            resp=getScanPayResp(respMap,wxpay,"2");

        }catch (Exception e){

            gatewayExceptionEmailService.sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.PAY,"微信扫码支付查询",outTradeNo,e);


            logger.error("查询微信订单异常，异常信息为{}",e);
            resp.setCode(PayResultEnums.SYS_EXCEPTION.getCode());
            resp.setDesc(PayResultEnums.SYS_EXCEPTION.getDesc());
        }

        return resp;
    }

    /**
     *获取微信openid
     */
    @Override
    public OpenIdResp getWxOpenId(String jsCode,String channel) {

        String appId = SystemConstant.WX_CHANNEL_KEY.equals(channel)?gatewayProperties.getCalculatorAppId():gatewayProperties.getAppletAppId();

        String appSecret =SystemConstant.WX_CHANNEL_KEY.equals(channel)?gatewayProperties.getCalculatorAppSecret():gatewayProperties.getAppletAppSecret();

        String openIdUrl = gatewayProperties.getAppletOpenIdUrl();

        OpenIdResp resp = new OpenIdResp();
        StringBuffer param = new StringBuffer();

        try {
            param.append("appid=");
            param.append(appId);
            param.append("&secret=");
            param.append(appSecret);
            param.append("&js_code=");
            param.append(jsCode);
            param.append("&grant_type=");
            param.append("authorization_code");
            String wxResp = HttpUtils.sendGet(openIdUrl,param.toString());
            logger.info("get openid={}", wxResp);
            resp = getResp(wxResp);

        }catch (Exception e){
            resp.setCode(ApiCallResult.EXCEPTION.getCode());
            resp.setCode(ApiCallResult.EXCEPTION.getDesc());

            gatewayExceptionEmailService.sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.USER,"获取用户openid异常",JSON.toJSONString(param),e);

            logger.error("查询微信openid异常，异常信息为：{}",e);
        }
        return resp;
    }

    private OpenIdResp getResp(String wxResp) {

        OpenIdResp resp = new OpenIdResp();

        JSONObject jsonObject = JSONObject.parseObject(wxResp);

        String openid = jsonObject.getString("openid");

        if(StringUtils.isNotEmpty(openid)){
            resp.setCode(ApiCallResult.SUCCESS.getCode());
            resp.setDesc(ApiCallResult.SUCCESS.getDesc());
            resp.setOpenId(jsonObject.getString("openid"));
            resp.setSessionKey(jsonObject.getString("session_key"));
            resp.setUnionId(jsonObject.getString("unionid"));
        }else{
            resp.setCode(jsonObject.getString("errcode"));
            resp.setDesc(jsonObject.getString("errmsg"));
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
            WXPay wxpay = getWXAppletPay(req);

            if(wxpay==null){
                return AppletPayResp.failure(PayResultEnums.INIT_ERROR.getCode(),PayResultEnums.INIT_ERROR.getDesc());
            }

            //组装请求参数
            Map<String, String> payParam = getAppletPayParam(req);

            logger.info("微信小程序请求，请求参数为：{}",JSON.toJSONString(payParam));

            //发起支付
            Map<String, String> resp = wxpay.unifiedOrder(payParam);

            logger.info("微信小程序支付返回参数为：{}",JSON.toJSONString(resp));

            saveRecord(payParam,resp,req.getChannel());

            scanPayResp = getAppletResp(resp,wxpay);


        } catch (Exception e) {

            gatewayExceptionEmailService.sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.PAY,"微信扫码支付异常",JSON.toJSONString(req),e);

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
}
