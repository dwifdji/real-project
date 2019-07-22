package com._360pai.seimi.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.dao.GPaiPmAreaDao;
import com._360pai.seimi.model.GPaiAreaModel;
import com._360pai.seimi.model.GPaiPm;
import com._360pai.seimi.service.GPaiPmService;
import com._360pai.seimi.util.HtmlRegexUtils;
import com._360pai.seimi.util.RequestUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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



    private static final String ITEM_LIST_URL = "http://s.gpai.net/sf/search.do?restate=3&cityNum=";

    private static final String ITEM_DETAIL_URL = "http://m.gpai.net/api/itemDetail?itemId=ITEM_ID";

    private static final String ITEM_EASY_DETAIL_URL = "m.gpai.net/api/itemEasyDetail?itemId=ITEM_ID";

    private static final String ITEM_TEXT_URL = "http://m.gpai.net/api/itemText?action=item&itemId=ITEM_ID";

    @Override
    public String[] startUrls() {
        return new String[]{};
    }

//    @Override
//    public List<Request> startRequests() {
////        return startByCity();
////        return startByNeedToUpdate();
//    }


//    private List<Request> startByCity() {
//        List<Request> requests = new LinkedList<>();
//        List<String> urls = new ArrayList<>();
//        List<JSONObject> dataList = gPaiPmService.getAllCities();
//
//        for (JSONObject province : dataList) {
//            logger.info("province={}", province.getString("name"));
//            List<JSONObject> cityList = (List<JSONObject>) province.get("cityList");
//            try{
//                for (JSONObject city : cityList) {
//                    logger.info("province={}, city={}", province.getString("name"), city.getString("name"));
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("cityId", city.getString("od"));
//                    map.put("cityName", city.getString("name"));
//                    map.put("provinceName", province.getString("name"));
//                    requests.add(Request.build(ITEM_LIST_URL + city.getString("id"), GPaiPmCrawler::start).setMeta(map));
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//        }
//        return requests;
//    }

    private List<Request> startByNeedToUpdate() {
        List<Request> requests = new LinkedList<>();
        int page = 0;
        while (true) {
            logger.info("当前页码={}", page);
            Page<GPaiPm> pageInfo = gPaiPmService.findNeedToUpdate(page, 500);
            if (!pageInfo.hasContent()) {
                break;
            }
            for (GPaiPm gPaiPm : pageInfo.getContent()) {
                logger.info("itemUrl为：{}", gPaiPm.getItemUrl());
                Map<String, Object> map = new HashMap<>();
                requests.add(Request.build(gPaiPm.getItemUrl(), GPaiPmCrawler::getItem).setMeta(map));
            }
            page ++;
        }

        return requests;
    }

    private static final Pattern COMPILE = Pattern.compile("\\{\"data\":\\[(.*?)]}");

    @Override
    public void start(Response response) {
        List<GPaiAreaModel> areaList = gPaiPmAreaDao.findAll();

        if(areaList == null || areaList.size() <= 0) {
            areaList = gPaiPmService.getAllCities();
        }

        try{
            for (int i = 492; i < areaList.size(); i++) {
                GPaiAreaModel areaModel = areaList.get(i);
                logger.info("province={}, city={}", areaModel.getProvince(), areaModel.getCity());
                Map<String, Object> map = new HashMap<>();
                map.put("cityName", areaModel.getAreaId());
                Thread.sleep(4000);
                push(Request.build(ITEM_LIST_URL + areaModel.getAreaId(), GPaiPmCrawler::getCategoryList).setMeta(map));
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
//        Map<String, String> header = getHeader();
        Integer totalPage = Integer.valueOf(HtmlRegexUtils.extractNum(pageStr));

        try {
            for (int i = 1; i <= totalPage; i++) {
                String pageUrl = url + "&page=" + i;
                logger.info("当前列表页url为：{}", pageUrl);
                push(Request.build(pageUrl, GPaiPmCrawler::getList).setMeta(response.getMeta()));
                Thread.sleep(4000);
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
            logger.info("itemUrl为：{}", itemUrl);

            String itemId = getItemId(RequestUtils.toMap(itemUrl));
            GPaiPm gPaiPm = gPaiPmService.findByItemId(Integer.parseInt(itemId));
            if (gPaiPm != null && StringUtils.isNotEmpty(gPaiPm.getAgency())) {
                logger.info("itemUrl为：{}，已爬取，跳过", itemUrl);
                continue;
            }
            push(Request.build(itemUrl, GPaiPmCrawler::getItem).setMeta(response.getMeta()));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

        String content = response.getContent();
        Document parse = Jsoup.parse(content);
        Map<String, Object> map = response.getMeta();
        String itemId = getItemId(RequestUtils.toMap(response.getRealUrl()));
        map.put("itemId", itemId);
        map.put("itemUrl", response.getRealUrl());
        GPaiPm gPaiPm = gPaiPmService.findByItemId(Integer.parseInt(itemId));

        if (gPaiPm == null) {
            gPaiPm = new GPaiPm();

        gPaiPm.setItemId(Integer.parseInt(itemId));
        gPaiPm.setItemUrl(response.getRealUrl());
        Element biddingExtension = parse.getElementsContainingOwnText("延时周期").first();

        try{
            Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (biddingExtension != null) {
            String biddingExtensionStr = HtmlRegexUtils.extractNum(biddingExtension.text());
            if (StringUtils.isEmpty(biddingExtensionStr)) {
                Element sibling = biddingExtension.nextElementSibling();
                if (sibling != null) {
                    gPaiPm.setBiddingExtension(Integer.parseInt(HtmlRegexUtils.extractNum(sibling.text())));
                }
            } else {
                gPaiPm.setBiddingExtension(Integer.parseInt(biddingExtensionStr));
            }
        }
        gPaiPm.setCity((Integer) map.get("cityName"));

        CloseableHttpClient httpClient = HttpClients.custom().build();

        gPaiPmService.setContent(ITEM_DETAIL_URL.replace("ITEM_ID", itemId), gPaiPm, httpClient);
        gPaiPmService.saveItem(gPaiPm);
        }
    }

//    public void getItemDetail(Response response) {
//        gPaiPmService.updateContent(response);
//    }


    private Map<String, String> getHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("Cache-Control", "no-cache");
        header.put("Pragma", "no-cache");
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        header.put("Accept", "text/plain, */*; q=0.01");
        header.put("Accept-Encoding", "gzip, deflate");
        header.put("Accept-Language", "zh-CN,zh;q=0.9");
        header.put("Connection", "keep-alive");
        header.put("Cookie",  "ASPSESSIONIDQSRSQQAD=MEBFBIMBGGPLKGHGGLOGDLFA; Hm_lvt_263a15f1b2e57ebc22960d3fa7c5537e=1553061015,1553736105; _Jo0OQK=5145C58DEBBB4ECE20F6C8D955B36E2C1BEA19A27F679399F01382BB9A512D5181E6D6422A5246CEE9FEC1870CBABBEBF840DEE15CA81A46439EBD0E142B8B294E10C3D7FF52CD341DB483B4F189B95C5EF483B4F189B95C5EF97BB8868CE018E66472F501F7EB820B7GJ1Z1Kg==; Hm_lpvt_263a15f1b2e57ebc22960d3fa7c5537e=1553759279; ASPSESSIONIDQQRRSQAC=AMPHHBDCHPKDHMPLMOLANDGK");
        header.put("Host", "www.gpai.net");
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        header.put("X-Requested-With", "XMLHttpRequest");

        header.put("Referer", "http://www.gpai.net/sf/");
        return header;
    }

    private HttpGet setAllHeader(HttpGet httpGet) {
        httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpGet.addHeader("Accept-Encoding", "gzip, deflate");
        httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
        httpGet.addHeader("Cache-Control", "no-cache");
        httpGet.addHeader("Connection", "keep-alive");
        httpGet.addHeader("Cookie",  "sto-id-20480-sg-server-80-tcp=APAAAAAKFAAA; Hm_lvt_263a15f1b2e57ebc22960d3fa7c5537e=1553061015,1553736105; ASPSESSIONIDCAQTSRBQ=ANBMJONBOKDLKJKJINGOAPLN; _Jo0OQK=542A709E3CD80264D92B11DF2C65DDFBCD921E06515BF17BD7261027E8D07E2DA2F4918E5DCE815C353A4BD64C09107603FE54A9D8EC720869E15419EA4700A8DF4D624CBB2326F0E637D64DE7DEA55C6DA7D64DE7DEA55C6DAA82E166847B7EF1384802BCEE76E9BA4GJ1Z1RA==; ASPSESSIONIDSQATSTAS=OLIKMNNBBGHOGJIEGLLHGLEG; sto-id-20480-search=BFAAAAAKFAAA;");
        httpGet.addHeader("Host", "www.gpai.net");
        httpGet.addHeader("Pragma", "no-cache");
        httpGet.addHeader("Referer", "http://www.gpai.net/sf/");
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        httpGet.addHeader("X-Requested-With", "XMLHttpRequest");
        return httpGet;
    }
}
