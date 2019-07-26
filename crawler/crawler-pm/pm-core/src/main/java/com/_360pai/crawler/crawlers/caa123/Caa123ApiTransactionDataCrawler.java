package com._360pai.crawler.crawlers.caa123;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.crawler.common.util.ComUtils;
import com._360pai.crawler.common.util.HttpUtilNew;
import com._360pai.crawler.common.util.HttpUtilNewModel;
import com._360pai.crawler.commons.AliPmEnum;
import com._360pai.crawler.commons.CaaPmEnum;
import com._360pai.crawler.commons.GPaiPmEnum;
import com._360pai.crawler.dao.caa123.Caa123AreaModelDao;
import com._360pai.crawler.dao.caa123.Caa123TransactionDataBidRecordDao;
import com._360pai.crawler.dao.caa123.Caa123TransactionDataDao;
import com._360pai.crawler.model.caa123.*;
import com._360pai.crawler.service.CaaPmService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 中拍协   https://sf.caa123.org.cn/index.html
 */
@Crawler(name = "caa123TransactionData", useUnrepeated = false)
@Component
public class Caa123ApiTransactionDataCrawler extends BaseSeimiCrawler {

    private final String loctionUrl  = "https://sf.caa123.org.cn/caa-web-ws/ws/0.1/auction/lots/loction";
    private final String typeUrl = "https://sf.caa123.org.cn/caa-web-ws/ws/0.1/auction/lots/type";
    private final String detailUrl = "https://sf.caa123.org.cn/caa-web-ws/ws/0.1/lot/";
    private final String bidUrl = "https://paimai.caa123.org.cn/personal-ws/ws/0.1/bid/pricelog/";
    private final String categoryUrl = "https://sf.caa123.org.cn/caa-web-ws/ws/0.1/lots?start=0&count=100&sortname=&sortorder=&lotStatus=&priceBegin=&priceEnd=&lotMode=&times=&isRestricted=&canLoan=";


    @Autowired
    private SystemProperties systemProperties;

    @Autowired
    private Caa123AreaModelDao caa123AreaModelDao;
    @Autowired
    private Caa123TransactionDataDao caa123TransactionDataDao;
    @Autowired
    private Caa123TransactionDataBidRecordDao caa123TransactionDataBidRecordDao;



    @Autowired
    private CaaPmService caaPmService;




    @Override
    public String[] startUrls() {
        return new String[]{};
    }


    @Override
    public void start(Response response) {


        //获取城市信息
        List<Caa123AreaModel> all = getAreaInfo();

        String typeSt = getHttpResponse(typeUrl);

        JSONArray jsonArray = JSONArray.parseArray(typeSt);

        try{

            for(CaaPmEnum.typeName e :CaaPmEnum.typeName .values()) {

                for (Caa123AreaModel model : all) {

                    Map<String, Object> params = new HashMap<>();

                    params.put("proName", model.getProvince());
                    params.put("cityName", model.getCity());
                    params.put("typeName",e.getVal2());

                    push(Request.build(categoryUrl + "&province=" + model.getProvince() + "&city=" + model.getCity()
                                    + "&standardType="+e.getKey()+"&secondaryType="+e.getVal(),
                            Caa123ApiTransactionDataCrawler::getCategoryList).setMeta(params));

                    Thread.sleep(10000);
                }

            }



        }catch (Exception e){
            logger.error("根据地区和类型爬取数据异常" , e.getMessage());
        }
    }

    private List<Caa123AreaModel> getAreaInfo() {

        List<Caa123AreaModel> all = caa123AreaModelDao.findAll();


        if(all == null || all.size() <= 0) {
            String s = getHttpResponse(loctionUrl);
            List<Caa123Province> caa123Provinces = JSONArray.parseArray(s, Caa123Province.class);
            Caa123Province caa123Province;
            List<Caa123City> cities;
            for (int i = 0, size = caa123Provinces.size(); i < size; i++) {
                caa123Province = caa123Provinces.get(i);
                cities = caa123Province.getCities();
                for(int j = 0; j < cities.size(); j++){
                    Caa123AreaModel caa123AreaModel = new Caa123AreaModel();
                    caa123AreaModel.setProvince(caa123Province.getProName());
                    caa123AreaModel.setProvinceId(caa123Province.getProId());
                    caa123AreaModel.setCity(cities.get(j).getCityName());
                    caa123AreaModel.setCityId(cities.get(j).getCityId());
                    caa123AreaModel.setCreateTime(new Date());

                    all.add(caa123AreaModel);
                }
            }

            caa123AreaModelDao.save(all);
        }


        return all;

    }

