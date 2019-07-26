package com._360pai.crawler.crawlers.lianjia;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.crawler.dao.lianjia.LianJiaAreaModelDao;
import com._360pai.crawler.model.lianjia.LianJiaAreaModel;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Crawler(name = "lianjiaTransactionArea", useUnrepeated = false)
@Component
public class LianJiaTransactionAreaCrawler extends BaseSeimiCrawler {

    @Autowired
    private LianJiaAreaModelDao lianJiaAreaModelDao;

    @Override
    public String[] startUrls() {
        return new String[0];
    }

    @Override
    public void start(Response response) {
        String[] cityUrls = {"https://sh.lianjia.com/chengjiao", "https://bj.lianjia.com/chengjiao",
                "https://gz.lianjia.com/chengjiao", "https://sz.lianjia.com/chengjiao", "https://hz.lianjia.com/chengjiao"};

        for(int j = 0; j < cityUrls.length; j++) {
            push(Request.build(cityUrls[j], LianJiaTransactionAreaCrawler::getCityList));
        }

    }

    public void getCityList(Response response) {
        JXDocument doc = response.document();
        List<JXNode> jxNodes = doc.selN("//div[@data-role = 'ershoufang']/div/a");
        JXNode jxNode = doc.selNOne("//div[@class='position']/dl[1]/h2/dt/@title");
        String citySt = jxNode.toString();
        String city = citySt.substring(0, citySt.indexOf("成交位置"));
        if(jxNodes != null && jxNodes.size() > 0) {
            for(int i = 0, len = jxNodes.size(); i < len; i++) {
                String areaUrl = jxNodes.get(i).sel("/@href").get(0).toString();
                String area = jxNodes.get(i).sel("/text()").get(0).toString();
                Map<String, Object> params = new HashMap<>();
                params.put("city", city);
                params.put("area", area);

                String requestUrl = response.getUrl();
                String substring = requestUrl.substring(0, requestUrl.indexOf("/chengjiao"));
                logger.info("不同城市的区域路径是" + substring + areaUrl);
                params.put("rootUrl", substring);

                String newUrl = areaUrl.contains("lf") ? areaUrl : substring + areaUrl;
                push(Request.build(newUrl, LianJiaTransactionAreaCrawler::getAreaList).setMeta(params));
            }
        }
    }

    public void getAreaList(Response response) {
        JXDocument doc = response.document();
        List<JXNode> jxNodes = doc.selN("//div[@data-role = 'ershoufang']/div[2]/a");

        List<LianJiaAreaModel> lianJiaAreaModels = new ArrayList<>();
        if(jxNodes != null && jxNodes.size() > 0) {
            for(int i = 0, len = jxNodes.size(); i < len; i++) {
                String city = (String) response.getMeta().get("city");
                String area = (String) response.getMeta().get("area");
                String rootUrl = (String) response.getMeta().get("rootUrl");

                LianJiaAreaModel lianJiaAreaModel = new LianJiaAreaModel();
                lianJiaAreaModel.setProvince(city);
                lianJiaAreaModel.setCity(area);
                String areaName = jxNodes.get(i).sel("/text()").get(0).toString();
                String areaUrl = jxNodes.get(i).sel("/@href").get(0).toString();
                lianJiaAreaModel.setArea(areaName);
                lianJiaAreaModel.setAreaUrl(rootUrl + areaUrl);
                lianJiaAreaModel.setCreateTime(new Date());

                lianJiaAreaModels.add(lianJiaAreaModel);
            }
            lianJiaAreaModelDao.save(lianJiaAreaModels);
        }
    }
}
