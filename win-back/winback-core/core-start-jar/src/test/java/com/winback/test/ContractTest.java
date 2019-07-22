package com.winback.test;

import com.winback.core.facade.contract.req.AppContractOrderReq;
import com.winback.core.service.contract.ContractOrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xdrodger
 * @Title: ContractTest
 * @ProjectName winback
 * @Description:
 * @date 2019/2/19 15:05
 */
public class ContractTest extends TestBase {
    @Autowired
    private ContractOrderService orderService;

    @Test
    public void testPayCallBack() {
        try {
            AppContractOrderReq.PayCallBackReq req = new AppContractOrderReq.PayCallBackReq();
            req.setOrderId(9);
            req.setPayOrderId("");
            String orderNo = orderService.payCallBack(req);
            System.out.println(orderNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
