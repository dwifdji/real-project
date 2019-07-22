package com._360pai.partner.controller.order;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.common.constants.ServiceOrderEnum;
import com._360pai.core.facade.order.ServiceOrderFacade;
import com._360pai.core.facade.order.req.ServiceOrderReq;
import com._360pai.core.facade.order.resp.ServiceOrderResp;
import com._360pai.core.facade.order.resp.ServiceOrderStatusResp;
import com._360pai.partner.controller.AbstractController;
import com._360pai.partner.controller.account.resp.AccountBaseInfo;
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
    @PostMapping(value = "/agency/serviceOrder/queryStatus")
    public ResponseModel queryStatus(@RequestBody ServiceOrderReq.QueryStatus req) {
        //参数校验
        Assert.notNull(req.getServiceOrderId(), "serviceOrderId 参数不对");
        ServiceOrderStatusResp resp = serviceOrderFacade.queryStatus(req);
        return ResponseModel.succ(resp);
    }

    /**
     * 找人处置需求单 支付
     */
    @PostMapping(value = "/agency/serviceOrder/disposalRequirementPay")
    public ResponseModel disposalRequirementPay(@RequestBody ServiceOrderReq.PayReq req) {
        //参数校验
        Assert.notNull(req.getRequirementId(), "requirementId 参数不对");
        ServiceOrderReq.DisposalRequirementPay disposalRequirementPay = new ServiceOrderReq.DisposalRequirementPay();
        List<Integer> list = new ArrayList<>(1);
        list.add(req.getRequirementId());

        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();

        disposalRequirementPay.setDisposalRequirementList(list);
        disposalRequirementPay.setAccountId(accountBaseInfo.getAccountId());
        disposalRequirementPay.setPartyId(accountBaseInfo.getAgencyId());
        ServiceOrderResp resp = serviceOrderFacade.disposalRequirementPay(disposalRequirementPay);
        return ResponseModel.succ(resp);
    }
}
