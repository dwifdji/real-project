package com._360pai.seimi.crawlers;

/**
 * @author xdrodger
 * @Title: RmfysszcPmCrawler
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/20 13:31
 */

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.model.RmfysszcPm;
import com._360pai.seimi.service.RmfysszcPmService;
import com._360pai.seimi.util.HtmlRegexUtils;
import com._360pai.seimi.util.HttpUtilNew;
import com._360pai.seimi.util.HttpUtilNewModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jodd.io.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * 人民法院诉讼资产网
 */
@Crawler(name = "rmfysszc", delay = 5, httpTimeOut=60000)
@Component
public class RmfysszcPmCrawler extends BaseSeimiCrawler {

    private static final String sepa = java.io.File.separator;

    private static final String dir = System.getProperty("user.dir") + sepa + "zeus-rest-crawler" + sepa + "zeus-crawler-core" + sepa + "src" + sepa + "main" + sepa + "lib" + sepa + "rmfysszc" + sepa;

    private static final String LIST_URL = "http://www1.rmfysszc.gov.cn/ProjectHandle.shtml";

    private static final String ITEM_DETAIL_URL = "http://www.rmfysszc.gov.cn/statichtml/rm_obj/ITEM_ID.shtml";

    @Autowired
    private RmfysszcPmService rmfysszcPmService;

    @Override
    public String[] startUrls() {
        //return getUrls();
        return new String[] {"http://www.rmfysszc.gov.cn/statichtml/rm_obj/96931.shtml"};
    }

