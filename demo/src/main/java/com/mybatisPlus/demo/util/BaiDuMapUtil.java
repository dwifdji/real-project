package com.mybatisPlus.demo.util;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.mybatisPlus.demo.model.AddressDetail;

public class BaiDuMapUtil {
	
	
	public static AddressDetail getAdressDetail(Map params) {
		params.put("ak", "tI4ZKcyR4WiPNY7Uabu1qNoyzikMdtaw");
		params.put("output", "json");
		
		String result = HttpsUtil.get("http://api.map.baidu.com/geocoder/v2/", params);
		JSONObject jsonObject = null;
		try {
			JSONObject josn = JSONObject.parseObject(result);
			jsonObject = josn.getJSONObject("result").getJSONObject("location");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(result);
		}
		
		
		// 获取真正的经纬度
		String lng = jsonObject.getString("lng");
		String lat = jsonObject.getString("lat");
		if(StringUtils.isNotBlank(lng)) {
			BigDecimal lngDecimal = new BigDecimal(lng);
			lng = lngDecimal.setScale(6, BigDecimal.ROUND_DOWN).toString();
		}
		
		if(StringUtils.isNotBlank(lat)) {
			BigDecimal lngDecimal = new BigDecimal(lat);
			lat = lngDecimal.setScale(6, BigDecimal.ROUND_DOWN).toString();
		}
		
		AddressDetail addressDetail = new AddressDetail();
		addressDetail.setLat(lat);
		addressDetail.setLng(lng);
		addressDetail.setAdress(jsonObject.getString("address"));
		
		return addressDetail;
	}
	
	
	
	public static AddressDetail getAdressDetailFormat(Map params, AddressDetail addressDetail) {
		params.put("ak", "tI4ZKcyR4WiPNY7Uabu1qNoyzikMdtaw");
		params.put("output", "json");
		params.put("pois", "1");
		params.put("latest_admin", "1");

		
		String result = HttpsUtil.get("http://api.map.baidu.com/geocoder/v2/", params);
		
		try {
			JSONObject josn = JSONObject.parseObject(result);
			String formatAddress = josn.getJSONObject("result").getString("formatted_address");
			addressDetail.setAdress(formatAddress);
		} catch (Exception e) {
			System.out.println(result);
			e.printStackTrace();
		}
	
		
		return addressDetail;
	}

}
