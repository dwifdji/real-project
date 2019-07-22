package com._360pai.controller.crawler;

import cn.wanghaomiao.seimi.spring.common.CrawlerCache;
import cn.wanghaomiao.seimi.struct.CrawlerModel;
import cn.wanghaomiao.seimi.struct.Request;
import com._360pai.seimi.util.HttpUtilNew;
import com._360pai.seimi.util.HttpUtilNewModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 公拍网
 */
@RestController
@Slf4j
public class GPaiPmController {

    /**
     * 功能描述:  controller方式启动
     * <p>
     * 可以在Controller中实时的添加抓取请求 CrawlerCache.consumeRequest(request);
     * 这里要注意Request对象的几个必填参数，
     * url 要抓取的地址
     * crawlerName 规则name
     * callBack 回调函数
     * 可以通过CrawlerCache.getCrawlerModel(crawlerName);获取到对应的 CrawlerModel，并进行相关信息的查询。
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/11/9 11:04
     */
    @RequestMapping(value = "/run/gPai")
    public String runGPai() {
        Request request = new Request();
        request.setCrawlerName("gPai");
        request.setUrl("http://s.gpai.net/sf/search.do?");
        request.setCallBack("start");
        CrawlerCache.consumeRequest(request);
        return "consume suc";
    }

    @RequestMapping(value = "/run/test")
    public String runTest() {
        CrawlerModel gPai = CrawlerCache.getCrawlerModel("test");
        gPai.startRequest();
        return "consume suc";
    }

    @RequestMapping(value = "/run/testPost")
    public String runTestPost() {
        //BasicNameValuePair[] params = new BasicNameValuePair[]{
        //        new BasicNameValuePair("lsswsbs", "8a8a818c56103c8d01562bb467b2012c")
        //};
        JSONObject json = new JSONObject();
        json.put("lsswsbs", "8a8a818c56103c8d01562bb467b2012c");
        BasicNameValuePair[] params = new BasicNameValuePair[]{
                new BasicNameValuePair("param", json.toJSONString())
        };
        HttpUtilNewModel res = HttpUtilNew.post("http://www.12348.gov.cn/lawdeptinfo/getlawdeptinfo", params);
        System.out.println(JSON.toJSONString(res));


        //List<JSONObject> dataList = new ArrayList<>();
        //HttpUtilNewModel res = HttpUtilNew.post(DEP_INFO_URL, json);
        return "consume suc";
    }
}
