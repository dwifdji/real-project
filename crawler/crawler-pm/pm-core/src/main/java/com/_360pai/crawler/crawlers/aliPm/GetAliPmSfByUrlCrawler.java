package com._360pai.crawler.crawlers.aliPm;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.crawler.common.util.*;
import com._360pai.crawler.commons.AliPmEnum;
import com._360pai.crawler.config.AsyncConfig;
import com._360pai.crawler.model.alipm.TAliPmSf;
import com._360pai.crawler.model.alipm.TAliPmSfUrl;
import com._360pai.crawler.service.alipm.AliPmSfService;
import com._360pai.crawler.service.alipm.AliPmUrlService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据url 获取阿里司法拍卖数据
 */
@Crawler(name = "getAliPmSfByUrlCrawler")
@Component
public class GetAliPmSfByUrlCrawler extends BaseSeimiCrawler {

    private static final Pattern COMPILE = Pattern.compile("\\{\"data\":\\[(.*?)]}");


    private static final Pattern STAGE_COMPILE = Pattern.compile("(【)(.*?)(】)");




    @Autowired
    private AsyncConfig asyncConfig;


    @Resource
    private AliPmUrlService aliPmUrlService;


    @Resource
    private AliPmSfService aliPmSfService;


    @Autowired
    private SystemProperties systemProperties;





    @Override
    public String[] startUrls() {

        return new String[]{"https://zc-paimai.taobao.com/zc_item_list.htm"};
    }

    @Override
    public void start(Response response) {

        try {

            List<TAliPmSfUrl>   urlList = aliPmUrlService.findAliPmSfTodoUrlList();
            for(TAliPmSfUrl  cityUrl :urlList){
                Map<String ,Object> params = getMap(cityUrl);
                push(Request.build( cityUrl.getReqUrl(), GetAliPmSfByUrlCrawler::getAliZcList).setMeta(params));
                Thread.sleep(100);

                //更新url状态
                cityUrl.setStatus(AliPmEnum.URL_STATUS.DONE.getKey());
                cityUrl.setUpdateTime(new Date());
                aliPmUrlService.updateAliPmSfUrl(cityUrl);
            }


        } catch (Exception e) {

        }

    }

    private Map<String,Object> getMap(TAliPmSfUrl cityUrl) {
        Map<String,Object> params = new HashMap<>();

        params.put("proName",cityUrl.getProName());
        params.put("cityName",cityUrl.getCityName());
        params.put("areaName",cityUrl.getAreaName());
        params.put("typeName",cityUrl.getTypeName());
        params.put("typeCode",cityUrl.getCategoryCode());

        return params;
    }


    /**
     *
     *获取阿里区域资产信息
     */
    public void getAliZcList(Response response) {


        try {
            Map<String,Object> map = response.getMeta();

            String pageStr = CrawlerUtils.getDocumentStr(response,"body > div.sf-wrap > div.pagination.J_Pagination > span.page-skip > em");

            Integer page = Integer.valueOf(pageStr);
            String url = response.getRequest().getUrl();
            for (int i = 1; i <= page; i++) {
                String pageUrl = url + "&page=" + i;
                Thread.sleep(100);
                push(Request.build(pageUrl, GetAliPmSfByUrlCrawler::getList).setMeta(map));
            }
        }catch (Exception e){

        }

    }




    /**
     *
     *获取资产信息
     */
    public void getList(Response response) {

        //解析每一页的数据
        Map<String,Object> reqMap = response.getMeta();
        String content = response.getContent();

        String doc = content.replace("\n", "");
        String text = null;
        Matcher matcher = COMPILE.matcher(doc);
        if (matcher.find()) {
            text = matcher.group(0);
        }
        if (StringUtils.isNoneBlank(text)) {
            JSONObject jsonObject = JSONObject.parseObject(text);
            JSONArray data = jsonObject.getJSONArray("data");
            for (int i = 0; i < data.size(); i++) {

                JSONObject json = data.getJSONObject(i);
                String itemUrl = json.getString("itemUrl");
                itemUrl = "https:" + itemUrl;
                Map<String,Object> map = new HashMap<>();
                map.putAll(reqMap);
                map.put("data",json.toJSONString());
                map.put("itemUrl",itemUrl);

                //结束时间小于 2019-7-11 号的直接返回
                if(checkEndDate(json)){
                    continue;
                }

                push(Request.build(itemUrl, GetAliPmSfByUrlCrawler::getDetail).setMeta(map));

            }
        }


  }

