package com.mybatisPlus.demo.controller;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.mybatisPlus.demo.model.JdAssetData;
import com.mybatisPlus.demo.model.JdPmTransactionDataAssets;
import com.mybatisPlus.demo.service.JdAssetDataService;
import com.mybatisPlus.demo.service.JdPmTransactionDataAssetsService;
import com.mybatisPlus.demo.util.HttpsUtil;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liuhaolei
 * @since 2019-06-11
 */
@RestController
@RequestMapping("/jdPmTransactionDataAssets")
public class JdPmTransactionDataAssetsController {
	
	@Autowired
	private JdPmTransactionDataAssetsService jdPmTransactionDataAssetsService;
	@Autowired
	private JdAssetDataService jdAssetDataService;
 
	private static final Pattern AREA_COMPILE = Pattern.compile("(建筑面积|建筑面积共计|建筑面积共计约|面积约|建筑总面积|合计|建筑|总计)(.*?)(平方米|㎡|</tr>)| m2）");

	@GetMapping("/getJDTransactionData")
	public String getJDTransactionData() {
	   List<JdPmTransactionDataAssets> jdPmTransactionDataAssets = 
			   jdPmTransactionDataAssetsService.getJDTransactionData();
	    
	    List<JdAssetData> arrayList = new ArrayList<JdAssetData>();
	    try {
			for (int i = 0, len = jdPmTransactionDataAssets.size(); i < len; i++) {
				JdPmTransactionDataAssets jdPmDataAsset = jdPmTransactionDataAssets.get(i);
				Map params = new HashedMap();
				params.put("ak", "iHGsQ1NkqM0rXPt0SuiFcPsOjhc9x57v");
				params.put("output", "json");
	 			params.put("address", jdPmDataAsset.getProvince().replaceAll(" ", "") 
						+ jdPmDataAsset.getCity().replaceAll(" ", "")  + 
						jdPmDataAsset.getCounty().replaceAll(" ", "")  + jdPmDataAsset.getAddress().replaceAll(" ", "") );
				
				String result = HttpsUtil.get("http://api.map.baidu.com/geocoder/v2/", params);
				
				JSONObject josn = JSONObject.parseObject(result);
				JSONObject jsonObject = josn.getJSONObject("result").getJSONObject("location");
				
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
				
				// 获取不同的面积
				String areaResult = HttpsUtil.get("https://paimai.jd.com/"+ jdPmDataAsset.getCode(), null);
				
	 			String buildArea = getBuildArea(areaResult);
	 			
	 			if(StringUtils.isBlank(buildArea)) {
	 				continue;
	 			}
				
	 			JdAssetData jdAssetData = new JdAssetData();
				jdAssetData.setProvince(jdPmDataAsset.getProvince());
				jdAssetData.setCity(jdPmDataAsset.getCity());
				jdAssetData.setCurrentPrice(new BigDecimal(jdPmDataAsset.getCurrentPrice()));
				jdAssetData.setStartPrice(new BigDecimal(jdPmDataAsset.getStartPrice()));
				jdAssetData.setCode(jdPmDataAsset.getCode());
				jdAssetData.setDeleteFlag(0);
				jdAssetData.setAssessmentPrice(new BigDecimal(jdPmDataAsset.getAssessmentPrice()));
				
				jdAssetData.setBuildArea(buildArea == null ? null : new BigDecimal(buildArea));
				
				
				jdAssetData.setLat(lat);
				jdAssetData.setLng(lng);
				jdAssetData.setCreateTime(new Date());
				jdAssetData.setName(jdPmDataAsset.getTitle());
				jdAssetData.setAddress(jdPmDataAsset.getAddress());
				jdAssetData.setCategoryName(jdPmDataAsset.getCategoryName());
				jdAssetData.setCountry(jdPmDataAsset.getCounty());
	 			
				arrayList.add(jdAssetData);
			}
	    } catch (Exception e) {
			e.printStackTrace();
		}	
		
		jdAssetDataService.insertBatch(arrayList);
		return null;
	}

	
	private String getBuildArea(String areaResult) {
		JXDocument jxNode = JXDocument.create(areaResult);
		JXNode selNOne = jxNode.selNOne("//input[@id='albumId']/@value");
		JXNode productId = jxNode.selNOne("//input[@id='skuId']/@value");

		String areaResultSt = HttpsUtil.get("https://paimai.jd.com/json/current/queryAlbumAnnouncement?albumId="+selNOne.toString(), null);

 		try {
 			String getfilterArea = getfilterArea(areaResultSt);
 			if(StringUtils.isBlank(getfilterArea)) {
 				areaResultSt = HttpsUtil.get("https://paimai.jd.com/json/paimaiProduct/productDesciption?productId="+productId.toString(), null);
 				getfilterArea = getfilterArea(areaResultSt);
 			}
 			return getfilterArea;
		} catch (Exception e) {
			System.out.println(e.getMessage());
 		}
		return null;
	}
	
	
	private String getfilterArea(String noticeInfo) {

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
	            return matcher1.group(0);
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

