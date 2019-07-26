package com._360pai.crawler.controller.crawler;

/**
 * @author xdrodger
 * @Title: RmfysszcPmController
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/20 13:29
 */

import cn.wanghaomiao.seimi.spring.common.CrawlerCache;
import cn.wanghaomiao.seimi.struct.CrawlerModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * 人民法院诉讼资产网
 */
@RestController
@Slf4j
public class RmfysszcPmController {

    private static final String LIST_URL = "http://www1.rmfysszc.gov.cn/ProjectHandle.shtml";

    private static final String sepa = File.separator;

    private static final String dir = System.getProperty("user.dir") + sepa + "zeus-rest-crawler" + sepa + "zeus-crawler-core" + sepa + "src" + sepa + "main" + sepa + "lib" + sepa + "rmfysszc" + sepa;

    @RequestMapping(value = "/run/rmfysszc")
    public String runGPai() {
        CrawlerModel gPai = CrawlerCache.getCrawlerModel("rmfysszc");
        gPai.startRequest();
        return "consume suc";
    }

    @RequestMapping(value = "/run/rmfysszcTest")
    public String runTest() {

        return "consume suc";
    }

}
