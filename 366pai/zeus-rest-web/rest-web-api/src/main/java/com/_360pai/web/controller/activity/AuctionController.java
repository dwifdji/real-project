package com._360pai.web.controller.activity;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.facade.activity.AuctionFacade;
import com._360pai.core.facade.activity.req.AuctionReq;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author RuQ
 * @Title: AuctionController
 * @ProjectName zeus-rest-web
 * @Description: 拍卖流程
 * @date 2018/9/5 15:11
 */
@RestController
@RequestMapping(value = "/confined/auction", produces = "application/json;charset=UTF-8")
public class AuctionController extends AbstractController {

    public static final Logger LOGGER = LoggerFactory.getLogger(AuctionController.class);

    @Reference(version = "1.0.0")
    private AuctionFacade auctionFacade;



    /**
     * 付保证金
     */
    @PostMapping(value = "/payDeposit")
    public ResponseModel payDeposit(@RequestBody AuctionReq req) {
        LOGGER.info("开始调用 payDeposit,参数:{}", JSON.toJSONString(req));
        if (req == null || req.getActivityId() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(auctionFacade.payDeposit(req));
    }


    /**
     * 出价
     */

    @PostMapping(value = "/bid")
    public ResponseModel bid(@RequestBody AuctionReq req) {
        LOGGER.info("开始调用 bid,参数:{}", JSON.toJSONString(req));
        if (req == null || req.getActivityId() == null || req.getBidAmount() == null
                || req.getBidAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        if (StringUtils.isEmpty(req.getAgencyCode())) {
            req.setAgencyCode(SystemConstant.DEFAULT_AGENCY_CODE);
        }
        return ResponseModel.succ(auctionFacade.bid(req));
    }

    /**
     * 签合同
     */

    @PostMapping(value = "/signContract")
    public ResponseModel signContract(@RequestBody AuctionReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(SystemConstant.ACCOUNT_COMPANY_TYPE.equals(accountBaseInfo.getType()) && !accountBaseInfo.isAdmin()){
            return ResponseModel.fail(ApiCallResult.NOT_COMPANY_ADMIN_ERRPR);
        }
        LOGGER.info("开始调用 signContract,参数:{}", JSON.toJSONString(req));
        if (req == null || req.getOrderId() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(auctionFacade.signContract(req));
    }


    @PostMapping(value = "/signLeaseContract")
    public ResponseModel signLeaseContract(@RequestBody AuctionReq req) {
        LOGGER.info("开始调用 signLeaseContract,参数:{}", JSON.toJSONString(req));
        if (req == null || req.getOrderId() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(auctionFacade.signLeaseContract(req));
    }


    /**
     * 支付
     */

    @PostMapping(value = "/pay")
    public ResponseModel pay(@RequestBody AuctionReq req) {
        LOGGER.info("开始调用 pay,参数:{}", JSON.toJSONString(req));
        if (req == null || req.getOrderId() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());

        return ResponseModel.succ(auctionFacade.pay(req));
    }

    /**
     * 发货
     */

    @PostMapping(value = "/confirmSend")
    public ResponseModel confirmSend(@RequestBody AuctionReq req) {
        LOGGER.info("开始调用 confirmSend,参数:{}", JSON.toJSONString(req));
        if (req == null || req.getOrderId() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());

        return ResponseModel.succ(auctionFacade.confirmSend(req));
    }


    /**
     * 收货
     */

    @PostMapping(value = "/confirmRev")
    public ResponseModel confirmRev(@RequestBody AuctionReq req) {
        LOGGER.info("开始调用 confirmRev,参数:{}", JSON.toJSONString(req));
        if (req == null || req.getOrderId() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());

        return ResponseModel.succ(auctionFacade.revSend(req));
    }



}
