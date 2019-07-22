package com._360pai.seimi.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.dao.Caa123AreaModelDao;
import com._360pai.seimi.dao.Caa123TransactionDataBidRecordDao;
import com._360pai.seimi.dao.Caa123TransactionDataDao;
import com._360pai.seimi.model.*;
import com._360pai.seimi.util.HttpUtilNew;
import com._360pai.seimi.util.HttpUtilNewModel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 公拍网
 */
@Crawler(name = "caa123TransactionData", useUnrepeated = false)
@Component
public class Caa123ApiTransactionDataCrawler extends BaseSeimiCrawler {

    private final String loctionUrl  = "https://sf.caa123.org.cn/caa-web-ws/ws/0.1/auction/lots/loction";
    private final String typeUrl = "https://paimai.caa123.org.cn/personal-ws/ws/0.1/auction/lots/type";
    private final String detailUrl = "https://paimai.caa123.org.cn/personal-ws/ws/0.1/lot/";
    private final String bidUrl = "https://paimai.caa123.org.cn/personal-ws/ws/0.1/bid/pricelog/";
    private final String categoryUrl = "https://paimai.caa123.org.cn/caa-search-ws/ws/0.1/lots?count=12&status=3";
    @Autowired
    private Caa123AreaModelDao caa123AreaModelDao;
    @Autowired
    private Caa123TransactionDataDao caa123TransactionDataDao;
    @Autowired
    private Caa123TransactionDataBidRecordDao caa123TransactionDataBidRecordDao;
    @Override
    public String[] startUrls() {
        return new String[]{};
    }


    @Override
    public void start(Response response) {

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

        String typeSt = getHttpResponse(typeUrl);

        JSONArray jsonArray = JSONArray.parseArray(typeSt);
        try{
            for(int p = 0 ; p < jsonArray.size(); p++) {
                JSONObject json = jsonArray.getJSONObject(p);
                for(int t = 0, len = all.size(); t < len; t++ ) {
                    Map<String, Object> params = new HashMap<>();
                    params.put("cityId", all.get(t).getCityId());
                    params.put("categoryName", json.getString("standardName"));
                    push(Request.build(categoryUrl + "&province=" + all.get(t).getProvinceId() + "&city="
                                    + all.get(t).getCityId() + "&standardType=" + json.getString("standardNum"),
                            Caa123ApiTransactionDataCrawler::getCategoryList).setMeta(params));

                    Thread.sleep(2000);
                }
            }
        }catch (Exception e){
            logger.error("根据地区和类型爬取数据异常" , e.getMessage());
        }
    }

    public void getCategoryList(Response response) {
        logger.info("爬取的类别路径是" + response.getUrl());
        JSONObject json = JSONObject.parseObject(response.getContent());

        Integer totalPages = json.getInteger("totalPages");
        Integer totalCount = json.getInteger("totalCount ");

        logger.info("总共需要爬取"+response.getMeta().get("categoryName") + "的" + totalPages + "页数据");
        try{
            for(int i = 1; i < totalPages + 1; i++ ) {

                push(Request.build( response.getUrl() + "&page=" + i + "&start=" + (i - 1) ,
                        Caa123ApiTransactionDataCrawler::getPageList).setMeta(response.getMeta()));

                logger.info("开始爬取" + i + "页数据");
                Thread.sleep(2000);
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

            Integer id = jsonObject.getInteger("id");
            Caa123TranscationData caa123TranscationData = caa123TransactionDataDao.selectOneByCode(id);

            if(caa123TranscationData == null) {
                Map<String, Object> params = new HashMap<>();
                params.put("id", id.toString());
                params.put("cityId", response.getMeta().get("cityId"));
                params.put("categoryName", response.getMeta().get("categoryName"));

                push(Request.build( detailUrl + jsonObject.getInteger("id"),
                        Caa123ApiTransactionDataCrawler::getDetailList).setMeta(response.getMeta()).setMeta(params));

            }
        }
    }

    public void getDetailList(Response response) {
        Caa123TranscationData caa123Auction = JSONObject.parseObject(response.getContent(), Caa123TranscationData.class);
        caa123Auction.setCityId((Integer) response.getMeta().get("cityId"));
        caa123Auction.setCode((String) response.getMeta().get("id"));
        caa123Auction.setCategoryName((String) response.getMeta().get("categoryName"));
        caa123Auction.setCreateTime(new Date());
        caa123Auction.setUpdateTime(new Date());

        //出价记录保存
        Map<String, Object> params = new HashMap<>();
        params.put("id", response.getMeta().get("id"));
        push(Request.build( bidUrl +  response.getMeta().get("id") + "?sortname=&sortorder=&start=0&count=10",
                Caa123ApiTransactionDataCrawler::getBidList).setMeta(params));

        caa123TransactionDataDao.save(caa123Auction);
    }


    public void getBidList(Response response) {
        JSONObject json = JSONObject.parseObject(response.getContent());
        Integer totalCount = json.getInteger("totalCount");
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
