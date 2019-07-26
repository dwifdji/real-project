package com._360pai.crawler.crawlers.jdPm;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.crawler.common.util.StringFormatUtil;
import com._360pai.crawler.dao.jdPm.JdPmTransactionDataAssetsDao;
import com._360pai.crawler.dao.jdPm.JdPmTransactionDataBidRecordAssetsDao;
import com._360pai.crawler.model.jdPm.JdPmTransactionDataAssets;
import com._360pai.crawler.model.jdPm.JdPmTransactionDataBidRecordAssets;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Crawler(name = "jdTransactionDataZiChan", useUnrepeated = false)
public class JDTransactionDataCrawlerZiChan extends BaseSeimiCrawler {

    private static final String DETAILURL = "https://paimai.jdPm.com/";

    @Autowired
    private JdPmTransactionDataAssetsDao jdPmTransactionDataDao;
    @Autowired
    private JdPmTransactionDataBidRecordAssetsDao jdPmTransactionDataBidRecordAssetsDao;

    @Override
    public String[] startUrls() {
        return new String[0];
    }

    @Override
    public void start(Response response) {
        JXDocument doc = response.document();
        List<JXNode> jxNodes = doc.selN("//div[@class='auction-desc']/dl[1]/dd/a");

            for (int i = 0; i < jxNodes.size(); i++) {
                String categoryName = jxNodes.get(i).sel("/@title").get(0).toString();
                String url = jxNodes.get(i).sel("/@href").get(0).toString();

                HashMap<String, Object> params = new HashMap<>();
                params.put("categoryName", categoryName);
                String newUrl = "https:" + url + "&paimaiStatus=2";
                push(Request.build(newUrl.replace("zichan_list.html", "getAssetsList.html"), JDTransactionDataCrawlerZiChan::getCategoryData).setMeta(params));
            }

    }


    public void getCategoryData(Response response){

        System.out.println(response.getContent());
        JSONObject json = JSONObject.parseObject(response.getContent());
        Integer total = Integer.parseInt(json.get("total").toString()) ;
        Map<String, Object> meta = response.getMeta();

        try {
            CookieStore cookieStore = new BasicCookieStore();
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

            for(int i = 1; i < total / 40 + 2; i++) {
                getPageData(response.getUrl() + "&page=" + i, (String)meta.get("categoryName"), httpClient);
            }
        } catch (Exception e) {
            logger.error("处理详情页数据异常{}", e.getMessage());
            e.printStackTrace();
        }
    }


