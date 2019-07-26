package com.liuhaolei.dreamer;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.liuhaolei.dreamer.util.UUIDUtil;

public class OpenLawTest {
//	
//	/**
//	 * 裁判文书搜索接口
//	 */
//	@Test
//	public void testJudgementSearch() throws ClientProtocolException, IOException {
//		//初始化参数
//		String url = "http://develop.openlaw.cn/judgement/search?keyword=债权转让";
//		getOpenLowMessage(url);
//	}
//
//	/**
//	 * 获取裁判文书内容
//	 */
//	@Test
//	public void testJudgementDetail() throws ClientProtocolException, IOException {
//		//初始化参数
//		String url = "http://develop.openlaw.cn/judgement/detail?id=e098b918f50445d9968b3a54d2bd8606";
//		getOpenLowMessage(url);
//	}
//	
//	/**
//	 * 法院数据接口
//	 */
//	@Test
//	public void testJudgementCourt() throws ClientProtocolException, IOException {
//		//初始化参数
//		String url = "http://develop.openlaw.cn/judgement/court?court=北京知识产权法院";
//		getOpenLowMessage(url);
//	}
//	
//	/**
//	 * 律所数据接口
//	 */
//	@Test
//	public void testJudgementLawfirm() throws ClientProtocolException, IOException {
//		//初始化参数
//		String url = "http://develop.openlaw.cn/judgement/lawfirm?lawFirmName=中伦律师事务所";
//		getOpenLowMessage(url);
//	}
//	
//	/**
//	 * 律师数据接口
//	 */
//	@Test
//	public void testJudgementLawyer() throws ClientProtocolException, IOException {
//		//初始化参数
//		String url = "http://develop.openlaw.cn/judgement/lawyer?licenseNumber=14114199410539008";
//		getOpenLowMessage(url);
//	}
//	
//	/**
//	 * 当事人数据接口
//	 */
//	@Test
//	public void testJudgementLitigant() throws ClientProtocolException, IOException {
//		//初始化参数
//		String url = "http://develop.openlaw.cn/judgement/litigant?litigant=贾跃亭";
//		getOpenLowMessage(url);
//	}
//	
//	
//	/**
//	 * 	通用分析数据接口
//	 */
//	@Test
//	public void testJudgementJudgement() throws ClientProtocolException, IOException {
//		//初始化参数
//		String url = "http://develop.openlaw.cn/analytic/judgement?keyword=乐视信息&dimension=court,cause";
//		getOpenLowMessage(url);
//	}
//	
//	/**
//	 * 	律所分析数据接口
//	 */
//	@Test
//	public void testJudgementAnalyticLawfirm() throws ClientProtocolException, IOException {
//		//初始化参数
//		String url = "http://develop.openlaw.cn/analytic/lawfirm?licenseNumber=25110200310551859&dimension=court,cause";
//		getOpenLowMessage(url);
//	}
//	
//	
//	/**
//	 * 律师分析数据接口
//	 */
//	@Test
//	public void testJudgementAnalyticLawyer() throws ClientProtocolException, IOException {
//		//初始化参数
//		String url = "http://develop.openlaw.cn/analytic/lawyer?licenseNumber=14114199410539008&dimension=court,cause";
//		getOpenLowMessage(url);
//	}
//	
//	/**
//	 * 法律法规详情数据接口
//	 */
//	@Test
//	public void testJudgementAnalyLawDetail() throws ClientProtocolException, IOException {
//		//初始化参数
//		String url = "http://develop.openlaw.cn/law/detail?id=0001366cb2b14e29b385c67d3285c041";
//		getOpenLowMessage(url);
//	}
//	
//	/**
//	 * 法律法规详情数据接口
//	 */
//	@Test
//	public void testJudgementLawSearch() throws ClientProtocolException, IOException {
//		//初始化参数
//		String url = "http://develop.openlaw.cn/law/search?keyword=故意杀人";
//		getOpenLowMessage(url);
//	}
//	
//	/**
//	 * 法律法规法条内容数据接口
//	 */
//	@Test
//	public void testJudgementLawArticle() throws ClientProtocolException, IOException {
//		//初始化参数
//		String url = "http://develop.openlaw.cn/?title=中华人民共和国行政诉讼法&anchor=62Article";
//		getOpenLowMessage(url);
//	}
//	
//	
//	private void getOpenLowMessage(String url ) throws ClientProtocolException, IOException {
//		final String appKey = "072f23263e4c483c9ee98fabfe93c886";
//		final String appSecret = "645a8123a57247f4aa0b5896192b5a51";
//		String nonce = UUIDutil.getUUID();
//		String dateTime = String.valueOf(new Date().getTime());
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		
//		HttpGet httpget = new HttpGet(url);
//		httpget.addHeader("AppKey", appKey);
//		httpget.addHeader("Nonce", nonce);
//		httpget.addHeader("CurTime", dateTime);
//		String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, dateTime);
//		
//		System.out.println("得到校验码++++++++++++++++++>>>>>>>>>>>>>>>>>" + checkSum);
//		httpget.addHeader("CheckSum", checkSum);
//
//		ResponseHandler responseHandler = new ResponseHandler() {
//
//			@Override
//			public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
//				int status = response.getStatusLine().getStatusCode();
//				if (status >= 200 && status < 300) {
//					HttpEntity entity = response.getEntity();
//					return entity != null ? EntityUtils.toString(entity) : null;
//				} else {
//					throw new ClientProtocolException("Unexpected response status: " + status);
//				}
//			}
//		};
//		String responseBody = (String) httpClient.execute(httpget, responseHandler);
//		System.out.println("调用接口后返回信息>>>>>>>>>>>>>" + responseBody);
//	}
//	
}
