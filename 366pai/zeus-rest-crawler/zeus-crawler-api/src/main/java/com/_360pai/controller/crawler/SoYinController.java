package com._360pai.controller.crawler;

import com._360pai.seimi.commons.AZiChanReq;
import com._360pai.seimi.service.SoYinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SoYinController {

    @Autowired
    private SoYinService soYinService;

    /**
     * 讼赢合同爬虫程序
     * @param aZiChanPage
     * @return
     */
    @RequestMapping(value = "soyin")
    public String getInvestors(AZiChanReq.AZiChanPage aZiChanPage){
//        Request request = new Request();
//        request.setCrawlerName("soyin");
//        request.setUrl("http://www.soyin.cn/contract/index.html");
//        request.setCallBack("start");
//        CrawlerCache.consumeRequest(request);
        soYinService.getSoYinContract();
        return "consume suc";
    }


    /**
     * 讼赢合同爬虫程序
     * @param aZiChanPage
     * @return
     */
    @RequestMapping(value = "login")
    public String getLoginType(AZiChanReq.AZiChanPage aZiChanPage){

        soYinService.getLoginType();
        return "consume suc";
    }


    /**
     * 讼赢合同爬虫程序
     * @param aZiChanPage
     * @return
     */
    @RequestMapping(value = "sendDataToWin")
    public String sendDataToWin(AZiChanReq.AZiChanPage aZiChanPage){

        soYinService.sendDataToWin();
        return "consume suc";
    }
}
