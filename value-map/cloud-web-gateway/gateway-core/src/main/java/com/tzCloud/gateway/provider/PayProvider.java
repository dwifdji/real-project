package com.tzCloud.gateway.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.tzCloud.arch.common.utils.RandomNumberGenerator;
import com.tzCloud.gateway.common.constants.PayEnums;
import com.tzCloud.gateway.common.constants.PayResultEnums;
import com.tzCloud.gateway.common.wxpay.WxPayResultEnums;
import com.tzCloud.gateway.condition.pay.TGatewayPayOrderCondition;
import com.tzCloud.gateway.controller.req.pay.UnifiedPayReq;
import com.tzCloud.gateway.controller.req.pay.UnifiedPayResp;
import com.tzCloud.gateway.controller.req.pay.UnifiedQueryReq;
import com.tzCloud.gateway.controller.req.pay.WxScanPayReq;
import com.tzCloud.gateway.controller.req.wxpay.ScanPayReq;
import com.tzCloud.gateway.controller.req.wxpay.ScanResultNoticeReq;
import com.tzCloud.gateway.dao.pay.TGatewayPayOrderDao;
import com.tzCloud.gateway.facade.PayFacade;
import com.tzCloud.gateway.model.pay.TGatewayPayOrder;
import com.tzCloud.gateway.resp.wxpay.ScanPayResp;
import com.tzCloud.gateway.service.pay.WxPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 描述：微信支付接口实现类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 15:44
 */
@Component
@Service(version = "1.0.0")
public class PayProvider implements PayFacade {

    private final Logger logger = LoggerFactory.getLogger(PayProvider.class);

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private TGatewayPayOrderDao tGatewayPayOrderDao;

    @Override
    public UnifiedPayResp unifiedPay(UnifiedPayReq req) {

        logger.info("调用统一支付接口，请求参数为：{}", JSON.toJSONString(req));

        UnifiedPayResp resp = new UnifiedPayResp();

        //微信扫码支付处理
        if(PayEnums.PAY_TYPE.SCAN_PAY.getType().equals(req.getPayType())){

            resp = getWxUnifiedPayResp(req);

        }else{

            resp.setCode(PayResultEnums.NOT_TYPE.getCode());
            resp.setDesc(PayResultEnums.NOT_TYPE.getDesc());
        }
        return resp;
    }


    private UnifiedPayResp getWxUnifiedPayResp(UnifiedPayReq req) {

        try {
            WxScanPayReq payReq =(WxScanPayReq)req.getPayParam();

            String payOrder = getOrderNum(PayEnums.PAY_ORDER_MARK.WX.getType());
            String productId = getOrderNum(PayEnums.PAY_ORDER_MARK.WX_ORDER.getType());
            ScanPayReq scanPay = new ScanPayReq();
            //微信支付为单位为分 需*100 取整
            scanPay.setTotal_fee(String.valueOf(req.getAmount().multiply(new BigDecimal("100")).setScale( 0, BigDecimal.ROUND_DOWN ).longValue()));
            scanPay.setProduct_id(productId);
            scanPay.setBody(payReq.getBody());
            scanPay.setOut_trade_no(payOrder);
            ScanPayResp scanPayResp = wxPayService.scanPay(scanPay);

            //记录支付订单
            savePayOrder(req,scanPayResp.getCode(),scanPayResp.getDesc(),payOrder,productId);

            return UnifiedPayResp.payResp(scanPayResp.getCode(),scanPayResp.getDesc(),req.getBusId(),payOrder,scanPayResp.getCode_url(),null);
        }catch (Exception e){

            logger.error("获取微信支付二维码异常,异常信息为：{}",e);
            return UnifiedPayResp.sysException();
        }


    }

