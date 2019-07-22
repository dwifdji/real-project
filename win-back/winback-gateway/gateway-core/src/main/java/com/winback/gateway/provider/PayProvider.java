package com.winback.gateway.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.gexin.fastjson.JSON;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.winback.arch.common.enums.EmailEnum;
import com.winback.arch.common.utils.DateUtil;
import com.winback.arch.common.utils.ExceptionEmailUtil;
import com.winback.arch.common.utils.RandomNumberGenerator;
import com.winback.arch.core.sysconfig.properties.GatewayProperties;
import com.winback.core.facade.contract.ContractFacade;
import com.winback.gateway.condition.pay.TGatewayPayOrderCondition;
import com.winback.gateway.model.pay.TGatewayPayOrder;
import com.winback.gateway.service.pay.AliPayService;
import com.winback.gateway.service.pay.PayOrderService;
import com.winback.gateway.service.pay.WxPayService;
import com.winback.gateway.common.constants.PayEnum;
import com.winback.gateway.common.constants.PayResultEnums;
import com.winback.gateway.controller.req.pay.*;
import com.winback.gateway.controller.req.wxpay.AppPayReq;
import com.winback.gateway.controller.req.wxpay.AppletPayReq;
import com.winback.gateway.facade.PayFacade;
import com.winback.gateway.resp.wxpay.AppPayResp;
import com.winback.gateway.resp.wxpay.AppletPayResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *统一支付接口
 */
@Slf4j
@Component
@Service(version = "1.0.0")
public class PayProvider implements PayFacade {

    @Autowired
    private WxPayService wxPayService;


    @Autowired
    private PayOrderService payOrderService;


    @Autowired
    private AliPayService aliPayService;

    @Autowired
    private GatewayProperties gatewayProperties;

    @Reference(version = "1.0.0")
    private ContractFacade contractFacade;


    @Override
    public PayResp unifyPay(PayReq req) {

        PayResp resp = new PayResp();

        log.info("请求统一支付接口，请求参数为:{}", JSON.toJSONString(req));

        //校验参数
        String errorMsg = checkPay(req);
        if(StringUtils.isNotEmpty(errorMsg)){
            resp.setDesc(errorMsg);
            resp.setCode(PayResultEnums.ORDER_NUMBER_ERROR.getDesc());
            //支付结果校验不通过 邮件提醒
            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.MINOR,EmailEnum.MODULE_TYPE.PAY,JSON.toJSONString(req),"支付参数校验不成功！",JSON.toJSONString(resp),null);
            return resp;
        }


        //支付宝App请求
        if(PayEnum.PAY_TYPE.ALI_PAY.equals(req.getType())&&PayEnum.BUSINESS_TYPE.APP_PAY.equals(req.getBusinessType())){

            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

            String orderId= getOrderNum(PayEnum.ORDER_MARK.AlI_APP_PAY.getType());
            model.setTotalAmount(req.getAmount().toString());
            model.setBody(req.getOrderDesc());
            model.setOutTradeNo(orderId);
            model.setSubject(req.getOrderDesc());
            AlipayTradeAppPayResponse appPayResponse = aliPayService.aliPayTradeAppPay(model);


            resp = getAlipayTradeAppPayResponse(appPayResponse,orderId);

            //微信app请求
        } else if(PayEnum.PAY_TYPE.WX_PAY.equals(req.getType())&&PayEnum.BUSINESS_TYPE.APP_PAY.equals(req.getBusinessType())){

            String orderId = getOrderNum(PayEnum.ORDER_MARK.WX_APP_PAY.getType());
            AppPayReq appPayReq = new AppPayReq();
            appPayReq.setBody(req.getOrderDesc());
            appPayReq.setTotal_fee(String.valueOf(req.getAmount().multiply(new BigDecimal("100")).setScale( 0, BigDecimal.ROUND_DOWN ).longValue()));
            appPayReq.setOut_trade_no(orderId);
            appPayReq.setProduct_id("order"+getOrderNum(PayEnum.ORDER_MARK.WX_APP_PAY.getType()));
            AppPayResp appPayResp = wxPayService.appPay(appPayReq);


            resp = getWxAppPayResp(appPayResp,appPayReq);


            //微信小程序请求
        } else if(PayEnum.PAY_TYPE.WX_PAY.equals(req.getType())&&PayEnum.BUSINESS_TYPE.APPLET_PAY.equals(req.getBusinessType())){

            if(StringUtils.isEmpty(req.getOpenId())){
                return PayResp.fail(PayResultEnums.PARAM_FAILURE);
            }

            String orderId = getOrderNum(PayEnum.ORDER_MARK.WX_APPLET_PAY.getType());
            AppletPayReq   appletPayReq = new AppletPayReq();
            appletPayReq.setBody(req.getOrderDesc());
            appletPayReq.setTotal_fee(String.valueOf(req.getAmount().multiply(new BigDecimal("100")).setScale( 0, BigDecimal.ROUND_DOWN ).longValue()));
            appletPayReq.setOut_trade_no(orderId);
            appletPayReq.setProduct_id("order"+getOrderNum(PayEnum.ORDER_MARK.WX_APPLET_PAY.getType()));
            appletPayReq.setOpen_id(req.getOpenId());
            AppletPayResp appletPayResp = wxPayService.appletPay(appletPayReq);

            resp = getAppletPayResp(appletPayResp,appletPayReq);


        }else {

            resp.setCode("111");
            resp.setDesc("该支付方式暂不支持！");
        }

