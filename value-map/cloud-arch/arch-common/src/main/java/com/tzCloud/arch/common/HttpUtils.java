package com.tzCloud.arch.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpUtils {
	/**
	 * 向指定URL发送GET方法的请求
	 *
	 * @param url   发送请求的URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String         result = "";
		BufferedReader in     = null;
		try {
			String urlNameString = url + "?" + param;
			URL    realUrl       = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setConnectTimeout(60000);
			connection.setReadTimeout(60000);

			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				log.debug(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.error("发送GET请求出现异常！" + e);

            throw new RuntimeException(e);
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}




	/**
	 * 向指定URL发送GET方法的请求
	 *
	 * @param url   发送请求的URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static InputStream sendGetInputStream(String url) {
		String         result = "";
		InputStream in     = null;
		try {
			String urlNameString = url;
			URL    realUrl       = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setConnectTimeout(60000);
			connection.setReadTimeout(60000);


			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				log.debug(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in =connection.getInputStream();

		} catch (Exception e) {
			log.error("发送GET请求出现异常！" + e);

			throw new RuntimeException(e);
		}

		return in;
	}








	public static String sendGet(String url) {
		StringBuilder  result = new StringBuilder();
		BufferedReader in     = null;
		try {
			String urlNameString = url;
			URL    realUrl       = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				log.debug(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			log.error("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result.toString();
	}


	/**
	 * 向指定URL发送GET方法的请求
	 *
	 * @param url   发送请求的URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param ,Map<String,String> headParams) {
		String  result = "";
		BufferedReader in     = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setConnectTimeout(60000);
			connection.setReadTimeout(60000);

			if(headParams!=null){
				for(String key : headParams.keySet()){
					connection.setRequestProperty(key, headParams.get(key));
				}
			}
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				log.debug(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.error("发送GET请求出现异常！" + e);

			throw new RuntimeException(e);
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public static String sendGetGBK(String url) {
		StringBuilder  result = new StringBuilder();
		BufferedReader in     = null;
		try {
			String urlNameString = url;
			URL    realUrl       = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setUseCaches(false);
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
//            connection.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            connection    .setRequestProperty("Accept-Encoding","gzip, deflate, br");

			connection.setRequestProperty("content-type", "text/html;charset=GBK");
			connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7,zh-HK;q=0.6");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
			connection.setRequestProperty("Cookie", "v=0; cookie2=1d5d04882f8f2f206538f16277a682f1; t=18dfd4e28ccec99a1bc1b8f4f2068ffa; _tb_token_=34b0d7558333; uc1=cookie14=UoTYM8dfrGnklA%3D%3D");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				log.debug(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "GBK"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			log.error("发送GET请求出现异常！" + e);
            throw new RuntimeException(e);
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result.toString();
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url   发送请求的 URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter    out    = null;
		BufferedReader in     = null;
		String         result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.debug("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally{
			closeIo(out,in);

		}
		return result;
	}


	/**
	 * 发送POST请求
	 *
	 * @param url        目的地址
	 * @param parameters 请求参数，Map类型。
	 * @return 远程响应结果
	 */
	public static String sendPost(String url, Map<String, String> parameters) {
		String         result = "";// 返回的结果
		BufferedReader in     = null;// 读取响应输入流
		PrintWriter    out    = null;
		try {
			String params = getParams(parameters);
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 设置POST方式
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			// 获取HttpURLConnection对象对应的输出流
			out = new PrintWriter(httpConn.getOutputStream());
			// 发送请求参数
			out.write(params);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应，设置编码方式
			in = new BufferedReader(new InputStreamReader(
					httpConn.getInputStream(), "UTF-8"));
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			closeIo(out,in);

		}
		return result;
	}

	private static void closeIo(PrintWriter out, BufferedReader in) {

		try {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	private static String getParams(Map<String,String> parameters) throws IOException{
		String params="";
		StringBuffer sb = new StringBuffer();// 处理请求参数

		// 编码请求参数
		if (parameters.size() == 1) {
			for (String name : parameters.keySet()) {
				sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
			}
			params = sb.toString();
		} else {
			for (String name : parameters.keySet()) {
				sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8")).append("&");
			}
			String temp_params = sb.toString();
			params = temp_params.substring(0, temp_params.length() - 1);
		}

		return params;
	}


	/**
	 * 发送POST请求 返回 BufferedReader
	 *
	 * @param url
	 *            目的地址
	 * @param
 	 * @return 远程响应结果
	 */
	public static InputStream sendPostReader(String url, String rawBody) {

		PrintWriter printWriter=null;
        try {
            URL               conUrl            = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) conUrl.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
			httpURLConnection.setConnectTimeout(60000);
			httpURLConnection.setReadTimeout(60000);
            // 获取URLConnection对象对应的输出流
			printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            printWriter.write(rawBody);
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            return  httpURLConnection.getInputStream();

        } catch (Exception e) {

            throw new RuntimeException(e);

         }finally {
			closeIo(printWriter,null);
		}

	}


	public static void main(String[] args) throws Exception {


//	   String test =HttpUtils.sendPost("http://localhost:8100/api/v1/dfftpay/zj", "name1=value1&name2=value2");

//	   System.out.println(test);
//
//	   String test     =HttpUtils.sendGet("https://sf-item.taobao.com/sf_item/580044135638.htm?spm=a213w.7398504.paiList.1.3a634755Yrmoo6", null);
//	   Document   document = Jsoup.parse(test);
//	   Element    element  = document.getElementById("J_Status");
//	   String ss = element.attr("value");
//	   System.out.println(ss);


//       String test     =HttpUtils.sendGetGBK("https://sf-item.taobao.com/sf_item/580044135638.htm?spm=a213w.7398504.paiList.1.3a634755Yrmoo6");
//	   String test     =HttpUtils.sendGetGBK("https://zc-paimai.taobao.com/zc/mn_detail.htm?spm=a219w.7808064.0.0.31f36e6dEq07GV&id=13094&size=150");
//	   Document   document = Jsoup.parse(test);
//       String text = document.text();
//       System.out.println(test);
//
//       Element    element  = document.getElementById("J_Status");
//	   String ss = element.attr("value");
//	   System.out.println(ss);

//       Map<String,String> cookies = new HashMap<>();
////       v=0; cookie2=1d5d04882f8f2f206538f16277a682f1; t=18dfd4e28ccec99a1bc1b8f4f2068ffa; _tb_token_=34b0d7558333; uc1=cookie14=UoTYM8dfrGnklA%3D%3D
//       cookies.put("v","0");
//       cookies.put("cookie2","1d5d04882f8f2f206538f16277a682f1");
//       cookies.put("t","18dfd4e28ccec99a1bc1b8f4f2068ffa");
//       cookies.put("_tb_token_","34b0d7558333");
//       cookies.put("uc1","cookie14=UoTYM8dfrGnklA%3D%3D%3D%3D");
//       Document doc = Jsoup
//               .connect("https://zc-paimai.taobao.com/zc/mn_detail.htm?spm=a219w.7808064.0.0.31f36e6dEq07GV&id=13094&size=150")
//               .header("Accept","*/*")
//               .header("Accept-Encoding","gzip, deflate, br")
//               .header("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7,zh-HK;q=0.6")
//               .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
//               .cookies(cookies)
//               .timeout(10000).get();
//       String ss = doc.toString();
//       System.out.println(ss);

//       HttpUtilNewModel httpUtilNewModel = HttpUtilNew.getGB2312(HttpUtilNew.commonHeaders(),null, "https://zc-paimai.taobao.com/zc/mn_detail.htm?spm=a219w.7808064.0.0.38b86e6dIxat6j&id=13116&size=150");
//        HttpUtilNewModel httpUtilNewModel = HttpUtilNew.getGB2312(HttpUtilNew.commonHeaders(), null, "https://zc-paimai.taobao.com/zc/mn_list.htm?orderId=1&filterByMerchanting=0&q=" + URLEncoder.encode("【民生银行温州分行】关于温州市奔马机电有限公司的债权转让", "GBK"));
//       System.out.println("httpUtilNewModel = " + httpUtilNewModel.getHtml());
//
//        Document document = Jsoup.parse(httpUtilNewModel.getHtml());
//        Element  ul       = document.getElementById("J_ulProjects");
//        Elements lis      = ul.getElementsByTag("li");

//        for (int i = 1; i < lis.size() + 1; i++) {
//            Elements href   = document.select("#J_ulProjects > li:nth-child(" + i + ") > a");
//            Elements status = document.select("#J_ulProjects > li:nth-child(" + i + ") > a > div.flag-section > div");
//            System.out.println(href.attr("href"));
//            System.out.println(status.attr("class"));
//            System.out.println("-------------");
//        }

//
//       for (Element element : li){
////           System.out.println(element.toString());
//           Elements elementsByAttribute = element.select("//*[@id=\"J_ulProjects\"]/li[1]/a/div[4]/div");
//           System.out.println(elementsByAttribute.toString());
//           System.out.println("----------------");
//       }

		String status = getStatusThirdEnrolling("【民生银行温州分行】关于温州市奔马机电有限公司的债权转让", "https://zc-paimai.taobao.com/zc/mn_detail.htm?spm=a219w.7808064.0.0.17146e6dk5tjUZ&id=13046&size=150");
		System.out.println(status);
	}

	/**
	 * statusStr (roadshow招商中 tobid已转竞价 offline线下已处理 )
	 * @param name
	 * @param url
	 * @return
	 */
	public static String getStatusThirdEnrolling(String name, String url) {
		Map<String, String> stringStringMap = URLRequest(url);
		String id = stringStringMap.get("id");
		System.out.println(stringStringMap);
		String statusStr = "roadshow";
		if (StringUtils.isBlank(id)){
			return statusStr;
		}
		try {
			HttpUtilNewModel httpUtilNewModel = HttpUtilNew.getGB2312(HttpUtilNew.commonHeaders(), null, "https://zc-paimai.taobao.com/zc/mn_list.htm?orderId=1&filterByMerchanting=0&q=" + URLEncoder.encode(name, "GBK"));
			Document         document         = Jsoup.parse(httpUtilNewModel.getHtml());
			Element          ul               = document.getElementById("J_ulProjects");
			Elements         lis              = ul.getElementsByTag("li");

			for (int i = 1; i < lis.size() + 1; i++) {
				Elements href   = document.select("#J_ulProjects > li:nth-child(" + i + ") > a");
				Elements status = document.select("#J_ulProjects > li:nth-child(" + i + ") > a > div.flag-section > div");
				System.out.println("-------------"+URLRequest(href.attr("href")).get("id")+status.attr("class"));
				if (id.equals(URLRequest(href.attr("href")).get("id"))) {
					statusStr = status.attr("class").replace(" ","").replace("flag", "").replace("-","");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusStr;
	}


	public static Map<String, String> URLRequest(String URL) {
		Map<String, String> mapRequest = new HashMap<>();

		String[] arrSplit = null;

		String strUrlParam = truncateUrlPage(URL);
		if (strUrlParam == null) {
			return mapRequest;
		}
//每个键值为一组
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
// System.out.println("----strSplit---"+strSplit);
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");
//    System.out.println("array:--->>>"+arrSplitEqual);
//解析出键值
			if (arrSplitEqual.length > 1) {
//正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

			} else {
				if (arrSplitEqual[0] != "") {
//只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}

	/**
	 *      * 去掉url中的路径，留下请求参数部分
	 *      * @param strURL url地址
	 *      * @return url请求参数部分
	 *      
	 */
	private static String truncateUrlPage(String strURL) {
		String   strAllParam = null;
		String[] arrSplit    = null;

		strURL = strURL.trim().toLowerCase();

		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1) {
			if (arrSplit.length > 1) {
				if (arrSplit[1] != null) {
					strAllParam = arrSplit[1];
				}
			}
		}

		return strAllParam;
	}

}


