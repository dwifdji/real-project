package com._360pai.core.provider;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.aspact.GatewayExceptionEmailService;
import com._360pai.core.common.constants.ExceptionEmailEnum;
import com._360pai.core.condition.pay.GatewayPayOrderCondition;
import com._360pai.core.dao.pay.GatewayPayOrderDao;
import com._360pai.core.facade.activity.AuctionFacade;
import com._360pai.core.facade.enrolling.EnrollingFacade;
import com._360pai.core.facade.enrolling.req.EnrollingReq;
import com._360pai.core.model.DfftPay.*;
import com._360pai.core.model.pay.GatewayPayBackRecord;
import com._360pai.core.model.pay.GatewayPayOrder;
import com._360pai.core.service.DfftPayService;
import com._360pai.core.service.WxPayService;
import com._360pai.gateway.common.dfftpay.PayEnums;
import com._360pai.gateway.common.dfftpay.PayResultEnums;
import com._360pai.gateway.common.wxpay.WxPayResultEnums;
import com._360pai.gateway.controller.req.dfftpay.*;
import com._360pai.gateway.controller.req.wxpay.AppletPayReq;
import com._360pai.gateway.controller.req.wxpay.ScanPayReq;
import com._360pai.gateway.controller.req.wxpay.ScanResultNoticeReq;
import com._360pai.gateway.facade.PayFacade;
import com._360pai.gateway.resp.wxpay.AppletPayResp;
import com._360pai.gateway.resp.wxpay.ScanPayResp;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 描述：微信支付接口实现类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 15:44
 */
@Component
@Service(version = "1.0.0")
public class PayProvider extends GatewayExceptionEmailService implements PayFacade {


    private final Logger logger = LoggerFactory.getLogger(PayProvider.class);


    @Autowired
    private  WxPayService wxPayService;


    @Autowired
    private GatewayPayOrderDao gatewayPayOrderDao;


    @Autowired
    private DfftPayService dfftPayService;

    @Autowired
    private GatewayProperties gatewayProperties;


    @Reference(version = "1.0.0")
    private AuctionFacade auctionFacade;


    @Reference(version = "1.0.0")
    private EnrollingFacade enrollingFacade;



    @Override
    public UnifiedPayResp unifiedPay(UnifiedPayReq req) {

        logger.info("调用统一支付接口，请求参数为：{}",JSON.toJSONString(req));

        UnifiedPayResp resp = new UnifiedPayResp();

        //微信扫码支付处理
        if(PayEnums.PAY_TYPE.SCAN_PAY.getType().equals(req.getPayType())){

            resp = getWxUnifiedPayResp(req);

        //小程序微信支付
        }else if(PayEnums.PAY_TYPE.APPLET_PAY.getType().equals(req.getPayType())){

            resp = getAppletPayResp(req);

            //保证金释放
        }else if(PayEnums.PAY_TYPE.LOCK_DEPOSIT.getType().equals(req.getPayType())){

            resp = getLockDepositResp(req);

         //保证金释放
        }else if(PayEnums.PAY_TYPE.RELEASE_DEPOSIT.getType().equals(req.getPayType())){

            resp = getReleaseDepositResp(req);

          //保证金支付
        }else if(PayEnums.PAY_TYPE.TRANSFER_DEPOSIT.getType().equals(req.getPayType())){

            resp = getTransferDepositResp(req);
        //直接支付
        }else if(PayEnums.PAY_TYPE.DIRECT_PAY.getType().equals(req.getPayType())){

            resp = getDirectPayResp(req);



         //通道支付类型
        }else if(PayEnums.PAY_TYPE.CHANNEL_PAY.getType().equals(req.getPayType())){

            resp = getChannelPayResp(req);

         //批量支付

        }else if(PayEnums.PAY_TYPE.BATCH_PAY.getType().equals(req.getPayType())){

            resp = getBatchPayResp(req);

        }else{

            resp.setCode(PayResultEnums.NOT_TYPE.getCode());
            resp.setDesc(PayResultEnums.NOT_TYPE.getDesc());
        }

        //校验返回code看看要不要发提醒邮件
        sendPayRemindEmail(req,resp);

        return resp;
    }

