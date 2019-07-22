package com._360pai.seimi.crawlers.gpai;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 获取公拍网 城市yrl
 */
@Crawler(name = "gaiCity")
@Component
public class CityCrawler extends BaseSeimiCrawler {


    @Override
    public String[] startUrls() {

        return new String[]{"http://s.gpai.net/sf/search.do"};
    }


    @Override
    public void start(Response response) {

        try {
            JXDocument document = response.document();

            List<JXNode> proList = document.selN("//ul[@class='condition']/li");


            //获取省份信息
            for (JXNode pro : proList) {


                String name  = pro.sel("/a/text()").get(0).toString();

                String href  = pro.sel("/a/@href").get(0).toString();

                //获取省份的id 参数

                String proId = Pattern.compile("[^0-9]").matcher(href).replaceAll("");

                //拼接请求的参数
                String url = "http://s.gpai.net/sf/AjaxHtml.do?Action=CITY&ID="+proId+"&cityNum="+proId+"&sQuy=http://s.gpai.net/sf/search.do?";


                Thread.sleep(2000);
                push(Request.build(url,CityCrawler::getCityUrl));


            }
        }catch (Exception e){

        }




    }

    public void getCityUrl(Response response) {


        try {
            JXDocument document = response.document();


            List<JXNode> cityList = document.selN("//a");


            //获取城市url
            for (JXNode city : cityList) {

                String name  = city.sel("/text()").get(0).toString();

                String href  = city.sel("/@href").get(0).toString();

                Thread.sleep(2000);


                String proId = Pattern.compile("[^0-9]").matcher(href).replaceAll("");

                //拼接请求的参数
                String url = "http://s.gpai.net/sf/AjaxHtml.do?Action=CITY&ID="+proId+"&cityNum="+proId+"&sQuy=http://s.gpai.net/sf/search.do?";


                if(Integer.valueOf(proId)<10000){
                    push(Request.build(url,CityCrawler::getCityQuUrl));
                }

                System.out.println(name);

                System.out.println(href);

                }




        }catch (Exception e){

        }






    }



    public void getCityQuUrl(Response response) {


        try {
            JXDocument document = response.document();


            List<JXNode> cityList = document.selN("//a");


            //获取城市url
            for (JXNode city : cityList) {

                String name = city.sel("/text()").get(0).toString();

                String href = city.sel("/@href").get(0).toString();

                Thread.sleep(2000);


                System.out.println(name);

                System.out.println(href);

            }


        } catch (Exception e) {

        }


    }

}