    /**
     *
     * 保存订单号
     * @param
     * @param
     */
    private void savePayOrder(UnifiedPayReq req,String payStatus,String msg,String orderNum,String tradeOrder) {

        TGatewayPayOrder order = new TGatewayPayOrder();
        order.setAmount(req.getAmount());
        order.setBusId(req.getBusId());
        order.setPartyId(req.getAccountId()==null?null:Long.valueOf(req.getAccountId()));
        order.setPayBusCode(req.getPayBusCode());
        order.setCreateTime(new Date());
        order.setPayType(req.getPayType());
        order.setPayStatus(payStatus);
        order.setMsg(msg);
        order.setLockTag(req.getLockTag());
        order.setOrderNum(orderNum);
        order.setTradeOrderId(tradeOrder);
        order.setJumpPay(req.getJumpPay());
        tGatewayPayOrderDao.insert(order);

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

    @Override
    public UnifiedPayResp scanResultNotice(ScanResultNoticeReq req) {

        UnifiedPayResp resp = new UnifiedPayResp();

        try{
            TGatewayPayOrderCondition condition = new TGatewayPayOrderCondition();
            condition.setPayBusCode(PayEnums.PAY_BUS_CODE.SCAN_PAY.getType());
            condition.setPayType(PayEnums.PAY_TYPE.SCAN_PAY.getType());
            condition.setOrderNum(req.getOutTradeNo());
            condition.setPayStatus(PayResultEnums.PAY_NOTICE.getCode());//等待异步通知的订单
            //查询订单
            TGatewayPayOrder order = tGatewayPayOrderDao.selectFirst(condition);
            //查询为空时 说明通知已经处理过了
            if(order==null){
                return UnifiedPayResp.fail(PayResultEnums.QUERY_EMPTY.getCode(),PayResultEnums.QUERY_EMPTY.getCode());
            }

            resp.setPayBusType(order.getPayBusCode());
            resp.setPayOrder(order.getOrderNum());
            resp.setBusId(order.getBusId());
            //支付响应成功时处理
            if(WxPayResultEnums.RETURN_CODE_SUCCESS.getCode().equals(req.getReturnCode())){


                //支付成功更新数据
                if(WxPayResultEnums.RETURN_CODE_SUCCESS.getCode().equals(req.getResultCode())){

                    order.setPayStatus(PayResultEnums.PAY_SUCCESS.getCode());
                    order.setMsg("异步通知支付成功");
                    order.setUpdateTime(new Date());
                }else{

                    order.setPayStatus(PayResultEnums.PAY_FAILURE.getCode());
                    order.setMsg(req.getErrCodeDes());
                }

                resp.setCode(order.getPayStatus());
                resp.setDesc(order.getMsg());
                tGatewayPayOrderDao.updateById(order);
            }else{
                resp.setCode(PayResultEnums.PAY_FAILURE.getCode());
                resp.setDesc(req.getReturnMsg());
            }

        }catch (Exception e){

            logger.error("处理微信异步通知异常，异常信息为：{}",e);
            return UnifiedPayResp.sysException();
        }

        return resp;
    }

    @Override
    public UnifiedPayResp unifiedPayQuery(UnifiedQueryReq req) {
        TGatewayPayOrderCondition condition = new TGatewayPayOrderCondition();
        condition.setOrderNum(req.getPayOrder());
        condition.setPayType(req.getPayType());
        //查询支付订单表
        TGatewayPayOrder order = tGatewayPayOrderDao.selectFirst(condition);
        if(order == null){
            return UnifiedPayResp.fail(PayResultEnums.QUERY_EMPTY.getCode(),PayResultEnums.QUERY_EMPTY.getDesc());
        }

        UnifiedPayResp resp = new UnifiedPayResp();
        resp.setBusId(order.getBusId());
        //微信支付查询
        if(PayEnums.PAY_TYPE.SCAN_PAY.getType().equals(req.getPayType())){

            ScanPayResp queryResp = wxPayService.queryScanPayResult(req.getPayOrder(),order.getPayBusCode());
            resp.setCode(queryResp.getCode());
            resp.setDesc(queryResp.getDesc());
            //支付成功或者最终支付失败的时候更新订单表
            updateOrderStatus(resp,order);

        }else{
            resp.setCode(PayResultEnums.NOT_TYPE.getCode());
            resp.setDesc(PayResultEnums.NOT_TYPE.getDesc());
        }

        return resp;
    }


    /**
     *
     * 查询结果更新到订单表
     */
    private void updateOrderStatus(UnifiedPayResp resp,TGatewayPayOrder order) {
        if(PayResultEnums.PAY_SUCCESS.getCode().equals(resp.getCode())||PayResultEnums.PAY_FAILURE.getCode().equals(resp.getCode())){

            order.setPayStatus(resp.getCode());
            order.setMsg(resp.getDesc());
            order.setUpdateTime(new Date());
            tGatewayPayOrderDao.updateById(order);

        }

    }
}
