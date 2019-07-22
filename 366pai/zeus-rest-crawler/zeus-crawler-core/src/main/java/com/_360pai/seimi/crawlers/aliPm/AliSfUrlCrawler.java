package com._360pai.seimi.crawlers.aliPm;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.commons.AliPmEnum;
import com._360pai.seimi.commons.util.CrawlerUtils;
import com._360pai.seimi.config.AsyncConfig;
import com._360pai.seimi.model.alipm.TAliPmSfUrl;
import com._360pai.seimi.service.alipm.AliPmUrlService;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 案例司法拍卖 请求url
 */
@Crawler(name = "aliSfUrlCity")
@Component
public class AliSfUrlCrawler extends BaseSeimiCrawler {

    @Autowired
    private AsyncConfig asyncConfig;

    @Resource
    private AliPmUrlService aliPmCityUrlService;


    @Override
    public String[] startUrls() {

        return new String[]{"https://sf.taobao.com/item_list.htm"};
    }

    @Override
    public void start(Response response) {

        try {

            List<JXNode> proList = CrawlerUtils.getJXNodeList(response,"//li[@class='triggle']");

            //获取省份信息
            for (JXNode pro : proList) {
                String proName = CrawlerUtils.getFirstJXNodeToStr(pro,"/em/a/text()");
                String proHref = CrawlerUtils.getFirstJXNodeToStr(pro,"/em/a/@href");
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("proName", proName);
                params.put("proHref", proHref);
                push(Request.build("https:"+ proHref, AliSfUrlCrawler::getCityInfo).setMeta(params));
            }
        } catch (Exception e) {


        }


    }


    public void getCityInfo(Response response) {

        try {


            List<JXNode> cityList = CrawlerUtils.getJXNodeList(response,"//div[@class='sub-condition J_SubCondition  hidden ']/ul/li");

            Map<String, Object> mata = response.getMeta();


            String proName = mata.get("proName").toString();
            String proHref = mata.get("proHref").toString();


            for (JXNode city : cityList) {

                String cityName = CrawlerUtils.getFirstJXNodeToStr(city,"/em/a/text()");
                String cityHref = CrawlerUtils.getFirstJXNodeToStr(city,"/em/a/@href");

                Map<String, Object> map = new HashMap<>();

                map.put("proName", proName);
                map.put("proHref", proHref);
                map.put("cityName", cityName);
                map.put("cityHref", cityHref);


                push(Request.build("https:" + cityHref, AliSfUrlCrawler::getAeraInfo).setMeta(map));


                Thread.sleep(200);


            }

        } catch (Exception e) {


        }


    }



    /**
     *
     *获取地区url
     */
    public void getAeraInfo(Response response) {

        try {

            List<JXNode> areaList = CrawlerUtils.getJXNodeList(response,"//div[@class='sub-condition J_SubCondition  small-subcondion']");


            Map<String, Object> mata = response.getMeta();


            for(JXNode area:areaList){


                List<JXNode> list = area.sel("/ul/li");

                String proName = mata.get("proName").toString();
                String proHref = mata.get("proHref").toString();

                String cityName = mata.get("cityName").toString();
                String cityHref = mata.get("cityHref").toString();


                for(JXNode node :list){


                    String areaName = CrawlerUtils.getFirstJXNodeToStr(node,"/em/a/text()");



                    String areaHref ="https:"+ CrawlerUtils.getFirstJXNodeToStr(node,"/em/a/@href");


                    //url 添加标的类型条件
                    for(AliPmEnum.SF_TYPE_CODE e :AliPmEnum.SF_TYPE_CODE.values()){
                        Map<String, Object> map = new HashMap<>();

                        map.put("proName", proName);
                        map.put("proHref", proHref);
                        map.put("cityName", cityName);
                        map.put("cityHref", cityHref);
                        map.put("areaName",areaName);

                        String  areaHref1 = areaHref+"&category="+e.getKey();
                        map.put("typeName",e.getVal());
                        map.put("areaHref",areaHref1);
                        map.put("categoryCode",e.getKey());
                        map.put("categoryName",e.getVal());

                        push(Request.build(areaHref1, AliSfUrlCrawler::getNumInfo).setMeta(map));

                    }


                }



            }



        } catch (Exception e) {



        }


    }



    /**
     *
     *获取地区url
     */
    public void getNumInfo(Response response) {


        asyncConfig.aliZcListExecutor().execute(new Runnable() {
            @Override
            public void run() {

                getExecuteNumInfo(response);

            }
        });






    }

    private void getExecuteNumInfo(Response response) {

        Map<String, Object> mata = response.getMeta();

        JXDocument document = response.document();

        JXNode numNode = document.selNOne("//div[@id='J_LimitFixed']");

        String  num = numNode.sel("/ul/li/em/text()").get(0).toString();

        String proName = mata.get("proName").toString();
        String cityName = mata.get("cityName").toString();
        String areaName = mata.get("areaName").toString();
        String areaHref = mata.get("areaHref").toString();
        String typeName = mata.get("typeName").toString();
        String categoryName = mata.get("categoryName").toString();
        String categoryCode = mata.get("categoryCode").toString();

        TAliPmSfUrl model = new TAliPmSfUrl();
        model.setProName(proName);
        model.setCityName(cityName);
        model.setAreaName(areaName);
        model.setReqUrl(areaHref);
        model.setCreateTime(new Date());
        model.setNum(num);
        model.setTypeName(typeName);
        model.setCategoryCode(categoryCode);
        model.setCategoryName(categoryName);
        model.setStatus(AliPmEnum.URL_STATUS.TODO.getKey());
        aliPmCityUrlService.saveAliPmSfUrl(model);


    }

}