        //记录请求记录
        savePayOrder(req,resp);

        log.info("调用统一支付接口结束，返回信息为：{}",JSON.toJSONString(resp));

        //支付返回结果不成功 发送邮件
        if(!PayResultEnums.PAY_SUCCESS.getCode().equals(resp.getCode())){
            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.PAY,JSON.toJSONString(req),"支付接口支付不成功！",JSON.toJSONString(resp),null);

        }

        return resp;
    }

    private PayResp getAlipayTradeAppPayResponse(AlipayTradeAppPayResponse appPayResponse,String orderId) {
        PayResp resp = new PayResp();
        resp.setCode(appPayResponse.getCode());
        resp.setDesc(appPayResponse.getMsg());
        resp.setPrepayId(appPayResponse.getBody());
        resp.setOrderId(orderId);
        return resp;
    }

    private PayResp getAppletPayResp(AppletPayResp appletPayResp,AppletPayReq   appletPayReq) {

        PayResp resp = new PayResp();
        resp.setCode(appletPayResp.getCode());
        resp.setDesc(appletPayResp.getDesc());
        if(PayResultEnums.PAY_NOTICE.getCode().equals(appletPayResp.getCode())){
            resp.setCode(PayResultEnums.PAY_SUCCESS.getCode());
            resp.setDesc(PayResultEnums.PAY_SUCCESS.getDesc());
        }

        resp.setPrepayId(appletPayResp.getPrepayId());
        resp.setOrderId(appletPayReq.getOut_trade_no());

        resp.setParam(getWxAppletParam(appletPayResp,appletPayReq));

        return resp;

    }

    private Object getWxAppletParam(AppletPayResp appletPayResp, AppletPayReq appletPayReq) {
        WxAppletPayVo vo = new WxAppletPayVo();
        vo.setAmount(appletPayReq.getTotal_fee());
        vo.setAppId(gatewayProperties.getAppletAppId());
        vo.setOrderId(appletPayReq.getOut_trade_no());
        vo.setPayDesc(appletPayReq.getBody());
        vo.setNonceStr(RandomNumberGenerator.getUUID());
        vo.setPayPackage("prepay_id="+appletPayResp.getPrepayId());
        vo.setSignType("MD5");
        vo.setTimeStamp(DateUtil.format(new Date(),DateUtil.FORMAT_LONG_NO_SPLIT));
        vo.setPaySign(getAppletPaySign(vo));
        return vo;
    }


    /**
     *
     *微信加密
     */
    private String getAppletPaySign(WxAppletPayVo vo) {
        Map<String, String> data = new HashMap<>();

        data.put("appId",vo.getAppId());
        data.put("nonceStr",vo.getNonceStr());
        data.put("package",vo.getPayPackage());
        data.put("signType",vo.getSignType());
        data.put("timeStamp",vo.getTimeStamp());
        try {

            return WXPayUtil.generateSignature(data, gatewayProperties.getWxPayKey(), WXPayConstants.SignType.MD5);

        }catch (Exception e){
            log.error("小程序支付二次签名异常，异常信息为：{}",e);
        }

        return null;

    }

    private PayResp getWxAppPayResp(AppPayResp appPayResp,AppPayReq appPayReq) {

        PayResp resp = new  PayResp();
        resp.setCode(appPayResp.getCode());
        resp.setDesc(appPayResp.getDesc());
        if(PayResultEnums.PAY_NOTICE.getCode().equals(appPayResp.getCode())){
            resp.setCode(PayResultEnums.PAY_SUCCESS.getCode());
            resp.setDesc(PayResultEnums.PAY_SUCCESS.getDesc());
        }

        resp.setOrderId(appPayReq.getOut_trade_no());
        resp.setPrepayId(appPayResp.getPrepayId());

        resp.setParam(getWxAppParam(appPayResp,appPayReq));


        return resp;


    }

    private Object getWxAppParam(AppPayResp appPayResp, AppPayReq appPayReq) {

        WxAppPayVo vo = new WxAppPayVo();
        vo.setAppid(gatewayProperties.getWxPayAppID());
        vo.setPartnerid(gatewayProperties.getWxPayMchID());
        vo.setNoncestr(RandomNumberGenerator.getUUID());
        vo.setPayPackage("Sign=WXPay");
        vo.setPrepayid(appPayResp.getPrepayId());
        vo.setTimestamp(getTenTimestamp()  );
        vo.setSign(getSignWxApp(vo));
        return vo;
    }

    private String getTenTimestamp() {

        String timestamp  = String.valueOf(new Date().getTime());


        if(timestamp.length()==13){
            timestamp = String.valueOf(new Date().getTime()/1000);
        }


        return timestamp;

    }

    private String getSignWxApp(WxAppPayVo vo) {


        Map<String, String> data = new HashMap<>();

        data.put("appid",vo.getAppid());
        data.put("partnerid",vo.getPartnerid());
        data.put("prepayid",vo.getPrepayid());
        data.put("package",vo.getPayPackage());
        data.put("noncestr",vo.getNoncestr());
        data.put("timestamp",vo.getTimestamp());
        try {

            return WXPayUtil.generateSignature(data, gatewayProperties.getWxPayKey(), WXPayConstants.SignType.MD5);

        }catch (Exception e){
            log.error("小程序支付二次签名异常，异常信息为：{}",e);
        }

        return null;

    }


    /**
     *
     * 获取订单号
     * @param
     * @param
     */
    private String getOrderNum(String mark) {

        return RandomNumberGenerator.generatorMarkOrderNumber(mark,4);
    }

    private void savePayOrder(PayReq req, PayResp resp) {

        TGatewayPayOrder order = new TGatewayPayOrder();

        order.setAccountId(req.getAccountId());
        order.setAmount(req.getAmount());
        order.setType(req.getType().getType());
        order.setBusinessCode(req.getBusinessType().getType());
        order.setBusinessNo(req.getBusinessId());
        order.setCreateTime(DateUtil.getSysTime());
        order.setOrderNo(resp.getOrderId());
        order.setStatus(PayResultEnums.PAY_SUCCESS.getCode().equals(resp.getCode())?PayResultEnums.PAY_NOTICE.getCode():resp.getCode());
        order.setDescInfo(PayResultEnums.PAY_SUCCESS.getCode().equals(resp.getCode())?PayResultEnums.PAY_NOTICE.getDesc():resp.getDesc());
        order.setDeleteFlag(false);

        payOrderService.savePayOrder(order);
    }

    private String checkPay(PayReq req) {

        if(req.getType()==null||req.getBusinessType()==null){
            return "请选择支付方式！";
        }


        if(req.getAmount()==null||req.getAmount().equals(BigDecimal.ZERO)){
            return "请选择支付方式！";
        }



        if(PayEnum.PAY_TYPE.WX_PAY.equals(req.getType())&&PayEnum.BUSINESS_TYPE.SCAN_PAY.equals(req.getBusinessType())&&StringUtils.isEmpty(req.getOpenId())){
            return "小程序支付请输入openid!";
        }

        return null;
    }

    @Override
    public PayResp query(QueryReq req) {

        TGatewayPayOrder order =getOrder(req);

        if(order==null){

            return PayResp.fail(PayResultEnums.QUERY_EMPTY);
        }


        return  queryOrder(order,req);



    }

    private PayResp queryOrder(TGatewayPayOrder order,QueryReq req) {
        PayResp resp = new PayResp();

        req.setType(order.getType());
        req.setBusinessType(order.getBusinessCode());

        //根据类型查询订单
        if(PayEnum.PAY_TYPE.ALI_PAY.getType().equals(req.getType())&&PayEnum.BUSINESS_TYPE.APP_PAY.getType().equals(req.getBusinessType())){

            AlipayTradeQueryModel model = new AlipayTradeQueryModel();
            model.setOutTradeNo(req.getOrderId());

            AlipayTradeQueryResponse queryResponse =aliPayService.aliPayQuery(model);

            resp = getAliQueryResp(queryResponse);


        }else if(PayEnum.PAY_TYPE.WX_PAY.getType().equals(req.getType())&&(PayEnum.BUSINESS_TYPE.APP_PAY.getType().equals(req.getBusinessType())||PayEnum.BUSINESS_TYPE.APPLET_PAY.getType().equals(req.getBusinessType()))){

            AppPayReq appPayReq = new AppPayReq();
            appPayReq.setOut_trade_no(req.getOrderId());

            AppPayResp appPayResp = wxPayService.queryAppPay(appPayReq);

            resp =  getWxQueryResp(appPayResp);
        }else{
            resp.setCode(PayResultEnums.REVOKED.getCode());
            resp.setDesc(PayResultEnums.REVOKED.getDesc());
        }

        //成功更新支付订单数据
        if(PayResultEnums.PAY_SUCCESS.getCode().equals(resp.getCode())){
            updateOrder(order,resp);
        }

        return resp;

    }

    private void updateOrder(TGatewayPayOrder order, PayResp resp) {
        order.setStatus(resp.getCode());
        order.setDescInfo(resp.getDesc());
        payOrderService.updatePayOrder(order);

    }

    private PayResp getAliQueryResp(AlipayTradeQueryResponse queryResponse) {
        PayResp resp = new PayResp();

        //解析阿里返回参数
        if("TRADE_SUCCESS".equals(queryResponse.getTradeStatus())){
            resp.setCode(PayResultEnums.PAY_SUCCESS.getCode());
            resp.setDesc(PayResultEnums.PAY_SUCCESS.getDesc());
        }else if("WAIT_BUYER_PAY".equals(queryResponse.getTradeStatus())){
            resp.setCode(PayResultEnums.ALI_NOT_PAY.getCode());
            resp.setDesc(PayResultEnums.ALI_NOT_PAY.getDesc());
        }else{
            resp.setCode(PayResultEnums.NOT_PAY.getCode());
            resp.setDesc(PayResultEnums.NOT_PAY.getDesc());
        }
        return resp;
    }

    private PayResp getWxQueryResp(AppPayResp appPayResp) {
        PayResp resp = new PayResp();
        resp.setCode(appPayResp.getCode());
        resp.setDesc(appPayResp.getDesc());
        return resp;
    }

    private TGatewayPayOrder getOrder(QueryReq req) {


        TGatewayPayOrderCondition condition = new TGatewayPayOrderCondition();

        condition.setOrderNo(req.getOrderId());
        condition.setDeleteFlag(false);
        TGatewayPayOrder order = payOrderService.getPayOrder(condition);

        return order;
    }

    @Override
    public void quartzQueryOrder() {
        //查询十分钟以内为支付成功的订单

        List<TGatewayPayOrder>  orderList = payOrderService.getNotPayOrder();

        for(TGatewayPayOrder order : orderList){

            QueryReq req = new QueryReq();
            req.setOrderId(order.getOrderNo());
            PayResp resp = queryOrder(order,req);

            //支付支付成功调用业务方接口
            if(PayResultEnums.PAY_SUCCESS.getCode().equals(resp.getCode())){
                contractFacade.payCallBack(order.getBusinessNo());
            }
        }
    }
}
