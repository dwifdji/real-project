package com.tzCloud.core.utils;


import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.tzCloud.arch.common.HttpUtils;
import com.tzCloud.arch.core.sysconfig.properties.GatewayProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 *百度地图工具类
 */
@Slf4j
public class BaiDuMapUtils {

	//private final static String ak = "tI4ZKcyR4WiPNY7Uabu1qNoyzikMdtaw";

	private final static String ak = "zMKIgj8LPGKssRqXQIuVG81sE5fcG6NY";




	/**
	 * 根据ip获取百度坐标位置
	 *
	 * ip : 请求ip
	 *
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String getLocationByIp(String ip,GatewayProperties gatewayProperties) {

		String url = "http://api.map.baidu.com/location/ip";

		StringBuffer param = new StringBuffer();
		param.append("ip=");
		param.append(ip);
		param.append("&ak=");
		param.append(ak);
		param.append("&coor=bd09ll");

		return HttpUtils.sendGet(url,param.toString());

	}



	/**
	 * 根据ip获取百度坐标位置
	 *
	 * ip : 请求ip
	 *
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String getLocationDetail(String uid,GatewayProperties gatewayProperties) {

		String url = "http://api.map.baidu.com/place/v2/detail";

		StringBuffer param = new StringBuffer();
		param.append("uid=");
		param.append(uid);
		param.append("&ak=");
		param.append(ak);
		param.append("&output=json&scope=2");

		return HttpUtils.sendGet(url,param.toString());

	}





	/**
	 * 地址提示信息
	 *
	 * ip : 请求ip
	 *
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String getSearchKeyList(String searchKey,GatewayProperties gatewayProperties) {

		String url = "http://api.map.baidu.com/place/v2/suggestion";

		StringBuffer param = new StringBuffer();
		param.append("query=");
		param.append(searchKey);
		param.append("&region=全国");
		param.append("&output=json");
		param.append("&ak=");
		param.append(ak);

		return HttpUtils.sendGet(url,param.toString());

	}




	/**
	 * 地址提示信息
	 *
	 * ip : 请求ip
	 *
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String getFactorInfoList(String query,String tag, String lat,String lng,Integer pageSize ,Integer pageNum ,String scope, GatewayProperties gatewayProperties,String radius) {

		String url = "http://api.map.baidu.com/place/v2/search";

		StringBuffer param = new StringBuffer();
		param.append("query=");
		param.append(tag);
        param.append("&tag=");
        param.append(tag);
        param.append("&page_size=");
        param.append(pageSize);
        param.append("&page_num=");
        param.append(pageNum);
        param.append("&scope=");
        param.append(scope);
        param.append("&location=");
		param.append(lat+","+lng);
		param.append("&radius=");
		param.append(MathUtil.multiply(radius,"1000"));
		param.append("&output=");
		param.append("json");
		param.append("&ak=");
		param.append(ak);

		return HttpUtils.sendGet(url,param.toString());

	}



	/**
	 * 地址提示信息
	 *
	 * ip : 请求ip
	 *
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String getIma(String query,String tag, String lat,String lng,Integer pageSize ,Integer pageNum ,String scope, GatewayProperties gatewayProperties) {

		String url = "http://api.map.baidu.com/panorama/v2?ak=E4805d16520de693a3fe707cdc962045&width=512&height=256&location=116.313393,40.04778&fov=180 ";

		StringBuffer param = new StringBuffer();
		param.append("query=");
		param.append(tag);
		param.append("&tag=");
		param.append(tag);
		param.append("&page_size=");
		param.append(pageSize);
		param.append("&page_num=");
		param.append(pageNum);
		param.append("&scope=");
		param.append(scope);
		param.append("&location=");
		param.append(lat+","+lng);
		param.append("&radius=");
		param.append("1000");
		param.append("&output=");
		param.append("json");
		param.append("&ak=");
		param.append(ak);

		return HttpUtils.sendGet(url,param.toString());

	}









	/**
	 * 含有unicode 的字符串转一般字符串
	 * @param unicodeStr 混有 Unicode 的字符串
	 * @return
	 */
	public static String decodeUnicode(String unicodeStr) {
		int length = unicodeStr.length();
		int count = 0;
		//正则匹配条件，可匹配“\\u”1到4位，一般是4位可直接使用 String regex = "\\\\u[a-f0-9A-F]{4}";
		String regex = "\\\\u[a-f0-9A-F]{1,4}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(unicodeStr);
		StringBuffer sb = new StringBuffer();

		while(matcher.find()) {
			String oldChar = matcher.group();//原本的Unicode字符
			String newChar = unicode2String(oldChar);//转换为普通字符
			int index = unicodeStr.indexOf(oldChar);

			sb.append(unicodeStr.substring(count, index));//添加前面不是unicode的字符
			sb.append(newChar);//添加转换后的字符
			count = index+oldChar.length();//统计下标移动的位置
		}
		sb.append(unicodeStr.substring(count, length));//添加末尾不是Unicode的字符
		return sb.toString();
	}


	/**
	 * unicode 转字符串
	 * @param unicode 全为 Unicode 的字符串
	 * @return
	 */
	public static String unicode2String(String unicode) {
		StringBuffer string = new StringBuffer();
		String[] hex = unicode.split("\\\\u");

		for (int i = 1; i < hex.length; i++) {
			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);
			// 追加成string
			string.append((char) data);
		}

		return string.toString();
	}




	public static String getQiNiuUrl(String url,GatewayProperties gatewayProperties) {

		String imgUrl =null;

		InputStream in = null;

		InputStream stream1 = null;

		InputStream stream2 = null;

		ByteArrayOutputStream baos = null;

		try {

			//获取二维码流
			in = HttpUtils.sendGetInputStream(url);

			baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len;
			while ((len = in.read(buffer)) > -1) {
				baos.write(buffer, 0, len);
			}
			baos.flush();

			stream1 = new ByteArrayInputStream(baos.toByteArray());

			stream2 = new ByteArrayInputStream(baos.toByteArray());

			//将图片上传七牛
			imgUrl = uploadQiNiu(stream2, null,gatewayProperties);


		} catch (Exception e) {


		} finally {

			try {
				if (in != null) {
					in.close();
				}
				if (stream1 != null) {
					stream1.close();
				}
				if (stream2 != null) {
					stream2.close();
				}
				if (baos != null) {
					baos.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}


			return imgUrl;
		}


	}










	public static String uploadQiNiu(InputStream in, String scene,GatewayProperties gatewayProperties) {

		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		//...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);

		//默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = null;
		try {
			byte[] uploadBytes = toByteArray(in);
			ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
			Auth auth = Auth.create(gatewayProperties.getAccessKey(), gatewayProperties.getSecretKey());
			String upToken = auth.uploadToken(gatewayProperties.getBucket());

			Response response = uploadManager.put(byteInputStream,key,upToken,null, null);
			//解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

			return  "https://"+gatewayProperties.getDomain()+"/"+putRet.key;


		} catch (Exception ex) {


 		}

		return null;

	}

	private static byte[] toByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		byte[] buffer=new byte[1024*4];
		int n=0;
		while ( (n=in.read(buffer)) !=-1) {
			out.write(buffer,0,n);
		}
		return out.toByteArray();
	}



	public static void main(String[] args) {


		System.out.println(BaiDuMapUtils.getFactorInfoList("上海市","美食","31.215918688821593","121.48166311916633",10,0,"2",null,"1"));
	}

}


