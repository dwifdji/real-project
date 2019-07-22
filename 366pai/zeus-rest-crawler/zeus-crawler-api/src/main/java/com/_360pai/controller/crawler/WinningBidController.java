package com._360pai.controller.crawler;

import cn.wanghaomiao.seimi.spring.common.CrawlerCache;
import cn.wanghaomiao.seimi.struct.Request;
import com._360pai.seimi.commons.AZiChanReq;
import com._360pai.seimi.service.WinningBidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WinningBidController {

    @Autowired
    private WinningBidService winningBidService;
    /**
     * 讼赢合同爬虫程序
     * @param aZiChanPage
     * @return
     */
    @RequestMapping(value = "winningBid")
    public String getWinningBids(AZiChanReq.AZiChanPage aZiChanPage){
        winningBidService.getWinningBids();
        return "consume suc";
    }
}