    /**
     *
     *小程序支付
     */
    private UnifiedPayResp getAppletPayResp(UnifiedPayReq req) {

        try {
            WxScanPayReq payReq =(WxScanPayReq)req.getPayParam();

            String payOrder = getOrderNum(PayEnums.PAY_ORDER_MARK.APPLET.getType());
            String productId = getOrderNum(PayEnums.PAY_ORDER_MARK.WX_ORDER.getType());
            AppletPayReq appletPay = new AppletPayReq();
            //微信支付为单位为分 需*100 取整
            appletPay.setTotal_fee(String.valueOf(req.getAmount().multiply(new BigDecimal("100")).setScale( 0, BigDecimal.ROUND_DOWN ).longValue()));
            appletPay.setProduct_id(productId);
            appletPay.setBody(payReq.getBody());
            appletPay.setOut_trade_no(payOrder);
            appletPay.setOpen_id(payReq.getOpenId());
            appletPay.setChannel(req.getPayBusCode());
            AppletPayResp appletPayResp = wxPayService.appletPay(appletPay);

            //记录支付订单
            savePayOrder(req,appletPayResp.getCode(),appletPayResp.getDesc(),payOrder,productId);

            return UnifiedPayResp.payResp(appletPayResp.getCode(),appletPayResp.getDesc(),req.getBusId(),payOrder,null,appletPayResp.getPrepayId());
        }catch (Exception e){

            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.PAY,PayEnums.PAY_TYPE.SCAN_PAY.getTypeName(),JSON.toJSONString(req),e);

            logger.error("小程序支付异常,异常信息为：{}",e);
            return UnifiedPayResp.sysException();
        }
    }


    //
    private UnifiedPayResp getBatchPayResp(UnifiedPayReq req) {

        List<BatchDirectReq> payList =(List<BatchDirectReq>)req.getPayParam();

        List<BatchPayInfoVo> bathPayInfo = new ArrayList<>();

        //当支付为一笔时
        if(payList.size()==1){
            BatchDirectReq batchDirectReq = payList.get(0);

            LockOrReleaseOrDirectReq lockOrReleaseOrDirectReq = new LockOrReleaseOrDirectReq();
            lockOrReleaseOrDirectReq.setRecMemCode(batchDirectReq.getRecMemCode());
            lockOrReleaseOrDirectReq.setRecMemName(batchDirectReq.getRecMemName());
            lockOrReleaseOrDirectReq.setPayMemCode(batchDirectReq.getPayMemCode());
            lockOrReleaseOrDirectReq.setPayMemName(batchDirectReq.getPayMemName());

            //调用直接支付
            UnifiedPayReq unifiedPay = new  UnifiedPayReq();

            unifiedPay.setPayType(PayEnums.PAY_TYPE.BATCH_PAY.getType());
            unifiedPay.setPayBusCode(req.getPayBusCode());
            unifiedPay.setBusId(batchDirectReq.getBusId());
            unifiedPay.setPayTo(batchDirectReq.getPayTo());
            unifiedPay.setAmount(batchDirectReq.getAmount());
            unifiedPay.setLockTag(batchDirectReq.getLockTag());
            unifiedPay.setPayParam(lockOrReleaseOrDirectReq);
            unifiedPay.setWhoPay(batchDirectReq.getWhoPay());
            UnifiedPayResp resp = getDirectPayResp(unifiedPay);
            BatchPayInfoVo vo = new BatchPayInfoVo();
            vo.setPayOrder(resp.getPayOrder());
            vo.setBusId(resp.getBusId());

            bathPayInfo.add(vo);
            resp.setBathPayInfo(bathPayInfo);

            return resp;
        }else{


            List<DirectOrLockPayReq> directOrLockPayReqList = new ArrayList<>();

            for(BatchDirectReq batchDirectReq :payList){

                BatchPayInfoVo infoVo = new BatchPayInfoVo();
                DirectOrLockPayReq  payReq = new DirectOrLockPayReq();
                payReq.setBusId(batchDirectReq.getBusId());
                String orderNum = getOrderNum(PayEnums.PAY_ORDER_MARK.DIRECT.getType());
                String tradeOrder = getOrderNum(PayEnums.PAY_ORDER_MARK.ORDER.getType());
                //查询是否已经有了记录
               /* GatewayPayOrder order =getPayOrderByBusId(batchDirectReq.getBusId());
                if(order!=null){
                    orderNum=order.getOrderNum();
                    tradeOrder=order.getTradeOrderId();
                }*/
                payReq.setPayMemCode(PayEnums.WHO_PAY.WEB_PAY.getType().equals(batchDirectReq.getWhoPay())?gatewayProperties.getPayCustomerId():batchDirectReq.getPayMemCode());
                payReq.setPayMemName(PayEnums.WHO_PAY.WEB_PAY.getType().equals(batchDirectReq.getWhoPay())?gatewayProperties.getPayCompanyName():batchDirectReq.getPayMemName());
                payReq.setRecMemCode(PayEnums.PAY_TO.PAY_TO_MEM.getType().equals(batchDirectReq.getPayTo())?batchDirectReq.getRecMemCode():gatewayProperties.getPayCustomerId());
                payReq.setRecMemName(PayEnums.PAY_TO.PAY_TO_MEM.getType().equals(batchDirectReq.getPayTo())?batchDirectReq.getRecMemName():gatewayProperties.getPayCompanyName());
                payReq.setPayID(orderNum);
                payReq.setPayType(PayEnums.DFFT_PAY_CODE.PAY_TYPE_PAY.getType());
                payReq.setLocktag(batchDirectReq.getLockTag());
                payReq.setTradeOrder(tradeOrder);
                payReq.setNotifyUrl(gatewayProperties.getNotifyUrl());
                payReq.setCurrency("CNY");
                payReq.setPayAmt(batchDirectReq.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                infoVo.setBusId(batchDirectReq.getBusId());
                infoVo.setPayOrder(orderNum);

                bathPayInfo.add(infoVo);

                directOrLockPayReqList.add(payReq);

            }

            PayResp payResp = dfftPayService.BatchDirectOrLockPay(directOrLockPayReqList);

            //保存订单信息
            saveOrder(payResp,directOrLockPayReqList, req);

            return getPayBatchResp(payResp,bathPayInfo);

        }


    }

    /**
     *
     *获取支付的订单
     */
    private GatewayPayOrder getPayOrderByBusId(String busId) {
        GatewayPayOrderCondition condition = new GatewayPayOrderCondition();
        condition.setBusId(busId);
        condition.setPayStatus(PayResultEnums.PAY_NOTICE.getCode());

        return gatewayPayOrderDao.selectFirst(condition);
    }

    /**
     *
     *保存批量的订单信息
     */
    private void saveOrder(PayResp payResp, List<DirectOrLockPayReq> directOrLockPayReqList,UnifiedPayReq payReq) {

        try {

            for(DirectOrLockPayReq req : directOrLockPayReqList){

                GatewayPayOrder order = new GatewayPayOrder();
                order.setAmount(new BigDecimal(req.getPayAmt()));
                order.setPayBusCode(payReq.getPayBusCode());
                order.setCreateTime(new Date());
                order.setPayType(payReq.getPayType());
                order.setPayStatus(payResp.getCode());
                order.setMsg(payResp.getDesc());
                order.setOrderNum(req.getPayID());
                order.setTradeOrderId(req.getTradeOrder());
                order.setJumpPay(req.getJumpPay());
                order.setPayMemName(req.getPayMemName());
                order.setPayMemCode(req.getPayMemCode());
                order.setRecMemName(req.getRecMemName());
                order.setBusId(req.getBusId());
                order.setRecMemCode(req.getRecMemCode());
                order.setPartyId(Long.valueOf(payReq.getPartyId()));
                gatewayPayOrderDao.insert(order);
            }

        }catch (Exception e){

            logger.error("保存批量支付订单异常，异常信息为：{}",e);
        }



    }

    private UnifiedPayResp getPayBatchResp(PayResp payResp,List<BatchPayInfoVo> bathPayInfo) {

        if(PayResultEnums.PAY_NOTICE.getCode().equals(payResp.getCode())){
            UnifiedPayResp resp = new UnifiedPayResp();

            resp.setCode(PayResultEnums.PAY_NOTICE.getCode());
            resp.setDesc(PayResultEnums.PAY_NOTICE.getDesc());
            resp.setUrl(gatewayProperties.getPayUrl());
            resp.setParam(payResp.getPayID());
            resp.setBathPayInfo(bathPayInfo);

            return resp;

        }else{
            return UnifiedPayResp.fail(payResp.getCode(),payResp.getDesc());

        }

     }

    //保证金支付
    private UnifiedPayResp getTransferDepositResp(UnifiedPayReq payReq) {

        MarginOperationReq lock = new MarginOperationReq();

        try{
            LockOrReleaseOrDirectReq req =(LockOrReleaseOrDirectReq)payReq.getPayParam();

            GatewayPayOrderCondition condition = new GatewayPayOrderCondition();

            condition.setPayStatus(PayResultEnums.PAY_SUCCESS.getCode());
            condition.setOrderNum(req.getOriginalPayID());

            GatewayPayOrder order = gatewayPayOrderDao.selectFirst(condition);
            if(order==null){
                return UnifiedPayResp.fail(PayResultEnums.QUERY_LOCK_EMPTY.getCode(),PayResultEnums.QUERY_LOCK_EMPTY.getDesc());
            }


            String orderNum = getOrderNum(PayEnums.PAY_ORDER_MARK.TRAN_RELEASE.getType());
            //不传金额则 全部锁定支付
            BigDecimal amount = payReq.getAmount()==null?order.getAmount():payReq.getAmount();

            String payCode = order.getPayMemCode();
            String payName = order.getPayMemName();
            String recCode = order.getRecMemCode();
            String recName = order.getRecMemName();
            String summary ="保证金支付";
            //是否为保证金退回
            if(PayEnums.BACK_TAG.BACK.getType().equals(payReq.getBackTag())){
                 recCode = order.getPayMemCode();
                 recName = order.getPayMemName();
                 payCode = order.getRecMemCode();
                 payName = order.getRecMemName();
                 summary ="保证金支付退回";
            }
            lock.setPayAmt(amount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            lock.setPayMemCode(payCode);
            lock.setPayMemName(payName);
            lock.setRecMemCode(recCode);//预招商锁定收款方为平台账号
            lock.setRecMemName(recName);
            lock.setPayID(orderNum);
            lock.setPayType(PayEnums.DFFT_PAY_CODE.PAY_TYPE_TRANSFER_DEPOSIT.getType());
            lock.setTradeOrder(order.getTradeOrderId());
            lock.setOriginalPayID(req.getOriginalPayID());
            lock.setNotifyUrl(gatewayProperties.getNotifyUrl());
            lock.setLockTag(payReq.getLockTag());
            lock.setSummary(summary);

            PayResp resp = dfftPayService.marginOperation(lock);

            payReq.setAmount(amount);
            req.setPayMemName(order.getPayMemName());
            req.setPayMemCode(order.getPayMemCode());
            req.setRecMemName(order.getRecMemName());
            req.setRecMemCode(order.getRecMemCode());
            payReq.setPayParam(req);

            savePayOrder(payReq,resp.getCode(),resp.getDesc(),orderNum,order.getTradeOrderId());

            return UnifiedPayResp.payResp(resp.getCode(),resp.getDesc(),payReq.getBusId(),orderNum,null,null);

        }catch (Exception e){

            logger.error("标的保证金支付异常，异常接口为：{}",e);


            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.PAY,PayEnums.PAY_TYPE.TRANSFER_DEPOSIT.getTypeName(),JSON.toJSONString(lock),e);


            return UnifiedPayResp.sysException();

        }

     }

    /**
     * 微信支付异步返回通知处理
     *
     */
    @Override
    public UnifiedPayResp scanResultNotice(ScanResultNoticeReq req) {

        UnifiedPayResp resp = new UnifiedPayResp();

        try{
            GatewayPayOrderCondition condition = new GatewayPayOrderCondition();
            condition.setPayBusCode(PayEnums.PAY_BUS_CODE.SCAN_PAY.getType());
            condition.setPayType(PayEnums.PAY_TYPE.SCAN_PAY.getType());
            condition.setOrderNum(req.getOutTradeNo());
            condition.setPayStatus(PayResultEnums.PAY_NOTICE.getCode());//等待异步通知的订单
            //查询订单
            GatewayPayOrder order = gatewayPayOrderDao.selectFirst(condition);
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
                gatewayPayOrderDao.updateById(order);
            }else{
                resp.setCode(PayResultEnums.PAY_FAILURE.getCode());
                resp.setDesc(req.getReturnMsg());
            }

        }catch (Exception e){

            logger.error("处理微信异步通知异常，异常信息为：{}",e);
            return UnifiedPayResp.sysException();
        }
        //校验返回code看看要不要发提醒邮件
        sendPayRemindEmail(req,resp);

        return resp;
    }

    /**
     *
     *东方付通异步返回处理
     */
    @Override
    public UnifiedPayResp ddftResultNotice(DfftPayCallBackReq req) {
        UnifiedPayResp resp = new UnifiedPayResp();

        try {
            //获取订单数据库订单
            GatewayPayOrderCondition condition = new GatewayPayOrderCondition();

            condition.setOrderNum(req.getPayID());
            condition.setPayStatus(PayResultEnums.PAY_NOTICE.getCode());

            GatewayPayOrder order = gatewayPayOrderDao.selectFirst(condition);
            if(order==null){
                resp.setCode(PayResultEnums.QUERY_EMPTY.getCode());
                resp.setDesc(PayResultEnums.QUERY_EMPTY.getDesc());
                return resp;
            }
            resp.setCode(req.getPayStatus());
            resp.setDesc(req.getPayMessage());
            resp.setBusId(order.getBusId());
            resp.setPayOrder(order.getOrderNum());
            resp.setPayBusType(order.getPayBusCode());
            //根据通知结果更新支付订单的状态
            if(PayResultEnums.PAY_SUCCESS.getCode().equals(req.getPayStatus())){

                order.setPayStatus(PayResultEnums.PAY_SUCCESS.getCode());
                order.setMsg("异步通知成功");
                order.setUpdateTime(new Date());
            }else{
                order.setPayStatus(req.getPayStatus());
                order.setMsg(req.getPayMessage());
                order.setUpdateTime(new Date());

            }

            gatewayPayOrderDao.updateById(order);
        }catch (Exception e){
            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.PAY,"支付回调异常",JSON.toJSONString(req),e);
            logger.error("支付回调处理异常，异常信息为：{}",e);
            resp.setCode(PayResultEnums.SYS_EXCEPTION.getCode());
            resp.setDesc(PayResultEnums.SYS_EXCEPTION.getDesc());

        }




        return resp;
    }

    /**
     * 支付统一查询接口
     *
     */
    @Override
    public UnifiedPayResp unifiedPayQuery(UnifiedQueryReq req) {
        GatewayPayOrderCondition condition = new GatewayPayOrderCondition();
        condition.setOrderNum(req.getPayOrder());
        condition.setPayType(req.getPayType());
        //查询支付订单表
        GatewayPayOrder order = gatewayPayOrderDao.selectFirst(condition);
         if(order == null){
            return UnifiedPayResp.fail(PayResultEnums.QUERY_EMPTY.getCode(),PayResultEnums.QUERY_EMPTY.getDesc());
        }

        UnifiedPayResp resp = new UnifiedPayResp();
        resp.setBusId(order.getBusId());
        //微信支付查询
        if(PayEnums.PAY_TYPE.SCAN_PAY.getType().equals(req.getPayType())||PayEnums.PAY_TYPE.APPLET_PAY.getType().equals(req.getPayType())){

            ScanPayResp queryResp = wxPayService.queryScanPayResult(req.getPayOrder(),order.getPayBusCode());
            resp.setCode(queryResp.getCode());
            resp.setDesc(queryResp.getDesc());
            //支付成功或者最终支付失败的时候更新订单表
            updateOrderStatus(resp,order);

        //东方付通支付查询
        }else if(PayEnums.PAY_TYPE.LOCK_DEPOSIT.getType().equals(req.getPayType())||PayEnums.PAY_TYPE.RELEASE_DEPOSIT.getType().equals(req.getPayType())||PayEnums.PAY_TYPE.TRANSFER_DEPOSIT.getType().equals(req.getPayType())||PayEnums.PAY_TYPE.DIRECT_PAY.getType().equals(req.getPayType())||PayEnums.PAY_TYPE.CHANNEL_PAY.getType().equals(req.getPayType())){

            CommResp queryOrder = dfftPayService.queryOrder(req.getPayOrder());

            resp.setCode(queryOrder.getCode());
            resp.setDesc(queryOrder.getDesc());
            //支付成功或者最终支付失败的时候更新订单表
            updateOrderStatus(resp,order);

        }else{
            resp.setCode(PayResultEnums.NOT_TYPE.getCode());
            resp.setDesc(PayResultEnums.NOT_TYPE.getDesc());
        }

        return resp;
    }

    @Override
    public void saveCallBackInfo(GatewayPayBackRecordReq req) {

        GatewayPayBackRecord record = new GatewayPayBackRecord();

        BeanUtils.copyProperties(req, record);

        dfftPayService.saveCallBackInfo(record);

    }

    @Override
    public void updateCallBackInfo(GatewayPayBackRecordReq req) {

        GatewayPayBackRecord record = new GatewayPayBackRecord();

        BeanUtils.copyProperties(req, record);

        dfftPayService.updateCallBackInfo(record);
    }

    @Override
    public void queryOrderQuartz() {
        //查询需要异步通知的订单
        List<GatewayPayOrder> orderList = gatewayPayOrderDao.getNoticeList();

        logger.info("定时任务查询支付状态，查询数据为：{}",JSON.toJSONString(orderList));
        //根据状态更新订单信息
        for(GatewayPayOrder order : orderList){
            CommResp queryOrder = dfftPayService.queryOrder(order.getOrderNum());

            //查询当最终成功或者失败时更新支付订单表
            if(PayResultEnums.PAY_SUCCESS.getCode().equals(queryOrder.getCode())||PayResultEnums.PAY_FAILURE.getCode().equals(queryOrder.getCode())){
                order.setPayStatus(queryOrder.getCode());
                order.setMsg(queryOrder.getDesc());
                order.setUpdateTime(new Date());
                gatewayPayOrderDao.updateById(order);
                //通知业务调用方
                notifyBus(order);
            }
        }

    }

    /**
     *
     *支付结果通知业务方
     */
    private void notifyBus(GatewayPayOrder order) {

        logger.info("支付结果通知业务方，通知的参数为：{}",JSON.toJSONString(order));

        if(PayEnums.PAY_BUS_CODE.BALANCE_PAY.getType().equals(order.getPayBusCode())){

            auctionCallBack(order);

        }else if (PayEnums.PAY_BUS_CODE.ENROLLING_COMMISSION_PAY.getType().equals(order.getPayBusCode())){

            logger.info("抵押物预招商支付结果回调业务方，回调参数为：{}",JSON.toJSONString(order));

            enrollingCallBack(order);

        }

    }

    /**
     *
     *拍品回调
     */
    private void auctionCallBack(GatewayPayOrder order) {

        logger.info("通知业务调用方{}",JSON.toJSONString(order));
        try {
            auctionFacade.payCallBack(order.getBusId(),order.getOrderNum(),PayResultEnums.PAY_SUCCESS.getCode().equals(order.getPayStatus())?SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS:SystemConstant.PAY_ORDER_STATUS_PAID_FAIL);

        }catch (Exception e){
            logger.error("回调异常，回调信息为,{}异常信息为{}",JSON.toJSONString(order),e);

        }

    }

    /**
     *
     *预招商佣金订单回调
     */
    private void enrollingCallBack(GatewayPayOrder order) {

        try {

            EnrollingReq.payCommissionCallBack payCommissionCallBack = new EnrollingReq.payCommissionCallBack();

            payCommissionCallBack.setBusId(order.getBusId());
            payCommissionCallBack.setOrderNum(order.getOrderNum());
            payCommissionCallBack.setPayStatus(order.getPayStatus());

            enrollingFacade.payCommissionCallBack(payCommissionCallBack);


        }catch (Exception e){

            logger.error("预招商佣金回调业务方异常，异常信息为：{}",e);
        }

    }


    /**
     *
     * 查询结果更新到订单表
     */
    private void updateOrderStatus(UnifiedPayResp resp,GatewayPayOrder order) {
        if(PayResultEnums.PAY_SUCCESS.getCode().equals(resp.getCode())||PayResultEnums.PAY_FAILURE.getCode().equals(resp.getCode())){

            order.setPayStatus(resp.getCode());
            order.setMsg(resp.getDesc());
            order.setUpdateTime(new Date());
            gatewayPayOrderDao.updateById(order);

        }

    }

    /**
     *
     *通道支付
     */
    private UnifiedPayResp getChannelPayResp(UnifiedPayReq req) {

        UnifiedChannelPayReq  channelPayReq = (UnifiedChannelPayReq)req.getPayParam();

        ChannelPayReq payReq = new ChannelPayReq();

        String orderNum = getOrderNum(PayEnums.PAY_ORDER_MARK.CHANNEL.getType());
        String tradeOrder = getOrderNum(PayEnums.PAY_ORDER_MARK.ORDER.getType());
        payReq.setPayMemCode(PayEnums.WHO_PAY.WEB_PAY.getType().equals(req.getWhoPay())?gatewayProperties.getPayCustomerId():channelPayReq.getPayMemCode());
        payReq.setPayMemName(PayEnums.WHO_PAY.WEB_PAY.getType().equals(req.getWhoPay())?gatewayProperties.getPayCompanyName():channelPayReq.getPayMemName());
        payReq.setAccName(channelPayReq.getAccName());
        payReq.setAccNO(channelPayReq.getAccNO());
        payReq.setBankCode(channelPayReq.getBankCode());
        payReq.setPayAmt(req.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        payReq.setPayID(orderNum);
        payReq.setTradeOrder(tradeOrder);
        payReq.setInstCash("0");//T+0 付款
        payReq.setPayType(PayEnums.DFFT_PAY_CODE.PAY_TYPE_CHANNEL.getType());
        payReq.setNotifyUrl(gatewayProperties.getNotifyUrl());
        PayResp resp = dfftPayService.channelPay(payReq);

        savePayOrder(req,resp.getCode(),resp.getDesc(),orderNum,tradeOrder);

        return UnifiedPayResp.payResp(resp.getCode(),resp.getDesc(),req.getBusId(),orderNum,null,null);


     }

     /**
      *
      * 直接支付
      */
    private UnifiedPayResp getDirectPayResp(UnifiedPayReq req) {

        LockOrReleaseOrDirectReq directReq = (LockOrReleaseOrDirectReq)req.getPayParam();

        DirectOrLockPayReq payReq = new DirectOrLockPayReq();

        String orderNum = getOrderNum(PayEnums.PAY_ORDER_MARK.DIRECT.getType());
        String tradeOrder = getOrderNum(PayEnums.PAY_ORDER_MARK.ORDER.getType());

        //查询是否已经有了记录
        /*GatewayPayOrder order =getPayOrderByBusId(req.getBusId());
        if(order!=null){
            orderNum=order.getOrderNum();
            tradeOrder=order.getTradeOrderId();
        }*/
        payReq.setPayMemCode(PayEnums.WHO_PAY.WEB_PAY.getType().equals(req.getWhoPay())?gatewayProperties.getPayCustomerId():directReq.getPayMemCode());
        payReq.setPayMemName(PayEnums.WHO_PAY.WEB_PAY.getType().equals(req.getWhoPay())?gatewayProperties.getPayCompanyName():directReq.getPayMemName());
        payReq.setRecMemCode(PayEnums.PAY_TO.PAY_TO_MEM.getType().equals(req.getPayTo())?directReq.getRecMemCode():gatewayProperties.getPayCustomerId());
        payReq.setRecMemName(PayEnums.PAY_TO.PAY_TO_MEM.getType().equals(req.getPayTo())?directReq.getRecMemName():gatewayProperties.getPayCompanyName());
        payReq.setPayID(orderNum);
        payReq.setPayType(PayEnums.DFFT_PAY_CODE.PAY_TYPE_PAY.getType());
        payReq.setLocktag(req.getLockTag());
        payReq.setPayAmt(req.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        payReq.setJumpPay(req.getJumpPay());
        payReq.setTradeOrder(tradeOrder);
        payReq.setNotifyUrl(gatewayProperties.getNotifyUrl());
        payReq.setPayMemType(req.getPayMemType());
        PayResp resp = dfftPayService.directOrLockPay(payReq);

        if(PayResultEnums.REQUEST_SUCCESS.getCode().equals(resp.getCode())){
            resp.setCode(PayResultEnums.PAY_NOTICE.getCode());
            resp.setDesc(PayResultEnums.PAY_NOTICE.getDesc());
        }

        savePayOrder(req,resp.getCode(),resp.getDesc(),orderNum,tradeOrder);

        return UnifiedPayResp.payResp(resp.getCode(),resp.getDesc(),req.getBusId(),orderNum,gatewayProperties.getPayUrl(),resp.getPayID());
    }


    /**
     *
     * 保证金释放
     */
    private UnifiedPayResp getReleaseDepositResp(UnifiedPayReq payReq) {


        MarginOperationReq lock = new MarginOperationReq();

        try{
            LockOrReleaseOrDirectReq req =(LockOrReleaseOrDirectReq)payReq.getPayParam();

            GatewayPayOrderCondition condition = new GatewayPayOrderCondition();

            condition.setPayStatus(PayResultEnums.PAY_SUCCESS.getCode());
            condition.setOrderNum(req.getOriginalPayID());

            GatewayPayOrder order = gatewayPayOrderDao.selectFirst(condition);
            if(order==null){
                return UnifiedPayResp.fail(PayResultEnums.QUERY_LOCK_EMPTY.getCode(),PayResultEnums.QUERY_LOCK_EMPTY.getDesc());
            }

             if(releaseAll(payReq)){

                return UnifiedPayResp.fail(PayResultEnums.QUERY_LOCK_EMPTY.getCode(),PayResultEnums.QUERY_LOCK_EMPTY.getDesc());

            }

            String payCode = order.getPayMemCode();
            String payName = order.getPayMemName();
            String recMemCode = order.getRecMemCode();
            String pecMemName = order.getRecMemName();
            //当解锁 保证金支付的订单时 或者直接支付时 付款方和收款方要互换
            if(req.getOriginalPayID().contains(PayEnums.PAY_ORDER_MARK.TRAN_RELEASE.getType())||req.getOriginalPayID().contains(PayEnums.PAY_ORDER_MARK.DIRECT.getType())||req.getOriginalPayID().contains(PayEnums.PAY_ORDER_MARK.TRANSFER.getType())){
                 payCode = order.getRecMemCode();
                 payName = order.getRecMemName();
                 recMemCode = order.getPayMemCode();
                 pecMemName = order.getPayMemName();
            }

            String orderNum = getOrderNum(PayEnums.PAY_ORDER_MARK.RELEASE.getType());

            //解锁金额不传时 全部解锁
            BigDecimal amount = payReq.getAmount()==null?order.getAmount():payReq.getAmount();

            lock.setPayAmt(amount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            lock.setPayMemCode(payCode);
            lock.setPayMemName(payName);
            lock.setRecMemCode(recMemCode);
            lock.setRecMemName(pecMemName);
            lock.setOriginalPayID(req.getOriginalPayID());
            lock.setPayID(orderNum);
            lock.setPayType(PayEnums.DFFT_PAY_CODE.PAY_TYPE_RELEASE_DEPOSIT.getType());
            lock.setTradeOrder(order.getTradeOrderId());
            lock.setNotifyUrl(gatewayProperties.getNotifyUrl());
            lock.setAuditFlag("1");
            lock.setSummary("保证金释放");
            lock.setCurrency("CNY");

            String errorParam = checkReleaseDepositParam(lock);
            //校验参数
            if(StringUtils.isNotEmpty(errorParam)){
                 return UnifiedPayResp.fail(PayResultEnums.PARAM_FAILURE.getCode(),errorParam);

            }


            PayResp resp = dfftPayService.marginOperation(lock);


            payReq.setAmount(amount);
            req.setPayMemName(order.getPayMemName());
            req.setPayMemCode(order.getPayMemCode());
            req.setRecMemName(order.getRecMemName());
            req.setRecMemCode(order.getRecMemCode());
            payReq.setPayParam(req);

            savePayOrder(payReq,resp.getCode(),resp.getDesc(),orderNum,order.getTradeOrderId());


            return UnifiedPayResp.payResp(resp.getCode(),resp.getDesc(),payReq.getBusId(),orderNum,null,null);

        }catch (Exception e){

            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.PAY,PayEnums.PAY_TYPE.RELEASE_DEPOSIT.getTypeName(),JSON.toJSONString(lock),e);


            logger.error("保证金释放异常，异常接口为：{}",e);

            return UnifiedPayResp.sysException();

        }

     }


     /**
      *
      *保证金释放校验参数
      */
    private String checkReleaseDepositParam(MarginOperationReq lock) {
        //原始ID
        if(StringUtils.isEmpty(lock.getOriginalPayID())){
            return "原始订单号不能为空！";
        }
        if(StringUtils.isEmpty(lock.getPayAmt())){
            return "金额不能为空！";
        }

        if(StringUtils.isEmpty(lock.getPayMemCode())){
            return "付款方CODE不能为空！";
        }

        if(StringUtils.isEmpty(lock.getRecMemCode())){
            return "收款方CODE不能为空！";
        }
        return null;
    }

    private boolean releaseAll(UnifiedPayReq payReq) {

        return false;
    }




    /**
     *
     *标的保证金锁定
     *
     */
    private UnifiedPayResp getLockDepositResp(UnifiedPayReq payReq) {

        MarginOperationReq lock = new MarginOperationReq();

        try{

            //校验支付订单中是否已有锁定订单
            if(checkExist(payReq)){

                return UnifiedPayResp.fail(PayResultEnums.ORDER_EXIST.getCode(),PayResultEnums.ORDER_EXIST.getDesc());
            }

            LockOrReleaseOrDirectReq req =(LockOrReleaseOrDirectReq)payReq.getPayParam();


            String orderNum = getOrderNum(PayEnums.PAY_ORDER_MARK.LOCK.getType());
            String tradeOrder =getOrderNum(PayEnums.PAY_ORDER_MARK.ORDER.getType());
            lock.setPayAmt(payReq.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            lock.setPayMemCode(req.getPayMemCode());
            lock.setPayMemName(req.getPayMemName());
            lock.setRecMemCode(PayEnums.PAY_TO.PAY_TO_MEM.getType().equals(payReq.getPayTo())?req.getRecMemCode():gatewayProperties.getPayCustomerId());//预招商锁定收款方为平台账号
            lock.setRecMemName(PayEnums.PAY_TO.PAY_TO_MEM.getType().equals(payReq.getPayTo())?req.getRecMemName():gatewayProperties.getPayCompanyName());
            lock.setPayID(orderNum);
            lock.setPayType(PayEnums.DFFT_PAY_CODE.PAY_TYPE_LOCK_DEPOSIT.getType());
            lock.setTradeOrder(tradeOrder);
            lock.setNotifyUrl(gatewayProperties.getNotifyUrl());
            lock.setSummary("保证金锁定");
            PayResp resp = dfftPayService.marginOperation(lock);

            savePayOrder(payReq,resp.getCode(),resp.getDesc(),orderNum,tradeOrder);

            return UnifiedPayResp.payResp(resp.getCode(),resp.getDesc(),payReq.getBusId(),orderNum,null,null);

        }catch (Exception e){

            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.PAY,PayEnums.PAY_TYPE.LOCK_DEPOSIT.getTypeName(),JSON.toJSONString(lock),e);

            logger.error("标的保证金锁定异常，异常接口为{}",e);

            return UnifiedPayResp.sysException();

        }


    }

    /**
     *
     *判断是否有成功时订单
     */
    private boolean checkExist(UnifiedPayReq payReq) {

        GatewayPayOrderCondition condition = new GatewayPayOrderCondition();
        condition.setPayType(payReq.getPayType());
        condition.setPartyId(Long.valueOf(payReq.getPartyId()));
        condition.setBusId(payReq.getBusId());
        condition.setPayStatus(PayResultEnums.PAY_SUCCESS.getCode());

        return  gatewayPayOrderDao.selectFirst(condition)!=null;
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

            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.PAY,PayEnums.PAY_TYPE.SCAN_PAY.getTypeName(),JSON.toJSONString(req),e);

            logger.error("获取微信支付二维码异常,异常信息为：{}",e);
            return UnifiedPayResp.sysException();
        }


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


    /**
     *
     * 保存订单号
     * @param
     * @param
     */
    private void savePayOrder(UnifiedPayReq req,String payStatus,String msg,String orderNum,String tradeOrder) {

        GatewayPayOrder order = new GatewayPayOrder();

        if(PayEnums.PAY_TYPE.LOCK_DEPOSIT.getType().equals(req.getPayType())||PayEnums.PAY_TYPE.RELEASE_DEPOSIT.getType().equals(req.getPayType())||PayEnums.PAY_TYPE.TRANSFER_DEPOSIT.getType().equals(req.getPayType())||PayEnums.PAY_TYPE.DIRECT_PAY.getType().equals(req.getPayType())||PayEnums.PAY_TYPE.BATCH_PAY.getType().equals(req.getPayType())){
            LockOrReleaseOrDirectReq lockOrReleaseOrDirectReq =(LockOrReleaseOrDirectReq)req.getPayParam();
            order.setPayMemCode(PayEnums.WHO_PAY.WEB_PAY.getType().equals(req.getWhoPay())?gatewayProperties.getPayCustomerId():lockOrReleaseOrDirectReq.getPayMemCode());
            order.setPayMemName(PayEnums.WHO_PAY.WEB_PAY.getType().equals(req.getWhoPay())?gatewayProperties.getPayCompanyName():lockOrReleaseOrDirectReq.getPayMemName());
            order.setRecMemCode(PayEnums.PAY_TO.PAY_TO_MEM.getType().equals(req.getPayTo())?lockOrReleaseOrDirectReq.getRecMemCode():gatewayProperties.getPayCustomerId());//预招商锁定收款方为平台账号
            order.setRecMemName(PayEnums.PAY_TO.PAY_TO_MEM.getType().equals(req.getPayTo())?lockOrReleaseOrDirectReq.getRecMemName():gatewayProperties.getPayCompanyName());
            order.setOriginalOrderNum(lockOrReleaseOrDirectReq.getOriginalPayID());
        }
        if(PayEnums.PAY_TYPE.CHANNEL_PAY.getType().equals(req.getPayType())){
            UnifiedChannelPayReq  channelPayReq = (UnifiedChannelPayReq)req.getPayParam();
            order.setPayMemCode(PayEnums.WHO_PAY.WEB_PAY.getType().equals(req.getWhoPay())?gatewayProperties.getPayCustomerId():channelPayReq.getPayMemCode());
            order.setPayMemName(PayEnums.WHO_PAY.WEB_PAY.getType().equals(req.getWhoPay())?gatewayProperties.getPayCompanyName():channelPayReq.getPayMemName());
            order.setRecMemCode(channelPayReq.getAccName());
            order.setRecMemName(channelPayReq.getAccNO());
        }
        order.setAmount(req.getAmount());
        order.setBusId(req.getBusId());
        order.setPartyId(req.getPartyId()==null?null:Long.valueOf(req.getPartyId()));
        order.setPayBusCode(req.getPayBusCode());
        order.setCreateTime(new Date());
        order.setPayType(req.getPayType());
        order.setPayStatus(payStatus);
        order.setMsg(msg);
        order.setLockTag(req.getLockTag());
        order.setOrderNum(orderNum);
        order.setTradeOrderId(tradeOrder);
        order.setJumpPay(req.getJumpPay());
        gatewayPayOrderDao.insert(order);

     }


}
