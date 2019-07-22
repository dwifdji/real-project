package com._360pai.seimi.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import org.seimicrawler.xpath.JXDocument;

/**
 * @author zxiao
 * @Title: SeimiAgentCrawler
 * @ProjectName 345V1
 * @Description:
 * @date 2018/11/10 9:17
 */
@Crawler(name = "sagent")
public class SeimiAgentCrawler extends BaseSeimiCrawler {
    @Override
    public String[] startUrls() {
        return new String[]{"http://openlaw.cn/search/judgement/default?type=&typeValue=&lawyerId=&lawFirmId=&courtId=&keyword=%E5%80%BA%E6%9D%83"};
    }

    @Override
    public void start(Response response) {
        Request build = Request.
                build("http://openlaw.cn/search/judgement/default?type=&typeValue=&lawyerId=&lawFirmId=&courtId=&keyword=%E5%80%BA%E6%9D%83", SeimiAgentCrawler::getTtile)
                .useSeimiAgent()
                .setSeimiAgentRenderTime(5000);
        push(build);
    }

    public void getTtile(Response response) {
        System.out.println("response = " + response.getContent());
        JXDocument document = response.document();
        Object object = document.selOne("//*[@id=\"filter-currents\"]/allText()");
        System.out.println("object = " + object);
    }
}
