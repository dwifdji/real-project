package com.winback.gateway.common.check;



import com.winback.arch.common.HttpUtils;
import com.winback.arch.core.sysconfig.properties.GatewayProperties;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：企查查请求工具类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/17 15:04
 */
public class QiChaChaUtils {


    /**
     *
     *企业关键字精确获取详情信息（Master）
     */
    public static String getDetailsByName(GatewayProperties gatewayProperties, String keyword)  {

        StringBuffer queryInfo = new StringBuffer();
        queryInfo.append("key=");
        queryInfo.append(gatewayProperties.getCheckKey());
        queryInfo.append("&keyword=");
        queryInfo.append(keyword);

        return  HttpUtils.sendGet(gatewayProperties.getCheckGetDetailsByNameUrl(),queryInfo.toString(),getHeadParams(gatewayProperties));
    }




    /**
     *
     *失信信息查询
     */
    public static String searchShiXin(GatewayProperties gatewayProperties, String keyword,String pageIndex ,String pageSize)  {

        StringBuffer queryInfo = new StringBuffer();
        queryInfo.append("key=");
        queryInfo.append(gatewayProperties.getCheckKey());
        queryInfo.append("&searchKey=");
        queryInfo.append(keyword);
        if(StringUtils.isNotEmpty(pageIndex)){
            queryInfo.append("&pageIndex=");
            queryInfo.append(pageIndex);
        }
        if(StringUtils.isNotEmpty(pageSize)){
            queryInfo.append("&pageSize=");
            queryInfo.append(pageSize);
        }
        return  HttpUtils.sendGet(gatewayProperties.getCheckSearchShiXinUrl(),queryInfo.toString(),getHeadParams(gatewayProperties));
    }




    /**
     *
     *执行信息
     */
    public static String searchZhiXing(GatewayProperties gatewayProperties, String keyword,String pageIndex ,String pageSize)  {

        StringBuffer queryInfo = new StringBuffer();
        queryInfo.append("key=");
        queryInfo.append(gatewayProperties.getCheckKey());
        queryInfo.append("&searchKey=");
        queryInfo.append(keyword);
        if(StringUtils.isNotEmpty(pageIndex)){
            queryInfo.append("&pageIndex=");
            queryInfo.append(pageIndex);
        }
        if(StringUtils.isNotEmpty(pageSize)){
            queryInfo.append("&pageSize=");
            queryInfo.append(pageSize);
        }
        return  HttpUtils.sendGet(gatewayProperties.getCheckSearchZhiXingUrl(),queryInfo.toString(),getHeadParams(gatewayProperties));
    }



    /**
     *
     *裁判文书信息
     */
    public static String searchJudgmentDoc(GatewayProperties gatewayProperties, String keyword,String pageIndex ,String pageSize)  {

        StringBuffer queryInfo = new StringBuffer();
        queryInfo.append("key=");
        queryInfo.append(gatewayProperties.getCheckKey());
        queryInfo.append("&keyWord=");
        queryInfo.append(keyword);
        if(StringUtils.isNotEmpty(pageIndex)){
            queryInfo.append("&pageIndex=");
            queryInfo.append(pageIndex);
        }
        if(StringUtils.isNotEmpty(pageSize)){
            queryInfo.append("&pageSize=");
            queryInfo.append(pageSize);
        }
        return  HttpUtils.sendGet(gatewayProperties.getCheckSearchJudgmentDocUrl(),queryInfo.toString(),getHeadParams(gatewayProperties));
    }



    /**
     *
     *裁判文书信息
     */
    public static String getOpException(GatewayProperties gatewayProperties, String keyword)  {

        StringBuffer queryInfo = new StringBuffer();
        queryInfo.append("key=");
        queryInfo.append(gatewayProperties.getCheckKey());
        queryInfo.append("&keyNo=");
        queryInfo.append(keyword);

        return  HttpUtils.sendGet(gatewayProperties.getCheckGetOpExceptionUrl(),queryInfo.toString(),getHeadParams(gatewayProperties));
    }




    /**
     *
     *股权十层穿透接口
     */
    public static String getStockAnalysisData(GatewayProperties gatewayProperties, String keyword)  {

        StringBuffer queryInfo = new StringBuffer();
        queryInfo.append("key=");
        queryInfo.append(gatewayProperties.getCheckKey());
        queryInfo.append("&keyword=");
        queryInfo.append(keyword);

        return  HttpUtils.sendGet(gatewayProperties.getCheckGetStockAnalysisDataUrl(),queryInfo.toString(),getHeadParams(gatewayProperties));
    }


    /**
     *
     *股权十层穿透接口
     */
    public static String searchInvestment(GatewayProperties gatewayProperties, String keyword,String pageIndex ,String pageSize)  {

        StringBuffer queryInfo = new StringBuffer();
        queryInfo.append("key=");
        queryInfo.append(gatewayProperties.getCheckKey());
        queryInfo.append("&keyword=");
        queryInfo.append(keyword);
        if(StringUtils.isNotEmpty(pageIndex)){
            queryInfo.append("&pageIndex=");
            queryInfo.append(pageIndex);
        }
        if(StringUtils.isNotEmpty(pageSize)){
            queryInfo.append("&pageSize=");
            queryInfo.append(pageSize);
        }

        return  HttpUtils.sendGet(gatewayProperties.getCheckSearchInvestmentUrl(),queryInfo.toString(),getHeadParams(gatewayProperties));
    }








    private static Map<String,String> getHeadParams(GatewayProperties gatewayProperties) {
        String timeSpan = Date2TimeStamp(new Date());

        String tokenInfo = gatewayProperties.getCheckKey()+timeSpan+gatewayProperties.getCheckSecretKey();

        String token = DigestUtils.md5Hex(tokenInfo).toUpperCase();

        Map<String ,String> headParams = new HashMap<>();
        headParams.put("Token",token);
        headParams.put("Timespan",timeSpan);

        return headParams;

    }


    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 日期
     *
     * @return
     */
    private static String Date2TimeStamp(Date dateStr) {
        try {
             return String.valueOf(dateStr.getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