    public void getPageData(String newUrl, String categoryName, CloseableHttpClient httpClient){
        try {
            System.out.println("调用的分页路径是" + newUrl);

            HttpGet get = new HttpGet(newUrl);

            CloseableHttpResponse execute = httpClient.execute(get);
            String html = EntityUtils.toString(execute.getEntity());
            List<JdPmTransactionDataAssets> jdPmTransactionDataList = new ArrayList<>();
            JSONObject json = JSONObject.parseObject(html);

            JSONArray jsonArray = (JSONArray) JSONArray.parse(json.get("ls").toString());
            for(int i = 0; i < jsonArray.size(); i++){
                JSONObject job = jsonArray.getJSONObject(i);
                String id = StringFormatUtil.changeObjectToStr(job.get("id"));
                Map<String, Object> params =  new HashMap<>();
                params.put("id", id);

                JdPmTransactionDataAssets oneByCode = jdPmTransactionDataDao.getOneByCode(id);  //
                if(oneByCode == null) {
                    String detailPageUrl = "https://mpaimai.jdPm.com/json/mobile/getProductbasicInfo.html?paimaiId=";
                    oneByCode = getDetailData(detailPageUrl + id, id, categoryName);
                }

                jdPmTransactionDataList.add(oneByCode);
            }
            jdPmTransactionDataDao.save(jdPmTransactionDataList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理详情页数据异常{}", e.getMessage());
        }
    }


    public JdPmTransactionDataAssets getDetailData(String newUrl, String id, String categoryName){
        try {

            CookieStore cookieStore = new BasicCookieStore();
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

            HttpGet get = new HttpGet(newUrl);
            CloseableHttpResponse execute = httpClient.execute(get);
            String html = EntityUtils.toString(execute.getEntity());
            JSONObject json = JSONObject.parseObject(html);
            //获取地址数据
            JSONObject productAddressJson = JSONObject.parseObject(json.getString("productAddress"));

            //保存详情数据
            JdPmTransactionDataAssets jdPmTransactionData = new JdPmTransactionDataAssets();
            jdPmTransactionData.setCode(id);
            jdPmTransactionData.setCategoryName(categoryName);
            jdPmTransactionData.setStartPrice(StringFormatUtil.changeObjectToStr(json.get("startPrice")));
            jdPmTransactionData.setTitle(StringFormatUtil.changeObjectToStr(json.get("title")));
            jdPmTransactionData.setAssessmentPrice(StringFormatUtil.changeObjectToStr(json.get("assessmentPrice")));
            jdPmTransactionData.setPriceLowerOffset(StringFormatUtil.changeObjectToStr(json.get("priceLowerOffset")));
            jdPmTransactionData.setEnsurePrice(StringFormatUtil.changeObjectToStr(json.get("ensurePrice")));
            jdPmTransactionData.setProvince(StringFormatUtil.changeObjectToStr(productAddressJson.get("province")));
            jdPmTransactionData.setCity(StringFormatUtil.changeObjectToStr(productAddressJson.get("city")));
            jdPmTransactionData.setCounty(StringFormatUtil.changeObjectToStr(productAddressJson.get("county")));
            jdPmTransactionData.setAddress(StringFormatUtil.changeObjectToStr(productAddressJson.get("address")));
            // 优先竞买人后续加上优先竞买人
            String priorPurchaser = StringFormatUtil.changeObjectToStr(json.get("priorPurchaser"));
            jdPmTransactionData.setPriorityFlag(priorPurchaser);

            Date beginTime = changeTimeToDate(StringFormatUtil.changeObjectToStr(json.get("startTime")));
            jdPmTransactionData.setBeginTime(beginTime);

            Date endTime = changeTimeToDate(StringFormatUtil.changeObjectToStr(json.get("endTime")));
            jdPmTransactionData.setEndTime(endTime);
            jdPmTransactionData.setCreateTime(new Date());

            String skuId = StringFormatUtil.changeObjectToStr(json.get("skuId"));
            String vendorId = StringFormatUtil.changeObjectToStr(json.get("verdorId"));

            String agencyUrl = "https://paimai.jdPm.com/json/current/queryVendorInfo.html?vendorId=" + vendorId;
            setAgencyInfo(agencyUrl, httpClient, jdPmTransactionData);

            //设置另外一个详情
            String bidListUrl = "https://paimai.jdPm.com/json/current/englishquery.html?paimaiId="+ id +"&skuId="+skuId+"&start=0&end=9";
            getAnotherDetailData(bidListUrl, httpClient, jdPmTransactionData, skuId);

            String likeListUrl = "https://paimai.jdPm.com/json/ensure/queryAccess?paimaiId=108498022";
            setLikeDetailData(likeListUrl, httpClient, jdPmTransactionData);

            //保存真正的详情数据
            return jdPmTransactionData;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理详情页数据异常{}", e.getMessage());
        }
        return null;
    }


    private void setLikeDetailData(String likeListUrl, CloseableHttpClient httpClient, JdPmTransactionDataAssets jdPmTransactionData) {

        try{
            HttpGet get = new HttpGet(likeListUrl);
            CloseableHttpResponse execute = httpClient.execute(get);
            String html = EntityUtils.toString(execute.getEntity());

            JSONObject json = JSONObject.parseObject(html);

            jdPmTransactionData.setAccessEnsureNum(json.getString("accessEnsureNum"));
            jdPmTransactionData.setAccessNum(json.getString("accessNum"));

         }catch (Exception e){
            logger.error("详情数据3异常", e.getMessage());
        }
    }


    private void setAgencyInfo(String newUrl, CloseableHttpClient httpClient, JdPmTransactionDataAssets jdPmTransactionData) {

        try{
            HttpGet get = new HttpGet(newUrl);
            CloseableHttpResponse execute = httpClient.execute(get);
            String html = EntityUtils.toString(execute.getEntity());
            JSONObject json = JSONObject.parseObject(html);

            String shopName = json.getString("shopName");
            jdPmTransactionData.setShopName(shopName);
        }catch (Exception e){
            logger.error("详情数据3异常", e.getMessage());
        }
    }


    private void getAnotherDetailData(String detailUrl, CloseableHttpClient httpClient,
                                      JdPmTransactionDataAssets jdPmTransactionData, String skuId) {
        try {
            HttpGet get = new HttpGet(detailUrl);

            CloseableHttpResponse execute = httpClient.execute(get);
            String html = EntityUtils.toString(execute.getEntity());
             JSONObject newJson = JSONObject.parseObject(html);

            jdPmTransactionData.setCurrentPrice(StringFormatUtil.changeObjectToStr(newJson.get("currentPrice")));
            jdPmTransactionData.setDelayedCount( StringFormatUtil.changeObjectToStr(newJson.get("delayedTime")));
            jdPmTransactionData.setAccessEnsureNum(StringFormatUtil.changeObjectToStr(newJson.get("accessEnsureNum")));
            jdPmTransactionData.setAccessNum(StringFormatUtil.changeObjectToStr(newJson.get("accessNum")));
            //获取
            String bidCount = StringFormatUtil.changeObjectToStr(newJson.get("bidCount"));
            int intCount = bidCount == null ? 0 : Integer.parseInt(bidCount);

            getBidList(httpClient, jdPmTransactionData.getCode(), intCount, skuId);

        }catch (Exception e){
            logger.error("获取详情2异常", e.getMessage());
        }
    }




    private void getBidList(CloseableHttpClient httpClient, String code, Integer intCount, String skuId) {


        try{
            for (int i = 0; i < intCount / 10 + 1; i++){
               String bidPageListUrl = "https://paimai.jdPm.com/json/current/englishquery.html?paimaiId="
                       + code +"&skuId="+skuId+"&start="+ String.valueOf(i == 0 ? 0 : i * 10) +"&end=" + String.valueOf(i * 10 + 9);
                System.out.println("最新的报名记录是" + bidPageListUrl);
                HttpGet get = new HttpGet(bidPageListUrl);

                CloseableHttpResponse execute = httpClient.execute(get);
                String html = EntityUtils.toString(execute.getEntity());
                JSONObject json = JSONObject.parseObject(html);

                saveBidList(code, StringFormatUtil.changeObjectToStr(json.get("bidList")));
            }
        }catch (Exception e){
            logger.error("获取出价记录异常", e.getMessage());
        }

    }


    private void saveBidList(String code, String bidList) {
        JSONArray jsonArray = JSONArray.parseArray(bidList);

        List<JdPmTransactionDataBidRecordAssets> jdPmTransactionDataBidRecords = new ArrayList<>();
        if(jsonArray != null && jsonArray.size() > 0) {
            for(int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);

                JdPmTransactionDataBidRecordAssets jdPmTransactionDataBidRecord = new JdPmTransactionDataBidRecordAssets();
                jdPmTransactionDataBidRecord.setAuctionCode(code);

                Date bidTime = changeTimeToDate(StringFormatUtil.changeObjectToStr(json.get("bidTime")));
                jdPmTransactionDataBidRecord.setBidTime(bidTime);

                jdPmTransactionDataBidRecord.setPriorFlag(StringFormatUtil.changeObjectToStr(json.get("isPrior")));
                jdPmTransactionDataBidRecord.setPrice(StringFormatUtil.changeObjectToStr(json.get("price")));
                jdPmTransactionDataBidRecord.setUserName(StringFormatUtil.changeObjectToStr(json.get("username")));
                jdPmTransactionDataBidRecord.setCreateTime(new Date());

                jdPmTransactionDataBidRecords.add(jdPmTransactionDataBidRecord);
            }
        }

        jdPmTransactionDataBidRecordAssetsDao.save(jdPmTransactionDataBidRecords);
    }



    private Date changeTimeToDate(String beginTime) {
        long timeTimestamp = Long.parseLong(beginTime);
        return new Date(timeTimestamp);
    }


}