    public void getCategoryList(Response response) {
        logger.info("爬取的类别路径是" + response.getUrl());
        JSONObject json = JSONObject.parseObject(response.getContent());

        Integer totalPages = json.getInteger("totalPages");

        logger.info("总共需要爬取"+response.getMeta().get("categoryName") + "的" + totalPages + "页数据");
        try{
            for(int i = 1; i < totalPages + 1; i++ ) {

                push(Request.build( response.getUrl() + "&page=" + i + "&start=" + (i - 1) ,
                        Caa123ApiTransactionDataCrawler::getPageList).setMeta(response.getMeta()));

                logger.info("开始爬取" + i + "页数据");
                Thread.sleep(10000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void getPageList(Response response) {
        JSONObject json = JSONObject.parseObject(response.getContent());
        JSONArray items = json.getJSONArray("items");

        for(int j = 0; j < items.size(); j++) {
            JSONObject jsonObject = items.getJSONObject(j);
            Map<String, Object> params = response.getMeta();


            Integer id = jsonObject.getInteger("id");


            params.put("id", id.toString());
            push(Request.build( detailUrl + jsonObject.getInteger("id"),
                    Caa123ApiTransactionDataCrawler::getDetailList).setMeta(response.getMeta()).setMeta(params));


        }
    }

    public void getDetailList(Response response) {
        Map<String, Object> params = response.getMeta();

        JSONObject  jsonResp = JSONObject.parseObject(response.getContent());



        Caa123TranscationData caa123Auction = JSONObject.parseObject(response.getContent(), Caa123TranscationData.class);
        caa123Auction.setCode((String) params.get("id"));
        caa123Auction.setCategoryName(params.get("typeName").toString());
        caa123Auction.setAddressDetail(jsonResp.getString("position"));
        caa123Auction.setProName(params.get("proName").toString());
        caa123Auction.setCityName(getCityInfo(params.get("cityName").toString(),caa123Auction.getProName()));
        caa123Auction.setStage(CaaPmEnum.time.getVal(jsonResp.getString("times")));
        caa123Auction.setStatus(jsonResp.getString("lotStatus"));
        caa123Auction.setStatusDesc(CaaPmEnum.Status.getVal(caa123Auction.getStatus()));
        caa123Auction.setCode(jsonResp.getString("id"));
        caa123Auction.setArea(getCaaAreaInfo(jsonResp));
        caa123Auction.setCreateTime(new Date());
        caa123Auction.setUpdateTime(new Date());
        caa123Auction.setItemUrl("https://sf.caa123.org.cn/lot/"+caa123Auction.getCode()+".html");
        caa123Auction.setSellType("拍卖");
        caa123Auction.setBidNum(jsonResp.getString("delayTimes"));
        caa123Auction.setLooker(jsonResp.getString("onLooker"));
        caa123Auction.setAppler(jsonResp.getString("enrollment"));

        Map<String,String> latLngInfo = ComUtils.getLatLngInfo(caa123Auction.getAddressDetail(),caa123Auction.getCityName(),systemProperties);
        String lat = null;
        String lng = null;
        if(latLngInfo!=null){
            lat = latLngInfo.get("lat");
            lng = latLngInfo.get("lng");
        }

        caa123Auction.setLat(lat);
        caa123Auction.setLng(lng);

        caaPmService.saveData(caa123Auction);

    }

    private String getCityInfo(String cityName, String proName) {
        if("北京市".equals(proName)||"天津市".equals(proName)||"上海市".equals(proName)||"重庆市".equals(proName)){


            return proName;
        }


        return cityName;
    }


    private String getCaaAreaInfo(JSONObject jsonResp) {

        String  remark = jsonResp.getString("remark");
        List<String> textStrList = new ArrayList<>();

        textStrList.add(remark);

        List<String> tableStrList = new ArrayList<>();

        tableStrList.add(remark);

        String resp = ComUtils.getAreaInfo(textStrList,tableStrList,ComUtils.TEXT_TYPE);

        if(resp!=null){
            return resp;
        }

        List<String> textStrList1 = new ArrayList<>();

        String noticeUrl = "https://sf.caa123.org.cn/caa-web-ws/ws/0.1/notice/lot/"+jsonResp.getString("id");

        String instructionUrl = "https://sf.caa123.org.cn/caa-web-ws/ws/0.1/instruction/lot/"+jsonResp.getString("id");


        textStrList1.add(noticeUrl);
        textStrList1.add(instructionUrl);

        List<String> tableStrList1 = new ArrayList<>();


        return ComUtils.getAreaInfo(textStrList1,tableStrList1,ComUtils.URL_TYPE);
    }


    public void getBidList(Response response) {
        JSONObject json = JSONObject.parseObject(response.getContent());
        Integer totalPages = json.getInteger("totalPages");

        for(int i = 1; i < totalPages + 1; i++) {
            push(Request.build( bidUrl +  response.getMeta().get("id") + "?sortname=&sortorder=&count=10&start=" + (i - 1),
                    Caa123ApiTransactionDataCrawler::getBidPageList).setMeta(response.getMeta()));
        }
    }


    public void getBidPageList(Response response) {
        JSONObject jsonObject = JSONObject.parseObject(response.getContent());
        JSONArray items = jsonObject.getJSONArray("items");

        List<Caa123TransactionDataBidRecord> caa123TransactionDataBidRecords = new ArrayList<>();
        for(int j = 0, len = items.size(); j < len; j++) {
            Caa123TransactionDataBidRecord caa123TransactionDataBidRecord =
                    JSONObject.parseObject(items.getString(j), Caa123TransactionDataBidRecord.class);
            caa123TransactionDataBidRecord.setLotId(Integer.parseInt((String) response.getMeta().get("id")));
            caa123TransactionDataBidRecord.setCreateTime(new Date());
            caa123TransactionDataBidRecords.add(caa123TransactionDataBidRecord);
        }

        caa123TransactionDataBidRecordDao.save(caa123TransactionDataBidRecords);
    }


    private String getHttpResponse(String url) {
        HttpUtilNewModel httpUtilNewModel = HttpUtilNew.get(url);
        if (200 == httpUtilNewModel.getStatusCode())
            return httpUtilNewModel.getHtml();
        else
            throw new RuntimeException("请求异常, url: " + url);
    }
}
