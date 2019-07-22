package com.winback.gateway.controller.wx;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.winback.gateway.common.wxpay.XMLUtil;
import com.winback.gateway.controller.req.wxpay.AppPayReq;
import com.winback.gateway.facade.WxPayFacade;
import com.winback.gateway.resp.wxpay.AppPayResp;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;

@RestController
public class WxPayController {

    @Reference(version = "1.0.0")
    private WxPayFacade wxPayFacade;

    @RequestMapping("/open/wxPay/noticeWxPayResult")
    public void noticeWxPayResult(HttpServletRequest request, HttpServletResponse response) {

        try {
            //接收微信支付结果通知的二进制信息并转化为字符串
            String result = getResult(request);
            //将xml信息转化为map结构的数据
            Map<String, String> map = XMLUtil.doXMLParse(result);
            //将map结构的数据转换为json
            Object json = JSONObject.toJSON(map);
//            logger.info("接收到微信支付结果通知，通知参数为{}", json);

            //后续进行业务处理

            //将接收到回调的信息返回给微信服务器
            responseWx(response);
        } catch (Exception e) {
            e.printStackTrace();
//            logger.info("微信回调支付结果数据异常{}", e);
        }
    }

    /**
     * 测试下单并返回预支付id
     * @param
     * @return
     */
    @RequestMapping("/open/wxPay/getPrepaidOrder")
    public AppPayResp getPrepaidOrder(AppPayReq appPayReq) {

        return wxPayFacade.appPay(appPayReq);
    }

    /**
     * 测试根据支付订单号查询支付状态
     * @param
     * @return
     */
    @RequestMapping("/open/wxPay/queryAppPay")
    public AppPayResp queryAppPay(AppPayReq appPayReq) {

        return wxPayFacade.queryAppPay(appPayReq);
    }

    /**
     * 获取请求数据
     */
    private String getResult(HttpServletRequest request) throws Exception {

        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        String result = new String(outSteam.toByteArray(), "utf-8");

        return result;
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
     * 拼装返回参数
     */
    private String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
    }

}
