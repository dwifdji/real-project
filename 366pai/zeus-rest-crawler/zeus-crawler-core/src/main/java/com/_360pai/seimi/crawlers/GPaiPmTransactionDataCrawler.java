package com._360pai.seimi.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.dao.AliPmTransactionDataBidRecordDao;
import com._360pai.seimi.dao.AliPmTransactionDataDao;
import com._360pai.seimi.model.AliPmTransactionData;
import com._360pai.seimi.model.AliPmTransactionDataBidRecord;
import com._360pai.seimi.util.StringFormatUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create by liuhaolei on 2018-03-18
 */
@Component
@Crawler(name = "gpaiTransactionData", useUnrepeated = false)
public class GPaiPmTransactionDataCrawler extends BaseSeimiCrawler {


    @Override
    public String[] startUrls() {
        return new String[0];
    }

    @Override
    public void start(Response response) {
        JXDocument document = response.document();

        List<JXNode> jxNodes = document.selN("//div[@class='type-select']/div[1]/div[2]/a");
        List<JXNode> newNodes = document.selN("//div[@class='type-select']/div[2]/div[2]/a");

        System.out.println(response.getContent());
        try {
            for(int i = 1; i < 2; i++) {

                String bigCategoryUrl = jxNodes.get(i).sel("/@href").get(0).toString();
                String bigCategoryName = jxNodes.get(i).sel("/text()").get(0).toString();

                for(int j = 1; j < 2; j++) {
                    String categoryUrl = newNodes.get(j).sel("/@href").get(0).toString();
                    String categoryName = newNodes.get(j).sel("/text()").get(0).toString();
                    String substring = categoryUrl.substring(categoryUrl.length() - 6, categoryUrl.length());

                    Map<String, Object> params = new HashMap<>();
                    params.put("bigCategoryName", bigCategoryName);
                    params.put("categoryName", categoryName);

                    push(Request.build(bigCategoryUrl + "&" + substring, GPaiPmTransactionDataCrawler::getCategoryList).setMeta(params));

                    Thread.sleep(1500);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void getCategoryList(Response response) {
        String url = response.getUrl();

        JXDocument doc = response.document();
        try {
            String totalPageUrl = doc.selNOne("//span[@class='page-infos']/label/text()").toString();
            String totalSt = totalPageUrl.substring(1, totalPageUrl.length() - 1);

            Integer totalPage = StringUtils.isBlank(totalSt) ? 0 : Integer.parseInt(totalSt);

            for(int i = 2; i < 3; i++) {
                push(Request.build(  url + "&page=" + i, GPaiPmTransactionDataCrawler::getPageList));

                Thread.sleep(1500);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取分页数据异常{}", e.getMessage());
        }
    }


    public void getPageList(Response response) {
        JXDocument doc = response.document();
        try {
            List<JXNode> jxNodes = doc.selN("//ul[@class='main-col-list clearfix']/li");

            for(int j = 0; j < 1; j++) {
                System.out.println(jxNodes.get(j));

            }

            System.out.println(jxNodes.get(0));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取分页数据异常{}", e.getMessage());
        }
    }
}
