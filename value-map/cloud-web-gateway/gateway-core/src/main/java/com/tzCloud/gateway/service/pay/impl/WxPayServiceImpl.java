package com.tzCloud.gateway.service.pay.impl;


import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPay;
import com.tzCloud.arch.common.utils.DateUtil;
import com.tzCloud.arch.core.sysconfig.properties.GatewayProperties;
import com.tzCloud.gateway.common.constants.PayEnum;
import com.tzCloud.gateway.common.constants.PayEnums;
import com.tzCloud.gateway.common.constants.PayResultEnums;
import com.tzCloud.gateway.common.wxpay.WxPayConfig;
import com.tzCloud.gateway.common.wxpay.WxPayResultEnums;
import com.tzCloud.gateway.controller.req.wxpay.ScanPayReq;
import com.tzCloud.gateway.dao.pay.TGatewayPayRecordDao;
import com.tzCloud.gateway.model.pay.TGatewayPayRecord;
import com.tzCloud.gateway.resp.wxpay.ScanPayResp;
import com.tzCloud.gateway.service.pay.WxPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private TGatewayPayRecordDao gatewayPayRecordDao;


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

            saveRecord(payParam,resp, PayEnums.PAY_TYPE.SCAN_PAY.getType());


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
     *保存请求记录
     */
    private void saveRecord(Map<String,String> payParam, Map<String,String> resp,String payType) {

        try {

            TGatewayPayRecord record = new TGatewayPayRecord();

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
    public ScanPayResp queryScanPayResult(String outTradeNo, String channel) {

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

}
