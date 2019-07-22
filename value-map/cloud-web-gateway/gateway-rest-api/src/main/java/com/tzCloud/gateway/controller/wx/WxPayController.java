package com.tzCloud.gateway.controller.wx;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tzCloud.core.facade.order.ServiceOrderCallBackFacade;
import com.tzCloud.gateway.common.constants.PayEnums;
import com.tzCloud.gateway.common.constants.PayResultEnums;
import com.tzCloud.gateway.common.wxpay.XMLUtil;
import com.tzCloud.gateway.controller.req.pay.UnifiedPayResp;
import com.tzCloud.gateway.controller.req.wxpay.AppPayReq;
import com.tzCloud.gateway.controller.req.wxpay.ScanResultNoticeReq;
import com.tzCloud.gateway.facade.PayFacade;
import com.tzCloud.gateway.facade.WxPayFacade;
import com.tzCloud.gateway.resp.wxpay.AppPayResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;

@RestController
public class WxPayController {


    private final Logger logger = LoggerFactory.getLogger(WxPayController.class);

    @Reference(version = "1.0.0")
    private PayFacade payFacade;

    @Reference(version = "1.0.0")
    ServiceOrderCallBackFacade serviceOrderCallBackFacade;

    @ResponseBody
    @RequestMapping(value = "/payNotifyUrl")
    public void payNotifyUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String result = getResult(request);

        logger.info("接收到微信支付结果通知，通知参数为{}", result);

        Map<String, String> map = XMLUtil.doXMLParse(result);

        UnifiedPayResp resp = getScanResultNoticeResp(map);

        if(PayResultEnums.PAY_SUCCESS.getCode().equals(resp.getCode())){
            serviceOrderCallBackFacade.doCallBackProcess(Integer.valueOf(resp.getBusId()),resp.getCode(),resp.getDesc());
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
