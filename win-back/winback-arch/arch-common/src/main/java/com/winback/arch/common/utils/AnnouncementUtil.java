package com.winback.arch.common.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: liuhaolei
 * @Title: AnnouncementUtil
 * @ProjectName: arch-common
 * @Description: 公告操作替换工具类
 * @Date: 2018-08-30
 */
public class AnnouncementUtil {
	
	/**
	 * 将传进来的占位符以及字符串进行替换
	 * 且以json数据替换
	 * @param openToken 开始符号
	 * @param closeToken 结束符号
	 * @param text 被替换的html字符串
	 * @param json 需要替换的字段值json
	 * @return
	 */
    public static String parse(String openToken, String closeToken, String text, JSONObject json)  {
    	String valueFlag = "";
    	
        if (json == null) {
            return text;
        }

        if (text == null || text.isEmpty()) {
            return "";
        }
        char[] src = text.toCharArray();
        int offset = 0;
        
        int start = text.indexOf(openToken, offset);
        if (start == -1) {
            return text;
        }
        final StringBuilder builder = new StringBuilder();
        StringBuilder expression = null;
        while (start > -1) {
            if (start > 0 && src[start - 1] == '\\') {
                builder.append(src, offset, start - offset - 1).append(openToken);
                offset = start + openToken.length();
            } else {
                if (expression == null) {
                    expression = new StringBuilder();
                } else {
                    expression.setLength(0);
                }
                builder.append(src, offset, start - offset);
                offset = start + openToken.length();
                int end = text.indexOf(closeToken, offset);
                while (end > -1) {
                    if (end > offset && src[end - 1] == '\\') {
                        expression.append(src, offset, end - offset - 1).append(closeToken);
                        offset = end + closeToken.length();
                        end = text.indexOf(closeToken, offset);
                    } else {
                        expression.append(src, offset, end - offset);
                        
                        //动态替换json字段值
                        Object object = json.get(expression.toString().trim());
                        if(object != null) {
                        	valueFlag = object.toString();
                        }else {
                        	valueFlag = "";
                        }
                        offset = end + closeToken.length();
                        break;
                    }
                }
                if (end == -1) {
                	
                    builder.append(src, start, src.length - start);
                    offset = src.length;
                } else {
 
                    builder.append(valueFlag);
                    offset = end + closeToken.length();
                }
            }
            start = text.indexOf(openToken, offset);
        }
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }
        return builder.toString();
    }
    
}
