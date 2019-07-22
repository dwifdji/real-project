package com._360pai.controller.crawler;

import cn.wanghaomiao.seimi.spring.common.CrawlerCache;
import cn.wanghaomiao.seimi.struct.Request;
import com._360pai.seimi.commons.AZiChanReq;
import com._360pai.seimi.service.TransactionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * create by liuhaolei on 2018/11/13
 */
@RestController
@RequestMapping("/azichan")
public class AZiChanController {

    @Autowired
    private TransactionDataService transactionDataService;

    /**
     * 爬取服务精英数据
     * @return
     */
    @RequestMapping(value = "service_organization")
    public String getServiceOrganization(){
        Request request = new Request();
        request.setCrawlerName("serviceOrganization");
        request.setUrl("http://www.azichan.com/org/list.html?laType=2");
        request.setCallBack("start");

        CrawlerCache.consumeRequest(request);
        return "consume suc";
    }

    /**
     * 爬取服务机构数据
     * @return
     */
    @RequestMapping(value = "service_elite")
    public String getServiceElite(){
        Request request = new Request();
        request.setCrawlerName("serviceElite");
        request.setUrl("http://www.azichan.com/lawyer/list.html?laType=2");
        request.setCallBack("start");

        CrawlerCache.consumeRequest(request);
        return "consume suc";
    }

    /**
     * 爬取成交数据
     * @return
     */
    @RequestMapping(value = "transaction_data")
    public String getTransactionData(){

//        RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
//        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        Integer insertCount = transactionDataService.getTransactionData();

//        Request request = new Request();
//        request.setCrawlerName("transactionData");
//        request.setUrl("http://www.azichan.com/login.do");
//        request.setCallBack("start");
//
//
//        CrawlerCache.consumeRequest(request);
        return "consume suc";
    }


    @RequestMapping(value = "investment_agency")
    public String getInvestmentAgency(AZiChanReq.AZiChanPage aZiChanPage){
        Request request = new Request();
        request.setCrawlerName("investmentAgency");
        request.setUrl("http://www.azichan.com/org/list.html?laType=1");
        request.setCallBack("start");
        Map<String, String> params = new HashMap<String, String>();
        params.put("total", aZiChanPage.getPageTotal());
        request.setParams(params);
        CrawlerCache.consumeRequest(request);
        return "consume suc";
    }



    @RequestMapping(value = "investors")
    public String getInvestors(AZiChanReq.AZiChanPage aZiChanPage){
        Request request = new Request();
        request.setCrawlerName("investors");
        request.setUrl("http://www.azichan.com/lawyer/list.html?page=1&laType=1");
        request.setCallBack("start");
        CrawlerCache.consumeRequest(request);
        return "consume suc";
    }
}
