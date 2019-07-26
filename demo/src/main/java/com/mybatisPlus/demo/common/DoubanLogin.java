package com.mybatisPlus.demo.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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

import com.alibaba.fastjson.JSONObject;

/**
 * 思路
 * 1:请求验证码的路径获取验证码token以url
 * 2:拼接验证码图片请求路径从而获取验证码图片
 * 3:
 * @author L1553
 *
 */
public class DoubanLogin {
	
	private static final String IMAGETOKENURL = "https://www.douban.com/j/misc/captcha"; 
	private static final String LOGINURL = "https://www.douban.com/accounts/login";
	private static final String DEMOURL = "https://www.douban.com/people/187586127/";
	/**
	 * 爬取带有验证码的信息
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public void loginDouban(String token, CloseableHttpClient httpClient) 
			throws ClientProtocolException, IOException {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入验证码");
		String captcha_solution = sc.nextLine();
		
		HttpPost post = new HttpPost(LOGINURL);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("form_email", "15538068782@163.com"));
		params.add(new BasicNameValuePair("form_password", "bit15538068782@"));
		params.add(new BasicNameValuePair("source", "index_nav"));
		params.add(new BasicNameValuePair("captcha-solution", captcha_solution));
		params.add(new BasicNameValuePair("captcha-id", token));
		post.setEntity(new UrlEncodedFormEntity(params));
		
		httpClient.execute(post);
		
		HttpGet getPersionMessage = new HttpGet(DEMOURL);
		CloseableHttpResponse execute = httpClient.execute(getPersionMessage);
		String responseDate = EntityUtils.toString(execute.getEntity());
		System.out.println("输出的最终页面请求是" + responseDate);
		
	}
	
	/**
	 * 获取验证码图片
	 */
	@SuppressWarnings("static-access")
	public String getImage(CloseableHttpClient httpClient) throws ClientProtocolException, IOException {
		
		HttpGet imageTokenGet = new HttpGet(IMAGETOKENURL);
		CloseableHttpResponse execute = httpClient.execute(imageTokenGet);
		String imageTokenUrl = EntityUtils.toString(execute.getEntity());
		
		imageTokenUrl = StringEscapeUtils.unescapeJavaScript(imageTokenUrl);
		JSONObject json = (JSONObject) JSONObject.parse(imageTokenUrl);
		String imageUrl = json.getString("url");
		String token = json.getString("token");
		
		downloadImage(imageUrl, httpClient);
		return token;
	}
	
	/**
	 * 下载图片
	 */
	public void downloadImage(String imageUrl, CloseableHttpClient httpClient) {
		InputStream in = null;
		FileOutputStream out = null;
		File file = null;
		
		try {
			HttpGet imageGet = new HttpGet("https:" + imageUrl);
			CloseableHttpResponse imageExecute = httpClient.execute(imageGet);
			
			in = imageExecute.getEntity().getContent();
			file = new File("D:/douban/images");
			if (!file.exists()) {
				file.mkdirs();
			}
			file  = new File("D:/douban/images/1212.jpg");
			out = new FileOutputStream(file);
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			
			if(in != null) {
				in.close();
			}
			if(out != null) {
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}

	
	public static void main(String[] args) {
		//获取验证码图片路径
		
		try {
			CookieStore cookieStore = new BasicCookieStore();
			CloseableHttpClient httpClient = HttpClients.custom()
					.setDefaultCookieStore(cookieStore).create().build();
			
			DoubanLogin doubanLogin = new DoubanLogin();
			
			String token = doubanLogin.getImage(httpClient);
			
			doubanLogin.loginDouban(token, httpClient);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
