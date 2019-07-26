package com._360pai.crawler.controller.crawler;

import cn.wanghaomiao.seimi.spring.common.CrawlerCache;
import cn.wanghaomiao.seimi.struct.Request;
import com._360pai.crawler.commons.LawtimeConstants;
import com._360pai.crawler.commons.RmfyggConstants;
import com._360pai.crawler.service.RmfyggService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xdrodger
 * @Title: RmfyggController
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/8 19:45
 */
@RestController
@Slf4j
public class LawtimeController {


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
    @RequestMapping(value = "/run/lawtime/court")
    public String runLawtimeCourt() {
        Request request = new Request();
        request.setUrl(LawtimeConstants.FAYUANY_URL);
        request.setCrawlerName("lawtime_court");
        request.setCallBack("start");
        CrawlerCache.consumeRequest(request);
        return "consume suc";
    }
}
