package com._360pai.core.service.impl;


import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.aspact.GatewayExceptionEmailService;
import com._360pai.core.common.constants.ExceptionEmailEnum;
import com._360pai.core.condition.pay.GatewayPayBackRecordCondition;
import com._360pai.core.condition.pay.GatewayPayMemberCondition;
import com._360pai.core.dao.pay.*;
import com._360pai.core.model.DfftPay.*;
import com._360pai.core.model.pay.*;
import com._360pai.core.service.DfftPayService;
import com._360pai.gateway.common.dfftpay.DfftPayUtils;
import com._360pai.gateway.common.dfftpay.PayEnums;
import com._360pai.gateway.common.dfftpay.PayResultEnums;
import com._360pai.gateway.resp.BindMemberResp;
import com._360pai.gateway.resp.QueryBalanceResp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.easterpay.util.EastPayUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 *
 * 描述：东方付通接口实现类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 16:25
 */
@Service
public class DfftPayServiceImpl extends  GatewayExceptionEmailService implements DfftPayService {

    private  final Logger logger = LoggerFactory.getLogger(AliSmsServiceImpl.class);



    @Autowired
    private GatewayPayChannelPayDao gatewayPayChannelPayDao;

    @Autowired
    private GatewayPayDirectLockPayDao gatewayPayDirectLockPayDao;

    @Autowired
    private GatewayPayMemberDao gatewayPayMemberDao;

    @Autowired
    private GatewayPayMarginOperationDao gatewayPayMarginOperationDao;

    @Autowired
    private GatewayPayRecordDao gatewayPayRecordDao;

    @Autowired
    private GatewayProperties gatewayProperties;


    @Autowired
    private GatewayPayBackRecordDao gatewayPayBackRecordDao;





    @Override
    public BindMemberResp bindMember(BindMemberReq req) {

        BindMemberResp commResp = new BindMemberResp();
        try{
            //组装参数
            Map<String,String> map = DfftPayDataAssemble.getBindMemberData(req);

            //调用东方付通
            ResponseModel resp = DfftPayUtils.sign(gatewayProperties,map,"single");

            commResp.setCode(resp.getCode());
            commResp.setDesc(resp.getDesc());
            commResp.setOrder(resp.getContent().toString());
            commResp.setUrl(gatewayProperties.getPayUrl());
            //将请求入库
            new Thread(()->bindMemberRecord(req,resp)).start();

         }catch (Exception e){
            //发送异常邮件
            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.PAY,"开通账户",JSON.toJSONString(req),e);

            logger.error("开通东方付通异常，异常信息为：{}",e);
            commResp.setCode(PayResultEnums.SYS_EXCEPTION.getCode());
            commResp.setDesc(PayResultEnums.SYS_EXCEPTION.getDesc());
        }


