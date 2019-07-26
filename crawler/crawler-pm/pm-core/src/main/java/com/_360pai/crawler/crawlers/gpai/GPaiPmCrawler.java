package com._360pai.crawler.crawlers.gpai;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.crawler.common.util.ComUtils;
import com._360pai.crawler.commons.AliPmEnum;
import com._360pai.crawler.commons.GPaiPmEnum;
import com._360pai.crawler.dao.gpai.GPaiPmAreaDao;
import com._360pai.crawler.model.gpai.GPaiAreaModel;
import com._360pai.crawler.model.gpai.GPaiPm;
import com._360pai.crawler.service.GPaiPmService;
import com._360pai.crawler.common.util.HtmlRegexUtils;
import com._360pai.crawler.common.util.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.seimicrawler.xpath.JXDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 公拍网
 */
@Crawler(name = "gPai")
@Component
public class GPaiPmCrawler extends BaseSeimiCrawler {
    @Autowired
    private GPaiPmService gPaiPmService;
    @Autowired
    private GPaiPmAreaDao gPaiPmAreaDao;

    @Autowired
    private SystemProperties systemProperties;

    private static final String ITEM_LIST_URL = "http://s.gpai.net/sf/search.do?restate=3&cityNum=";

    private static final String ITEM_DETAIL_URL = "http://m.gpai.net/api/itemDetail?itemId=ITEM_ID";

    @Override
    public String[] startUrls() {
        return new String[]{};
    }

    @Override
    public void start(Response response) {
        List<GPaiAreaModel> areaList = gPaiPmAreaDao.findAll();

        if(areaList == null || areaList.size() <= 0) {
            areaList = gPaiPmService.getAllCities();
        }

        try{
            for (GPaiAreaModel areaModel :areaList) {
                logger.info("province={}, city={}", areaModel.getProvince(), areaModel.getCity());

                //按类型分类
                for(GPaiPmEnum.type_name e :GPaiPmEnum.type_name.values()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("areaId", areaModel.getAreaId());
                    map.put("proName",areaModel.getProvince());
                    map.put("cityName",areaModel.getCity());
                    map.put("areaName",areaModel.getArea());
                    map.put("typeName",e.getVal());

                    Thread.sleep(100);
                    push(Request.build(ITEM_LIST_URL + areaModel.getAreaId()+"&at="+e.getKey(), GPaiPmCrawler::getCategoryList).setMeta(map));
                }



            }
        }catch (Exception e){
                e.printStackTrace();
        }
    }

    public void getCategoryList(Response response) {
        String content = response.getContent();
        String url = response.getRequest().getUrl();
        Document parse = Jsoup.parse(content);
        String pageStr = parse.select("body > div.container > div.block-wrap.notice-wrap > div.auto > div.page-bar > div.page-nav > span.page-infos > label").text();
        if (StringUtils.isEmpty(pageStr)) {
            pageStr = "1";
        }
        logger.info("当前列表页url为：{}", url);
        Integer totalPage = Integer.valueOf(HtmlRegexUtils.extractNum(pageStr));

        try {
            for (int i = 1; i <= totalPage; i++) {
                String pageUrl = url + "&page=" + i;
                logger.info("当前列表页url为：{}", pageUrl);
                push(Request.build(pageUrl, GPaiPmCrawler::getList).setMeta(response.getMeta()));
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getList(Response response) {
        String content = response.getContent();
        Document parse = Jsoup.parse(content);
        Elements lis = parse.select("body > div.container > div.block-wrap.notice-wrap > div.auto > div.filt-result-list ul.main-col-list.clearfix > li");
        Set<String> itemUrls = new HashSet<>();
        for (Element li : lis) {
            String itemUrl = li.select("div.list-item > a").first().attr("href");
            itemUrls.add(itemUrl);

            push(Request.build(itemUrl, GPaiPmCrawler::getItem).setMeta(response.getMeta()));

        }
        logger.info("商品列表抓取结束数量为：{}", itemUrls.size());
    }

    private String getItemId(Map<String, String> params) {
        if (params.containsKey("Web_Item_ID")) {
            return params.get("Web_Item_ID");
        }
        return "";
    }


    public void getItem(Response response) {


        Map<String, Object> map = response.getMeta();
        String itemId = getItemId(RequestUtils.toMap(response.getRealUrl()));
        map.put("itemId", itemId);
        map.put("itemUrl", response.getRealUrl());
        GPaiPm gPaiPm = new GPaiPm();
        CloseableHttpClient httpClient = HttpClients.custom().build();

        gPaiPmService.setContent(ITEM_DETAIL_URL.replace("ITEM_ID", itemId), gPaiPm, httpClient);
        gPaiPm.setItemId(Integer.parseInt(itemId));
        gPaiPm.setItemUrl(response.getRealUrl());
        gPaiPm.setTypeName((String)map.get("typeName"));
        gPaiPm.setProName((String)map.get("proName"));
        gPaiPm.setCityName((String)map.get("cityName"));
        gPaiPm.setAreaName((String)map.get("areaName"));
        gPaiPm.setCity((Integer) map.get("areaId"));
        gPaiPm.setAddressDetail(gPaiPm.getTitle());

        //	数量/单位 为空时取公告 须知的数据
        if(StringUtils.isEmpty(gPaiPm.getArea())){


            gPaiPm.setArea(getAreaInfo(response,gPaiPm.getTitle()));
        }
        Map<String,String> latLngInfo = ComUtils.getLatLngInfo(gPaiPm.getAddressDetail(),gPaiPm.getCityName(),systemProperties);
        String lat = null;
        String lng = null;
        if(latLngInfo!=null){
            lat = latLngInfo.get("lat");
            lng = latLngInfo.get("lng");
        }
        gPaiPm.setLat(lat);
        gPaiPm.setLng(lng);
        gPaiPm.setIsDelete(false);
        gPaiPm.setCreateTime(new Date());

        gPaiPmService.saveItem(gPaiPm);

    }

    private String getAreaInfo(Response response,String title) {

        //title 抓取 建面 关键字
        String titleArea = ComUtils.getFilterArea(title);
        if(StringUtils.isNotEmpty(titleArea)){
            return titleArea;
        }

        try {
            JXDocument document  = response.document();

            Object noticeObj = document.selOne("//div[@class='details-con']");


            if(noticeObj==null){
                noticeObj = document.selOne("//div[@class='xq-cont3']");
            }

            String noticeStr = String.valueOf(noticeObj);

            List<String> textStrList = new ArrayList<>();

            textStrList.add(noticeStr);

            List<String> tableStrList = new ArrayList<>();

            tableStrList.add(noticeStr);

            return ComUtils.getAreaInfo(textStrList,tableStrList,ComUtils.TEXT_TYPE);
        }catch (Exception e){


            System.out.println(response.getUrl());
        }




        return null;

     }
}
