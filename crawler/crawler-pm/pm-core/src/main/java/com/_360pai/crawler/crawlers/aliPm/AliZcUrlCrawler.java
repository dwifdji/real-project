package com._360pai.crawler.crawlers.aliPm;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.crawler.commons.AliPmEnum;
import com._360pai.crawler.model.alipm.TAliPmZcUrl;
import com._360pai.crawler.service.alipm.AliPmUrlService;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取阿里 资产拍卖城市 url
 */
@Crawler(name = "aliCity")
@Component
public class AliZcUrlCrawler extends BaseSeimiCrawler {


    @Resource
    private AliPmUrlService aliPmCityUrlService;


    @Override
    public String[] startUrls() {

        return new String[]{"https://zc-paimai.taobao.com/zc_item_list.htm"};
    }

    @Override
    public void start(Response response) {

        try {
            JXDocument document = response.document();

            //            List<JXNode> proList = document.selN("//li[@class='triggle J_FilterCity']");

            List<JXNode> proList = document.selN("//li[@class='triggle']");


            //获取省份信息
            for (JXNode pro : proList) {


                String proName = pro.sel("/em/a/text()").get(0).toString();

                String proHref = pro.sel("/em/a/@href").get(0).toString();


                Map<String, Object> params = new HashMap<String, Object>();
                params.put("proName", proName);
                params.put("proHref", proHref);

                 push(Request.build("https:" + proHref, AliZcUrlCrawler::getCityInfo).setMeta(params));


            }
        } catch (Exception e) {

        }


    }


    public void getCityInfo(Response response) {

        try {

            JXDocument document = response.document();

            Map<String, Object> mata = response.getMeta();

            List<JXNode> cityList = document.selN("//li[@class='triggle J_FilterCity']");

            String proName = mata.get("proName").toString();
            String proHref = mata.get("proHref").toString();


            for (JXNode city : cityList) {


                String cityName = city.sel("/em/a/text()").get(0).toString();

                String cityHref = city.sel("/em/a/@href").get(0).toString();

                Map<String, Object> map = new HashMap<>();

                map.put("proName", proName);
                map.put("proHref", proHref);
                map.put("cityName", cityName);
                map.put("cityHref", cityHref);


                push(Request.build("https:" + cityHref, AliZcUrlCrawler::getAeraInfo).setMeta(map));


                Thread.sleep(2000);


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

            JXDocument document = response.document();

            Map<String, Object> mata = response.getMeta();

            List<JXNode> areaList = document.selN("//div[@class='sub-condition J_SubCondition  small-subcondion']");


            for(JXNode area:areaList){


                List<JXNode> list = area.sel("/ul/li");

                String proName = mata.get("proName").toString();
                String proHref = mata.get("proHref").toString();

                String cityName = mata.get("cityName").toString();
                String cityHref = mata.get("cityHref").toString();


                for(JXNode node :list){


                    String areaName = node.sel("/em/a/text()").get(0).toString();

                    String areaHref ="https:"+ node.sel("/em/a/@href").get(0).toString();




                    //请求的url 处理 去掉 招商项目
                    areaHref = areaHref.replaceAll("auction_source=0","auction_source=2");

                    //url 添加标的类型条件
                    for(AliPmEnum.TYPE_CODE e :AliPmEnum.TYPE_CODE.values()){


                        for(AliPmEnum.ZICHAN_CODE z :AliPmEnum.ZICHAN_CODE.values()){
                            Map<String, Object> map = new HashMap<>();

                            map.put("proName", proName);
                            map.put("proHref", proHref);
                            map.put("cityName", cityName);
                            map.put("cityHref", cityHref);
                            map.put("areaName",areaName);
                            map.put("typeName",e.getVal());
                            String  areaHref1 = areaHref.replaceAll("_______","_______"+e.getKey());
                            areaHref1 = areaHref1.replaceAll(e.getKey()+"__",e.getKey()+"_"+z.getKey()+"_");
                            map.put("areaHref",areaHref1);
                            map.put("assetName",z.getVal());
                            push(Request.build(areaHref1, AliZcUrlCrawler::getNumInfo).setMeta(map));
                        }




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
        Map<String, Object> mata = response.getMeta();

        JXDocument document = response.document();


        JXNode numNode = document.selNOne("//div[@id='J_LimitFixed']");

        String  num = numNode.sel("/ul/li/em/text()").get(0).toString();


        String proName = mata.get("proName").toString();
        String proHref = mata.get("proHref").toString();

        String cityName = mata.get("cityName").toString();
        String cityHref = mata.get("cityHref").toString();

        String areaName = mata.get("areaName").toString();
        String areaHref = mata.get("areaHref").toString();
        String typeName = mata.get("typeName").toString();


        String assetName = mata.get("assetName").toString();


        TAliPmZcUrl model = new TAliPmZcUrl();
        model.setProName(proName);
        model.setCityName(cityName);
        model.setAreaName(areaName);
        model.setReqUrl(areaHref);
        model.setCreateTime(new Date());
        model.setNum(num);
        model.setTypeName(typeName);
        model.setStatus(AliPmEnum.URL_STATUS.TODO.getKey());
        model.setAssetName(assetName);
        aliPmCityUrlService.saveAliPmCityUrl(model);



    }

}