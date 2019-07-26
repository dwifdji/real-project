package com._360pai.crawler.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Response;
import org.springframework.stereotype.Component;

/**
 * @author xdrodger
 * @Title: RmfyggCrawler
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/8 19:40
 */
@Crawler(name = "rmfygg")
@Component
public class RmfyggCrawler extends BaseSeimiCrawler {

    @Override
    public String[] startUrls() {
        return new String[0];
    }

    @Override
    public void start(Response response) {

    }
}
