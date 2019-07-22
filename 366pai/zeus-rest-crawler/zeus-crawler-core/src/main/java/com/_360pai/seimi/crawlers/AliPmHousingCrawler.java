package com._360pai.seimi.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.model.AliPmHouseLoan;
import com._360pai.seimi.service.AliPmHouseService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.seimicrawler.xpath.JXDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create by liuaholei on 2018/12/06
 */
@Component
@Crawler(name = "alihouse", useUnrepeated = false)
public class AliPmHousingCrawler extends BaseSeimiCrawler {
	
	private final String baseUrl = "https://sf.taobao.com/item_list.htm?";

	private final String loanUrl = "https://paimai.taobao.com/loan/json/getLoanInfoList.do?itemId=";

    private final String auctionUrl = "https://paimai.taobao.com/loan/json/getAuctionInfo.do?itemId=";

    private static final Pattern COMPILE = Pattern.compile("\\{\"data\":\\[(.*?)]}");

    @Autowired
    private AliPmHouseService aliPmHouseService;
	
    @Override
    public String[] startUrls() {
        return  null;
    }

    @Override
    public void start(Response response) {
        String url = response.getUrl();

        try {
            for (int i = 1; i < 151; i++) {
                logger.info("已经开始爬取第{}", i + " 页");
                push(Request.build(url + "&page=" + i, AliPmHousingCrawler::getPageHtml));
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

	}
    
    
    public void getPageHtml(Response response){
        String content = response.getContent();

        String doc = content.replace("\n", "");
        Matcher regexData = COMPILE.matcher(doc);

        String dataString =  "";
        if (regexData.find()) {
            dataString = regexData.group(0);
        }

        if(StringUtils.isNoneBlank(dataString)) {
            JSONObject json = JSON.parseObject(dataString);
            String data = json.getString("data");

            JSONArray jsonArrays = JSON.parseArray(data);

            for(int i = 0, len = jsonArrays.size(); i < len; i++) {
                JSONObject jsonObject = jsonArrays.getJSONObject(i);
                String detailUrl = jsonObject.getString("itemUrl");
                push(Request.build( "https:" + detailUrl, AliPmHousingCrawler::getDetailHtml));
            }

        }
    }

    public void getDetailHtml(Response response) {

        JXDocument doc = response.document();
        String loanHtmlUrl = "https:" + getStringValue(doc.selOne("//div[@class='pm-bid-right']/input[1]/@value"));
        String citySt = getStringValue(doc.selOne("//div[@id='itemAddress']/text()"));
        String cityDetailSt = getStringValue(doc.selOne("//div[@id='itemAddressDetail']/text()"));

        String itemId = loanHtmlUrl.substring(loanHtmlUrl.indexOf("=") + 1, loanHtmlUrl.length());

        CloseableHttpClient httpClient = HttpClients.custom().build();
        HttpGet getLoanList = new HttpGet(loanUrl + itemId);
        HttpGet getAuction = new HttpGet(auctionUrl + itemId);
        CloseableHttpResponse execute = null;
        try {
            execute = httpClient.execute(getAuction);
            String responseAuction = EntityUtils.toString(execute.getEntity());

            JSONObject jsonObject = JSONObject.parseObject(responseAuction);
            String status = jsonObject.getString("status");
            if("1".equals(status)) {
                JSONObject item = JSONObject.parseObject(jsonObject.getString("item"));
                String title = item.getString("title");
                String currentPrice = item.getString("currentPrice");
                String picUrl = item.getString("picUrl");

                Integer aliPmHouse = aliPmHouseService.getAliPmHouseByTitle(title);
                if(aliPmHouse == null || aliPmHouse == 0) {
                    execute = httpClient.execute(getLoanList);
                    String responseLoan = EntityUtils.toString(execute.getEntity());

                    setLoanList(responseLoan, title, currentPrice, picUrl, citySt, cityDetailSt, createUUID());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取详情数据错误{}", e.getMessage());
        }
    }


    private void setLoanList(String response, String title, String currentPrice, String picUrl,
                             String city, String cityDetail, String groupSt) {
        JSONObject json = JSONObject.parseObject(response);
        JSONArray loanList = JSONObject.parseArray(json.getString("loanList"));

        String[] allCity = city.split(" ");
        ArrayList<AliPmHouseLoan> aliPmHouses = new ArrayList<>();
        for(int i = 0, len = loanList.size(); i <len; i++) {
            JSONObject newJson = loanList.getJSONObject(i);
            AliPmHouseLoan aliPmHouse = new AliPmHouseLoan();
            aliPmHouse.setActivityName(title);
            aliPmHouse.setHouseType("商业用房");
            aliPmHouse.setActivityPrice(currentPrice);
            aliPmHouse.setProvince(allCity[0]);
            aliPmHouse.setActivityCity(allCity[1]);
            aliPmHouse.setArea(allCity[2]);
            aliPmHouse.setPicUrl(picUrl);
            aliPmHouse.setCityItem(cityDetail);
            aliPmHouse.setTypeGroup(groupSt);
            aliPmHouse.setAgency(newJson.getString("orgName"));
            aliPmHouse.setLoanRatio(newJson.getString("loanPercent") + "%");
            aliPmHouse.setInterestRate(newJson.getString("rateDiscount") + "倍");
            aliPmHouse.setOtherFee(newJson.getString("exchangeFee"));
            aliPmHouses.add(aliPmHouse);
        }

        aliPmHouseService.saveAliPmHouseList(aliPmHouses);
    }


    private String getStringValue(Object object) {
    	if(object == null) {
    		return "";
    	}else {
    		return object.toString();
    	}
    }

    private String createUUID(){
        String uuid = UUID.randomUUID().toString();	//获取UUID并转化为String对象
        uuid = uuid.replace("-", "");				//因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
        return uuid;
    }

}
