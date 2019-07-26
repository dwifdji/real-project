package com._360pai.crawler.common.util;

import com._360pai.crawler.common.dto.AddressDetail;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaiDuMapUtil {

	private static final Pattern AREA_COMPILE = Pattern.compile("(一、拍卖标的|建筑面积|建筑面积共计|建筑面积共计约|面积约|建筑总面积|合计|建筑|总计|（<)(.*?)(平方米|㎡|</tr>)| m2）");


	public static AddressDetail getAdressDetail(Map params) {
		params.put("ak", "iHGsQ1NkqM0rXPt0SuiFcPsOjhc9x57v");
		params.put("output", "json");
		
		String result = HttpsUtil.get("http://api.map.baidu.com/geocoder/v2/", params);
		
		JSONObject json = JSONObject.parseObject(result);
        JSONObject jsonModel = json.getJSONObject("result");
        if(jsonModel == null) {
            return null;
        }

        AddressDetail addressDetail = new AddressDetail();
        JSONObject jsonObject = jsonModel.getJSONObject("location");
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
		addressDetail.setLat(lat);
		addressDetail.setLng(lng);
		addressDetail.setAdress(jsonObject.getString("address"));
		
		return addressDetail;
	}
	
	
	
	public static AddressDetail getAdressDetailFormat(Map params, AddressDetail addressDetail) {
		params.put("ak", "iHGsQ1NkqM0rXPt0SuiFcPsOjhc9x57v");
		params.put("output", "json");
		params.put("pois", "1");
		params.put("latest_admin", "1");

		
		String result = HttpsUtil.get("http://api.map.baidu.com/geocoder/v2/", params);
		
		JSONObject josn = JSONObject.parseObject(result);
		String formatAddress = josn.getJSONObject("result").getString("formatted_address");
		addressDetail.setAdress(formatAddress);
		
		return addressDetail;
	}



	public static String getfilterArea(String noticeInfo) {

		String doc = noticeInfo.replace("\n", "");

		String text = null;
		Matcher matcher = AREA_COMPILE.matcher(doc);
		if (matcher.find()) {
			text = matcher.group(2);
			int countStr = countStr(text, "</td>\\r\\n");
			if(countStr == 1) {
				text = text.substring(text.lastIndexOf("'>") + 2, text.length());
			} else if(countStr == 2){
				text = text.substring(text.lastIndexOf("'>") + 2, text.lastIndexOf("</td>"));
			}
			Pattern pattern = Pattern.compile("([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])");
			Matcher matcher1 = pattern.matcher(text);

			if (matcher1.find()) {
			    return matcher.group(0);
			}

		}

		return text;
	}

	/**
	 * 判断str1中包含str2的个数
	 * @param str1
	 * @param str2
	 * @return counter
	 */
	public static int countStr(String str1, String str2) {
		int counter = 0;
		if (str1.indexOf(str2) == -1) {
			return 0;
		} else if (str1.indexOf(str2) != -1) {
			counter++;
			countStr(str1.substring(str1.indexOf(str2) +
					str2.length()), str2);
			return counter;
		}
		return 0;
	}
}
