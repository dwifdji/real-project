package com._360pai.seimi.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import org.apache.commons.lang3.StringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.seimicrawler.xpath.exception.XpathSyntaxErrorException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
@Crawler(name = "transactionData", useCookie = true, useUnrepeated = false )
public class TransactionDataCrawler extends BaseSeimiCrawler {

    private final String baseUrl = "http://www.azichan.com";

    @Override
    public String[] startUrls() {
        return null;
    }

    @Override
    public List<Request> startRequests() {
        List<Request> requests = new LinkedList<>();
        Request start = Request.build("http://www.azichan.com/login.do","start");
        Map<String,String> params = new HashMap<>();
        params.put("account","15538068782");
        params.put("pwd","bit789654");
        start.setHttpMethod(HttpMethod.POST);
        start.setParams(params);
        requests.add(start);
        return requests;
    }

    @Override
    public void start(Response response) {
        String url = response.getUrl();
        Map<String, String> params = response.getParams();
        System.out.println("开始调用的请求是:" + url + ">>>>" + "参数是params" + params);
        String content = response.getContent();
        System.out.println("执行第一个请求路径返回的数据是" + content);
    }


    public void getDetailHtml(Response response) {
        JXDocument doc = response.document();
        String content = response.getContent();
        logger.info("不登录是不是可以进行调用数据啦" + content);
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
