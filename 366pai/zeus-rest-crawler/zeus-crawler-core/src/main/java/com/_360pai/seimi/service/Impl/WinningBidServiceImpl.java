package com._360pai.seimi.service.Impl;

import com._360pai.seimi.dao.WinningBidDao;
import com._360pai.seimi.model.WinningBid;
import com._360pai.seimi.service.WinningBidService;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WinningBidServiceImpl implements WinningBidService {

    private static String baseUrl = "http://ztb.shmh.gov.cn/ZCJY/Generate/";

    @Autowired
    private WinningBidDao winningBidDao;

    @Override
    public void getWinningBids() {
        try{
            for(int j = 1; j < 4; j++) {
                CloseableHttpClient httpClient = HttpClients.custom().build();

                HttpPost httpPost = new HttpPost("http://ztb.shmh.gov.cn/ZCJY/Generate/LSSJList.aspx");
                List<NameValuePair> nameValuePairs = new ArrayList<>();
//                nameValuePairs.add(new BasicNameValuePair("__EVENTTARGET", "ctl00$ctl00$Content$Content$DBPager1"));   //自己用户名
//                nameValuePairs.add(new BasicNameValuePair("__EVENTARGUMENT", "go-input"));   //自己用户名
//                nameValuePairs.add(new BasicNameValuePair("__VIEWSTATE", "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"));   //自己用户名
//                nameValuePairs.add(new BasicNameValuePair("__EVENTVALIDATION", "/wEWGwKqn92+CgLx09z7CALkoNupCgKO/cDKAQL/zrDiBgLwoZqMCgLxoZqMCgLyoZqMCgLzoZqMCgL0oZqMCgL1oZqMCgLXjaOTAgKn4uHjDgK+4uHjDgK04sXjDgKh4sXjDgK04pnjDgKv4qHiDgK44oHjDgK/4sXjDgK/4r3jDgKx4r3jDgKO4pXjDgKv4unjDgKv4q3jDgKn4vXjDgLx5vGMD+0kj3nJrSaGZ8mOc9K7nau10lB/"));   //自己用户名
//                nameValuePairs.add(new BasicNameValuePair("ctl00$ctl00$Content$Content$hidTransferType", "cz"));   //自己用户名
//                nameValuePairs.add(new BasicNameValuePair("ctl00$ctl00$Content$Content$txtTransferItemName", ""));   //自己用户名
//                nameValuePairs.add(new BasicNameValuePair("ctl00$ctl00$Content$Content$txtTransferName", ""));   //自己用户名
//                nameValuePairs.add(new BasicNameValuePair("ctl00$ctl00$Content$Content$ddlAssetsLevel", ""));   //自己用户名
//                nameValuePairs.add(new BasicNameValuePair("ctl00$ctl00$Content$Content$ddlTown", ""));   //自己用户名
//                nameValuePairs.add(new BasicNameValuePair("ctl00_ctl00_Content_Content_DBPager1_PageSize", "10"));   //自己用户名
//                nameValuePairs.add(new BasicNameValuePair("ctl00_ctl00_Content_Content_DBPager1_PageIndexFromInput", String.valueOf(j)));   //自己用户名
//                nameValuePairs.add(new BasicNameValuePair("ctl00$ctl00$Content$Content$DBPager1_PageArgs", "96,10,1,10"));   //自己用户名
//                nameValuePairs.add(new BasicNameValuePair("ctl00_ctl00_Content_Content_DBPager2_PageSize", "10"));   //自己用户名
//                nameValuePairs.add(new BasicNameValuePair("ctl00_ctl00_Content_Content_DBPager2_PageIndexFromInput", "1"));   //自己用户名
//                nameValuePairs.add(new BasicNameValuePair("ctl00$ctl00$Content$Content$DBPager2_PageArgs", "0,0,0,10"));   //自己用户名

                nameValuePairs.add(new BasicNameValuePair("__EVENTTARGET", "ctl00$ctl00$Content$Content$DBPager1"));   //自己用户名
                nameValuePairs.add(new BasicNameValuePair("__EVENTARGUMENT", "go-input"));   //自己用户名
                nameValuePairs.add(new BasicNameValuePair("__VIEWSTATE","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"));
                nameValuePairs.add(new BasicNameValuePair("__EVENTVALIDATION","/wEWHAKzpeoUAvHT3PsIAvuVrKAEAuSg26kKAo79wMoBAv/OsOIGAvChmowKAvGhmowKAvKhmowKAvOhmowKAvShmowKAvWhmowKAteNo5MCAqfi4eMOAr7i4eMOArTixeMOAqHixeMOArTimeMOAq/ioeIOArjigeMOAr/ixeMOAr/iveMOArHiveMOAo7ileMOAq/i6eMOAq/ireMOAqfi9eMOAvHm8YwPfYr7TNEpt9d5oKkCeks7Z05k2co=" ));
                nameValuePairs.add(new BasicNameValuePair("ctl00$ctl00$Content$Content$hidTransferType", "cz"));   //自己用户名
                nameValuePairs.add(new BasicNameValuePair("ctl00$ctl00$Content$Content$hidGGYear", "2013"));   //自己用户名
                nameValuePairs.add(new BasicNameValuePair("ctl00$ctl00$Content$Content$txtTransferItemName", ""));   //自己用户名
                nameValuePairs.add(new BasicNameValuePair("ctl00$ctl00$Content$Content$txtTransferName", ""));   //自己用户名
                nameValuePairs.add(new BasicNameValuePair("ctl00$ctl00$Content$Content$ddlAssetsLevel", ""));   //自己用户名
                nameValuePairs.add(new BasicNameValuePair("ctl00$ctl00$Content$Content$ddlTown", ""));   //自己用户名
                nameValuePairs.add(new BasicNameValuePair("ctl00_ctl00_Content_Content_DBPager1_PageSize", "10"));   //自己用户名
                nameValuePairs.add(new BasicNameValuePair("ctl00_ctl00_Content_Content_DBPager1_PageIndexFromInput", String.valueOf(j)));   //自己用户名
                nameValuePairs.add(new BasicNameValuePair("ctl00$ctl00$Content$Content$DBPager1_PageArgs", "26,3,1,10"));   //自己用户名
                nameValuePairs.add(new BasicNameValuePair("ctl00_ctl00_Content_Content_DBPager2_PageSize", "10"));   //自己用户名
                nameValuePairs.add(new BasicNameValuePair("ctl00_ctl00_Content_Content_DBPager2_PageIndexFromInput", "1"));   //自己用户名
                nameValuePairs.add(new BasicNameValuePair("ctl00$ctl00$Content$Content$DBPager2_PageArgs", "0,0,0,10"));   //自己用户名


                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

                String html = EntityUtils.toString(httpResponse.getEntity());

                //创建xPath对象
                JXDocument doc = JXDocument.create(html);
                List<JXNode> jxNodes = doc.selN("//div[@id='cz']/div/table/tbody/tr");

                for(int i = 1; i< jxNodes.size();i++) {
                    JXNode jxNode = jxNodes.get(i);
                    String title = getStringValue(jxNode.sel("/td/table/tbody/tr/td/a/@title").get(0));
                    String detailUrl = getStringValue(jxNode.sel("/td/table/tbody/tr/td[1]/a/@href").get(0));
                    String streetTown = getStringValue(jxNode.sel("/td/table/tbody/tr/td[3]/span/text()").get(0));
                    String hitTime = getStringValue(jxNode.sel("/td/table/tbody/tr/td[5]/text()").get(0));
                    getDetailHtml(baseUrl + detailUrl, httpClient, streetTown, hitTime);
                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void getDetailHtml(String detailUrl, CloseableHttpClient httpClient, String streetTown, String hitTime) {
        try {
            if(StringUtils.isNotBlank(detailUrl)) {
                HttpGet getDetail = new HttpGet(detailUrl);

                CloseableHttpResponse executeCityPage = httpClient.execute(getDetail);
                //创建多线程进行多线程执行
                String detailHtml = EntityUtils.toString(executeCityPage.getEntity());

                JXDocument doc = JXDocument.create(detailHtml);

                List<JXNode> jxNodes = doc.selN("//div[@class='text']/table/tbody/tr");
                String createTime = getStringValue(doc.sel("//div[@class='text']/div[1]/span/text()").get(0));
                String code = getStringValue(doc.sel("//div[@class='text']/div[2]/span/text()").get(0));

                String lessor = getStringValue(jxNodes.get(1).sel("/td[2]/span/text()").get(0));
                String rental = getStringValue(jxNodes.get(2).sel("/td[2]/span/text()").get(0));
                String transferMethod = getStringValue(jxNodes.get(3).sel("/td[2]/span/text()").get(0));
                String bidderQualification = getStringValue(jxNodes.get(4).sel("/td[2]/span/text()").get(0));

                String biddingParty = getStringValue(jxNodes.get(6).sel("/td[2]/div/table/tbody/tr[2]/td[1]/text()").get(0));
                String biddingPrice = getStringValue(jxNodes.get(6).sel("/td[2]/div/table/tbody/tr[2]/td[2]/text()").get(0));
                String biddingTime = getStringValue(jxNodes.get(6).sel("/td[2]/div/table/tbody/tr[2]/td[3]/text()").get(0));

                String winningBidder = getStringValue(jxNodes.get(7).sel("/td[2]/span/text()").get(0));
                String filingTime = getStringValue(jxNodes.get(8).sel("/td[2]/span/text()").get(0));
                String winningPrice = getStringValue(jxNodes.get(9).sel("/td[2]/span/text()").get(0));

                WinningBid winningBid = new WinningBid();
                winningBid.setBidderQualification(bidderQualification);
                winningBid.setBiddingParty(biddingParty);
                winningBid.setBiddingPrice(biddingPrice);
                winningBid.setCode(code);
                winningBid.setLessor(lessor);
                winningBid.setRental(rental);
                winningBid.setTransferMethod(transferMethod);
                winningBid.setCreateTime(new Date());
                winningBid.setBiddingTime(biddingTime);
                winningBid.setFilingTime(filingTime);
                winningBid.setWinningBidder(winningBidder);
                winningBid.setWinningPrice(winningPrice);
                winningBid.setPublicTime(createTime);
                winningBid.setStreetTown(streetTown);
                winningBid.setHitTime(hitTime);
                winningBid.setYearTime("2013");

                winningBidDao.save(winningBid);

            }
        }catch (Exception e){
            e.printStackTrace();
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
