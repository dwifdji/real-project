package com._360pai.crawler.crawlers.jdPm;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.crawler.model.jdPm.JdPm;
import com._360pai.crawler.service.JdPmService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zxiao
 * @Title: JdPmCrawler
 * @ProjectName 345V1
 * @Description:
 * @date 2018/11/9 14:42
 */
@Crawler(name = "jdPm")
public class JdPmCrawler extends BaseSeimiCrawler {

    @Resource
    private JdPmService jdPmService;

    private static final String ITEM_URL = "https://mpaimai.jdPm.com/json/mobile/getProductbasicInfo.html?paimaiId=CODE&callback=";

    private static final String NUM_URL = "https://paimai.jdPm.com/json/ensure/queryAccess?t=991500sku=&paimaiId=CODE";

    private static final String REMIND_URL = "https://api.m.jdPm.com/api?jsonp=&appid=auctionRemind&functionId=queryRemind&body=%7B%22id%22%3A%22" + "CODE" + "%22%2C%22remindType%22%3A1%7D&rand=" + System.currentTimeMillis() / 1000 + "&_=" + System.currentTimeMillis() / 1000;

    @Override
    public String[] startUrls() {
        return new String[]{"https://auction.jdPm.com/getAssetsList.html?" +
                    "childrenCateId=12767" +
                "&sortField=8" +
                "&limit=40" +
                "&callback=" +
                "&_=1541746056206"};
    }

    @Override
    public void start(Response response) {
        String content = response.getContent();
        JSONObject jsonObject = JSONObject.parseObject(content);
        Integer total = jsonObject.getInteger("total");
        int page = total / 40;
        page = total % 40 > 0 ? page + 1 : page;
        for (int i = 1; i <= page; i++) {
            StringBuilder url = new StringBuilder(response.getRequest().getUrl());
            url.append("&page=").append(i);
            logger.info("当前查看的列表页URL为：{}", url.toString());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            push(Request.build(url.toString(), JdPmCrawler::getItemData));
        }


    }

    public void getItemData(Response response) {
        String content = response.getContent();
        JSONObject jsonObject = JSONObject.parseObject(content);
        JSONArray ls = jsonObject.getJSONArray("ls");

        for (int i = 0; i < ls.size(); i++) {
            JSONObject json = ls.getJSONObject(i);
            logger.info("当前爬取的URL列表为：{}", response.getRequest().getUrl());
            logger.info("商品列表中获取到的JSON数据为：{}", json);
            String code = json.getString("id");
            String title = json.getString("title");
            BigDecimal currentPrice = json.getBigDecimal("currentPrice");
            String startTime = json.getString("startTime");
            String endTime = json.getString("endTime");
            String vendorName = json.getString("vendorName");
            String paimaiStatus = json.getString("paimaiStatus");
            String displayStatus = json.getString("displayStatus");
            BigDecimal startPrice = json.getBigDecimal("startPrice");


            JdPm jdPm = jdPmService.findByCode(code);
            if (jdPm == null) {
                jdPm = new JdPm();
            }
            jdPm.setCode(code);
            jdPm.setTitle(title);
            jdPm.setAgency(vendorName);
            jdPm.setStatus(paimaiStatus);
            jdPm.setStartTime(new Date(Long.valueOf(startTime)));
            jdPm.setEndTime(new Date(Long.valueOf(endTime)));
            jdPm.setRespData(json.toJSONString());
            jdPm.setDisplayStatus(displayStatus);
            jdPm.setStartPrice(startPrice.setScale(2, BigDecimal.ROUND_HALF_UP));

            if (paimaiStatus.equals("1")) {
                if (!displayStatus.equals("3")) {
                    jdPm.setAmount(currentPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
                } else {
                    jdPm.setCurrentPrice(currentPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
                }
            } else {
                jdPm.setCurrentPrice(currentPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
            }

            jdPmService.saveItem(jdPm);

            Map<String, Object> map = new HashMap<>(16);
            map.put("code", code);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //获取商品的基本信息
            push(Request.build(ITEM_URL.replace("CODE", code), JdPmCrawler::getProductInfo).setMeta(map));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //获取浏览人数和报名数量
            push(Request.build(NUM_URL.replace("CODE", code), JdPmCrawler::getNumInfo).setMeta(map));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //获取提醒人数
            push(Request.build(REMIND_URL.replace("CODE", code), JdPmCrawler::getRemind).setMeta(map));
        }
    }

    public void getProductInfo(Response response) {
        jdPmService.updateContent(response);
    }

    public void getNumInfo(Response response) {

        jdPmService.updateNum(response);
    }

    public void getRemind(Response response) {

        jdPmService.updateRemind(response);
    }

    public static void main(String[] args) {
        int a = 400;
        int b = 0;

        int c = a / 40;

        int d = a % 40 > 0 ? c + 1 : c;

        System.out.println("d = " + d);


    }
}
