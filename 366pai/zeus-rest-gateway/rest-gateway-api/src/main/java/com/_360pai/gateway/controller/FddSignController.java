package com._360pai.gateway.controller;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.facade.account.AcctFacade;
import com._360pai.core.facade.activity.ActivityFacade;
import com._360pai.core.facade.activity.AuctionFacade;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.enrolling.EnrollingWebFacade;
import com._360pai.core.facade.enrolling.req.EnrollingReq;
import com._360pai.gateway.common.fddSignature.FddEnums;
import com._360pai.gateway.controller.req.fdd.FddCallBackReq;
import com._360pai.gateway.controller.req.fdd.GatewayFddCallBackRecordReq;
import com._360pai.gateway.controller.req.fdd.GenerateContractResp;
import com._360pai.gateway.facade.FddSignatureFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 描述：法大大签章回调
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/28 15:24
 */
@RestController
@RequestMapping(value ="/fdd")
public class FddSignController {

    private final Logger logger = LoggerFactory.getLogger(FddSignController.class);

    @Reference(version = "1.0.0")
    private FddSignatureFacade fddSignatureFacade;


    @Reference(version = "1.0.0")
    private AuctionFacade auctionFacade;


    @Reference(version = "1.0.0")
    private ActivityFacade activityFacade;


    @Reference(version = "1.0.0")
    private EnrollingWebFacade enrollingWebFacade;


    @Reference(version = "1.0.0")
    private AcctFacade acctFacade;


    @PostMapping("/callBack")
    public void scanResultNotice(FddCallBackReq req) {

        logger.info("接收到法大大异步通知请求，请求参数为{}",JSON.toJSONString(req));

        //校验是否已经回调了
        if(checkExist(req)){
            return;
        }

        try {
            //自动签章返回结果处理
            GenerateContractResp resp =fddSignatureFacade.extSignNotify(req);

            logger.info("gateway处理法大大返回字段为：{}",JSON.toJSONString(resp));

            //gateway处理完毕根据返回记录表
            updateCallBack(resp,"1");

            // 成交确认协议返回
            if(FddEnums.SING_TYPE.DEAL.getType().equals(resp.getSignType())||FddEnums.SING_TYPE.LEASE_DEAL.getType().equals(resp.getSignType())||FddEnums.SING_TYPE.LEASE_WITHOUT_LICENSE.getType().equals(resp.getSignType())||FddEnums.SING_TYPE.LEASE_HAS_LICENSE.getType().equals(resp.getSignType())){

                logger.info("成交协议回调业务方,调用参数为{}",JSON.toJSONString(resp));
                //成交协议返回处理
                dealCallBack(resp);

            }else if(FddEnums.SING_TYPE.DELEGATION.getType().equals(resp.getSignType())||FddEnums.SING_TYPE.BANK_DELEGATION.getType().equals(resp.getSignType())||FddEnums.SING_TYPE.OFFLINE_DELEGATION.getType().equals(resp.getSignType())||FddEnums.SING_TYPE.BANK_OFFLINE_DELEGATION.getType().equals(resp.getSignType())||FddEnums.SING_TYPE.LEASE_DELEGATION.getType().equals(resp.getSignType())){
                logger.info("委托协议、银行类协议回调业务方,调用参数为{}",JSON.toJSONString(resp));
                //委托协议处理
                delegationCallBack(resp);

             }else if(FddEnums.SING_TYPE.ENROLLING_DELEGATION.getType().equals(resp.getSignType())||FddEnums.SING_TYPE.ZHAIQUAN_DELEGATION.getType().equals(resp.getSignType())||FddEnums.SING_TYPE.WUQUAN_DELEGATION.getType().equals(resp.getSignType())){

                logger.info("预招商回调业务方,回调参数为{}",JSON.toJSONString(resp));
                //预招商回调
                enrollingCallBack(resp);

                //补充协议
            }else if(FddEnums.SING_TYPE.ADDITIONAL.getType().equals(resp.getSignType())){
                logger.info("补充协议回调业务方,回调参数为{}",JSON.toJSONString(resp));


                additionalCallBack(resp);

                //咨询服务合同
            }else if (FddEnums.SING_TYPE.SERVICE_ADVISORY.getType().equals(resp.getSignType())){
                logger.info("咨询服务合同回调业务方,回调参数为{}",JSON.toJSONString(resp));


                serviceAdvisoryCallBack(resp);
            }

        }catch (Exception e){

            logger.error("法大大签章回调处理异常，异常信息为：{}",e);

        }



     }

    private void serviceAdvisoryCallBack(GenerateContractResp resp) {

        try {

            acctFacade.signCallBack(resp.getActivityId(),resp.getContractId(),ApiCallResult.SUCCESS.getCode().equals(resp.getCode()));

        }catch (Exception e){

            logger.info("咨询服务合同通知业务方异常，等待Job重新发起，通知参数为：{},异常信息为：{}",JSON.toJSONString(resp),e);

        }
        updateCallBack(resp,"");

    }

