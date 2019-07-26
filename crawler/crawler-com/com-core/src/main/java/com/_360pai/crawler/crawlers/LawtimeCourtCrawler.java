package com._360pai.crawler.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.crawler.model.LawtimeCourt;
import com._360pai.crawler.service.LawtimeService;
import com.alibaba.fastjson.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author xdrodger
 * @Title: RmfyggCrawler
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/8 19:40
 */
@Crawler(name = "lawtime_court")
@Component
public class LawtimeCourtCrawler extends BaseSeimiCrawler {

    @Autowired
    private LawtimeService lawtimeService;

    @Override
    public String[] startUrls() {
        return new String[0];
    }

    @Override
    public void start(Response response) {
        String content = response.getContent();
        Document parse = Jsoup.parse(content);
        //Elements list = parse.select("#main > div.warp1 > div.midarea > div.mapbox > div > div > p > a");
        Elements list = parse.select("#m_cmap > area");
        Set<String> itemUrls = new HashSet<>();
        for (Element a : list) {
            String itemUrl = a.attr("href");
            String province = a.text();
            itemUrls.add(itemUrl);
            logger.info("itemUrl={}, province={}", itemUrl, province);

            Map<String, Object> map = new HashMap<>();
            map.put("province", province);
            push(Request.build(itemUrl, LawtimeCourtCrawler::getListByProvince).setMeta(map));

            try {
                Thread.sleep(1000 * 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void getListByProvince(Response response) {
        Set<String> itemUrls = new HashSet<>();
        if (response.getRealUrl().contains("province")) {
            Document parse = Jsoup.parse(response.getContent());
            Elements list = parse.select("#main > div.midarea.ma2 > div.mcol > h2 > span.right > a");
            for (Element a : list) {
                String itemUrl = a.attr("href");
                System.out.println(a.parent().parent().ownText());
                System.out.println(a.parent().parent().text());
                itemUrls.add(itemUrl);
                Map<String, Object> map = response.getMeta();
                String city = a.parent().parent().ownText().replace("法院", "");
                logger.info("getListByProvince={}, province={}, city={}", itemUrl, map.get("province"), city);
                map.put("city", city);
                push(Request.build(itemUrl, LawtimeCourtCrawler::getListByCity).setMeta(map));
                try {
                    Thread.sleep(1000 * 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            String itemUrl = response.getRealUrl();
            itemUrls.add(itemUrl);
            Map<String, Object> map = response.getMeta();
            logger.info("getListByProvince={}, province={}, city={}", itemUrl, map.get("province"), map.get("province"));
            map.put("city", map.get("province"));
            push(Request.build(itemUrl, LawtimeCourtCrawler::getListByCity).setMeta(map));
            //try {
            //    Thread.sleep(3000);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
        }
    }

    public void getListByCity(Response response) {
        Document parse = Jsoup.parse(response.getContent());
        Map<String, Object> map = response.getMeta();
        Elements list = parse.select("#main > div.midarea.ma2 > div.mcol > dl.newline > dt.mccname > a");
        for (Element a : list) {
            String itemUrl = a.attr("href");
            LawtimeCourt lawtimeCourt = lawtimeService.findByItemUrl(itemUrl);
            if (lawtimeCourt != null) {
                continue;
            }
            logger.info("getListByCity={}, province={}, city={}", itemUrl, map.get("province"), map.get("city"));
            push(Request.build(itemUrl, LawtimeCourtCrawler::getItem).setMeta(map));
            try {
                Thread.sleep(1000 * 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list = parse.select("#main > div.midarea.ma2 > div.mcpage.c0165B8 > p > a");
        Set<String> itemUrls = new HashSet<>();
        if (list != null) {
            for (Element a : list) {
                String itemUrl = a.attr("href");
                itemUrls.add(itemUrl);
            }
            for (String itemUrl : itemUrls) {
                push(Request.build(itemUrl, LawtimeCourtCrawler::getListByCity).setMeta(map));
            }
        }
    }

    public void getItem(Response response) {
        Map<String, Object> data = getData(response);
        logger.info("" + JSON.toJSONString(data));
        LawtimeCourt lawtimeCourt = lawtimeService.findByName((String) data.get("name"));
        Date now = new Date();
        if (lawtimeCourt == null) {
            lawtimeCourt = JSON.toJavaObject(JSON.parseObject(JSON.toJSONString(data)), LawtimeCourt.class);
            lawtimeCourt.setDeleteFlag(false);
            lawtimeCourt.setCreateTime(now);
            lawtimeCourt.setUpdateTime(now);
            lawtimeService.save(lawtimeCourt);
        } else {
            LawtimeCourt lawtimeCourtUpdate = JSON.toJavaObject(JSON.parseObject(JSON.toJSONString(data)), LawtimeCourt.class);
            lawtimeCourtUpdate.setId(lawtimeCourt.getId());
            lawtimeCourtUpdate.setDeleteFlag(lawtimeCourt.getDeleteFlag());
            lawtimeCourtUpdate.setCreateTime(lawtimeCourt.getCreateTime());
            lawtimeCourtUpdate.setUpdateTime(now);
            lawtimeService.save(lawtimeCourtUpdate);
        }
    }

    private Map<String, Object> getData(Response response) {
        Document parse = Jsoup.parse(response.getContent());
        Map<String, Object> data = new HashMap<>();
        data.putAll(response.getMeta());
        Element name = parse.select("#main > div.contarea > div.dcdetail > h2").first();
        if (name != null) {
            data.put("name", name.ownText());
        }
        Element type = parse.select("#main > div.contarea > div.dcdetail > div.dclevel > p:nth-child(1) > a").first();
        if (type != null) {
            data.put("type", type.ownText());
        }
        Element address = parse.select("#main > div.contarea > div.dcdetail > dl > dd:nth-child(2)").first();
        if (address != null) {
            data.put("address", address.ownText());
        }
        Element phone = parse.select("#main > div.contarea > div.dcdetail > p:nth-child(6)").first();
        if (phone != null) {
            data.put("phone", phone.ownText());
        }
        Element otherPhone = parse.select("#main > div.contarea > div.dcdetail > p:nth-child(9)").first();
        if (otherPhone != null) {
            data.put("otherPhone", otherPhone.ownText());
        }
        Element workTime = parse.select("#main > div.contarea > div.dcdetail > p:nth-child(11)").first();
        if (workTime != null) {
            data.put("workTime", workTime.ownText());
        }
        Element description = parse.select("#main > div.contarea > div.dcintro > p").first();
        if (address != null) {
            data.put("description", description.ownText());
        }
        Element imgUrl = parse.select("#main > div.contarea > div.dcintro > p > img").first();
        if (imgUrl != null) {
            data.put("imgUrl", imgUrl.attr("src").replaceFirst("//", ""));
        }
        data.put("itemUrl", response.getRealUrl());
        return data;
    }
}
