package com._360pai.seimi.service.Impl;

import com._360pai.seimi.dao.InvestmentAssetDao;
import com._360pai.seimi.model.TInvestmentAsset;
import com._360pai.seimi.service.InvestmentAssetService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class InvestmentAssetServiceImpl implements InvestmentAssetService {
    private String baseUrl = "http://www.azichan.com";

    @Resource
    private InvestmentAssetDao investmentAssetDao;
    @Override
    public void savaAssetList(String url, String assetsTotal, Integer investmentId) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        List<NameValuePair> nameValuePairs = null;

        if (httpClient == null) {
            CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            //模拟登陆A资产登陆
            httpPost = new HttpPost("http://www.azichan.com/login.do");
            nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("account", "15538068782"));   //自己用户名
            nameValuePairs.add(new BasicNameValuePair("pwd", "bit789654"));//自己密码
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpClient.execute(httpPost);

            Integer pages = Integer.parseInt(assetsTotal) / 15;
            List<TInvestmentAsset> tInvestmentAssets = null;
            for (int i = 1; i < pages + 2; i++) {
                tInvestmentAssets = new ArrayList<>();
                HttpGet get = new HttpGet(url + "?page=" + i);
                CloseableHttpResponse execute = httpClient.execute(get);
                String htmlSt = EntityUtils.toString(execute.getEntity());

                getPageDetail(httpClient, htmlSt, tInvestmentAssets, investmentId);

                investmentAssetDao.save(tInvestmentAssets);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("获取不良资产详情页数据{}", e.getMessage());
        }
    }

    private void getPageDetail(CloseableHttpClient httpClient, String pageHtml, List<TInvestmentAsset> tInvestmentAssets, Integer investmentId) throws Exception{
        JXDocument doc = JXDocument.create(pageHtml);

        List<JXNode> jxNodes = doc.selN("//div[@class='bulletin']/div");
        for (JXNode jxNode: jxNodes) {

            String assetDate = getStringValue(jxNode.sel("/span/text()").get(0).toString());
            String assetState = getStringValue(jxNode.sel("/em/text()").get(0).toString());
            String assetDetailUrl = getStringValue(jxNode.sel("/a/@href").get(0).toString());
            String assetName = getStringValue(jxNode.sel("/a/text()").get(0).toString());

            HttpGet get = new HttpGet( baseUrl + assetDetailUrl);
            CloseableHttpResponse execute = httpClient.execute(get);
            String detailHtml = EntityUtils.toString(execute.getEntity());

            JXDocument detailDoc = JXDocument.create(detailHtml);
            String detailTxt =  getStringValue(detailDoc.selN ("//div[@class='noticeMain']").get(0));

            TInvestmentAsset tInvestmentAsset = new TInvestmentAsset();
            tInvestmentAsset.setAssetDate(assetDate);
            tInvestmentAsset.setAssetState(assetState);
            tInvestmentAsset.setDetailDoc(detailTxt);
            tInvestmentAsset.setAssetName(assetName);
            tInvestmentAsset.setInvestmentId(String.valueOf(investmentId));
            tInvestmentAssets.add(tInvestmentAsset);
        }

    }

    private String getStringValue(Object object) {
        if(object == null) {
            return "";
        }else {
            return object.toString();
        }
    }
}