         return commResp;
    }




    @Override
    public QueryBindMemberResp queryBindMember(QueryBindMemberReq req) {
        QueryBindMemberResp queryBindMemberResp = new QueryBindMemberResp();

        try {
            //查询开户记录表
            GatewayPayMemberCondition condition = new GatewayPayMemberCondition();

            condition.setMemCode(req.getMemCode());
            condition.setMemName(req.getMemName());
            GatewayPayMember member = gatewayPayMemberDao.selectFirst(condition);

            if(member==null){
                queryBindMemberResp.setCode(PayResultEnums.QUERY_EMPTY.getCode());
                queryBindMemberResp.setDesc(PayResultEnums.QUERY_EMPTY.getDesc());
                return queryBindMemberResp;
            }
            //已绑定直接返回
            if(PayResultEnums.MEM_BOUND.getCode().equals(member.getStatus())){

                queryBindMemberResp.setCode(PayResultEnums.MEM_BOUND.getCode());
                queryBindMemberResp.setDesc(PayResultEnums.MEM_BOUND.getDesc());
                return queryBindMemberResp;
            }


            ResponseModel resp = DfftPayUtils.requestPay(gatewayProperties,DfftPayDataAssemble.getQueryBindMemberData(req));

            queryBindMemberResp = getQueryResp(resp,queryBindMemberResp);

            updateMemberStatus(req,queryBindMemberResp,member);
        }catch (Exception e){
            queryBindMemberResp.setCode(PayResultEnums.SYS_EXCEPTION.getCode());
            queryBindMemberResp.setDesc(PayResultEnums.SYS_EXCEPTION.getDesc());

            //发送异常邮件
            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.PAY,"查询用户绑定记录",JSON.toJSONString(req),e);
            logger.error("查询开户记录异常，异常信息为：{}",e);

        }






        return queryBindMemberResp;
    }

    //记录查询更新转态
    private void updateMemberStatus(QueryBindMemberReq req, QueryBindMemberResp queryBindMemberResp,GatewayPayMember member) {
        //请求记录
        commRecord(req.getMemCode(),"queryBindMember",JSON.toJSONString(req),JSON.toJSONString(queryBindMemberResp));

        member.setStatus(queryBindMemberResp.getCode());
        member.setMsg(queryBindMemberResp.getDesc());
        member.setUpdateTime(new Date());

        gatewayPayMemberDao.updateById(member);

    }


    //获取账户信息
    @Override
    public QueryBalanceResp queryBalance(QueryBindMemberReq req) {
        QueryBalanceResp balanceResp = new QueryBalanceResp();

        try {
            ResponseModel resp = DfftPayUtils.requestPay(gatewayProperties,DfftPayDataAssemble.getQueryBalaceData(req));


            balanceResp.setCode(resp.getCode());
            balanceResp.setDesc(resp.getDesc());

            //当请求成功时
            if(PayResultEnums.REQUEST_SUCCESS.getCode().equals(resp.getCode())){

                String content = String.valueOf(resp.getContent());
                JSONObject obj=JSON.parseObject(content);
                balanceResp.setCode(String.valueOf(obj.get("payStatus")));
                balanceResp.setDesc(String.valueOf(obj.get("payMessage")));
                balanceResp.setFreeAmt(String.valueOf(obj.get("freeAmt")));
                balanceResp.setTotalAmt(String.valueOf(obj.get("totalAmt")));
                balanceResp.setLockedAmt(String.valueOf(obj.get("lockedAmt")));

            }
        }catch (Exception e){

            balanceResp.setCode(PayResultEnums.SYS_EXCEPTION.getCode());
            balanceResp.setCode(PayResultEnums.SYS_EXCEPTION.getDesc());
            //发送异常邮件
            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.PAY,"查询账户信息",JSON.toJSONString(req),e);

            logger.error("查询账户信息异常，异常信息为：{}",e);
        }





        return balanceResp;



     }


    @Override
    public PayResp directOrLockPay(DirectOrLockPayReq req) {
        PayResp payResp = new PayResp();

        try {
            //直接支付不跳转
            if(PayEnums.JUMP_PAY_TYPE.AUTO_PAY.getType().equals(req.getJumpPay())){

                ResponseModel resp = DfftPayUtils.requestPay(gatewayProperties,DfftPayDataAssemble.getDirectOrLockPayData(req));
                payResp =getPayResp(resp);

                directOrLockPayRecord(req,payResp);

                return  payResp;
            }

            //组装参数以及请求东方富通
            ResponseModel resp = DfftPayUtils.sign(gatewayProperties,DfftPayDataAssemble.getDirectOrLockPayData(req),"single");

            payResp.setCode(resp.getCode());
            payResp.setDesc(resp.getDesc());
            payResp.setPayID(resp.getContent().toString());
            //请求记录入库
            directOrLockPayRecord(req,payResp);
        }catch (Exception e){
            payResp.setCode(PayResultEnums.SYS_EXCEPTION.getCode());
            payResp.setDesc(PayResultEnums.SYS_EXCEPTION.getDesc());
            //发送异常邮件
            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.PAY,"直接支付",JSON.toJSONString(req),e);
            logger.error("直接支付异常，异常信息为：{}",e);
        }



        return payResp;
    }

    @Override
    public PayResp BatchDirectOrLockPay(List<DirectOrLockPayReq> reqList) {


        PayResp payResp = new PayResp();

        try{

            List<Map<String, String>> encodeOrder = new ArrayList<>(reqList.size());


            for(DirectOrLockPayReq payReq :reqList){

                ResponseModel resp = DfftPayUtils.sign(gatewayProperties,DfftPayDataAssemble.getDirectOrLockPayData(payReq),"batch");
                payResp.setCode(resp.getCode());
                payResp.setDesc(resp.getDesc());
                //请求记录入库
                directOrLockPayRecord(payReq,payResp);
                encodeOrder.add((Map<String, String>)resp.getContent());
            }


            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(encodeOrder);

            payResp.setCode(PayResultEnums.PAY_NOTICE.getCode());
            payResp.setDesc(PayResultEnums.PAY_NOTICE.getDesc());
            payResp.setPayID(URLEncoder.encode(EastPayUtil.encodebase64(json),"UTF-8"));

            return payResp;
        }catch (Exception e){

            //发送异常邮件
            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.PAY,PayEnums.PAY_TYPE.BATCH_PAY.getTypeName(),JSON.toJSONString(reqList),e);

            logger.error("批量支付异常异常信息为{}",e);

            payResp.setCode(PayResultEnums.SYS_EXCEPTION.getCode());
            payResp.setCode(PayResultEnums.SYS_EXCEPTION.getDesc());
        }

        return payResp;
    }


    @Override
    public PayResp marginOperation(MarginOperationReq req) {
        PayResp payResp = new PayResp();
        try {
            //保证金参数组装以及请求 获取返回参数
            ResponseModel resp = DfftPayUtils.requestPay(gatewayProperties,DfftPayDataAssemble.getMarginOperationData(req));


            payResp = getPayResp(resp);
            //保证金类操作请求入库
            marginOperationRecord(req,payResp);
        }catch (Exception e){
            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.PAY,"保证金操作",JSON.toJSONString(req),e);

            payResp.setCode(PayResultEnums.SYS_EXCEPTION.getCode());
            payResp.setDesc(PayResultEnums.SYS_EXCEPTION.getDesc());

            logger.error("保证金操作异常，异常信息为：{}",e);
        }



        return payResp;
    }



    @Override
    public PayResp channelPay(ChannelPayReq req) {
        PayResp payResp = new PayResp();

        try {
            //通道支付请求
            ResponseModel resp = DfftPayUtils.requestPay(gatewayProperties,DfftPayDataAssemble.getChannelPayData(req));


            payResp = getPayResp(resp);
            //通道支付请求入库
            channelPayRecord(req,payResp);
        }catch (Exception e){

            //发送异常邮件
            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.PAY,PayEnums.PAY_TYPE.CHANNEL_PAY.getTypeName(),JSON.toJSONString(req),e);

            payResp.setCode(PayResultEnums.SYS_EXCEPTION.getCode());
            payResp.setDesc(PayResultEnums.SYS_EXCEPTION.getDesc());

            logger.error("渠道支付异常，异常信息为：{}",e);

        }


        return payResp;
    }


    @Override
    public PayResp fenRunPay(FenRunPayReq req) {
        PayResp payResp = new PayResp();

        try {
            ResponseModel resp = DfftPayUtils.requestPay(gatewayProperties,DfftPayDataAssemble.getFenRunPayData(req));

            payResp = new PayResp();
        }catch (Exception e){
            //发送异常邮件
            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.PAY,"分润支付",JSON.toJSONString(req),e);

            payResp.setCode(PayResultEnums.SYS_EXCEPTION.getCode());
            payResp.setDesc(PayResultEnums.SYS_EXCEPTION.getDesc());

            logger.error("分润支付异常，异常信息为：{}",e);
        }



        return payResp;
    }

    /**
     * 查询订单状态
     *
     * @param payId 请求参数
     */
    @Override
    public CommResp queryOrder(String payId) {
        CommResp commResp = new CommResp();

        try {
            ResponseModel resp = DfftPayUtils.requestPay(gatewayProperties,DfftPayDataAssemble.getQueryOrderMap(payId));

            return getQueryPayResp(resp);

        }catch (Exception e){

            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.PAY,"查询订单状态",payId,e);

            logger.error("订单查询状态异常，异常信息为：{}",e);
            commResp.setCode(PayResultEnums.SYS_EXCEPTION.getCode());
            commResp.setDesc(PayResultEnums.SYS_EXCEPTION.getDesc());

        }


        return commResp;

    }

    @Override
    public void saveCallBackInfo(GatewayPayBackRecord req) {

        GatewayPayBackRecordCondition condition = new GatewayPayBackRecordCondition();
        condition.setOrderNum(req.getOrderNum());
        condition.setPayStatus(req.getPayStatus());

        if(gatewayPayBackRecordDao.selectFirst(condition)==null){

            gatewayPayBackRecordDao.insert(req);

        }

    }

    @Override
    public void updateCallBackInfo(GatewayPayBackRecord req) {

        GatewayPayBackRecordCondition condition = new GatewayPayBackRecordCondition();
        condition.setOrderNum(req.getOrderNum());


        GatewayPayBackRecord payBackRecord = gatewayPayBackRecordDao.selectFirst(condition);
        if(payBackRecord==null){
            return;
        }
        payBackRecord.setCoreStatus(req.getCoreStatus());
        payBackRecord.setGatewayStatus(req.getGatewayStatus());
        payBackRecord.setUpdateTime(new Date());

        gatewayPayBackRecordDao.updateById(payBackRecord);

    }



    private CommResp getQueryPayResp(ResponseModel resp) {

        CommResp queryResp = new CommResp();
        queryResp.setCode(resp.getCode());
        queryResp.setDesc(resp.getDesc());
        //当请求成功时
        if(PayResultEnums.REQUEST_SUCCESS.getCode().equals(resp.getCode())){

            String content = String.valueOf(resp.getContent());
            JSONObject obj=JSON.parseObject(content);

            String status = String.valueOf(obj.get("status"));
            //已支付状态
            if("0001".equals(status)){
                queryResp.setCode(PayResultEnums.PAY_SUCCESS.getCode());
                queryResp.setDesc(PayResultEnums.PAY_SUCCESS.getDesc());

            }else if("04".equals(status)){
                queryResp.setCode(PayResultEnums.PAY_FAILURE.getCode());
                queryResp.setDesc(PayResultEnums.PAY_FAILURE.getDesc());
            }else{

                queryResp.setCode(PayResultEnums.PAY_NOTICE.getCode());
                queryResp.setDesc(PayResultEnums.PAY_NOTICE.getDesc());
            }
         }
        return queryResp;

    }


    /**
     * 东方付通开户请求记录
     *
     * @param req 请求参数
     * @param resp resp 返回参数
      */
    private void bindMemberRecord(BindMemberReq req, ResponseModel resp) {


        commRecord(req.getMemCode(),"bindMember",JSON.toJSONString(req),JSON.toJSONString(resp));

        try{

            GatewayPayMember member = new GatewayPayMember();
            member.setMemCode(req.getMemCode());
            member.setMemName(req.getMemName());
            member.setMemType(req.getPayMemType());
            member.setPayType("09020");
            member.setStatus(resp.getCode());
            member.setMsg(resp.getDesc());
            member.setCreateTime(new Date());
            gatewayPayMemberDao.insert(member);

        }catch (Exception e){

            logger.error("东方付通开户请求记录异常，异常信息为：{}",e);
        }



    }

    /**
     * 东方付通请求统一记录表
     *
     * @param busId 业务关键记录
     * @param
     */
    private void commRecord(String busId,String payType,String requestParam,String responseParam) {

        try{

            GatewayPayRecord record = new GatewayPayRecord();
            record.setBusId(busId);
            record.setPayType(payType);
            record.setRequestParam(requestParam);
            record.setResponseParam(responseParam);
            record.setCreateTime(new Date());

            gatewayPayRecordDao.insert(record);

        }catch (Exception e){

            logger.error("东方付通统一记录异常，异常信息为：{}",e);
        }

    }

    /**
     * 东方付通直接或锁定支付记录
     *
     * @param req 请求参数
     * @param payResp  返回参数
     */
    private void directOrLockPayRecord(DirectOrLockPayReq req, PayResp payResp) {

        commRecord(req.getPayID(),req.getPayType(),JSON.toJSONString(req),JSON.toJSONString(payResp));

        try{
            GatewayPayDirectLockPay pay = new GatewayPayDirectLockPay();
            pay.setBankAccount(req.getBankAccount());
            pay.setBankUse(req.getBankUse());
            pay.setCreateTime(new Date());
            pay.setLockTag(req.getLocktag());
            pay.setPayAmt(req.getPayMemType());
            pay.setPayId(req.getPayID());
            pay.setOriginalPayId(req.getOriginalPayID());
            pay.setPayMemCode(req.getPayMemCode());
            pay.setPayMemName(req.getPayMemName());
            pay.setRecMemCode(req.getRecMemCode());
            pay.setRecMemName(req.getRecMemName());
            pay.setPayMemType(req.getPayMemType());
            pay.setStatus(payResp.getCode());
            pay.setMsg(payResp.getDesc());
            gatewayPayDirectLockPayDao.insert(pay);

        }catch (Exception e){

            logger.error("插入东方付通直接或锁定支付记录异常，异常信息为：{}",e);
        }

    }

    /**
     * 东方付通保证金操作记录
     *
     * @param req 请求参数
     * @param payResp  返回参数
     */
    private void marginOperationRecord(MarginOperationReq req, PayResp payResp) {

        commRecord(req.getPayID(),req.getPayType(),JSON.toJSONString(req),JSON.toJSONString(payResp));

        try{
            GatewayPayMarginOperation pay = new GatewayPayMarginOperation();
            pay.setAuditFlag(req.getAuditFlag());
            pay.setCreateTime(new Date());
            pay.setMsg(payResp.getDesc());
            pay.setOriginalPayId(req.getOriginalPayID());
            pay.setPayAmt(req.getPayAmt());
            pay.setPayMemCode(req.getPayMemCode());
            pay.setPayMemName(req.getPayMemName());
            pay.setRecMemCode(req.getRecMemCode());
            pay.setRecMemName(req.getRecMemName());
            pay.setPayId(req.getPayID());
            pay.setPayType(req.getPayType());
            pay.setStatus(payResp.getCode());
            gatewayPayMarginOperationDao.insert(pay);
        }catch (Exception e){
            logger.error("记录东方付通保证金记录异常，异常信息为：{}",e);
        }

    }

    /**
     * 东方付通通道支付请求记录
     *
     * @param req 请求参数
     * @param payResp  返回参数
     */
    private void channelPayRecord(ChannelPayReq req, PayResp payResp ) {

        commRecord(req.getPayID(),req.getPayType(),JSON.toJSONString(req),JSON.toJSONString(payResp));

        try{
            GatewayPayChannelPay pay = new GatewayPayChannelPay();

            pay.setCreateTime(new Date());
            pay.setMsg(payResp.getDesc());
            pay.setOriginalPayId(req.getOriginalPayID());
            pay.setPayAmt(req.getPayAmt());
            pay.setPayMemCode(req.getPayMemCode());
            pay.setPayMemName(req.getPayMemName());
            pay.setPayId(req.getPayID());
            pay.setPayType(req.getPayType());
            pay.setStatus(payResp.getCode());
            pay.setMsg(payResp.getDesc());
            gatewayPayChannelPayDao.insert(pay);
        }catch (Exception e){
            logger.error("插入通道支付记录异常，异常信息为：{}",e);
        }

    }

    /**
     *
     *支付类返回参数解析
     * @param resp 请求的返回参数
     */
    private PayResp getPayResp(ResponseModel resp) {

        PayResp payResp = new PayResp();
        payResp.setCode(resp.getCode());
        payResp.setDesc(resp.getDesc());

        //当请求成功时
        if(PayResultEnums.REQUEST_SUCCESS.getCode().equals(resp.getCode())){

            String content = String.valueOf(resp.getContent());
            JSONObject obj=JSON.parseObject(content);
            payResp.setCode(String.valueOf(obj.get("payStatus")));
            payResp.setDesc(String.valueOf(obj.get("payMessage")));
            payResp.setPayID(String.valueOf(obj.get("payID")));
        }

        return payResp;
    }

    /**
     *
     * 查询绑定记录返回
     * @param resp 请求的返回参数
     */
    private QueryBindMemberResp getQueryResp(ResponseModel resp,QueryBindMemberResp queryBindMemberResp) {
        queryBindMemberResp.setCode(resp.getCode());
        queryBindMemberResp.setDesc(resp.getDesc());
        //当请求成功时
        if(PayResultEnums.REQUEST_SUCCESS.getCode().equals(resp.getCode())){

            String content = String.valueOf(resp.getContent());
            JSONObject obj=JSON.parseObject(content);
            queryBindMemberResp.setCode(String.valueOf(obj.get("payStatus")));
            queryBindMemberResp.setDesc(String.valueOf(obj.get("payMessage")));
            queryBindMemberResp.setMemCode(String.valueOf(obj.get("memCode")));
            queryBindMemberResp.setMemName(String.valueOf(obj.get("memName")));
        }

        return queryBindMemberResp;
    }



    @Override
    public void queryOrderQuartz() {

    }
}
