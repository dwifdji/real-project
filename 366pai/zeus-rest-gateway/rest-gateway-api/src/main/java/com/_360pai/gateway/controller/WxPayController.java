package com._360pai.gateway.controller;

import com._360pai.core.facade.enrolling.EnrollingWebFacade;
import com._360pai.core.facade.enrolling.req.EnrollingReq;
import com._360pai.core.facade.order.ServiceOrderCallBackFacade;
import com._360pai.gateway.common.dfftpay.PayEnums;
import com._360pai.gateway.common.dfftpay.PayResultEnums;
import com._360pai.gateway.common.wxpay.XMLUtil;
import com._360pai.gateway.controller.req.dfftpay.UnifiedPayResp;
import com._360pai.gateway.controller.req.wxpay.ScanResultNoticeReq;
import com._360pai.gateway.facade.PayFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;

@Controller
@RequestMapping("/wxpay")
public class WxPayController {


    private final Logger logger = LoggerFactory.getLogger(WxPayController.class);

    @Reference(version = "1.0.0")
    private PayFacade payFacade;

    @Reference(version = "1.0.0")
    ServiceOrderCallBackFacade serviceOrderCallBackFacade;


    @Reference(version = "1.0.0")
    EnrollingWebFacade enrollingWebFacade;

    @ResponseBody
    @RequestMapping(value = "/payNotifyUrl")
    public void payNotifyUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String result = getResult(request);

        logger.info("接收到微信支付结果通知，通知参数为{}", result);

        Map<String, String> map = XMLUtil.doXMLParse(result);

        UnifiedPayResp resp = getScanResultNoticeResp(map);

        if(PayResultEnums.PAY_SUCCESS.getCode().equals(resp.getCode())){
            //返回结果支付结果给业务调用方
            //抵押物订单发布回调
            if(PayEnums.PAY_BUS_CODE.ENROLLING_COMMISSION_PAY.getType().equals(resp.getPayBusType())){
                logger.info("微信回调预招商，回调参数为：{}",JSON.toJSONString(resp));
                if(PayResultEnums.PAY_SUCCESS.getCode().equals(resp.getCode())){
                    EnrollingReq.submitOrder req = new EnrollingReq.submitOrder();
                    req.setActivityId(resp.getBusId());
                    req.setOrderNum(resp.getPayOrder());
                    req.setPayStatus(resp.getCode());
                    enrollingWebFacade.submitOrderCallBack(req);
                }
            }else{

                serviceOrderCallBackFacade.doCallBackProcess(Integer.valueOf(resp.getBusId()),resp.getCode(),resp.getDesc());

            }
        }

        //响应结果返回微信端
        responseWx(response);
    }

    /**
     * 给微信返回响应结果
     */
    private void responseWx(HttpServletResponse response) throws Exception {

        // 支付成功，商户处理后同步返回给微信参数
        PrintWriter writer = response.getWriter();

        // 通知微信已经收到消息，不要再给我发消息了，否则微信会8连击调用本接口
        String noticeStr = setXML("SUCCESS", "");
        writer.write(noticeStr);
        writer.flush();

    }


    /**
     * 根据通知更新支付订单
     */
    private UnifiedPayResp getScanResultNoticeResp(Map<String, String> map) {

        ScanResultNoticeReq req = new ScanResultNoticeReq();

        req.setReturnCode(map.get("return_code"));
        req.setReturnMsg(map.get("return_msg"));
        req.setResultCode(map.get("result_code"));
        req.setOutTradeNo(map.get("out_trade_no"));
        req.setTotalFee(map.get("total_fee"));
        req.setErrCodeDes(map.get("err_code_des"));
        return payFacade.scanResultNotice(req);

    }


    /**
     * 获取请求数据
     */
    private String getResult(HttpServletRequest request) throws Exception {

        InputStream           inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[]                buffer   = new byte[1024];
        int                   len      = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        String result = new String(outSteam.toByteArray(), "utf-8");

        return result;
    }

    /**
     * 拼装返回参数
     */
    public static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
    }

}