    private String[] getUrls() {
        List<String> list = new LinkedList<>();
        int page = 1;
        while (true) {
            logger.info("当前页码={}", page);
            HttpUtilNewModel res = HttpUtilNew.post(getHeaders(), getParams(page), LIST_URL);
            if (res == null || 200 != res.getStatusCode() || StringUtils.isEmpty(res.getHtml())) {
                logger.error("当前页码={}，请求异常", page);
                break;
            }
            String content = res.getHtml();
            JSONObject result = JSON.parseObject(content);
            Document doc = Jsoup.parse(result.getString("html"));
            Elements elements = doc.select("div.product > div.p_img > a");
            for (Element element : elements) {
                String href = element.attr("href");
                String itemId = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf("."));
                String path = dir + "";
                File file = new File(dir + itemId + ".shtml");
                if (file.exists()) {
                    continue;
                }
                list.add(ITEM_DETAIL_URL.replace("ITEM_ID", itemId));
            }
            doc = Jsoup.parse(result.getString("page"));
            Element curPage = doc.select("a.pagecur").first();
            if (curPage == null || page != Integer.parseInt(curPage.text())) {
                break;
            }
            page ++;
        }
        return list.toArray(new String[]{});
    }

    @Override
    public List<Request> startRequests() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public void start(Response response) {
        //getItem(response);
        processLocalFile();
    }

    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
        headers.put("X-Requested-With", "XMLHttpRequest");
        headers.put("Referer", "http://www1.rmfysszc.gov.cn/projects.shtml");
        return headers;
    }

    private NameValuePair[] getParams(int page) {
        NameValuePair[] params = new BasicNameValuePair[] {
                new BasicNameValuePair("type", "0"),
                new BasicNameValuePair("name", ""),
                new BasicNameValuePair("area", ""),
                new BasicNameValuePair("state", "0"),
                new BasicNameValuePair("time", "0"),
                new BasicNameValuePair("time1", ""),
                new BasicNameValuePair("time2", ""),
                new BasicNameValuePair("money", ""),
                new BasicNameValuePair("money1", ""),
                new BasicNameValuePair("number", "0"),
                new BasicNameValuePair("fid1", ""),
                new BasicNameValuePair("fid2", ""),
                new BasicNameValuePair("fid3", ""),
                new BasicNameValuePair("order", "0"),
                new BasicNameValuePair("include", "0"),
                new BasicNameValuePair("page", page + ""),
        };
        return params;
    }

    private String getItemId(String url) {
        if (StringUtils.isEmpty(url)) {
            return "";
        }
        return url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
    }

    public void getItem(Response response) {
        logger.info("当前itemUrl={}", response.getRealUrl());
        try {
            String itemId = getItemId(response.getRealUrl());
            File file = new File(dir + itemId + ".shtml");
            if (!file.exists()) {
                file.createNewFile();
            }
            response.saveTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processLocalFile() {
        try {
            String path = dir + "";
            File dir = new File(path);
            if (!dir.isDirectory()) {
                return;
            }
            File[] list  = dir.listFiles();
            for (File file : list) {
                String content = FileUtil.readString(file);
                Document doc = Jsoup.parse(content);
                String itemId = file.getName().replace(".shtml", "");
                parseDoc(itemId, doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void parseDoc(String itemId, Document doc) {
        RmfysszcPm rmfysszcPm = rmfysszcPmService.findByItemId(itemId);
        if (rmfysszcPm == null) {
            rmfysszcPm = new RmfysszcPm();
            rmfysszcPm.setItemId(itemId);
            rmfysszcPm.setItemUrl("http://www.rmfysszc.gov.cn/statichtml/rm_obj/" + itemId + ".shtml");
            rmfysszcPm.setCreateTime(new Date());
            rmfysszcPm.setUpdateTime(new Date());
            rmfysszcPm.setIsDelete(false);
        }
        Element title = doc.select("#Title > h1").first();
        if (title != null) {
            rmfysszcPm.setTitle(title.text());
        }
        Element startPrice = doc.select("#price > div:nth-child(1) > span").first();
        if (startPrice != null) {
            String startPriceStr = HtmlRegexUtils.extractNum(startPrice.text());
            if (StringUtils.isNotEmpty(startPriceStr)) {
                rmfysszcPm.setStartPrice(new BigDecimal(startPriceStr).multiply(new BigDecimal(10000)));
            }
        }
        Element refPrice = doc.select("#bg1 > div:nth-child(1) > table > tbody > tr:nth-child(1) > td > span:nth-child(2)").first();
        if (refPrice != null) {
            String refPriceStr = HtmlRegexUtils.extractNum(refPrice.text());
            if (StringUtils.isNotEmpty(refPriceStr)) {
                rmfysszcPm.setRefPrice(new BigDecimal(refPriceStr).multiply(new BigDecimal(10000)));
            }
        }
        Element deposit = doc.select("#bg1 > div:nth-child(1) > table > tbody > tr:nth-child(2) > td > span:nth-child(2)").first();
        if (deposit != null) {
            String depositStr = HtmlRegexUtils.extractNum(deposit.text());
            if (StringUtils.isNotEmpty(depositStr)) {
                rmfysszcPm.setDeposit(new BigDecimal(depositStr).multiply(new BigDecimal(10000)));
            }
        }
        JSONObject extra = new JSONObject();
        JSONArray imageUrls = new JSONArray();
        Elements images = doc.select("#zzsc > a:nth-child(1) > img");
        for (Element image : images) {
            imageUrls.add(image.attr("src"));
        }
        extra.put("imageUrls", imageUrls);
        rmfysszcPm.setExtra(extra.toJSONString());

        Element element = doc.select("#bg1 > div:nth-child(2) > table > tbody > tr:nth-child(1) > td > span").first();
        if (element != null) {
            rmfysszcPm.setContactName(element.text());
        }
        element = doc.select("#bg1 > div:nth-child(2) > table > tbody > tr:nth-child(2) > td > span").first();
        if (element != null) {
            rmfysszcPm.setContactName(HtmlRegexUtils.extractStringByColon(element.text()));
        }
        element = doc.select("#bg1 > div:nth-child(2) > table > tbody > tr:nth-child(3) > td > span").first();
        if (element != null) {
            rmfysszcPm.setContactName(HtmlRegexUtils.extractStringByColon(element.text()));
        }
        element = doc.select("#bg1 > div:nth-child(1) > table > tbody > tr:nth-child(3) > td > span").first();
        if (element != null) {
            rmfysszcPm.setPublishAt(HtmlRegexUtils.extractStringByColon(element.text()));
        }
        rmfysszcPmService.saveItem(rmfysszcPm);
    }
}
