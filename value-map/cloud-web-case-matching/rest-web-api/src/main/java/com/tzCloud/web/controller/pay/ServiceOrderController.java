package com.tzCloud.web.controller.pay;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.core.facade.order.ServiceOrderFacade;
import com.tzCloud.core.facade.order.req.ServiceOrderReq;
import com.tzCloud.core.facade.order.resp.ServiceConfigResp;
import com.tzCloud.core.facade.order.resp.ServiceOrderResp;
import com.tzCloud.core.facade.order.resp.ServiceOrderStatusResp;
import com.tzCloud.web.controller.AbstractController;
import org.apache.shiro.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiaolei
 * @create 2019-06-19 15:11
 */
@RestController
public class ServiceOrderController extends AbstractController {


    @Reference(version = "1.0.0")
    private ServiceOrderFacade serviceOrderFacade;

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
     *  会员扫码支付
     */
    @PostMapping(value = "/confined/serviceOrder/membershipOpen")
    public ResponseModel membershipOpen(@RequestBody ServiceOrderReq.MembershipOpenReq req) {
        //参数校验
        Assert.notNull(req.getMembershipFeeType(), "membershipType 参数不对");
        req.setAccountId(loadCurLoginId());
        ServiceOrderResp serviceOrderResp = serviceOrderFacade.membershipPay(req);
        return ResponseModel.succ(serviceOrderResp);
    }

    /**
     *  会员扫码支付
     */
    @GetMapping(value = "/open/serviceOrder/membershipFee")
    public ResponseModel membershipFee() {
        List<ServiceConfigResp> membershipConfig = serviceOrderFacade.getMembershipConfig();
        return ResponseModel.succ(membershipConfig);
    }


}
