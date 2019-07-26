package com._360pai.crawler.common.util;

import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：爬虫工具类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/4/2 17:52
 */
public class ComUtils {

    private static final Pattern AREA_COMPILE = Pattern.compile("(建筑面积|建筑总面积|房屋所有权证确权面积|房产面积|建筑证载面积|登记簿记载面积|建面|房屋面积|产权面积|证载面积|面积为)(.*?)(平方米|㎡|平米|m2|M2|'')");


    //合计建筑面积
    private static final Pattern TOTAL_AREA_COMPILE = Pattern.compile("(合计建筑面积|总建筑面积)(.*?)(平方米|㎡|平米|m2|M2)");


    public static final String TEXT_TYPE = "text";

    public static final String URL_TYPE = "url";


    public static String timeStamp2Date(String time) {
        Long timeLong = Long.parseLong(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//要转换的时间格式
        Date date;
        try {
            date = sdf.parse(sdf.format(timeLong));
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * textStr 纯文本类字符串 解析顺序按照 list顺利
     *
     * tableStr table 类解析
     * type 文本格式  url 格式
     */
    public static String getAreaInfo(List<String> textStrList,List<String> tableStrList,String type) {

        try {
            for(String str :textStrList){

                if(URL_TYPE.equals(type)){
                    str = HttpsUtil.get(str,null);
                }

                String text = getFilterArea(str);

                if(isNumber(text)){
                    return text;
                }
            }

            for(String tableStr :tableStrList){

                if(URL_TYPE.equals(type)){
                    tableStr = HttpsUtil.get(tableStr,null);
                }


                String text =getFilterTableArea(tableStr);

                if(isNumber(text)){
                    return text;
                }


            }
        }catch (Exception e){


        }


        return null;


    }


    /**
     *
     *详情的表格解析
     */
    public static String getFilterTableArea(String str) {

        String area = null;

        try {


           Document doc = Jsoup.parse(str);

            //获取表格数据
            List<Element> elementList = doc.getElementsByTag("table");

            if(elementList.size()<1){
                return null;
            }

            //倒叙排列  获取最内满足条件的表格
            Collections.reverse(elementList);
            Element elementKey = null;
            for(Element element :elementList){
                //当满足条件时取值
                if(hasKeyWorld(element.text())){
                    elementKey =element;
                }

            }

            //都没有满足条件的表格时 直接返回
            if(elementKey==null){
                return null;
            }

            //获取tr标签
            Elements els = elementKey.select("tr");


            for(int i=0;i<els.size();i++){
                Element el =els.get(i);
                if (hasKeyWorld(el.text())) {

                    //获取td 标签
                    Elements tds = el.select("td");
                    int tdNum =0;
                    for(int j=0;j<tds.size();j++){
                        //获取建筑面积所在的列
                        if(hasKeyWorld(tds.get(j).text())){
                            tdNum =j;
                            //建筑面积的下一列的值

                            area = getNum(tds.get(j+1).text());
                            if(isNumber(area)){
                                continue;
                            }
                        }
                    }
                    //当下一列值 满足条件时 直接返回
                    if(isNumber(area)){
                        continue;
                    }

                    //不满足条件时 获取下一列相同位置的值
                    Elements tds2 = els.get(i+1).select("td");

                    area = getNum(tds2.get(tdNum).text());

                    if(isNumber(area)){
                        continue;
                    }
                }

            }


        }catch (Exception e){

            System.out.println(e);

        }


        return area==null?null:area.trim();
    }

    private static boolean hasKeyWorld(String text) {

        return   text.indexOf("建筑面积") != -1||text.indexOf("建筑总面积") != -1||text.indexOf("房屋所有权证确权面积") != -1||text.indexOf("房产面积") != -1||text.indexOf("建筑证载面积") != -1||text.indexOf("登记簿记载面积") != -1||text.indexOf("建面") != -1||text.indexOf("房屋面积") != -1;
    }








    public static String getFilterArea(String noticeInfo) {

        //过滤html标签
        noticeInfo = Html2Text(noticeInfo);
        String text = null;

        //合计面积获取
        String totalArea = getTotalAreaInfo(noticeInfo);

        //获取到合计的值 直接返回
        if(StringUtils.isNotEmpty(totalArea)){
            return totalArea;
        }

        Matcher matcher = AREA_COMPILE.matcher(noticeInfo);
        if (matcher.find()) {

            text = matcher.group(2);
            Pattern pattern = Pattern.compile("([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])+");


            Matcher matcher1 = pattern.matcher(text);

            List<String> areaList = new ArrayList<>();

            while(matcher1.find()) {
                areaList.add(matcher1.group());
            }

            if(areaList.size()>0){
                return areaList.get(areaList.size()-1);
            }



        }

        return text;
    }

    private static String getTotalAreaInfo(String noticeInfo) {


        Matcher matcher = TOTAL_AREA_COMPILE.matcher(noticeInfo);
        if (matcher.find()) {

            String text = matcher.group(2);
            Pattern pattern = Pattern.compile("([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])+");

            Matcher matcher1 = pattern.matcher(text);

            List<String> areaList = new ArrayList<>();

            while(matcher1.find()) {
                areaList.add(matcher1.group());
            }

            if(areaList.size()>0){
                return areaList.get(areaList.size()-1);
            }

        }

        return null;
    }

    private static String getNum(String inputString) {
        Pattern pattern = Pattern.compile("([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])");
        Matcher matcher1 = pattern.matcher(inputString);
        if (matcher1.find()) {
            return matcher1.group(0);
        }

        return null;
    }




    private static String Html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;// 返回文本字符串
    }



    private static boolean isNumber(String string) {
        if (StringUtils.isBlank(string)){
            return false;
        }

        //判断是否为数据
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
        if(!pattern.matcher(string).matches()){
            return false;
        }

        //判断获取到的数据是否合理 当小于11 并且为整数是认为不合理
        if(new BigDecimal(string).compareTo(new BigDecimal("11")) == -1&&isNumeric(string)){

            return false;
        }


         return true;
    }


    public static boolean isNumeric(String string){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(string).matches();
    }




    public static Map<String,String> getLatLngInfo(String addressDetail, String city, SystemProperties systemProperties) {



        Map<String,String> latLngInfo = new HashMap<>();


        try{

            String url = "http://api.map.baidu.com/geocoder/v2/";

            StringBuffer param = new StringBuffer();
            param.append("address=");
            param.append(StringFilter(addressDetail,40));
            param.append("&ak=");
            //param.append("zMKIgj8LPGKssRqXQIuVG81sE5fcG6NY");
            param.append(systemProperties.getBaiDuMapAk());
            param.append("&output=");
            param.append("json");
            param.append("&city=");
            param.append(city);

            String resp = HttpUtilNew.sendGet(url,param.toString());


            JSONObject jsonObject = JSON.parseObject(resp);

            latLngInfo.put("lat",jsonObject.getString("status"));
            latLngInfo.put("lng",jsonObject.getString("status"));

            //正确返回
            if("0".equals(jsonObject.getString("status"))){

                JSONObject result = jsonObject.getJSONObject("result");
                JSONObject location = result.getJSONObject("location");
                latLngInfo.put("lat",location.get("lat").toString());
                latLngInfo.put("lng",location.get("lng").toString());

            }


        }catch (Exception e){
            latLngInfo.put("lat","999");
            latLngInfo.put("lng","999");

        }





        return latLngInfo;
    }


    public static String StringFilter(String str,Integer size){
        str = str.replaceAll("[^0-9\\u4e00-\\u9fa5]", "");

        if(str.length()>size){

            str = str.substring(0,size);
        }

        return str;
    }


    public static void main(String[] args) {

        System.out.println(getFilterArea("这个的建筑面积为：1-2c:122.21平方米"));
    }

}
