package com._360pai.crawler.controller.crawler;

import cn.wanghaomiao.seimi.spring.common.CrawlerCache;
import cn.wanghaomiao.seimi.struct.CrawlerModel;
import cn.wanghaomiao.seimi.struct.Request;
import com._360pai.crawler.common.util.DateUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author zxiao
 * @Title: AliPmController
 * @ProjectName 345V1
 * @Description:
 * @date 2018/11/9 9:36
 */
@RestController
public class AliPmController {

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
    @RequestMapping(value = "/run/aliPm")
    public String runAliPm() {
        Request request = new Request();
        request.setCrawlerName("aliPm");

        String endDay = DateUtil.today();
        Date date = DateUtil.lastMouth(3);
        String startDay = DateUtil.format(date, DateUtil.NORM_DATE_PATTERN);
        request.setUrl("https://zc-paimai.taobao.com/zc_item_list.htm?spm=a213w.7398504.filter.46.32af4afcdbcGBp" +
                "&front_category=56956002" +
                "&sorder=-1" +
                "&auction_start_seg=0" +
                "&auction_start_from=" + startDay + "" +
                "&auction_start_to=" + endDay);

        request.setCallBack("start");
        CrawlerCache.consumeRequest(request);
        return "consume suc";
    }

    /**
     * ali住宅贷款信息
     * @return
     */
    @RequestMapping(value = "getGetAliHouseLoan")
    public String getGetAliHouseLoan(){
        Request request = new Request();
        request.setCrawlerName("alihouse");
        request.setUrl("https://sf.taobao.com/item_list.htm?" +
                "spm=a213w.7398504.pagination.2.3ec813dchPFY0E&category=50025969&auction_source=0&st_param=-1&support_loans=1&auction_start_seg=-1");
        request.setCallBack("start");

        CrawlerCache.consumeRequest(request);
        return "consume suc";
    }

    /**
     * ali住宅贷款信息
     * @return
     */
    @RequestMapping(value = "getGetAliHouseLoan2")
    public String getGetAliHouseLoan2(){
        Request request = new Request();
        request.setCrawlerName("alihouse");
        request.setUrl("https://sf.taobao.com/item_list.htm?" +
                "spm=a213w.7398504.filter.3.726413dcWKAcpm&category=200782003&auction_source=0&st_param=-1&support_loans=1&auction_start_seg=-1");
        request.setCallBack("start");

        CrawlerCache.consumeRequest(request);
        return "consume suc";
    }



    @RequestMapping(value = "/run/getAliPmByCity")
    public String getAliPmByCity() {
        CrawlerModel gPai = CrawlerCache.getCrawlerModel("getAliPmByCity");
        gPai.startRequest();
        return "consume suc";
    }




    @RequestMapping(value = "/run/aliSfUrlCity")
    public String aliSfUrlCity() {
        CrawlerModel gPai = CrawlerCache.getCrawlerModel("aliSfUrlCity");
        gPai.startRequest();
        return "consume suc";
    }



    @RequestMapping(value = "/run/getAliPmSfByUrl")
    public String getAliPmSfByUrlCrawler() {
        CrawlerModel gPai = CrawlerCache.getCrawlerModel("getAliPmSfByUrlCrawler");
        gPai.startRequest();
        return "consume suc";
    }







    @RequestMapping(value = "/run/aliCity")
    public String aliCity() {
        CrawlerModel gPai = CrawlerCache.getCrawlerModel("aliCity");
        gPai.startRequest();
        return "consume suc";
    }
}
