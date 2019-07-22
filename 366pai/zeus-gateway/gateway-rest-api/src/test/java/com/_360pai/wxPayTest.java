package com._360pai;

import com._360pai.arch.common.utils.DateUtil;
import com._360pai.gateway.common.dfftpay.PayEnums;
import com._360pai.gateway.controller.req.dfftpay.UnifiedPayReq;
import com._360pai.gateway.controller.req.dfftpay.UnifiedQueryReq;
import com._360pai.gateway.controller.req.dfftpay.WxScanPayReq;
import com._360pai.gateway.controller.req.wx.WXACodeUnLimitReq;
import com._360pai.gateway.controller.req.wxpay.ScanPayReq;
import com._360pai.gateway.facade.PayFacade;
import com._360pai.gateway.facade.WxFacade;
import com._360pai.gateway.facade.WxPayFacade;
import com._360pai.gateway.resp.wxpay.ScanPayResp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


public class wxPayTest extends BaseJunit {

    @Autowired
    private PayFacade payFacade;

    @Autowired
    private WxPayFacade wxPayFacade;


    @Autowired
    private WxFacade wxFacade;

    /**
     *
     * 法大大开通用户接口
     * @param
     * @param
     */
    @Test
    public void wxPayFacadeTest() {

        ScanPayReq  req = new ScanPayReq();

        req.setBody("微信测试");
        req.setTotal_fee("1");
        req.setProduct_id(UUID.randomUUID().toString());
        req.setOut_trade_no(UUID.randomUUID().toString().replaceAll("-",""));
        ScanPayResp resp = wxPayFacade.scanPay(req);

        System.out.println(JSON.toJSONString(resp));
    }





    /**
     *
     * 微信支付测试
     * @param
     * @param
     */
    @Test
    public void payFacade() {

        UnifiedPayReq req = new UnifiedPayReq();

        WxScanPayReq wx = new WxScanPayReq();
        wx.setBody("微信支付测试");
        wx.setOpenId("oTWB75dAt5fhdxHxQrQsomsMcoOw");
        req.setPartyId(111);
        req.setBusId(UUID.randomUUID().toString());
        req.setPayBusCode(PayEnums.PAY_BUS_CODE.ENROLLING_LOCK_DEPOSIT.getType());
        req.setAmount(new BigDecimal(0.01));
        req.setPayType(PayEnums.PAY_TYPE.APPLET_PAY.getType());
        req.setPayParam(wx);
        System.out.println(JSON.toJSONString(payFacade.unifiedPay(req)));
    }



    /**
     * *
     * 统一查询接口
     * @param
     * @param
     */
    @Test
    public void query() {

        UnifiedQueryReq req = new UnifiedQueryReq();

        req.setPayType(PayEnums.PAY_TYPE.SCAN_PAY.getType());
        req.setPayOrder("wx201809111509112722");


         System.out.println(JSON.toJSONString(payFacade.unifiedPayQuery(req)));
    }





    /**
     *
     *获取微信openId1
     */
    @Test
    public void getWxOpenId() {



        System.out.println(JSON.toJSONString(wxFacade.getWxOpenId("120012")));
    }


    /**
     *
     *获取小程序二维码
     */
    @Test
    public void getWXACodeUnLimit() {
        WXACodeUnLimitReq req = new WXACodeUnLimitReq();

        req.setScene("DP1");
        req.setType("1");
        System.out.println(JSON.toJSONString(wxFacade.getWXACodeUnLimit(req)));
    }



    @Test
    public void test() {
        WXACodeUnLimitReq req = new WXACodeUnLimitReq();
BigDecimal AS= new BigDecimal("0.00");

        System.out.println(AS.toString().equals("0.00"));
    }

    @Test
    public void testSendTemplateMsg() {
        try {
            JSONObject params = new JSONObject();
            params.put("projectName", "上海娄山关路债权");
            params.put("annualizedReturn", "15%");
            params.put("income", "285906.34");
            params.put("updateTime", DateUtil.formatNormDate(new Date()));
            wxFacade.sendDebtCalculatorBroadcastTemplateMsg("oqr7y1ZwavYWE3qH-wR3nFgohvI8", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JSONObject params = new JSONObject();
            params.put("projectName", "上海人民广场人民苑债权");
            params.put("todayInterest", "406.17");
            params.put("income", "65906.34");
            params.put("updateTime", DateUtil.formatNormDate(new Date()));
            wxFacade.sendPrincipalInterestCalculatorBroadcastTemplateMsg("oqr7y1ZwavYWE3qH-wR3nFgohvI8", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--");
    }

}
