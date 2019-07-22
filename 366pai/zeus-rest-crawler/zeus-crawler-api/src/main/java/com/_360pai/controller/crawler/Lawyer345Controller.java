package com._360pai.controller.crawler;

import cn.wanghaomiao.seimi.spring.common.CrawlerCache;
import cn.wanghaomiao.seimi.struct.Request;
import com._360pai.seimi.commons.AZiChanReq;
import com._360pai.seimi.service.Lawyer365Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Lawyer345Controller {
    @Autowired
    private Lawyer365Service lawyer365Service;
    /**
     * 律师345合同爬虫
     * @param aZiChanPage
     * @return
     */
    @RequestMapping(value = "lawyer345")
    public String getInvestors(AZiChanReq.AZiChanPage aZiChanPage){
        Request request = new Request();
        request.setCrawlerName("lawyer345");
        request.setUrl("http://www.64365.com/contract/all/");
        request.setCallBack("start");
        CrawlerCache.consumeRequest(request);
        return "consume suc";
    }



    /**
     * 律师345合同爬虫
     * @param aZiChanPage
     * @return
     */
    @RequestMapping(value = "downLoadUrls")
    public String downLoadUrls(AZiChanReq.AZiChanPage aZiChanPage){
        lawyer365Service.downLoadUrls();
        return "consume suc";
    }
}
