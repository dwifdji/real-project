package com._360pai.crawler.common.util;

import cn.wanghaomiao.seimi.struct.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.util.List;

/**
 * 描述：爬虫工具类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/4/2 17:52
 */
public class CrawlerUtils {

    public static List<JXNode> getJXNodeList(Response response,String xpath){

        JXDocument document = response.document();

        return document.selN(xpath);
    }


    public static String getFirstJXNodeToStr(JXNode jxNode,String xpath){

        List<JXNode> list =  jxNode.sel(xpath);
        if(list==null||list.size()<1){
            return null;
        }

        return list.get(0).toString();
    }



    public static String getDocumentStr(
            Response response, String cssQuery){

        String content = response.getContent();
        Document parse = Jsoup.parse(content);

        return parse.select(cssQuery).text();
    }

}
