package com._360pai.seimi.crawlers.gpai;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Response;
import org.apache.commons.lang.StringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 获取公拍网 法院数据
 */
@Crawler(name = "gaiCourt")
@Component
public class CourtCrawler extends BaseSeimiCrawler {


    @Override
    public String[] startUrls() {

        return new String[]{"http://www.gpai.net/sf/courtList.do"};
    }



    @Override
    public void start(Response response) {

        JXDocument document = response.document();

        List<JXNode> cityList = document.selN("//dl[@class='city']");

        int num =0;

        for(JXNode city: cityList){

            List<JXNode> nameList = city.sel("/dt");

            for(JXNode name :nameList){
                String a  = name.sel("/a/text()").get(0).toString();

                String href  = name.sel("/a/@href").get(0).toString();

                String count = Pattern.compile("[^0-9]").matcher(a).replaceAll("");

                System.out.println(a);

                System.out.println(href);

                System.out.println(count);
                if(StringUtils.isNotEmpty(count)){

                    num = num+Integer.valueOf(count);
                }
            }



            List<JXNode> courtList = city.sel("/dd/span");
            for(JXNode court :courtList){

                String a  = court.sel("/a/text()").get(0).toString();

                String href  = court.sel("/a/@href").get(0).toString();


                String count  = court.sel("/span/text()").get(0).toString();

                count = Pattern.compile("[^0-9]").matcher(count).replaceAll("");


                System.out.println(a);

                System.out.println(href);

                System.out.println(count);


                if(StringUtils.isNotEmpty(count)){
                    num = num+Integer.valueOf(count);

                }

            }





        }


        System.out.println("全部数量为："+num);


    }

}
