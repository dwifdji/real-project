package com._360pai.seimi.crawlers;

/**
 * @author xdrodger
 * @Title: TestCrawler
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/16 14:42
 */

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 测试
 */
@Crawler(name = "test", delay = 5)
@Component
public class TestCrawler extends BaseSeimiCrawler {

    @Override
    public String[] startUrls() {
        return new String[0];
    }

    //@Override
    //public List<Request> startRequests() {
    //    List<Request> requests = new LinkedList<>();
    //    Request start = Request.build("http://www.12348.gov.cn/lawdeptinfo/getlawdeptinfo","start");
    //    Map<String,String> params = new HashMap<>();
    //    params.put("lsswsbs","8a8a818c56103c8d01562bb467b2012c");
    //    JSONObject json = new JSONObject();
    //    json.put("lsswsbs", "8a8a818c56103c8d01562bb467b2012c");
    //    BasicNameValuePair[] naps = new BasicNameValuePair[]{
    //            new BasicNameValuePair("param", json.toJSONString())
    //    };
    //    start.setHttpMethod(HttpMethod.POST);
    //    start.setParams(params);
    //    requests.add(start);
    //    return requests;
    //}

    @Override
    public void start(Response response) {
        logger.info(response.getContent());

        System.out.println("--");
    }
}
