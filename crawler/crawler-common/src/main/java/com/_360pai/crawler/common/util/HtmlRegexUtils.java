package com._360pai.crawler.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 *
 * 描述： 用正则表达式，获得某个字符串中指定的内容
 *
 * 作者： zxy 版本： 1.0.0 时间： 2017年1月5日 下午4:50:58
 */
public class HtmlRegexUtils {

	private final static String regxpForImgTag = "<\\s*img\\s+([^>]*)\\s*>"; // 找出IMG标签

	private final static String regxpForImaTagSrcAttrib = "src=\"([^\"]+)\""; // 找出IMG标签的SRC属性

	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
	private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
	private static final String regEx_link = "<link[^>]*?>"; // 定义link的正则表达式
	private static final String regEx_zhujie = "<\\!--[\\s\\S]*-->"; // 定义注解的正则表达式

	private static final String regEx_iframe = "<iframe[^>]*?>[\\s\\S]*?<\\/iframe>"; // 定义iframe的正则表达式
	private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
	private static final String regEx_html_zhishi = "<\\!--div[\\s\\S]*div>-->"; // 定义HTML标签的正则表达式
	private static final String regEx_space = "\\s*|\t|\r|\n";// 定义空格回车换行符
	private static final String regKh = "([^)]+)"; // ()

	public static String extractNum(String s) {

		if (StringUtils.isEmpty(s)) {
			return "";
		}

        s = s.replace(",","");

        Pattern p = Pattern.compile("((\\-)?\\d+(.\\d+)?)");
		Matcher m = p.matcher(s);
		if (m.find()) {
			return m.group(1);
		}
		return "";
	}

	public static String extractNum3(String s) {

        s = s.replace(",","");

        Pattern p = Pattern.compile("('.*?')");
		Matcher m = p.matcher(s);
		if (m.find()) {
			return extractNum(m.group(1));
		}
		return "";
	}


	public static String extractDate(String s) {
		Pattern p = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{1,2}");
		Matcher m = p.matcher(s);
		if (m.find()) {
			return m.group(0);
		}
		return "";
	}

    public static Float extractFloatNum2(String s)
    {
        Float ret = extractFloatNum(s);

        if(ret==null)
        {
            return 0F;
        }

        return ret;
    }

    public static Float extractFloatNum(String s)
    {
        String ss = extractNum(s);

        if(ss==null || ss.equals(""))
        {
            return null;
        }

        return Float.valueOf(ss);
    }

    public static Integer extractIntegerNum(String s)
    {
        String ss = extractNum(s);

        if(ss==null || ss.equals(""))
        {
            return null;
        }

        return Integer.valueOf(ss);
    }

	public static String extractNumCode(String s) {
		Pattern p = Pattern.compile("http://www.99fund.com/main/products/pofund/(\\d+)/fundgk.shtml");
		Matcher m = p.matcher(s);
		if (m.find()) {
			return m.group(1);
		}
		return "";
	}
	public static String extractNumCodeNew(String s) {
		Pattern p = Pattern.compile("http://99fund.com/main/products/pofund/(\\d+)/fundgk.shtml");
		Matcher m = p.matcher(s);
		if (m.find()) {
			return m.group(1);
		}
		return "";
	}

	public static String extractFundCodeNew(String s) {

		Pattern p = Pattern.compile("fundcode=\"(.*?)\"");
		Matcher m = p.matcher(s);
		if (m.find()) {
			return m.group(1);
		}
		return "";
	}

	public static String extractTTCodeNew(String s) {

		Pattern p = Pattern.compile("http://fund.eastmoney.com/(\\d+).html");
		Matcher m = p.matcher(s);
		if (m.find()) {
			return m.group(1);
		}
		return "";
	}

	/**
	 * 从乱七八糟的字符串中获得金额
	 *
	 * @param str
	 * @return
	 */
	public static String getAmt(String str) {
		Pattern p = Pattern.compile("\\d+(\\.\\d{1,2})?");
		Matcher m = p.matcher(str);
		boolean isFind = m.find();
		if (isFind) {
			return m.group();
		}
		return "";
	}

	/**
	 * 从乱七八糟的字符串中获得一些纯数字的code
	 *
	 * @param str
	 * @return
	 */
	public static String getCode(String str) {
		Pattern p = Pattern.compile("(\\d+)*");
		Matcher m = p.matcher(str);
		boolean isFind = m.find();
		if (isFind) {
			return m.group();
		}
		return "";
	}

	/**
	 * 删除html标签
	 *
	 * @param str
	 * @return
	 */
	public static String removeHtml(String str) {
		str = str.replaceAll("&nbsp;", "");

		// Pattern p_script = Pattern.compile(regEx_script,
		// Pattern.CASE_INSENSITIVE);
		// Matcher m_script = p_script.matcher(htmlStr);
		// htmlStr = m_script.replaceAll(""); // 过滤script标签
		//
		// Pattern p_style = Pattern
		// .compile(regEx_style, Pattern.CASE_INSENSITIVE);
		// Matcher m_style = p_style.matcher(htmlStr);
		// htmlStr = m_style.replaceAll(""); // 过滤style标签
		//
		// Pattern p_html = Pattern.compile(regEx_html,
		// Pattern.CASE_INSENSITIVE);
		// Matcher m_html = p_html.matcher(htmlStr);
		// htmlStr = m_html.replaceAll(""); // 过滤html标签
		//
		Pattern p_space = Pattern
				.compile(regEx_space, Pattern.CASE_INSENSITIVE);
		Matcher m_space = p_space.matcher(str);
		str = m_space.replaceAll(""); // 过滤空格回车标签
		return str.trim(); // 返回文本字符串
	}

	public static String removeAllHtml(String htmlStr) {
		if (htmlStr == null) {
			return null;
		}
		htmlStr = htmlStr.replaceAll("&nbsp;", "");

		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
		Matcher m_space = p_space.matcher(htmlStr);
		htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
		return htmlStr.trim(); // 返回文本字符串
	}

	/**
	 * 根据冒号提取内容
	 */
	public static String extractStringByColon(String str) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		String[] arr;
		if (str.contains(":")) {
			arr = str.split(":");
		} else if (str.contains("：")) {
			arr = str.split("：");
		} else {
			return str;
		}
		return arr[arr.length-1].trim();
	}

	public static void main(String[] args) {
//		String str = "人民币： 余额： 0.00";
//		System.out.println(getAmt(str));
//		String str2 = "href=\"http://www.99fund.com/main/products/pofund/519518/fundgk.shtml";
//		String str3 = "href=\"http://99fund.com/main/products/pofund/164701/fundgk.shtml";
//		System.out.println(extractNumCode(str2));
//
//		System.out.println(extractNumCodeNew(str3));

//		String str4 = "<a id=\"divdend_110550\" href=\"javascript:void(0);\" alt=\"现金分红\" class=\"share-money hover-title-title\" divdendmethod=\"1\" accountno=\"0000000000196876484\" fundcode=\"001104\" productid=\"110550\">现金分红</a>";
//
//		String s = extractFundCodeNew(str4);
//
//		System.out.println("s = " + s);

		String date = extractNum3("to3rdDetailUrl('004221')");

		System.out.println("date = " + date);
	}
}
