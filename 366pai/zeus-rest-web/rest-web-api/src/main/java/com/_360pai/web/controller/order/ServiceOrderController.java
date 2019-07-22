package com._360pai.web.controller.order;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.common.constants.ServiceOrderEnum;
import com._360pai.core.facade.order.ServiceOrderFacade;
import com._360pai.core.facade.order.req.ServiceOrderReq;
import com._360pai.core.facade.order.resp.ServiceOrderResp;
import com._360pai.core.facade.order.resp.ServiceOrderStatusResp;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述 服务类 支付订单
 *
 * @author : whisky_vip
 * @date : 2018/9/12 14:53
 * @see ServiceOrderEnum
 */
@RestController
public class ServiceOrderController extends AbstractController {

    @Reference(version = "1.0.0")
    ServiceOrderFacade serviceOrderFacade;

    /**
     * 支付订单查询
     */
    @PostMapping(value = "/confined/serviceOrder/queryStatus")
    public ResponseModel queryStatus(@RequestBody ServiceOrderReq.QueryStatus req) {
        //参数校验
        Assert.notNull(req.getServiceOrderId(), "serviceOrderId 参数不对");
        ServiceOrderStatusResp resp = serviceOrderFacade.queryStatus(req);
        return ResponseModel.succ(resp);
    }


    /**
     * 资产大买办需求单 支付
     */
    @PostMapping(value = "/confined/serviceOrder/compradorRequirementPay")
    public ResponseModel compradorRequirementPay(@RequestBody ServiceOrderReq.PayReq req) {
        //参数校验
        Assert.notNull(req.getRequirementId(), "requirementId 参数不对");
        req.setAccountId(loadCurLoginAccountInfo().getAccountId());
        req.setPartyId(loadCurLoginAccountInfo().getPartyPrimaryId());
        ServiceOrderResp resp = serviceOrderFacade.compradorRequirementPay(req);
        return ResponseModel.succ(resp);
    }


    /**
     * 配资乐需求单 支付
     */
    @PostMapping(value = "/confined/serviceOrder/withfudigRequirementPay")
    public ResponseModel withfudigRequirementPay(@RequestBody ServiceOrderReq.PayReq req) {
        //参数校验
        Assert.notNull(req.getRequirementId(), "requirementId 参数不对");
        req.setAccountId(loadCurLoginAccountInfo().getAccountId());
        req.setPartyId(loadCurLoginAccountInfo().getPartyPrimaryId());
        ServiceOrderResp resp = serviceOrderFacade.withfudigRequirementPay(req);
        return ResponseModel.succ(resp);
    }

    /**
     * 找人处置需求单 支付
     */
    @PostMapping(value = "/confined/serviceOrder/disposalRequirementPay")
    public ResponseModel disposalRequirementPay(@RequestBody ServiceOrderReq.PayReq req) {
        //参数校验
        Assert.notNull(req.getRequirementId(), "requirementId 参数不对");
        ServiceOrderReq.DisposalRequirementPay disposalRequirementPay = new ServiceOrderReq.DisposalRequirementPay();
        List<Integer> list = new ArrayList<>(1);
        list.add(req.getRequirementId());
        disposalRequirementPay.setDisposalRequirementList(list);
        disposalRequirementPay.setAccountId(loadCurLoginAccountInfo().getAccountId());
        disposalRequirementPay.setPartyId(loadCurLoginAccountInfo().getPartyPrimaryId());
        ServiceOrderResp resp = serviceOrderFacade.disposalRequirementPay(disposalRequirementPay);
        return ResponseModel.succ(resp);
    }



    /**
     * 基础尽调 支付
     */
    @PostMapping(value = "/confined/serviceOrder/adjustedPay")
    public ResponseModel adjustedPay(@RequestBody ServiceOrderReq.AdjustedPay req) {
        Assert.notNull(req.getRequirementId(), "requirement 参数不对");
        req.setAccountId(loadCurLoginAccountInfo().getAccountId());
        req.setPartyId(loadCurLoginAccountInfo().getPartyPrimaryId());
        req.setActivityId(req.getRequirementId());
        ServiceOrderResp resp = serviceOrderFacade.adjustedPay(req);
        return ResponseModel.succ(resp);
    }

    /**
     * 我要处置 支付
     */
    @PostMapping(value = "/confined/serviceOrder/providerShowPay")
    public ResponseModel providerShowPay(@RequestBody ServiceOrderReq.ProviderShowPay req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        Assert.notNull(req.getActivityId(), "activityId 参数不对");
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        ServiceOrderResp resp = serviceOrderFacade.providerShowPay(req);
        return ResponseModel.succ(resp);
    }

    /**
     * 第三方提供尽调支付
     */
    @PostMapping(value = "/confined/serviceOrder/thirdReportPay")
    public ResponseModel thirdReportPay(@RequestBody ServiceOrderReq.AdjustedPay req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        Assert.notNull(req.getActivityId(), "activityId 参数不对");
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        Map map = serviceOrderFacade.thirdReportPay(req);
        return ResponseModel.succ(map);
    }

}
