package com.tzCloud;

import com.gexin.fastjson.JSON;
import com.tzCloud.gateway.common.constants.PayEnum;
import com.tzCloud.gateway.controller.req.pay.PayReq;
import com.tzCloud.gateway.facade.PayFacade;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;


/**
 * @author wuchuanqi
 * @create 2018-11-29 16:00
 */
public class PayTest extends TestBase{

    @Resource
    private PayFacade payFacade;

    /**
     * 微信app支付
     *
     */
    @Test
    public void unifyPay() {


        PayReq req = new PayReq();

        req.setAccountId(1);
        req.setBusinessType(PayEnum.BUSINESS_TYPE.APP_PAY);
        req.setType(PayEnum.PAY_TYPE.WX_PAY);
        req.setAmount(new BigDecimal("0.01"));
        req.setBusinessId("wx20190130001");
        req.setOrderDesc("APP支付");
        //System.out.println("返回参数为....."+JSON.toJSONString(payFacade.unifyPay(req)));
    }





    @Test
    public void wxAppletPay() {


        PayReq req = new PayReq();

        req.setAccountId(1);
        req.setBusinessType(PayEnum.BUSINESS_TYPE.APPLET_PAY);
        req.setType(PayEnum.PAY_TYPE.WX_PAY);
        req.setAmount(new BigDecimal("0.01"));
        req.setBusinessId("wx20190130001");
        req.setOrderDesc("小程序支付");
        //System.out.println("返回参数为....."+JSON.toJSONString(payFacade.unifyPay(req)));
    }








    /**
     * 支付宝app支付
     *
     */
    @Test
    public void aliUnifyPay() {


        PayReq req = new PayReq();

        req.setAccountId(1);
        req.setBusinessType(PayEnum.BUSINESS_TYPE.APP_PAY);
        req.setType(PayEnum.PAY_TYPE.ALI_PAY);
        req.setAmount(new BigDecimal("0.01"));
        req.setBusinessId("ali20190130001");
        req.setOrderDesc("APP支付");
        //System.out.println("返回参数为....."+JSON.toJSONString(payFacade.unifyPay(req)));
    }








}




