package com._360pai.seimi.service.Impl;

import com._360pai.seimi.dao.TransactionDataDao;
import com._360pai.seimi.model.TTransactionData;
import com._360pai.seimi.service.TransactionDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
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
public class TransactionDataServiceImpl implements TransactionDataService {

    private final String baseUrl = "http://www.azichan.com";

    private final String cityUrl = "http://www.azichan.com/allnotice/list.html";
    @Resource
    private TransactionDataDao transactionDataDao;

    @Override
    public Integer getTransactionData() {

        List<Cookie> cookies = null;
        String pageUrl = "";
        try {
            CookieStore cookieStore = new BasicCookieStore();
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            //模拟登陆A资产登陆
            HttpPost httpPost = new HttpPost("http://www.azichan.com/login.do");
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("account", "15538068782"));   //自己用户名
            nameValuePairs.add(new BasicNameValuePair("pwd", "bit789654"));//自己密码

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            List<TTransactionData> tTransactionList = null;
            HttpGet get = new HttpGet(cityUrl);

            CloseableHttpResponse execute = httpClient.execute(get);
            String html = EntityUtils.toString(execute.getEntity());
            JXDocument doc = JXDocument.create(html);

            List<JXNode> jxNodes = doc.selN("//div[@class = 'filterList borderBg']/ul/li[1]/div[2]/em");


            for(int i = 1; i < jxNodes.size(); i++) {
                JXNode jxNode = jxNodes.get(i);
                String cityId = jxNode.sel("/@id").get(0).toString();
                String cityName = jxNode.sel("/text()").get(0).toString();
                System.out.println("最终的区域名称是" + cityName);

                String[] cityIdArray = cityId.split("_");
                String realCity = cityIdArray[1];
                HttpGet getCity = new HttpGet(cityUrl + "?cityid=" + realCity );

                CloseableHttpResponse executeCity = httpClient.execute(getCity);
                String cityHtml = EntityUtils.toString(executeCity.getEntity());
                String rapalceHtml = cityHtml.replaceAll(" ", "");
                int totalIndex = rapalceHtml.indexOf("responsePage('/allnotice/list.htm',1,");
                int totalLastIndex = rapalceHtml.indexOf(",10,5,'prevPageId");
                int total = Integer.parseInt(rapalceHtml.substring(totalIndex + 37, totalLastIndex));


                for (int j = 1; j < total / 10 + 2; j++ ) {
                    System.out.println("开始爬取"+ cityName +"第" + j + "页数据");
                    tTransactionList = new ArrayList<>();
                    HttpGet getCityPage = new HttpGet("http://www.azichan.com/allnotice/list.htm?page="+ j + "&cityid=" + realCity);
                    CloseableHttpResponse executeCityPage = httpClient.execute(getCityPage);

                    //创建多线程进行多线程执行
                    getPageDetail(EntityUtils.toString(executeCityPage.getEntity()), httpClient, tTransactionList, cityName);
                    transactionDataDao.save(tTransactionList);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.info("爬取列表页异常{}", e.getMessage());
        }
        return null;
    }

    public void getPageDetail(String pageHtml, CloseableHttpClient httpClient, List<TTransactionData> transactionDataList, String cityName) {

        JXDocument doc = JXDocument.create(pageHtml);
        List<JXNode> jxNodes = doc.selN("//div[@class='mb15 trdate_list_table']/div");

        try {
            if (jxNodes != null && jxNodes.size() > 0){
                String detailUrl = "";
                for (JXNode jxNode:jxNodes ) {
                    String realName = jxNode.sel("/div/p/text()").get(0).toString();
                    TTransactionData tTransactionDataModel = transactionDataDao.getTransactionDataByName(realName);

                    if(tTransactionDataModel == null || StringUtils.isBlank(tTransactionDataModel.getTitle())) {
                        List<JXNode> url = jxNode.sel("/@onclick");
                        detailUrl = url.get(0).toString();
                        detailUrl= baseUrl + detailUrl.substring(detailUrl.indexOf("=") + 2, detailUrl.length() - 1);

                        HttpGet get = new HttpGet(detailUrl);
                        CloseableHttpResponse execute = httpClient.execute(get);
                        TTransactionData tTransactionData = getObjectDetail(EntityUtils.toString(execute.getEntity()), cityName);
                        transactionDataList.add(tTransactionData);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            log.info("爬取详情页异常{}", e.getMessage());
        }
    }

    private TTransactionData getObjectDetail(String detailHtml, String cityName) {
        JXDocument doc = JXDocument.create(detailHtml);

        //标题
        String title = getStringValue(doc.selOne("//h6[@class='debtRightTitle']/b/text()"));
        //资产总额
        String totalAssets = getStringValue(doc.selOne("//div[@class='debtRightInfo']/em[1]/text()"));
        totalAssets = "".equals(totalAssets)? "" : totalAssets.substring(5).replaceAll(" ", "");
        //债权数量
        String claimNumber = getStringValue(doc.selOne("//div[@class='debtRightInfo']/em[2]/text()"));
        claimNumber = "".equals(claimNumber) ? "" : claimNumber.substring(5).replaceAll(" ", "");
        //更新时间
        String updateTime = getStringValue(doc.selOne("//div[@class='debtRightInfo']/em[3]/text()"));
        updateTime = "".equals(updateTime) ? "":updateTime.substring(5);
        //受让方
        String bearCompany = getStringValue(doc.selOne("//div[@class='mainRight']/div[1]/div[2]/div/a/text()"));
        //转让方
        String transferCompany = getStringValue(doc.selOne("//div[@class='mainRight']/div[2]/div[2]/div/a/text()"));
        //联系人
        String  contactPeople = getStringValue(doc.selOne("//div[@class='mainRight']/div[3]/div[2]/div[1]/p/text()"));
        //联系电话
        String  phone = getStringValue(doc.selOne("//div[@class='mainRight']/div[3]/div[2]/div[2]/p/text()"));

        //图片暂时不处理
        String  image = getStringValue(null);

        TTransactionData transactionData = new TTransactionData();
        transactionData.setBearCompany(bearCompany);
        transactionData.setClaimNumber(claimNumber);
        transactionData.setContactPeople(contactPeople);
        transactionData.setImageUrl(image);
        transactionData.setTitle(title);
        transactionData.setUpdateTime(updateTime);
        transactionData.setPhone(phone);
        transactionData.setTransferCompany(transferCompany);
        transactionData.setTotalAssets(totalAssets);
        transactionData.setCityName(cityName);

        return  transactionData;
    }


    private String getStringValue(Object object) {
        if(object == null) {
            return "";
        }else {
            return object.toString();
        }
    }
}
