package com._360pai.crawler.controller.crawler;

import cn.wanghaomiao.seimi.spring.common.CrawlerCache;
import cn.wanghaomiao.seimi.struct.Request;
import com._360pai.crawler.dao.lianjia.LianJiaAreaModelDao;
import com._360pai.crawler.model.lianjia.LianJiaAreaModel;
import com._360pai.crawler.service.TransactionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionDataController {

    @Autowired
    private LianJiaAreaModelDao lianJiaAreaModelDao;


    /**
     * 京东成交数据
     * @return
     */
    @RequestMapping(value = "getJDTransactionData")
    public String getJDTransactionData(String type){
        Request request = new Request();
        if("1".equals(type)) {
            request.setCrawlerName("jdTransactionDataSiFa");
            request.setUrl("https://auction.jd.com/sifa_list.html");
        }else {
            request.setCrawlerName("jdTransactionDataZiChan");
            request.setUrl("https://auction.jdPm.com/zichan_list.html");
        }
        request.setCallBack("start");
        CrawlerCache.consumeRequest(request);
        return "successful";
    }


    /**
     * 阿里成交数据
     * @return
     */
    @RequestMapping(value = "getALiTransactionData")
    public String getALiTransactionData(){
        Request request = new Request();
        request.setCrawlerName("aliTransactionData");
        request.setUrl("https://sf.taobao.com/item_list.htm?" +
                "spm=a213w.7398504.filter.59.47ec4c38vAcs72&category=50025972&auction_source=0&sorder=2&st_param=-1&auction_start_seg=-1");
        request.setCallBack("start");
        CrawlerCache.consumeRequest(request);
        return "successful";
    }


    /**
     * 公拍网成交数据
     * @return
     */
    @RequestMapping(value = "getGPaiTransactionData")
    public String getGPaiTransactionData(){
        Request request = new Request();
        request.setCrawlerName("gpaiTransactionData");
        request.setUrl("http://s.gpai.net/sf/search.do?restate=3&action=court");
        request.setCallBack("start");
        CrawlerCache.consumeRequest(request);
        return "successful";
    }

    /**
     * 人民法院诉讼网成交数据
     * @return
     */
    @RequestMapping(value = "getRMFYSSWTransactionData")
    public String getRMFYSSWTransactionData(){
        Request request = new Request();
        request.setCrawlerName("rmfysswTransactionData");
        request.setUrl("https://www1.rmfysszc.gov.cn/projects.shtml?dh=3&gpstate=1&wsbm_slt=1");
        request.setCallBack("start");
        CrawlerCache.consumeRequest(request);
        return "successful";
    }


    /**
     * 中国拍卖者协会
     * @return
     */
    @RequestMapping(value = "getCaa123TransactionData")
    public String getCaa123TransactionData(){
        Request request = new Request();
        request.setCrawlerName("caa123TransactionData");
        request.setUrl("https://paimai.caa123.org.cn/caa-search-ws/ws/0.1/lots?start=0&count=12");
        request.setCallBack("start");
        CrawlerCache.consumeRequest(request);
        return "successful";
    }


    /**
     * 链家二手房交易市场
     * @return
     */
    @RequestMapping(value = "getLianJiaTransactionData")
    public String getLianJiaTransactionData(){
        Request request = new Request();
        List<LianJiaAreaModel> all = lianJiaAreaModelDao.findAll();

        if(all == null || all.size() <= 0) {
            request.setCrawlerName("lianjiaTransactionArea");
        }else {
            request.setCrawlerName("lianjiaTransactionData");
        }
        request.setUrl("https://sh.lianjia.com/chengjiao/");
        request.setCallBack("start");
        CrawlerCache.consumeRequest(request);
        return "successful";
    }


    /**
     * 链家二手房交易市场
     * @return
     */
    @RequestMapping(value = "getLianJiaSellData")
    public String getLianJiaSellData(){
        Request request = new Request();
        List<LianJiaAreaModel> all = lianJiaAreaModelDao.findAll();

        if(all == null || all.size() <= 0) {
            request.setCrawlerName("lianjiaTransactionArea");
        }else {
            request.setCrawlerName("lianjiaSellData");
        }
        request.setUrl("https://sh.lianjia.com/ershoufang/");
        request.setCallBack("start");
        CrawlerCache.consumeRequest(request);
        return "successful";
    }




}
