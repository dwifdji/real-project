package com._360pai.controller.crawler;

import cn.wanghaomiao.seimi.spring.common.CrawlerCache;
import cn.wanghaomiao.seimi.struct.CrawlerModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 中国法律服务网
 */
@RestController
@Slf4j
public class LegalServicesOfChinaController {

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
    @RequestMapping(value = "/run/legalServicesOfChina")
    public String runGPai() {
        CrawlerModel gPai = CrawlerCache.getCrawlerModel("legalServicesOfChina");
        gPai.startRequest();
        return "consume suc";
    }
}
