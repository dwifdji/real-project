package com._360pai.seimi.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.model.TInvestor;
import com._360pai.seimi.model.TServiceElite;
import com._360pai.seimi.model.TTransactionData;
import com._360pai.seimi.service.InvestorService;
import com._360pai.seimi.service.ServiceEliteService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by liuaholei on 2018/11/14
 */
@Component
@Crawler(name = "investors", useUnrepeated = false)
public class InvestorsCrawler extends BaseSeimiCrawler {
	
	@Autowired
	private InvestorService investorService;
	
	private final String baseUrl = "http://www.azichan.com";
	private final String pageUrl  = "http://www.azichan.com/lawyer/list.html?laType=1";
	private final String cityUrl = "http://www.azichan.com/allnotice/list.html";

	@Override
    public String[] startUrls() {
        return new String[]{" "};
    }

    @Override
    public void start(Response response) {

        try {

			CookieStore cookieStore = new BasicCookieStore();
			CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
			//模拟登陆A资产登陆
			HttpPost httpPost = new HttpPost("http://www.azichan.com/login.do");
			List<NameValuePair> nameValuePairs = new ArrayList<>();
			nameValuePairs.add(new BasicNameValuePair("account", "15538068782"));   //自己用户名
			nameValuePairs.add(new BasicNameValuePair("pwd", "bit789654"));//自己密码

			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			List<TTransactionData> tTransactionList = null;
			HttpGet get = new HttpGet(cityUrl);

			CloseableHttpResponse execute = httpClient.execute(get);
			String html = EntityUtils.toString(execute.getEntity());
			JXDocument doc = JXDocument.create(html);

			List<JXNode> jxNodes = doc.selN("//div[@class = 'filterList borderBg']/ul/li[1]/div[2]/em");


			for(int i = 1; i < jxNodes.size(); i++) {
				JXNode jxNode = jxNodes.get(i);
				String cityId = jxNode.sel("/@id").get(0).toString();
				String cityName = jxNode.sel("/text()").get(0).toString();
				System.out.println("最终的区域名称是" + cityName);

				String[] cityIdArray = cityId.split("_");
				String realCity = cityIdArray[1];

				String jsonSt = "{\"provinceId\":"+realCity+"}";
				String findUrl = pageUrl + "&lawyerQuery=" + jsonSt;

				URL url = new URL(findUrl);
				URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);

				HttpGet getCity = new HttpGet(uri);

				CloseableHttpResponse executeCity = httpClient.execute(getCity);
				String cityHtml = EntityUtils.toString(executeCity.getEntity());


				String rapalceHtml = cityHtml.replaceAll(" ", "");

//				System.out.println("最终的总数是" + rapalceHtml);
				int totalIndex = rapalceHtml.indexOf("responsePage('/lawyer/list.html',1,");
				int totalLastIndex = rapalceHtml.indexOf(",pageSize,5,'prevPageId','nextPageId'");
				int total = Integer.parseInt(rapalceHtml.substring(totalIndex + 35, totalLastIndex));

				for (int j = 1; j < total / 10 + 2; j++ ) {
					System.out.println("开始爬取"+ cityName +"第" + j + "页数据");
					//封装数据并传参数
					Map<String, Object> params = response.getMeta();
					params.put("cityName", cityName);

					String newUrl = findUrl + "&page=" + j;
					URL newurl = new URL(newUrl);
					URI newuri = new URI(newurl.getProtocol(), newurl.getHost(), newurl.getPath(), newurl.getQuery(), null);
					push(Request.build(newuri.toString(), InvestorsCrawler::getPageHtml).setMeta(params));
					Thread.sleep(1000);
				}
			}


        } catch (Exception e) {
            e.printStackTrace();
			logger.error("调用分页数据异常{}", e.getMessage());
		}
    }
    
    
    public void getPageHtml(Response response){
        JXDocument doc = response.document();

		try {
        	List<JXNode> nodes = doc.selN("//ul[@class='organizations clearfix']/li");



			for (JXNode jxNode : nodes) {
				List<JXNode> nameList = jxNode.sel("/div/div[2]/h3/b/text()");
				String realName = nameList.get(0).toString();

				TInvestor tInvestor = investorService.getInvestorByName(realName);
				if(tInvestor == null) {
					List<JXNode> urlSt = jxNode.sel("/@onclick");
					String detailUrl = urlSt.get(0).toString();
					String url = baseUrl + detailUrl.substring(13, detailUrl.length() - 2);

					push(Request.build(url,InvestorsCrawler::getDetailHtml).setMeta(response.getMeta()));
					Thread.sleep(1000);
				}
			}
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理分页详情数据异常{}", e.getMessage());
        }
    }
    
    /**
     * 获取详情数据并进行封装
     * @param response
     */
    public void getDetailHtml(Response response) {
    	 JXDocument doc = response.document();
    	 try {
			String investorName = getStringValue(doc.selNOne("//h2[@class = 'orgsName mt20']/text()"));
			String compantName = getStringValue(doc.selNOne("//a[@class = 'v2_person_org']/text()"));
//			String cityName = getStringValue(doc.selNOne("//div[@class = 'plr50 basicInfos clearfix']/ul/li[1]/text()"));

			Map<String, Object> meta = response.getMeta();
			String cityName = meta.get("cityName").toString();
			TInvestor tInvestor = new TInvestor();
			tInvestor.setCityName(cityName);
			tInvestor.setCompanyName(compantName);
			tInvestor.setInvestorName(investorName);
	
			investorService.saveInvestor(tInvestor);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取具体详情数据异常{}", e.getMessage());
		}
    }
    
    
    private String getStringValue(Object object) {
    	if(object == null) {
    		return "";
    	}else {
    		return object.toString();
    	}
    }

}