    private boolean checkEndDate(JSONObject json) {


        try {
            String  dayNum =  systemProperties.getCrawlerAliEndDate();

            if(StringUtils.isEmpty(dayNum)){
                return false;
            }

            String endDate = ComUtils.timeStamp2Date(json.getString("end"));

            /**
             *前几天的日期
             */
            String  crawlerAliEndDate = DateUtil.getNDaysAgoTimeString(Integer.valueOf(dayNum));

            SimpleDateFormat sdf=new SimpleDateFormat(DateUtil.STYLE_2);
            Date bt=sdf.parse(endDate);
            Date et=sdf.parse(crawlerAliEndDate);

            //当结束时间小于配置时间时 直接返回
            if (bt.before(et)){
                return true;
            }
        }catch (Exception e){



        }


        return false;
    }


    /**
     *
     *获取详情信息
     */
     public void getDetail(Response response)   {


         asyncConfig.aliZcListExecutor().execute(new Runnable() {
             @Override
             public void run() {
                 getExecuteDetail(response);

             }
         });


    }

    private void getExecuteDetail(Response response) {
         //解析每一页的数据
        Map<String,Object> reqMap = response.getMeta();

        List<JXNode> picList =CrawlerUtils.getJXNodeList(response,"//ul[@id='J_UlThumb']/li");

        JXDocument document = response.document();

        //获取详情的图片
        JSONArray picArray = new JSONArray();

        for(JXNode node :picList){

            String pic = "https:"+node.sel("/div/a/img/@src").get(0).toString();
            picArray.add(pic);
        }
        String appler = (String) document.selOne("//*[@id=\"page\"]/div[4]/div/div/div[2]/div[3]/span[1]/em/text()");
        //提醒人数
        String reminder = (String) document.selOne("//*[@id=\"page\"]/div[4]/div/div/div[2]/div[3]/span[3]/em/text()");
        //浏览人数
        String looker = (String) document.selOne("//*[@id=\"J_Looker\"]/text()");

        //处置单位
        String disposition = (String) document.selOne("//*[@id=\"page\"]/div[4]/div/div/div[3]/div[2]/p/span[2]/a/text()");
        //联系人
        String person = (String) document.selOne("//*[@id=\"page\"]/div[4]/div/div/div[3]/div[2]/div/div/em/text()");
        //联系电话
        String personPhone = (String) document.selOne("//*[@id=\"page\"]/div[4]/div/div/div[3]/div[2]/div/div/div/allText()");


        List<JXNode> alist = document.selN("//div[@class=\"crumbs\"]/a");

        if(alist !=null &&alist.size()>1){
            disposition =alist.get(1).sel("/text()").get(0).toString();
        }


        //成交价
        String amount = (String) document.selOne("//*[@id=\"sf-price\"]/div/p[1]/span/em/text()");

        if(StringUtils.isEmpty(amount)){
            amount = (String) document.selOne("//*[@id=\"sf-price\"]/span[2]/em/text()");
        }

        //起始价
        String beginPirce = null;

        //加价幅度
        String raisePrice = null;

        //运费
        String freight = null;
        //竞价周期
        String cycle = null;

        //优先购买人
        String preemptor = null;

        //保证金
        String deposit = null;
        //延迟周期
        String delayPeriod = null;

        //保留价
        String reservePrice = null;

        String addressInfo = null;

        //详细地址
        String detailPro = (String) document.selOne("//*[@id=\"itemAddress\"]/text()");



        String addressDetail = (String) document.selOne("//*[@id=\"itemAddressDetail\"]/text()");


        //类型
        String sellType = null;

        List<JXNode> tableList = document.selN("//*[@id=\"J_HoverShow\"]/tr");


        //获取表格的数据信息
        List<JXNode> noticeUrlList = document.selN("//*[@id=\"J_NoticeDetail\"]");
        //公告url
        String noticeUrl ="https:"+ CrawlerUtils.getFirstJXNodeToStr(noticeUrlList.get(0),"/@data-from");


        List<JXNode> instructionList = document.selN("//*[@id=\"J_ItemNotice\"]");
        //须知url
        String instruction_url = "https:"+ CrawlerUtils.getFirstJXNodeToStr(instructionList.get(0),"/@data-from");


        List<JXNode> detailUrlList = document.selN("//*[@id=\"J_desc\"]");

        //详情url
        String detailUrl = "https:"+ CrawlerUtils.getFirstJXNodeToStr(detailUrlList.get(0),"/@data-from");



        List<JXNode> stageList = document.selN("//div[@class=\"pm-main clearfix\"]/h1");


        String stage =   CrawlerUtils.getFirstJXNodeToStr(stageList.get(0),"/text()");


        for(JXNode tr:tableList){

            List<JXNode> tdList  = tr.sel("/td");


            for(JXNode td: tdList){

                List<JXNode>  tds = td.sel("/span/text()");

                if(tds==null||tds.size()<1){
                    continue;
                }
                String tdKey = tds.get(0).toString();

                tdKey =tdKey.replaceAll("\r|\n|\\s", "");

                if("起始价".equals(tdKey)||"起拍价".equals(tdKey)||"变卖价".equals(tdKey)){
                    beginPirce = td.sel("/span/span/text()").get(0).toString();
                }


                if("加价幅度".equals(tdKey)){
                    raisePrice = td.sel("/span/span/text()").get(0).toString();
                }


                if("运费".equals(tdKey)){
                    freight = td.sel("/span/text()").get(1).toString().replaceAll(":","");
                    if(StringUtils.isEmpty(freight)){
                        freight = td.sel("/span/span/text()").get(0).toString();
                    }
                }

                if("竞价周期".equals(tdKey)){
                    cycle = td.sel("/span/text()").get(1).toString().replaceAll(":","");
                }


                if("优先购买权人".equals(tdKey)){
                    preemptor = td.sel("/span/text()").get(1).toString().replaceAll(":","");
                }


                if("保证金".equals(tdKey)){
                    deposit = td.sel("/span/span/text()").get(0).toString();
                }



                if("延时周期".equals(tdKey)){
                    delayPeriod = td.sel("/span/text()").get(1).toString().replaceAll(":","");
                }

                if("保留价".equals(tdKey)){
                    reservePrice = td.sel("/span/text()").get(1).toString().replaceAll(":","");
                }

                if("类型".equals(tdKey)){
                    sellType = td.sel("/span/span/text()").get(0).toString().replaceAll(":","");
                }


            }

        }


        JSONObject dataJson = JSONObject.parseObject(reqMap.get("data").toString());

        if(org.apache.commons.lang.StringUtils.isEmpty(addressDetail)){
            addressInfo =dataJson.getString("title");
        }else{

            addressInfo = ComUtils.StringFilter(detailPro+addressDetail,100);
        }


        Map<String,String> latLngInfo = ComUtils.getLatLngInfo(addressInfo,getValueByKey(reqMap,"cityName"),systemProperties);
        String lat = null;
        String lng = null;
        if(latLngInfo!=null){
             lat = latLngInfo.get("lat");
             lng = latLngInfo.get("lng");
        }

        TAliPmSf model = new TAliPmSf();

        model.setAmount(amount);
        model.setAppler(appler);
        model.setProName(getValueByKey(reqMap,"proName"));
        model.setAreaName(getValueByKey(reqMap,"areaName"));
        model.setCityName(getValueByKey(reqMap,"cityName"));
        model.setCode(dataJson.getString("id"));
        model.setCreateTime(new Date());
        model.setCycle(cycle);
        model.setDeposit(deposit);
        model.setPerson(person);
        model.setDelayPeriod(delayPeriod);
        model.setReminder(reminder);
        model.setTitle(dataJson.getString("title"));
        model.setStatus(dataJson.getString("status"));
        model.setItemUrl(getValueByKey(reqMap,"itemUrl"));
        model.setReservePrice(reservePrice);
        model.setRaisePrice(raisePrice);
        model.setFreight(freight);
        model.setPreemptor(preemptor);
        model.setRaisePrice(raisePrice);
        model.setPersonPhone(personPhone);
        model.setDisposition(disposition);
        model.setPicUrl(picArray.toJSONString());
        model.setLooker(looker);
        model.setStaringPrice(beginPirce);
        model.setStartDate(ComUtils.timeStamp2Date(dataJson.getString("start")));
        model.setEndDate(ComUtils.timeStamp2Date(dataJson.getString("end")));
        model.setStartStamp(dataJson.getString("start"));
        model.setEndStamp(dataJson.getString("end"));

        model.setDataDetail(getValueByKey(reqMap,"data"));
        model.setCurrentPrice(dataJson.getString("currentPrice"));//当前价
        model.setBidNum(dataJson.getString("bidCount"));//竞价总数
        model.setConsultPrice("0.00".equals(dataJson.getString("consultPrice"))?dataJson.getString("marketPrice"):dataJson.getString("consultPrice"));//评估价
        model.setTypeName(getValueByKey(reqMap,"typeName"));
        model.setTypeCode(getValueByKey(reqMap,"typeCode"));

        model.setDeleteFlag(0);
        model.setMarketPrice(dataJson.getString("marketPrice"));//市场价

        model.setAddressDetail(addressInfo);
        model.setLng(lng);
        model.setLat(lat);

        model.setNoticeUrl(noticeUrl);
        model.setInstructionUrl(instruction_url);
        model.setDetailUrl(detailUrl);

        model.setArea(getAreaInfo(model));
        model.setSellType(sellType);
        model.setStage(getStageInfo(stage));
        aliPmSfService.saveAliPmSf(model);


    }



