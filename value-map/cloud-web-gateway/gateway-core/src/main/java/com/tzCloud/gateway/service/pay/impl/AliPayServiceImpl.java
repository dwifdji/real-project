package com.tzCloud.gateway.service.pay.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.arch.common.utils.DateUtil;
import com.tzCloud.gateway.dao.pay.TGatewayPayRecordDao;
import com.tzCloud.gateway.model.pay.TGatewayPayRecord;
import com.tzCloud.gateway.service.pay.AliPayService;
import com.tzCloud.arch.core.sysconfig.properties.GatewayProperties;
import com.tzCloud.gateway.common.alipay.AliPayClientFactory;
import com.tzCloud.gateway.common.constants.PayEnum;
import com.tzCloud.gateway.common.constants.PayResultEnums;
import com.tzCloud.gateway.dao.pay.TGatewayPayRecordDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述：支付宝接口实现
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/16 10:00
 */
@Slf4j
@Service
public class AliPayServiceImpl implements AliPayService {

    @Autowired
    private GatewayProperties gatewayProperties;


    @Autowired
    private TGatewayPayRecordDao gatewayPayRecordDao;


    @Override
    public AlipayTradeAppPayResponse aliPayTradeAppPay(AlipayTradeAppPayModel model) {

        log.info("开始请求支付宝接口，请求参数为：{}", JSON.toJSONString(model));

        AlipayTradeAppPayResponse aliPayResponse = new AlipayTradeAppPayResponse();

        try {
            AlipayTradeAppPayRequest aliPayRequest = new AlipayTradeAppPayRequest();
            aliPayRequest.setBizModel(model);
            aliPayRequest.setReturnUrl(gatewayProperties.getAliPayReturnUrl());
            aliPayRequest.setNotifyUrl(gatewayProperties.getAliPayNotifyUrl());

            //获取支付宝客户端
            AlipayClient alipayClient = AliPayClientFactory.getAliPayClient(gatewayProperties);

            aliPayResponse = alipayClient.sdkExecute(aliPayRequest);
            aliPayResponse.setCode(PayResultEnums.PAY_SUCCESS.getCode());
            aliPayResponse.setMsg(PayResultEnums.PAY_SUCCESS.getDesc());
        }catch (Exception e){

            log.error("支付宝支付异常，异常信息为：{}",e);
            aliPayResponse.setCode(PayResultEnums.SYS_EXCEPTION.getCode());
            aliPayResponse.setMsg(PayResultEnums.SYS_EXCEPTION.getDesc());
        }

        saveRecord(model,aliPayResponse);

        return aliPayResponse;
    }


    @Override
    public AlipayTradeQueryResponse aliPayQuery(AlipayTradeQueryModel model) {

        AlipayTradeQueryResponse queryResponse = new AlipayTradeQueryResponse();

        try {
            //初始化请求类
            AlipayTradeQueryRequest aliQueryRequest = new AlipayTradeQueryRequest();
            //设置业务参数，alipayModel为前端发送的请求信息，开发者需要根据实际情况填充此类
            aliQueryRequest.setBizModel(model);
            aliQueryRequest.setReturnUrl(gatewayProperties.getAliPayReturnUrl());
            aliQueryRequest.setNotifyUrl(gatewayProperties.getAliPayNotifyUrl());

            //获取支付宝客户端
            AlipayClient alipayClient = AliPayClientFactory.getAliPayClient(gatewayProperties);

            queryResponse = alipayClient.sdkExecute(aliQueryRequest);

        }catch (Exception e){

            log.error("支付宝查询异常，异常信息为：{}",e);
            queryResponse.setCode(ApiCallResult.EXCEPTION.getCode());
            queryResponse.setMsg(ApiCallResult.EXCEPTION.getDesc());
        }

        //saveRecord(model,queryResponse);

        return queryResponse;


    }

    //保存支付宝请求记录
    private void saveRecord(AlipayTradeAppPayModel model, AlipayTradeAppPayResponse aliPayResponse) {


        try {

            TGatewayPayRecord  record = new TGatewayPayRecord();

            record.setBusId(model.getOutTradeNo());
            record.setRequestParam(JSON.toJSONString(model));
            record.setResponseParam(JSON.toJSONString(aliPayResponse));
            record.setCreateTime(DateUtil.getSysTime());
            record.setPayType(PayEnum.PAY_TYPE.ALI_PAY.getType());

            gatewayPayRecordDao.insert(record);
        }catch (Exception e){
            log.error("保存支付宝请求记录异常，异常信息为：{}",e);
        }


    }
}
