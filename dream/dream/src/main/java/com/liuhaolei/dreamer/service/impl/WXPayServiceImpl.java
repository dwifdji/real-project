package com.liuhaolei.dreamer.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.liuhaolei.dreamer.common.WxPayResultEnums;
import com.liuhaolei.dreamer.common.res.ResponseModel;
import com.liuhaolei.dreamer.common.res.ResultStatus;
import com.liuhaolei.dreamer.conf.MyWXPayConfig;
import com.liuhaolei.dreamer.conf.WXPayProperties;
import com.liuhaolei.dreamer.service.WXPayService;
import com.liuhaolei.dreamer.util.XMLUtil;

@Service
public class WXPayServiceImpl implements WXPayService{
	
	@Autowired
	private WXPayProperties wxPayProperties;
	
    private  final Logger logger = LoggerFactory.getLogger(WXPayServiceImpl.class);
	
	@Override
	public ResponseModel appPayOrder(String fee) {
		WXPay wxpay = getWXPay(wxPayProperties);
		if(wxpay == null) {
			return ResponseModel.failApi(ResultStatus.INIT_WX_ERROR);
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params = getParams(params, fee);
		
		logger.info("发起微信支付请求，请求参数为：{}",JSON.toJSONString(params));
        //发起支付
		Map<String, String> resp = new HashMap<>();
		try {
			resp  = wxpay.unifiedOrder(params);
			
			//处理返回结果，根据不同的结果返回给app
			getScanPayResp(resp,wxpay,"1");
			
			logger.info("微信支付返回参数为：{}",JSON.toJSONString(resp));
		} catch (Exception e) {
			logger.error("调用支付订单异常{}", e.getMessage());
		}
       
        //处理信息返回给前台
		return null;
	}
	
	
	private void getScanPayResp(Map<String, String> resp, WXPay wxpay, String type) throws Exception {
		//生成预支付订单接口
		 if(WxPayResultEnums.RETURN_CODE_SUCCESS.getCode().equals(resp.get("return_code"))){
	            //返回验签
	            if(wxpay.isResponseSignatureValid(resp)){
	               logger.info("调用成功且验证签名成功");
	            }
	            //业务请求响应成功
		 }
	}

	
	//初始化参数
	private Map<String, String> getParams(Map<String, String> params, String fee) {
		params.put("body", "测试的app-调用一下");
		params.put("out_trade_no", UUID.randomUUID().toString().replaceAll("-",""));
		params.put("fee_type", "CNY");
		params.put("total_fee", fee);
		params.put("spbill_create_ip", "127.0.0.1");
		params.put("notify_url", wxPayProperties.getWxPayNotifyUrl());
		params.put("trade_type", "APP");  // 此处指定为扫码支付
		params.put("product_id", UUID.randomUUID().toString().replaceAll("-",""));
		params.put("nonce_str", UUID.randomUUID().toString().replaceAll("-",""));
		return params;
	}


	private WXPay getWXPay(WXPayProperties wxPayProperties2) {
		
		try {
			WXPayConfig config = new MyWXPayConfig(wxPayProperties);
	        WXPay wxpay = new WXPay(config);
	    	return wxpay;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("初始化支付参数异常{}", e.getMessage());
		}
		return null;
	}

	
	//支付成功之后回调本地接口并将成功收到的信息返回给微信服务器
	@Override
	public void getPayStatus(HttpServletRequest req, HttpServletResponse res) {
		try {
			//接收微信支付结果通知的二进制信息并转化为字符串
			String result = getResult(req);
			//将xml信息转化为map结构的数据
			Map<String, String> map = XMLUtil.doXMLParse(result);
			//将map结构的数据转换为json
			Object json = JSONObject.toJSON(map);
			logger.info("接收到微信支付结果通知，通知参数为{}", json);
			//将接收到回调的信息返回给微信服务器
			responseWx(res);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
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
    public static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
    }

}
