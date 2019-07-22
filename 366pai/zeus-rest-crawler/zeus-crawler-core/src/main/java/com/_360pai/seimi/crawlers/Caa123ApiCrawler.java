package com._360pai.seimi.crawlers;

import com._360pai.seimi.dao.Caa123CityDao;
import com._360pai.seimi.dao.Caa123PmDao;
import com._360pai.seimi.dao.Caa123ProvinceDao;
import com._360pai.seimi.model.Caa123Auction;
import com._360pai.seimi.model.Caa123City;
import com._360pai.seimi.model.Caa123Pm;
import com._360pai.seimi.model.Caa123Province;
import com._360pai.seimi.util.HttpUtilNew;
import com._360pai.seimi.util.HttpUtilNewModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaolei
 * @create 2018-11-13 16:39
 */
@Slf4j
@Component
@NoArgsConstructor
public class Caa123ApiCrawler  {

    private Caa123PmDao caa123PmDao;
    private Caa123ProvinceDao caa123ProvinceDao;
    private Caa123CityDao caa123CityDao;
    @Autowired
    public Caa123ApiCrawler(Caa123PmDao caa123PmDao, Caa123ProvinceDao caa123ProvinceDao, Caa123CityDao caa123CityDao) {
        this.caa123PmDao = caa123PmDao;
        this.caa123ProvinceDao = caa123ProvinceDao;
        this.caa123CityDao = caa123CityDao;
    }

    // 司法拍卖列表
    private final String sifaListUrl        = "https://sf.caa123.org.cn/caa-web-ws/ws/0.1/lots?sortname=&sortorder=&lotStatus=&province=&city=&priceBegin=&priceEnd=&lotMode=&times=&isRestricted=&canLoan=&standardType=&secondaryType=&count=100";
    private final String sifaLisWithCitytUrl= "https://sf.caa123.org.cn/caa-web-ws/ws/0.1/lots?sortname=&sortorder=&lotStatus=&priceBegin=&priceEnd=&lotMode=&times=&isRestricted=&canLoan=&standardType=&secondaryType=&count=100";
    // 拍品详情
    private final String sifaDetailUrl      = "https://sf.caa123.org.cn/caa-web-ws/ws/0.1/lot/";
    // 变卖公告
    private final String sifaNoticeUrl      = "https://sf.caa123.org.cn/caa-web-ws/ws/0.1/notice/lot/";
    // 变卖须知
    private final String sifaInstructionUrl = "https://sf.caa123.org.cn/caa-web-ws/ws/0.1/instruction/lot/";
    // 拍品介绍
    private final String sifaGoodsUrl       = "https://sf.caa123.org.cn/caa-web-ws/ws/0.1/goods/lot/";
    // 省市
    private final String loctionUrl         = "https://sf.caa123.org.cn/caa-web-ws/ws/0.1/auction/lots/loction";

