package com._360pai.controller.crawler;

import com._360pai.seimi.crawlers.Caa123ApiCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 中国拍卖行业协会
 */
@RestController
public class Caa123PaiController {

    @Autowired
    private Caa123ApiCrawler caa123ApiCrawler;

    @RequestMapping(value = "/run/caa123Pai/withCity")
    public String runGPai() {
        caa123ApiCrawler.getListByCity();
        return "OK";
    }

    @RequestMapping(value = "/run/caa123Pai/all")
    public String runGPaiAll() {
        caa123ApiCrawler.getList(0);
        return "OK";
    }

    @RequestMapping(value = "/update/city")
    public String updateCity() {
        caa123ApiCrawler.updateCity();
        return "OK";
    }
}
