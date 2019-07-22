package com._360pai.controller.crawler;

/**
 * @author xdrodger
 * @Title: RmfysszcPmController
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/20 13:29
 */

import cn.wanghaomiao.seimi.spring.common.CrawlerCache;
import cn.wanghaomiao.seimi.struct.CrawlerModel;
import com._360pai.seimi.util.HttpUtilNew;
import com._360pai.seimi.util.HttpUtilNewModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jodd.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 人民法院诉讼资产网
 */
@RestController
@Slf4j
public class RmfysszcPmController {

    private static final String LIST_URL = "http://www1.rmfysszc.gov.cn/ProjectHandle.shtml";

    private static final String sepa = java.io.File.separator;

    private static final String dir = System.getProperty("user.dir") + sepa + "zeus-rest-crawler" + sepa + "zeus-crawler-core" + sepa + "src" + sepa + "main" + sepa + "lib" + sepa + "rmfysszc" + sepa;

    @RequestMapping(value = "/run/rmfysszc")
    public String runGPai() {
        CrawlerModel gPai = CrawlerCache.getCrawlerModel("rmfysszc");
        gPai.startRequest();
        return "consume suc";
    }

    @RequestMapping(value = "/run/rmfysszcTest")
    public String runTest() {
        //NameValuePair[] params = new BasicNameValuePair[] {
        //        new BasicNameValuePair("type", "0"),
        //        new BasicNameValuePair("name", ""),
        //        new BasicNameValuePair("area", ""),
        //        new BasicNameValuePair("state", "0"),
        //        new BasicNameValuePair("time", "0"),
        //        new BasicNameValuePair("time1", ""),
        //        new BasicNameValuePair("time2", ""),
        //        new BasicNameValuePair("money", ""),
        //        new BasicNameValuePair("money1", ""),
        //        new BasicNameValuePair("number", "0"),
        //        new BasicNameValuePair("fid1", ""),
        //        new BasicNameValuePair("fid2", ""),
        //        new BasicNameValuePair("fid3", ""),
        //        new BasicNameValuePair("order", "0"),
        //        new BasicNameValuePair("include", "0"),
        //        new BasicNameValuePair("page", "1"),
        //};
        //Map<String, String> headers = new HashMap<>();
        //headers.put("Content-Type", "application/x-www-form-urlencoded");
        //headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
        //headers.put("X-Requested-With", "XMLHttpRequest");
        //headers.put("Referer", "http://www1.rmfysszc.gov.cn/projects.shtml");
        //HttpUtilNewModel res = HttpUtilNew.post(headers, params, LIST_URL);
        //if (res == null || 200 != res.getStatusCode()) {
        //    return "";
        //}
        //if (StringUtils.isEmpty(res.getHtml())) {
        //    return "";
        //}
        //JSONObject result = JSON.parseObject(res.getHtml());
        //System.out.println(res.getHtml());
        //Document doc = Jsoup.parse(res.getHtml());
        //try {
        //    File file = new File(dir + "list_" + "1" + ".shtml");
        //    if (!file.exists()) {
        //        file.createNewFile();
        //    }
        //    FileUtil.writeString(file, res.getHtml());
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
        //
        //try {
        //    String content = FileUtil.readString(dir + "list_" + "1" + ".shtml");
        //    JSONObject result = JSON.parseObject(content);
        //    Document doc = Jsoup.parse(result.getString("html"));
        //    Element element = doc.select("div.product > div.p_img > a").first();
        //    System.out.println(element.attr("href"));
        //    String href = element.attr("href");
        //    String itemId = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf("."));
        //    System.out.println(itemId);
        //    doc = Jsoup.parse(result.getString("page"));
        //    element = doc.select("a.pagecur").first();
        //    if (element != null) {
        //        System.out.println(element.text());
        //    }
        //    System.out.println("--");
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
        return "consume suc";
    }

}