    public void getList(int start) {
        try {
            if (start >= 1) {
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
        int page ;
        int totalPages = 0;
        int totalCount ;
        try {
            String jsonStr = cycleCall(start);
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            page = jsonObject.getInteger("page");
            log.info("当前页: " + page);
            totalCount = jsonObject.getInteger("totalCount");
            log.info("总条数: " + totalCount);
            totalPages = jsonObject.getInteger("totalPages");
            log.info("总页数: " + totalPages);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            jsonArray.parallelStream().forEach(u -> {
                Caa123Pm caa123Pm = JSONObject.parseObject(u.toString(), Caa123Pm.class);
                log.info("id: " + caa123Pm.getId());
            getDetail(caa123Pm.getId(), 0);
            });
            if (start < totalPages) {
                getList(start + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (start < totalPages) {
                getList(start + 1);
            }
        }
    }

    private String cycleCall(int start) {
        int tag = 0;
        int totalCount;
        String call;
        do {
            if (tag >= 1) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
            call = getHttpResponse(sifaListUrl + "&start=" + start);
            JSONObject jsonObject = JSONObject.parseObject(call);
            totalCount = jsonObject.getInteger("totalCount");
            tag++;
        } while(tag < 3 && totalCount == 0);

        return call;
    }

    private String getHttpResponse(String url) {
        HttpUtilNewModel httpUtilNewModel = HttpUtilNew.get(url);
        if (200 == httpUtilNewModel.getStatusCode())
            return httpUtilNewModel.getHtml();
        else
            throw new RuntimeException("请求异常, url: " + url);
    }

    private void getDetail(int id, int cityId) {
        log.info("查询 id : " + id + " 的详情信息");
        String jsonStr = getHttpResponse(sifaDetailUrl + id);
        Caa123Auction caa123Auction = JSONObject.parseObject(jsonStr, Caa123Auction.class);
        caa123Auction.setCityId(cityId);
        caa123PmDao.insertOrUpdateAuction(caa123Auction);
    }

    public void updateCity() {
        String s = getHttpResponse(loctionUrl);
        List<Caa123Province> caa123Provinces = JSONArray.parseArray(s, Caa123Province.class);
        Caa123Province caa123Province;
        List<Caa123City> cities;
        for (int i = 0, size = caa123Provinces.size(); i < size; i++) {
            caa123Province = caa123Provinces.get(i);
            caa123ProvinceDao.save(caa123Province);
            cities = caa123Province.getCities();
            Integer proId = caa123Province.getProId();
            cities.forEach(u -> u.setProId(proId));
            caa123CityDao.save(cities);
        }
    }


    public void getListByCity() {
        /**
         *  1.获取省市列表
         *  2.根据省市查询分页列表
         */
        List<Caa123City> byJoinProvince = caa123CityDao.findByJoinProvince();
        List<Integer> cityIds = new LinkedList<>();
        byJoinProvince.parallelStream().forEach(u -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException _ignored) {
                Thread.currentThread().interrupt();
            }
            String proName        = u.getProName();
            String cityName       = u.getCityName();
            Integer cityId        = u.getCityId();
            log.info("当前查询: " + proName + " "+ cityName + " city_id: " + cityId);
            getListByCity(encode(proName), encode(cityName), 0, cityId);
            cityIds.add(cityId);
        });
        log.info("size: " + cityIds.size());
        log.info("已查询cityId: " + cityIds.toString());
    }

    private String cycleCallWithCity(String proName, String cityName, int start) {
        int tag = 0;
        int totalCount;
        String call;
        do {
            if (tag >= 1) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
            call = getHttpResponse(sifaLisWithCitytUrl + "&province="+ proName +"&city=" + cityName + "&start=" + start);
            JSONObject jsonObject = JSONObject.parseObject(call);
            totalCount = jsonObject.getInteger("totalCount");
            tag++;
        } while(tag < 3 && totalCount == 0);

        return call;
    }

    private void getListByCity(String proName, String cityName, int start, int cityId) {
        int page ;
        int totalCount ;
        int totalPages = 0;
        try {
            String jsonStr = cycleCallWithCity(proName, cityName, start);
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            page = jsonObject.getInteger("page");
            log.info("当前页数: " + page);
            totalCount = jsonObject.getInteger("totalCount");
            log.info("总条数: " + totalCount);
            totalPages = jsonObject.getInteger("totalPages");
            log.info("总页数: " + totalPages);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            jsonArray.parallelStream().forEach(u -> {
                Caa123Pm caa123Pm = JSONObject.parseObject(u.toString(), Caa123Pm.class);
                log.info("id: " + caa123Pm.getId());
                getDetail(caa123Pm.getId(), cityId);
            });
            if (start < totalPages) {
                getListByCity(proName, cityName,start + 1, cityId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (start < totalPages) {
                getListByCity(proName, cityName,start + 1, cityId);
            }
        }
    }


    public static void main(String[] args) {
        Caa123ApiCrawler crawler = new Caa123ApiCrawler();
        HttpUtilNewModel httpUtilNewModel = HttpUtilNew.get(crawler.sifaListUrl + "&start=" + 0);
        System.out.println("httpUtilNewModel = " + JSON.toJSONString(httpUtilNewModel));
    }

    private static String encode(String url)

    {
        try {

            return URLEncoder.encode( url, "UTF-8" );

        } catch (UnsupportedEncodingException e) {

            return null;
        }
    }


}