    /**
     *
     *补充协议回调
     */
    private void additionalCallBack(GenerateContractResp resp) {

        try {
            ActivityReq.SignAdditionalAgreementCallBackReq  backReq = new ActivityReq.SignAdditionalAgreementCallBackReq();
            backReq.setActivityId(Integer.valueOf(resp.getActivityId()));
            backReq.setContractId(resp.getContractId());
            backReq.setSellerPartyId(Integer.valueOf(resp.getPartyId()));
            backReq.setHasSuccess(ApiCallResult.SUCCESS.getCode().equals(resp.getCode()));

            activityFacade.signAdditionalAgreementCallBack(backReq);
        }catch (Exception e){

            logger.info("补充协议通知业务方异常，等待Job重新发起，通知参数为：{},异常信息为：{}",JSON.toJSONString(resp),e);

        }
        updateCallBack(resp,"");

    }

    /**
     *
     *
     *gateway处理完毕更新记录表
     */
    private void updateCallBack(GenerateContractResp resp,String type) {

        //gateway处理成功 更新转态
        GatewayFddCallBackRecordReq req = new GatewayFddCallBackRecordReq();

        req.setContractId(resp.getContractId());
        req.setTransactionId(resp.getTransactionId());
        if("1".equals(type)){
            req.setGatewayStatus(resp.getCode());
        }else{
            req.setCoreStatus(resp.getCode());
        }

        fddSignatureFacade.updateCallBackInfo(req);

    }

    /**
     *
     *预招商回调
     */
    private void enrollingCallBack(GenerateContractResp resp) {

        try {

            EnrollingReq.signCallBack signCallBack = new EnrollingReq.signCallBack();
            signCallBack.setActivityId(resp.getActivityId());
            signCallBack.setPartyId(resp.getPartyId());
            signCallBack.setContractId(resp.getContractId());
            signCallBack.setStatus(resp.getCode());
            enrollingWebFacade.signCallBack(signCallBack);

        }catch (Exception e){

            logger.info("预招商协议通知业务方异常，等待Job重新发起，通知参数为：{},异常信息为：{}",JSON.toJSONString(resp),e);
        }

        updateCallBack(resp,"");

    }

    /**
     *
     *委托协议
     */
    private void delegationCallBack(GenerateContractResp resp) {


        try {

            ActivityReq.SignDelegationAgreementCallBackReq callBackReq = new ActivityReq.SignDelegationAgreementCallBackReq();

            callBackReq.setActivityId(Integer.valueOf(resp.getActivityId()));
            callBackReq.setContractId(resp.getContractId());
            callBackReq.setHasSuccess(ApiCallResult.SUCCESS.getCode().equals(resp.getCode()));
            callBackReq.setSellerPartyId(Integer.valueOf(resp.getPartyId()));
            activityFacade.signDelegationAgreementCallBack(callBackReq);

        }catch (Exception e){

            logger.info("委托协议通知业务方异常，等待Job重新发起，通知参数为：{},异常信息为：{}",JSON.toJSONString(resp),e);
        }

        updateCallBack(resp,"");


    }

    /**
      *
      *成交协议处理
      */
    private void dealCallBack(GenerateContractResp resp) {

        try {

            auctionFacade.signCallBack(resp.getSignRole(),Integer.valueOf(resp.getPartyId()),Integer.valueOf(resp.getActivityId()),resp.getContractId(),ApiCallResult.SUCCESS.getCode().equals(resp.getCode()));

        }catch (Exception e){

            logger.info("成交协议通知业务方异常，等待Job重新发起，通知参数为：{},异常信息为：{}",JSON.toJSONString(resp),e);
        }


        updateCallBack(resp,"");
    }

    /**
      *
      *校验回调记录是否已经存在
      *
      */
    private boolean checkExist(FddCallBackReq req) {
        //只记录委托人、买受人的签章回调 自动签章回调不记录
        if(!(req.getTransaction_id().contains(FddEnums.TRANSACTION_TYPE.BUYER.getType())||req.getTransaction_id().contains(FddEnums.TRANSACTION_TYPE.SELLER.getType()))){
            return true;
        }

        try {
            //添加回调记录
            GatewayFddCallBackRecordReq callBackRecordReq = new GatewayFddCallBackRecordReq();
            callBackRecordReq.setContractId(req.getContract_id());
            callBackRecordReq.setTransactionId(req.getTransaction_id());
            callBackRecordReq.setCreatedAt(new Date());
            callBackRecordReq.setReqparam(JSON.toJSONString(req));
            callBackRecordReq.setType("2".equals(req.getType())?"2":"1");
            ResponseModel resp =fddSignatureFacade.saveCallBackInfo(callBackRecordReq);

            if(ApiCallResult.SUCCESS.getCode().equals(resp.getCode())){
                return false;
            }

        }catch (Exception e){

            logger.error("插入法大大回调记录异常，异常信息为{}",e);
        }

        return false;
    }


}