    private HttpUtilNewModel getHttpUtilNewModelInfo(String addressDetail) {


        //调取百度地图 获取经纬度
        String url = "http://api.map.baidu.com/geocoder/v2/";

        StringBuffer param = new StringBuffer();
        param.append("address=");
        param.append(addressDetail);
        param.append("&ak=");
        param.append("tI4ZKcyR4WiPNY7Uabu1qNoyzikMdtaw");
        param.append("&output=");
        param.append("json");

        url = url+"?"+param.toString();


        HttpUtilNewModel model =  HttpUtilNewIP.get(url);

       while (model.getStatusCode()!=200){
            getHttpUtilNewModelInfo(addressDetail);
        }


        return model;
    }





    /**
     *
     *获取建筑面积信息
     */
    private String getAreaInfo(TAliPmSf model) {

        List<String> textStrList = new ArrayList<>();

        textStrList.add(model.getNoticeUrl());
        textStrList.add(model.getInstructionUrl());
        textStrList.add(model.getDetailUrl());

        List<String> tableStrList = new ArrayList<>();

        tableStrList.add(model.getDetailUrl());

        return ComUtils.getAreaInfo(textStrList,tableStrList,ComUtils.URL_TYPE);



    }


    public static void main(String[] args) {

        String url = "https://api.m.jd.com/api?appid=paimai&functionId=queryProductDescription&body=%7b%22paimaiId%22:109335079,%22source%22:0%7d&loginType=3";


        String resp  =    HttpsUtil.get(url,null);



        JSONObject jsonObject = JSON.parseObject(resp);

        String data = jsonObject.getString("data");


        List<String> textStrList = new ArrayList<>();


        List<String> tableStrList = new ArrayList<>();

        tableStrList.add(url);


        System.out.println(ComUtils.getAreaInfo(textStrList,tableStrList,ComUtils.URL_TYPE));


    }



    private String getStageInfo(String info) {

        String text = null;
        Matcher matcher = STAGE_COMPILE.matcher(info);
        if (matcher.find()) {
            text = matcher.group(2);
        }

        return text;
    }







    private String getValueByKey(Map<String,Object> reqMap, String proName) {
        Object value = reqMap.get(proName);

        if(value==null){
            return null;
        }

        return value.toString();
    }


}